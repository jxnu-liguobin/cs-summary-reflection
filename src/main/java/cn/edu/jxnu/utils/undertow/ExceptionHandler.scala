package cn.edu.jxnu.utils.undertow

import io.undertow.server.HttpServerExchange

/**
 * undertow 异常处理器
 *
 * @author 梦境迷离
 * @time 2019-08-18
 * @version v1.0
 */
trait ExceptionHandler {

  def handleException(exchange: HttpServerExchange, cause: Throwable): Unit

}
