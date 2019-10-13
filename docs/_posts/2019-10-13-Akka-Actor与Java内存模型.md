---
title: Actor与Java内存模型
categories:
  - Akka
tags: [Akka-actor入门]
description: 主要介绍Lightbend平台的Akka与Java内存模型的关系
---

* 目录
{:toc}

使用Lightbend平台(包括Scala和Akka)的一个主要好处是它简化了编写并发软件的过程。

### Java内存模型

在Java 5之前，Java内存模型(JMM)定义不当。当多个线程访问共享内存时，可能会得到各种奇怪的结果，例如：

* 看不到其他线程写入的值的线程：可见性问题
* 观察其他线程“不可能”行为的线程，由未按预期顺序执行的指令引起：指令重新排序问题

随着JSR 133在Java 5中的实现，许多这些问题都得到了解决。JMM是一组基于“happens-before”关系的规则，它限制一个内存访问必须在另一个内存访问之前发生，以及相反的，当它们被允许发生故障时。这些规则的两个例子是：

* 监视器锁定规则：在每次获得相同锁之前都会释放锁。
* 易失性变量规则：在每次读取相同的易失性变量之前，都会写入易失性变量。

尽管JMM看起来很复杂，但规范试图在易用性和编写性能良好和可伸缩的并发数据结构之间找到一种平衡。

### Actor与Java内存模型

通过Akka中的Actors实现，有两种方法可以让多个线程在共享内存上执行操作：

* 如果一个消息被发送给一个参与者(例如由另一个actor)。在大多数情况下，消息是不可变的，但是如果消息不是构造正确的不可变对象，而没有“happens-before”规则，则接收方可能会看到部分初始化的数据结构，甚至可能是空值(Long/Double类型)。
* 如果参与者在处理消息时更改其内部状态，并在稍后处理另一条消息时访问该状态。重要的是要认识到，对于Actor模型，您不能保证相同的线程将对不同的消息执行相同的actor。

为了防止Actor的可见性和重新排序问题，Akka保证以下两个“happens before”规则：

* actor发送规则：向actor发送消息，发生在同一actor接收该消息之前。
* actor随后的处理规则：actor对一条消息进行处理，发生在同一actor处理下一条消息之前。

       
        注
        
        用外行的术语来说，这意味着当参与者处理下一条消息时，对参与者内部字段的更改是可见的。因此，演员中的字段不一定是易失性的或等效的。

这两条规则只适用于同一个actor实例，如果使用不同的actor，则无效。

### Future与Java内存模型

在执行向其注册的任何回调之前，将完成“未来”的调用。

我们建议不要关闭非最终字段(在Java中为Final，在Scala中为Val)，如果做如果选择关闭非最终字段，则必须标记它们。易挥发以使该字段的当前值在回调中可见。

如果在引用上关闭，还必须确保所引用的实例是线程安全的。我们强烈建议远离使用锁定的对象，因为它会带来性能问题，在最坏的情况下，会导致死锁。这就是同步的危险。

### Actor与共享可变状态

由于Akka在JVM上运行，仍然需要遵循一些规则。

* 关闭内部Actor状态并将其公开给其他线程
```scala
import akka.actor.{ Actor, ActorRef }
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.collection.mutable

case class Message(msg: String)

class EchoActor extends Actor {
  def receive = {
    case msg => sender() ! msg
  }
}

class CleanUpActor extends Actor {
  def receive = {
    case set: mutable.Set[_] => set.clear()
  }
}

class MyActor(echoActor: ActorRef, cleanUpActor: ActorRef) extends Actor {
  var state = ""
  val mySet = mutable.Set[String]()

  def expensiveCalculation(actorRef: ActorRef): String = {
    // this is a very costly operation
    "Meaning of life is 42"
  }

  def expensiveCalculation(): String = {
    // this is a very costly operation
    "Meaning of life is 42"
  }

  def receive = {
    case _ =>
      implicit val ec = context.dispatcher
      implicit val timeout = Timeout(5 seconds) // needed for `?` below

      // Example of incorrect approach
      // Very bad: shared mutable state will cause your
      // application to break in weird ways
      Future { state = "This will race" }
      ((echoActor ? Message("With this other one")).mapTo[Message]).foreach { received =>
        state = received.msg
      }

      // Very bad: shared mutable object allows
      // the other actor to mutate your own state,
      // or worse, you might get weird race conditions
      cleanUpActor ! mySet

      // Very bad: "sender" changes for every message,
      // shared mutable state bug
      Future { expensiveCalculation(sender()) }

      // Example of correct approach
      // Completely safe: "self" is OK to close over
      // and it's an ActorRef, which is thread-safe
      Future { expensiveCalculation() }.foreach { self ! _ }

      // Completely safe: we close over a fixed value
      // and it's an ActorRef, which is thread-safe
      val currentSender = sender()
      Future { expensiveCalculation(currentSender) }
  }
}
```
* 讯息应不可变，这是为了避免共享可变状态陷阱。