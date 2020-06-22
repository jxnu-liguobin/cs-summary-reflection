/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 剑指 Offer 28. 对称的二叉树
 *
 * 请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 * @author liguobin@growingio.com
 * @version 1.0,2020/6/22
 */
object Leetcode_JZ_28 {

    // 256 ms,17.65%
    // 35.5 MB,100.00%
    fun isSymmetric(root: TreeNode?): Boolean {
        fun helper(l: TreeNode?, r: TreeNode?): Boolean {
            if (l == null && r == null) {
                return true
            }
            if (l == null || r == null) {
                return false
            }
            if (l.`val` != r.`val`) {
                return false
            }
            return helper(l.left, r.right) && helper(l.right, r.left)
        }
        if (root == null) return true
        return helper(root.left, root.right)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = isSymmetric(TreeNodeData.treeNode_3())
        print(ret)
    }
}
