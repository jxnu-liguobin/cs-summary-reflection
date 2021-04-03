/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import io.github.dreamylost.TreeNode

/**
 * 98. 验证二叉搜索树 (Medium)
 *
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 假设一个二叉搜索树具有如下特征：
 *
 * - 节点的左子树只包含小于当前节点的数。
 *
 * - 节点的右子树只包含大于当前节点的数。
 *
 * - 所有左子树和右子树自身必须也是二叉搜索树。
 *
 * @see <a href="https://leetcode-cn.com/problems/validate-binary-search-tree/">leetcode-cn.com</a>
 */
object Leetcode_98 extends App {

  /**
   * 为递归的每个节点设置上界和下界，只要在该区间内就是合法的。
   * 当然最简单的解决办法是中序遍历，如果是单调递增的序列那么就是合法的二叉搜索树。
   */
  def isValidBST(root: TreeNode): Boolean = {
    // 此处用 Long 解决节点值越界问题
    def recur(node: TreeNode, min: Long, max: Long): Boolean = {
      if (node == null) return true
      if (node.value > min && node.value < max) {
        return recur(node.left, min, node.value) && recur(node.right, node.value, max)
      }
      false
    }
    recur(root, Long.MinValue, Long.MaxValue)
  }

  val root = new TreeNode(3)
  root.left = new TreeNode(1)
  root.right = new TreeNode(5)
  root.left.left = new TreeNode(-1)
  root.left.right = new TreeNode(2)
  root.right.left = new TreeNode(4)
  root.right.right = new TreeNode(9)

  println(isValidBST(root))
  println(isValidBST(new TreeNode(2147483647)))
  println(isValidBST(null))
}
