package cn.edu.jxnu.leetcode.scala

import cn.edu.jxnu.leetcode.ListNode
import cn.edu.jxnu.leetcode.ListNodeConstants
import scala.language.implicitConversions

/**
 *
 * 链表反转
 *
 * 206. Reverse Linked List (Easy)
 *
 * 递归
 * @author 梦境迷离
 * @time 2018年7月23日
 * @version v1.0
 */
object Leetcode_206_ListNode extends App {

  /**
   * 友情提示，修改Java代码的时候，由于Scala已经编译过，此时会不找不到Java中新修改的代码的部分，所以需要对Scala代码进行restart complier
   * 如果不是Scala项目可能会没有该选项，此时需要使用coverage as转换为Scala application
   */

  var list = ListNodeConstants.getListNodeOne
  val listNode = list
  while (list != null) {
    print(list.value + " ")
    list = list.next
  }
  println
  var ret = reverseList(listNode)
  println(ret)
  while (ret != null) {
    print(ret.value + " ")
    ret = ret.next
  }

  def reverseList(head: ListNode): ListNode = {
    if (head == null || head.next == null) {
      return head
    }
    val next = head.next
    val newHead = reverseList(next)
    next.next = head
    head.next = null
    newHead
  }
}