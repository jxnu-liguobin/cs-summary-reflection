/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import kotlin.math.max

/**
 * 865. 具有所有最深结点的最小子树
 *
 * 给定一个根为 root 的二叉树，每个结点的深度是它到根的最短距离。
 * 如果一个结点在整个树的任意结点之间具有最大的深度，则该结点是最深的。
 * 一个结点的子树是该结点加上它的所有后代的集合。返回能满足“以该结点为根的子树中包含所有最深的结点”这一条件的具有最大深度的结点。
 *
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-09-26
 *  @version 1.0
 */
class Leetcode_865 {

    companion object {

        /**
         * 题目读懂就很简单了，一棵树如果左右子树高度相同则根节点就是最小子树，如果不是一样高，则最小子树一定在高的一边，向高的一边递归就行了
         * 200 ms,100.00%
         * 34.4 MB,100.00%
         */
        fun subtreeWithAllDeepest(root: TreeNode?): TreeNode? {
            fun height(r: TreeNode?): Int {
                return if (r == null) 0 else max(height(r.left), height(r.right)) + 1
            }
            if (root == null) return null
            val left = height(root.left)
            val right = height(root.right)
            return when {
                left == right -> root
                left > right -> subtreeWithAllDeepest(root.left)
                else -> subtreeWithAllDeepest(root.right)
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
        }
    }
}
