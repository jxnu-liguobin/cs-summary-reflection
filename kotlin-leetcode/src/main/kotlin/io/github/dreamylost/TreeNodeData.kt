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

    fun treeNode_10(): TreeNode {
        val n5 = TreeNode(5)
        val n8 = TreeNode(8)
        val n11 = TreeNode(11)
        val n13 = TreeNode(13)
        val n4 = TreeNode(4)
        val n7 = TreeNode(7)
        val n2 = TreeNode(2)
        val n1 = TreeNode(1)
        n5.left = n4
        n5.right = n8
        n4.left = n11
        n8.left = n13
        n8.right = TreeNode(4)
        n8.right?.left = TreeNode(5)
        n8.right?.right = n1
        n11.left = n7
        n11.right = n2
        return n5
    }

    fun treeNode_5_1(): TreeNode {
        val n1 = TreeNode(1)
        val n2 = TreeNode(2)
        val n3 = TreeNode(3)
        val n4 = TreeNode(4)
        val n5 = TreeNode(5)

        n3.left = n4
        n3.right = n5
        n4.left = n1
        n4.right = n2
        return n3
    }

    fun treeNode_2_1(): TreeNode {
        val n1 = TreeNode(1)
        val n4 = TreeNode(4)
        n4.left = n1
        return n4
    }
}
