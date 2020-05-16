package io.github.dreamylost

/**
  * 589. N叉树的前序遍历
  *
 * @author liguobin@growingio.com
  * @version 1.0,2020/4/9
  */
object Leetcode_589 extends App {

  val node5 = new Node(5)
  val node6 = new Node(6)
  val node3 = new Node(3)
  val node2 = new Node(2)
  val node4 = new Node(4)
  val node1 = new Node(1)

  node3.children = List(node5, node6)
  node1.children = List(node3, node2, node4)

  println(preorder(node1))

  //N叉树
  class Node(var _value: Int) {
    var value: Int = _value
    var children: List[Node] = List()
  }

  def preorder(root: Node): List[Int] = {
    import scala.collection.mutable
    var ret = List[Int]()
    val stack = mutable.Stack[Node]()
    stack.push(root)
    while (stack.nonEmpty) {
      val node = stack.pop()
      if (node != null) {
        ret = ret ++ Seq(node.value)
        if (node.children.nonEmpty) {
          //先右后左，出来的顺序就是根先左后右
          node.children.reverse.foreach(stack.push)
        }
      }
    }
    ret
  }
}
