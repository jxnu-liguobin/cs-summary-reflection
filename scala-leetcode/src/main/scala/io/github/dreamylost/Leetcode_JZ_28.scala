package io.github.dreamylost

/**
  * 剑指 Offer 28 (T58). 对称的二叉树
  *
  * 请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
  *
  * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
  *
  * @author 梦境迷离
  * @version 1.0,2020/6/22
  */
object Leetcode_JZ_28 {

  /**
    * 556 ms,100.00%
    * 51.2 MB,100.00%
    *
    * @param root
    * @return
    */
  def isSymmetric(root: TreeNode): Boolean = {
    def helper(l: TreeNode, r: TreeNode): Boolean = {
      if (l == null && r == null) {
        return true
      }
      if (l == null || r == null) {
        return false
      }
      if (l.value != r.value) {
        return false
      }
      helper(l.left, r.right) && helper(l.right, r.left)
    }

    if (root == null) return true
    helper(root.left, root.right)

  }
}
