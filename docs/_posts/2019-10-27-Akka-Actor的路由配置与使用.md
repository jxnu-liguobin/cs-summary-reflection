---
title: Actor的路由配置与使用
categories:
  - Akka
tags: [Akka-Actor中文文档]
description: actor路由介绍
---

* 目录
{:toc}

### 依赖

```scala
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.26"
```

消息可以通过路由器发送，以便有效地将它们路由到目的地Actor，即它的路由者。路由器可以在Actor的内部或外部使用，您可以自己管理路由器，也可以使用具有配置功能的独立路由器Actor。路由器很像Actor，但是路由器与普通Actor的实现并不同。

根据应用程序的需要，可以使用不同的路由策略。Akka提供了几种有用的路由策略。但是，正如您将在本章中看到的，也可以创建您自己的路由器。

### 一个简单的路由器

下面的示例演示如何使用Router并从Actor中管理路由。

```scala
import akka.routing.{ ActorRefRoutee, RoundRobinRoutingLogic, Router }

class Master extends Actor {
  var router = { 
      //暂且称router为路由器，routees为路由者或路由。下同
      //简单理解：路由者是路由器的实体，是Actor对象
      //一个路由器可以有多个路由者，并且路由者可以由外部提供，但宏观上属于同一个路由器。所以也可说路由器是Actor
      //可能有误
    val routees = Vector.fill(5) {
      val r = context.actorOf(Props[Worker])
      context.watch(r)
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  def receive = {
    case w: Work =>
      router.route(w, sender())
    case Terminated(a) =>
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[Worker])
      context.watch(r)
      router = router.addRoutee(r)
  }
}
```

我们创建一个Router ，并指定它在将消息路由到路由者时应该使用RoundRobinRoutingLogic。

Akka提供的路由逻辑如下：

* akka.routing.RoundRobinRoutingLogic
* akka.routing.RandomRoutingLogic
* akka.routing.SmallestMailboxRoutingLogic
* akka.routing.BroadcastRoutingLogic
* akka.routing.ScatterGatherFirstCompletedRoutingLogic
* akka.routing.TailChoppingRoutingLogic
* akka.routing.ConsistentHashingRoutingLogic

我们将routees创建为封装在ActorRefRoutee中的普通子Actor。我们看着那些被终止的Actor，如果他们被终止的话，他们就有能力取代他们。

通过路由器发送消息是使用route方法完成的，就像上面示例中对Work消息所做的那样。

Router是不可变的并且RoutingLogic是线程安全的；这意味着它们也可以在Actor之外使用。


    一般来说，发送到路由器的任何消息都将被发送到其路由者，但有一个例外。特殊广播消息将发送到路由器的所有路由者。但是，当您对路由者使用BalancingPool时，不要使用广播消息，如特别处理的消息中所述的那样。

### 一个路由器Actor

路由器也可以创建为一个自包含的Actor，它管理路由器本身，并从配置中加载路由逻辑和其他设置。

这种类型的路由器Actor有两种不同的风格：

* Pool - 路由器创建路由者作为子Actor，并在它们终止时将其从路由器中移除。
* Group - 路由者在外部被创建并且路由器使用Actor的选择器将消息发送到指定路径，而不监视Actor的终止。

可以在配置中或以编程方式定义路由器Actor的设置。为了使Actor利用外部可配置的路由器，必须使用FromConfig支持包装器来表示Actor接受来自配置的路由设置。这与远程部署形成了对比，因为远程部署不需要这样的标记支持。如果Actor的props没有被包装在FromConfig中，它将忽略部署配置的路由器部分。

您通过路由器Actor以与普通Actor相同的方式向路由器发送消息，即通过其ActorRef发送消息。路由器Actor将消息转发到其路由者上，而不更改原始发送方。当路由者回复路由消息时，答复将发送给原始发送方，而不是路由器Actor。

通常，发送到路由器的任何消息都会被发送到它的路由者，但也有一些例外。这些记录在下面的特别处理消息（Specially Handled Messages）部分中。

#### 池 Pool

