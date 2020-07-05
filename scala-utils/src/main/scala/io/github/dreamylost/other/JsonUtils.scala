/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.other

import play.api.libs.json.JsObject
import play.api.libs.json.JsValue

import scala.collection.mutable

/**
  * @author 梦境迷离
  * @version 1.0,2020/2/28
  */
object JsonUtils {

  /**
    * 比较两个json对象的结构是否相等，以及返回错误所在的层级
    */
  def compareJsonSchema(source: JsObject, target: JsObject) = {
    //标记递归状态
    val exit = mutable.Queue[Boolean]()
    //在第几层失败
    var depth = 0

    def log(sourcesMap: Map[String, JsValue], targetMap: Map[String, JsValue]): Unit = {
      println("=====compare key start=====")
      println(s"The $depth floor sourcesMap => ${sourcesMap}")
      println(s"The $depth floor targetMap => ${targetMap}")
      println("=====compare key end=====")
    }

    /**
      * 排除不等情况并存到queue中
      * 1. key数量不同
      * 2. key值不同
      * 3. 有子结构则递归判断
      * 4. 最终检验是否有标记在queue，无则相等
      *
      * @param source
      * @param target
      */
    def compare(source: JsObject, target: JsObject)(d: Int): (Int, Boolean) = {
      depth = d + 1
      val toMapJsValue = (jsObj: JsObject) =>
        jsObj.fields.map(s => s._1 -> s._2.asOpt[JsObject].getOrElse(s._2)).toMap
      val toMapJsObj = (map: Map[String, JsValue]) =>
        map.filter(s => s._2.asOpt[JsObject].isDefined).map(s => s._1 -> s._2.as[JsObject])
      val sourcesMap = toMapJsValue(source.as[JsObject])
      val targetMap = toMapJsValue(target.as[JsObject])
      log(sourcesMap, targetMap)
      if (sourcesMap.keySet != targetMap.keySet) {
        exit.enqueue(true)
        return depth -> false
      }
      val sourceChildren = toMapJsObj(sourcesMap)
      val targetChildren = toMapJsObj(targetMap)
      log(sourceChildren, targetChildren)
      if (sourceChildren.keySet != targetChildren.keySet) {
        depth = depth + 1
        exit.enqueue(true)
        return depth -> false
      }
      for ((k1, v1) <- sourceChildren) {
        for ((k2, v2) <- targetChildren) {
          if (k1 == k2) {
            compare(v1, v2)(depth)
          }
        }
      }

      if (exit.isEmpty) depth -> true else depth -> false

    }

    compare(source, target)(0)

  }
}
