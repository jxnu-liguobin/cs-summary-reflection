/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 653. 两数之和 IV - 输入 BST
  *
  * 给定一个二叉搜索树和一个目标结果，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-15
  * @version v1.0
  */
object Leetcode_653 extends App {

  val ret = findTarget(TreeNodeData.treeData3_4(), 4)
  println(ret)

  /**
    * 2060 ms,16.67%
    * 54.7 MB,100.00%
    *
    * @param root
    * @param k
    * @return
    */
  def findTarget(root: TreeNode, k: Int): Boolean = {
    def inorderTraversal(root: TreeNode): Seq[Int] = {
      var ret = Seq[Int]()
      var stack = List[TreeNode]()
      if (root == null) return ret
      var cur = root
      while (cur != null || stack.nonEmpty) {
        while (cur != null) {
          //头进头出模拟栈
          stack = cur :: stack
          cur = cur.left
        }
        val (node, s) = (stack.head, stack.tail)
        stack = s
        ret = ret ++ Seq(node.value)
        cur = node.right
      }
      ret
    }

    val nums = inorderTraversal(root)
    var i = 0
    var j = nums.length - 1
    while (i < j) {
      if (nums(i) + nums(j) == k) {
        return true
      } else if (nums(i) + nums(j) < k) {
        i += 1
      } else {
        j -= 1
      }
    }
    false
  }

}
