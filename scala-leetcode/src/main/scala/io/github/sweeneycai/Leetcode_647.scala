/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.annotation.tailrec

/**
 * 647. 回文子串 (Medium)
 *
 * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
 *
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
 */
object Leetcode_647 extends App {

  /**
   * 该题和求最长回文字串有异曲同工之妙，最佳解法是中间搜索
   */
  def countSubstrings(s: String): Int = {
    var res = 0

    @tailrec
    def recur(i: Int, j: Int, count: Int): Int = {
      if (i < 0 || j >= s.length || s.charAt(i) != s.charAt(j))
        count
      else
        recur(i - 1, j + 1, count + 1)
    }

    for (i <- 0 until s.length) {
      res += recur(i, i, 0)
      res += recur(i, i + 1, 0)
    }
    res
  }

  println(countSubstrings("dbcaacbd"))
}
