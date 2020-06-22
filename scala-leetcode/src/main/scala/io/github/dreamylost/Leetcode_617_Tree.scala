package io.github.dreamylost

/**
  * 617. Merge Two Binary Trees (Easy)
  * Input:
  *       Tree 1                     Tree 2
  *          1                         2
  *         / \                       / \
  *        3   2                     1   3
  *       /                           \   \
  *      5                             4   7
  * Output:
  * Merged tree:
  *         3
  *        / \
  *       4   5
  *      / \   \
  *     5   4   7
  * @author 梦境迷离
  * @time 2018年7月29日
  * @version v1.0
  */
object Leetcode_617_Tree extends App {

  def mergeTrees(t1: TreeNode, t2: TreeNode): TreeNode = {
    if (t1 == null && t2 == null) return null
    if (t1 == null) return t2
    if (t2 == null) return t1
    val root = new TreeNode(t1.value + t2.value)
    root.left = mergeTrees(t1.left, t2.left)
    root.right = mergeTrees(t1.right, t2.right)
    return root
  }

}
