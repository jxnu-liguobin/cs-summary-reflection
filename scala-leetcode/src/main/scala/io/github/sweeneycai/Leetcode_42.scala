/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
 * 42. 接雨水 (Hard)
 *
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，
 * 计算按此排列的柱子，下雨之后能接多少雨水。
 */
object Leetcode_42 extends App {

  /**
   * 遍历两次，求出每个节点当前左边的最大值和右边的最大值，每个节点的储水量为：
   * f(n) = min(leftMax, rightMax) - h(n)
   * 最后遍历一次即可。
   */
  def trap(height: Array[Int]): Int = {
    if (height.isEmpty) return 0
    // 该点左边节点的最大值
    val leftMax = height
      .foldLeft(Array[Int](0)) { (b, a) =>
        if (a < b.last) b :+ b.last
        else b :+ a
      }
      .drop(1)
    // 该点右边节点的最大值
    val rightMax = height
      .foldRight(Array[Int](0)) { (a, b) =>
        if (a < b.head) b.head +: b
        else a +: b
      }
      .dropRight(1)
    var res = 0
    for (i <- height.indices) {
      res += math.min(leftMax(i), rightMax(i)) - height(i)
    }
    res
  }

  println(trap(Array(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)))
}
