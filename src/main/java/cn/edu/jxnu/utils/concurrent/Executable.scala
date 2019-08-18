package cn.edu.jxnu.utils.concurrent

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

/**
 * 线程执行器
 *
 * @author 梦境迷离
 * @time 2019-08-18
 * @version v1.0
 */
trait Executable {

  def executeSafely[T](block: => Future[T], exceptionCaught: Throwable => Unit)(implicit ec: ExecutionContext): Unit = {
    try {
      block.recover {
        case NonFatal(t) => exceptionCaught(t)
      }
    } catch {
      case NonFatal(t) => exceptionCaught(t)
    }
  }
}
