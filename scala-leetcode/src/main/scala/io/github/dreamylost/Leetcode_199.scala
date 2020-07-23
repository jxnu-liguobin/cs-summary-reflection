/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 199. 二叉树的右视图
  *
  * @see https://github.com/jxnu-liguobin
  * @author dreamylost
  * @since 2020-07-23
  * @version v1.0
  */
object Leetcode_199 extends App {

  val ret = rightSideView(TreeNodeData.treeData3_5())
  println(ret)

  /**
    * 568 ms,88.89%
    * 53.4 MB,100.00%
    *
    * @param root
    * @return
    */
  def rightSideView(root: TreeNode): List[Int] = {
    if (root == null) return Nil
    if (root.left == null && root.right == null) return List(root.value)
    val nodeQueue = new scala.collection.mutable.Queue[TreeNode]()
    val list = new scala.collection.mutable.ListBuffer[Int]
    nodeQueue.enqueue(root)
    while (nodeQueue.nonEmpty) {
      val levelWeight: Int = nodeQueue.size
      0 until levelWeight foreach { i =>
        val node: TreeNode = nodeQueue.dequeue()
        if (i == levelWeight - 1) {
          list.append(node.value)
        }
        if (node.left != null) nodeQueue.enqueue(node.left)
        if (node.right != null) nodeQueue.enqueue(node.right)
      }
    }

    list.toList
  }

}
