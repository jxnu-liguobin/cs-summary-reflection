/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import io.github.dreamylost.TreeNode
import scala.collection.mutable

/**
  * 99. 恢复二叉搜索树 (Hard)
  * 二叉搜索树中的两个节点被错误地交换。
  *
  * 在不改变其结构的情况下，恢复这棵树。
  */
object Leetcode_99 extends App {

  /**
    * 非递归版本
    */
  def recoverTree(root: TreeNode): Unit = {
    val stack: mutable.Stack[TreeNode] = mutable.Stack()
    var pre = new TreeNode(Int.MinValue)
    var bigger: TreeNode = null
    var lower: TreeNode = null
    var cur = root
    while (stack.nonEmpty || cur != null) {
      if (cur != null) {
        stack.push(cur)
        cur = cur.left
      } else {
        cur = stack.pop()
        if (cur.value < pre.value && bigger == null) {
          bigger = pre
          lower = cur
        } else if (cur.value < pre.value) {
          lower = cur
        }
        pre = cur
        cur = cur.right
      }
    }
    if (bigger != null && lower != null) {
      val value = bigger.value
      bigger.value = lower.value
      lower.value = value
    }
  }

  /**
    * 递归版本
    */
  def recoverTreeWithRecur(root: TreeNode): Unit = {
    if (root == null) return
    var pre = new TreeNode(Int.MinValue)
    var bigger: TreeNode = null
    var lower: TreeNode = null
    def helper(node: TreeNode): Unit = {
      if (node.left != null) helper(node.left)
      if (node.value < pre.value) {
        lower = node
        if (bigger == null) bigger = pre
      }
      pre = node
      if (node.right != null) helper(node.right)
    }
    helper(root)
    val value = bigger.value
    bigger.value = lower.value
    lower.value = value
  }

  val root = new TreeNode(3)
  root.left = new TreeNode(1)
  root.right = new TreeNode(4)
  root.right.left = new TreeNode(2)
  recoverTree(root)
  println(root)
}
