/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 971. 翻转二叉树以匹配先序遍历
 *
 * 给定一个有 N 个节点的二叉树，每个节点都有一个不同于其他节点且处于 {1, ..., N} 中的值。
 * 通过交换节点的左子节点和右子节点，可以翻转该二叉树中的节点。
 * 考虑从根节点开始的先序遍历报告的 N 值序列。将这一 N 值序列称为树的行程。
 * （回想一下，节点的先序遍历意味着我们报告当前节点的值，然后先序遍历左子节点，再先序遍历右子节点。）
 * 我们的目标是翻转最少的树中节点，以便树的行程与给定的行程 voyage 相匹配。 如果可以，则返回翻转的所有节点的值的列表。你可以按任何顺序返回答案。如果不能，则返回列表 [-1]。
 * @author 梦境迷离
 * @version 1.0,2020/10/9
 */
class Leetcode_971 {

    companion object {

        /**
         * 前序DFS时，交换整个左右子树
         * 216 ms,100.00%
         * 33.7 MB,100.00%
         */
        fun flipMatchVoyage(root: TreeNode?, voyage: IntArray): List<Int> {
            var i = 0
            val ret = mutableListOf<Int>()
            var invalid = false
            fun dfsVoyage(r: TreeNode?) {
                if (r == null) return
                if (r.`val` != voyage[i]) {
                    invalid = true
                    return
                }
                if (r.left != null && r.left?.`val` != voyage[i + 1]) {
                    val temp = r.left
                    r.left = r.right
                    r.right = temp
                    ret.add(r.`val`)
                }
                i++
                dfsVoyage(r.left)
                dfsVoyage(r.right)
            }
            dfsVoyage(root)
            return if (invalid) mutableListOf(-1) else ret
        }

        @JvmStatic
        fun main(args: Array<String>) {
        }
    }
}
