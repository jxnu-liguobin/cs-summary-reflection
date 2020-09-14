/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import kotlin.math.abs
import kotlin.math.max

/**
 * 1026. 节点与其祖先之间的最大差值
 *
 * 给定二叉树的根节点 root，找出存在于不同节点 A 和 B 之间的最大值 V，其中 V = |A.val - B.val|，且 A 是 B 的祖先。
 * （如果 A 的任何子节点之一为 B，或者 A 的任何子节点是 B 的祖先，那么我们认为 A 是 B 的祖先）
 *
 * @author liguobin@growingio.com
 * @version 1.0,2020/9/14
 */
class Leetcode_1026 {

    companion object {
        /**
         * 仅在到达叶子时计算：
         * 188 ms,100.00%
         * 34 MB,100.00%
         */
        fun maxAncestorDiff(root: TreeNode?): Int {
            var ret = 0
            fun find(root: TreeNode?, valueMax: Int, valueMin: Int) {
                if (root == null) return
                val countMax = valueMax.coerceAtLeast(root.`val`) // max(valueMax, val)
                val countMin = valueMin.coerceAtMost(root.`val`) // min(valueMin, val)
                if (root.left == null && root.right == null) {
                    ret = max(ret, abs(countMax - countMin))
                }
                find(root.left, countMax, countMin)
                find(root.right, countMax, countMin)
            }
            find(root, Integer.MIN_VALUE, Integer.MAX_VALUE)
            return ret
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = maxAncestorDiff(TreeNodeData.treeNode_5_1())
            println(ret)
        }
    }
}
