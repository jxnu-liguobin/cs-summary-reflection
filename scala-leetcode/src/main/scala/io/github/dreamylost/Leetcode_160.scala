/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 160. 相交链表
 * A:a1 → a2
 *             ↘
 *               c1 → c2 → c3
 *               ↗
 * B:b1 → b2 → b3
 * 要求：时间复杂度为 O(N)，空间复杂度为 O(1)
 *
 * @author 梦境迷离
 * @since 2021年02月13日
 */
object Leetcode_160 extends App {

  val short = ListNodeConstants.getListNodeOne
  val long = ListNodeConstants.getListNodeTwo
  val ret = getIntersectionNode(short, long)
  print(ret)

  /**
   * 652 ms,85.00%
   * 55.5 MB,20.00%
   * 设 A 的长度为 a + c，B 的长度为 b + c，其中 c 为尾部公共部分长度，可知 a + c + b = b + c + a。
   * 当访问 A 链表的指针访问到链表尾部时，令它从链表 B 的头部开始访问链表 B；同样地，当访问 B 链表的指针访问到链表尾部时，令它从链表 A 的头部开始访问链表 A。
   * 这样就能控制访问 A 和 B 两个链表的指针能同时访问到交点。
   */
  def getIntersectionNode(headA: ListNode, headB: ListNode): ListNode = {
    var l1 = headA
    var l2 = headB
    while (l1 != l2) {
      l1 = if (l1 == null) headB else l1.next
      l2 = if (l2 == null) headA else l2.next
    }
    l1
  }

}
