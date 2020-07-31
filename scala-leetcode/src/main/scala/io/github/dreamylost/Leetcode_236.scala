/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 236. 二叉树的最近公共祖先
  *
  * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
  *
  * @see https://github.com/jxnu-liguobin
  * @author 梦境迷离
  * @since 2020-07-27
  * @version 1.0
  */
object Leetcode_236 {

  /**
    * lca问题
    *
    * 780 ms,75.00%
    * 56.2 MB,100.00%
    *
    * @param root
    * @param p
    * @param q
    * @return
    */
  def lowestCommonAncestor(root: TreeNode, p: TreeNode, q: TreeNode): TreeNode = {
    if (root == null) return root
    if ((root == p) || (root == q)) return root //这个if 单独写快点
    val left = lowestCommonAncestor(root.left, p, q)
    val right = lowestCommonAncestor(root.right, p, q)
    if (left != null && right != null) root
    else if (left == null && right == null) null
    else if (right == null) left
    else right
  }
}
