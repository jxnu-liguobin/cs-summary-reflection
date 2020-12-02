/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 976. 三角形的最大周长
 * 给定由一些正数（代表长度）组成的数组 A，返回由其中三个长度组成的、面积不为零的三角形的最大周长。
 * 如果不能形成任何面积不为零的三角形，返回 0。
 * @author 梦境迷离
 * @since 2020/11/29
 * @version 1.0
 */
class Leetcode_976 {
    companion object {
        /**
         * 两边之和大于第三边，两边之差小于第三边。周长最大时，第三边需要为最大边
         * 388 ms,14.29%
         * 37.9 MB,14.29%

         */
        fun largestPerimeter(A: IntArray): Int {
            // sortedWith与sortWith不同，sortedWith不会更改原数据，而是返回排序后的数据
            val arr = A.sortedWith(Comparator { o1, o2 -> o1 - o2 })
            for (index in arr.size - 1 downTo 2) {
                if (arr[index - 1] + arr[index - 2] > arr[index]) {
                    return arr[index - 1] + arr[index - 2] + arr[index]
                }
            }
            return 0
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = largestPerimeter(arrayOf(3, 6, 2, 3).toIntArray())
            println(ret)
        }
    }
}
