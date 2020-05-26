---
title: Actor的路由配置与使用
categories:
  - AkkaActor
tags:
  - akka 2.5.25文档
description: actor路由介绍
---

# 2019-10-27-AkkaActor-Actor的路由配置与使用

* 目录

  {:toc}

## 依赖

```scala
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.26"
```

消息可以通过路由器发送，以便有效地将它们路由到目的地Actor，即它的路由者。路由器可以在Actor的内部或外部使用，您可以自己管理路由器，也可以使用具有配置功能的独立路由器Actor。路由器很像Actor，但是路由器与普通Actor的实现并不同。

根据应用程序的需要，可以使用不同的路由策略。Akka提供了几种有用的路由策略。但是，正如您将在本章中看到的，也可以创建您自己的路由器。

## 一个简单的路由器

下面的示例演示如何使用Router并从Actor中管理路由。

```scala
import akka.routing.{ ActorRefRoutee, RoundRobinRoutingLogic, Router }

class Master extends Actor {
  var router = { 
      //暂且称router为路由器，routees为路由者或路由。下同
      //简单理解：路由者是路由器的实体，是Actor对象
      //一个路由器可以有多个路由者，并且路由者可以由外部提供，但宏观上属于同一个路由器。所以也可说路由器是Actor
      //可能有误，目前先把它比作线程池和线程（路由还有路由组）
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

> 一般来说，发送到路由器的任何消息都将被发送到其路由者，但有一个例外。特殊广播消息将发送到路由器的所有路由者。但是，当您对路由者使用BalancingPool时，不要使用广播消息，如特别处理的消息中所述的那样。

## 一个路由器Actor

路由器也可以创建为一个自包含的Actor，它管理路由器本身，并从配置中加载路由逻辑和其他设置。

这种类型的路由器Actor有两种不同的风格：

* Pool - 路由器创建路由者作为子Actor，并在它们终止时将其从路由器中移除。
* Group - 路由者在外部被创建并且路由器使用Actor的选择器将消息发送到指定路径，而不监视Actor的终止。

可以在配置中或以编程方式定义路由器Actor的设置。为了使Actor利用外部可配置的路由器，必须使用FromConfig支持包装器来表示Actor接受来自配置的路由设置。这与远程部署形成了对比，因为远程部署不需要这样的标记支持。如果Actor的props没有被包装在FromConfig中，它将忽略部署配置的路由器部分。

您通过路由器Actor以与普通Actor相同的方式向路由器发送消息，即通过其ActorRef发送消息。路由器Actor将消息转发到其路由者上，而不更改原始发送方。当路由者回复路由消息时，答复将发送给原始发送方，而不是路由器Actor。

通常，发送到路由器的任何消息都会被发送到它的路由者，但也有一些例外。这些记录在下面的特别处理消息（Specially Handled Messages）部分中。

### 池 Pool

下面的代码和配置片段演示如何创建一个循环路由器将消息转发给五个Worker路由者。路由者将被创建为路由器的子节点。

```text
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

### 远程部署路由

除了能够将本地Actor创建为路由者外，还可以指示路由器将其创建的子节点部署到一组远程主机上。路由者将以round-robin fashion方式部署。为了远程部署路由者，请将路由器配置包装在RemoteRouterConfig中，附加要部署到的节点的远程地址。远程部署要求类路径中包含akka-remote模块。

```scala
import akka.actor.{ Address, AddressFromURIString }
import akka.remote.routing.RemoteRouterConfig
val addresses = Seq(
  Address("akka.tcp", "remotesys", "otherhost", 1234),
  AddressFromURIString("akka.tcp://othersys@anotherhost:1234"))
val routerRemote = system.actorOf(RemoteRouterConfig(RoundRobinPool(5), addresses).props(Props[Echo]))
```

### 发送者

默认情况下，当路由器发送消息时，它将隐式地将自己设置为发送方。

```scala
sender() ! x // replies will go to this actor
```

但是，对于路由器来说，将路由器设置为发送者通常是有用的。例如，如果要隐藏路由器后面路由者的详细信息，则可能希望将路由器设置为发送方。下面的代码片段演示如何将父路由器设置为发送者。

