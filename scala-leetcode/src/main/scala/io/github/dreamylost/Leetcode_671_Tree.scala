package io.github.dreamylost

import io.github.dreamylost.TreeNode

/**
  * 找出二叉树中第二小的节点
  *
 * 671. Second Minimum Node In a Binary Tree (Easy)
  * 给定一个非空的特殊二叉树，由具有非负值的节点组成，其中该树中的每个节点都有两个或零个子节点。如果节点有两个子节点，那么该节点的值是其两个子节点之间的较小值。
  * 如果不存在这样的第二最小值，则输出- 1代替。
  *
 * @author 梦境迷离
  * @time 2018年8月10日
  * @version v1.0
  */
object Leetcode_671_Tree extends App {

  def findSecondMinimumValue(root: TreeNode): Int = {
    if (root == null) return -1
    if (root.left == null && root.right == null) return -1
    var leftVal = root.left.value
    var rightVal = root.right.value
    if (leftVal == root.value) leftVal = findSecondMinimumValue(root.left)
    if (rightVal == root.value) rightVal = findSecondMinimumValue(root.right)
    if (leftVal != -1 && rightVal != -1) return Math.min(leftVal, rightVal)
    if (leftVal != -1) return leftVal
    return rightVal
  }
}
