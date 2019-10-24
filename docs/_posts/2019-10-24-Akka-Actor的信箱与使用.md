---
title: Actor的信箱与使用
categories:
  - Akka
tags: [Akka-Actor入门]
description: 本章介绍actor的信箱类型及其常用配置
---

* 目录
{:toc}

### 依赖

若要使用邮箱，必须在项目中添加下列依赖项：

```scala
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.26"
```

Akka的邮箱保存着发送给Actor的消息。通常，每个Actor都有自己的邮箱，但是使用BalancingPool，所有的路由器都将共享一个邮箱实例。
此处的邮箱与信箱意思等同，传递意思等同发送。

### 邮箱选择

#### Actor需要的消息队列类型

通过让某个Actor扩展参数化特征RequiresMessageQueue，可以为特定类型的Actor要求某种类型的消息队列。以下是一个例子：

```scala
import akka.dispatch.RequiresMessageQueue
import akka.dispatch.BoundedMessageQueueSemantics

class MyBoundedActor extends MyActor with RequiresMessageQueue[BoundedMessageQueueSemantics]
```

需要将RequiresMessageQueue特质的类型参数映射到配置中的邮箱，如下所示：

```
bounded-mailbox {
  mailbox-type = "akka.dispatch.NonBlockingBoundedMailbox"
  mailbox-capacity = 1000 
}

akka.actor.mailbox.requirements {
  "akka.dispatch.BoundedMessageQueueSemantics" = bounded-mailbox
}
```

现在，每次创建MyBoundedActor类型的Actor时，它都会尝试获得一个有界的邮箱。如果Actor在部署中配置了不同的邮箱（直接或通过具有指定邮箱类型的dispatcher），则将覆盖此映射配置。

注意：为Actor创建的邮箱中的队列类型将根据该特质中的所需类型进行检查，如果队列没有实现所需的类型，则Actor的创建将失败。

#### 调度器需要的消息队列类型

调度器还可能需要运行在其上的Actor使用的邮箱类型。一个例子是BalancingDispatcher，它需要一个对多个并发消费者来说是线程安全的消息队列。这样的要求是在dispatcher配置部分中配置的，如下所示：

```
my-dispatcher {
  mailbox-requirement = org.example.MyInterface //暂称requirement为“要求的或需要的邮箱类型”
}
```

给定的是一个类或接口的全限定名，然后确保它是消息队列的实现的超类型。如有冲突，例如，如果Actor需要一个不能满足此要求的邮箱类型，那么Actor的创建就会失败。

#### 如何选择邮箱类型

当创建一个Actor时，ActorRefProvider首先确定将执行它的调度器，然后按以下方式确定邮箱：

1. 如果Actor的部署配置部分包含mailbox key，那么指定的此配置部分将描述要使用的邮箱类型。
2. 如果actor的Props包含邮箱选择（即在其上调用了WithMailbox），那么指定的此配置部分将描述要使用的邮箱类型（请注意，这需要是一个绝对配置路径，例如`myapp.Special-mailbox`，而不是嵌套在Akka命名空间中的相对路径）。
3. 如果dispatcher的配置部分包含mailbox-type key，则将使用相同部分来配置邮箱类型。
4. 如果Actor需要如上所述的邮箱类型，则将使用该要求的映射来确定要使用的邮箱类型；如果失败则将尝试dispatcher的（如果有的话）。
5. 如果dispatcher需要如上所述的邮箱类型，则该要求的映射将用于确定要使用的邮箱类型。
6. 默认邮箱`akka.actor.default-mailbox`将被使用。

#### 默认邮箱

如果未如上所述指定需要的邮箱，则使用默认邮箱。默认情况下，它是一个无边界邮箱，由`java.util.concurrent.concurrentlinkedqueue`支持。

`SingleConsumerOnlyUndeddedMailbox`是一个更高效的邮箱，它可以用作默认邮箱，但不能与BalancingDispatcher一起使用。

将`SingleConsumerOnlyUnderdedMailbox`配置为默认邮箱：

```
akka.actor.default-mailbox {
  mailbox-type = "akka.dispatch.SingleConsumerOnlyUnboundedMailbox"
}
```

#### 将哪些配置传递给邮箱类型

每个邮箱类型都由一个类实现，该类扩展了MailboxType，并接受两个构造函数参数：`ActorSystem.Settings`对象和Config部分。后者是通过从Actor系统的配置中获取命名的配置部分，用邮箱类型的配置路径覆盖其id key并向默认邮箱配置部分添加fall-back来实现的

### 内建邮箱的实现

Akka附带了许多邮箱实现，如下所示：

* UnboundedMailbox（默认）
  * 默认邮箱
  * 实现：基于`java.util.concurrent.ConcurrentLinkedQueue`
  * 阻塞：否
  * 有界性：否
  * 配置名称："unbounded" or "akka.dispatch.UnboundedMailbox"