```scala
sender().tell("reply", context.parent) // replies will go back to parent
sender().!("reply")(context.parent) // alternative syntax (beware of the parens!)
```

### 监督

由池路由器创建的路由者将被创建为路由器的子节点。因此路由器也是孩子们的监护人。

路由器Actor的监督策略可以配置为池的supervisorStrategy属性。如果没有提供配置，路由器默认为“总是升级（always escalate）”策略。这意味着错误会传递给路由器的主管进行处理。路由器的主管将决定如何处理任何错误。

> 路由器的主管将把这个错误当作路由器本身的错误来处理。因此，停止或重新启动的指令将导致路由器本身停止或重新启动。路由器反过来会导致其子节点停止并重新启动。

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

### 组 Group

有时，与其让路由器Actor创建其路由者，不如单独创建路由者并将其提供给路由器供其使用。您可以通过将路由者的路径传递到路由器的配置来做到这一点。消息将与ActorSelections一起发送到这些路径，通配符可以并将产生与显式使用ActorSelection相同的语义。

下面的示例展示了如何通过为路由器提供三个路由者的路径字符串来创建路由器。

```text
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

## 路由器使用

在本节中，我们将描述如何创建不同类型的路由器Actor。

本节中的路由器Actor是从名为parent的顶级Actor中创建的。注意，配置中的部署路径以/parent/开头，后面跟着路由器Actor的名称。下面都会演示各类路由器如何在代码中以编码的方式配置或如何在配置文件中配置（包括组和池）。

```scala
system.actorOf(Props[Parent], "parent")
```

### RoundRobinPool 和 RoundRobinGroup

以轮询调度的方式传递给它的路由者们

在配置中定义的RoundRobinPool：

```text
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

```text
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

### RandomPool 和 RandomGroup

此路由器类型为每条消息随机选择一个路由器。

在配置中定义的RandomPool：

```text
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

```text
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

### BalancingPool

一个路由器，它将尝试将工作从繁忙的路由者重新分配到空闲的路由者。所有的路由器共用同一个邮箱。

```text
balancingpool有一个特性，即它的路由者没有真正不同的身份：他们有不同的名字，但在大多数情况下，与他们交谈不会以合适的Actor结束。
因此，不能将其用于要求状态保持在路由者内的工作流，在这种情况下，必须将整个状态包含在消息中。

使用SmallestMailboxPool，您可以拥有一个垂直扩展的服务，它可以在回复原始客户端之前以有状态的方式与后端的其他服务交互。
另一个优点是它不像BalancingPool那样对消息队列实现施加限制。

当您对路由器使用BalancingPool时，不要使用广播消息，如特殊处理的消息中所描述的那样。
```

配置中定义的BalancingPool：

```text
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

```text
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

```text
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

```text
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

### SmallestMailboxPool

一种路由器，试图用邮箱中最少的消息发送给未挂起的子路由者。所选内容按以下顺序进行：

* 选取邮箱为空的任何空闲路由者（不处理邮件）
* 选择邮箱为空的路由者
* 选取邮箱中待处理邮件最少的路由者
* 选择任何远程路由者，远程Actor被认为是最低优先级的，因为他们的邮箱大小是未知的

在配置中定义的SmallestMailboxPool：

```text
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

### BroadcastPool 和 BroadcastGroup

广播路由器将接收到的消息转发给所有路由者。

在配置中定义的BroadCastPool：

```text
akka.actor.deployment {
  /parent/router13 {
    router = broadcast-pool
    nr-of-instances = 5
  }
}
```

```scala
val router13: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router13")
```

代码中定义的BroadCastPool：

```scala
val router14: ActorRef =
  context.actorOf(BroadcastPool(5).props(Props[Worker]), "router14")
```

在配置中定义的BroadCastGroup：

```text
akka.actor.deployment {
  /parent/router15 {
    router = broadcast-group
    routees.paths = ["/user/workers/w1", "/user/workers/w2", "/user/workers/w3"]
  }
}
```

```scala
val router15: ActorRef =
  context.actorOf(FromConfig.props(), "router15")
```

