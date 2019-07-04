package cn.edu.jxnu.scala.utils

import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Results}
import play.api.routing.sird._
import play.api.test.WsTestClient
import play.core.server.Server

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * scala常用方法
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-04
 */
object ScalaMethod extends App {

  //测试序列化Map
  json

  def json {
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


  //将DTO使用Ok(*)写入流，所需要的隐式参数
  implicit def jsonWritable[A](implicit writes: Writes[A], codec: play.api.mvc.Codec): Writeable[A] = {
    implicit val contentType = ContentTypeOf[A](Some(ContentTypes.JSON))
    val transform = Writeable.writeableOf_JsValue.transform compose writes.writes
    Writeable(transform)
  }


  //URL参数转Map
  def getToken(body: String, key: String = "access_token"): String = {
    val map = mutable.HashMap[String, String]() //可变 Map
    if (body.isEmpty || !body.contains("&") && !body.contains("=")) ""
    else {
      val params = body.split("&")
      for (p <- params) {
        val kv = p.split("=")
        if (kv.length == 2) {
          map.+=(kv(0) -> kv(1))
        }
      }
      map.getOrElse(key, "")
    }
  }


  //for推断中抛出异常
  //定义通用方法
  def failCondition(condition: Boolean, PlayException: => Exception): Future[Unit] =
    if (condition) Future.failed(PlayException) else Future.successful(())

  //使用
  //  for{
  //    _ <- failCondition(user.isEmpty, InternalErrorException(s"user not found ${p.userId}"))
  //  }


  //测试WSClient编码
  //VM参数 -Dplay.allowGlobalApplication=true
  testWSClient

  def testWSClient: Unit = {
    Server.withRouter() {
      case GET(p"/test") =>
        Action(Results.Ok("""{"foo": "👍"}""").as("application/json"))
    } { implicit port =>
      WsTestClient.withClient { client =>
        //bodyAsBytes是字节的字符串表示，body是HTML文档的字符串表示，可能需要注意乱码
        //Future.successful(Ok(html).as(contentType="text/html")以上两种都可以被转换为html
        println(Await.result(client.url("/test").get().map(_.body.toString), Duration.create("3s"))) // unexpected output
        println(Await.result(client.url("/test").get().map(r => new String(r.bodyAsBytes.toArray)), Duration.create("3s"))) // expected output
      }
    }
  }

}