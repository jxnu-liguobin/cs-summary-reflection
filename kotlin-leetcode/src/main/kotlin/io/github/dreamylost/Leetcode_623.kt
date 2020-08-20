/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 623. 在二叉树中增加一行
 *
 * 给定一个二叉树，根节点为第1层，深度为 1。在其第 d 层追加一行值为 v 的节点。
 * 添加规则：给定一个深度值 d （正整数），针对深度为 d-1 层的每一非空节点 N，为 N 创建两个值为 v 的左子树和右子树。
 * 将 N 原先的左子树，连接为新节点 v 的左子树；将 N 原先的右子树，连接为新节点 v 的右子树。
 * 如果 d 的值为 1，深度 d - 1 不存在，则创建一个新的根节点 v，原先的整棵树将作为 v 的左子树。
 *  @see https://github.com/jxnu-liguobin
 *  @author 梦境迷离
 *  @since 2020-08-12
 *  @version 1.0
 */
object Leetcode_623 {
  // 236 ms ,100.00%
  // 36.3 MB ,100.00%
  fun addOneRow(root: TreeNode?, v: Int, d: Int): TreeNode? {
    if (root == null) return null
    if (d == 1) {
      val addNode = TreeNode(v)
      addNode.left = root.left
      addNode.right = root.right
      return addNode
    }
    val queue: java.util.LinkedList<TreeNode> = java.util.LinkedList()
    queue.addLast(root)
    var curDepth = 2
    while (!queue.isEmpty()) {
      if (curDepth == d) {
        for (p in queue) {
          val pLeft = TreeNode(v)
          val pRight = TreeNode(v)
          pLeft.left = p.left
          p.left = pLeft
          pRight.right = p.right
          p.right = pRight
        }
        return root
      } else {
        val size = queue.size
        for (i in 0 until size) {
          val node = queue.pollFirst()
          if (node.left != null) {
            queue.addLast(node.left)
          }
          if (node.right != null) {
            queue.addLast(node.right)
          }
        }
        curDepth++
      }
    }
    return root
  }

  // 244 ms ,100.00%
  // 37.7 MB ,100.00%
  fun addOneRow2(root: TreeNode?, v: Int, d: Int): TreeNode? {
    if (root == null) return null
    if (d == 1) {
      val t = TreeNode(v)
      t.left = root
      return t
    }
    if (d == 2) {
      val l = TreeNode(v)
      val r = TreeNode(v)
      l.left = root.left
      r.right = root.right
      root.left = l
      root.right = r
      return root
    }
    root.left = addOneRow(root.left, v, d - 1)
    root.right = addOneRow(root.right, v, d - 1)
    return root
  }

  @JvmStatic
  fun main(args: Array<String>) {
    val ret = addOneRow(TreeNodeData.treeNode_6(), 1, 2)
    print(ret)
  }
}
