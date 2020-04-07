package io.github.dreamylost

/**
 * 590. N叉树的后序遍历
 *
 * @author liguobin@growingio.com
 * @version 1.0,2020/4/7
 */
object LeetCode_590 extends App {

  val node5 = new Node(5)
  val node6 = new Node(6)
  val node3 = new Node(3)
  val node2 = new Node(2)
  val node4 = new Node(4)
  val node1 = new Node(1)

  node3.children = List(node5, node6)
  node1.children = List(node3, node2, node4)

  println(postorder(node1))

  //N叉树
  class Node(var _value: Int) {
    var value: Int = _value
    var children: List[Node] = List()
  }

  //使用不可变的,leetcode中编译不过，版本问题，2.11.0后为过期
  def postorder2(root: Node): List[Int] = {
    import scala.collection.immutable.Stack
    var ret = List[Int]()
    var stack = Stack[Node]()
    stack = stack.push(root)
    while (stack.nonEmpty) {
      val (head, tail) = stack.pop2
      stack = tail
      ret = ret.:+(head.value)
      if (head.children.nonEmpty) {
        //根+先左后右，出来的顺序就是根+先右后左。翻转后就是左右根
        head.children.foreach(e => stack = stack.push(e))
      }
    }
    ret.reverse
  }

  def postorder(root: Node): List[Int] = {
    import scala.collection.mutable
    var ret = List[Int]()
    val stack = mutable.Stack[Node]()
    stack.push(root)
    while (stack.nonEmpty) {
      val node = stack.pop()
      if (node != null) {
        ret = ret ++ Seq(node.value)
        if (node.children.nonEmpty) {
          //先左后右，出来的顺序就是根先右后左。翻转后就是左右根
          node.children.foreach(stack.push)
        }
      }
    }
    ret.reverse
  }
}
