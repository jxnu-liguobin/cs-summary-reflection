---
title: Actor的调度器
categories:
  - Akka
tags: [V2.5.25]
description: 介绍Actor的调度器（dispatcher）及其使用
---


* 目录
{:toc}

Akka的消息调度器（MessageDispatcher）是使Akka Actors“滴答”的东西，可以说它是机器的引擎。所有MessageDispatcher实现也都是一个ExecutionContext（参考“Scala的Future解读”），这意味着它们可以用于执行任意代码，例如 Future 实例。以下调度器与调度程序等同，均指Dispatcher，可以理解为Spring的Servlet分派器。

### 默认的调度器

每个ActorSystem都会有一个默认的调度程序，如果没有为Actor配置任何其他的话，就会使用它。可以修改默认调度程序，默认情况下，它是具有指定的default-executor的调度程序。如果使用传入的ExecutionContext创建ActorSystem，则此ExecutionContext将用作此ActorSystem中所有调度程序的默认执行器（与Future中使用自定义ExecutionContext类似）。如果未给出ExecutionContext，它将返回到`akka.actor.default-dispatcher.default-executor.fallback`中指定的执行器默认情况下，这是一个“fork-join-executor”，它在大多数情况下提供了出色的性能。

### 查看调度器

调度器实现ExecutionContext接口，因此可以用于运行Future调用等。

```scala
// for use with Futures, Scheduler, etc.
implicit val executionContext = system.dispatchers.lookup("my-dispatcher")
```

### 为Actor设置调度器

因此，如果您想给Actor一个不同于默认的调度程序，您需要做两件事，第一件是配置Dispatcher：

```
my-dispatcher {
  # 基于事件的调度器的名称
  type = Dispatcher
  # 使用哪种执行器
  executor = "fork-join-executor"
  # fork连接池的配置
  fork-join-executor {
    # Min number of threads to cap factor-based parallelism number to
    parallelism-min = 2
    # Parallelism (threads) ... ceil(available processors * factor)
    parallelism-factor = 2.0
    # Max number of threads to cap factor-based parallelism number to
    parallelism-max = 10
  }
  # Throughput defines the maximum number of messages to be
  # processed per actor before the thread jumps to the next actor.
  # Set to 1 for as fair as possible.
  throughput = 100
}
```

> parallelism-max没有设置ForkJoinPool分配的线程总数的上限。它是一个专门设置会话池持续运行的热线程数，以减少处理新传入任务的延迟。您可以在JDK的[ForkJoinPool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html)文档中阅读更多有关并行性的内容。

另一个使用“thread-pool-executor”的示例：

```
blocking-io-dispatcher {
  type = Dispatcher
  executor = "thread-pool-executor"
  thread-pool-executor {
    fixed-pool-size = 32
  }
  throughput = 1
}
```

