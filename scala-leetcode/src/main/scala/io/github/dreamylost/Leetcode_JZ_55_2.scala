/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 剑指 Offer 55 - II. 平衡二叉树
  *
  * 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
  * @author 梦境迷离
  * @version 1.0,2020/6/23
  */
object Leetcode_JZ_55_2 extends App {

  val ret = isBalanced(TreeNodeData.treeData3_3())
  println(ret)

  /**
    * 632 ms,40.00%
    * 51.7 MB,100.00%
    *
    * @param root
    * @return
    */
  def isBalanced(root: TreeNode): Boolean = {
    var ret = true
    def maxDepth(r: TreeNode): Int = {
      if (r == null) return 0
      val left = maxDepth(r.left)
      val right = maxDepth(r.right)
      if (math.abs(left - right) > 1) {
        ret = false
      }
      math.max(left, right) + 1
    }
    maxDepth(root)
    ret
  }

}
