/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 144. 二叉树的前序遍历(迭代)
  * @author 梦境迷离 dreamylost
  * @since 2020-07-18
  * @version v1.0
  */
object Leetcode_144 extends App {

  val ret = preorderTraversal(TreeNodeData.treeData3_5())
  println(ret)

  /**
    * 544 ms,70.00%
    * 50.6 MB,100.00%
    *
    * @param root
    * @return
    */
  def preorderTraversal(root: TreeNode): List[Int] = {
    if (root == null) return List.empty
    var ret = List.empty[Int]
    var queue = Seq.empty[TreeNode]
    queue = Seq(root) ++ queue
    while (queue.nonEmpty) {
      val node = queue.head
      queue = queue.tail
      ret = ret ++ Seq(node.value)
      if (node.right != null) queue = Seq(node.right) ++ queue
      if (node.left != null) queue = Seq(node.left) ++ queue
    }
    ret

  }

}
