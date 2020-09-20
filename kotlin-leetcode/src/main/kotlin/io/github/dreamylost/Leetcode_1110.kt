/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 1110. 删点成林
 *
 * 给出二叉树的根节点 root，树上每个节点都有一个不同的值。
 * 如果节点值在 to_delete 中出现，我们就把该节点从树上删去，最后得到一个森林（一些不相交的树构成的集合）。
 * 返回森林中的每棵树。你可以按任意顺序组织答案。
 *
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-09-20
 *  @version 1.0
 */
class Leetcode_1110 {

    companion object {

        /**
         * 296 ms,100.00%
         * 36.8 MB,100.00%
         */
        fun delNodes(root: TreeNode?, to_delete: IntArray): List<TreeNode?> {
            val toDeletes = mutableSetOf<Int>()
            val nodes = arrayListOf<TreeNode?>()
            fun dfs(r: TreeNode?): TreeNode? {
                if (r == null) return null
                r.left = dfs(r.left)
                r.right = dfs(r.right)
                // 后续断根，需要删除的元素的父节点返回空
                if (toDeletes.contains(r.`val`)) {
                    if (r.left != null) nodes.add(r.left)
                    if (r.right != null) nodes.add(r.right)
                    return null
                }
                return r
            }

            toDeletes.addAll(to_delete.asList())
            if (!toDeletes.contains(root?.`val`)) {
                nodes.add(root)
            }

            dfs(root)
            return nodes
        }

        @JvmStatic
        fun main(args: Array<String>) {
        }
    }
}
