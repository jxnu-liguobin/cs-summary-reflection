---
title: akka基本使用例子
categories:
- Scala
tags: [Scala]
---

* 目录
{:toc}

#### 说明

    Gradle依赖版本 compile "com.typesafe.akka:akka-actor_2.11:2.4.4"
    
    
- [库 - scala-akka-crawler](https://github.com/jxnu-liguobin/scala-akka-crawler)

#### 入门实例（helloworld）

```scala
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object Test {

    /**
     * actor1
     */
    class Actor1 extends Actor with ActorLogging {

        //创建子Actor，每个对象都有ActorContext对象，可通过context方法获取
        val actor2 = context.actorOf(Props(new Actor2("actor2")), "actor2")

        //重写接收方法
        override def receive: Actor.Receive = {
            //只接收，并打印日志，ActorLogging接口
            case "test" => log.info("received test!")
            //接收，并发送给子Actor tell与!等同，tell有两个参数，使用!时第二个参数会成为隐式参数
            //sender是函数而不是val实例，使用它可以获取当前消息的发送者，该发送者与收到的消息处于同一个上下文中
            //最好使用额外的val变量保存sender方法的值。
            case msg => actor2.tell(msg, sender())
        }
    }

    /**
     * actor2
     *
     * @param name
     */
    class Actor2(name: String) extends Actor with ActorLogging {
    
        override def receive = {
            //打印消息+发送者
            case msg => log.info(name + " received message [{}] from sender of [{}]", msg, sender)
        }
    }

    def main(args: Array[String]): Unit = {
        //1.创建守护对象
        val demo = ActorSystem("TestActorSystem")
        //2.在守护对象下方创建Actor对象并获取该对象的ActorRef引用
        val actor1 = demo.actorOf(Props[Actor1], name = "actor1")
        //3.使用ActorRef引用向Actor对象发送消息
        actor1 ! "test"
        actor1 ! "你好"
    }

}
```

#### 创建监督策略

```scala
//首先，这是一对一的策略，这意味着每个孩子被分开对待(一个完全对一的策略工作非常相似，唯一的区别是，任何决定都适用于主管的所有孩子，而不仅仅是失败的)。
//在上面的示例中，10分钟和1分钟分别传递给maxNrOfRetry和withinTimeRange参数，这意味着策略以每分钟10次重新启动子策略。
//如果在withinTimeRange持续时间内重新启动计数超过maxNrOfRetry，则停止子actor。
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._

override val supervisorStrategy =
  OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException      => Resume
    case _: NullPointerException     => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception                => Escalate
  }
```

当未为参与者定义监督策略时，默认情况下将处理下列异常：

* ActorInitializationException会stop失败的子actor
* ActorKilledException会stop失败的子actor
* DeathPactException会stop失败的子actor
* Exception将重新启动失败的子actor
* 其他类型Throwable将升级为子actor

您可以将自己的策略与默认策略结合起来：

```scala
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._

override val supervisorStrategy =
  OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException => Resume
    case t =>
      super.supervisorStrategy.decider.applyOrElse(t, (_: Any) => Escalate)
  }
```

#### 测试

准备一个合适的主管Actor：

```scala
import akka.actor.Actor

class Supervisor extends Actor {
  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._
  import scala.concurrent.duration._

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException      => Resume
      case _: NullPointerException     => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception                => Escalate
    }

  def receive = {
    case p: Props => sender() ! context.actorOf(p)
  }
}
```

这个主管Actor将被用来创造一个子actor，我们可以用这个子actor做测试：

```scala
import akka.actor.Actor

class Child extends Actor {
  var state = 0
  def receive = {
    case ex: Exception => throw ex
    case x: Int        => state = x
    case "get"         => sender() ! state
  }
}
```

通过使用TestActorSystems中描述的实用程序，测试更容易：

```scala
import com.typesafe.config.{ Config, ConfigFactory }
import org.scalatest.{ BeforeAndAfterAll, Matchers }
import akka.testkit.{ EventFilter, ImplicitSender, TestActors, TestKit }

class FaultHandlingDocSpec(_system: ActorSystem)
    extends TestKit(_system)
    with ImplicitSender
    with WordSpecLike
    with Matchers
    with BeforeAndAfterAll {

  def this() =
    this(
      ActorSystem(
        "FaultHandlingDocSpec",
        ConfigFactory.parseString("""
      akka {
        loggers = ["akka.testkit.TestEventListener"]
        loglevel = "WARNING"
      }
      """)))

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "A supervisor" must {
    "apply the chosen strategy for its child" in {
      // code here
    }
  }
}
```

创建actor：

```scala
val supervisor = system.actorOf(Props[Supervisor], "supervisor")

supervisor ! Props[Child]
val child = expectMsgType[ActorRef] // retrieve answer from TestKit’s testActor
```

第一个测试将演示Resume，因此我们尝试在actor中设置一些非初始状态，然后失败：

```scala
child ! 42 // set state to 42
child ! "get"
expectMsg(42)

child ! new ArithmeticException // crash it
child ! "get"
expectMsg(42)
```

如您所见，值42在故障处理指令中幸存下来。现在，如果我们将失败更改为更严重的NullPointerException，则不再是这样的：

```scala
child ! new NullPointerException // crash it harder
child ! "get"
expectMsg(0)
```

最后，在发生致命IllegalArgumentException事件时，主管actor将终止该子actor：

```scala
watch(child) // have testActor watch “child”
child ! new IllegalArgumentException // break it
expectMsgPF() { case Terminated(`child`) => () }
```

到目前为止，主管actor完全没有受到子actor的失败的影响，因为命令确实处理了它。在出现异常的情况下，这种情况不再是真的，并且主管actor会使故障升级。

```scala
supervisor ! Props[Child] // create new child
val child2 = expectMsgType[ActorRef]
watch(child2)
child2 ! "get" // verify it is alive
expectMsg(0)

child2 ! new Exception("CRASH") // escalate failure
expectMsgPF() {
  case t @ Terminated(`child2`) if t.existenceConfirmed => ()
}
```

主管actor本身由ActorSystem提供的顶级actor进行监督，该系统具有在所有异常情况下重新启动的默认策略(ActorInitializationException和ActorledKilException的显著例外)。
因为在重新启动的情况下，默认的指令是杀死所有的子actor，所以我们希望的子actor不能在这个失败中生存下来。如果这是不想要的(这取决于用例)，我们需要使用一个不同的监督者来覆盖这个行为。

```scala
class Supervisor2 extends Actor {
  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._
  import scala.concurrent.duration._

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException      => Resume
      case _: NullPointerException     => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception                => Escalate
    }

  def receive = {
    case p: Props => sender() ! context.actorOf(p)
  }
  // override default to kill all children during restart
  override def preRestart(cause: Throwable, msg: Option[Any]): Unit = {}
}
```

有了这个父程序，子程序在升级后的重新启动中存活下来，如上一次测试所示：

```scala
val supervisor2 = system.actorOf(Props[Supervisor2], "supervisor2")

supervisor2 ! Props[Child]
val child3 = expectMsgType[ActorRef]

child3 ! 23
child3 ! "get"
expectMsg(23)

child3 ! new Exception("CRASH")
child3 ! "get"
expectMsg(0)
```

#### Actor初始化

```scala
import akka.actor.Actor

/**
 * actor四大默认存在的方法
 * 类似Java Servlet
 * @author 梦境迷离
 * @time 2019-02-11
 */
class ShoppingCart extends Actor {
    //必须拓展（混入）Actor特质【UntypedAbstractActor特质也可以，实现非类型化的actor】
    //按照逻辑排序的四个方法
    //开始之前，大多数情况下需要重写
    //代码块的代码被删除了
    override def preStart(): Unit = {}

    //终止之后，大多数情况下需要重写
    override def postStop(): Unit = {}

    //重启之前，通常不需要重写
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {}

    //重启之后，默认调用preStart，通常不需要重写
    /** Actor源码该方法中
     * def postRestart(reason: Throwable): Unit = {
     * preStart()
     * }
     */
    override def postRestart(reason: Throwable): Unit = {}

    //用户编写的代码至少支持receive代码块（重写）
    override def receive = {
        //处理所有类型消息并不作处理
        case _ =>
    }

}

```

#### 路由与容错策略

使用actor池与路由：

```scala
parser = getContext().actorOf(Props.create(classOf[PageParsingActor], pageRetriever).
withRouter(new RoundRobinPool(Constant.round_robin_pool_size)).withDispatcher("worker-dispatcher"))
```

配置：

```
worker-dispatcher {
  type = akka.dispatch.BalancingDispatcherConfigurator
}
```

#### 消息模式匹配注意点

向发送者回复消息：

```scala
//向发送者sender发送消息，并携带自己的ref
sender ! (content, self)
```

发送者接收：

```scala
//回复者携带了自己的ref，此时消息是一个二元组
case (content: PageContent, _) 
```

[超详细Java版Actor的讲解](http://ifeve.com/akka-doc-java-untyped-actors/)