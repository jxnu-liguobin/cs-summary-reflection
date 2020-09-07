/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import kotlin.math.max

/**
 * 1123. 最深叶节点的最近公共祖先
 *
 * 给你一个有根节点的二叉树，找到它最深的叶节点的最近公共祖先。
 * 回想一下：叶节点 是二叉树中没有子节点的节点
 * 树的根节点的 深度 为 0，如果某一节点的深度为 d，那它的子节点的深度就是 d+1
 * 如果我们假定 A 是一组节点 S 的 最近公共祖先，S 中的每个节点都在以 A 为根节点的子树中，且 A 的深度达到此条件下可能的最大值。
 * @author liguobin@growingio.com
 * @version 1.0,2020/9/7
 */
class Leetcode_1123 {

    companion object {

        /**
         * 248 ms,50.00%
         * 36.2 MB,50.00%
         */
        fun lcaDeepestLeaves(root: TreeNode?): TreeNode? {
            if (root == null) return null
            val left = getMaxDepth(root.left)
            val right = getMaxDepth(root.right)
            return if (left < right) {
                lcaDeepestLeaves(root.right)
            } else if (left == right) root else {
                lcaDeepestLeaves(root.left)
            }
        }

        private fun getMaxDepth(root: TreeNode?): Int {
            if (root == null) return 0
            return max(getMaxDepth(root.left), getMaxDepth(root.right)) + 1
        }
    }
}
