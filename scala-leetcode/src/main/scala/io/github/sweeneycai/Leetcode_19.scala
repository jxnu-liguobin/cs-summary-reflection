/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import io.github.dreamylost.ListNode

/**
  * 19. 删除链表的倒数第N个节点 (Medium)
  *
  * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
  */
object Leetcode_19 extends App {

  /**
    * 一趟扫描实现
    */
  def removeNthFromEnd(head: ListNode, n: Int): ListNode = {
    if (head == null) null
    else {
      var cur = head
      var target: ListNode = null
      for (_ <- 0 until n) cur = cur.next
      // 找到要删除节点的前一个节点
      while (cur != null) {
        cur = cur.next
        if (target == null) target = head
        else target = target.next
      }
      // 为空说明要删除的是头节点，不为空就要判断是不是尾节点
      if (target == null) head.next
      else if (target.next.next == null) {
        target.next = null
        head
      } else {
        target.next = target.next.next
        head
      }
    }
  }
}
