package io.github.dreamylost

/**
  * 判断平衡树
  *
 * 110. Balanced Binary Tree (Easy)
  *
 * @author 梦境迷离
  * @time 2018年7月28日
  * @version v1.0
  */
object Leetcode_110_Tree extends App {

  private var result = true

  def isBalanced(root: TreeNode): Boolean = {
    maxDepth(root)
    result
  }

  //@tailrec 尾递归使用注解优化
  def maxDepth(root: TreeNode): Int = {
    if (root == null)
      return 0
    val l = maxDepth(root.left)
    val r = maxDepth(root.right)
    if (Math.abs(l - r) > 1)
      result = false
    1 + math.max(l, r)
  }

}
