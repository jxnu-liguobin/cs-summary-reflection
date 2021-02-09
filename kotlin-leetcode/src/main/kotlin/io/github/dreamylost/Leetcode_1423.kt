/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import kotlin.math.max

/**
 * 1423. 可获得的最大点数
 * 几张卡牌 排成一行，每张卡牌都有一个对应的点数。点数由整数数组 cardPoints 给出。
 * 每次行动，你可以从行的开头或者末尾拿一张卡牌，最终你必须正好拿 k 张卡牌。
 * 你的点数就是你拿到手中的所有卡牌的点数之和。
 * 给你一个整数数组 cardPoints 和整数 k，请你返回可以获得的最大点数。
 * @author 梦境迷离
 * @version 1.0,2021/2/6
 */
object Leetcode_1423 {
    /**
     * 352 ms,100.00%
     * 44.3 MB,100.00%
     */
    fun maxScore(cardPoints: IntArray, k: Int): Int {
        var temp = 0
        for (i in 0 until k) {
            temp += cardPoints[i]
        }
        var max: Int = temp
        for (i in 0 until k) {
            temp += cardPoints[cardPoints.size - 1 - i] - cardPoints[k - 1 - i]
            max = max(max, temp)
        }
        return max
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = maxScore(intArrayOf(1, 2, 3, 4, 5, 6, 1), 3)
        print(ret)
    }
}
