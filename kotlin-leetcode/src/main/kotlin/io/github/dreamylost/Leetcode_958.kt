/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.util.*

/**
 * 958. 二叉树的完全性检验
 *
 *  给定一个二叉树，确定它是否是一个完全二叉树。
 *
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-10-07
 *  @version 1.0
 */
class Leetcode_958 {

    companion object {
        /**
         * 直接根据完全二叉树的条件来判断
         * 188 ms,75.00%
         * 33.8 MB,33.33%
         */
        fun isCompleteTree(root: TreeNode?): Boolean {
            if (root == null) return false
            val queue = LinkedList<TreeNode>()
            queue.addLast(root)
            var flag = false
            while (queue.isNotEmpty()) {
                val node = queue.pollFirst()
                if (node?.left == null && node?.right != null || (flag && (node?.left != null || node?.right != null))) {
                    return false
                }
                if (node?.left == null || node.right == null) {
                    flag = true
                }
                if (node?.left != null) {
                    queue.addLast(node.left)
                }
                if (node?.right != null) {
                    queue.addLast(node.right)
                }
            }
            return true
        }

        /**
         * 广度遍历二叉树，当出现 null 值时停止遍历，如果此时还有没有遍历到的结点，说明该树非完全二叉树
         * 208 ms,25.00%
         * 33.6 MB,66.67%
         */
        fun isCompleteTree_(root: TreeNode?): Boolean {
            if (root == null) return false
            val queue = LinkedList<TreeNode>()
            queue.addLast(root)
            var node: TreeNode? = TreeNode(-1)
            while (node != null) {
                node = queue.pollFirst()
                queue.addLast(node?.left)
                queue.addLast(node?.right)
            }
            while (queue.isNotEmpty()) {
                if (queue.pollLast() != null) {
                    return false
                }
            }
            return true
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = isCompleteTree(TreeNodeData.treeNode_3())
            println(ret)
        }
    }
}
