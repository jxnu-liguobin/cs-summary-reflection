/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import kotlin.math.max

/**
 * 1372. 二叉树中的最长交错路径
 *
 * 给你一棵以 root 为根的二叉树，二叉树中的交错路径定义如下：
 * 选择二叉树中 任意 节点和一个方向（左或者右）。
 * 如果前进方向为右，那么移动到当前节点的的右子节点，否则移动到它的左子节点。
 * 改变前进方向：左变右或者右变左。
 * 重复第二步和第三步，直到你在树中无法继续移动。
 * 交错路径的长度定义为：访问过的节点数目 - 1（单个节点的路径长度为 0 ）。
 * 请你返回给定树中最长 交错路径 的长度。
 * @author 梦境迷离
 * @version 1.0,2020/10/10
 */
class Leetcode_1372 {
    /**
     * 404 ms,100.00%
     * 46.5 MB,100.00%
     */
    fun longestZigZag(root: TreeNode?): Int {
        var ret = 0
        fun dfs(r: TreeNode?, isLeft: Boolean): Int {
            if (r == null) return 0
            val l = dfs(r.left, true)
            val r = dfs(r.right, false)
            ret = max(ret, max(l, r))
            return if (isLeft) {
                r + 1
            } else l + 1
        }
        dfs(root, true)
        return ret
    }
}
