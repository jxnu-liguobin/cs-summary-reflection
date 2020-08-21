/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 剑指 Offer 07. 重建二叉树
 *
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 * @author 梦境迷离
 * @version 1.0,2020/8/20
 */
object Leetcode_JZ_7 {

    /**
     * 308 ms,19.05%
     * 77.2 MB,50.00%
     */
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        if (preorder.isEmpty()) return null
        val rootValue = preorder[0]
        val root = TreeNode(rootValue)
        val inorderSplitIndex = inorder.indexOf(rootValue)
        root.left = buildTree(preorder.sliceArray(1 until inorderSplitIndex + 1), inorder.sliceArray(0 until inorderSplitIndex))
        root.right = buildTree(preorder.sliceArray(inorderSplitIndex + 1 until preorder.size), inorder.sliceArray(inorderSplitIndex + 1 until inorder.size))
        return root
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = buildTree(arrayOf(3, 9, 20, 15, 7).toIntArray(), arrayOf(9, 3, 15, 20, 7).toIntArray())
        println(ret)
    }
}
