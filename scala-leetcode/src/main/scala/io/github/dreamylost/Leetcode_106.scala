/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 106. 从中序与后续遍历序列构造二叉树
 *
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 *
 * 你可以假设树中没有重复的元素。
 * 例如，给出
 *
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 *
 * @author 梦境迷离 dreamylost
 * @since 2020-07-02
 * @version v1.0
 */
object Leetcode_106 extends App {

  val ret = buildTree(Array(9, 3, 15, 20, 7), Array(9, 15, 7, 20, 3))
  println(ret)

  /**
   * 784 ms,75.00%
   * 97.6 MB,100.00%
   *
   * @param inorder
   * @param postorder
   * @return
   */
  def buildTree(inorder: Array[Int], postorder: Array[Int]): TreeNode = {
    if (postorder.isEmpty) return null
    val value = postorder.last
    val inorderIndex = inorder.indexOf(value)
    val root = new TreeNode(value)
    root.left =
      buildTree(inorder.slice(0, inorderIndex), postorder.slice(0, inorderIndex)) // slice左闭右开
    root.right = buildTree(
      inorder.slice(inorderIndex + 1, inorder.length),
      postorder.slice(inorderIndex, postorder.length - 1)
    )
    root

  }
}
