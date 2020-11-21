/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 148. 排序链表
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 * 进阶： 你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-11-21
 *  @version 1.0
 */
class Leetcode_148 {
    companion object {

        /**
         * 归并
         * 428 ms,8.33%
         * 44 MB,16.67%
         */
        fun sortList(head: ListNode?): ListNode? {
            if (head?.next == null) return head
            var slowp = head
            var fastp = head.next!!.next
            while (fastp?.next != null) {
                slowp = slowp?.next
                fastp = fastp.next?.next
            }
            val r = sortList(slowp?.next)
            slowp?.next = null
            val l = sortList(head)
            return mergeList(l, r)
        }

        private fun mergeList(l: ListNode?, r: ListNode?): ListNode? {
            var l = l
            var r = r
            val tmpHead: ListNode? = ListNode(Int.MIN_VALUE)
            var p = tmpHead
            while (l != null && r != null) {
                if (l.`val` < r.`val`) {
                    p?.next = l
                    l = l.next
                } else {
                    p?.next = r
                    r = r.next
                }
                p = p?.next
            }
            p?.next = l ?: r
            return tmpHead?.next
        }

        /**
         * 快排 超时
         */
        fun sortList2(head: ListNode?): ListNode? {
            if (head?.next == null) return head
            val newHead = ListNode(Int.MIN_VALUE)
            newHead.next = head
            return quickSort(newHead, null)
        }

        private fun quickSort(head: ListNode?, end: ListNode?): ListNode? {
            if (head == end || head?.next == end || head?.next?.next == end) return head
            val tmpHead: ListNode? = ListNode(Int.MIN_VALUE)
            // 划分点
            val partition = head?.next
            var p = partition
            var tp = tmpHead
            // 小于划分点的添加到临时表后面
            while (p != null && p.next !== end) {
                if (p.next?.`val`!! < partition?.`val`!!) {
                    tp?.next = p.next
                    tp = tp?.next
                    p.next = p.next?.next
                } else {
                    p = p.next
                }
            }
            // 合并临时链表和原链表，将原链表接到临时链表后面即可
            tp?.next = head?.next
            // 将临时链表插回原链表，注意是插回！（不做这一步在对右半部分处理时就断链了）
            head?.next = tmpHead?.next
            quickSort(head, partition)
            quickSort(partition, end)
            return head?.next
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val head = ListNode(4)
            head.next = ListNode(2)
            head.next!!.next = ListNode(1)
            head.next!!.next?.next = ListNode(3)
            var ret = sortList2(head)
            while (ret != null) {
                println(ret.`val`)
                ret = ret.next
            }
        }
    }
}
