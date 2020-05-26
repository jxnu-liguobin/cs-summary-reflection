---
title: Actor的容错处理
categories:
  - AkkaActor
tags:
  - akka 2.5.25文档
description: 主要介绍Akka-actor模块的基本的容错处理使用
---

# 2019-10-19-AkkaActor-Actor的容错处理

* 目录

  {:toc}

## 依赖

容错是针对actor的，所以需要actor的依赖：

```scala
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.25"
```

正如ActorSystems中所解释的那样，每个actor都是其子actor的主管，因此每个actor都定义了故障处理监督策略。这一策略不能在事后改变，因为它是actor系统结构的一个组成部分。 具体的监督策略可以参考（Akka分类下的"Akka的监督与监控"）。

## 实践中的故障处理

首先，让我们看一个示例，它说明了处理数据存储错误的一种方法，这是现实应用程序中一个典型的失败例子。 当然，这取决于实际的应用程序，当数据存储不可用时，可以做什么，但是在这个示例中，我们使用了最好的方法：重新连接。

阅读以下源代码。内嵌的注释解释了故障处理的不同部分以及添加它们的原因。还强烈建议运行此示例，因为很容易跟踪日志输出以了解运行时发生的情况。

## 创建监督策略

下面几节将更深入地解释故障处理机制和备选方案。

为了演示起见，让我们考虑以下策略：

```scala
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

我们选择了一些著名的异常类型，以演示在监督中描述的故障处理指令的应用。 首先，这是一对一的策略，这意味着每个孩子被分开对待（一个所有对一的策略工作非常相似，唯一的区别是，任何决定都适用于主管的所有孩子，而不仅仅是失败的孩子）。 在上面的示例中，10分钟和1分钟分别传递给maxNrOfRetry和withinTimeRange参数，这意味着策略以每分钟10次重新启动子actor（孩子）。 如果在withinTimeRange持续时间内重新启动计数超过maxNrOfRetry，则停止子actor。

此外，这些参数也有特殊的值。如果您指定：

* -1 to maxNrOfRetries, and Duration.Inf to withinTimeRange
  * 孩子总是不受任何限制地重新启动。
* -1 to maxNrOfRetries, and a non-infinite Duration to withinTimeRange
  * maxNrOfRetry被视为1
* a non-negative number to maxNrOfRetries and Duration.Inf to withinTimeRange
  * 在时间范围内被视为无限持续时间\(即\)无论花费多长时间，一旦重新启动计数超过maxNrOfRetry，子actor就会停止。

构成主体的match语句是Decider的类型，它是一个PartialFunction\[Throwable，Directive\]类型。这是将子故障类型映射到相应指令的部分。

```text
如果策略是在监督actor(而不是同伴对象内)内声明的，则它的分配器(Decider)可以以线程安全的方式访问该actor的所有内部状态，包括获得对当前失败子消息的引用(可作为失败消息的发送方使用)。
```

## 默认的监督策略

如果定义的策略不涵盖抛出的异常，则使用Escalate。

当没有为actor定义监督策略时，默认情况下将处理下列异常：

* ActorInitializationException 停止失败的子actor
* ActorKilledException 停止失败的子actor
* DeathPactException 停止失败的子actor
* Exception will 重启（不保留状态）失败的子actor
* Other types of Throwable 上升到父actor

如果异常一直升级到根守护程序，它将以与上面定义的默认策略相同的方式处理它。

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

## 停止监督策略

更接近Erlang的方法是，在孩子失败时阻止他们，然后在死亡观察表明孩子失去时，在主管那里采取纠正措施。 此策略还预先打包为SupervisorStrategy.StoppingStrategy，附带一个StoppingSupervisorStrategy配置器，以便在您希望“/user”监护人应用它时使用。 “/user”监护人是用户创建的actor的顶级监护人。

## 故障日志记录

默认情况下，除非升级，否则监督策略会记录故障。升级的故障应该在层次结构中较高的级别处理，并可能记录在案。

您可以通过在实例化时将loggingEnable设置为false来禁止SupervisorStrategy的默认日志记录。 定制化的日志记录可以在分配器（Decider）内完成。请注意，当监督策略在监督actor之内被声明时，对当前失败子节点的引用可作为发送方使用。

您还可以通过重写logFailure方法，在您自己的SupervisorStrategy实现中自定义日志记录。

## 高层actor的监督

Toplevel actor是指使用system.actorOf\(\)创建的参与者，它们是用户守护者的子类。在这种情况下监护人应用配置的策略是不应用特殊规则。

## 测试应用

下一节展示了在需要测试设置的实际情况下，不同指令的效果。首先，我们需要一个合适的主管：

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

这个主管将被用来创造一个孩子（子actor），我们可以用这个孩子做实验：

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

通过使用[TestActorSystems](https://doc.akka.io/docs/akka/current/testing.html)中描述的实用程序，测试更容易，其中TestProbe提供了一个对接收和检查答复有用的actor 引用。

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

创造actor：

```scala
val supervisor = system.actorOf(Props[Supervisor], "supervisor")

supervisor ! Props[Child]
val child = expectMsgType[ActorRef] // retrieve answer from TestKit’s testActor
```

第一个测试将演示Resume指令，因此我们尝试在actor中设置一些非初始状态，然后失败：

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

最后，在发生致命IllegalArgumentException事件时，主管将终止该孩子：

```scala
watch(child) // have testActor watch “child”
child ! new IllegalArgumentException // break it
expectMsgPF() { case Terminated(`child`) => () }
```

到目前为止，主管完全没有受到孩子的失败的影响，因为指令集确实处理了它。在出现异常的情况下，这种情况不再是真的，并且主管会使故障升级。

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

主管本身由ActorSystem提供的顶级actor进行监督，该系统具有在所有异常情况下重新启动的默认策略（ActorInitializationException和ActorledKilException例外）。 因为在重新启动的情况下，默认的指令是杀死所有的孩子，所以我们期望我们可怜的孩子活不过这次失败。

如果这是不想要的（这取决于用例），我们需要使用一个不同的监督者来覆盖这个行为。即当不需要杀死所有子actor时，可以参考以下用例。

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

* 使用搜狗翻译、百度翻译、谷歌翻译，仅供参考
* 来自官方文档、参考《响应式架构 消息模式Actor实现与Scala、Akka应用集成》
* 后续随着理解深入会继续修改错误和描述，以便更好理解，本博客开源，欢迎指出错误

