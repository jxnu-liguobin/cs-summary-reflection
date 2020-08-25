/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 684. 冗余连接
 *
 * 在本问题中, 树指的是一个连通且无环的无向图。
 * 输入一个图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。附加的边的两个顶点包含在1到N中间，这条附加的边不属于树中已存在的边。
 * 结果图是一个以边组成的二维数组。每一个边的元素是一对[u, v] ，满足 u < v，表示连接顶点u 和v的无向图的边。
 * 返回一条可以删去的边，使得结果图是一个有着N个节点的树。如果有多个答案，则返回二维数组中最后出现的边。答案边 [u, v] 应满足相同的格式 u < v。
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-08-25
 *  @version 1.0
 */
class Leetcode_684 {
    companion object {

        /**
         * 并查集
         * 240 ms,28.57%
         * 36.7 MB,50.00%
         */
        fun findRedundantConnection(edges: Array<IntArray>): IntArray {
            // 1.默认父节点设置为自己
            val parent = Array(1001) { i -> i }
            // 2.查找父节点
            fun findParent(x: Int): Int {
                return if (x == parent[x]) x else {
                    parent[x] = findParent(parent[x])
                    parent[x]
                }
            }

            // 3.合并两个节点，先找到两个集合的代表元素，然后将前者的父节点设为后者即可
            fun merge(x: Int, y: Int) {
                val a = findParent(x)
                val b = findParent(y)
                if (a != b) {
                    parent[a] = b
                }
            }

            val ret = Array(2) { 0 }
            for (i in 0 until edges.size) {
                val from = edges[i][0]
                val to = edges[i][1]
                if (findParent(from) != findParent(to)) {
                    merge(to, from)
                } else {
                    ret[0] = from
                    ret[1] = to
                }
            }
            return ret.toIntArray()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = findRedundantConnection(
                arrayListOf(
                    arrayListOf(1, 2).toIntArray(), arrayListOf(1, 3).toIntArray(),
                    arrayListOf(2, 3).toIntArray()
                ).toTypedArray()
            )
            ret.forEach { print(it) }
        }
    }
}