* SingleConsumerOnUnedMailbox
  * 这个队列可能比默认队列快，也可能不快，这取决于您的用例（一定要正确地进行基准测试）
  * 实现：基于多生产者单消费者的队列，不能与BalancingDispatcher同时使用
  * 阻塞：否
  * 有界性：否
  * 配置名称："akka.dispatch.SingleConsumerOnlyUnboundedMailbox"
* NonBlockingBoundedMailbox
  * 实现：基于一种高效的多生产者单消费者队列
  * 阻塞：否，将溢出的消息丢弃到死信中
  * 有界性：是
  * 配置名称："akka.dispatch.NonBlockingBoundedMailbox"
* UnboundedControlAwareMailbox
  * 传递具有更高优先级的继承自akka.dispatch.ControlMessage的消息
  * 实现：基于两个`java.util.concurrent.ConcurrentLinkedQueue`
  * 阻塞：否
  * 有界性：否
  * 配置名称："akka.dispatch.UnboundedControlAwareMailbox"
* UnboundedPriorityMailbox
  * 相同优先级消息的传递顺序是未定义的（与StablePriorityMailbox形成对比）
  * 实现：基于`java.util.concurrent.PriorityBlockingQueue`
  * 阻塞：否
  * 有界性：否
  * 配置名称："akka.dispatch.UnboundedPriorityMailbox"
* UnboundedStablePriorityMailbox
  * 对于优先级相同的消息保留FIFO顺序（与UnboundedPriorityMailbox形成对比）
  * 实现：基于`akka.util.PriorityQueueStabilizer`（封装了java.util.concurrent.PriorityBlockingQueue）
  * 阻塞：否
  * 有界性：否
  * 配置名称："akka.dispatch.UnboundedStablePriorityMailbox"

当达到容量并配置的`mailbox-push-timeout-time`为非0，其他有界限的邮箱实现将阻止发送者。

注意：以下邮箱只应与`mailbox-push-timeout-time`为0的一起使用，因为当`mailbox-push-timeout-time`非0时，下面所有的信箱都是阻塞的，反应式不推荐使用阻塞，万不得已也应当隔离出阻塞操作到独立的调度线程。

* BoundedMailbox
  * 实现：基于`java.util.concurrent.LinkedBlockingQueue`
  * 阻塞：若与`mailbox-push-timeout-time`不为0时一起使用则是，否则不是
  * 有界性：是
  * 配置名称："bounded” or “akka.dispatch.BoundedMailbox"
* BoundedPriorityMailbox
  * 实现：基于`akka.util.BoundedBlockingQueue`（封装了java.util.PriorityQueue）
  * 相同优先级消息的传递顺序是未定义的（与BoundedStablePriorityMailbox形成对比）
  * 阻塞：若与`mailbox-push-timeout-time`不为0时一起使用则是，否则不是
  * 有界性：是
  * 配置名称："akka.dispatch.BoundedPriorityMailbox"
* BoundedStablePriorityMailbox
  * 实现：基于`akka.util.PriorityQueueStabilizer`（封装了java.util.PriorityQueue）
  * 阻塞：若与`mailbox-push-timeout-time`不为0时一起使用则是，否则不是
  * 有界性：是
  * 配置名称："akka.dispatch.BoundedStablePriorityMailbox"
* BoundedControlAwareMailbox
  * 传递具有更高优先级的继承自akka.dispatch.ControlMessage的消息
  * 实现：基于两个`java.util.concurrent.ConcurrentLinkedQueue`，如果已达到容量，则阻塞队列。
  * 阻塞：若与`mailbox-push-timeout-time`不为0时一起使用则是，否则不是
  * 有界性：是
  * 配置名称："akka.dispatch.BoundedControlAwareMailbox"

### 邮箱配置示例

#### 优先级邮箱

如何创建PriorityMailbox：

```scala
import akka.dispatch.PriorityGenerator
import akka.dispatch.UnboundedStablePriorityMailbox
import com.typesafe.config.Config

// We inherit, in this case, from UnboundedStablePriorityMailbox
// and seed it with the priority generator
class MyPrioMailbox(settings: ActorSystem.Settings, config: Config)
    extends UnboundedStablePriorityMailbox(
      // Create a new PriorityGenerator, lower prio means more important
      PriorityGenerator {
        //如果可能的话，应该首先处理高优先级的消息
        case 'highpriority => 0

        //如果可能的话，应该最后处理低优先级的消息
        case 'lowpriority => 2

        // PoisonPill when no other left
        case PoisonPill => 3

        // We default to 1, which is in between high and low
        case otherwise => 1
      }
```

然后将其添加到配置中：

```
prio-dispatcher {
  mailbox-type = "docs.dispatcher.DispatcherDocSpec$MyPrioMailbox"
  //Other dispatcher configuration goes here
}
```

然后是一个如何使用它的例子：


