/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 117. 填充每个节点的下一个右侧节点指针 II
 *
 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
 *
 * 初始状态下，所有 next 指针都被设置为 NULL。
 *
 * @author 梦境迷离 dreamylost
 * @since 2020-07-13
 * @version v1.0
 */
object Leetcode_117 extends App {

  val ret = connect(Node.getNode_7())
  println(ret)

  /**
   * 层序遍历链接每一层
   *
   * 递归与116类似，区别：不是完全二叉树
   *
   * 636 ms,20.00%
   * 52.9 MB,100.00%
   *
   * @param root
   * @return
   */
  def connect(root: Node): Node = {
    if (root == null) return null
    var queue = Seq[Node]()
    queue = queue ++ Seq(root)
    while (queue.nonEmpty) {
      val size = queue.size
      for (i <- 0 until size) {
        val node = queue.head
        queue = queue.tail
        //确保只在当前层级结束时才建立下一个指针
        if (i < size - 1) {
          node.next = queue.head
        }

        if (node.left != null) queue = queue ++ Seq(node.left)
        if (node.right != null) queue = queue ++ Seq(node.right)
      }
    }

    root
  }

  /**
   * 不使用队列，把每层都看成列表
   *
   * 556 ms,80.00%
   * 53.2 MB,100.00%
   *
   * @param root
   * @return
   */
  def connect2(root: Node) = {
    var head: Node = root
    while (head != null) {
      //链表头
      val level = new Node(Int.MinValue)
      var tail = level
      while (head != null) {
        if (head.left != null) {
          tail.next = head.left
          tail = tail.next
        }
        if (head.right != null) {
          tail.next = head.right
          tail = tail.next
        }
        head = head.next
      }
      //每次head都从链表头开始
      head = level.next
    }
    root
  }

}
