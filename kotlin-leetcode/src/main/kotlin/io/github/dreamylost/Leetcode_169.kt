/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 169. 多数元素
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于⌊ n/2 ⌋的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 * @author 梦境迷离
 * @version 1.0,2021/2/5
 */
object Leetcode_169 {
    // 摩尔选票法
    // 296 ms,25.86%
    // 40.8 MB,75.86%
    fun majorityElement(nums: IntArray): Int {
        var count = 1
        var maj = nums[0]
        for (idx in 1 until nums.size)
            if (maj == nums[1]) {
                count += 1
            } else {
                count -= 1
                if (count == 0) {
                    maj = nums[idx + 1]
                }
            }
        return maj
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = majorityElement(intArrayOf(2, 2, 1, 1, 1, 2, 2))
        print(ret)
    }
}
