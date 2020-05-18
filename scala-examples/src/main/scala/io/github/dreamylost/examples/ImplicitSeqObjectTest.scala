package io.github.dreamylost.examples

import play.api.libs.json._

/**
  *
 * @author 梦境迷离
  * @since 2019-09-30
  * @version v1.0
  */
object ImplicitSeqObjectTest extends App {

  //需要显示指定自己需要的特别序列化方式
  Console println Json.toJson(TestEntity("name1"))(TestEntity.Implicits.writerForOne)
  Console println Json.toJson(Seq(TestEntity("name1"), TestEntity("name2")))(
    TestEntity.Implicits.writerForSeq
  )

  /**
    * RESULT:
    * {{{
    *  {"name":"name1"}
    * [{"name":"name2"},{"name":"name1"}]
    * }}}
    */

}

case class TestEntity(name: String)

object TestEntity {

  //正常情况的write/read一般放在伴生对象这里，直接就可以自动引用，不需要显示指定

  object Implicits {

    //需求：对返回的对象进行特别处理，去掉或新增2个需要展示的字段或者修改（或不同使用者对格式要求不一样），但不是所有该VO使用方都需要这两个字段。
    //由于该展示VO可能被多个业务或功能同时使用，最简单的是设置Option并且不需要使用的返回null，这在前端使用接口时没问题。但是有没有更好的办法？

    //get使用
    implicit val writerForSeq = new Writes[Seq[TestEntity]] {
      override def writes(seqs: Seq[TestEntity]): JsArray = {
        seqs.foldLeft(JsArray())((a, b) => a.+:(customToJson(b)))
      }
    }

    //post使用
    implicit val writerForOne = new Writes[TestEntity] {
      override def writes(o: TestEntity): JsValue = customToJson(o)
    }

    //这里需要显示指定返回类型JsObject因为foldLeft
    //定义自己的json序列化格式而不影响别人
    private def customToJson(testEntity: TestEntity): JsObject = Json.obj("name" -> testEntity.name)

  }

}
