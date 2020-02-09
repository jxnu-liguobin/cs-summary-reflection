---
title: LightArrayRevolverScheduler
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
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.{ AtomicLong, AtomicReference }
import scala.annotation.tailrec
import scala.collection.immutable
import scala.concurrent.{ Await, ExecutionContext, Future, Promise }
import scala.concurrent.duration._
import scala.util.control.NonFatal
import com.typesafe.config.Config
import akka.event.LoggingAdapter
import akka.util.Helpers
import akka.util.Unsafe.{ instance => unsafe }
import akka.dispatch.AbstractNodeQueue

//本类是Akka actor 2.5.23 默认定时器实现，感兴趣可以看看整理的一个PPT https://github.com/jxnu-liguobin/scala-examples/blob/master/scala-other/src/main/scala/io/github/dreamylost/timer/TimingWheels.ppt
//此调度程序的实现基于诸如Netty的HashedWheelTimer之类的由桶组成的旋转轮，它以固定的滴答速度前进，将在当前存储桶中找到的任务分派到各自的任务ExecutionContexts（线程池）
//任务保存在TaskHolders中，取消后，它们将取消对实际任务的引用，仅在下次轮子到达该存储桶时才清理该外壳
//这样就可以使用一个简单的链接列表来将TaskHolders链接到轮子
//值得注意的是，该调度程序在调度单发任务时不会获得当前时间戳，而是始终将任务延迟四舍五入为TickDuration的整数倍
//这意味着任务安排的时间可能比计划的要晚一滴答（轮子的一个桶的位置），如果 now() + delay <= nextTick，表示已经完成
class LightArrayRevolverScheduler(config: Config, log: LoggingAdapter, threadFactory: ThreadFactory)
    extends Scheduler
    with Closeable {

  import Helpers.Requiring
  import Helpers.ConfigOps

  val WheelSize =
    config
      .getInt("akka.scheduler.ticks-per-wheel")
      //默认512，必须是2的幂次
      //理解为轮子（类似时钟的刻度盘）上有512个刻度
      .requiring(ticks => (ticks & (ticks - 1)) == 0, "ticks-per-wheel must be a power of 2")
  val TickDuration =
    config
    //轮上每次移动1个刻度的延迟，这个值影响精度。windows上默认10ms，且最小是1ms
    //停止定时器可能需要1个滴答，这个值可能影响到关闭actor的时间
      .getMillisDuration("akka.scheduler.tick-duration")
      .requiring(
        _ >= 10.millis || !Helpers.isWindows,
        "minimum supported akka.scheduler.tick-duration on Windows is 10ms")
      .requiring(_ >= 1.millis, "minimum supported akka.scheduler.tick-duration is 1ms")
      //当关闭调度程序时，通常会有一个线程需要停止，而此超时决定了等待多长时间
      //在超时的情况下，actor系统的关闭将继续进行，而不运行可能仍在排队的任务
      //这个时间默认是5ms
  val ShutdownTimeout = config.getMillisDuration("akka.scheduler.shutdown-timeout")

  import LightArrayRevolverScheduler._

  private def roundUp(d: FiniteDuration): FiniteDuration = {
    val dn = d.toNanos
    val r = ((dn - 1) / tickNanos + 1) * tickNanos
    if (r != dn && r > 0 && dn > 0) r.nanos else d
  }

  //时钟实现是可替换的（用于测试），其实现必须返回单调递增的纳秒序列
  protected def clock(): Long = System.nanoTime

  //可替换用于测试
  protected def startTick: Int = 0

  //可重写用于测试
  protected def getShutdownTimeout: FiniteDuration = ShutdownTimeout

  //可重写用于测试
  protected def waitNanos(nanos: Long): Unit = {
    // see http://www.javamex.com/tutorials/threads/sleep_issues.shtml 解释了这里为什么需要这么做
    val sleepMs = if (Helpers.isWindows) (nanos + 4999999) / 10000000 * 10 else (nanos + 999999) / 1000000
    try Thread.sleep(sleepMs)
    catch {
      case _: InterruptedException => Thread.currentThread().interrupt() // we got woken up
    }
  }

  //固定延迟的调度任务
  override def scheduleWithFixedDelay(initialDelay: FiniteDuration, delay: FiniteDuration)(runnable: Runnable)(
      implicit executor: ExecutionContext): Cancellable = {
    //验证延迟的最大值
    checkMaxDelay(roundUp(delay).toNanos)
    super.scheduleWithFixedDelay(initialDelay, delay)(runnable)
  }

  override def schedule(initialDelay: FiniteDuration, delay: FiniteDuration, runnable: Runnable)(
      implicit executor: ExecutionContext): Cancellable = {
    checkMaxDelay(roundUp(delay).toNanos)
    try new AtomicReference[Cancellable](InitialRepeatMarker) with Cancellable { self =>
    //原子性的初始化操作
      compareAndSet(
        InitialRepeatMarker,
        schedule(
          executor,
          new AtomicLong(clock() + initialDelay.toNanos) with Runnable {
            override def run(): Unit = {
              try {
                runnable.run()
                val driftNanos = clock() - getAndAdd(delay.toNanos)
                if (self.get != null)
                //还没到时间，继续调度
                  swap(schedule(executor, this, Duration.fromNanos(Math.max(delay.toNanos - driftNanos, 1))))
              } catch {
                case _: SchedulerException => //忽略排队失败或终止的目标actor
              }
            }
          },
          roundUp(initialDelay)))

      @tailrec private def swap(c: Cancellable): Unit = {
        get match {
            //更新，直到c都被关闭了
            //cancel方法会取消此可取消项，如果成功，则返回true。如果此可取消的已被（同时）取消，则此方法将返回false，尽管isCancelled将返回true。
          case null => if (c != null) c.cancel()
          //cas预期更新失败，交换c
          case old  => if (!compareAndSet(old, c)) swap(c)
        }
      }

      @tailrec final def cancel(): Boolean = {
        get match {
          case null => false
          case c =>
            //可取消的，使用cas更新
            if (c.cancel()) compareAndSet(c, null)
            else compareAndSet(c, null) || cancel()//不可取消的，尝试调用cancel取消
        }
      }

      override def isCancelled: Boolean = get == null//AtomicReference get为空时表示可以取消
    } catch {
      case cause @ SchedulerException(msg) => throw new IllegalStateException(msg, cause)
    }
  }

  //将Runnable延迟执行一次
  override def scheduleOnce(delay: FiniteDuration, runnable: Runnable)(
      implicit executor: ExecutionContext): Cancellable =
    try schedule(executor, runnable, roundUp(delay))
    catch {
      case cause @ SchedulerException(msg) => throw new IllegalStateException(msg, cause)
    }

  override def close(): Unit = {

    def runTask(task: Runnable): Unit = {
      try task.run()
      catch {
        case e: InterruptedException => throw e
        case _: SchedulerException   => //忽略终止的actor
        //执行定时器任务时发生非致命的异常
        case NonFatal(e)             => log.error(e, "exception while executing timer task")
      }
    }

    Await.result(stop(), getShutdownTimeout).foreach {
      case task: Scheduler.TaskRunOnClose =>
        runTask(task)
      case holder: TaskHolder => // don't run
        holder.task match {
          case task: Scheduler.TaskRunOnClose =>
            runTask(task)
          case _ => // don't run
        }
      case _ => // don't run
    }
  }

  //这个调度器的最大支持的任务频率，即在Hz中重复任务执行之间的最小时间间隔的倒数
  override val maxFrequency: Double = 1.second / TickDuration

  /*
   * 下面是实际的定时器实现
   */

  private val start = clock()
  private val tickNanos = TickDuration.toNanos
  private val wheelMask = WheelSize - 1
  private val queue = new TaskQueue

  private def schedule(ec: ExecutionContext, r: Runnable, delay: FiniteDuration): TimerTask =
    if (delay <= Duration.Zero) {
     //计时器关闭后无法排队
      if (stopped.get != null) throw SchedulerException("cannot enqueue after timer shutdown")
      ec.execute(r)
      NotCancellable
    } else if (stopped.get != null) {
        //计时器关闭后无法排队
      throw SchedulerException("cannot enqueue after timer shutdown")
    } else {
      val delayNanos = delay.toNanos
      checkMaxDelay(delayNanos)

      val ticks = (delayNanos / tickNanos).toInt
      val task = new TaskHolder(r, ticks, ec)
      queue.add(task)
      if (stopped.get != null && task.cancel())
      //计时器关闭后无法排队
        throw SchedulerException("cannot enqueue after timer shutdown")
      task
    }

  private def checkMaxDelay(delayNanos: Long): Unit =
    //延迟/滴答的延迟不能大于MaxValue
    if (delayNanos / tickNanos > Int.MaxValue)
      //由于舍入，错误消息中有1秒的误差
      throw new IllegalArgumentException(
        s"Task scheduled with [${delayNanos.nanos.toSeconds}] seconds delay, " +
        s"which is too far in future, maximum delay is [${(tickNanos * Int.MaxValue).nanos.toSeconds - 1}] seconds")

  private val stopped = new AtomicReference[Promise[immutable.Seq[TimerTask]]]
  //关闭，使用超时
  private def stop(): Future[immutable.Seq[TimerTask]] = {
    val p = Promise[immutable.Seq[TimerTask]]()
    //http://hg.openjdk.java.net/jdk7/jdk7/hotspot/file/9b0ca45cd756/src/share/vm/prims/unsafe.cpp
    if (stopped.compareAndSet(null, p)) {//期望为null，失败为false
    //中断定时器线程以使其更快地关闭是不好的，因为它可能正在执行计划的任务，这可能对被中断的响应不好。相反，我们只需再等待一个滴答就可以完成。
      p.future//返回的这里没任何地方填充task，不太明白
    } else Future.successful(Nil)
  }

  //定时任务的主线程
  @volatile private var timerThread: Thread = threadFactory.newThread(new Runnable {

    var tick = startTick
    var totalTick: Long = tick //不环绕（不会因为轮子满一周而被重置）的滴答数，用于计算睡眠时间
    val wheel = Array.fill(WheelSize)(new TaskQueue)//将轮子中的所有桶都初始化为TaskQueue队列

    private def clearAll(): immutable.Seq[TimerTask] = {
      @tailrec def collect(q: TaskQueue, acc: Vector[TimerTask]): Vector[TimerTask] = {
        q.poll() match {
          case null => acc
          case x    => collect(q, acc :+ x)
        }
      }
      (0 until WheelSize).flatMap(i => collect(wheel(i), Vector.empty)) ++ collect(queue, Vector.empty)
    }

    @tailrec
    private def checkQueue(time: Long): Unit = queue.pollNode() match {
      case null => ()
      case node =>
        node.value.ticks match {
          case 0 => node.value.executeTask()
          case ticks =>
            val futureTick = ((
              time - start + //计算自定时器启动以来的纳秒数
              (ticks * tickNanos) + // 添加所需延迟
              tickNanos - 1 // 四舍五入
            ) / tickNanos).toInt //并转换为槽号（轮子上的索引）
            //tick是将环绕（需要转的圈数）的Int，但是futureTick的toInt给我们模运算，并且在任何情况下，差异（偏移）都是正确的
            val offset = futureTick - tick
            val bucket = futureTick & wheelMask
            node.value.ticks = offset
            wheel(bucket).addNode(node)
        }
        checkQueue(time)
    }

    override final def run(): Unit =
      try nextTick()
      catch {
        case t: Throwable =>
          log.error(t, "exception on LARS’ timer thread")
          stopped.get match {
            case null =>
              val thread = threadFactory.newThread(this)
              log.info("starting new LARS thread")
              try thread.start()
              catch {
                case e: Throwable =>
                  log.error(e, "LARS cannot start new thread, ship’s going down!")
                  stopped.set(Promise.successful(Nil))
                  clearAll()
              }
              timerThread = thread
            case p =>
              assert(stopped.compareAndSet(p, Promise.successful(Nil)), "Stop signal violated in LARS")
              p.success(clearAll())
          }
          throw t
      }

    @tailrec final def nextTick(): Unit = {
      val time = clock()
      val sleepTime = start + (totalTick * tickNanos) - time

      if (sleepTime > 0) {
        //小睡前检查队列
        checkQueue(time)
        waitNanos(sleepTime)
      } else {
        //求得开始的刻度在轮子上桶中的位置
        val bucket = tick & wheelMask
        //获得本桶中的任务队列
        val tasks = wheel(bucket)
        val putBack = new TaskQueue
        //执行轮子上的桶
        @tailrec def executeBucket(): Unit = tasks.pollNode() match {
          case null => ()
          case node =>
            val task = node.value
            if (!task.isCancelled) {
              if (task.ticks >= WheelSize) {
                task.ticks -= WheelSize//如果需要滴答的次数大于轮子的大小，说明还需要转圈，暂不执行该桶中的队列中的任务
                putBack.addNode(node)
              } else task.executeTask()//执行队列中的任务
            }
            executeBucket()//递归调用自己判断桶上是否有任务达到执行时间
        }
        executeBucket()
        //更新本桶中整个队列
        wheel(bucket) = putBack
        //刻度加1（轮子上的指针走了一步）
        tick += 1
        totalTick += 1//总的刻度数加1
      }
      stopped.get match {
        case null => nextTick()
        case p =>
          assert(stopped.compareAndSet(p, Promise.successful(Nil)), "Stop signal violated in LARS")
          p.success(clearAll())
      }
    }
  })

  timerThread.start()
}

