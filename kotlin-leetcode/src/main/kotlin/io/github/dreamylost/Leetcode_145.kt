/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.util.*

/**
 * 145. 二叉树的后序遍历
 *
 * 给定一个二叉树，返回它的 后序 遍历。
 * @author 梦境迷离
 * @version 1.0,2020/9/1
 */
class Leetcode_145 {
    companion object {
        /**
         * 204 ms,37.74%
         * 34.4 MB,20.00%
         * 后续就是前序在遍历时将子树顺序调整后的结果的倒序
         */
        fun postorderTraversal(root: TreeNode?): List<Int> {
            val ret = mutableListOf<Int>()
            if (root == null) return ret
            val stack = LinkedList<TreeNode>()
            stack.addLast(root)
            while (!stack.isEmpty()) {
                val node = stack.pollLast()
                ret.add(node?.`val`!!)
                if (node.left != null) stack.addLast(node.left)
                if (node.right != null) stack.addLast(node.right)
            }
            ret.reverse()
            return ret
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = postorderTraversal(TreeNodeData.treeNode_3())
            println(ret)
        }
    }
}
