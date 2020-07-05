/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 最小路径
  *
  * 111. Minimum Depth of Binary Tree (Easy)
  *
  * 树的根节点到叶子节点的最小路径长度
  *
  * @author 梦境迷离
  * @time 2018年8月9日
  * @version v1.0
  */
object Leetcode_111_Tree extends App {

  def minDepth(root: TreeNode): Int = {
    if (root == null) return 0
    var left = minDepth(root.left)
    var right = minDepth(root.right)
    if (left == 0 || right == 0) return left + right + 1
    return math.min(left, right) + 1
  }

}
