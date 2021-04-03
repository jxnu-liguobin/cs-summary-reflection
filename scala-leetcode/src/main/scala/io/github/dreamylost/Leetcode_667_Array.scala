/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 数组相邻差值的个数
 *
 * 给定两个整数 n 和 k，你需要实现一个数组，这个数组包含从 1 到 n 的 n 个不同整数，同时满足以下条件：
 * ① 如果这个数组是 [a1, a2, a3, ... , an] ，那么数组 [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|] 中应该有且仅有 k 个不同整数；.
 * ② 如果存在多种答案，你只需实现并返回其中任意一种.
 *
 * @author 梦境迷离
 * @time 2018年7月19日
 * @version v1.0
 */
object Leetcode_667_Array extends App {

  /**
   * 492 ms,100.00%
   * 49.4 MB,100.00%
   */
  def constructArray(n: Int, k: Int): Array[Int] = {
    val ret = new Array[Int](n)
    ret(0) = 1
    var interval = k
    for (i <- 1 to k) {
      ret(i) = if (i % 2 == 1) ret(i - 1) + interval else ret(i - 1) - interval
      interval -= 1
    }
    for (i <- k + 1 until n) {
      ret(i) = i + 1
    }
    ret
  }

  val ret = constructArray(3, 2)
  for (i <- ret) {
    print(i)
  }

}
