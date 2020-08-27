/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 701. 二叉搜索树中的插入操作
 *
 * 给定二叉搜索树（BST）的根节点和要插入树中的值，将值插入二叉搜索树。 返回插入后二叉搜索树的根节点。 保证原始二叉搜索树中不存在新值。
 * 注意，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。 你可以返回任意有效的结果。
 * @author 梦境迷离
 * @version 1.0,2020/8/27
 */
class Leetcode_701 {
    companion object {

        /**
         * 416 ms,23.08%
         * 35.4 MB,100.00%
         */
        fun insertIntoBST(root: TreeNode?, `val`: Int): TreeNode? {
            val newNode = TreeNode(`val`)
            if (root == null) return newNode
            if (`val` < root.`val`) {
                root.left = insertIntoBST(root.left, `val`)
            } else {
                root.right = insertIntoBST(root.right, `val`)
            }

            return root
        }

        @JvmStatic
        fun main(args: Array<String>) {
        }
    }
}
