/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.other

import java.time.LocalDateTime

import io.github.dreamylost.other.DateUtils._
import play.api.libs.json.JsNull
import play.api.libs.json.JsObject
import play.api.libs.json.JsValue
import play.api.libs.json.Json

import scala.collection.mutable

/**
  * 自定义隐式转化
  *
  * @author 梦境迷离
  * @since 2019-08-26
  * @version v1.0
  */
object CustomConversions {

  /**
    * {{{
    *   Json.obj().removeNull
    * }}}
    *
    * @param jsValue
    */
  implicit class filterJsonWrapper(jsValue: JsObject) {
    //去除json中的空值，并保证顺序
    def removeNull = {
      val fields = jsValue.fields.filterNot(_._2 == JsNull)
      val maps = new mutable.LinkedHashMap[String, JsValue]()
      fields.map(field => maps.+=(field._1 -> field._2))
      Json.obj().copy(maps)
    }
  }

  implicit class LocalDateTimeOpt2StringOPt(localDateTime: Option[LocalDateTime]) {
    def toStrOpt = localDateTimeToString(localDateTime)

    def toStrNoMsOpt = localDateTimeToStringNoMS(localDateTime) //无毫秒
  }

  implicit class LongOpt2LocalDateTimeOpt(timestamp: Option[Long]) {
    def toLocalDateTimeOpt = longToLocalDateTime(timestamp)
  }

  implicit class LocalDateTimeOpt2Long(localDateTime: Option[LocalDateTime]) {
    def toLong = localDateTimeToLong(localDateTime) //default = 0L
  }

  //Long转Hash
  //  implicit class id2HashWrapper (any: AnyVal) {
  //    def toHash = MD5Utils.md5(any + "")
  //  }
  //
  //  //可能为空的Long转Hash
  //  implicit class idOpt2HashWrapper (long: Option[AnyVal]) {
  //    def toHashOpt = {
  //      long match {
  //        case Some(l) => MD5Utils.md5(l + "")
  //        case _ => throw GlobalException(CodeMsg.HASH_ERROR)
  //      }
  //    }
  //  }

}
