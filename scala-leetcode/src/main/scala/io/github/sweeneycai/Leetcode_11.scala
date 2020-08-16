/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 11. 盛最多水的容器 (Medium)
  *
  * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点(i,ai) 。
  * 在坐标内画 n 条垂直线，垂直线 i的两个端点分别为(i,ai) 和 (i, 0)。
  * 找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。
  *
  * 说明：你不能倾斜容器，且n的值至少为 2.
  */
object Leetcode_11 extends App {
  def maxArea(height: Array[Int]): Int = {
    var left = 0
    var right = height.length - 1
    var res = 0
    while (left < right) {
      res = math.max(math.min(height(left), height(right)) * (right - left), res)
      if (height(left) > height(right))
        right -= 1
      else
        left += 1
    }
    res
  }

  println(maxArea(Array(1, 8, 6, 2, 5, 4, 8, 3, 7)))
}
