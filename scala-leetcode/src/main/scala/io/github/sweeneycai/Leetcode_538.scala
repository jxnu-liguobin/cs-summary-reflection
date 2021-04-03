/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import io.github.dreamylost.TreeNode

/**
 * 538. 把二叉搜索树转换为累加树 (Easy)
 *
 * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
 *
 * @see <a href="https://leetcode-cn.com/problems/convert-bst-to-greater-tree/">leetcode-cn.com</a>
 */
object Leetcode_538 extends App {
  val root = new TreeNode(3)

  def convertBST(root: TreeNode): TreeNode = {
    var cnt = 0

    def recur(node: TreeNode): Unit = {
      if (node == null) return
      recur(node.right)
      cnt += node.value
      node.value = cnt
      recur(node.left)
    }

    recur(root)
    root
  }

  root.left = new TreeNode(1)
  root.left.left = new TreeNode(0)
  root.left.right = new TreeNode(2)
  root.right = new TreeNode(5)
  root.right.left = new TreeNode(4)
  root.right.right = new TreeNode(6)

  BinaryTreeUtils.levelOrder(root)
  BinaryTreeUtils.levelOrder(convertBST(root))
}
