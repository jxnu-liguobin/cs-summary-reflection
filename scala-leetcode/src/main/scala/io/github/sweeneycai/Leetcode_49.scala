/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * 49. 字母异位词分组
  * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
  */
object Leetcode_49 extends App {
  def groupAnagrams(strs: Array[String]): List[List[String]] = {
    val hashMap: mutable.HashMap[String, ListBuffer[String]] = mutable.HashMap.empty
    for (str <- strs) {
      val key = str.sorted
      if (hashMap.contains(key)) hashMap(key).append(str)
      else hashMap += key -> ListBuffer(str)
    }
    hashMap.values.map(_.toList).toList
  }

  println(
    groupAnagrams(
      Array(
        "eat",
        "tea",
        "tan",
        "ate",
        "nat",
        "bat"
      )
    )
  )
}
