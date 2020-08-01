/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 1008. 先序遍历构造二叉树
  *
  * 返回与给定先序遍历 preorder 相匹配的二叉搜索树（binary search tree）的根结点。
  *
  * (回想一下，二叉搜索树是二叉树的一种，其每个节点都满足以下规则，对于 node.left 的任何后代，值总 < node.val，而 node.right 的任何后代，值总 > node.val。
  * 此外，先序遍历首先显示节点的值，然后遍历 node.left，接着遍历 node.right。）
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-12
  * @version v1.0
  */
object Leetcode_1008 extends App {

  val ret = bstFromPreorder(Array(8, 5, 1, 7, 10, 12))
  println(ret)

  /**
    * 已知前序序列，找到第一个不满足左子树的节点，作用相当于在中序序列中构造二叉搜索树时计算的mid
    * 548 ms,100.00%
    * 50.6 MB,100.00%
    *
    * @param preorder
    * @return
    */
  def bstFromPreorder(preorder: Array[Int]): TreeNode = {
    def buildSearchTree(values: Array[Int], l: Int, r: Int): TreeNode = {
      if (l > r) {
        return null
      }
      val root = new TreeNode(values(l))
      var index = l
      while (index <= r && values(index) <= root.value) index += 1
      //head已经被使用
      root.left = buildSearchTree(values, l + 1, index - 1)
      root.right = buildSearchTree(values, index, r)
      root
    }

    buildSearchTree(preorder, 0, preorder.length - 1)
  }
}
