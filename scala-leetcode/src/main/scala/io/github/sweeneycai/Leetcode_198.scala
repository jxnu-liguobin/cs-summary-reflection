/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
 * 198. 打家劫舍 (Easy)
 *
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，
 * 影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
 * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 *
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你
 * 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 */
object Leetcode_198 extends App {
  def rob(nums: Array[Int]): Int = {
    if (nums.isEmpty) 0
    else if (nums.length <= 2) nums.max
    else {
      val count = new Array[Int](nums.length)
      // 两个初始状态
      count(0) = nums(0)
      count(1) = nums(1)
      for (i <- nums.indices.drop(2)) {
        // 如果该位置大于2的时候，该位置的状态可以从前三个状态转化过来
        // f(n) = max(nums(i)+f(n-3), nums(i)+f(n-2), f(n-1))
        // 等于2的时候就必须从前两个状态转化过来
        if (i <= 2) count(i) = math.max(nums(i) + count(i - 2), count(i - 1))
        else count(i) = Array(nums(i) + count(i - 2), count(i - 1), nums(i) + count(i - 3)).max
      }
      count.last
    }
  }

  println(rob(Array(2, 1, 1, 2)))
}
