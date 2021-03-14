/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.json

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import io.github.dreamylost.json.JacksonScalaSupportSpec.Filter

/**
  * json序列化工具 Scala支持
  *
  * @author 梦境迷离
  * @since 2019-08-26
  * @version v1.0
  */
object JacksonScalaSupport {

  lazy val mapper: ObjectMapper = {
    val mapper = new ObjectMapper() with ScalaObjectMapper with CaseClassObjectMapper
    mapper.setSerializationInclusion(Include.NON_NULL)
    mapper.setSerializationInclusion(Include.NON_ABSENT)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
    mapper.registerModule(DefaultScalaModule)
    mapper.registerModule(ParametersModule)
    mapper
  }
}

object ParametersModule extends SimpleModule {

  this.addDeserializer(classOf[Filter], new CaseClassDeserializer[Filter])

}
