/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.util.*

/**
 * 剑指 Offer 32 - III. 从上到下打印二叉树 III
 *
 * 请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
 *
 * @author 梦境迷离
 * @version 1.0,2020/8/19
 */
object Leetcode_JZ_32_2 {

    //236 ms ,78.57%
    //34.6 MB ,85.71%
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) return emptyList()
        val ret = mutableListOf<List<Int>>()
        val queue = LinkedList<TreeNode>()
        queue.addLast(root)
        var level = 0
        while (!queue.isEmpty()) {
            val size = queue.size
            level++
            val levelNode = LinkedList<Int>()
            for (i in 0 until size) {
                val node = queue.pollFirst()
                if (node != null) {
                    //使用数组会导致头插法比较慢
                    //这样占内存大一点，但是速度和直接翻转差不多
                    if (level.and(1) == 0) {
                        levelNode.addFirst(node.`val`)
                    } else {
                        levelNode.addLast(node.`val`)
                    }
                }
                if (node?.left != null) queue.addLast(node.left)
                if (node?.right != null) queue.addLast(node.right)
            }
            ret.add(levelNode)
        }
        return ret
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = levelOrder(TreeNodeData.treeNode_10())
        println(ret)
    }
}
