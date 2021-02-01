/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 136. 只出现一次的数字
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * @author 梦境迷离
 * @since 2021/1/31
 * @version 1.0
 */
object Leetcode_136 {
    /**
     * 252 ms,82.98%,
     * 37.2 MB,37.24%
     */
    fun singleNumber(nums: IntArray): Int {
        var ret = 0
        // 任何一个数字异或它自己都等于0
        for (num in nums) {
            ret = ret xor num
        }
        return ret
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = singleNumber(intArrayOf(4, 1, 2, 1, 2))
        print(ret)
    }
}
