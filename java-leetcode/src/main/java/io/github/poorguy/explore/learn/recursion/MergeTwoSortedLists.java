/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode() {}
 * ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
 * this.next = next; } }
 */
class MergeTwoSortedLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode head = new ListNode(0);
        ListNode p = head;
        ListNode p1 = l1;
        ListNode p2 = l2;
        while (p1 != null || p2 != null) {
            if (p1 == null) {
                p.next = p2;
                p = p.next;
                p2 = p2.next;
            } else if (p2 == null) {
                p.next = p1;
                p = p.next;
                p1 = p1.next;
            } else {
                if (p1.val < p2.val) {
                    p.next = p1;
                    p = p.next;
                    p1 = p1.next;
                } else {
                    p.next = p2;
                    p = p.next;
                    p2 = p2.next;
                }
            }
        }
        return head.next;
    }
}
