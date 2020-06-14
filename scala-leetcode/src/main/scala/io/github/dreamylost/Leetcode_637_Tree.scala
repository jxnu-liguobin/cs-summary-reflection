package io.github.dreamylost

/**
  * 层次遍历
  * 使用 BFS 进行层次遍历。不需要使用两个队列来分别存储当前层的节点和下一层的节点，因为在开始遍历一层的节点时，当前队列中的节点数就是当前层的节点数，只要控制遍历这么多节点数，就能保证这次遍历的都是当前层的节点。
  *
 *  637. Average of Levels in Binary Tree (Easy)
  * 一棵树每层节点的平均数
  *
 * @author 梦境迷离
  * @time 2018年8月10日
  * @version v1.0
  */
object Leetcode_637_Tree extends App {

  val ret = averageOfLevels(TreeNodeData.treeData5_1())

  ret.foreach(println)

  /**
    * 993类似
    * 层序遍历时按层处理元素
    *
   * 848 ms,75.00%
    * 53.8 MB,100.00%
    *
   * @param root
    * @return
    */
  def averageOfLevels(root: TreeNode): Array[Double] = {
    import scala.collection.immutable.Queue
    var ret = Array[Double]()
    if (root == null) return ret
    var queue = Queue[TreeNode]()
    queue = queue :+ root
    while (queue.nonEmpty) {
      val cnt = queue.size
      var sum = 0d
      for (_ <- 0 until cnt) {
        val (node, q) = queue.dequeue
        queue = q
        sum += node.value
        if (node.left != null) queue = queue.enqueue(node.left)
        if (node.right != null) queue = queue.enqueue(node.right)
      }
      ret = ret ++ Array[Double](sum / cnt.asInstanceOf[Double])
    }
    ret
  }
}
