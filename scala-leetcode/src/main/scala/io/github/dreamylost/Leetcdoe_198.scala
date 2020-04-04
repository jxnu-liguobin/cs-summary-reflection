package io.github.dreamylost

/**
  * 198. 打家劫舍
  * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
  * 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。
  *
  * @date: 2018-08-16
  * @author: 梦境迷离
  */
object Leetcdoe_198 extends App {

  println(rob(Array(2, 7, 9, 3, 1)))

  def rob(nums: Array[Int]): Int = {

    if (nums == null || nums.length == 0) return 0
    if (nums.length == 1) return nums(0)
    val memo = new Array[Int](nums.length)
    memo(0) = nums(0)
    memo(1) = math.max(nums(0), nums(1))
    for (i <- 2 until nums.length) {
      memo(i) = math.max(memo(i - 1), memo(i - 2) + nums(i))
    }
    memo(nums.length - 1)
  }

}
