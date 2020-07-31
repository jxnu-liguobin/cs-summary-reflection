/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  *  假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
  *  每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
  *  注意：给定 n 是一个正整数。
  *
  * @author 梦境迷离
  * @time 2018年8月14日
  * @version v1.0
  */
object Leetcode_70 extends App {

  print(climbStairs(2))

  def climbStairs(number: Int): Int = {
    if (number <= 0)
      return 0
    if (number == 1 || number == 2)
      return number
    var (first, second, third) = (1, 2, 0)
    for (i <- 3 to number) {
      third = first + second
      first = second
      second = third
    }
    third
  }
}
