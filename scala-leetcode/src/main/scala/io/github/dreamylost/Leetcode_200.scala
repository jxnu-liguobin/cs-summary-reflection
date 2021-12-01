/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 206. 反转链表
 *
 * @author 梦境迷离
 * @since 2021/12/1
 * @version 1.0
 */
object Leetcode_200 {

  def reverseList(head: ListNode): ListNode = {
    if (head == null || head.next == null) return head
    val newHead = reverseList(head.next)
    head.next.next = head
    head.next = null
    newHead
  }

  /**
   * 500 ms,18.00%
   * 52.6 MB,94.00%
   *
   * @param head
   * @return
   */
  def reverseList2(head: ListNode): ListNode = {
    var newHead: ListNode = null
    var curHead = head
    while (curHead != null) {
      val tail = curHead.next //保存其余节点，防止丢尾部
      curHead.next = newHead //断开链，链接新的头节点
      newHead = curHead // 换头节点，头左移动
      curHead = tail // 移动旧链
    }
    newHead
  }

}
