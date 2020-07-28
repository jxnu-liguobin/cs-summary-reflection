/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 1302. 层数最深叶子节点的和
  *
  * 给你一棵二叉树，请你返回层数最深的叶子节点的和。
  *
  * @see https://github.com/jxnu-liguobin
  * @author 梦境迷离
  * @since 2020-07-28
  * @version 1.0
  */
object Leetcode_1302 extends App {

  val ret = deepestLeavesSum(TreeNodeData.treeData8_1())
  assert(ret == 15)

  /**
    * 976 ms,100.00%
    * 59.2 MB,100.00%
    *
    * @param root
    * @return
    */
  def deepestLeavesSum(root: TreeNode): Int = {
    import scala.collection.mutable
    import scala.collection.mutable.ListBuffer
    import scala.collection.mutable.Queue
    if (root == null) return 0
    if (root.left == null && root.right == null) return root.value
    val nodeQueue = new Queue[TreeNode]()
    nodeQueue.enqueue(root)
    var list: mutable.ListBuffer[Int] = ListBuffer.apply()
    while (nodeQueue.nonEmpty) {
      list = new ListBuffer[Int]
      val levelWeight: Int = nodeQueue.size
      0 until levelWeight foreach { _ =>
        val node: TreeNode = nodeQueue.dequeue()
        list.append(node.value)
        if (node.left != null) nodeQueue.enqueue(node.left)
        if (node.right != null) nodeQueue.enqueue(node.right)
      }
    }
    list.sum
  }
}
