package io.github.dreamylost

//给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
//
// 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
//
// 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
//
// 示例：
//
// 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
//输出：7 -> 0 -> 8
//原因：342 + 465 = 807
//
//
/**
  * @author 梦境迷离
  * @time 2019-08-14
  * @version v2.0
  */
object Leetcode_2_ListNode {

  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    var link1 = l1
    var link2 = l2
    val root = new ListNode(0)
    var cursor = root
    var carry = 0
    while (link1 != null || link2 != null || carry != 0) {
      val l1Val = if (link1 != null) link1.value else 0
      val l2Val = if (link2 != null) link2.value else 0
      val sumVal = l1Val + l2Val + carry
      carry = sumVal / 10
      val sumNode = new ListNode(sumVal % 10)
      cursor.next = sumNode
      cursor = sumNode
      if (link1 != null) link1 = link1.next
      if (link2 != null) link2 = link2.next
    }
    root.next
  }
}
