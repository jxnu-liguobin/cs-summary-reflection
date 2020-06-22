package io.github.dreamylost

/**
  * 镜像二叉树非递归
  *
  * @author liguobin@growingio.com
  * @version 1.0,2020/3/18
  */
object Leetcode_Interview_27 extends App {

  //层次遍历-QUEUE
  def mirrorTree(root: TreeNode): TreeNode = {
    import scala.collection.mutable
    val queue = mutable.Queue[TreeNode]()
    queue.enqueue(root)
    while (queue.nonEmpty) {
      val tmp = queue.dequeue()
      if (tmp != null) {
        val left = tmp.left
        tmp.left = tmp.right
        tmp.right = left
        queue.enqueue(tmp.right)
        queue.enqueue(tmp.left)
      }
    }
    root
  }

}
