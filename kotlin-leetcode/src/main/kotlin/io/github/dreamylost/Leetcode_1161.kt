/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.util.*

/**
 * 1161. 最大层内元素和
 *
 * 给你一个二叉树的根节点 root。设根节点位于二叉树的第 1 层，而根节点的子节点位于第 2 层，依此类推。
 * 请你找出层内元素之和 最大 的那几层（可能只有一层）的层号，并返回其中 最小 的那个。
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-10-02
 *  @version 1.0
 */
class Leetcode_1161 {

    /**
     * 432 ms,100.00%
     * 42.6 MB,50.00%
     */
    fun maxLevelSum(root: TreeNode?): Int {
        if (root == null) return 0
        val queue = LinkedList<TreeNode>()
        queue.addLast(root)
        var ret = root.`val`
        var level = 1
        var maxLevel = 1
        while (!queue.isEmpty()) {
            val len = queue.size
            var sum = 0
            for (i in 0 until len) {
                val node = queue.pollFirst()
                sum += node?.`val` ?: 0
                if (node?.left != null) queue.addLast(node.left)
                if (node?.right != null) queue.addLast(node.right)
            }
            if (sum > ret) {
                ret = sum
                maxLevel = level
            }
            level++
        }
        return maxLevel
    }
}
