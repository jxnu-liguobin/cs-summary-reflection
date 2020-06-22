package io.github.dreamylost.concurrent

import java.util.concurrent.TimeUnit

import scala.concurrent.Future

/**
  * 简单令牌桶实现
  *
  * @param capacity           - 令牌容量
  * @param nanosBetweenTokens - 令牌生成间隔时间
  * @author liguobin@growingio.com
  * @version 1.0,2020/3/23
  */
abstract class TokenBucket(capacity: Long, nanosBetweenTokens: Long) {
  require(capacity >= 0, "Capacity must be non negative.")
  require(nanosBetweenTokens > 0, "Time between tokens must be larger than zero nanoseconds.")

  //当前可用的令牌数
  private[this] var availableTokens: Long = _
  //最后更新时间
  private[this] var lastUpdate: Long = _

  /**
    * 令牌桶初始化方法
    */
  def init(): Unit = {
    //初始化可用令牌数就是最大容量
    availableTokens = capacity
    //初始化最后更新时间，需要重写该方法，传入令牌桶生成时间
    lastUpdate = currentTime
  }

  /**
    * 以纳米为单位的当前时间。返回值是单调递增的，与wall-clock没有关系
    */
  def currentTime: Long

  /*
   * 每当元素应通过令牌桶时调用此（副作用）方法。此方法将返回元素需要延迟以与令牌桶参数保持一致的纳秒数。
   * 如果可以立即发出该元素，则返回零。该方法不处理溢出，如果某个元素的延迟时间（以纳秒为单位）长于正Long可以表示的范围，则返回不确定的值。
   * 如果返回非零值，则调用者有责任在返回的延迟过去之前不调用此方法（但可以稍后调用）
   * cost：需要花费多少令牌，可以大于存储桶的容量。
   */
  def offer(cost: Long): Long = {
    //需要消耗的令牌数
    if (cost < 0) throw new IllegalArgumentException("Cost must be non negative")
    //使用相同函数获取当前时间戳
    val now = currentTime
    //本次调用距离最后更新令牌桶的时间戳
    val timeElapsed = now - lastUpdate
    //根据当前时间计算到账（到达）多少个令牌
    val tokensArrived =
      //间隔时间是否大于令牌生成间隔时间
      if (timeElapsed >= nanosBetweenTokens) {
        //说明自最后更新令牌桶后，还生成了新的令牌
        if (timeElapsed < nanosBetweenTokens * 2) {
          //说明此段时间内仅生成了一个令牌，最后更新时间需要更新一下
          lastUpdate += nanosBetweenTokens
          1
        } else {
          //说明自最后更新令牌桶后，还生成了超过1个的令牌
          val tokensArrived = timeElapsed / nanosBetweenTokens
          //最后更新时间需要更新一下
          lastUpdate += tokensArrived * nanosBetweenTokens
          //需要等待的令牌
          tokensArrived
        }
      } else 0 //没有新的令牌生成
    //如果可用令牌+补偿令牌超过了令牌最大桶容量，则令牌个数至多为capacity
    availableTokens = math.min(availableTokens + tokensArrived, capacity)
    //判断需要花费的小于可用的令牌数，表示不需要等待，可用直接执行
    if (cost <= availableTokens) {
      //可用令牌减少
      availableTokens -= cost
      0
    } else {
      //当花费的大于可用的令牌，计算还需要多少个令牌
      val remainingCost = cost - availableTokens
      //自到达令牌更新了令牌桶后，过去了多长时间
      val timeSinceTokenArrival = now - lastUpdate
      //还需要的令牌 * 每个令牌的生成间隔时间 - 上次到达令牌距今已经过去了多久时间
      val delay = remainingCost * nanosBetweenTokens - timeSinceTokenArrival
      //因为不满足足够的令牌，本次可用相当于就是为0
      availableTokens = 0
      //等待一定时间后，令牌足够
      lastUpdate = now + delay
      //返回延迟时间
      delay
    }
  }

}

/**
  * 令牌桶默认实现，使用`System.nanoTime`作为时间源
  */
final class NanoTimeTokenBucket(_cap: Long, _period: Long) extends TokenBucket(_cap, _period) {

  override def currentTime: Long = System.nanoTime()

  def require[T](body: () => Future[T], semaphore: Long, allowBlock: Boolean = false): Future[T] = {
    val delay = offer(semaphore)
    if (delay == 0) {
      body()
    } else {
      if (!allowBlock) {
        Future.failed(new Exception("Not enough semaphore"))
      } else {
        TimeUnit.NANOSECONDS.sleep(delay)
        body()
      }
    }
  }
}

object NanoTimeTokenBucket {

  def apply(_cap: Long, _period: Long): NanoTimeTokenBucket = new NanoTimeTokenBucket(_cap, _period)
}

object TokenBucketSpec extends App {

  import scala.concurrent.ExecutionContext
  import scala.util.Failure
  import scala.util.Success

  val tokenBucket = new NanoTimeTokenBucket(10, 1000000)
  tokenBucket.init()
  new Thread(() => {
    //需要15个，但是只有10个
    //val delay = remainingCost * nanosBetweenTokens - timeSinceTokenArrival
    val ret = tokenBucket.offer(15) / 1000 / 1000
    println(ret)
  }).start()

  implicit val ex = ExecutionContext.Implicits.global
  val tokenBucket2 = NanoTimeTokenBucket(10, 1000000)
  val ret = tokenBucket2.require(() => Future.successful(1 * 2 * 3 * 4 * 5 * 6), 20)
  ret onComplete {
    case Success(_) =>
    case Failure(exception) =>
      println(exception.getMessage)
  }
}
