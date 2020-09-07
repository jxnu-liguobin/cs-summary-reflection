/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 1261. 在受污染的二叉树中查找元素
 *
 * 给出一个满足下述规则的二叉树：

root.val == 0
如果 treeNode.val == x 且 treeNode.left != null，那么 treeNode.left.val == 2 * x + 1
如果 treeNode.val == x 且 treeNode.right != null，那么 treeNode.right.val == 2 * x + 2
现在这个二叉树受到「污染」，所有的 treeNode.val 都变成了 -1。

请你先还原二叉树，然后实现 FindElements 类：

FindElements(TreeNode* root) 用受污染的二叉树初始化对象，你需要先把它还原。
bool find(int target) 判断目标值 target 是否存在于还原后的二叉树中并返回结果。
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-09-05
 *  @version 1.0
 */
class Leetcode_1261 {
    companion object {
        /**
         * 1396 ms,25.00%
         * 57 MB,25.00%
         */
        class FindElements(val root: TreeNode?) {

            fun restoreTree(r: TreeNode?) {
                if (r == null) return
                val rootVal = r.`val`
                if (r.left != null) {
                    r.left!!.`val` = 2 * rootVal + 1
                }
                if (r.right != null) {
                    r.right!!.`val` = 2 * rootVal + 2
                }
                restoreTree(r.left)
                restoreTree(r.right)
            }

            init {
                root?.`val` = 0
                restoreTree(root)
            }

            fun find(target: Int): Boolean {
                fun findWithTree(r: TreeNode?, t: Int): Boolean {
                    return if (r == null) false else if (r.`val` == target) true else findWithTree(r.left, t) || findWithTree(r.right, t)
                }
                return findWithTree(root, target)
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val t1 = TreeNode(-1)
            val t2 = TreeNode(-1)
            t1.right = t2
            val obj = FindElements(t1)
            val param_1 = obj.find(2)
            println(param_1)
        }
    }
}
