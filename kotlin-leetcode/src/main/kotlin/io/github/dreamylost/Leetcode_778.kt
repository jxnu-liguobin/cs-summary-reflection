/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.util.Comparator
import java.util.LinkedList
import kotlin.math.max

/**
 * 778. 水位上升的泳池中游泳
 * 在一个 N x N 的坐标方格 grid 中，每一个方格的值 grid[i][j] 表示在位置 (i,j) 的平台高度。
 * 现在开始下雨了。当时间为 t 时，此时雨水导致水池中任意位置的水位为 t 。你可以从一个平台游向四周相邻的任意一个平台，但是前提是此时水位必须同时淹没这两个平台。假定你可以瞬间移动无限距离，也就是默认在方格内部游动是不耗时的。当然，在你游泳的时候你必须待在坐标方格里面。
 * 你从坐标方格的左上平台 (0，0) 出发。最少耗时多久你才能到达坐标方格的右下平台 (N-1, N-1)？
 * @author 梦境迷离
 * @since 2021/1/30
 * @version 1.0
 */
object Leetcode_778 {

    /**
     * 312 ms,50.00%
     * 44.7 MB,50.00%
     */
    fun swimInWater(grid: Array<IntArray>): Int {
        val len = grid.size
        val total = grid.size * grid.size
        val nodes = mutableListOf<Triple<Int, Int, Int>>()
        for (i in 0 until len) {
            for (j in 0 until len) {
                if (i < len - 1) {
                    // 矩阵中(i, j)元素为第几个元素，由坐标 i * len + j 得出
                    nodes.add(Triple(i * len + j, (i + 1) * len + j, max(grid[i][j], grid[i + 1][j])))
                }
                if (j < len - 1) {
                    nodes.add(Triple(i * len + j, i * len + j + 1, max(grid[i][j], grid[i][j + 1])))
                }
            }
        }
        // 根据结点之间的权值进行排序
        nodes.sortWith(Comparator { e1, e2 -> e1.third - e2.third })
        var ans = 0
        val union = UnionFind(total)
        for (edge in nodes) {
            val x = edge.first
            val y = edge.second
            val rank = edge.third
            union.union(x, y)
            if (union.find(0) == union.find(total - 1)) {
                ans = rank
                break
            }
        }
        return ans
    }

    /**
     * 240 ms,50.00%
     * 41.2 MB,50.00%
     */
    fun swimInWater2(grid: Array<IntArray>): Int {
        val n = grid.size
        var left = 0
        var right = n * n - 1
        // 这是本问题具有的 单调性。因此可以使用二分查找定位到最短等待时间。具体来说：在区间 [0, N * N - 1] 里猜一个整数，针对这个整数从起点（左上角）开始做一次深度优先遍历或者广度优先遍历。
        // 当小于等于该数值时，如果存在一条从左上角到右下角的路径，说明答案可能是这个数值，也可能更小；
        // 当小于等于该数值时，如果不存在一条从左上角到右下角的路径，说明答案一定比这个数值更大。
        while (left < right) {
            val mid = (left + right) / 2
            if (grid[0][0] <= mid && bfs(grid, mid)) {
                right = mid
            } else {
                left = mid + 1
            }
        }
        return left
    }

    // 使用广度优先遍历得到从 (x, y) 开始向四个方向的所有小于等于 threshold 且与 (x, y) 连通的结点
    private fun bfs(grid: Array<IntArray>, threshold: Int): Boolean {
        val directions = arrayOf(intArrayOf(0, 1), intArrayOf(0, -1), intArrayOf(1, 0), intArrayOf(-1, 0))
        val n = grid.size
        fun inArea(x: Int, y: Int): Boolean {
            return x in 0 until n && y >= 0 && y < n
        }

        val queue = LinkedList<IntArray>()
        queue.offer(intArrayOf(0, 0))
        val visited = Array(n) { BooleanArray(n) }
        visited[0][0] = true
        while (!queue.isEmpty()) {
            val front = queue.poll()
            val x = front[0]
            val y = front[1]
            for (direction in directions) {
                val newX = x + direction[0]
                val newY = y + direction[1]
                if (inArea(newX, newY) && !visited[newX][newY] && grid[newX][newY] <= threshold) {
                    if (newX == n - 1 && newY == n - 1) {
                        return true
                    }
                    queue.offer(intArrayOf(newX, newY))
                    visited[newX][newY] = true
                }
            }
        }
        return false
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val grid = arrayOf(intArrayOf(0, 1, 2, 3, 4), intArrayOf(24, 23, 22, 21, 5), intArrayOf(12, 13, 14, 15, 16), intArrayOf(11, 17, 18, 19, 20), intArrayOf(10, 9, 8, 7, 6))
        val ret = swimInWater(grid)
        print(ret)
    }
}
