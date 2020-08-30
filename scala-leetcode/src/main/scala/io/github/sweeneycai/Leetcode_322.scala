/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable

/**
  * 322. 零钱兑换 (Medium)
  *
  * 给定不同面额的硬币 coins 和一个总金额 amount。
  * 编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
  * 如果没有任何一种硬币组合能组成总金额，返回 -1。
  *
  * @see <a href="https://leetcode-cn.com/problems/coin-change/">leetcode-cn.com</a>
  */
object Leetcode_322 extends App {

  /**
    * 未优化的递归解法
    * 超出内存限制
    */
  def coinChange1(coins: Array[Int], amount: Int): Int = {
    def dp(n: Int): Int = {
      if (n == 0) 0
      else if (n < 0) -1
      else {
        var res = Int.MaxValue
        for (coin <- coins) {
          // 确保子问题有解
          if (dp(n - coin) != -1) {
            res = math.min(res, 1 + dp(n - coin))
          }
        }
        if (res == Int.MaxValue) -1
        else res
      }
    }

    dp(amount)
  }

  /**
    * 自顶向下的记忆递归解法
    * 820 ms, 51.1 MB
    */
  def coinChange2(coins: Array[Int], amount: Int): Int = {
    // 将查找过的元素记录下来
    val lookup: mutable.HashMap[Int, Int] = mutable.HashMap.empty

    def dp(n: Int): Int = {
      if (n == 0) 0
      else if (n < 0) -1
      else if (lookup.contains(n)) lookup(n)
      else {
        var res = Int.MaxValue
        for (coin <- coins) {
          if (dp(n - coin) != -1)
            res = math.min(res, dp(n - coin) + 1)
        }
        // 向查找表中添加已经计算出结果的元素
        if (res != Int.MaxValue) {
          lookup.+=(n -> res)
          res
        } else {
          lookup.+=(n -> -1)
          res
        }
      }
    }

    dp(amount)
  }

  /**
    * 自底向上的动态规划
    * 628 ms, 51.1 MB
    *
    * 最优子结构：每一个amount对应的最小零钱数
    * dp方程：dp[i] = min(dp[i], dp[i-coin] + 1)
    * 即 零钱数 11 的状态可以由 10 的状态加一得到，
    * 穷举coins即可得到最优解。
    */
  def coinChange3(coins: Array[Int], amount: Int): Int = {
    val dp: Array[Int] = Array.fill(amount + 1)(amount + 1)
    dp(0) = 0
    for (i <- dp.indices.drop(0)) {
      for (coin <- coins) {
        if (i - coin >= 0) {
          dp(i) = math.min(dp(i - coin) + 1, dp(i))
        }
      }
    }
    if (dp.last == amount + 1) {
      -1
    } else {
      dp.last
    }
  }

  println(coinChange2(Array(419, 408), 1000))
  println(coinChange3(Array(1, 5, 5), 11))
}
