/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 724. 寻找数组的中心索引
 * 给你一个整数数组 nums，请编写一个能够返回数组 “中心索引” 的方法。
 * 数组 中心索引 是数组的一个索引，其左侧所有元素相加的和等于右侧所有元素相加的和。
 * 如果数组不存在中心索引，返回 -1 。如果数组有多个中心索引，应该返回最靠近左边的那一个。
 * 注意：中心索引可能出现在数组的两端。
 * @author 梦境迷离
 * @version 1.0,2021/1/28
 */
object Leetcode_724 {

    /**
     * 292 ms,81.48%
     * 38.9 MB,70.37%
     */
    fun pivotIndex(nums: IntArray): Int {
        val sum = nums.sum()
        var cnt = 0
        for (i in nums.indices) {
            if (cnt == sum - nums[i] - cnt) {
                return i
            }
            cnt += nums[i]
        }
        return -1
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = pivotIndex(arrayOf(2, 1, -1).toIntArray())
        val ret2 = pivotIndex(arrayOf(0, 0, 0, 0, 1).toIntArray())
        val ret3 = pivotIndex(arrayOf(1, 7, 3, 6, 5, 6).toIntArray())
        println(ret)
        println(ret2)
        println(ret3)
    }
}
