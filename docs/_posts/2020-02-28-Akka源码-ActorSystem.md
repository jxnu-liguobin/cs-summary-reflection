---
title: ActorSystem
categories:
- Akka源码
tags: [源码分析]
---


```scala
/*
* Copyright (C) 2009-2019 Lightbend Inc. <https://www.lightbend.com>
*/

package akka.actor

import java.io.Closeable
import java.util.concurrent._
import java.util.concurrent.atomic.AtomicReference
import java.util.Optional

import akka.actor.dungeon.ChildrenContainer
import akka.actor.setup.{ ActorSystemSetup, Setup }
import akka.annotation.InternalApi
import akka.dispatch._
import akka.event._
import akka.japi.Util.immutableSeq
import akka.util._
import akka.util.Helpers.toRootLowerCase
import com.typesafe.config.{ Config, ConfigFactory }

import scala.annotation.tailrec
import scala.collection.immutable
import scala.compat.java8.FutureConverters
import scala.compat.java8.OptionConverters._
import scala.concurrent.{ ExecutionContext, ExecutionContextExecutor, Future, Promise }
import scala.util.{ Failure, Success, Try }
import scala.util.control.{ ControlThrowable, NonFatal }

//引导程序配置
object BootstrapSetup {

  /**
   * Scala API: Scala API：使用默认值构造引导程序设置
   * 请注意，将其传递给actor系统与根本不传递任何[[BootstrapSetup]]相同
   * 您可以使用BootstrapSetup的各种“with”方法，返回的实例派生一个具有不同于默认值的值的实例
   */
  def apply(): BootstrapSetup = {
    new BootstrapSetup()
  }

  /**
   * Scala API: 创建启动actor系统所需的引导程序设置
   *
   * @see [[BootstrapSetup]] 类加载器、配置、默认的线程池
   */
  def apply(classLoader: Option[ClassLoader], config: Option[Config], defaultExecutionContext: Option[ExecutionContext]): BootstrapSetup =
    new BootstrapSetup(classLoader, config, defaultExecutionContext)

  /**
   * Scala API: 保留默认的类加载器和默认的执行上下文，但使用自定义的配置
   */
  def apply(config: Config): BootstrapSetup = apply(None, Some(config), None)

  /**
   * Java API: 同上，给Java用，Scala使用apply构造，Java使用静态方法
   *
   * @see [[BootstrapSetup]] for description of the properties
   */
  def create(classLoader: Optional[ClassLoader], config: Optional[Config], defaultExecutionContext: Optional[ExecutionContext]): BootstrapSetup =
    apply(classLoader.asScala, config.asScala, defaultExecutionContext.asScala)

  /**
   * Java  API: 同上，给Java用，Scala使用apply构造，Java使用静态方法
   */
  def create(config: Config): BootstrapSetup = apply(config)

  /**
   * Java API: 同上，给Java用，Scala使用apply构造，Java使用静态方法
   */
  def create(): BootstrapSetup = {
    new BootstrapSetup()
  }

}

//选择器，唯一描述符 目前只有下面三种，构造函数私有
abstract class ProviderSelection private(private[akka] val identifier: String)

object ProviderSelection {

  //Akka支持本地、远程和集群使用选择器(一种类似文件路径又比较特殊的URL，具有层级关系)
  case object Local extends ProviderSelection("local")

  case object Remote extends ProviderSelection("remote")

  case object Cluster extends ProviderSelection("cluster")

  /**
   * JAVA API
   */
  def local(): ProviderSelection = Local

  /**
   * JAVA API
   */
  def remote(): ProviderSelection = Remote

  /**
   * JAVA API
   */
  def cluster(): ProviderSelection = Cluster

}

/**
 * 使用[[Bootstrap Setup]]构造函数中的工厂之一创建的actor系统的核心引导程序设置是内部 API
 *
 * @param classLoader             如果未提供ClassLoader，它将通过首先检查当前线程的getContextClassLoader来获取当前的ClassLoader，
 *                                然后尝试遍历堆栈以查找调用方的类加载器，然后退回到与ActorSystem类关联的ClassLoader
 * @param config                  用于actor系统的配置 如果未提供配置，则将从ClassLoader获取默认参考配置
 * @param defaultExecutionContext 如果定义，ExecutionContext将用作此ActorSystem中的默认执行线程池
 *                                如果未提供ExecutionContext，则系统将回退到在“akka.actor.default-dispatcher.default-executor.fallback”下配置的执行线程池
 * @param actorRefProvider        覆盖config中的akka.actor.provider设置，可以是local(默认)，remote或cluster 它也可以是提供者的完全限定的类名
 */
final class BootstrapSetup private(
  val classLoader: Option[ClassLoader] = None,
  val config: Option[Config] = None,
  val defaultExecutionContext: Option[ExecutionContext] = None,
  val actorRefProvider: Option[ProviderSelection] = None) extends Setup {
  //修复默认配置会返回一个新的实例
  def withClassloader(classLoader: ClassLoader): BootstrapSetup =
    new BootstrapSetup(Some(classLoader), config, defaultExecutionContext, actorRefProvider)

  def withConfig(config: Config): BootstrapSetup =
    new BootstrapSetup(classLoader, Some(config), defaultExecutionContext, actorRefProvider)

  def withDefaultExecutionContext(executionContext: ExecutionContext): BootstrapSetup =
    new BootstrapSetup(classLoader, config, Some(executionContext), actorRefProvider)

  //使用远程或集群时指定选择器提供者本地不推荐使用，因为选择器遍历actor树会对性能有影响
  def withActorRefProvider(name: ProviderSelection): BootstrapSetup =
    new BootstrapSetup(classLoader, config, defaultExecutionContext, Some(name))

}

//actor系统的单例对象，用来对外暴露构建ActorSystem实例的接口
object ActorSystem {

  //获取当前akka的版本
  val Version: String = akka.Version.current // generated file

  //获取操作系统的环境  windows linux unix
  val EnvHome: Option[String] = System.getenv("AKKA_HOME") match {
    case null | "" | "." ⇒ None
    case value ⇒ Some(value)
  }
  //获取系统中名字为akka.home的属性值
  val SystemHome: Option[String] = System.getProperty("akka.home") match {
    case null | "" ⇒ None
    case value ⇒ Some(value)
  }

  //若没有配置SystemHome则使用EnvHome
  val GlobalHome: Option[String] = SystemHome orElse EnvHome

  /**
   * 创建一个名称为“default”的新ActorSystem，通过首先检查当前线程的getContextClassLoader来获取当前的ClassLoader，
   * 然后尝试遍历堆栈以查找调用方的类加载器，然后退回到与ActorSystem类关联的ClassLoader 然后，它使用ClassLoader加载默认的参考配置
   */
  def create(): ActorSystem = apply()

  /**
   * 同上，但名字由使用者指定
   */
  def create(name: String): ActorSystem = apply(name)

  /**
   * Java API: 使用指定的名称和设置创建一个新的actor系统，核心actor系统设置在[[BootstrapSetup]]中定义的
   */
  def create(name: String, setups: ActorSystemSetup): ActorSystem = apply(name, setups)

  /**
   * Java API: 同上，内部调用Scala接口
   * def apply(settings: Setup*): ActorSystemSetup = new ActorSystemSetup(settings.map(s ⇒ s.getClass → s).toMap)
   */
  def create(name: String, bootstrapSetup: BootstrapSetup): ActorSystem =
    create(name, ActorSystemSetup.create(bootstrapSetup))

  /**
   * 使用配置config来创建一个名为name的actor系统
   */
  def create(name: String, config: Config): ActorSystem = apply(name, config)

  /**
   * 同上，需要传入类加载器
   */
  def create(name: String, config: Config, classLoader: ClassLoader): ActorSystem = apply(name, config, classLoader)

  /**
   * 请注意，给定的ExecutionContext将由已配置executor =“ default-executor”的所有调度程序使用，
   * 包括那些尚未定义executor设置的调度程序，因此回退至默认值“default-dispatcher.executor”的
   *
   */
  def create(name: String, config: Config, classLoader: ClassLoader, defaultExecutionContext: ExecutionContext): ActorSystem = apply(name, Option(config), Option(classLoader), Option(defaultExecutionContext))

  /**
   * Scala API: 同create，Scala使用 val system = ActorSystem() 创建单例对象的实例
   */
  def apply(): ActorSystem = apply("default")

  /**
   * Scala API: 同create
   */
  def apply(name: String): ActorSystem = apply(name, None, None, None)

  /**
   * Scala API: 同create
   */
  def apply(name: String, setup: ActorSystemSetup): ActorSystem = {
    //获取引导程序配置
    val bootstrapSettings = setup.get[BootstrapSetup]
    //从引导程序配置中获取类加载器，否则查找，底层查找顺序如下(如上classLoader参数的解释)
    //Option(Thread.currentThread.getContextClassLoader) orElse
    //(Reflect.getCallerClass map findCaller) getOrElse getClass.getClassLoader
    val cl = bootstrapSettings.flatMap(_.classLoader).getOrElse(findClassLoader())
    //从引导程序配置中获取配置，否则使用ConfigFactory加载指定的类加载下的默认配置("reference.conf")
    val appConfig = bootstrapSettings.flatMap(_.config).getOrElse(ConfigFactory.load(cl))
    //从引导程序配置中获取默认的执行线程池
    val defaultEC = bootstrapSettings.flatMap(_.defaultExecutionContext)
    //actorSystem的具体实现
    new ActorSystemImpl(name, appConfig, cl, defaultEC, None, setup).start()
  }

  /**
   * Scala API: 同上apply，参数不同
   */
  def apply(name: String, bootstrapSetup: BootstrapSetup): ActorSystem =
    create(name, ActorSystemSetup.create(bootstrapSetup))

  /**
   * Scala API: 同上apply，参数不同
   */
  def apply(name: String, config: Config): ActorSystem = apply(name, Option(config), None, None)

  /**
   * Scala API: 同上apply，参数不同
   */
  def apply(name: String, config: Config, classLoader: ClassLoader): ActorSystem = apply(name, Option(config), Option(classLoader), None)

  /**
   * Scala API: 同上apply，参数不同
   */
  def apply(
    name: String,
    config: Option[Config] = None,
    classLoader: Option[ClassLoader] = None,
    defaultExecutionContext: Option[ExecutionContext] = None): ActorSystem =
    apply(name, ActorSystemSetup(BootstrapSetup(classLoader, config, defaultExecutionContext)))

  /**
   * 设置是ActorSystem的总体设置，还可以方便地访问Config对象(The Typesafe Config Library API)
   */
  class Settings(classLoader: ClassLoader, cfg: Config, final val name: String, val setup: ActorSystemSetup) {

    def this(classLoader: ClassLoader, cfg: Config, name: String) = this(classLoader, cfg, name, ActorSystemSetup())

    /**
     * actor系统配置的后备配置
     */
    final val config: Config = {
      //检查配置
      val config = cfg.withFallback(ConfigFactory.defaultReference(classLoader))
      config.checkValid(ConfigFactory.defaultReference(classLoader), "akka")
      config
    }

    //获取当前akka的版本，和当前使用的是哪中actor(本地、远程、集群)
    final val ConfigVersion: String = getString("akka.version")
    final val ProviderClass: String =
      setup.get[BootstrapSetup]
        .flatMap(_.actorRefProvider).map(_.identifier)
        .getOrElse(getString("akka.actor.provider")) match {
        case "local" ⇒ classOf[LocalActorRefProvider].getName
        //这两个类不能被类引用，因为它们可能不在类路径中(需要额外的依赖，非基础actor系统所需)
        case "remote" ⇒ "akka.remote.RemoteActorRefProvider"
        case "cluster" ⇒ "akka.cluster.ClusterActorRefProvider"
        case fqcn ⇒ fqcn
      }
    //以下配置在akka-actor reference.conf，默认值可能已经更改
    //获取当前配置监护人策略，指/user路径下的监护
    final val SupervisorStrategyClass: String = getString("akka.actor.guardian-supervisor-strategy")
    //创建超时，指ActorSystem.actorOf的超时
    final val CreationTimeout: Timeout = Timeout(config.getMillisDuration("akka.actor.creation-timeout"))
    //向正在启动的顶级actor发送操作的超时 仅当将绑定信箱或CallingThreadDispatcher用于顶级actor时，这才有意义
    final val UnstartedPushTimeout: Timeout = Timeout(config.getMillisDuration("akka.actor.unstarted-push-timeout"))
    //是否允许Java序列化，默认on，以后可能为off不推荐用
    final val AllowJavaSerialization: Boolean = getBoolean("akka.actor.allow-java-serialization")
    //是否允许启用其他序列化绑定，用于兼容
    final val EnableAdditionalSerializationBindings: Boolean =
      !AllowJavaSerialization || getBoolean("akka.actor.enable-additional-serialization-bindings")
    //是否序列化所有消息  序列化和反序列化(非原始)消息以确保不变性，这仅用于测试默认off
    final val SerializeAllMessages: Boolean = getBoolean("akka.actor.serialize-messages")
    //对创建者进行序列化和反序列化(在Props中)以确保可以通过网络发送它们，这仅用于测试 标记为deploy.scope == LocalScope的纯本地部署免于验证默认off
    final val SerializeAllCreators: Boolean = getBoolean("akka.actor.serialize-creators")
    //日志等级 OFF, ERROR, WARNING, INFO, DEBUG 可选
    final val LogLevel: String = getString("akka.loglevel")
    //控制台的日志等级
    final val StdoutLogLevel: String = getString("akka.stdout-loglevel")
    //日志实现，可多个，在引导时注册，默认akka.event.Logging$DefaultLogger
    final val Loggers: immutable.Seq[String] = immutableSeq(getStringList("akka.loggers"))
    //默认的日志调度器
    final val LoggersDispatcher: String = getString("akka.loggers-dispatcher")
    //日志的过滤器 过滤LoggingAdapter在将日志事件发布到eventStream之前使用的日志事件，默认akka.event.DefaultLoggingFilter
    final val LoggingFilter: String = getString("akka.logging-filter")
    //日志启动超时大小 日志是在ActorSystem启动期间同步创建和注册的，并且由于它们是actor，因此此超时用于限制等待时间，默认5s
    final val LoggerStartTimeout: Timeout = Timeout(config.getMillisDuration("akka.logger-startup-timeout"))
    //启动actor系统时，将完整的配置记录在INFO级别这在不确定使用哪种配置时很有用默认off
    final val LogConfigOnStart: Boolean = config.getBoolean("akka.log-config-on-start")
    //将被记录的死信个数，以info级别记录，默认10
    final val LogDeadLetters: Int = toRootLowerCase(config.getString("akka.log-dead-letters")) match {
      case "off" | "false" ⇒ 0
      case "on" | "true" ⇒ Int.MaxValue
      case _ ⇒ config.getInt("akka.log-dead-letters")
    }
    //在actor系统关闭时，关闭对死信的记录
    final val LogDeadLettersDuringShutdown: Boolean = config.getBoolean("akka.log-dead-letters-during-shutdown")

    //该功能以DEBUG级别记录任何收到的消息(这里的记录是在开启debug后会打印出来)
    final val AddLoggingReceive: Boolean = getBoolean("akka.actor.debug.receive")
    //记录所有消息，包括Kill, PoisonPill
    final val DebugAutoReceive: Boolean = getBoolean("akka.actor.debug.autoreceive")
    //记录actor生命周期的更改
    final val DebugLifecycle: Boolean = getBoolean("akka.actor.debug.lifecycle")
    //启用所有LoggingFSM的调试日志，包括events, transitions and timers
    final val FsmDebugEvent: Boolean = getBoolean("akka.actor.debug.fsm")
    //记录eventStream上的订阅的更改
    final val DebugEventStream: Boolean = getBoolean("akka.actor.debug.event-stream")
    //记录为处理的消息(与死信不同)
    final val DebugUnhandledMessage: Boolean = getBoolean("akka.actor.debug.unhandled")
    //启用错误配置的路由器的WARN日志记录
    final val DebugRouterMisconfiguration: Boolean = getBoolean("akka.actor.debug.router-misconfiguration")

    final val Home: Option[String] = config.getString("akka.home") match {
      case "" ⇒ None
      case x ⇒ Some(x)
    }

    //定时器的具体实现类
    final val SchedulerClass: String = getString("akka.scheduler.implementation")
    //此ActorSystem创建的线程是否应为守护程序
    final val Daemonicity: Boolean = getBoolean("akka.daemonic")
    final val JvmExitOnFatalError: Boolean = getBoolean("akka.jvm-exit-on-fatal-error")
    //JVM shutdown, System.exit(-1), in case of a fatal error,such as OutOfMemoryError
    final val JvmShutdownHooks: Boolean = getBoolean("akka.jvm-shutdown-hooks")
    //一致性hash路由器的每个节点的虚拟节点的数量，这里感觉是每个节点之间的虚拟节点数
    final val DefaultVirtualNodesFactor: Int = getInt("akka.actor.deployment.default.virtual-nodes-factor")

    if (ConfigVersion != Version)
      throw new akka.ConfigurationException("Akka JAR version [" + Version + "] does not match the provided config version [" + ConfigVersion + "]")

    /**
     * Returns the String representation of the Config that this Settings is backed by
     */
    override def toString: String = config.root.render

  }

  private[akka] def findClassLoader(): ClassLoader = Reflect.findClassLoader()
}

/**
 * 此类不打算由用户代码扩展 如果你想实际使用自己的Akka，最好考虑扩展[[akka.actor.ExtendedActorSystem]]
 *
 */
abstract class ActorSystem extends ActorRefFactory {

  import ActorSystem._

  /**
   * actor系统的名称，用以区分同一jvm或类加载器下的不同的actor系统
   */
  def name: String

  /**
   * 从提供的配置中提取核心设置
   */
  def settings: Settings

  /**
   * 日志配置
   */
  def logConfiguration(): Unit

  /**
   * 在应用程序守护程序下面构造一个路径，以与[[ActorSystem＃actorSelection]]一起使用
   */
  def /(name: String): ActorPath

  /**
   * Java API: 同/
   */
  def child(child: String): ActorPath = /(child)

  /**
   * 同上
   */
  def /(name: Iterable[String]): ActorPath

  /**
   * Java API: 递归创建，会附加所有孩子actor的名称
   */
  def descendant(names: java.lang.Iterable[String]): ActorPath = /(immutableSeq(names))

  /**
   * 自该时间起的启动时间(以毫秒为单位)
   */
  val startTime: Long = System.currentTimeMillis

  /**
   * 该actor系统的正常运行时间(以秒为单位)
   */
  def uptime: Long = (System.currentTimeMillis - startTime) / 1000

  /**
   * 这个actor系统的主事件总线，例如用于日志
   */
  def eventStream: EventStream

  /**
   * Java API: 同上
   */
  def getEventStream: EventStream = eventStream

  /**
   * 方便的日志记录适配器，用于[[ActorSystem＃eventStream]]
   */
  def log: LoggingAdapter

  /**
   * Actor引用，将消息发送给已停止或不存在的actor时会将消息重新路由到该引用 尽力而为地交付给此actor，因此不能严格保证
   */
  def deadLetters: ActorRef

  //#scheduler
  /**
   * 轻型调度程序，用于在将来某个截止日期之后运行异步任务 不是很精确，但是很廉价
   */
  def scheduler: Scheduler

  //#scheduler

  /**
   * Java API: 同上
   */
  def getScheduler: Scheduler = scheduler

  /**
   * 用于查找已配置的调度程序的Helper对象
   */
  def dispatchers: Dispatchers

  /**
   * 默认的调度器
   **/
  implicit def dispatcher: ExecutionContextExecutor

  /**
   * Java API: 同上
   */
  def getDispatcher: ExecutionContextExecutor = dispatcher

  /**
   * 查找配置的信箱类型的帮助对象
   */
  def mailboxes: Mailboxes

  /**
   * 注册一个代码块(回调)，以便在[actor system.terminate()]]发出并且此actor系统中的所有actor都已停止后运行
   * 通过多次调用此方法，可以注册多个代码块回调将按与注册相反的顺序依次运行，即先运行上次注册
   * 请注意，在完成所有已注册的回调之前，ActorSystem不会终止
   *
   * 如果系统已终止或终止已启动，则抛出RejectedExecutionException
   *
   * Scala API
   */
  def registerOnTermination[T](code: ⇒ T): Unit

  /**
   * Java API: 注册的是Runnable任务
   */
  def registerOnTermination(code: Runnable): Unit

  /**
   * 终止此actor系统这将停止守护者actor，而守护者(监护者)actor又将递归地停止其所有子actor，系统守护者(日志记录参与者所在的位置)，然后执行所有注册的终止处理程序
   * 注意不要使用此actor系统的“dispatcher”在返回的future完成时安排任何操作，因为它在future完成之前已经关闭
   */
  def terminate(): Future[Terminated]

  /**
   * 返回在ActorSystem终止并执行终止挂钩后将完成的未来如果您使用[[registerOnTermination]]注册了任何回调，
   * 则在完成所有注册的回调之前，此方法返回的Future将不会完成
   * 注意不要在这个actor系统的“dispatcher”上安排任何操作，因为它在将来完成之前已经被关闭
   */
  def whenTerminated: Future[Terminated]

  /**
   * 返回一个CompletionStage，它将在ActorSystem终止并执行终止挂钩后完成如果您使用[[registerOnTermination]]注册了任何回调，
   * 则在完成所有注册的回调之前，此方法返回的CompletionStage将不会完成
   * 注意不要在这个actor系统的“dispatcher”上安排任何操作，因为它在将来完成之前已经被关闭
   */
  def getWhenTerminated: CompletionStage[Terminated]

  /**
   * 注册提供的扩展并创建其负载(payload)，如果此扩展尚未注册，则此方法具有putIfAbsent语义，如果正在从另一个执行线程注册，则此方法可能会阻塞，等待负载的初始化
   */
  def registerExtension[T <: Extension](ext: ExtensionId[T]): T

  /**
   * 返回与提供的扩展关联的有效负载，如果未注册，则引发IllegalStateException如果正在从另一个执行线程注册，则此方法可能会阻塞，等待负载的初始化
   */
  def extension[T <: Extension](ext: ExtensionId[T]): T

  /**
   * 返回指定的扩展是否已注册，如果正在从另一个执行线程注册，则此方法可能会阻塞，等待负载的初始化
   */
  def hasExtension(ext: ExtensionId[_ <: Extension]): Boolean
}

/**
 * 拓展ActorSystem
 */
abstract class ExtendedActorSystem extends ActorSystem {

  /**
   * The ActorRefProvider is the only entity which creates all actor references within this actor system.
   */
  def provider: ActorRefProvider

  /**
   * The top-level supervisor of all actors created using system.actorOf(...).
   */
  def guardian: InternalActorRef

  /**
   * The top-level supervisor of all system-internal services like logging.
   */
  def systemGuardian: InternalActorRef

  /**
   * Create an actor in the "/system" namespace. This actor will be shut down
   * during system.terminate only after all user actors have terminated.
   */
  def systemActorOf(props: Props, name: String): ActorRef

  /**
   * A ThreadFactory that can be used if the transport needs to create any Threads
   */
  def threadFactory: ThreadFactory

  /**
   * ClassLoader wrapper which is used for reflective accesses internally. This is set
   * to use the context class loader, if one is set, or the class loader which
   * loaded the ActorSystem implementation. The context class loader is also
   * set on all threads created by the ActorSystem, if one was set during
   * creation.
   */
  def dynamicAccess: DynamicAccess

  /**
   * Filter of log events that is used by the LoggingAdapter before
   * publishing log events to the eventStream
   */
  def logFilter: LoggingFilter

  /**
   * For debugging: traverse actor hierarchy and make string representation.
   * Careful, this may OOM on large actor systems, and it is only meant for
   * helping debugging in case something already went terminally wrong.
   */
  private[akka] def printTree: String

}

/**
 * ActorSystem内部具体实现
 */
@InternalApi
private[akka] class ActorSystemImpl(
  val name: String,
  applicationConfig: Config,
  classLoader: ClassLoader,
  defaultExecutionContext: Option[ExecutionContext],
  val guardianProps: Option[Props], //构造actor实例的配方
  setup: ActorSystemSetup) extends ExtendedActorSystem {

  //验证ActorSystem名字的有效性
  if (!name.matches("""^[a-zA-Z0-9][a-zA-Z0-9-_]*$"""))
    throw new IllegalArgumentException(
      "invalid ActorSystem name [" + name +
        "], must contain only word characters (i.e. [a-zA-Z0-9] plus non-leading '-' or '_')")

  import ActorSystem._

  //死信日志监听器 actor
  @volatile private var logDeadLetterListener: Option[ActorRef] = None
  //配置
  final val settings: Settings = new Settings(classLoader, applicationConfig, name, setup)

  //处理未捕获的异常
  protected def uncaughtExceptionHandler: Thread.UncaughtExceptionHandler =
    new Thread.UncaughtExceptionHandler() {
      def uncaughtException(thread: Thread, cause: Throwable): Unit = {
        cause match {
          //非致命异常
          case NonFatal(_) | _: InterruptedException | _: NotImplementedError | _: ControlThrowable ⇒ log.error(cause, "Uncaught error from thread [{}]", thread.getName)
          case _ ⇒
            //致命异常 结束
            if (cause.isInstanceOf[IncompatibleClassChangeError] && cause.getMessage.startsWith("akka"))
              System.err.println(
                s"""Detected ${cause.getClass.getName} error, which MAY be caused by incompatible Akka versions on the classpath.
                   | Please note that a given Akka version MUST be the same across all modules of Akka that you are using,
                   | e.g. if you use akka-actor [${akka.Version.current} (resolved from current classpath)] all other core
                   | Akka modules MUST be of the same version. External projects like Alpakka, Persistence plugins or Akka
                   | HTTP etc. have their own version numbers - please make sure you're using a compatible set of libraries.
                  """.stripMargin.replaceAll("[\r\n]", ""))

            if (settings.JvmExitOnFatalError)
              try logFatalError("shutting down JVM since 'akka.jvm-exit-on-fatal-error' is enabled for", cause, thread)
              finally System.exit(-1)
            else
              try logFatalError("shutting down", cause, thread)
              finally terminate()
        }
      }

      //内联函数，记录致命异常
      @inline
      private def logFatalError(message: String, cause: Throwable, thread: Thread): Unit = {
        // First log to stderr as this has the best chance to get through in an 'emergency panic' situation:
        import System.err
        err.print("Uncaught error from thread [")
        err.print(thread.getName)
        err.print("]: ")
        err.print(cause.getMessage)
        err.print(", ")
        err.print(message)
        err.print(" ActorSystem[")
        err.print(name)
        err.println("]")
        System.err.flush()
        cause.printStackTrace(System.err)
        System.err.flush()

        // Also log using the normal infrastructure - hope for the best:
        markerLogging.error(LogMarker.Security, cause, "Uncaught error from thread [{}]: " + cause.getMessage + ", " + message + " ActorSystem[{}]", thread.getName, name)
      }
    }

  //可监控的线程池
  final val threadFactory: MonitorableThreadFactory =
    MonitorableThreadFactory(name, settings.Daemonicity, Option(classLoader), uncaughtExceptionHandler)

  /**
   * 这是一个扩展点：通过重写此方法，子类可以控制参与者系统的所有反射活动
   */
  protected def createDynamicAccess(): DynamicAccess = new ReflectiveDynamicAccess(classLoader)

  private val _pm: DynamicAccess = createDynamicAccess()

  def dynamicAccess: DynamicAccess = _pm

  //在INFO级别记录消息
  def logConfiguration(): Unit = log.info(settings.toString)

  //本实例作为系统实现
  protected def systemImpl: ActorSystemImpl = this

  //在系统监护人(/system)下构建actor(系统监护人和ActorSystem是不同的东西，这里的ActorSystem实例实际上是在/user下的，而不是/system，具体参考actor系统的监护)
  //https://dreamylost.cn/akka/Akka-Actor%E7%9A%84%E7%9B%91%E7%9D%A3%E4%B8%8E%E7%9B%91%E6%8E%A7.html
  def systemActorOf(props: Props, name: String): ActorRef = systemGuardian.underlying.attachChild(props, name, systemService = true)

  //在用户监护人(/user)下创建actor
  def actorOf(props: Props, name: String): ActorRef =
    if (guardianProps.isEmpty) guardian.underlying.attachChild(props, name, systemService = false)
    else throw new UnsupportedOperationException(
      //无法在带有自定义用户监护人的ActorSystem上从外部创建顶级参与者
      s"cannot create top-level actor [$name] from the outside on ActorSystem with custom user guardian")

  //同时，但使用默认的随机名，具体实现在Children中如下：
  //val num = Unsafe.instance.getAndAddLong(this, AbstractActorCell.nextNameOffset, 1)
  //Helpers.base64(num)
  def actorOf(props: Props): ActorRef =
    if (guardianProps.isEmpty) guardian.underlying.attachChild(props, systemService = false)
    else throw new UnsupportedOperationException("cannot create top-level actor from the outside on ActorSystem with custom user guardian")

  //关闭，递归关闭孩子节点，关闭是异步的
  def stop(actor: ActorRef): Unit = {
    val path = actor.path
    val guard = guardian.path
    val sys = systemGuardian.path
    path.parent match {
      case `guard` ⇒ guardian ! StopChild(actor)
      case `sys` ⇒ systemGuardian ! StopChild(actor)
      case _ ⇒ actor.asInstanceOf[InternalActorRef].stop()
    }
  }

  import settings._

  // 这提供了基本的日志记录(到stdout)，直到在下面调用.start()为止
  val eventStream = new EventStream(this, DebugEventStream)
  eventStream.startStdoutLogger(settings)

  val logFilter: LoggingFilter = {
    val arguments = Vector(classOf[Settings] → settings, classOf[EventStream] → eventStream)
    dynamicAccess.createInstanceFor[LoggingFilter](LoggingFilter, arguments).get
  }

  private[this] val markerLogging = new MarkerLoggingAdapter(eventStream, getClass.getName + "(" + name + ")", this.getClass, logFilter)
  val log: LoggingAdapter = markerLogging

  val scheduler: Scheduler = createScheduler()

  val provider: ActorRefProvider = try {
    val arguments = Vector(
      classOf[String] → name,
      classOf[Settings] → settings,
      classOf[EventStream] → eventStream,
      classOf[DynamicAccess] → dynamicAccess)

    dynamicAccess.createInstanceFor[ActorRefProvider](ProviderClass, arguments).get
  } catch {
    case NonFatal(e) ⇒
      Try(stopScheduler())
      throw e
  }

  //死信引用
  def deadLetters: ActorRef = provider.deadLetters

  //信箱
  val mailboxes: Mailboxes = new Mailboxes(settings, eventStream, dynamicAccess, deadLetters)

  //调度程序
  val dispatchers: Dispatchers = new Dispatchers(settings, DefaultDispatcherPrerequisites(
    threadFactory, eventStream, scheduler, dynamicAccess, settings, mailboxes, defaultExecutionContext))

  val dispatcher: ExecutionContextExecutor = dispatchers.defaultGlobalDispatcher

  val internalCallingThreadExecutionContext: ExecutionContext =
    dynamicAccess.getObjectFor[ExecutionContext]("scala.concurrent.Future$InternalCallbackExecutor$").getOrElse(
      new ExecutionContext with BatchingExecutor {
        override protected def unbatchedExecute(r: Runnable): Unit = r.run()

        override protected def resubmitOnBlock: Boolean = false // Since we execute inline, no gain in resubmitting
        override def reportFailure(t: Throwable): Unit = dispatcher reportFailure t
      })

  //关闭actor系统的回调
  private[this] final val terminationCallbacks = new TerminationCallbacks(provider.terminationFuture)(dispatcher)

  override def whenTerminated: Future[Terminated] = terminationCallbacks.terminationFuture

  override def getWhenTerminated: CompletionStage[Terminated] = FutureConverters.toJava(whenTerminated)

  //查找actor系统的顶级守护者(/root监护者)
  def lookupRoot: InternalActorRef = provider.rootGuardian

  //用户actor顶级的守护者(/user)
  def guardian: LocalActorRef = provider.guardian

  //系统actor的顶级守护者(/system)
  def systemGuardian: LocalActorRef = provider.systemGuardian

  //在/user下查询指定actor名称的actor的路径
  def /(actorName: String): ActorPath = guardian.path / actorName

  def /(path: Iterable[String]): ActorPath = guardian.path / path

  //检查版本用
  private def allModules: List[String] = List(
    "akka-actor",
    "akka-actor-testkit-typed",
    "akka-actor-typed",
    "akka-agent",
    "akka-camel",
    "akka-cluster",
    "akka-cluster-metrics",
    "akka-cluster-sharding",
    "akka-cluster-sharding-typed",
    "akka-cluster-tools",
    "akka-cluster-typed",
    "akka-discovery",
    "akka-distributed-data",
    "akka-multi-node-testkit",
    "akka-osgi",
    "akka-persistence",
    "akka-persistence-query",
    "akka-persistence-shared",
    "akka-persistence-typed",
    "akka-protobuf",
    "akka-remote",
    "akka-slf4j",
    "akka-stream",
    "akka-stream-testkit",
    "akka-stream-typed")

  @volatile private var _initialized = false

  /**
   * 断言ActorSystem已完全初始化
   */
  def assertInitialized(): Unit =
    if (!_initialized)
      throw new IllegalStateException(
        "The calling code expected that the ActorSystem was initialized but it wasn't yet. " +
          "This is probably a bug in the ActorSystem initialization sequence often related to initialization of extensions. " +
          "Please report at https://github.com/akka/akka/issues."
      )

  //启动，注册关闭回调任务，验证模块的版本，对事件系统，拓展和日志系统进行初始化
  private lazy val _start: this.type = try {

    registerOnTermination(stopScheduler())
    // the provider is expected to start default loggers, LocalActorRefProvider does this
    provider.init(this)
    // at this point it should be initialized "enough" for most extensions that we might want to guard against otherwise
    _initialized = true

    //配置死信个数时开启死信监听器，使用systemActorOf在system/节点上添加子actor
    if (settings.LogDeadLetters > 0)
      logDeadLetterListener = Some(systemActorOf(Props[DeadLetterListener], "deadLetterListener"))
    //必须在actor系统“就绪”后调用，Starts system actor that takes care of unsubscribing subscribers that have terminated.
    eventStream.startUnsubscriber()
    ManifestInfo(this).checkSameVersion("Akka", allModules, logWarning = true)
    //加载所有拓展
    loadExtensions()
    if (LogConfigOnStart) logConfiguration()
    this
  } catch {
    case NonFatal(e) ⇒
      try terminate() catch {
        case NonFatal(_) ⇒ Try(stopScheduler())
      }
      throw e
  }

  def start(): this.type = _start

  //注册runnable任务，在关闭时执行回退 与下面不同的是，这个是传入处理任务的函数而不是Runnable对象
  def registerOnTermination[T](code: ⇒ T): Unit = {
    registerOnTermination(new Runnable {
      def run = code
    })
  }

  //注册runnable任务，在关闭时执行回退
  def registerOnTermination(code: Runnable): Unit = {
    terminationCallbacks.add(code)
  }

  override def terminate(): Future[Terminated] = {
    //在actor系统关闭时，不需要对死信记录则将死信监听器全部关闭
    if (!settings.LogDeadLettersDuringShutdown) logDeadLetterListener foreach stop
    //关闭监护人actor，ActorSystem
    guardian.stop()
    whenTerminated //获取terminationCallbacks的Future
  }

  @volatile var aborting = false

  /**
   * 这种关闭试图关闭系统并释放资源比普通关机更强大例如，它不会等待远程部署的子角色终止，然后终止其父角色
   */
  def abort(): Unit = {
    aborting = true
    terminate()
  }

  //#create-scheduler
  /**
   * Create the scheduler service. This one needs one special behavior: if
   * Closeable, it MUST execute all outstanding tasks upon .close() in order
   * to properly shutdown all dispatchers.
   *
   * Furthermore, this timer service MUST throw IllegalStateException if it
   * cannot schedule a task. Once scheduled, the task MUST be executed. If
   * executed upon close(), the task may execute before its timeout.
   */
  protected def createScheduler(): Scheduler =
    dynamicAccess.createInstanceFor[Scheduler](settings.SchedulerClass, immutable.Seq(
      classOf[Config] → settings.config,
      classOf[LoggingAdapter] → log,
      classOf[ThreadFactory] → threadFactory.withName(threadFactory.name + "-scheduler"))).get

  //#create-scheduler

  /*
   * This is called after the last actor has signaled its termination, i.e.
   * after the last dispatcher has had its chance to schedule its shutdown
   * action.
   */
  protected def stopScheduler(): Unit = scheduler match {
    case x: Closeable ⇒ x.close()
    case _ ⇒
  }

  private val extensions = new ConcurrentHashMap[ExtensionId[_], AnyRef]

  /**
   * 返回注册到指定扩展的任何扩展；如果未注册，则返回null
   */
  @tailrec
  private def findExtension[T <: Extension](ext: ExtensionId[T]): T = extensions.get(ext) match {
    case c: CountDownLatch ⇒
      c.await(); findExtension(ext) //正在注册，等待完成并重试
    case t: Throwable ⇒ throw t //初始化失败，再次抛出相同的
    case other ⇒
      other.asInstanceOf[T] //可以是T或null，在这种情况下，我们将null返回为T
  }

  /**
   * 注册拓展
   */
  @tailrec
  final def registerExtension[T <: Extension](ext: ExtensionId[T]): T = {
    findExtension(ext) match {
      case null ⇒ //不存在，开始注册
        val inProcessOfRegistration = new CountDownLatch(1)
        extensions.putIfAbsent(ext, inProcessOfRegistration) match { //表示正在进行注册的信号
          case null ⇒ try { // 信号已成功发送
            ext.createExtension(this) match { // 创建并初始化扩展
              case null ⇒ throw new IllegalStateException(s"Extension instance created as 'null' for extension [$ext]")
              case instance ⇒
                extensions.replace(ext, inProcessOfRegistration, instance) //用初始化的扩展名替换inProcess信号
                instance //Profit!
            }
          } catch {
            case t: Throwable ⇒
              extensions.replace(ext, inProcessOfRegistration, t) //移除inProcess信号
              throw t //升级
          } finally {
            inProcessOfRegistration.countDown //始终将进程内信号通知监听器
          }
          case _ ⇒ registerExtension(ext) //其他人正在为此扩展注册扩展，请重试
        }
      case existing ⇒ existing.asInstanceOf[T]
    }
  }

  //获取拓展
  def extension[T <: Extension](ext: ExtensionId[T]): T = findExtension(ext) match {
    case null ⇒ throw new IllegalArgumentException(s"Trying to get non-registered extension [$ext]")
    case some ⇒ some.asInstanceOf[T]
  }

  def hasExtension(ext: ExtensionId[_ <: Extension]): Boolean = findExtension(ext) != null

  //加载拓展
  private def loadExtensions(): Unit = {
    /**
     * 加载扩展失败时引发异常(向后兼容需要)
     */
    def loadExtensions(key: String, throwOnLoadFail: Boolean): Unit = {
      immutableSeq(settings.config.getStringList(key)) foreach { fqcn ⇒
        dynamicAccess.getObjectFor[AnyRef](fqcn) recoverWith { case _ ⇒ dynamicAccess.createInstanceFor[AnyRef](fqcn, Nil) } match {
          case Success(p: ExtensionIdProvider) ⇒ registerExtension(p.lookup())
          case Success(p: ExtensionId[_]) ⇒ registerExtension(p)
          case Success(_) ⇒
            if (!throwOnLoadFail) log.error("[{}] is not an 'ExtensionIdProvider' or 'ExtensionId', skipping...", fqcn)
            else throw new RuntimeException(s"[$fqcn] is not an 'ExtensionIdProvider' or 'ExtensionId'")
          case Failure(problem) ⇒
            if (!throwOnLoadFail) log.error(problem, "While trying to load extension [{}], skipping...", fqcn)
            else throw new RuntimeException(s"While trying to load extension [$fqcn]", problem)
        }
      }
    }

    //列出应在actor系统启动时加载的扩展的(Full Qualified Class Name)
    //库扩展是在启动时加载的常规扩展，并且
    //可供第三方库作者在类路径上显示扩展时启用自动加载
    loadExtensions("akka.library-extensions", throwOnLoadFail = true)
    //列出应在actor系统启动时加载的扩展的FQCN
    loadExtensions("akka.extensions", throwOnLoadFail = false)
  }

  override def toString: String = lookupRoot.path.root.address.toString

  //打印actor树
  override def printTree: String = {
    def printNode(node: ActorRef, indent: String): String = {
      node match {
        case wc: ActorRefWithCell ⇒
          val cell = wc.underlying
          (if (indent.isEmpty) "-> " else indent.dropRight(1) + "⌊-> ") +
            node.path.name + " " + Logging.simpleName(node) + " " +
            (cell match {
              case real: ActorCell ⇒ if (real.actor ne null) real.actor.getClass else "null"
              case _ ⇒ Logging.simpleName(cell)
            }) +
            (cell match {
              case real: ActorCell ⇒ " status=" + real.mailbox.currentStatus
              case _ ⇒ ""
            }) +
            " " + (cell.childrenRefs match {
            case ChildrenContainer.TerminatingChildrenContainer(_, toDie, reason) ⇒
              "Terminating(" + reason + ")" +
                (toDie.toSeq.sorted mkString("\n" + indent + "   |    toDie: ", "\n" + indent + "   |           ", ""))
            case x@(ChildrenContainer.TerminatedChildrenContainer | ChildrenContainer.EmptyChildrenContainer) ⇒ x.toString
            case n: ChildrenContainer.NormalChildrenContainer ⇒ n.c.size + " children"
            case x ⇒ Logging.simpleName(x)
          }) +
            (if (cell.childrenRefs.children.isEmpty) "" else "\n") +
            ({
              val children = cell.childrenRefs.children.toSeq.sorted
              val bulk = children.dropRight(1) map (printNode(_, indent + "   |"))
              bulk ++ (children.lastOption map (printNode(_, indent + "    ")))
            } mkString ("\n"))
        case _ ⇒
          indent + node.path.name + " " + Logging.simpleName(node)
      }
    }

    printNode(lookupRoot, "")
  }

  //关闭时的回调实现
  final class TerminationCallbacks[T](upStreamTerminated: Future[T])(implicit ec: ExecutionContext) {
    private[this] final val done = Promise[T]()
    private[this] final val ref = new AtomicReference(done)

    //onComplete从不触发两次这样安全以避免空检查
    upStreamTerminated onComplete {
      //设置为null并返回旧值
      t ⇒ ref.getAndSet(null).complete(t)
    }

    /**
     * 添加将在ActorSystem终止时执行的任务注意，回调的执行顺序与插入顺序相反
     *
     * @param r 如果在ActorSystem终止后调用抛出RejectedExecutionException
     */
    final def add(r: Runnable): Unit = {
      @tailrec def addRec(r: Runnable, p: Promise[T]): Unit = ref.get match {
        case null ⇒ throw new RejectedExecutionException("ActorSystem already terminated.")
        case some if ref.compareAndSet(some, p) ⇒ some.completeWith(p.future.andThen { case _ ⇒ r.run() })
        case _ ⇒ addRec(r, p)
      }

      addRec(r, Promise[T]())
    }

    /**
     * 返回一个future，该future将在执行所有已注册的回调之后完成
     */
    def terminationFuture: Future[T] = done.future
  }

}
```