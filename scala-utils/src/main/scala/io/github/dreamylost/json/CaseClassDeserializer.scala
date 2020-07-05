/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.json

import java.lang.reflect.Constructor
import java.lang.reflect.Method

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.ObjectNode

import scala.reflect.runtime.universe._

/**
  * case class 解码器
  *
  * @author 梦境迷离
  * @since 2020-04-30
  * @version v1.0
  */
class CaseClassDeserializer[T: Manifest]() extends StdDeserializer[T](manifest[T].runtimeClass) {

  /**
    * 构造函数、方法、字段、字段索引、基本类型
    */
  private val constructor: Constructor[_] = handledType().getConstructors.head
  private val methods: Array[Method] = handledType().getMethods
  private val fields: Array[Symbol] = typeOf[T].members.filter(!_.isMethod).toArray.reverse
  private val fieldsWithIndex: Array[(Symbol, Int)] = fields.zipWithIndex
  private val numberTypes: Seq[Type] = Seq(
    typeOf[Int],
    typeOf[Long],
    typeOf[Char],
    typeOf[Short],
    typeOf[Byte],
    typeOf[Float],
    typeOf[Double]
  )

  /**
    * 对象转Java的零值
    *
    * @param typ
    * @return
    */
  private def zeroValue(typ: Type): AnyRef = {
    typ match {
      case t if numberTypes.contains(t) ⇒ 0.asInstanceOf[AnyRef]
      case t if t =:= typeOf[Boolean] ⇒ Boolean.box(false)
      case t if t <:< typeOf[Option[_]] ⇒ None
      case t if t <:< typeOf[collection.Map[_, _]] ⇒ collection.Map.empty
      case t if t <:< typeOf[Iterable[_]] ⇒ Nil
      case _ ⇒ null
    }
  }

  /**
    * 反序列化
    *
    * @param jp
    * @param ctxt
    * @throws
    * @return
    */
  @throws[JsonProcessingException]
  override def deserialize(jp: JsonParser, ctxt: DeserializationContext): T = {
    val node: ObjectNode = jp.getCodec.readTree(jp)
    val mapper: ObjectMapper with CaseClassObjectMapper =
      jp.getCodec.asInstanceOf[ObjectMapper with CaseClassObjectMapper]
    val params: Array[AnyRef] = fieldsWithIndex.map {
      case (field, index) ⇒
        val fieldName = field.name.toString.trim
        if (node.hasNonNull(fieldName)) {
          // 在 json 里存在该字段
          val javaType = mapper.constructType(field.typeSignature)
          val subJsonParser = mapper.treeAsTokens(node.get(fieldName))
          mapper.readValue(subJsonParser, javaType).asInstanceOf[AnyRef]
        } else {
          // 在 json 里不存在该字段
          // 获取拿到 case class 默认值的方法的 methodName
          val methodName = "$lessinit$greater$default$" + (index + 1)
          // 没有默认值时用零值替代
          methods.find(_.getName == methodName).fold(zeroValue(field.typeSignature))(_.invoke(null))
        }
    }
    constructor.newInstance(params: _*).asInstanceOf[T]
  }
}

/**
  * scala java 类型映射
  *
  * @author 梦境迷离
  * @since 2020-04-30
  * @version v1.0
  */
trait CaseClassObjectMapper {
  self: ObjectMapper =>

  def constructType(typ: Type): JavaType = {
    def checkArgumentLength(argumentsLength: Int, shouldBeLength: Int) = {
      if (argumentsLength != shouldBeLength) {
        throw new IllegalArgumentException(
          s"need exactly $shouldBeLength type parameter for types (${typ.typeSymbol.fullName})"
        )
      }
    }

    //使用Scala反射获取运行时类的类型
    lazy val clazz: Class[_] =
      runtimeMirror(getClass.getClassLoader).runtimeClass(typ.typeSymbol.asClass)
    lazy val typeArguments: Array[JavaType] = typ.typeArgs.map(constructType).toArray

    typ match {
      case t if t == typeOf[Any] =>
        getTypeFactory.constructType(classOf[Any])
      case _ if clazz.isArray =>
        checkArgumentLength(typeArguments.length, 1)
        getTypeFactory.constructArrayType(typeArguments(0))
      case t if t <:< typeOf[collection.Map[_, _]] =>
        checkArgumentLength(typeArguments.length, 2)
        getTypeFactory.constructMapLikeType(clazz, typeArguments(0), typeArguments(1))
      case t if t <:< typeOf[collection.Iterable[_]] =>
        checkArgumentLength(typeArguments.length, 1)
        getTypeFactory.constructCollectionLikeType(clazz, typeArguments(0))
      case t if t <:< typeOf[Option[_]] =>
        checkArgumentLength(typeArguments.length, 1)
        getTypeFactory.constructReferenceType(clazz, typeArguments(0))
      case _ =>
        getTypeFactory.constructParametricType(clazz, typeArguments: _*)
    }
  }
}
