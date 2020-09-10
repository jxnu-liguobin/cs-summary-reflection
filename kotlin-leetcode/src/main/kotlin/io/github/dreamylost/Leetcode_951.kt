/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 951. 翻转等价二叉树
 *
 * 我们可以为二叉树 T 定义一个翻转操作，如下所示：选择任意节点，然后交换它的左子树和右子树。
 * 只要经过一定次数的翻转操作后，能使 X 等于 Y，我们就称二叉树 X 翻转等价于二叉树 Y。
 * 编写一个判断两个二叉树是否是翻转等价的函数。这些树由根节点 root1 和 root2 给出。
 * @author liguobin@growingio.com
 * @version 1.0,2020/9/10
 */
class Leetcode_951 {

    companion object {

        /**
         * 204 ms,25.00%
         * 32.5 MB,50.00%
         */
        fun flipEquiv(root1: TreeNode?, root2: TreeNode?): Boolean {
            if (root1 == null && root2 == null) return true
            if (root1 == null || root2 == null) return false
            if (root1.`val` != root2.`val`) return false
            if (root1.`val` == root2.`val`) {
                // 对应比较+交叉比较(因为可以翻转任意次)
                return flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right) ||
                    (flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left))
            }
            return false
        }
    }
}
