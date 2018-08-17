package cn.edu.jxnu.leetcode.scala

import cn.edu.jxnu.leetcode.TreeNode

import scala.collection.mutable.Queue

/**
 * 层次遍历
 * 使用 BFS 进行层次遍历。不需要使用两个队列来分别存储当前层的节点和下一层的节点，因为在开始遍历一层的节点时，当前队列中的节点数就是当前层的节点数，只要控制遍历这么多节点数，就能保证这次遍历的都是当前层的节点。
 *
 *  637. Average of Levels in Binary Tree (Easy)
 * 一棵树每层节点的平均数
 * @author 梦境迷离
 * @time 2018年8月10日
 * @version v1.0
 */
object Leetcode_637_Tree extends App {

  def averageOfLevels(root: TreeNode): List[Double] = {
    val ret = List()
    if (root == null) return ret
    val queue = Queue[TreeNode]()
    queue :+ root
    while (!queue.isEmpty) {
      var cnt = queue.size
      var sum = 0
      for (i <- 0 until cnt) {
        val node = queue.dequeue()
        sum += node.value
        if (node.left != null) queue :+ node.left//向尾部追加
        if (node.right != null) queue :+ node.right
      }
      ret.+:(sum / cnt)
    }
    ret
  }
}