```scala
// We create a new Actor that just prints out what it processes
class Logger extends Actor {
  val log: LoggingAdapter = Logging(context.system, this)

  self ! 'lowpriority
  self ! 'lowpriority
  self ! 'highpriority
  self ! 'pigdog
  self ! 'pigdog2
  self ! 'pigdog3
  self ! 'highpriority
  self ! PoisonPill

  def receive = {
    case x => log.info(x.toString)
  }
}
val a = system.actorOf(Props(classOf[Logger], this).withDispatcher("prio-dispatcher"))

/*
 * Logs:
 * 'highpriority
 * 'highpriority
 * 'pigdog
 * 'pigdog2
 * 'pigdog3
 * 'lowpriority
 * 'lowpriority
 */
```

还可以像这样直接配置邮箱类型（这是顶级配置条目）：

```
prio-mailbox {
  mailbox-type = "docs.dispatcher.DispatcherDocSpec$MyPrioMailbox"
  //Other mailbox configuration goes here
}

akka.actor.deployment {
  /priomailboxactor {
    mailbox = prio-mailbox
  }
}
```

然后在部署中使用它，如下所示：

```scala
import akka.actor.Props
val myActor = context.actorOf(Props[MyActor], "priomailboxactor")
```

或者这样的代码：

```scala
import akka.actor.Props
val myActor = context.actorOf(Props[MyActor].withMailbox("prio-mailbox"))
```

#### ControlAwareMailbox

如果一个Actor需要能够立即接收控制消息，那么ControlAwareMailbox就会非常有用，而不管它的邮箱中已经有多少其他消息。如前介绍，这个消息类型优先级更高。

它可以配置如下：

```
control-aware-dispatcher {
  mailbox-type = "akka.dispatch.UnboundedControlAwareMailbox"
  //Other dispatcher configuration goes here
}
```

控制消息需要扩展ControlMessage特质：

```scala
import akka.dispatch.ControlMessage

case object MyControlMessage extends ControlMessage
```

然后是一个如何使用它的例子：


```scala
// We create a new Actor that just prints out what it processes
class Logger extends Actor {
  val log: LoggingAdapter = Logging(context.system, this)

  self ! 'foo
  self ! 'bar
  self ! MyControlMessage
  self ! PoisonPill

  def receive = {
    case x => log.info(x.toString)
  }
}
val a = system.actorOf(Props(classOf[Logger], this).withDispatcher("control-aware-dispatcher"))

/*
 * Logs:
 * MyControlMessage
 * 'foo
 * 'bar
 */
```

### 创建自己的邮箱类型

```scala
// Marker trait used for mailbox requirements mapping
trait MyUnboundedMessageQueueSemantics
```

```scala
boundedMailbox extends MailboxType with ProducesMessageQueue[MyUnboundedMailbox.MyMessageQueue] {

  import MyUnboundedMailbox._

  // 此构造函数签名必须存在，它将由Akka调用。
  def this(settings: ActorSystem.Settings, config: Config) = {
    // 将你的初始化代码放在这里
    this()
  }

  // 调用create方法来创建MessageQueue
  final override def create(owner: Option[ActorRef], system: Option[ActorSystem]): MessageQueue =
    new MyMessageQueue()
}
```

然后将MailboxType的FQCN指定为dispatcher配置或邮箱配置中“mailbox-type”的值。

注意：确保包含一个构造函数，该构造函数需要`akka.actor.ActorSystem.Settings`和`com.ypesafe.config.Confi`g参数，因为此构造函数是以反射方式调用来构造你的邮箱类型的。作为第二个参数传入的配置是配置中使用此邮箱类型描述dispatcher或邮箱设置的部分；将对使用该配置类型的每个dispatcher或邮箱设置实例化邮箱类型一次。

您还可以使用邮箱作为调度器的要求（requirement ），如下所示：

```
custom-dispatcher {
  mailbox-requirement =
  "jdocs.dispatcher.MyUnboundedMessageQueueSemantics"
}

akka.actor.mailbox.requirements {
  "jdocs.dispatcher.MyUnboundedMessageQueueSemantics" =
  custom-dispatcher-mailbox
}

custom-dispatcher-mailbox {
  mailbox-type = "jdocs.dispatcher.MyUnboundedMailbox"
}
```

或者像这样定义Actor类的要求：

```scala
class MySpecialActor extends Actor with RequiresMessageQueue[MyUnboundedMessageQueueSemantics] {
  // ...
}
```

### system.actorOf的特殊语义

为了使system.actorOf同步和非阻塞，同时保持返回类型ActorRef（以及返回的ref全部功能的语义），对这种情况进行特殊处理。
在幕后，构造了一种无意义的Actor引用，它被发送给系统的监护人Actor，实际上是监护人Actor创建了该Actor及其上下文，并将这些内容放入到该Actor的引用中。在此之前，发送给ActorRef的消息将在本地排队，只有在交换了真正的填充后，它们才会被传输到真正的邮箱中。因此，

```scala
val props: Props = ...
//此actor使用MyCustomMailbox，假定它是一个单例
system.actorOf(props.withDispatcher("myCustomMailbox")) ! "bang"
assert(MyCustomMailbox.instance.getLastEnqueuedMessage == "bang")
```

可能会失败；您必须留出一些时间来通过并重试检查