/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 814. 二叉树剪枝
 *
 * 给定二叉树根结点 root ，此外树的每个结点的值要么是 0，要么是 1。返回移除了所有不包含 1 的子树的原二叉树。( 节点 X 的子树为 X 本身，以及所有 X 的后代。)
 * @author 梦境迷离
 * @version 1.0,2020/8/27
 */
class Leetcode_814 {
    companion object {

        /**
         * 232 ms,14.29%
         * 32.4 MB,100.00%
         */
        fun pruneTree(root: TreeNode?): TreeNode? {
            if (root == null) return null
            root.left = pruneTree(root.left)
            root.right = pruneTree(root.right)
            // 后续遍历，叶子置空
            if (root.`val` == 0 && root.left == null && root.right == null) {
                return null
            }
            return root
        }

        @JvmStatic
        fun main(args: Array<String>) {
        }
    }
}
