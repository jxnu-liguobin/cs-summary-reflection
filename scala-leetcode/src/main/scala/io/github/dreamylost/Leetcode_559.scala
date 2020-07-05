/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 559. N叉树的最大深度
  *
  * @author 梦境迷离
  * @since 2020-04-23
  * @version v1.0
  */
object Leetcode_559 extends App {

  val node5 = new Node(5)
  val node6 = new Node(6)
  val node3 = new Node(3)
  val node2 = new Node(2)
  val node4 = new Node(4)
  val node1 = new Node(1)

  node3.children = List(node5, node6)
  node1.children = List(node3, node2, node4)

  println(maxDepth(node1))
  println(maxDepth2(node1))

  //N叉树
  class Node(var _value: Int) {
    var value: Int = _value
    var children: List[Node] = List()
  }

  //DFS
  def maxDepth(root: Node): Int = {
    if (root == null) return 0
    var depth = 0
    for (i <- root.children.indices) {
      depth = Math.max(depth, maxDepth(root.children(i)))
    }
    depth + 1
  }

  //层序遍历
  def maxDepth2(root: Node): Int = {
    if (root == null) return 0
    if (root.children.isEmpty) return 1
    var depth = 0
    var queue = root :: List()
    while (queue.nonEmpty) {
      depth += 1
      queue.indices foreach { _ =>
        val node = queue.take(1).headOption
        queue = queue.drop(1)
        if (node.fold(false)(_.children.nonEmpty)) {
          queue = queue ::: node.fold(List[Node]())(_.children)
        }
      }
    }
    depth
  }

  //纯函数式，竟然会快点
  def maxDepth3(root: Node): Int = {
    val depth: scala.collection.mutable.ArrayBuffer[Int] =
      scala.collection.mutable.ArrayBuffer[Int]()
    Option(root).foldRight(depth) { (r, depth) =>
      r match {
        case null => depth.+=(0)
        case r if r.children.isEmpty => depth.+=(1)
        case r =>
          var queue = r :: List()
          while (queue.nonEmpty) {
            depth.+=(1)
            queue.indices foreach { _ =>
              val node = queue.take(1).headOption
              queue = queue.drop(1)
              if (node.fold(false)(_.children.nonEmpty)) {
                queue = queue ::: node.fold(List[Node]())(_.children)
              }
            }
          }
          depth
      }
    }
    depth.sum
  }
}
