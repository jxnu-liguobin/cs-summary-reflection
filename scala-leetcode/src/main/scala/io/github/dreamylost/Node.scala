/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import com.google.gson.annotations.Expose

/**
  * n叉树结构
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-12
  * @version v1.0
  */
class Node(var _value: Int) extends PrintlnSupport {

  @Expose var value: Int = _value
  @Expose var left: Node = null
  @Expose var right: Node = null
  @Expose var next: Node = null

  override def toString = prettyPrinting(this)
}

object Node {

  def getNode_7(): Node = {
    val node5 = new Node(5)
    val node6 = new Node(6)
    val node3 = new Node(3)
    val node2 = new Node(2)
    val node4 = new Node(4)
    val node1 = new Node(1)
    val node7 = new Node(7)

    node1.left = node2
    node1.right = node3

    node2.left = node4
    node2.right = node5

    node3.left = node6
    node3.right = node7

    node1
  }
}
