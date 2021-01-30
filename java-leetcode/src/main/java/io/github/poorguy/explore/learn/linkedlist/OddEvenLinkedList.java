/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.linkedlist;

/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode() {}
 * ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
 * this.next = next; } }
 */
class OddEvenLinkedList {
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }

        ListNode oddHead = new ListNode(0, head);
        ListNode oddStatisHead = oddHead;
        ListNode evenHead = new ListNode(0, head.next);
        ListNode evenStatisHead = evenHead;
        ListNode pointer = new ListNode(0, head);
        int count = 0;
        while (pointer.next != null) {
            count++;
            pointer = pointer.next;
            if (count % 2 != 0) {
                oddHead.next = pointer;
                oddHead = oddHead.next;
            } else {
                evenHead.next = pointer;
                evenHead = evenHead.next;
            }
        }
        evenHead.next = null;
        oddHead.next = evenStatisHead.next;
        return oddStatisHead.next;
    }
}
