/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 1122. 数组的相对排序
 * 给你两个数组，arr1 和 arr2，
 * 1.arr2 中的元素各不相同
 * 2.arr2 中的每个元素都出现在 arr1 中
 * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-11-14
 *  @version 1.0
 */
class Leetcode_1122 {
    companion object {

        /**
         * 188 ms,100.00%
         * 34.3 MB,57.14%
         * 计数排序
         * 1.记录arr1的元素的次数
         * 2.恢复arr2中存在的arr1的元素
         * 3.恢复arr2中不存在的arr1的元素
         */
        fun relativeSortArray(arr1: IntArray, arr2: IntArray): IntArray {
            val valuesCount = Array(1001) { 0 }
            arr1.forEach { valuesCount[it] += 1 }
            var idx = 0
            arr2.forEach { e ->
                while (valuesCount[e] > 0) {
                    valuesCount[e] -= 1
                    arr1[idx++] = e
                }
            }
            for (i in 0 until valuesCount.size)
                while (valuesCount[i] > 0) {
                    valuesCount[i] -= 1
                    arr1[idx++] = i
                }

            return arr1
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val arr1 = arrayOf(2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19)
            val arr2 = arrayOf(2, 1, 4, 3, 9, 6)
            val ret = relativeSortArray(arr1.toIntArray(), arr2.toIntArray())
            ret.forEach { print(it) }
        }
    }
}
