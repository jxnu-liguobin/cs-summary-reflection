/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

/**
 * 164. 最大间距
 *
 * 给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
 * 如果数组元素个数小于 2，则返回 0。
 * @author 梦境迷离
 * @version 1.0,2020/11/26
 */
class Leetcode_164 {

    companion object {
        /**
         * 桶排序（不需要桶间的排序）
         * 208 ms,100.00%
         * 35.7 MB,50.00%
         */
        fun maximumGap(nums: IntArray): Int {
            if (nums.size < 2) return 0
            val len = nums.size
            var max = Int.MIN_VALUE
            var min = Int.MAX_VALUE
            for (i in nums.indices) {
                max = max(max, nums[i])
                min = min(min, nums[i])
            }
            if (max - min == 0) return 0
            val bucketMin = Array(len - 1) { Int.MAX_VALUE }
            val bucketMax = Array(len - 1) { Int.MIN_VALUE }
            val interval = ceil((max - min).toDouble() / (len - 1)).toInt()
            for (i in nums.indices) {
                val index: Int = (nums[i] - min) / interval
                if (nums[i] == min || nums[i] == max) continue
                bucketMax[index] = max(bucketMax[index], nums[i])
                bucketMin[index] = min(bucketMin[index], nums[i])
            }
            var ret = 0
            var preBucketMax = min
            for (i in 0 until len - 1) {
                if (bucketMax[i] == Int.MIN_VALUE) continue
                ret = max(bucketMin[i] - preBucketMax, ret)
                preBucketMax = bucketMax[i]
            }
            return max(ret, max - preBucketMax)
        }

        /**
         * 基数排序
         * 240 ms,50.00%
         * 34.4 MB,50.00%
         */
        fun maximumGap_(nums: IntArray): Int {
            if (nums.size < 2) return 0
            var max = nums[0]
            for (anArr in nums) {
                if (anArr > max) {
                    max = anArr
                }
            }
            // 分别针对数值的个位、十位、百分位等进行排序
            var exp = 1
            while (max / exp > 0) {
                // 存储待排元素的临时数组
                val temp = IntArray(nums.size)
                val buckets = IntArray(10)
                // 统计每个桶中的个数
                for (value in nums) {
                    buckets[value / exp % 10]++
                }
                // 计算排序后数字的下标
                for (i in 1..9) {
                    buckets[i] += buckets[i - 1]
                }
                // 排序
                for (i in nums.indices.reversed()) {
                    val index = nums[i] / exp % 10
                    temp[buckets[index] - 1] = nums[i]
                    buckets[index]--
                }
                System.arraycopy(temp, 0, nums, 0, nums.size)
                exp *= 10
            }
            var res = -1
            for (i in 1 until nums.size) {
                res = max(res, nums[i] - nums[i - 1])
            }
            return res
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val arr = arrayOf(2, 99999999)
            val ret = maximumGap(arr.toIntArray())
            val ret2 = maximumGap_(arr.toIntArray())
            println(ret)
            println(ret2)
        }
    }
}
