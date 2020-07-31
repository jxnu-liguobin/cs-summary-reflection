/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 给出不同面额的硬币以及一个总金额. 写一个方法来计算给出的总金额可以换取的最少的硬币数量. 如果已有硬币的任意组合均无法与总金额面额相等, 那么返回 -1.
  *
  * @author 梦境迷离
  * @time 2018年8月12日
  * @version v1.0
  */
object CoinChange extends App {

  def coinChange(coins: Array[Int], amount: Int): Int = {
    if (amount < 1) return 0
    val dp = new Array[Int](amount + 1)
    var sum = 1
    while (sum <= amount) { //硬币和
      var min = -1
      for (coin <- coins) { //每个硬币
        if (sum >= coin && dp(sum - coin) != -1) { //硬币必须小于sum,需要的硬币没被用过
          val temp = dp(sum - coin) + 1
          min = if (min < 0) temp else (math.min(temp, min)) //min=temp,或者min=temp,min
        }
      }
      dp(sum) = min
      sum = sum + 1
    }
    dp(amount)
  }
}