线程池执行器Dispatcher是使用java.util.concurrent.ThreadPoolExecutor实现的。您可以在JDK的[ThreadPoolExecutor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ThreadPoolExecutor.html)文档中更详细地了解它。或者查阅[Akka官方的配置文件说明](https://doc.akka.io/docs/akka/current/general/configuration.html)

然后像往常一样创建actor对象，并在部署配置中定义Dispatcher。

```scala
import akka.actor.Props
val myActor = context.actorOf(Props[MyActor], "myactor")
```

配置文件中

```
akka.actor.deployment {
  /myactor {
    dispatcher = my-dispatcher
  }
}
```

部署配置的一个替代方法是在代码中定义Dispatcher。如果在部署配置中定义Dispatcher，则将使用此值而不是以编程方式提供的参数。即配置优先级高与代码。

```scala
import akka.actor.Props
val myActor =
  context.actorOf(Props[MyActor].withDispatcher("my-dispatcher"), "myactor1")
```

> 您在WithDispatcher中指定的Dispatcher和部署配置中的Dispatcher属性实际上是配置的路径。所以在这个例子中它是一个属于顶级层次结构的调度器，你也可以把它作为一个属于子级别层次的调度器，你需要使用点来表示层级关系，比如：“foo.bar.my dispatcher”。actor的层次结构说明请参考“监督与监控”。

### 调度器类型

* Dispatcher
这是一个基于事件的调度器，它将一组Actors绑定到线程池。如果没有指定一个，则使用默认的Dispatcher。
    * 可共享性：不受限
    * 邮箱：任意，为每个actor创建一个
    * 使用场景：默认分派器，bulkheading
    * 底层实现：java.util.concurrent.executorService。使用“fork join executor”、“fork-join-executor”或akka.dispatcher.ExecutorServiceConfigurator的FQCN指定使用“executor”。
* PinnedDispatcher
这个Dispatcher为使用它的每个actor指定了一个唯一的线程；也就是说，每个actor都将有自己的线程池，其中只有一个线程在池中。
    * 可共享性：无
    * 邮箱：任意，为每个actor创建一个
    * 使用场景：bulkheading
    * 底层实现：任何akka.dispatch.ThreadPoolExecutorConfigurator。默认情况下是“thread-pool-executor”。
* CallingThreadDispatcher
此调度器仅在当前线程上运行调用。此Dispatcher不创建任何新线程，但可以从不同的线程同时用于同一个演员。有关详细信息和限制，请参阅[CallingThreadDispatcher](https://doc.akka.io/docs/akka/current/testing.html#callingthreaddispatcher)。
    * 可共享性：不受限
    * 邮箱：任意，每个线程为每个actor创建一个（按需）
    * 使用场景：测试
    * 底层实现：调用线程（duh）

更多调度器配置示例：

配置具有固定线程池大小的调度器，例如用于执行阻塞IO的actor：

```
blocking-io-dispatcher {
  type = Dispatcher
  executor = "thread-pool-executor"
  thread-pool-executor {
    fixed-pool-size = 32
  }
  throughput = 1
}
```

然后使用它：

```scala
val myActor =
  context.actorOf(Props[MyActor].withDispatcher("blocking-io-dispatcher"), "myactor2")
```

另一个基于核数(例如CPU绑定任务)使用线程池的示例

```
my-thread-pool-dispatcher {
  # Dispatcher is the name of the event-based dispatcher
  type = Dispatcher
  # What kind of ExecutionService to use
  executor = "thread-pool-executor"
  # Configuration for the thread pool
  thread-pool-executor {
    # minimum number of threads to cap factor-based core number to
    core-pool-size-min = 2
    # No of core threads ... ceil(available processors * factor)
    core-pool-size-factor = 2.0
    # maximum number of threads to cap factor-based number to
    core-pool-size-max = 10
  }
  # Throughput defines the maximum number of messages to be
  # processed per actor before the thread jumps to the next actor.
  # Set to 1 for as fair as possible.
  throughput = 100
}
```

在保持某些内部状态的actor相对较少的情况下，使用关联池的另一种调度程序可能会增加吞吐量。关联池尽力确保actor始终计划在同一线程上运行。此actor-to-thread pinning旨在减少cpu缓存未命中，从而显著提高吞吐量。

```
affinity-pool-dispatcher {
  # Dispatcher is the name of the event-based dispatcher
  type = Dispatcher
  # What kind of ExecutionService to use
  executor = "affinity-pool-executor"
  # Configuration for the thread pool
  affinity-pool-executor {
    # Min number of threads to cap factor-based parallelism number to
    parallelism-min = 8
    # Parallelism (threads) ... ceil(available processors * factor)
    parallelism-factor = 1
    # Max number of threads to cap factor-based parallelism number to
    parallelism-max = 16
  }
  # Throughput defines the maximum number of messages to be
  # processed per actor before the thread jumps to the next actor.
  # Set to 1 for as fair as possible.
  throughput = 100
}
```

配置PinnedDispatcher：

```
my-pinned-dispatcher {
  executor = "thread-pool-executor"
  type = PinnedDispatcher
}
```

然后使用它：

```scala
val myActor =
  context.actorOf(Props[MyActor].withDispatcher("my-pinned-dispatcher"), "myactor3")
```

> 根据上面的my-thread-pool-dispatcher示例，thread-pool-executor配置是不适用的。这是因为当使用PinnedDispatcher时，每个actor都会有自己的线程池，而这个线程池只有一个线程。

不能保证随着时间的推移使用相同的线程，因为PinnedDispatcher有超时设置，以便在空闲actor的情况下减少资源使用。要始终使用相同的线程，您需要向PinnedDispatcher的配置中添加thread-pool-executor.allow-core-timeout=off。

### 小心处理阻塞


在某些情况下，不可避免地要执行阻塞操作，即让线程在不确定的时间内休眠，等待外部事件的发生。例如，遗留的RDBMS驱动程序或消息传递API，其根本原因通常是(网络)I/O造成的。

```scala
class BlockingActor extends Actor {
  def receive = {
    case i: Int =>
      Thread.sleep(5000) //阻塞5秒
      println(s"Blocking operation finished: ${i}")
  }
}
```

面对这种情况，您可能会尝试将阻塞调用包装在一个未来中，然后使用它，但是这个策略太简单了：当应用程序在增长的负载下运行时，您很可能会发现瓶颈或内存不足或线程不足。

```scala
class BlockingFutureActor extends Actor {
  implicit val executionContext: ExecutionContext = context.dispatcher

  def receive = {
    case i: Int =>
      println(s"Calling blocking Future: ${i}")
      Future {
        Thread.sleep(5000) //阻塞5秒
        println(s"Blocking future finished ${i}")
      }
  }
}
```

### 问题：阻塞默认调度器

这里的关键是这一行：

```scala
implicit val executionContext: ExecutionContext = context.dispatcher
```

使用context.Dispatcher作为执行阻塞的Future executes的调度器可能是一个问题，因为默认情况下，这个调度器用于所有其他actor处理，除非您为该actor设置了一个单独的调度器（参考“为Actor设置调度器”）。


如果所有可用线程都被阻塞，那么同一调度程序上的所有actor都将急需线程，并且无法处理传入的消息。

> 如果可能的话，也应该避免阻塞API。尝试查找或构建反应式API，以便将阻塞最小化，或转移到专用调度程序。通常，当与现有库或系统集成时，不可能避免阻塞API。下面的解决方案解释如何正确处理阻塞操作。请注意，同样的提示适用于管理Akka中任何地方的阻塞操作，包括streams、http和其他构建在其之上的反应式库。

```scala
class PrintActor extends Actor {
  def receive = {
    case i: Int =>
      println(s"PrintActor: ${i}")
  }
}
``

```scala
val actor1 = system.actorOf(Props(new BlockingFutureActor))
val actor2 = system.actorOf(Props(new PrintActor))

for (i <- 1 to 100) {
  actor1 ! i
  actor2 ! i
}
```

在这里，应用程序向BlockingFutureActor和PrintActor发送100条消息，大量akka.actor.default-dispatcher线程正在处理请求。当您运行上述代码时，可能会看到整个应用程序被阻塞在这样的地方：

```
>　PrintActor: 44
>　PrintActor: 45
```

PrintActor被认为是非阻塞的，但是它不能继续处理剩余的消息，因为所有的线程都被其他阻塞的actor占用和阻塞，从而导致线程饥饿。


### 解决方案：专门用于阻塞操作的调度器

在applicy.conf中，专门用于阻塞行为的调度器应该配置如下：

```
my-blocking-dispatcher {
  type = Dispatcher
  executor = "thread-pool-executor"
  thread-pool-executor {
    fixed-pool-size = 16
  }
  throughput = 1
}
```

基于线程池的调度程序允许我们对它将承载的线程数量设置一个限制，这样我们就可以严格控制系统中最多会有多少阻塞的线程。

应该根据您期望在这个dispatcher上运行的工作负载以及运行应用程序的机器的核数来精确地调整其大小。通常，一个很小的核数是一个很好的默认值。


每当必须执行阻塞时，请使用上述配置的dispatcher而不是默认的dispatcher：

```scala
class SeparateDispatcherFutureActor extends Actor {
  implicit val executionContext: ExecutionContext = context.system.dispatchers.lookup("my-blocking-dispatcher")

  def receive = {
    case i: Int =>
      println(s"Calling blocking Future: ${i}")
      Future {
        Thread.sleep(5000)
        println(s"Blocking future finished ${i}")
      }
  }
}
```

### 阻塞操作的可用解决方案

“阻塞问题”的非详尽解决办法包括以下建议：

* 在路由器管理的actor（或一组actor）中执行阻塞调用，确保配置专门用于此目的或足够大小的线程池。
* 在Future中执行阻塞调用，确保在任何时间点都有这样的调用次数的上限（提交无限制的此类任务数量将耗尽您的内存或线程限制）。
* 在Future中执行阻塞调用，为线程池提供一个线程数量上限，这个上限适合于运行应用程序的硬件，如本节中详细解释的那样。
* 指定一个线程来管理一组阻塞资源（例如，NIO选择器驱动多个通道），并在actor消息出现时分派事件。

第一种可能性特别适合于具有单线程性质的资源，比如数据库句柄，传统上只能一次执行一个未执行的查询，并使用内部同步来确保这一点。一个常见的模式是为N个actor创建一个路由器，每个actor封装一个DB连接并处理发送给路由器的查询。然后，必须对数字N进行调优，以获得最大吞吐量，这取决于部署在哪个硬件上的DBMS。

* 使用搜狗翻译、百度翻译、谷歌翻译，仅供参考
* 来自官方文档、参考《响应式架构 消息模式Actor实现与Scala、Akka应用集成》
* 后续随着理解深入会继续修改错误和描述，以便更好理解，本博客开源，欢迎指出错误