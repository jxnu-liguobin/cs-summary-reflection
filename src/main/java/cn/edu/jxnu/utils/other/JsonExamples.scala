package cn.edu.jxnu.utils.other

import play.api.libs.json.Json

/**
 * Json 处理例子
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-15
 */
object JsonExamples extends App {

  //定义Map
  val queryParamsRequest: Map[String, Seq[String]] = Map("clickId" -> Seq("1", "2", "3", "4"), "currTime" -> Seq("5", "4", "3", "2"))

  val jsValue = Json.toJson(queryParamsRequest)

  //JsValue转换为字符串
  val jsonQueryParams = Json.stringify(jsValue)
  println("序列化成字符串 => " + jsonQueryParams)

  //JsValue转换为格式化字符串
  val prettyPrint = Json.prettyPrint(jsValue)
  println("JsValue格式化成字符串 => " + prettyPrint)

  //从字符串中解析成JsValue
  //自定义对象需要定义Reader
  val json = Json.parse(jsonQueryParams)
  println("反序列的Json值 => " + json)

  //将JsValue转换为Map
  val queryParamResult = json.validate[Map[String, Seq[String]]].getOrElse(Map())

  //遍历Map，并打印
  for ((k, v) <- queryParamResult) {
    println(k, v)

  }
}
