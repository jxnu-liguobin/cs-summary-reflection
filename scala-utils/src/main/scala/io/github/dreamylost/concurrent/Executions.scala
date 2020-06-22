package io.github.dreamylost.concurrent

import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContextExecutor

/**
  * 线程池，可替换默认的Implicits.global
  *
  * @author 梦境迷离
  * @since 2019-08-24
  * @version v1.0
  */
object Executions {

  object Implicits extends LazyLogging {
    implicit val mapper: ExecutionContextExecutor = ExecutionContext.fromExecutorService(
      new ForkJoinPool(
        Runtime.getRuntime.availableProcessors() * 2,
        ForkJoinPool.defaultForkJoinWorkerThreadFactory,
        (t: Thread, e: Throwable) ⇒ logger.error(e.getLocalizedMessage, e),
        true
      )
    )

    implicit val io: ExecutionContextExecutor =
      ExecutionContext.fromExecutor(Executors.newFixedThreadPool(20))
  }

}
