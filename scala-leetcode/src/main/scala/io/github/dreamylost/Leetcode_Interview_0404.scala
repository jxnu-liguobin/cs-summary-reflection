/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 面试题 04.04. 检查平衡性
 *
 * 实现一个函数，检查二叉树是否平衡。在这个问题中，平衡树的定义如下：任意一个节点，其两棵子树的高度差不超过 1。
 *
 * @author 梦境迷离 dreamylost
 * @version 1.0,2020/6/19
 */
object Leetcode_Interview_0404 extends App {

  val ret = isBalanced(TreeNodeData.treeData3_3())
  println(ret)

  /**
   * 624 ms,83.33%
   * 52.1 MB,100.00%
   *
   * @param root
   * @return
   */
  def isBalanced(root: TreeNode): Boolean = {
    var ret = true

    def maxDepth(root: TreeNode): Int = {
      Option(root).fold(0) { rot =>
        val l = maxDepth(rot.left)
        val r = maxDepth(rot.right)
        if (Math.abs(l - r) > 1)
          ret = false
        1 + math.max(l, r)
      }
    }

    maxDepth(root)
    ret

  }
}
