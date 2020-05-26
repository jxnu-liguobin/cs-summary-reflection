---
title: CircuitBreaker
categories:
  - Akka源码
tags:
  - 源码分析
---

# 2020-03-20-Akka源码-CircuitBreaker

```scala
/*
* Copyright (C) 2009-2018 Lightbend Inc. <https://www.lightbend.com>
*/

package akka.pattern

import java.util.Optional
import java.util.concurrent.{Callable, CompletionStage, CopyOnWriteArrayList}
import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger, AtomicLong}
import java.util.function.{BiFunction, Consumer}

import akka.AkkaException
import akka.actor.Scheduler
import akka.dispatch.ExecutionContexts.sameThreadExecutionContext
import akka.util.JavaDurationConverters._
import akka.util.Unsafe

import scala.compat.java8.FutureConverters
import scala.concurrent.duration._
import scala.concurrent._
import scala.util.{Failure, Success, Try}
import scala.util.control.{NonFatal, NoStackTrace}

/**
*
*1.正常运行时，断路器处于Closed状态：
*  1)超出配置的callTimeout的异常或调用均会增加失败计数器
*  2)成功将失败计数重置为零
*  3)当失败计数器达到maxFailures时，断路器跳闸至Open状态
*2.当断路器处于Open状态时：
*  1)所有调用都以CircuitBreakerOpenException快速失败
*  2)配置resetTimeout后，断路器进入Half-Open状态
*3.当断路器处于Half-Open状态时：
*  1)允许尝试的第一个调用通过，但不会快速失败
*  2)所有其他调用都会快速失败，异常情况与Open状态相同
*  3)如果第一次调用成功，断路器复位回Closed状态，resetTimeout复位
*  4)如果第一次呼叫失败，断路器将再次跳闸至Open状态（对于指数后退断路器，resetTimeout乘以指数后退系数）
*4.状态转换侦听器：
*  1)可以通过onOpen、onClose和onHalfOpen为每个状态条目提供回调
*  2)它们在提供的ExecutionContext中执行
*5.调用结果侦听器：
*  1)回调可用于收集有关所有调用的统计信息，或对成功、失败或超时等特定调用结果做出反应
*  2)支持的回调包括：onCallSuccess、onCallFailure、onCallTimeout和onCallBreakerOpen
*  3)它们在提供的ExecutionContext中执行
*/
object CircuitBreaker {

//提供用于CircuitBreaker的工厂方法的伴生对象，该断路器在调用者的线程中运行回调
   /**
    * 创建一个断路器
    * 使用withSyncCircuitBreaker时，回调函数在调用者的线程中运行；
    * 使用withCircuitBreaker时，回调函数在与Future中传递的相同的ExecutionContext中运行
    * 要将另一个ExecutionContext用于回调，可以在构造函数中指定ExecutionContext
    *
    * @param scheduler    Akka scheduler实例，如：system.scheduler
    * @param maxFailures  断路器打开前最大错误次数
    * @param callTimeout  [[scala.concurrent.duration.FiniteDuration]] XX 时间之后才认为调用失败，失败时会导致故障计数器增加
    * @param resetTimeout [[scala.concurrent.duration.FiniteDuration]] XX 时间之后尝试关闭
    */
def apply(scheduler: Scheduler, maxFailures: Int, callTimeout: FiniteDuration, resetTimeout: FiniteDuration): CircuitBreaker =
    new CircuitBreaker(scheduler, maxFailures, callTimeout, resetTimeout)(sameThreadExecutionContext)

   /**
    * Java API
    */
@deprecated("Use the overloaded one which accepts java.time.Duration instead.", since = "2.5.12")
def create(scheduler: Scheduler, maxFailures: Int, callTimeout: FiniteDuration, resetTimeout: FiniteDuration): CircuitBreaker =
    apply(scheduler, maxFailures, callTimeout, resetTimeout)

   /**
    * Java API
    */
def create(scheduler: Scheduler, maxFailures: Int, callTimeout: java.time.Duration, resetTimeout: java.time.Duration): CircuitBreaker =
    apply(scheduler, maxFailures, callTimeout.asScala, resetTimeout.asScala)

//所有异常都将导致值为true
private val exceptionAsFailure: Try[_] ⇒ Boolean = {
    case _: Success[_] ⇒ false
    case _ ⇒ true
}

//Java版
private def exceptionAsFailureJava[T]: BiFunction[Optional[T], Optional[Throwable], java.lang.Boolean] =
    new BiFunction[Optional[T], Optional[Throwable], java.lang.Boolean] {
    override def apply(t: Optional[T], err: Optional[Throwable]) = {
        if (err.isPresent)
        true
        else
        false
    }
    }
//Java 错误函数转化为Scala的
protected def convertJavaFailureFnToScala[T](javaFn: BiFunction[Optional[T], Optional[Throwable], java.lang.Boolean]): Try[T] ⇒ Boolean = {
    val failureFnInScala: Try[T] ⇒ Boolean = {
    case Success(t) ⇒ javaFn(Optional.of(t), Optional.empty())
    case Failure(err) ⇒ javaFn(Optional.empty(), Optional.of(err))
    }
    failureFnInScala
}
}

//断路器的具体实现
class CircuitBreaker(
scheduler: Scheduler,
maxFailures: Int,
callTimeout: FiniteDuration,
val resetTimeout: FiniteDuration,
maxResetTimeout: FiniteDuration,
exponentialBackoffFactor: Double)(implicit executor: ExecutionContext) extends AbstractCircuitBreaker {

require(exponentialBackoffFactor >= 1.0, "factor must be >= 1.0")

@deprecated("Use the overloaded one which accepts java.time.Duration instead.", since = "2.5.12")
def this(executor: ExecutionContext, scheduler: Scheduler, maxFailures: Int, callTimeout: FiniteDuration, resetTimeout: FiniteDuration) = {
    this(scheduler, maxFailures, callTimeout, resetTimeout, 36500.days, 1.0)(executor)
}

def this(executor: ExecutionContext, scheduler: Scheduler, maxFailures: Int, callTimeout: java.time.Duration, resetTimeout: java.time.Duration) = {
    this(scheduler, maxFailures, callTimeout.asScala, resetTimeout.asScala, 36500.days, 1.0)(executor)
}

//二进制兼容
def this(scheduler: Scheduler, maxFailures: Int, callTimeout: FiniteDuration, resetTimeout: FiniteDuration)(implicit executor: ExecutionContext) = {
    this(scheduler, maxFailures, callTimeout, resetTimeout, 36500.days, 1.0)(executor)
}

   /**
    * 每次断路器闭合的失败尝试都会使“resetTimeout”成倍增加，默认的指数补偿因子为2，说明这个实现是一个指数退避断路器
    *
    * @param maxResetTimeout resetTimeout的上限
    */
def withExponentialBackoff(maxResetTimeout: FiniteDuration): CircuitBreaker = {
    new CircuitBreaker(scheduler, maxFailures, callTimeout, resetTimeout, maxResetTimeout, 2.0)(executor)
}

   /**
    * Java API
    */
def withExponentialBackoff(maxResetTimeout: java.time.Duration): CircuitBreaker = {
    withExponentialBackoff(maxResetTimeout.asScala)
}

   /**
    * 保留对CircuitBreaker当前状态的引用-只能通过辅助方法访问（反射，来自AbstractCircuitBreaker的Unsafe封装）
    */
@volatile
private[this] var _currentStateDoNotCallMeDirectly: State = Closed

   /**
    * 保留对CircuitBreaker的当前resetTimeout的引用-只能通过辅助方法访问
    */
@volatile
private[this] var _currentResetTimeoutDoNotCallMeDirectly: FiniteDuration = resetTimeout

   /**
    * 内联函数
    * Helper方法，通过Unsafe修改状态
    *
    * @param oldState 上一个过渡状态
    * @param newState 下一个过渡状态
    * @return 前一个状态是否正确匹配
    */
@inline
private[this] def swapState(oldState: State, newState: State): Boolean =
//CAS操作
    Unsafe.instance.compareAndSwapObject(this, AbstractCircuitBreaker.stateOffset, oldState, newState)


   /**
    * Unsafe获取当前状态
    */
@inline
private[this] def currentState: State =
    Unsafe.instance.getObjectVolatile(this, AbstractCircuitBreaker.stateOffset).asInstanceOf[State]

   /**
    * Unsafe修改ResetTimeout
    */
@inline
private[this] def swapResetTimeout(oldResetTimeout: FiniteDuration, newResetTimeout: FiniteDuration): Boolean =
    Unsafe.instance.compareAndSwapObject(this, AbstractCircuitBreaker.resetTimeoutOffset, oldResetTimeout, newResetTimeout)

   /**
    * Unsafe获取当前ResetTimeout
    */
@inline
private[this] def currentResetTimeout: FiniteDuration =
    Unsafe.instance.getObjectVolatile(this, AbstractCircuitBreaker.resetTimeoutOffset).asInstanceOf[FiniteDuration]

   /**
    * 包装需要被保护的异步调用代码块
    *
    * @param body            需被保护的异步代码块
    * @param defineFailureFn 该函数定义了应该考虑的故障，从而增加故障计数
    * @return [[scala.concurrent.Future]] containing the call result or a
    *         `scala.concurrent.TimeoutException` if the call timed out
    */
def withCircuitBreaker[T](body: ⇒ Future[T], defineFailureFn: Try[T] ⇒ Boolean): Future[T] =
    currentState.invoke(body, defineFailureFn)


def withCircuitBreaker[T](body: ⇒ Future[T]): Future[T] = currentState.invoke(body, CircuitBreaker.exceptionAsFailure)

   /**
    * Java API
    */
def callWithCircuitBreaker[T](body: Callable[Future[T]]): Future[T] =
    callWithCircuitBreaker(body, CircuitBreaker.exceptionAsFailureJava[T])

   /**
    * Java API
    */
def callWithCircuitBreaker[T](body: Callable[Future[T]], defineFailureFn: BiFunction[Optional[T], Optional[Throwable], java.lang.Boolean]): Future[T] = {
    val failureFnInScala = CircuitBreaker.convertJavaFailureFnToScala(defineFailureFn)

    withCircuitBreaker(body.call, failureFnInScala)
}

   /**
    * Java API (8)
    */
def callWithCircuitBreakerCS[T](body: Callable[CompletionStage[T]]): CompletionStage[T] =
    callWithCircuitBreakerCS(body, CircuitBreaker.exceptionAsFailureJava)

   /**
    * Java API (8)
    */
def callWithCircuitBreakerCS[T](
    body: Callable[CompletionStage[T]],
    defineFailureFn: BiFunction[Optional[T], Optional[Throwable], java.lang.Boolean]): CompletionStage[T] =
    FutureConverters.toJava[T](callWithCircuitBreaker(new Callable[Future[T]] {
    override def call(): Future[T] = FutureConverters.toScala(body.call())
    }, defineFailureFn))

   /**
    *
    * 在调用者线程中执行，由于同步性scala.concurrent.TimeoutException只会在主体完成之后抛出
    * 如果调用超时，则抛出 java.util.concurrent.TimeoutException
    *
    * @param body 需要被保护的代码
    * @return The result of the call
    */
def withSyncCircuitBreaker[T](body: ⇒ T): T =
    withSyncCircuitBreaker(body, CircuitBreaker.exceptionAsFailure)

   /**
    * 同上，但支持定义失败函数以支持被故障计数
    */
def withSyncCircuitBreaker[T](body: ⇒ T, defineFailureFn: Try[T] ⇒ Boolean): T =
    Await.result(
    withCircuitBreaker(
        try Future.successful(body) catch {
        case NonFatal(t) ⇒ Future.failed(t)
        },
        defineFailureFn),
    callTimeout)

   /**
    * Java API
    */
def callWithSyncCircuitBreaker[T](body: Callable[T]): T =
    callWithSyncCircuitBreaker(body, CircuitBreaker.exceptionAsFailureJava[T])

   /**
    * Java API
    */
def callWithSyncCircuitBreaker[T](body: Callable[T], defineFailureFn: BiFunction[Optional[T], Optional[Throwable], java.lang.Boolean]): T = {
    val failureFnInScala = CircuitBreaker.convertJavaFailureFnToScala(defineFailureFn)
    withSyncCircuitBreaker(body.call, failureFnInScala)
}

   /**
    * 通过CircuitBreaker标记成功的调用
    * 有时，断路器的被调用者会向调用者Actor回复一条消息，在这种情况下，标记为成功的调用比通过使用Future更为方便
    */
def succeed(): Unit = {
    currentState.callSucceeds()
}

   /**
    * 通过CircuitBreaker标记失败的调用有时，断路器的被调用者会向调用者Actor回复一条消息，在这种情况下，标记为失败的调用比通过使用Future更为方便
    */
def fail(): Unit = {
    currentState.callFails()
}

   /**
    * 类似电路上开关的闭合和打开
    * 如果内部状态为“闭合”，则返回true 警告：这是一个“power API”调用，您应谨慎使用
    * 如CircuitBreaker一样，CircuitBreaker的常规用例希望远程调用返回Future 因此，如果您自己检查状态，并在CircuitBreaker外部进行远程调用，则应该自己管理状态
    */
def isClosed: Boolean = {
    currentState == Closed
}

   /**
    * 如果内部状态为“打开”，则返回true 警告：这是一个“power API”调用，您应谨慎使用
    * 如CircuitBreaker一样，CircuitBreaker的常规用例希望远程调用返回Future 因此，如果您自己检查状态，并在CircuitBreaker外部进行远程调用，则应该自己管理状态
    */
def isOpen: Boolean = {
    currentState == Open
}

   /**
    * 强调状态
    * 如果内部状态为“半开”，则返回true 警告：这是一个“power API”调用，您应谨慎使用
    * 如CircuitBreaker一样，CircuitBreaker的常规用例希望远程调用返回Future 因此，如果您自己检查状态，并在CircuitBreaker外部进行远程调用，则应该自己管理状态
    */
def isHalfOpen: Boolean = {
    currentState == HalfOpen
}

   /**
    * 添加一个断路器在打开时执行的回调，回调在构造函数中提供的ExecutionContext中执行
    *
    * @param callback 状态更改时要调用的处理程序
    * @return CircuitBreaker for fluent usage
    */
def onOpen(callback: ⇒ Unit): CircuitBreaker = addOnOpenListener(new Runnable {
    def run = callback
})

   /**
    * Java API
    */
@deprecated("Use addOnOpenListener instead", "2.5.0")
def onOpen(callback: Runnable): CircuitBreaker = addOnOpenListener(callback)

   /**
    * Java API for onOpen
    *
    */
def addOnOpenListener(callback: Runnable): CircuitBreaker = {
    Open addListener callback
    this
}

   /**
    * 添加一个断路器在处于半开状态时执行的回调，回调在构造函数中提供的ExecutionContext中执行
    *
    * @param callback 状态更改时要调用的处理程序
    * @return CircuitBreaker for fluent usage
    */
def onHalfOpen(callback: ⇒ Unit): CircuitBreaker = addOnHalfOpenListener(new Runnable {
    def run = callback
})

   /**
    * JavaAPI
    */
@deprecated("Use addOnHalfOpenListener instead", "2.5.0")
def onHalfOpen(callback: Runnable): CircuitBreaker = addOnHalfOpenListener(callback)

   /**
    * JavaAPI
    */
def addOnHalfOpenListener(callback: Runnable): CircuitBreaker = {
    HalfOpen addListener callback
    this
}

   /**
    * 添加一个回调以在断路器状态关闭时执行，回调在构造函数中提供的ExecutionContext中执行
    *
    * @param callback 状态更改时要调用的处理程序
    * @return CircuitBreaker for fluent usage
    */
def onClose(callback: ⇒ Unit): CircuitBreaker = addOnCloseListener(new Runnable {
    def run = callback
})

   /**
    * JavaAPI
    */
@deprecated("Use addOnCloseListener instead", "2.5.0")
def onClose(callback: Runnable): CircuitBreaker = addOnCloseListener(callback)

   /**
    * JavaAPI
    */
def addOnCloseListener(callback: Runnable): CircuitBreaker = {
    Closed addListener callback
    this
}

   /**
    * 添加一个回调以在调用成功完成时执行，回调在构造函数中提供的ExecutionContext中执行
    *
    * @param callback 成功调用时要调用的处理程序，其中传递的值是经过的时间（以纳秒为单位）
    * @return CircuitBreaker for fluent usage
    */
def onCallSuccess(callback: Long ⇒ Unit): CircuitBreaker = addOnCallSuccessListener(new Consumer[Long] {
    def accept(result: Long): Unit = callback(result)
})

   /**
    * JavaAPI
    */
def addOnCallSuccessListener(callback: Consumer[Long]): CircuitBreaker = {
    successListeners add callback
    this
}

   /**
    * 添加一个回调以在调用失败后执行，回调在构造函数中提供的ExecutionContext中执行
    *
    * @param callback 失败调用时要调用的处理程序，其中传递的值是经过的时间（以纳秒为单位）
    * @return CircuitBreaker for fluent usage
    */
def onCallFailure(callback: Long ⇒ Unit): CircuitBreaker = addOnCallFailureListener(new Consumer[Long] {
    def accept(result: Long): Unit = callback(result)
})

   /**
    * JavaAPI
    */
def addOnCallFailureListener(callback: Consumer[Long]): CircuitBreaker = {
    callFailureListeners add callback
    this
}

   /**
    * 添加一个回调以在超时结束时执行，回调在构造函数中提供的ExecutionContext中执行
    *
    * @param callback 超时结束时调用的处理程序，其中传递的值是经过的时间（以纳秒为单位）
    * @return CircuitBreaker for fluent usage
    */
def onCallTimeout(callback: Long ⇒ Unit): CircuitBreaker = addOnCallTimeoutListener(new Consumer[Long] {
    def accept(result: Long): Unit = callback(result)
})

   /**
    * JavaAPI
    */
def addOnCallTimeoutListener(callback: Consumer[Long]): CircuitBreaker = {
    callTimeoutListeners add callback
    this
}

   /**
    * 添加一个回调，以在由于打开断路器而导致调用失败时执行，回调在构造函数中提供的ExecutionContext中执行
    *
    * @param callback 由于断路器打开，在调用时调用的处理程序失败
    * @return CircuitBreaker for fluent usage
    */
def onCallBreakerOpen(callback: ⇒ Unit): CircuitBreaker = addOnCallBreakerOpenListener(new Runnable {
    def run = callback
})

   /**
    * JavaAPI
    */
def addOnCallBreakerOpenListener(callback: Runnable): CircuitBreaker = {
    callBreakerOpenListeners add callback
    this
}

   /**
    * 当前故障计数
    *
    * @return count
    */
private[akka] def currentFailureCount: Int = Closed.get

   /**
    * 实现状态之间的一致过渡 如果尝试了无效的转换，则抛出IllegalStateException
    * 使用CAS转换状态
    *
    * @param fromState from
    * @param toState   to
    */
private def transition(fromState: State, toState: State): Unit = {
    if (swapState(fromState, toState))
    toState.enter()
    //否则其他一些线程已经交换状态
}

   /**
    * 将断路器跳到打开状态 这在“关闭”或“半开”状态下有效半开或者闭合时均可以到“打开”状态
    */
private def tripBreaker(fromState: State): Unit = transition(fromState, Open)

   /**
    * 将断路器重置为闭合状态 仅在半开状态下有效
    */
private def resetBreaker(): Unit = transition(HalfOpen, Closed)

   /**
    * 调用所有onSuccess回调处理程序
    *
    * @param start 调用的时间（纳秒）
    */
private def notifyCallSuccessListeners(start: Long): Unit = if (!successListeners.isEmpty) {
    val elapsed = System.nanoTime() - start
    val iterator = successListeners.iterator()
    while (iterator.hasNext) {
    val listener = iterator.next()
    executor.execute(new Runnable {
        def run() = listener.accept(elapsed)
    })
    }
}

   /**
    * 调用所有onCallFailure回调处理程序
    *
    * @param start 调用的时间（纳秒）
    */
private def notifyCallFailureListeners(start: Long): Unit = if (!callFailureListeners.isEmpty) {
    val elapsed = System.nanoTime() - start
    val iterator = callFailureListeners.iterator()
    while (iterator.hasNext) {
    val listener = iterator.next()
    executor.execute(new Runnable {
        def run() = listener.accept(elapsed)
    })
    }
}

   /**
    * 调用所有onCallTimeout回调处理程序
    *
    * @param start 调用的时间（纳秒）
    */
private def notifyCallTimeoutListeners(start: Long): Unit = if (!callTimeoutListeners.isEmpty) {
    val elapsed = System.nanoTime() - start
    val iterator = callTimeoutListeners.iterator()
    while (iterator.hasNext) {
    val listener = iterator.next()
    executor.execute(new Runnable {
        def run() = listener.accept(elapsed)
    })
    }
}

   /**
    * 调用所有onCallBreakerOpen回调处理程序
    */
private def notifyCallBreakerOpenListeners(): Unit = if (!callBreakerOpenListeners.isEmpty) {
    val iterator = callBreakerOpenListeners.iterator()
    while (iterator.hasNext) {
    val listener = iterator.next()
    executor.execute(listener)
    }
}

    /**
    * 尝试通过过渡到半开状态来重置断路器 仅在打开状态下有效
    */
private def attemptReset(): Unit = transition(Open, HalfOpen)
//断路器超时异常
private val timeoutEx = new TimeoutException("Circuit Breaker Timed out.") with NoStackTrace

private val callFailureListeners = new CopyOnWriteArrayList[Consumer[Long]]

private val callTimeoutListeners = new CopyOnWriteArrayList[Consumer[Long]]

private val callBreakerOpenListeners = new CopyOnWriteArrayList[Runnable]

private val successListeners = new CopyOnWriteArrayList[Consumer[Long]]

    /**
    * 内部状态抽象
    */
private sealed trait State {
    //所有监听器
    private val listeners = new CopyOnWriteArrayList[Runnable]

    /**
    * 添加在状态项上调用的侦听器函数
    */
    def addListener(listener: Runnable): Unit = listeners add listener

    /**
    * 监听器是否存在
    */
    private def hasListeners: Boolean = !listeners.isEmpty

    /**
    * 通过在隐式参数ExecutionContext中执行的Future通知侦听器转换事件
    */
    protected def notifyTransitionListeners(): Unit = {
    if (hasListeners) {
        val iterator = listeners.iterator
        while (iterator.hasNext) {
        val listener = iterator.next
        executor.execute(listener)
        }
    }
    }

    /**
    * 抛出异常或超过允许的超时调用被视为失败调用，否则视为成功调用
    */
    def callThrough[T](body: ⇒ Future[T], defineFailureFn: Try[T] ⇒ Boolean): Future[T] = {

    //将非致命异常转化为Future.failed
    def materialize[U](value: ⇒ Future[U]): Future[U] = try value catch {
        case NonFatal(t) ⇒ Future.failed(t)
    }
    //callTimeout=0时，不需要定时器
    if (callTimeout == Duration.Zero) {
        val start = System.nanoTime()
        val f = materialize(body)

        f.onComplete {
        case s: Success[_] ⇒
            //触发调用成功监听器
            notifyCallSuccessListeners(start)
            callSucceeds()
        case Failure(ex) ⇒
            //触发调用失败监听器
            notifyCallFailureListeners(start)
            callFails()
        }

        f
    } else {
        val start = System.nanoTime()
        val p = Promise[T]()
        //构造函数传进来的ExecutionContext - 线程池
        implicit val ec = sameThreadExecutionContext

        p.future.onComplete { fResult ⇒
        if (defineFailureFn(fResult)) {
            callFails()
        } else {
            //触发调用成功监听器
            notifyCallSuccessListeners(start)
            callSucceeds()
        }
        }
        //延迟callTimeout时间后检查Promise p的状态是否失败，失败则抛出断路器超时异常
        val timeout = scheduler.scheduleOnce(callTimeout) {
        if (p tryFailure timeoutEx) {
            //触发调用超时监听器
            notifyCallTimeoutListeners(start)
        }
        }

        materialize(body).onComplete {
        case Success(result) ⇒
            p.trySuccess(result)
            //取消掉定时器
            timeout.cancel
        case Failure(ex) ⇒
            if (p.tryFailure(ex)) {
            //触发调用失败监听器
            notifyCallFailureListeners(start)
            }
            //取消掉定时器
            timeout.cancel
        }
        p.future
    }
    }

    def callThrough[T](body: ⇒ Future[T]): Future[T] = callThrough(body, CircuitBreaker.exceptionAsFailure)

    /**
    * 所有状态的抽象调用方法
    */
    def invoke[T](body: ⇒ Future[T], defineFailureFn: Try[T] ⇒ Boolean): Future[T]

    def invoke[T](body: ⇒ Future[T]): Future[T] = invoke(body, CircuitBreaker.exceptionAsFailure)

    /**
    * 成功时调用
    */
    def callSucceeds(): Unit

    /**
    * 失败时调用
    */
    def callFails(): Unit

    /**
    * 转换状态在转换期间调用，调用子类方法_enter后通知侦听器
    */
    final def enter(): Unit = {
    _enter()
    //通知所有的转化监听器
    notifyTransitionListeners()
    }

    /**
    * 抽象方法
    */
    def _enter(): Unit
}

   /**
    * 闭合状态的具体实现
    * 继承自AtomicInteger，拥有原子计数器，标记处在闭合状态时的错误次数
    */
private object Closed extends AtomicInteger with State {

    override def invoke[T](body: ⇒ Future[T], defineFailureFn: Try[T] ⇒ Boolean): Future[T] =
    callThrough(body, defineFailureFn)

    //成功时重置错误次数为0
    override def callSucceeds(): Unit = set(0)

    //失败时，如果当前错误次数等于最大错误次数，将断路器设为打开状态，不再处理任务
    override def callFails(): Unit = if (incrementAndGet() == maxFailures) tripBreaker(Closed)

    override def _enter(): Unit = {
    //重置错误次数，并且修改断路器的退避时间    
    set(0)
    swapResetTimeout(currentResetTimeout, resetTimeout)
    }

    override def toString: String = "Closed with failure count = " + get()
}

   /**
    * 半开状态的具体实现
    * 继承自AtomicBoolean，标记是否为半开状态
    */
private object HalfOpen extends AtomicBoolean(true) with State {

    override def invoke[T](body: ⇒ Future[T], defineFailureFn: Try[T] ⇒ Boolean): Future[T] =
    if (compareAndSet(true, false))
        //处于半开状态时，可以处理第一次请求
        callThrough(body, defineFailureFn)
    else {
        //后续请求将使得断路器打开，不再处理任务
        notifyCallBreakerOpenListeners()
        //且同时抛出断路器异常，0表示处于半开状态
        Future.failed[T](new CircuitBreakerOpenException(0.seconds))
    }
    //半开状态的调用成功时，断路器回到闭合状态 
    override def callSucceeds(): Unit = resetBreaker()
    //半开状态调用失败时，回到打开状态
    override def callFails(): Unit = tripBreaker(HalfOpen)

    override def _enter(): Unit = set(true)

    override def toString: String = "Half-Open currently testing call for success = " + get()
}

/**
 * 打开状态的具体实现，继承自AtomicLong，标记打开时间
 */
private object Open extends AtomicLong with State {

    override def invoke[T](body: ⇒ Future[T], defineFailureFn: Try[T] ⇒ Boolean): Future[T] = {
    //唤起所有打开监听器    
    notifyCallBreakerOpenListeners()
    //返回 异常，包含重置之前的剩余时间
    Future.failed(new CircuitBreakerOpenException(remainingDuration()))
    }

    //计算剩余时间
    private def remainingDuration(): FiniteDuration = {
    val fromOpened = System.nanoTime() - get
    val diff = currentResetTimeout.toNanos - fromOpened
    if (diff <= 0L) Duration.Zero
    else diff.nanos
    }

    /**
    * 对于打开状态不提供此操作，因为调用永远不会执行，因此没有成功或失败
    */
    override def callSucceeds(): Unit = ()

    /**
    * 对于打开状态不提供此操作，因为调用永远不会执行，因此没有成功或失败
    */
    override def callFails(): Unit = ()

    /**
    * 进入此状态后，通过[[akka.actor.Scheduler]]安排一次尝试重置的时间，并保存输入时间以计算尝试重置之前的剩余时间
    */
    override def _enter(): Unit = {
    set(System.nanoTime())
    scheduler.scheduleOnce(currentResetTimeout) {
        //在打开状态时，尝试等待currentResetTimeout时间后过渡到半开状态，以便可以恢复断路器
        attemptReset()
    }
    //下一个重置时间，使用退避，exponentialBackoffFactor=2
    val nextResetTimeout = currentResetTimeout * exponentialBackoffFactor match {
        case f: FiniteDuration ⇒ f
        case _ ⇒ currentResetTimeout
    }
    //下次的退避时间必须小于用户指定的最大退避时间
    if (nextResetTimeout < maxResetTimeout)
        swapResetTimeout(currentResetTimeout, nextResetTimeout)
    }

    /**
    * 重写以获得更具描述性的toString方法
    */
    override def toString: String = "Open"
}

}

/**
* 断路器打开时引发异常
*
* @param remainingDuration 保存尝试重置之前的剩余时间 持续时间为零表示断路器当前处于半开状态
* @param message           默认为“断路器打开；调用快速失败”
*/
class CircuitBreakerOpenException(
val remainingDuration: FiniteDuration,
message: String = "Circuit Breaker is open; calls are failing fast")
extends AkkaException(message) with NoStackTrace
```

```java
/**
 * 抽象断路器
 */
package akka.pattern;

import akka.util.Unsafe;
//使用Unsafe反射获取CircuitBreaker的属性值
class AbstractCircuitBreaker {
  protected final static long stateOffset;
  protected final static long resetTimeoutOffset;

  static {
    try {
      stateOffset = Unsafe.instance.objectFieldOffset(CircuitBreaker.class.getDeclaredField("_currentStateDoNotCallMeDirectly"));
      resetTimeoutOffset = Unsafe.instance.objectFieldOffset(CircuitBreaker.class.getDeclaredField("_currentResetTimeoutDoNotCallMeDirectly"));
    } catch(Throwable t){
      throw new ExceptionInInitializerError(t);
    }
  }
}
```

