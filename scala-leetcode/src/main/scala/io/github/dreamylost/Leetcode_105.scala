/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 105. 从前序与中序遍历序列构造二叉树
  *
  * 根据一棵树的前序遍历与中序遍历构造二叉树。
  *
  * 你可以假设树中没有重复的元素。
  * 例如，给出
  * 前序遍历 preorder = [3,9,20,15,7]
  * 中序遍历 inorder = [9,3,15,20,7]
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-02
  * @version v1.0
  */
object Leetcode_105 extends App {

  val ret = buildTree(Array(3, 9, 20, 15, 7), Array(9, 3, 15, 20, 7))
  println(ret)

  /**
    * 784 ms,83.33%
    * 97.3 MB,100.00%
    *
    * @param preorder
    * @param inorder
    * @return
    */
  def buildTree(preorder: Array[Int], inorder: Array[Int]): TreeNode = {
    if (preorder.isEmpty) return null
    val value = preorder.head
    val inorderIndex = inorder.indexOf(value)
    val root = new TreeNode(value)
    root.left = buildTree(preorder.slice(1, inorderIndex + 1), inorder.slice(0, inorderIndex))
    root.right = buildTree(
      preorder.slice(inorderIndex + 1, preorder.length),
      inorder.slice(inorderIndex + 1, inorder.length)
    )
    root
  }
}
