/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 5. 最长回文子串 (Medium)
  *
  * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
  */
object Leetcode_5 {

  /**
    * 中间搜索算法
    */
  def longestPalindrome(s: String): String = {
    def search(index1: Int, index2: Int): String = {
      var i = index1
      var j = index2
      while (i >= 0 && j < s.length && s.charAt(i) == s.charAt(j)) {
        i -= 1
        j += 1
      }
      s.substring(i + 1, j)
    }

    var res = ""

    for (i <- 0 until s.length) {
      val ss = search(i, i)
      if (res.length < ss.length) res = ss
      if (i + 1 < s.length) {
        val ss1 = search(i, i + 1)
        if (ss1.length > res.length) res = ss1
      }
    }
    res
  }

  def main(args: Array[String]): Unit = {
    println(longestPalindrome("abababababababdda"))
  }
}
