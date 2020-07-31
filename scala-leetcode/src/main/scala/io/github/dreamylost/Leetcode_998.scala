/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 998. 最大二叉树 II
  *
  * 最大树定义：一个树，其中每个节点的值都大于其子树中的任何其他值。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-10
  * @version v1.0
  */
object Leetcode_998 {

  /**
    * 题目意思是插入一个值，保持最大树规则
    * 672 ms,100.00%
    * 51.1 MB,100.00%
    *
    * @param root
    * @param `val`
    * @return
    */
  def insertIntoMaxTree(root: TreeNode, `val`: Int): TreeNode = {
    //为空时，val就是root，val比root大时，val就是新的root
    //因为左边都小于右边，所以再从右边找
    if (root == null) return new TreeNode(`val`)
    if (root.value < `val`) {
      val pre = new TreeNode(`val`)
      pre.left = root
      return pre
    }
    root.right = insertIntoMaxTree(root.right, `val`)
    root
  }
}
