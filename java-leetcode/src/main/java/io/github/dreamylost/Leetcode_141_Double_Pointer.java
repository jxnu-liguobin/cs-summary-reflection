/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 给定一个链表，判断链表中是否有环。
 *
 * @author 梦境迷离
 * @time 2018-09-23
 */
public class Leetcode_141_Double_Pointer {

    /**
     * 快慢指针相遇
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode walker = head;
        ListNode runner = head;
        while (runner.next != null && runner.next.next != null) {
            walker = walker.next;
            runner = runner.next.next;
            if (walker == runner) return true;
        }
        return false;
    }

    /**
     * 递归
     *
     * @param head
     * @return
     */
    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) return false;
        if (head.next == head) return true; // 尾的下一个是头结点
        ListNode nextNode = head.next;
        head.next = head;
        boolean isCycle = hasCycle2(nextNode);
        return isCycle;
    }
}
