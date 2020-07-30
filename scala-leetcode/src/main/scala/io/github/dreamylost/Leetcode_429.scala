/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 429. N叉树的层序遍历
  *
  * 给定一个 N 叉树，返回其节点值的层序遍历。 (即从左到右，逐层遍历)。
  *
  * @see https://github.com/jxnu-liguobin
  * @author 梦境迷离
  * @since 2020-07-30
  * @version 1.0
  */
object Leetcode_429 extends App {

  val ret = levelOrder(Node.getNode5_1())
  println(ret)

  //N叉树
  class Node(var _value: Int) {
    var value: Int = _value
    var children: List[Node] = List()
  }

  object Node {
    def getNode5_1(): Node = {
      val node1 = new Node(1)
      val node2 = new Node(2)
      val node3 = new Node(3)
      val node4 = new Node(4)
      val node5 = new Node(5)
      val node6 = new Node(6)

      node1.children = List(node3, node2, node4)
      node3.children = List(node5, node6)

      node1
    }
  }

  /**
    * 976 ms,100.00%
    * 53.5 MB,100.00%
    * @param root
    * @return
    */
  def levelOrder(root: Node): List[List[Int]] = {
    if (root == null) return List.empty[List[Int]]
    import scala.collection.mutable
    var ret = List[List[Int]]()
    val queue = new mutable.Queue[Node]()
    queue.enqueue(root)
    while (queue.nonEmpty) {
      var level = List[Int]()
      val size = queue.size
      (0 until size) foreach { _ =>
        val node = queue.dequeue()
        level = level ::: List(node.value)
        if (node.children != null) {
          node.children.foreach(n => queue.enqueue(n))
        }

      }
      ret = ret ::: List(level)
    }
    ret

  }

}
