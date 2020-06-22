package io.github.dreamylost.examples

import java.util

import scala.collection.JavaConverters._

/**
  * Java集合转换为Scala集合
  *
  * @author 梦境迷离
  * @since 2019-09-25
  * @version v1.0
  */
object JavaCollectionTest extends App {

  val javaMap = new util.LinkedHashMap[String, String]
  javaMap.put("1", "1")

  //使用迭代器遍历
  val scalaMap = javaMap.asScala.toMap //return Map(1 -> 1)
  //这里toSeq里面实际就是toStream
  val scalaStream = javaMap.values().asScala.toSeq //return Stream(1, ?)

  println(scalaMap)
  println(scalaStream)

}