下面的代码和配置片段演示如何创建一个循环路由器将消息转发给五个Worker路由者。路由者将被创建为路由器的子节点。

```
akka.actor.deployment {
  /parent/router1 {
    router = round-robin-pool
    nr-of-instances = 5
  }
}
```

```scala
val router1: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router1")
```

下面是相同的示例，但使用的是以编程方式提供的路由器配置，而不是从配置中提供的。

```scala
val router2: ActorRef =
  context.actorOf(RoundRobinPool(5).props(Props[Worker]), "router2")
```

#### 远程部署路由

除了能够将本地Actor创建为路由者外，还可以指示路由器将其创建的子节点部署到一组远程主机上。路由者将以round-robin fashion方式部署。为了远程部署路由者，请将路由器配置包装在RemoteRouterConfig中，附加要部署到的节点的远程地址。远程部署要求类路径中包含akka-remote模块。

```scala
import akka.actor.{ Address, AddressFromURIString }
import akka.remote.routing.RemoteRouterConfig
val addresses = Seq(
  Address("akka.tcp", "remotesys", "otherhost", 1234),
  AddressFromURIString("akka.tcp://othersys@anotherhost:1234"))
val routerRemote = system.actorOf(RemoteRouterConfig(RoundRobinPool(5), addresses).props(Props[Echo]))
```

#### 发送者

默认情况下，当路由器发送消息时，它将隐式地将自己设置为发送方。

```scala
sender() ! x // replies will go to this actor
```

但是，对于路由器来说，将路由器设置为发送者通常是有用的。例如，如果要隐藏路由器后面路由者的详细信息，则可能希望将路由器设置为发送方。下面的代码片段演示如何将父路由器设置为发送者。

```scala
sender().tell("reply", context.parent) // replies will go back to parent
sender().!("reply")(context.parent) // alternative syntax (beware of the parens!)
```

#### 监督

由池路由器创建的路由者将被创建为路由器的子节点。因此路由器也是孩子们的监护人。

路由器Actor的监督策略可以配置为池的supervisorStrategy属性。如果没有提供配置，路由器默认为“总是升级（always escalate）”策略。这意味着错误会传递给路由器的主管进行处理。路由器的主管将决定如何处理任何错误。

注意路由器的主管将把这个错误当作路由器本身的错误来处理。因此，停止或重新启动的指令将导致路由器本身停止或重新启动。路由器反过来会导致其子节点停止并重新启动。

应该指出的是，路由器的重新启动行为已经被覆盖，这样，重新启动时，同时仍然重新创建孩子，仍然将保持相同数目的Actor在池中。

这意味着，如果您没有指定路由器或其父路由器的supervisorStrategy，路由器中的故障将升级到路由器的父节点，在默认情况下，父路由器将重新启动路由者，该路由器将重新启动所有路由者（它使用升级，并且在重新启动期间不会停止路由者）。其原因是默认行为添加.WithRouter到子节点的定义不会更改应用于子节点的监督策略。这可能是一种效率低下的情况，您可以通过在定义路由器时指定策略来避免。

设置策略如下所示：

```scala
val escalator = OneForOneStrategy() {
  case e => testActor ! e; SupervisorStrategy.Escalate
}
val router =
  system.actorOf(RoundRobinPool(1, supervisorStrategy = escalator).props(routeeProps = Props[TestActor]))
```

如果池路由器的子节点终止，则池路由器不会自动生成新的子节点。如果一个池路由器的所有子节点都已终止，则除非它是一个动态路由器，否则路由器将自行终止，例如使用一个resizer。

#### 组 Group

有时，与其让路由器Actor创建其路由者，不如单独创建路由者并将其提供给路由器供其使用。您可以通过将路由者的路径传递到路由器的配置来做到这一点。消息将与ActorSelections一起发送到这些路径，通配符可以并将产生与显式使用ActorSelection相同的语义。

下面的示例展示了如何通过为路由器提供三个路由者的路径字符串来创建路由器。

```
akka.actor.deployment {
  /parent/router3 {
    router = round-robin-group
    routees.paths = ["/user/workers/w1", "/user/workers/w2", "/user/workers/w3"]
  }
}
```

