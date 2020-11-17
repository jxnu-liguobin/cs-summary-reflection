/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import kotlin.math.abs

/**
 * 1030. 距离顺序排列矩阵单元格
 * 给出 R 行 C 列的矩阵，其中的单元格的整数坐标为 (r, c)，满足 0 <= r < R 且 0 <= c < C。
 * 另外，我们在该矩阵中给出了一个坐标为 (r0, c0) 的单元格。
 * 返回矩阵中的所有单元格的坐标，并按到 (r0, c0) 的距离从最小到最大的顺序排，
 * 其中，两单元格(r1, c1) 和 (r2, c2) 之间的距离是曼哈顿距离，|r1 - r2| + |c1 - c2|。（你可以按任何满足此条件的顺序返回答案。）
 * 示例：输入：R = 1, C = 2, r0 = 0, c0 = 0
 * 输出：[[0,0],[0,1]]
 * 解释：从 (r0, c0) 到其他单元格的距离为：[0,1]
 * @author 梦境迷离
 * @version 1.0,2020/11/17
 */
class Leetcode_1030 {
    companion object {
        /**
         * 468 ms,25.00%
         * 40.7 MB,25.00%
         */
        fun allCellsDistOrder(R: Int, C: Int, r0: Int, c0: Int): Array<IntArray> {
            val ans: MutableList<IntArray> = mutableListOf()
            for (r in 0 until R) for (c in 0 until C) {
                ans.add(arrayOf(r, c).toIntArray())
            }
            val calcute = { a: IntArray ->
                abs(a[0] - r0) + abs(a[1] - c0)
            }
            ans.sortWith(
                Comparator { o1, o2 ->
                    calcute(o1).compareTo(calcute(o2))
                }
            )
            return ans.toTypedArray()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = allCellsDistOrder(1, 2, 0, 0)
            ret.forEach {
                it.forEach {
                    print(it)
                }
                println()
            }
        }
    }
}
