/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 剑指 Offer 34. 二叉树中和为某一值的路径
 *
 * 输入一棵二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径。从树的根节点开始往下一直到叶节点所经过的节点形成一条路径。
 * @author 梦境迷离
 * @version 1.0,2020/6/22
 */
object Leetcode_JZ_34 {

    // 280 ms ,40.00%
    // 38.4 MB ,33.33%
    fun pathSum(root: TreeNode?, sum: Int): List<List<Int>> {
        if (root == null) return emptyList()
        val ret = mutableListOf<List<Int>>()

        fun helper(root: TreeNode?, sum: Int, mutableList: MutableList<Int>): Int {
            if (root == null) return -1
            val tmp = sum - root.`val`
            mutableList.add(root.`val`)
            if (tmp == 0 && root.left == null && root.right == null) {
                ret.add(ArrayList(mutableList))
            } else {
                helper(root.left, tmp, mutableList)
                helper(root.right, tmp, mutableList)
            }
            return mutableList.removeAt(mutableList.size - 1)
        }

        helper(root, sum, ArrayList())
        return ret
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = pathSum(TreeNodeData.treeNode_10(), 22)
        print(ret)
    }
}
