/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 114. 二叉树展开为链表
  *
  * 给定一个二叉树，原地将它展开为一个单链表。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-06
  * @version v1.0
  */
object Leetcode_114 extends App {

  /**
    * 超时。。。
    * 右子树->左子树->根节点
    *
    * @see Leetcode_Interview_17_12 也是超时
    * @param root
    */
  def flatten(root: TreeNode): Unit = {
    if (root == null) return
    flatten(root.right)
    flatten(root.left)
    val tmp = root.right
    root.right = root.left
    root.left = null
    var cur = root
    while (cur.right != null) cur = cur.right
    cur.right = tmp
    root.right = cur
  }

  /**
    * 用临时变量，没超时。Leetcode_Interview_17_12 convertBiNode2 超时了
    * 580 ms,50.00%
    * 51.2 MB,100.00%
    *
    * @param r
    */
  def flatten_(r: TreeNode): Unit = {
    var pre: TreeNode = null

    def helper(root: TreeNode): Unit = {
      if (root == null) return
      helper(root.right)
      helper(root.left)
      //1.后续遍历，left和right已经遍历了
      //2.当前节点连上上个子树
      root.right = pre
      //3.置空左子树
      root.left = null
      pre = root
    }

    helper(r)
  }

  /**
    * 584 ms,50.00%
    * 51 MB,100.00%
    *
    * @param root
    */
  def flatten2(root: TreeNode) = {
    var head = root
    while (head != null) {
      if (head.left == null) {
        head = head.right
      } else {
        // 找左子树最右边的节点
        var pre = head.left
        while (pre.right != null) {
          pre = pre.right
        }
        //将原来的右子树接到左子树的最右边节点
        pre.right = head.right
        // 将左子树插入到右子树的地方
        head.right = head.left
        head.left = null
        head = head.right
      }
    }

  }
}
