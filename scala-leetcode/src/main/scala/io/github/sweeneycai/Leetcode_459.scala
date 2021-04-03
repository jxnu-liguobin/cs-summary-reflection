/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable.ListBuffer

/**
 * 459. 重复的子字符串 (Easy)
 *
 * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
 */
object Leetcode_459 extends App {
  def repeatedSubstringPattern(s: String): Boolean = {
    val possible: ListBuffer[Int] = ListBuffer.empty
    // 先找出所有的因数
    for (i <- 1 to s.length / 2) {
      if (s.length % i == 0) possible.append(i)
    }
    // 因数范围内，与字符串进行比对，重复这个过程
    for (i <- possible) {
      var cnt = 0
      while (cnt < s.length) {
        if (s.charAt(cnt) == s.charAt(cnt % i))
          cnt += 1
        else
          cnt = s.length + 1 // break
      }
      if (cnt == s.length) return true
    }
    false
  }

  println(repeatedSubstringPattern(""))
  println(repeatedSubstringPattern("aba"))
  println(repeatedSubstringPattern("abab"))
}
