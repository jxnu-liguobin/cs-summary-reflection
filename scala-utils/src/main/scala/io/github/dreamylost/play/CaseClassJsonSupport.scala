/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.play

import play.api.http.Writeable
import play.api.libs.json.Json
import play.api.libs.json.Writes
import play.api.mvc.Codec

/**
  * 有Writes隐式对象参数的的entity类，使用隐式对象转化为Writeable可以被写入到response
  *
  * @author 梦境迷离
  * @since 2019-11-23
  * @version v1.0
  */
trait CaseClassJsonSupport {

  implicit def writeableOf_caseClass[T <: Product](implicit
      codec: Codec,
      tjs: Writes[T]
  ): Writeable[T] = {
    Writeable(a => codec.encode(Json.stringify(Json.toJson(a))), Some("application/json"))
  }

  implicit def writeableOf_seqOfCaseClass[T <: Product](implicit
      codec: Codec,
      tjs: Writes[T]
  ): Writeable[Seq[T]] = {
    Writeable(a => codec.encode(Json.stringify(Json.toJson(a))), Some("application/json"))
  }
}