代码中定义的BroadCastGroup：

```scala
val paths = List("/user/workers/w1", "/user/workers/w2", "/user/workers/w3")
val router16: ActorRef =
  context.actorOf(BroadcastGroup(paths).props(), "router16")
```

```text
广播路由器总是将每一条消息广播给他们的路由者。如果不想广播所有消息，则可以使用非广播路由器并根据需要使用广播消息。
```

### ScatterGatherFirstCompletedPool 和 ScatterGatherFirstCompletedGroup

ScatterGatherFirstCompletedRouter将把信息发送给所有的路由者。然后等待它得到的第一个回复。此结果将被发送回原发件人。其他答复被丢弃。

它期望在配置的持续时间内至少有一个回复，否则它将在akka.actor.Status.Failure中使用akka.pattern.AskTimeoutException进行回复。

在配置中定义的ScatterGatherFirstCompletedPool：

```text
akka.actor.deployment {
  /parent/router17 {
    router = scatter-gather-pool
    nr-of-instances = 5
    within = 10 seconds
  }
}
```

```scala
val router17: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router17")
```

代码中定义的ScatterGatherFirstCompletedPool：

```scala
val router18: ActorRef =
  context.actorOf(ScatterGatherFirstCompletedPool(5, within = 10.seconds).props(Props[Worker]), "router18")
```

在配置中定义的ScatterGatherFirstCompletedGroup：

```text
akka.actor.deployment {
  /parent/router19 {
    router = scatter-gather-group
    routees.paths = ["/user/workers/w1", "/user/workers/w2", "/user/workers/w3"]
    within = 10 seconds
  }
}
```

```scala
val router19: ActorRef =
  context.actorOf(FromConfig.props(), "router19")
```

代码中定义的ScatterGatherFirstCompletedGroup：

```scala
val paths = List("/user/workers/w1", "/user/workers/w2", "/user/workers/w3")
val router20: ActorRef =
  context.actorOf(ScatterGatherFirstCompletedGroup(paths, within = 10.seconds).props(), "router20")
```

### TailChoppingPool 和 TailChoppingGroup

TailChoppingRouter首先将消息发送给一个随机选择的路由者，然后在一个小延迟后发送到第二个路由者（从其余的路由随机选择）等等。它等待第一次回复，然后返回给原始发件人。其他答复被丢弃。

该路由器的目标是通过对多个路由器执行冗余查询来减少延迟，前提是其它Actor之一的响应速度可能仍然比最初的更快。

