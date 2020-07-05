/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 从有序链表中删除重复节点
  *
  * 83. Remove Duplicates from Sorted List (Easy)
  *
  * Given 1->1->2, return 1->2.
  * Given 1->1->2->3->3, return 1->2->3.
  *
  * @author 梦境迷离
  * @time 2018年7月25日
  * @version v1.0
  */
object Leetcode_83_ListNode extends App {

  var list = ListNodeConstants.getHasDuplicate

  val listNode = list
  while (list != null) {
    print(list.value + " ")
    list = list.next
  }

  var ret = deleteDuplicates(listNode)
  println

  println(ret)
  while (ret != null) {
    print(ret.value + " ")
    ret = ret.next
  }

  def deleteDuplicates(head: ListNode): ListNode = {
    if (head == null || head.next == null) return head
    head.next = deleteDuplicates(head.next)
    return if (head.value == head.next.value) head.next else head
  }
}
