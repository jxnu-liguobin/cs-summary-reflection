/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 1457. 二叉树中的伪回文路径
 *
 * 给你一棵二叉树，每个节点的值为 1 到 9 。我们称二叉树中的一条路径是 「伪回文」的，当它满足：路径经过的所有节点值的排列中，存在一个回文序列。
 * 请你返回从根到叶子节点的所有路径中 伪回文 路径的数目。
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-10-07
 *  @version 1.0
 */
class Leetcode_1457 {

    companion object {

        /**
         * 512 ms,100.00%
         * 52.9 MB,100.00%
         */
        fun pseudoPalindromicPaths(root: TreeNode?): Int {
            var ret = 0
            fun dfsPaths(r: TreeNode?, paths: MutableList<Int>) {
                if (r == null) return
                paths.add(r.`val`)
                val cnt = Array(10) { 0 }
                if (r.left == null && r.right == null) {
                    for (e in paths) {
                        cnt[e] += 1
                    }
                    val count = cnt.count { it and 1 == 1 } // 奇数
                    if (count <= 1) {
                        ret++
                    }
                    println(paths)
                } else {
                    dfsPaths(r.left, paths)
                    dfsPaths(r.right, paths)
                }
                paths.removeAt(paths.size - 1)
            }

            dfsPaths(root, mutableListOf())
            return ret
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = pseudoPalindromicPaths(TreeNodeData.treeNode6_1())
            println(ret)
        }
    }
}
