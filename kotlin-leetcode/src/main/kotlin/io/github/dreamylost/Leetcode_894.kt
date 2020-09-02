/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 894. 所有可能的满二叉树
 *
 * 满二叉树是一类二叉树，其中每个结点恰好有 0 或 2 个子结点。
 * 返回包含 N 个结点的所有可能满二叉树的列表。 答案的每个元素都是一个可能树的根结点。
 * 答案中每个树的每个结点都必须有 node.val=0。你可以按任何顺序返回树的最终列表。
 * @author 梦境迷离
 * @version 1.0,2020/9/2
 */
class Leetcode_894 {
    companion object {
        /**
         * 316 ms,50.00%
         * 48.5 MB,100.00%
         */
        fun allPossibleFBT(N: Int): List<TreeNode?> {
            // 偶数节点不存在满二叉树，下面步调也使用+2
            // 可以加个Map记录避免重复计算
            if (N == 0 || N.and(1) == 0) return emptyList()
            if (N == 1) return listOf(TreeNode(0))
            val ret = mutableListOf<TreeNode?>()
            for (i in 1 until N step 2) {
                val left = allPossibleFBT(i)
                val right = allPossibleFBT(N - i - 1)
                for (l in left) for (r in right) {
                    val root = TreeNode(0)
                    root.left = l
                    root.right = r
                    ret.add(root)
                }
            }
            return ret
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = allPossibleFBT(7)
            println(ret)
        }
    }
}
