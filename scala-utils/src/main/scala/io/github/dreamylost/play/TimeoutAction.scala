package io.github.dreamylost.play

import java.util.Locale

import akka.actor.ActorSystem
import akka.pattern.after
import javax.inject.Inject
import play.api.Configuration
import play.api.i18n.Lang
import play.api.libs.json.Json
import play.api.mvc.Results.RequestTimeout
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

/**
  * 请求超时action
  *
 * @author 梦境迷离
  * @since 2019-08-24
  * @version v1.0
  */
class TimeoutAction @Inject() (
    parser: BodyParsers.Default,
    actorSystem: ActorSystem,
    configuration: Configuration
)(implicit ec: ExecutionContext)
    extends ActionBuilderImpl(parser) {

  /**
    * {{{
    *   timeoutAction andThen other actions
    * }}}
    *
   * @param request
    * @param block
    * @tparam A
    * @return
    */
  override def invokeBlock[A](
      request: Request[A],
      block: Request[A] ⇒ Future[Result]
  ): Future[Result] = {
    val timeout = configuration
      .getOptional[Duration]("api.timeout")
      .map(_.toSeconds.seconds)
      .getOrElse(30.seconds)
    val futureResponse = block(request)
    val futureTimeout = after(timeout, actorSystem.scheduler)(Future.successful {
      implicit val locale: Locale =
        request.acceptLanguages.headOption.getOrElse(Lang.defaultLang).toLocale
      val code = "request_timeout"
      RequestTimeout(
        Json.obj(
          "message" -> code,
          "errors" -> Seq(Json.obj("code" -> code, "message" -> s"${code}_msg"))
        )
      )
    })
    Future firstCompletedOf Seq(futureResponse, futureTimeout)
  }
}
