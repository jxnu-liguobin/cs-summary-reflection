/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 1530. 好叶子节点对的数量
 *
 * 给你二叉树的根节点 root 和一个整数 distance 。
 * 如果二叉树中两个叶节点之间的最短路径长度小于或者等于distance，那它们就可以构成一组好叶子节点对 。返回树中好叶子节点对的数量 。
 *
 * @author 梦境迷离
 * @version 1.0,2020/9/23
 */
class Leetcode_1530 {

    /**
     * 504 ms,20.00%
     * 42.4 MB,100.00%
     */
    companion object {
        // 1.后序遍历，对于每一个遍历到的节点r，计算r左右子树中叶子节点到r的距离，记录在map中。
        // 2.组合左右子树中的叶子节点对，如果它们的距离只和小于distance，则结果加1。
        // 3.向上返回当前节点r的所有叶子节点距离给上层节点。
        fun countPairs(root: TreeNode?, distance: Int): Int {
            var ret = 0
            fun dfs(r: TreeNode?, d: Int): Map<TreeNode, Int> {
                if (r == null) return emptyMap()
                if (r.left == null && r.right == null) return mutableMapOf(Pair(r, 0))
                // 增加距离
                val left = dfs(r.left, d).map { t -> Pair(t.key, t.value + 1) }.toMap()
                val right = dfs(r.right, d).map { t -> Pair(t.key, t.value + 1) }.toMap()
                for (i in left) for (j in right) {
                    if (i.value + j.value <= distance) {
                        ret++
                    }
                }
                return left + right
            }
            dfs(root, distance)
            return ret
        }

        @JvmStatic
        fun main(args: Array<String>) {
        }
    }
}
