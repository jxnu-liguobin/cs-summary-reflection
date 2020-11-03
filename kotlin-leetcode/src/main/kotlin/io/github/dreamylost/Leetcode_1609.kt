/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.util.LinkedList

/**
 * 1609. 奇偶树
 * 如果一棵二叉树满足下述几个条件，则可以称为 奇偶树 ：
 * 1.二叉树根节点所在层下标为 0 ，根的子节点所在层下标为 1 ，根的孙节点所在层下标为 2 ，依此类推。
 * 2.偶数下标 层上的所有节点的值都是 奇 整数，从左到右按顺序 严格递增
 * 3.奇数下标 层上的所有节点的值都是 偶 整数，从左到右按顺序 严格递减
 *
 * 给你二叉树的根节点，如果二叉树为 奇偶树 ，则返回 true ，否则返回 false 。
 *
 * @author 梦境迷离
 * @version 1.0,2020/11/3
 */
class Leetcode_1609 {

    companion object {

        /**
         * 层序遍历
         * 564 ms,80.00%
         * 66.4 MB,60.00%
         */
        fun isEvenOddTree(root: TreeNode?): Boolean {
            if (root == null) return false
            val queue = LinkedList<TreeNode>()
            queue.addLast(root)
            var level = 0
            while (!queue.isEmpty()) {
                var len = queue.size
                var oddPre = Integer.MAX_VALUE
                var evenPre = 0
                while (len-- > 0) {
                    val node = queue.pollFirst()
                    if (node != null) {
                        // 奇数
                        if (level.and(1) == 1) {
                            if (node.`val`.and(1) == 1 || node.`val` >= oddPre) {
                                return false
                            } else {
                                oddPre = node.`val`
                            }
                        } else {
                            // 偶数
                            if (node.`val`.and(1) == 0 || node.`val` <= evenPre) {
                                return false
                            } else {
                                evenPre = node.`val`
                            }
                        }
                    }
                    if (node?.left != null) queue.addLast(node.left)
                    if (node?.right != null) queue.addLast(node.right)
                }
                level++
            }
            return true
        }
    }
}
