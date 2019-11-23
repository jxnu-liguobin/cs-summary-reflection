package cn.edu.jxnu.scala

import play.api.libs.json.{ JsNull, JsValue, Json, Writes }

/**
 *
 * @author 梦境迷离
 * @since 2019-09-07
 * @version v1.0
 */
object OptionTest extends App {


  /** *
   * 下面是常用的Option类型的操作
   */
  //有一个Option类型，无法估计Option值不存在时，是否有默认类型，但是可以预估到经过函数F后的默认类型，这种情况很适合使用fold（play的表单验证）
  val opt = Option("ssss")
  //不清楚opt的默认值，但是知道opt经过F函数后一定是true或者false
  val resOpt = opt.fold(false)(_ != "sss")
  println(resOpt) //opt为空时返回false，否则返回_ != "sss"的值，这里返回true

  //如果可以预估Option的默认值更加简单，这个opt就不会有空指针。而直接使用get则不行
  val resGetOrElse = opt.getOrElse("-1") != "sss"
  println(resGetOrElse) //默认是 -1与sss的比较，也是true

  //如果有默认值与当前类型是相同的Option类型，则可以使用orElse，这种一般用在三个Option时,需要与getOrElse连用： A orElse B getOrElse "-1"
  val resOrElse = opt.orElse(Option("-1")).getOrElse("-2") != "sss"
  println(resOrElse) //默认是 -1与sss的比较，也是true


  //测试类
  case class TestJson(id: String, name: Option[String])

  object TestJson {
    implicit val writer = new Writes[TestJson] {
      override def writes(o: TestJson): JsValue = {
        val fields = Json.obj(
          "id" -> o.id,
          "name" -> o.name
        ).fields.filterNot(_._2 == JsNull)
        Json.obj().copy(fields.toMap)
      }
    }
  }

  //去掉空值
  println(Json.toJson(TestJson("iddd", None)))

}