//内部api
object LightArrayRevolverScheduler {
  private[this] val taskOffset = unsafe.objectFieldOffset(classOf[TaskHolder].getDeclaredField("task"))

  private class TaskQueue extends AbstractNodeQueue[TaskHolder]

  protected[actor] trait TimerTask extends Runnable with Cancellable

  protected[actor] class TaskHolder(@volatile var task: Runnable, var ticks: Int, executionContext: ExecutionContext)
      extends TimerTask {

    @tailrec
    private final def extractTask(replaceWith: Runnable): Runnable =
      task match {
        case t @ (ExecutedTask | CancelledTask) => t
        case x                                  => if (unsafe.compareAndSwapObject(this, taskOffset, x, replaceWith)) x else extractTask(replaceWith)
      }

    private[akka] final def executeTask(): Boolean = extractTask(ExecutedTask) match {
      case ExecutedTask | CancelledTask => false
      case other =>
        try {
          executionContext.execute(other)
          true
        } catch {
          case _: InterruptedException => Thread.currentThread().interrupt(); false
          case NonFatal(e)             => executionContext.reportFailure(e); false
        }
    }

    //仅应在execDirectly中调用
    override def run(): Unit = extractTask(ExecutedTask).run()

    override def cancel(): Boolean = extractTask(CancelledTask) match {
      case ExecutedTask | CancelledTask => false
      case _                            => true
    }

    override def isCancelled: Boolean = task eq CancelledTask
  }

  private[this] val CancelledTask = new Runnable { def run = () }
  private[this] val ExecutedTask = new Runnable { def run = () }

  private val NotCancellable: TimerTask = new TimerTask {
    def cancel(): Boolean = false
    def isCancelled: Boolean = false
    def run(): Unit = ()
  }

  private val InitialRepeatMarker: Cancellable = new Cancellable {
    def cancel(): Boolean = false
    def isCancelled: Boolean = false
  }
}
```