package io.github.dreamylost.play

import play.api.data.FormError
import play.api.data.Forms._
import play.api.data.format.Formatter
import play.api.http.{ ContentTypeOf, ContentTypes, Writeable }
import play.api.libs.json.{ JsNull, JsValue, Json, Writes }

/**
 * 编解码工具
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-15
 */
object ImplicitUtils {

  /** 将DTO写入流，所需要的隐式参数
   *
   * {{{
   *   Ok(dto)
   * }}}
   *
   * @param writes
   * @param codec
   * @tparam A
   * @return
   */
  implicit def jsonWritable[A](implicit writes: Writes[A], codec: play.api.mvc.Codec): Writeable[A] = {
    implicit val contentType = ContentTypeOf[A](Some(ContentTypes.JSON))
    val transform = Writeable.writeableOf_JsValue.transform compose writes.writes
    Writeable(transform)
  }

  /**
   * Play 表单类型映射，使用
   * {{{
   *    val userForm = Form(
   *    mapping(
   *      "name" -> json
   *    )(User.apply)(User.unapply) //可选的optional(json)
   *  )
   * }}}
   */
  val json = of[JsValue]

  implicit def JsValueFormat: Formatter[JsValue] = new Formatter[JsValue] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], JsValue] = {
      Right(data.get(key).map(Json.parse).getOrElse(JsNull))
    }

    override def unbind(key: String, value: JsValue): Map[String, String] = {
      Map.empty
    }
  }


  //  slick读取数据库的json类型数据，需要的隐式对象
  //  private implicit val jsValueMappedColumnType: BaseColumnType[JsValue] = MappedColumnType.base[JsValue, String](Json.stringify, Json.parse)
}
