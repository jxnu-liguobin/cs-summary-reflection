/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 剑指 Offer 32 - I. 从上到下打印二叉树
 *
 * 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
 *
 * @author 梦境迷离
 * @version 1.0,2020/8/19
 */
object Leetcode_JZ_32_1 {

  /**
   * 236 ms,11.11%
   * 35.7 MB,16.67%
   */
  fun levelOrder(root: TreeNode?): IntArray {
    if (root == null) return emptyArray<Int>().toIntArray()
    val ret = arrayListOf<Int>()
    val queue = java.util.LinkedList<TreeNode>()
    queue.addLast(root)
    while (!queue.isEmpty()) {
      val node = queue.pollFirst()
      ret.add(node.`val`)
      if (node?.left != null) queue.addLast(node.left)
      if (node?.right != null) queue.addLast(node.right)
    }
    return ret.toIntArray()
  }

  @JvmStatic
  fun main(args: Array<String>) {
    val ret = levelOrder(TreeNodeData.treeNode_10())
    for (i in ret) {
      println(i)
    }
  }
}
