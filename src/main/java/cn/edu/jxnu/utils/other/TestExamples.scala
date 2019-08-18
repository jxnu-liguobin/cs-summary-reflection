package cn.edu.jxnu.utils.other

import play.api.mvc.{Action, Results}
import play.api.routing.sird._
import play.api.test.WsTestClient
import play.core.server.Server
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

/**
 * @author 梦境迷离
 * @version 1.0, 2019-07-15
 */
object TestExamples {

  //测试 ws 和 json编码
  def testWSClient: Unit = {
    Server.withRouter() {
      case GET(p"/test ") =>
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
