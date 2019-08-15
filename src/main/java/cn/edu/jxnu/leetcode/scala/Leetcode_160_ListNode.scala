package cn.edu.jxnu.leetcode.scala

import cn.edu.jxnu.leetcode.{ListNode, ListNodeConstants}

/**
 *
 * 链表是空节点，或者有一个值和一个指向下一个链表的指针，因此很多链表问题可以用递归来处理。
 * 找出两个链表的交点
 *160. Intersection of Two Linked Lists (Easy)
 * *
 * A:          a1 → a2
 * ↘
 * c1 → c2 → c3
 * ↗
 * B:    b1 → b2 → b3
 * 要求：时间复杂度为 O(N)，空间复杂度为 O(1)
 *
 * @author 梦境迷离
 * @time 2018年7月23日
 * @version v1.0
 */
object Leetcode_160_ListNode extends App {
  /**
   * 设 A 的长度为 a + c，B 的长度为 b + c，其中 c 为尾部公共部分长度，可知 a + c + b = b + c + a。
   * 当访问 A 链表的指针访问到链表尾部时，令它从链表 B 的头部开始访问链表 B；同样地，当访问 B 链表的指针访问到链表尾部时，令它从链表 A 的头部开始访问链表 A。
   * 这样就能控制访问 A 和 B 两个链表的指针能同时访问到交点。
   */

  val short = ListNodeConstants.getListNodeOne()
  val long = ListNodeConstants.getListNodeTwo()
  val ret = getIntersectionNode(short, long)
  print(ret)

  def getIntersectionNode(headA: ListNode, headB: ListNode): ListNode = {
    var l1 = headA
    var l2 = headB
    while (l1 != l2) {
      l1 = if (l1 == null) headB else l1.next
      l2 = if (l2 == null) headA else l2.next
    }
    l1.next = null
    l1
  }

}