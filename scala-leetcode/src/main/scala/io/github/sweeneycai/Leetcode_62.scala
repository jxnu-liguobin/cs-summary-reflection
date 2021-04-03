/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
 * 62. 不同路径 (Medium)
 *
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 *
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 *
 * 问总共有多少条不同的路径？
 *
 * @see <a href="https://leetcode-cn.com/problems/unique-paths">leetcode-cn.com</a>
 */
object Leetcode_62 extends App {

  /**
   * 原始dp，空间复杂度为 m * n
   * 执行用时 520 ms，内存消耗 49.2 MB
   */
  def uniquePaths1(m: Int, n: Int): Int = {
    val array: Array[Array[Int]] = Array.ofDim(m, n)
    for (i <- array.indices) {
      array(i)(0) = 1
    }
    for (j <- array(0).indices) {
      array(0)(j) = 1
    }
    for (i <- array.indices.drop(1); j <- array(0).indices.drop(1))
      array(i)(j) = array(i - 1)(j) + array(i)(j - 1)
    array.last.last
  }

  /**
   * 优化一，使用两个数组，记录处理行的状态，空间复杂度为 2 * n
   * 执行用时 476 ms，内存消耗 48.9 MB
   */
  def uniquePaths2(m: Int, n: Int): Int = {
    var pre = new Array[Int](n)
    val cur = new Array[Int](n)
    for (i <- pre.indices) {
      pre(i) = 1
      cur(i) = 1
    }
    // 使用两个数组模拟矩阵的处理过程
    for (_ <- 1 until m) {
      for (j <- 1 until n) {
        cur(j) = pre(j) + cur(j - 1)
      }
      pre = cur
    }
    cur.last
  }

  /**
   *  优化3，使用单数组，空间复杂度 n
   *  执行用时 456 ms，内存消耗 48.8 MB
   */
  def uniquePaths3(m: Int, n: Int): Int = {
    val cur = new Array[Int](n)
    for (i <- cur.indices) {
      cur(i) = 1
    }
    for (_ <- 1 until m) {
      for (j <- 1 until n) {
        cur(j) += cur(j - 1)
      }
    }
    cur.last
  }

  println(uniquePaths1(7, 3))
  println(uniquePaths2(7, 3))
  println(uniquePaths3(7, 3))
}
