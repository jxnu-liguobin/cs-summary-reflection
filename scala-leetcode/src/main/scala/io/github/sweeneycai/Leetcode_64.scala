/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 64. 最小路径和 (Medium)
  *
  * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
  *
  * 说明：每次只能向下或者向右移动一步。
  */
object Leetcode_64 {

  /**
    * 递推公式：
    * f(i)(j) = grid(i)(j) + min(grid(i - 1)(j), grid(i)(j - 1))
    */
  def minPathSum(grid: Array[Array[Int]]): Int = {
    if (grid.isEmpty) return 0
    val copy = grid.clone() // 不修改原数组
    for (i <- 1 until copy.length) {
      copy(i)(0) += copy(i - 1)(0)
    }
    for (j <- 1 until copy(0).length) {
      copy(0)(j) += copy(0)(j - 1)
    }
    for (i <- 1 until copy.length; j <- 1 until copy(0).length) {
      copy(i)(j) += math.min(copy(i - 1)(j), copy(i)(j - 1))
    }
    copy.last.last
  }

  def main(args: Array[String]): Unit = {
    println(
      minPathSum(
        Array(
          Array(1, 3, 1),
          Array(1, 5, 6),
          Array(4, 2, 1)
        )
      )
    )
  }
}
