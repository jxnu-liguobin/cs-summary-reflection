package io.github.dreamylost

/**
 * 统计左叶子节点的和
 *
 * 404. Sum of Left Leaves (Easy)
 *
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 *
 * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
 * @author 梦境迷离
 * @time 2018年8月9日
 * @version v1.0
 */
object Leetcode_404_Tree extends App {

  def sumOfLeftLeaves(root: TreeNode): Int = {
    if (root == null) return 0
    if (isLeaf(root.left))
      return root.left.value + sumOfLeftLeaves(root.right)
    return sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right)
  }

  private def isLeaf(node: TreeNode): Boolean = {
    if (node == null)
      return false
    return node.left == null && node.right == null
  }
}