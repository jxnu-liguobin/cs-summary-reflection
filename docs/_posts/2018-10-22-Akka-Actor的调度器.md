---
title: Actor的调度器
categories:
- Akka
tags: [Akka-Actor入门]
description: 介绍Actor的调度器（dispatcher）及其使用
---


* 目录
{:toc}

Akka的消息调度器（MessageDispatcher）是使Akka Actors“滴答”的东西，可以说它是机器的引擎。所有MessageDispatcher实现也都是一个ExecutionContext（参考“Scala的Future解读”），这意味着它们可以用于执行任意代码，例如 Future 实例。以下调度器与调度程序等同，均指Dispatcher。

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

注意：parallelism-max没有设置ForkJoinPool分配的线程总数的上限。它是一个专门设置会话池持续运行的热线程数，以减少处理新传入任务的延迟。您可以在JDK的[ForkJoinPool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html)文档中阅读更多有关并行性的内容。

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

注意：您在WithDispatcher中指定的Dispatcher和部署配置中的Dispatcher属性实际上是配置的路径。所以在这个例子中它是一个属于顶级层次结构的调度器，你也可以把它作为一个属于子级别层次的调度器，你需要使用点来表示层级关系，比如：“foo.bar.my dispatcher”。actor的层次结构说明请参考“监督与监控”。

### 调度器类型

* Dispatcher
这是一个基于事件的调度器，它将一组Actors绑定到线程池。如果没有指定一个，则使用默认的Dispatcher。
    * 可共享性：不受限
    * 邮箱：任意，为每个actor创建一个
    * 使用场景：默认分派器，bulkheading
    * 底层实现：`java.util.concurrent.executorService`。使用“fork join executor”、“fork-join-executor”或`akka.dispatcher.ExecutorServiceConfigurator`的FQCN指定使用“executor”。
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

注意：根据上面的my-thread-pool-dispatcher示例，thread-pool-executor配置是不适用的。这是因为当使用PinnedDispatcher时，每个actor都会有自己的线程池，而这个线程池只有一个线程。

不能保证随着时间的推移使用相同的线程，因为PinnedDispatcher有超时设置，以便在空闲actor的情况下减少资源使用。要始终使用相同的线程，您需要向PinnedDispatcher的配置中添加`thread-pool-executor.allow-core-timeout=off`。

### 小心处理阻塞













