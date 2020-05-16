package io.github.dreamylost.play

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * 异步请求记录
  *
 * @author 梦境迷离
  * @since 2019-08-24
  * @version v1.0
  */
trait PerformanceRecord extends LazyLogging {
  private val durationLimit = 100.millisecond.toMillis

  implicit class FutureWrapper[T](f: Future[T]) {

    /**
      * val a = Future {
      *   Thread.sleep(1000)
      * }
      * a.elapsed("a future")
      *
     * @param invokeTag
      * @return
      */
    def elapsed(invokeTag: String): Future[T] = {
      val begin = System.currentTimeMillis()
      f.map { result =>
        val duration = System.currentTimeMillis() - begin
        if (duration > durationLimit)
          logger.warn(
            s"slow invoked: [${invokeTag}] elapsed [${System.currentTimeMillis() - begin}].ms"
          )
        result
      }
    }
  }

}
