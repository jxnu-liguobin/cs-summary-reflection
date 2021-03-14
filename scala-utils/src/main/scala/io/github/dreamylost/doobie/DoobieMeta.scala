/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.doobie

import doobie._
import org.postgresql.util.HStoreConverter
import org.postgresql.util.PGobject
import play.api.libs.json.JsValue
import play.api.libs.json.Json

import scala.collection.convert.ImplicitConversionsToJava
import scala.collection.convert.ImplicitConversionsToScala

/**
  * doobie 自定义类型映射 隐式对象
  *
  * @author 梦境迷离
  * @since 2019-08-18
  * @version v1.0
  */
object DoobieMeta {

  //将pgsql的json与play-json进行转化
  implicit val jsonMeta: Meta[JsValue] =
    Meta.Advanced
      .other[PGobject]("json")
      .timap[JsValue](a => Json.parse(a.getValue))(a => {
        val o = new PGobject
        o.setType("json")
        o.setValue(a.toString())
        o
      })

  //将pgsql的hstore与Map[String,String]之间进行转化
  implicit val hstoreMapMeta: Meta[Map[String, String]] = Meta.Advanced
    .other[PGobject]("hstore")
    .timap[Map[String, String]](a =>
      ImplicitConversionsToScala.`map AsScala`(HStoreConverter.fromString(a.getValue)).toMap
    )(a => {
      val o = new PGobject
      o.setType("hstore")
      o.setValue(HStoreConverter.toString(ImplicitConversionsToJava.`map AsJavaMap`(a)))
      o
    })
}
