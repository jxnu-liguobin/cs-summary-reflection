package io.github.dreamylost

/**
  * 统计有序矩阵中的负数
  *
 * @author 梦境迷离
  * @since 2020-03-15
  * @version v1.0
  */
object Leetcode_1351 extends App {
  def countNegatives(grid: Array[Array[Int]]): Int = {
    grid.flatten.count(_ < 0) //应该将矩阵是排序的考虑进去，从右下角或左下角使用标记位
  }

  Console println countNegatives(
    Array(Array(4, 3, 2, -1), Array(3, 2, 1, -1), Array(1, 1, -1, -2), Array(-1, -1, -2, -3))
  )
}
