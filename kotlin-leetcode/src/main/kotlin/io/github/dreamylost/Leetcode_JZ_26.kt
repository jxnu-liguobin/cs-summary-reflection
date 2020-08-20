/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 剑指 Offer 28. 对称的二叉树
 *
 * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
 * B是A的子结构， 即 A中有出现和B相同的结构和节点值。
 * @author 梦境迷离
 * @version 1.0,2020/8/20
 */
object Leetcode_JZ_26 {

    /**
     * 256 ms,100.00%
     * 37.1 MB,33.33%
     */
    fun isSubStructure(A: TreeNode?, B: TreeNode?): Boolean {
        if (A == null || B == null) return false
        return isSubTree(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B)
    }

    private fun isSubTree(a: TreeNode?, b: TreeNode?): Boolean {
        if (b == null) return true
        if (a == null) return false
        if (a.`val` != b.`val`) return false
        return isSubTree(a.left, b.left) && isSubTree(a.right, b.right)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = isSubStructure(TreeNodeData.treeNode_5_1(), TreeNodeData.treeNode_2_1())
        print(ret)
    }
}
