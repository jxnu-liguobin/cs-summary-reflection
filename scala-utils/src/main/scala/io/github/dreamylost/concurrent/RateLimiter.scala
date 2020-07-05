/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.concurrent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.Deadline
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success

/**
  * 简单限流器：任意period时间内请求数不能大于requests
  *
  * @author 梦境迷离
  * @since 2020-03-22
  * @version v1.0
  */
class RateLimiter(requests: Int, period: FiniteDuration) {

  private val startTimes = {
    val onePeriodAgo = Deadline.now - period
    Array.fill(requests)(onePeriodAgo)
  }

  //要使用的下一个槽的索引，跟踪最后一个作业插入队列中的时间，从而强制速率限制
  private var position = 0

  private def lastTime = startTimes(position)

  private def enqueue(time: Deadline) = {
    startTimes(position) = time
    position += 1
    if (position == requests) position = 0
  }

  def call[T](block: => Future[T]): Future[T] = {
    //获取当前的时间戳与队列中最后一个时间戳比较
    val now = Deadline.now
    if ((now - lastTime) < period) {
      Future.failed(new Exception("RateLimiter Exceeded"))
    } else {
      enqueue(now)
      block
    }
  }
}

object RateLimiterSpec extends App {
  //本程序10秒内必定执行完成。所以理论上总会有5个成功的
  val limiter = new RateLimiter(5, 10.seconds)
  var success = 0
  (1 to 100).foreach { i =>
    val ret = limiter.call(Future.successful(1))
    ret onComplete {
      case Failure(exception) =>
        println(s"failed => ${100 - success}, $exception")
      case Success(value) =>
        success += 1
        println(s"success => $success")
    }
    ret
  }
}
