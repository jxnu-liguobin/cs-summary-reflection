package io.github.dreamylost

/**
  * 746. Min Cost Climbing Stairs
  *
  * @date: 2018-08-20
  * @author: liguobin
  */
object Leetcode_746 extends App {

  val ret = minCostClimbingStairs(Array(1, 100, 1, 1, 1, 100, 1, 1, 100, 1))
  print(ret)

  def minCostClimbingStairs(cost: Array[Int]): Int = {

    if (cost.length == 1) return 0

    var s1 = cost(cost.length - 1)
    var s2 = 0
    var currval = 0
    // S1和S2跟踪下一步最小成本（一步两步）
    // 从倒数第二步开始到首步
    var i = cost.length - 2
    while (i >= 0) {
      currval = cost(i) + math.min(s1, s2)
      s2 = s1
      s1 = currval
      i -= 1
    }
    return math.min(s1, s2) // 1步或者两步

  }

}
