package io.github.dreamylost

/**
  * 树的对称
  *
  * 101. Symmetric Tree (Easy)
  *
  * 1
  * / \
  * 2   2
  * / \ / \
  * 3  4 4  3
  *
  * @author 梦境迷离
  * @time 2018年7月18日
  * @version v1.0
  */
object Leetcode_101_Tree extends App {

  def isSymmetric(root: TreeNode): Boolean = {
    if (root == null) return true
    isSymmetric(root.left, root.right)
  }

  private def isSymmetric(t1: TreeNode, t2: TreeNode): Boolean = {
    if (t1 == null && t2 == null) return true
    if (t1 == null || t2 == null) return false
    if (t1.value != t2.value) return false
    isSymmetric(t1.left, t2.right) && isSymmetric(t1.right, t2.left)
  }

}
