/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import kotlin.math.max
import kotlin.math.pow

/**
 * 655. 输出二叉树
 *
 * 在一个 m*n 的二维字符串数组中输出二叉树，并遵守以下规则：
 * 1.行数 m 应当等于给定二叉树的高度。
 * 2.列数 n 应当总是奇数。
 * 3.根节点的值（以字符串格式给出）应当放在可放置的第一行正中间。根节点所在的行与列会将剩余空间划分为两部分（左下部分和右下部分）。
 * 你应该将左子树输出在左下部分，右子树输出在右下部分。左下和右下部分应当有相同的大小。即使一个子树为空而另一个非空，你不需要为空的子树输出任何东西，但仍需要为另一个子树留出足够的空间。然而，如果两个子树都为空则不需要为它们留出任何空间。
 * 5.每个未使用的空间应包含一个空的字符串""。
 * 6.使用相同的规则输出子树。
 *
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-08-24
 *  @version 1.0
 */
class Leetcode_655 {
    companion object {

        /**
         * 204 ms,100.00%
         * 34.5 MB,100.00%
         */
        fun printTree(root: TreeNode?): List<List<String>> {
            fun getDepth(root: TreeNode?): Int {
                return if (root == null) {
                    0
                } else max(getDepth(root.left), getDepth(root.right)) + 1
            }

            val depth = getDepth(root)
            val col = (2.0.pow(depth) - 1).toInt()
            val ret: Array<Array<String?>> = Array(depth) { arrayOfNulls<String?>(col) }

            fun printTree(rt: TreeNode?, d: Int, l: Int, r: Int) {
                if (rt == null) return
                val pos = (l + r) / 2
                ret[d][pos] = rt.`val`.toString()
                printTree(rt.left, d + 1, l, pos)
                printTree(rt.right, d + 1, pos, r)
            }

            printTree(root, 0, 0, col)
            return ret.map { it -> it.map { it.orEmpty() } }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = printTree(TreeNodeData.treeNode_2_2())
            println(ret)
        }
    }
}
