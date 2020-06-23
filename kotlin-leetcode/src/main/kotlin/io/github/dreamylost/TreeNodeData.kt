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
}
