/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 并查集模板
 */
class UnionFind(
    var size: Int // 连通量大小
) {
    private var maxCount: Int = 0 // 可删除的最大数量
    private var parent: IntArray = IntArray(size + 1) { i -> i } // 父结点指向数组
    private var rank: IntArray = IntArray(size + 1) { 1 } // 秩数组

    /**
     * 寻找父结点函数
     * @param index
     * @return
     */
    fun find(index: Int): Int {
        if (index != parent[index]) {
            parent[index] = find(parent[index])
        }
        return parent[index]
    }

    /**
     * 合并集合函数
     * @param index1
     * @param index2
     * @return
     */
    fun union(index1: Int, index2: Int): Boolean {
        val root1 = find(index1)
        val root2 = find(index2)
        if (root1 == root2) {
            maxCount++
            return true
        }
        when {
            rank[root1] > rank[root2] -> {
                parent[root2] = root1 // 合并集合
                rank[root1] = rank[root1] + rank[root2] // 合并秩
            }
            rank[root1] < rank[root2] -> {
                parent[root1] = root2
                rank[root2] = rank[root2] + rank[root1]
            }
            else -> {
                parent[root2] = root1
                rank[root1] = rank[root1] + rank[root2]
            }
        }
        return false
    }

    /**
     * 获取可删除的最大数量方法
     * @return
     */
    fun getDeleteCount(): Int {
        val root = find(1)
        val ranks = rank[root]
        return if (ranks == size) maxCount else -1
    }
}
