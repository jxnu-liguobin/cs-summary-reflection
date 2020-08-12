/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * kotlin 测试数据
 * @author 梦境迷离
 * @version 1.0,2020/6/22
 */
object TreeNodeData {

    fun treeNode_3(): TreeNode {
        val root = TreeNode(1)
        val l = TreeNode(2)
        val r = TreeNode(2)
        root.left = l
        root.right = r
        return root
    }

    fun treeNode_6(): TreeNode {
        val n1 = TreeNode(1)
        val n2 = TreeNode(2)
        val n3 = TreeNode(3)
        val n4 = TreeNode(4)
        val n5 = TreeNode(5)
        val n6 = TreeNode(6)

        n4.left = n2
        n4.left = n6

        n2.left = n3
        n2.right = n1

        n6.left = n5
        return n4
    }
}
