package io.github.dreamylost

/**
 *
 * 归并两个有序的链表
 *
 * 21. Merge Two Sorted Lists (Easy)
 *
 * @author 梦境迷离
 * @time 2018年7月25日
 * @version v1.0
 */
object Leetcode_21_ListNode extends App {

    var listA = ListNodeConstants.getOrderListA
    var listB = ListNodeConstants.getOrderListB
    val listNodeA = listA
    val listNodeB = listB
    while (listA != null) {
        print(listA.value + " ")
        listA = listA.next
    }
    println
    while (listB != null) {
        print(listB.value + " ")
        listB = listB.next
    }
    println
    var ret = mergeTwoLists(listNodeA, listNodeB)
    println(ret)
    while (ret != null) {
        print(ret.value + " ")
        ret = ret.next
    }

    def mergeTwoLists(l1: ListNode, l2: ListNode): ListNode = {
        if (l1 == null) return l2
        if (l2 == null) return l1
        if (l1.value < l2.value) {
            l1.next = mergeTwoLists(l1.next, l2)
            return l1
        } else {
            l2.next = mergeTwoLists(l1, l2.next)
            return l2
        }
    }

}