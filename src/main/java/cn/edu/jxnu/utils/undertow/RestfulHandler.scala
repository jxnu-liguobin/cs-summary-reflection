package cn.edu.jxnu.utils.undertow

import java.nio.ByteBuffer
import java.util.concurrent.{ Executor, Executors }

import cn.edu.jxnu.utils.concurrent.Executable
import com.typesafe.scalalogging.LazyLogging
import io.undertow.security.api.AuthenticationMechanism.AuthenticationMechanismOutcome
import io.undertow.server.{ HttpHandler, HttpServerExchange }
import io.undertow.util.Methods._
import io.undertow.util.{ Headers, StatusCodes }

import scala.concurrent.{ ExecutionContext, Future }
import scala.runtime.BoxedUnit

/**
 * undertow restful处理器接口
 *
 * crud处理器混入该特质
 *
 * @author 梦境迷离
 * @time 2019-08-18
 * @version v1.0
 */
trait RestfulHandler extends HttpHandler with Executable with LazyLogging {

  private[this] lazy val contentType = "application/json;charset=utf-8"
  private[this] implicit lazy val ec: ExecutionContext = ExecutionContext.fromExecutor(workers)
  protected lazy val workers: Executor = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())

  protected val exceptionHandler: ExceptionHandler

  //通用restful请求处理
  override def handleRequest(exchange: HttpServerExchange): Unit = {
    exchange.dispatch(workers, () => {
      lazy val exceptionCaught = (t: Throwable) => {
        exceptionHandler.handleException(exchange, t)
        exchange.endExchange()
        logger.error(t.getLocalizedMessage, t)
      }
      executeSafely(
        authenticate(exchange).map {
          case AuthenticationMechanismOutcome.AUTHENTICATED =>
            executeSafely({
              val future = exchange.getRequestMethod match {
                //调用子类的实现
                case GET => get(exchange)
                case PUT => put(exchange)
                case POST => post(exchange)
                case PATCH => patch(exchange)
                case DELETE => delete(exchange)
              }
              future.map {
                case () | _: BoxedUnit =>
                  exchange.setStatusCode(StatusCodes.NO_CONTENT)
                  exchange.endExchange()
                case result =>
                  exchange.getResponseHeaders.put(Headers.CONTENT_TYPE, contentType)
                  exchange.setStatusCode(getResponseStatusCode(exchange))
                  exchange.getResponseSender.send(ByteBuffer.wrap(writeAsBytes(result)))
              }
            }, exceptionCaught)
          case outcome: AuthenticationMechanismOutcome =>
            val (status, bytes) = authenticateFailureResponse(exchange, outcome)
            exchange.setStatusCode(status)
            if (bytes.nonEmpty) {
              exchange.getResponseHeaders.put(Headers.CONTENT_TYPE, contentType)
              exchange.getResponseSender.send(ByteBuffer.wrap(bytes))
            }
            exchange.endExchange()
        }, exceptionCaught)
    })
  }

  def writeAsBytes(result: Any): Array[Byte]

  def getResponseStatusCode(exchange: HttpServerExchange): Int = {
    exchange.getRequestMethod match {
      case GET => StatusCodes.OK
      case PUT => StatusCodes.OK
      case POST => StatusCodes.CREATED
      case PATCH => StatusCodes.NO_CONTENT
      case DELETE => StatusCodes.NO_CONTENT
    }
  }

  def authenticateFailureResponse(exchange: HttpServerExchange, outcome: AuthenticationMechanismOutcome): (Int, Array[Byte]) = {
    val status = outcome match {
      case AuthenticationMechanismOutcome.NOT_ATTEMPTED => StatusCodes.FORBIDDEN
      case AuthenticationMechanismOutcome.NOT_AUTHENTICATED => StatusCodes.UNAUTHORIZED
      case AuthenticationMechanismOutcome.AUTHENTICATED => throw new IllegalStateException("Request has authenticated")
    }
    status -> Array.emptyByteArray
  }

  def authenticate(exchange: HttpServerExchange): Future[AuthenticationMechanismOutcome] = {
    Future.successful(AuthenticationMechanismOutcome.AUTHENTICATED)
  }

  def get(exchange: HttpServerExchange): Future[Any] = Future.failed(new UnsupportedOperationException)

  def put(exchange: HttpServerExchange): Future[Any] = Future.failed(new UnsupportedOperationException)

  def patch(exchange: HttpServerExchange): Future[Unit] = Future.failed(new UnsupportedOperationException)

  def post(exchange: HttpServerExchange): Future[Any] = Future.failed(new UnsupportedOperationException)

  def delete(exchange: HttpServerExchange): Future[Unit] = Future.failed(new UnsupportedOperationException)

}
