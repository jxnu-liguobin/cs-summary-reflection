/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 两节点的最长路径（二叉树的直径） 不必须经过根节点
  * 543. Diameter of Binary Tree (Easy)
  * Input:
  *
  *  1
  * / \
  * 2  3
  * / \
  * 4   5
  *
  * Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
  *
  * @author 梦境迷离
  * @time 2018年7月28日
  * @version v1.0
  */
object Leetcode_543_Tree extends App {

  /**
    * 编程之美解法参见GetMaximumDistance
    * 1.经过root节点的最大路径=maxDepth left + maxDepth right + 1
    * 2.将二叉树的直径转换为：二叉树的每个节点的左右子树的高度和的最大值（以每一个节点为根节点计算最长路径（左子树边长+右子树边长））
    * 624 ms,66.67%
    * 51.9 MB,100.00%
    *
    * @param root
    * @return
    */
  def diameterOfBinaryTree(root: TreeNode): Int = {
    var max = 0

    def depth(root: TreeNode): Int = {
      if (root == null) return 0
      val leftDepth = depth(root.left)
      val rightDepth = depth(root.right)
      max = math.max(max, leftDepth + rightDepth)
      math.max(leftDepth, rightDepth) + 1
    }

    depth(root)
    max
  }

}
