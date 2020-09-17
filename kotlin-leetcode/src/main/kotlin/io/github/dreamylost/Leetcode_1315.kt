/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 1315. 祖父节点值为偶数的节点和
 *
 * 给你一棵二叉树，请你返回满足以下条件的所有节点的值之和：
 * 该节点的祖父节点的值为偶数。（一个节点的祖父节点是指该节点的父节点的父节点。）
 * 如果不存在祖父节点值为偶数的节点，那么返回 0 。

 * @author liguobin@growingio.com
 * @version 1.0,2020/9/17
 */
class Leetcode_1315 {

    companion object {

        /**
         * 324 ms,100.00%
         * 36.7 MB,100.00%
         */
        fun sumEvenGrandparent(root: TreeNode?): Int {
            if (root == null) return 0

            fun sumParent(r: TreeNode?, ret: MutableList<Int>) {
                if (r == null) return
                if (r.`val` % 2 == 0) {
                    if (r.left?.left != null) {
                        ret.add(r.left?.left?.`val` ?: 0)
                    }
                    if (r.left?.right != null) {
                        ret.add(r.left?.right?.`val` ?: 0)
                    }
                    if (r.right?.left != null) {
                        ret.add(r.right?.left?.`val` ?: 0)
                    }
                    if (r.right?.right != null) {
                        ret.add(r.right?.right?.`val` ?: 0)
                    }
                }

                sumParent(r.left, ret)
                sumParent(r.right, ret)
            }

            val ret = mutableListOf<Int>()
            sumParent(root, ret)
            return ret.sum()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = sumEvenGrandparent(TreeNodeData.treeNode_5_1())
            println(ret)
        }
    }
}
