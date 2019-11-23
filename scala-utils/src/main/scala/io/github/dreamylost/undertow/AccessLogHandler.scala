package io.github.dreamylost.undertow

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.{ Objects, UUID }

import com.google.common.net.HttpHeaders
import com.typesafe.scalalogging.Logger
import io.undertow.server.{ ExchangeCompletionListener, HttpHandler, HttpServerExchange }
import io.undertow.util.HeaderValues

/**
 * undertow 访问日志处理器
 *
 * @author 梦境迷离
 * @time 2019-08-18
 * @version v1.0
 */
class AccessLogHandler(next: HttpHandler, service: Option[String]) extends HttpHandler {

  private[this] val logger: Logger = Logger("access")
  private[this] lazy val serviceName = service.getOrElse("-")
  private[this] val listener: ExchangeCompletionListener =
    (exchange: HttpServerExchange, nextListener: ExchangeCompletionListener.NextListener) => {
      try {
        val requestId = getRequestId(exchange)
        logger.info(accessLogMessage(requestId, exchange))
      } finally {
        nextListener.proceed()
      }
    }

  override def handleRequest(exchange: HttpServerExchange): Unit = {
    exchange.addExchangeCompleteListener(listener)
    next.handleRequest(exchange)
  }

  private[this] def getRequestId(exchange: HttpServerExchange): String = {
    val requestId = exchange.getRequestHeaders.get("X-Request-Id")
    if (isEmpty(requestId)) {
      UUID.randomUUID().getLeastSignificantBits.toHexString
    } else {
      requestId.getFirst
    }
  }

  private[this] def accessLogMessage(requestId: String, exchange: HttpServerExchange): String = {
    val cost = (System.nanoTime() - exchange.getRequestStartTime) / 1000000.0
    val path = exchange.getRequestPath
    val status = exchange.getStatusCode
    val headers = exchange.getRequestHeaders
    val protocol = exchange.getProtocol.toString
    val bytesSent = exchange.getResponseBytesSent
    val method = exchange.getRequestMethod.toString
    val referer = headers.get(HttpHeaders.REFERER)
    val ua = headers.getFirst(HttpHeaders.USER_AGENT)
    val now = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    val referred = if (isEmpty(referer)) "-" else referer.getFirst
    val address = exchange.getSourceAddress.getAddress.getHostAddress
    s"""$address $serviceName $requestId - [$now] "$method $path $protocol" $status $bytesSent $cost "$referred" "$ua""""
  }

  private[this] def isEmpty(values: HeaderValues): Boolean = Objects.isNull(values) || values.isEmpty

}

object AccessLogHandler {

  def apply(next: HttpHandler): AccessLogHandler = new AccessLogHandler(next, None)

  def apply(next: HttpHandler, service: String): AccessLogHandler = new AccessLogHandler(next, Option(service))

}