```scala
val router3: ActorRef =
  context.actorOf(FromConfig.props(), "router3")
```

下面是相同的示例，但使用的是以编程方式提供的路由器配置，而不是从配置中提供的。

```scala
val paths = List("/user/workers/w1", "/user/workers/w2", "/user/workers/w3")
val router4: ActorRef =
  context.actorOf(RoundRobinGroup(paths).props(), "router4")
```

路由者的Actor是从路由器外部创建的：

```scala
system.actorOf(Props[Workers], "workers")
```

```scala
class Workers extends Actor {
  context.actorOf(Props[Worker], name = "w1")
  context.actorOf(Props[Worker], name = "w2")
  context.actorOf(Props[Worker], name = "w3")
  // ...
```

路径可以包含运行在远程主机上的Actor的协议和地址信息。远程处理要求将akka-remote模块包括在类路径中。

```scala
akka.actor.deployment {
  /parent/remoteGroup {
    router = round-robin-group
    routees.paths = [
      "akka.tcp://app@10.0.0.1:2552/user/workers/w1",
      "akka.tcp://app@10.0.0.2:2552/user/workers/w1",
      "akka.tcp://app@10.0.0.3:2552/user/workers/w1"]
  }
}
```

### 路由器使用

在本节中，我们将描述如何创建不同类型的路由器Actor。

本节中的路由器Actor是从名为parent的顶级Actor中创建的。注意，配置中的部署路径以/parent/开头，后面跟着路由器Actor的名称。

```scala
system.actorOf(Props[Parent], "parent")
```

#### RoundRobinPool 和 RoundRobinGroup

以轮询调度的方式传递给它的路由者们

在配置中定义的RoundRobinPool：

```
akka.actor.deployment {
  /parent/router1 {
    router = round-robin-pool
    nr-of-instances = 5
  }
}
```

```scala
val router1: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router1")
```

代码中定义的RoundRobinPool：

```scala
val router2: ActorRef =
  context.actorOf(RoundRobinPool(5).props(Props[Worker]), "router2")
```

在配置中定义的RoundRobinGroup：

```
akka.actor.deployment {
  /parent/router3 {
    router = round-robin-group
    routees.paths = ["/user/workers/w1", "/user/workers/w2", "/user/workers/w3"]
  }
}
```

```scala
val router3: ActorRef =
  context.actorOf(FromConfig.props(), "router3")
```

在代码中定义的RoundRobinGroup：

```scala
val paths = List("/user/workers/w1", "/user/workers/w2", "/user/workers/w3")
val router4: ActorRef =
  context.actorOf(RoundRobinGroup(paths).props(), "router4")
```

#### RandomPool 和 RandomGroup

此路由器类型为每条消息随机选择一个路由器。

在配置中定义的RandomPool：

```
akka.actor.deployment {
  /parent/router5 {
    router = random-pool
    nr-of-instances = 5
  }
}
```

```scala
val router5: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router5")
```

代码中定义的RandomPool：

```scala
val router6: ActorRef =
  context.actorOf(RandomPool(5).props(Props[Worker]), "router6")
```

在配置中定义的RandomGroup：

```
akka.actor.deployment {
  /parent/router7 {
    router = random-group
    routees.paths = ["/user/workers/w1", "/user/workers/w2", "/user/workers/w3"]
  }
}
```

```scala
val router7: ActorRef =
  context.actorOf(FromConfig.props(), "router7")
```

代码中定义的RandomGroup：

```scala
val paths = List("/user/workers/w1", "/user/workers/w2", "/user/workers/w3")
val router8: ActorRef =
  context.actorOf(RandomGroup(paths).props(), "router8")
```

#### BalancingPool

