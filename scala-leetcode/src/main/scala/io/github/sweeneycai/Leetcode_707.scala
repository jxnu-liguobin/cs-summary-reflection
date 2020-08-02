/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 707. 设计链表
  * 难度：中等
  * 题目要点：单链表实现比较简单，代码中尽量不要使用`null`。
  */
object Leetcode_707 {

  class MyLinkedList() {

    /** Initialize your data structure here. */
    trait Node {
      var value: Int
      var next: Node
    }

    case class ListNode(var value: Int, var next: Node) extends Node {
      override def toString: String = value + " -> " + next.toString
    }

    case object Null extends Node {
      var value: Int = -1
      var next: Node = _

      override def toString: String = "Null"
    }

    val fakeHead: ListNode = ListNode(0, Null)

    def head: Node = fakeHead.next

    var last: Node = Null

    var length = 0

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    def get(index: Int): Int = {
      getNode(index).value
    }

    private def getNode(index: Int): Node = {
      if (index < 0) Null
      else {
        var cur = head
        var n = index
        while (n > 0) {
          cur = cur.next
          n -= 1
        }
        cur
      }
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    def addAtHead(`val`: Int): Boolean = {
      length += 1
      head match {
        case Null =>
          fakeHead.next = ListNode(`val`, Null)
          last = fakeHead.next
          true
        case ListNode(_, _) =>
          val node = ListNode(`val`, Null)
          node.next = fakeHead.next
          fakeHead.next = node
          true
        case _ => false
      }
    }

    /** Append a node of value val to the last element of the linked list. */
    def addAtTail(`val`: Int): Boolean = {
      length += 1
      last match {
        case Null => addAtHead(`val`)
        case ListNode(_, Null) =>
          last.next = ListNode(`val`, Null)
          last = last.next
          true
        case _ => false
      }
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    def addAtIndex(index: Int, `val`: Int): Boolean = {
      if (index > length || index < 0) false
      else if (index == length) addAtTail(`val`)
      else if (index == 0) addAtHead(`val`)
      else {
        val newNode = ListNode(`val`, Null)
        val node = getNode(index - 1)
        newNode.next = node.next
        node.next = newNode
        length += 1
        true
      }
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    def deleteAtIndex(index: Int): Boolean = {
      if (index < 0 || index > length - 1) false
      else if (index == 0) {
        length -= 1
        fakeHead.next = head.next
        true
      } else if (index == length - 1) {
        val nextLast = getNode(length - 2)
        nextLast.next = Null
        last = nextLast
        length -= 1
        true
      } else {
        length -= 1
        val former = getNode(index - 1)
        var delete = former.next
        former.next = delete.next
        delete = null
        true
      }
    }

    override def toString: String = head.toString
  }

  val myLinkedList = new MyLinkedList()
  myLinkedList.addAtHead(1)
  println(myLinkedList)
  myLinkedList.addAtTail(3)
  myLinkedList.addAtTail(3)
  myLinkedList.addAtIndex(1, 2)
  println(myLinkedList.get(0))
  myLinkedList.deleteAtIndex(2)
  println(myLinkedList)
}
