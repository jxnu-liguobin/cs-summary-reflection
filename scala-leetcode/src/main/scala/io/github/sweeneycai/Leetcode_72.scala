/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 72. 编辑距离 (Hard)
  *
  * 给你两个单词word1 和word2，请你计算出将word1转换成word2 所使用的最少操作数。
  *
  * 你可以对一个单词进行如下三种操作：
  *
  * 插入一个字符
  * 删除一个字符
  * 替换一个字符
  */
object Leetcode_72 extends App {

  /**
    * dp 经典问题
    * 设 dp(i)(j) 代表从状态i到状态j所需要的步数，显而易见，
    * dp(i)(0) = i, dp(j)(0) = j
    * 而对于dp(i)(j)则有不同的选择：
    *
    * - 从 dp(i)(j-1) 转移过来，即 word2 添加一个字符
    * - 从 dp(i-1)(j) 转移过来，即 word1 添加一个字符
    * - 从 dp(i-1)(j-1) 转移过来，这个时候分两种情况进行讨论
    */
  def minDistance(word1: String, word2: String): Int = {
    val n = word1.length
    val m = word2.length
    if (n * m == 0) return n + m
    val dp: Array[Array[Int]] = Array.ofDim(n + 1, m + 1)
    for (i <- dp.indices) dp(i)(0) = i
    for (i <- dp(0).indices) dp(0)(i) = i
    for (i <- 0 until n; j <- 0 until m) {
      if (word1.charAt(i) == word2.charAt(j)) {
        // 两个字符相等，本次编辑距离为0，一定是当前最小的编辑距离
        dp(i + 1)(j + 1) = dp(i)(j)
      } else {
        // 两个字符不相等，可以从其他的状态转移过来，编辑距离需要加一
        val right = dp(i + 1)(j)
        val up = dp(i)(j + 1)
        val right_up = dp(i)(j)
        dp(i + 1)(j + 1) = math.min(right, math.min(up, right_up)) + 1
      }
    }
    dp(n)(m)
  }

  println(minDistance("horse", "ros"))
}
