/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.play

import play.api.libs.json.JsObject
import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.mvc.Results.BadRequest
import play.api.mvc.Results.Conflict
import play.api.mvc.Results.Forbidden
import play.api.mvc.Results.Locked
import play.api.mvc.Results.NotFound
import play.api.mvc.Results.Ok
import play.api.mvc.Results.UnprocessableEntity
import play.api.mvc.Results.UnsupportedMediaType

import scala.concurrent.Future
import scala.concurrent.Future.successful

/**
 * @author 梦境迷离
 * @since 2019-09-04
 * @version v1.0
 */
object CommonResult {

  val futureOk = successful(Ok(Json.obj("status" -> "OK")))

  val oK = Ok(Json.obj("status" -> "OK"))

  def badRequest(message: String): Result = BadRequest(build("Bad Request", message))

  def futureBadRequest(message: String): Future[Result] = successful(badRequest(message))

  def unsupportedMedia(message: String): Result =
    UnsupportedMediaType(build("Unsupported MediaType", message))

  def futureUnsupportedMedia(message: String): Future[Result] =
    successful(unsupportedMedia(message))

  def unprocessableEntity(message: String): Result =
    UnprocessableEntity(build("Unprocessable Entity", message))

  def futureUnprocessableEntity(message: String): Future[Result] =
    successful(unprocessableEntity(message))

  def notFound(message: String): Result = NotFound(build("Not Found", message))

  def futureNotFound(message: String): Future[Result] = successful(notFound(message))

  def forbidden(message: String): Result = Forbidden(build("Forbidden", message))

  def futureForbidden(message: String): Future[Result] = successful(forbidden(message))

  def conflict(message: String): Result = Conflict(build("Conflict", message))

  def futureConflict(message: String): Future[Result] = successful(conflict(message))

  def lock(message: String): Result = Locked(build("Lock", message))

  def futureLock(message: String): Future[Result] = successful(lock(message))

  def build(kind: String, message: String): JsObject =
    Json.obj("message" -> kind, "errors" -> Seq(Json.obj("message" -> message)))

}
