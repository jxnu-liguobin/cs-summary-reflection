package io.github.dreamylost

/**
 * 给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
 * 请你返回该链表所表示数字的 十进制值 。
 *
 * @author 梦境迷离
 * @since 2020-01-12
 * @version v1.0
 */
object LeetCode1299 extends App {

  class ListNode(_x: Int = 0) {
    var next: ListNode = _
    val x: Int = _x
  }

  //[1,0,0,1,0,0,1,1,1,0,0,0,0,0,0]
  /**
   * 如果一个十进制的数字，542542要和11拼接，需要542*10+1 = 5421542∗10+1=5421
   * 如果一个二进制的数字，101101要和11拼接，需要101*10+1 = 1011101∗10+1=1011 （注意按照二进制的方法计算）
   */
  def getDecimalValue(head: ListNode): Int = {
    var tmp: ListNode = head
    var `val` = 0
    //链表移动到右侧下一个节点的过程，其实就是二进制数左移1位的结果。
    while (tmp != null) {
      `val` = `val` << 1 | tmp.x
      tmp = tmp.next
    }
    `val`
  }

  println(getDecimalValue(new ListNode(1)))

}