PeterBailis的一篇博客文章很好地描述了这种优化：[做多余的工作来加速分布式查询](http://www.bailis.org/blog/doing-redundant-work-to-speed-up-distributed-queries/)。

在配置中定义的TailChoppingPool：

```text
akka.actor.deployment {
  /parent/router21 {
    router = tail-chopping-pool
    nr-of-instances = 5
    within = 10 seconds
    tail-chopping-router.interval = 20 milliseconds
  }
}
```

```scala
val router21: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router21")
```

代码中定义的TailChoppingPool：

```scala
val router22: ActorRef =
  context.actorOf(TailChoppingPool(5, within = 10.seconds, interval = 20.millis).props(Props[Worker]), "router22")
```

在配置中定义的TailChoppingGroup：

```scala
akka.actor.deployment {
  /parent/router23 {
    router = tail-chopping-group
    routees.paths = ["/user/workers/w1", "/user/workers/w2", "/user/workers/w3"]
    within = 10 seconds
    tail-chopping-router.interval = 20 milliseconds
  }
}
```

```scala
val router23: ActorRef =
  context.actorOf(FromConfig.props(), "router23")
```

代码中定义的TailChoppingGroup：

```scala
val paths = List("/user/workers/w1", "/user/workers/w2", "/user/workers/w3")
val router24: ActorRef =
  context.actorOf(TailChoppingGroup(paths, within = 10.seconds, interval = 20.millis).props(), "router24")
```

### ConsistentHashingPool 和 ConsistentHashingGroup

ConsistentHashingPool使用一致性Hash来根据发送的消息选择路由。[本文](http://www.tom-e-white.com/2007/11/consistent-hashing.html)很好地了解了如何实现一致散列。

有三种方法可以为一致性Hash key定义要使用的数据。

* 您可以定义路由器的hashMapping，以将传入的消息映射到其一致性Hash key。这使得决定对发送方透明。
* 这些消息可以实现akka.routing.ConsistentHashingRouter.ConsistentHashable。key是消息的一部分，与消息定义一起定义它很方便。
* 可以将消息包装在akka.routing.ConsistentHashingRouter.ConsistentHashableEnvelope中，以定义用一致性Hash key的数据。发送者知道要使用的密钥。

这些定义一致性Hash key的方法可以同时用于一个路由器。首先尝试hashMapping。

Code example:

```scala
import akka.actor.Actor
import akka.routing.ConsistentHashingRouter.ConsistentHashable

class Cache extends Actor {
  var cache = Map.empty[String, String]

  def receive = {
    case Entry(key, value) => cache += (key -> value)
    case Get(key)          => sender() ! cache.get(key)
    case Evict(key)        => cache -= key
  }
}

final case class Evict(key: String)

final case class Get(key: String) extends ConsistentHashable {
  override def consistentHashKey: Any = key
}

final case class Entry(key: String, value: String)
```

```scala
import akka.actor.Props
import akka.routing.ConsistentHashingPool
import akka.routing.ConsistentHashingRouter.ConsistentHashMapping
import akka.routing.ConsistentHashingRouter.ConsistentHashableEnvelope

def hashMapping: ConsistentHashMapping = {
  case Evict(key) => key
}

val cache: ActorRef =
  context.actorOf(ConsistentHashingPool(10, hashMapping = hashMapping).props(Props[Cache]), name = "cache")

cache ! ConsistentHashableEnvelope(message = Entry("hello", "HELLO"), hashKey = "hello")
cache ! ConsistentHashableEnvelope(message = Entry("hi", "HI"), hashKey = "hi")

cache ! Get("hello")
expectMsg(Some("HELLO"))

cache ! Get("hi")
expectMsg(Some("HI"))

cache ! Evict("hi")
cache ! Get("hi")
expectMsg(None)
```

在上面的示例中，您可以看到GET消息实现了ConsistentHasable本身，而Entry消息被包装在ConsistentHashableEnKabe中。Evict消息由hashMapping部分函数处理。

ConsistentHashingPool在配置中定义：

```text
akka.actor.deployment {
  /parent/router25 {
    router = consistent-hashing-pool
    nr-of-instances = 5
    virtual-nodes-factor = 10
  }
}
```

```scala
val router25: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router25")
```

在代码中定义的ConsistentHashingPool：

```scala
val router26: ActorRef =
  context.actorOf(ConsistentHashingPool(5).props(Props[Worker]), "router26")
```

ConsistentHashingGroup在配置中定义：

```text
akka.actor.deployment {
  /parent/router27 {
    router = consistent-hashing-group
    routees.paths = ["/user/workers/w1", "/user/workers/w2", "/user/workers/w3"]
    virtual-nodes-factor = 10
  }
}
```

```scala
val router27: ActorRef =
  context.actorOf(FromConfig.props(), "router27")
```

在代码中定义的ConsistentHashingGroup：

```scala
val paths = List("/user/workers/w1", "/user/workers/w2", "/user/workers/w3")
val router28: ActorRef =
  context.actorOf(ConsistentHashingGroup(paths).props(), "router28")
```

virtual-nodes-factor是在一致性hash节点环中使用的每个路由者的虚拟节点数，以使分布更加均匀。

## 特别处理的消息

发送给路由器Actor的大部分消息将根据路由器的路由逻辑转发。但是，有几种类型的消息具有特殊的行为。

> 请注意，除了Broadcast之外，这些特殊消息只由独立的路由器Actor来处理，而不是由akka.routing.Router组件来处理，这在上面章节“一个简单的路由器”中描述了。

### 广播消息

广播消息（Broadcast）可以用来向路由器的所有路由者们发送消息。当路由器接收到广播消息时，它将把消息的有效载荷广播给所有被路由者，而不管该路由器通常如何路由其消息。

下面的示例显示了如何使用广播消息向路由器的每个路由者发送非常重要的消息。

```scala
import akka.routing.Broadcast
router ! Broadcast("Watch out for Davy Jones' locker")
```

在本例中，路由器接收广播消息，提取其有效载荷\(“Watch out for Davy Jones' locker”\)，然后将有效载荷发送给路由器的所有路由者。由每个路由者Actor来处理接收到的有效载荷消息。

> 当您对路由器使用BalancingPool时，不要使用广播消息。BalancingPool上的路由者们共享相同的邮箱实例，因此一些路由者可能会多次获得广播消息，而其他的路由者则得不到广播消息。

### 毒丸消息

毒丸（PoisonPill）消息对包括路由器在内的所有Actor都有特殊处理。当任何Actor收到PoisonPill消息时，该Actor将被停止。有关详细信息，[请参阅PoisonPill文档](https://doc.akka.io/docs/akka/current/actors.html#poison-pill)。

```scala
import akka.actor.PoisonPill
router ! PoisonPill
```

对于通常将消息传递给路由者的路由器，重要的是要意识到毒丸消息仅由路由器处理。发送到路由器的毒丸消息不会发送到路由者。

然而，发送给路由器的PoisonPill消息可能仍然会影响其路由者，因为它将停止路由器，而当路由器停止时，它也会停止其子路由器。阻止子节点是正常的Actor行为。路由器将停止它创建的子路由者。每个子程序将处理其当前消息，然后停止。这可能导致一些消息未被处理。有关停止Actor的更多信息，[请参见文档](https://doc.akka.io/docs/akka/current/actors.html#stopping-actors)。

如果希望停止路由器及其路由者，但希望路由者们首先处理当前邮箱中的所有邮件，则不应向路由器发送PoisonPill消息。相反，您应该将PoisonPill消息包装在广播消息中，以便每个路由者都能接收PoisonPill消息。请注意，这将停止所有路由器，即使路由者不是路由器的子节点，也就是说即使是以编程方式提供给路由器的路由者们。

```scala
import akka.actor.PoisonPill
import akka.routing.Broadcast
router ! Broadcast(PoisonPill)
```

使用上面所示的代码，每个路由者将收到一条PoisonPill消息。每个路由者将继续正常地处理其消息，最终处理PoisonPill。这会让路由者停下来。在所有路由者停止后，路由器本身就会自动停止，除非它是一个动态路由器，例如使用一个resizer。

Brendan W McAdams出色的博客文章[Distributing Akka Workloads - And Shutting Down Afterwards](http://bytes.codes/2013/01/17/Distributing_Akka_Workloads_And_Shutting_Down_After/)，更详细地讨论了如何使用PoisonPill消息可以用来关闭路由器和路由者。

### 终止消息

终止消息是另一种具有特殊处理功能的消息类型。有关Actor如何处理终止消息的一般信息，请参见[终止演员](https://doc.akka.io/docs/akka/current/actors.html#killing-actors)。

当一个Kill消息被发送到一个路由器时，路由器会在内部处理该消息，而不会将它发送给它的路由者。路由器将抛出ActorKilledException并失败。然后，它将被恢复、重新启动或终止，这取决于它的监督方式。参考本博客的“Actor的监督与监控”一文

路由器的子路由者也将被挂起，并将受到应用于路由器的监督指令的影响。非路由器的子路由者，即那些在路由器外部创建的，不会受到影响。

```scala
import akka.actor.Kill
router ! Kill
```

与PoisonPill消息一样，在终止路由器（它间接杀死路由器的子女（碰巧是路由者））和直接终止路由者（其中一些人可能不是子节点）之间存在着区别。为了直接终止路由者，应该向路由器发送一条包含在广播消息中的终止消息。

### 管理消息

* 向路由器Actor发送akka.routing.GetRoutees将让它在akka.routing.Routees消息中传回它目前使用的路由者。
* 向路由器Actor发送akka.routing.AddRoutee将该路由者添加到它的路由者集合中。
* 向路由器Actor发送akka.routing.RemoveRoutee将该路由者从其路由者集合中删除。
* 发送akka.routing.AdjustPoolSize对池路由器Actor进行调整，将该数目的路由者从它的路由者集合中添加或移除。

这些管理消息可能在其他消息之后处理，因此，如果您在发送AddRoutee之后立即发送一条普通消息，则不能保证路由者在路由普通消息时已被更改。如果您需要知道更改是什么时候应用的，您可以发送AddRoutee，后面跟着GetRoutees，并且当您收到Routees的回复时，您知道前面的更改已经应用了。

## 动态调整大小的池

大多数池可以与固定数目的路由者一起使用，也可以通过调整大小的策略来动态地调整路由者的数量。

有两种resizers（支持调整池大小）类型：默认Resizer和OptimalSizeExploringResizer。

### Default Resizer

默认的resizer根据压力调整池大小，以池中繁忙的路由者的百分比来衡量。当压力高于某一阈值时，它会增大池的大小；如果压力低于某一阈值，则会后退。这两个阈值都是可配置的，类似HashMap平衡因子。

配置中定义了默认resizer的池大小：

```text
akka.actor.deployment {
  /parent/router29 {
    router = round-robin-pool
    resizer {
      lower-bound = 2
      upper-bound = 15
      messages-per-resize = 100
    }
  }
}
```

```scala
val router29: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router29")
```

还有几个配置选项可用，并在[配置](https://doc.akka.io/docs/akka/current/general/configuration.html)的akka.actor.deployment.default.resizer部分中进行了描述。

在代码中定义的resizer的池大小：

```scala
val resizer = DefaultResizer(lowerBound = 2, upperBound = 15)
val router30: ActorRef =
  context.actorOf(RoundRobinPool(5, Some(resizer)).props(Props[Worker]), "router30")
```

还值得指出的是，如果在配置文件中定义路由器，则将使用此值而不是以编程方式发送的任何参数。即外部配置能覆盖编码的配置。

### Optimal Size Exploring Resizer

OptimalSizeExploringResizer将池调整到提供最多消息吞吐量的最佳大小。我们暂称这个为调整器。

当您期望性能函数的池大小是凸函数时，此调整器最有效。例如，当您有CPU绑定的任务时，最佳大小将与CPU内核数绑定。当您的任务受IO限制时，最佳大小将限制为与该IO服务的并发连接的最佳数目--例如4节点弹性搜索集群可以最佳速度处理4-8个并发请求。

它通过跟踪每种池大小的消息吞吐量并定期执行以下三个调整大小操作（一次调整一次）来实现此目的：

* 如果在一段时间内没有看到所有路由者都被充分利用，请减小尺寸。
* 探索附近的随机池大小，以尝试收集吞吐量指标。
* 以更好的吞吐量指标（比其他任何附近的指标都更好）优化到附近的泳池大小

当池被充分利用时（即所有路由者都处于繁忙状态），它会在探索和优化之间随机选择。如果一段时间未完全使用该池，它会将该池的大小减小到最后看到的最大利用率乘以可配置的比率。

通过不断地探索和优化，调整器最终将达到最佳尺寸并保持在附近。当最佳尺寸更改时，它将开始朝着新尺寸前进。

它保留了性能日志，使其具有状态，并且具有比默认Resizer大的内存占用量。内存使用量为O（n），其中n是您允许的大小数，即upperBound-lowerBound

配置中定义的具有OptimalSizeExplorerResizer的池：

```text
akka.actor.deployment {
  /parent/router31 {
    router = round-robin-pool
    optimal-size-exploring-resizer {
      enabled = on
      action-interval = 5s
      downsize-after-underutilized-for = 72h
    }
  }
}
```

```scala
val router31: ActorRef =
  context.actorOf(FromConfig.props(Props[Worker]), "router31")
```

[配置](https://doc.akka.io/docs/akka/current/general/configuration.html)的akka​​.actor.deployment.default.optimal-size-exploring-resizer部分中提供了更多可用的配置选项并进行了描述。

调整大小是通过向Actor池发送消息来触发的，但是没有同步完成；相反，将消息发送到“Head”RouterActor以执行大小更改。因此，您不能依靠调整大小来在所有其他工作人员繁忙时立即创建新的工作人员，因为刚刚发送的消息将被排队到繁忙的Actor的邮箱中。若要解决此问题，请将池配置为使用平衡调度器，有关更多信息，请参见本博客的“Actor的调度器”。

## 如何在Akka内设计路由

从表面上看，路由器看起来像普通的Actor，但实际上它们的实现方式不同。路由器被设计成能够非常高效地接收信息并迅速传递给路由者。

普通的Actor可以用来路由消息，但是Actor的单线程处理可能成为瓶颈。路由器可以通过优化允许并发路由的message-processing管道来实现更高的吞吐量。这是通过将路由器的路由逻辑直接嵌入到其ActorRef中而不是在路由器Actor中实现的。发送到路由器的ActorRef的消息可以立即路由到路由者，完全绕过单线程路由器Actor。

这样做的代价是，路由代码的内部结构比路由器是用正常的Actor实现的要复杂得多。幸运的是，对于路由API的使用者来说，所有这些复杂性都是不可见的。但是，在实现自己的路由器时，应该注意到这一点。

## 自定义路由

如果找不到Akka提供的足以满足您需求的路由器，则可以创建自己的路由器。为了推出自己的路由器，您必须满足本节中说明的某些条件。

在创建自己的路由器之前，应考虑具有类似路由器行为的正常Actor是否可以像成熟的路由器一样完成这项工作。如上所述，与普通Actor相比，路由器的主要好处是其更高的性能。但是它们的编写比普通Actor要复杂一些。因此，如果在您的应用程序中可接受较低的最大吞吐量，则您可能希望坚持使用传统Actor。但是，本部分假定您希望获得最佳性能，因此将演示如何创建自己的路由器。

在本例中创建的路由器正在将每条消息复制到几个目的地。

从路由逻辑开始：

```scala
import scala.collection.immutable
import java.util.concurrent.ThreadLocalRandom
import akka.routing.RoundRobinRoutingLogic
import akka.routing.RoutingLogic
import akka.routing.Routee
import akka.routing.SeveralRoutees

class RedundancyRoutingLogic(nbrCopies: Int) extends RoutingLogic {
  val roundRobin = RoundRobinRoutingLogic()
  def select(message: Any, routees: immutable.IndexedSeq[Routee]): Routee = {
    val targets = (1 to nbrCopies).map(_ => roundRobin.select(message, routees))
    SeveralRoutees(targets)
  }
}
```

将为每个消息调用select，在此示例中，通过循环使用，通过重用现有的RoundRobinRoutingLogic并将结果包装在SeveralRoutees实例中，通过循环选择一些目的地。SeveralRoutees会将消息发送到所有提供的路由

路由逻辑的实现必须是线程安全的，因为它可能在Actor之外使用。

```scala
final case class TestRoutee(n: Int) extends Routee {
  override def send(message: Any, sender: ActorRef): Unit = ()
}

  val logic = new RedundancyRoutingLogic(nbrCopies = 3)

  val routees = for (n <- 1 to 7) yield TestRoutee(n)

  val r1 = logic.select("msg", routees)
  r1.asInstanceOf[SeveralRoutees].routees should be(Vector(TestRoutee(1), TestRoutee(2), TestRoutee(3)))

  val r2 = logic.select("msg", routees)
  r2.asInstanceOf[SeveralRoutees].routees should be(Vector(TestRoutee(4), TestRoutee(5), TestRoutee(6)))

  val r3 = logic.select("msg", routees)
  r3.asInstanceOf[SeveralRoutees].routees should be(Vector(TestRoutee(7), TestRoutee(1), TestRoutee(2)))
```

您可以在此处停止，并按照[简单路由器](https://doc.akka.io/docs/akka/current/routing.html#simple-router)中所述将RedundancyRoutingLogic与akka.routing.Router一起使用。

让我们继续，并使其成为一个独立的，可配置的，路由器Actor。

创建一个扩展Pool、Group或CustomRouterConfig的类。该类是路由逻辑的工厂，包含路由器的配置。在这里，我们把它变成一个Group。

```scala
import akka.dispatch.Dispatchers
import akka.routing.Group
import akka.routing.Router
import akka.japi.Util.immutableSeq
import com.typesafe.config.Config

final case class RedundancyGroup(routeePaths: immutable.Iterable[String], nbrCopies: Int) extends Group {

  def this(config: Config) =
    this(routeePaths = immutableSeq(config.getStringList("routees.paths")), nbrCopies = config.getInt("nbr-copies"))

  override def paths(system: ActorSystem): immutable.Iterable[String] = routeePaths

  override def createRouter(system: ActorSystem): Router =
    new Router(new RedundancyRoutingLogic(nbrCopies))

  override val routerDispatcher: String = Dispatchers.DefaultDispatcherId
}
```

这完全可以当Akka提供的路由器Actor来使用。

```scala
for (n <- 1 to 10) system.actorOf(Props[Storage], "s" + n)

val paths = for (n <- 1 to 10) yield ("/user/s" + n)
val redundancy1: ActorRef =
  system.actorOf(RedundancyGroup(paths, nbrCopies = 3).props(), name = "redundancy1")
redundancy1 ! "important"
```

> 我们在RedundancyGroup中添加了一个构造函数，它接受Config参数。这使得在配置中定义它成为可能。

```text
akka.actor.deployment {
  /redundancy2 {
    router = "jdocs.routing.RedundancyGroup"
    routees.paths = ["/user/s1", "/user/s2", "/user/s3"]
    nbr-copies = 5
  }
}
```

注意路由器属性中的完全限定类名。路由器类必须扩展akka.routing.RouterConfig（Pool、Group或CustomRouterConfig），并具有一个com.typesafe.config.Config参数的构造函数。配置的部署部分将传递给构造函数。

```scala
val redundancy2: ActorRef = system.actorOf(FromConfig.props(), name = "redundancy2")
redundancy2 ! "very important"
```

## 配置调度器

如[调度器](https://doc.akka.io/docs/akka/current/dispatchers.html)所述，池中已创建孩子的调度器将从Props中获取。

为了轻松定义池路由的调度器，您可以在配置的部署部分内联定义调度器。

```text
akka.actor.deployment {
  /poolWithDispatcher {
    router = random-pool
    nr-of-instances = 5
    pool-dispatcher {
      fork-join-executor.parallelism-min = 5
      fork-join-executor.parallelism-max = 5
    }
  }
}
```

这是唯一需要为池启用专用调度程序的操作。

> 如果使用一组Actor并路由到其路径，则它们仍将使用在其Props中为其配置的同一调度程序，因此在创建Actor调度程序后将无法对其进行更改。

“Head”路由器不能总是在同一个Dispatcher上运行，因为它不处理相同类型的消息，因此这个特殊的Actor不使用配置在Props中的调度程序，而是从RouterConfig获取路由器Dispatcher，默认为Actor系统的默认调度程序。所有标准路由器都允许在其构造函数或工厂方法中设置此属性，自定义路由器必须以适当的方式实现该方法。

```scala
val router: ActorRef = system.actorOf(
  // “head” router actor will run on "router-dispatcher" dispatcher
  // Worker routees will run on "pool-dispatcher" dispatcher
  RandomPool(5, routerDispatcher = "router-dispatcher").props(Props[Worker]),
  name = "poolWithDispatcher")
```

```scala
val router: ActorRef = system.actorOf(
  // “head” router actor will run on "router-dispatcher" dispatcher
  // Worker routees will run on "pool-dispatcher" dispatcher
  RandomPool(5, routerDispatcher = "router-dispatcher").props(Props[Worker]),
  name = "poolWithDispatcher")
```

> 不允许将routerDispatcher配置为akka.dispatch.BalancingDispatcherConfigurator，因为其他任何Actor都不能处理用于特殊路由器Actor的消息。

* 使用搜狗翻译、百度翻译、谷歌翻译，仅供参考
* 来自官方文档、参考《响应式架构 消息模式Actor实现与Scala、Akka应用集成》
* 后续随着理解深入会继续修改错误和描述，以便更好理解，本博客开源，欢迎指出错误

