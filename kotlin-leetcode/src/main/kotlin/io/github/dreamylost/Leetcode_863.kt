/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 863. 二叉树中所有距离为 K 的结点
 *
 * 给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 K 。
 * 返回到目标结点 target 距离为 K 的所有结点的值的列表。 答案可以以任何顺序返回。
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-08-29
 *  @version 1.0
 */
class Leetcode_863 {
    companion object {

        /**
         * 224 ms,100.00%
         * 34 MB,100.00%
         */
        fun distanceK(root: TreeNode?, target: TreeNode?, K: Int): List<Int> {
            if (root == null) return mutableListOf()
            val nodeParent = hashMapOf<TreeNode, TreeNode?>()
            val ret = mutableListOf<Int>()
            // 1.记录每个节点的父节点
            fun dfs(r: TreeNode?, parent: TreeNode?) {
                if (r == null) return
                nodeParent[r] = parent
                dfs(r.left, r)
                dfs(r.right, r)
            }

            // 2.preNode表示前一个节点，避免走回头路
            fun findNode(t: TreeNode?, k: Int, list: MutableList<Int>, preNode: TreeNode?) {
                if (t == null) return
                if (k == 0) {
                    list.add(t.`val`)
                    return
                }
                val parent = nodeParent[t]
                if (parent != null && preNode != parent) {
                    findNode(parent, k - 1, list, t)
                }
                if (t.left != null && preNode != t.left) {
                    findNode(t.left, k - 1, list, t)
                }
                if (t.right != null && preNode != t.right) {
                    findNode(t.right, k - 1, list, t)
                }
            }
            dfs(root, null)
            findNode(target, K, ret, TreeNode(0))
            return ret
        }
    }
}
