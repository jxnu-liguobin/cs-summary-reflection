/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.concurrent

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.control.NonFatal

/**
  * 带错误捕获的线程执行器
  *
  * @author 梦境迷离
  * @time 2019-08-18
  * @version v1.0
  */
trait Executable {

  def executeSafely[T](block: => Future[T], exceptionCaught: Throwable => Unit)(implicit
      ec: ExecutionContext
  ): Unit = {
    try {
      block.recover {
        //非致命错误
        case NonFatal(t) => exceptionCaught(t)
      }
    } catch {
      case NonFatal(t) => exceptionCaught(t)
    }
  }
}
