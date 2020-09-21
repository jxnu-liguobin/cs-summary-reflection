/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import io.github.dreamylost.TreeNode

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object BinaryTreeUtils {
  def levelOrder(root: TreeNode): Unit = {
    val listBuffer: ListBuffer[List[Int]] = ListBuffer.empty[List[Int]]
    val queue: mutable.Queue[TreeNode] = mutable.Queue(root)

    while (queue.nonEmpty) {
      val tmpBuffer: ListBuffer[Int] = ListBuffer.empty
      val length = queue.length
      for (_ <- 0 until length) {
        val node = queue.dequeue()
        tmpBuffer.append(node.value)
        if (node.left != null) queue.enqueue(node.left)
        if (node.right != null) queue.enqueue(node.right)
      }
      listBuffer.append(tmpBuffer.toList)
    }

    println(listBuffer)
  }
}
