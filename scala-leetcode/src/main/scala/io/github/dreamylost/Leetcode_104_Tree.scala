package io.github.dreamylost

/**
  * 树的高度
  * 104. Maximum Depth of Binary Tree (Easy)
  *
 * @time 2018年7月28日
  * @version v1.0
  */
object Leetcode_104_Tree extends App {

  def maxDepth(root: TreeNode): Int = {
    if (root == null) return 0
    math.max(maxDepth(root.left), maxDepth(root.right)) + 1
  }

}
