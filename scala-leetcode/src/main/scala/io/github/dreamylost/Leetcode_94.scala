/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 94. 二叉树的中序遍历
  * 给定一个二叉树，返回它的中序遍历。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-26
  * @version v1.0
  */
object Leetcode_94 extends App {

  val ret = inorderTraversal(TreeNodeData.treeData5_1())
  println(ret)

  /**
    * 注意第二个while需要将当前节点的所有左节点入栈，第一个出栈的就成了最左叶子
    * 556 ms,60.00%
    * 51.1 MB,100.00%
    *
    * @param root
    * @return
    */
  def inorderTraversal(root: TreeNode): List[Int] = {
    var ret = List[Int]()
    var stack = List[TreeNode]()
    if (root == null) return ret
    var cur = root
    while (cur != null || stack.nonEmpty) {
      while (cur != null) {
        stack = cur :: stack
        cur = cur.left
      }
      val (node, s) = stack.head -> stack.tail
      stack = s
      ret = ret ++ Seq(node.value)
      cur = node.right
    }
    ret
  }
}