一个路由器，它将尝试将工作从繁忙的路由者重新分配到空闲的路由者。所有的路由器共用同一个邮箱。

    balancingpool有一个特性，即它的路由者没有真正不同的身份：他们有不同的名字，但在大多数情况下，与他们交谈不会以合适的Actor结束。
    因此，不能将其用于要求状态保持在路由者内的工作流，在这种情况下，必须将整个状态包含在消息中。

    使用SmallestMailboxPool，您可以拥有一个垂直扩展的服务，它可以在回复原始客户端之前以有状态的方式与后端的其他服务交互。
    另一个优点是它不像BalancingPool那样对消息队列实现施加限制。

    当您对路由器使用BalancingPool时，不要使用广播消息，如特殊处理的消息中所描述的那样。


配置中定义的BalancingPool：

```
akka.actor.deployment {
  /parent/router9 {
    router = balancing-pool
    nr-of-instances = 5
  }
}
```

```scala
val router9: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router9")
```

代码中定义的BalancingPool：

```scala
val router10: ActorRef =
  context.actorOf(BalancingPool(5).props(Props[Worker]), "router10")
```

由池使用的balancing调度器的附加配置可以在路由器部署配置的pool-dispatcher部分中配置。

```
akka.actor.deployment {
  /parent/router9b {
    router = balancing-pool
    nr-of-instances = 5
    pool-dispatcher {
      attempt-teamwork = off
    }
  }
}
```

BalancingPool会自动为其路由者使用一个特殊的BalancingDispatcher--而不考虑在路由者Props对象上设置的任何调度器。这是必需的，以便通过所有路由对象共享同一邮箱来实现平衡语义。

虽然不可能更改路由者使用的调度器，但可以微调已使用的执行器。默认情况下，使用fork-join-dispatcher调度器，并且可以按照Dispatchers中的说明进行配置。在需要路由器执行阻塞操作的情况下，使用thread-pool-executor显式地提示分配的线程数可能会有用：

```
akka.actor.deployment {
  /parent/router10b {
    router = balancing-pool
    nr-of-instances = 5
    pool-dispatcher {
      executor = "thread-pool-executor"

      # allocate exactly 5 threads for this pool
      thread-pool-executor {
        core-pool-size-min = 5
        core-pool-size-max = 5
      }
    }
  }
}
```

在默认无界邮箱不适合的情况下，还可以更改balancing调度器使用的邮箱。可能出现这种情况的一个例子，是是否需要管理每条消息的优先级。然后，您可以实现一个优先级邮箱并配置调度器：

```
akka.actor.deployment {
  /parent/router10c {
    router = balancing-pool
    nr-of-instances = 5
    pool-dispatcher {
      mailbox = myapp.myprioritymailbox
    }
  }
}
```

请记住，BalancingDispatcher需要一个对多个并发消费者来说必须是线程安全的消息队列。因此，支持此类Dispatcher的自定义邮箱的消息队列必须实现akka.dispatch.MultipleConsumerSemantics。请参阅有关如何在邮箱中实现自定义邮箱的详细信息。

不存在BalancingPool的组变体。

#### SmallestMailboxPool

一种路由器，试图用邮箱中最少的消息发送给未挂起的子路由者。所选内容按以下顺序进行：

* 选取邮箱为空的任何空闲路由者（不处理邮件）
* 选择邮箱为空的路由者
* 选取邮箱中待处理邮件最少的路由者
* 选择任何远程路由者，远程Actor被认为是最低优先级的，因为他们的邮箱大小是未知的

在配置中定义的SmallestMailboxPool：

```
akka.actor.deployment {
  /parent/router11 {
    router = smallest-mailbox-pool
    nr-of-instances = 5
  }
}
```

```scala
val router11: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router11")
```

代码中定义的SmallestMailboxPool：

```scala
val router12: ActorRef =
  context.actorOf(SmallestMailboxPool(5).props(Props[Worker]), "router12")
```

没有SmallestMailboxPool的组变体，因为邮箱的大小和Actor的内部调度状态实际上无法从路由者的路径中获得。

#### BroadcastPool 和 BroadcastGroup

未完。



参考：
* 使用搜狗翻译、百度翻译、谷歌翻译，仅供参考
* 来自官方文档、参考《响应式架构 消息模式Actor实现与Scala、Akka应用集成》
* 后续随着理解深入会继续修改错误和描述，以便更好理解，本博客开源，欢迎指出错误。