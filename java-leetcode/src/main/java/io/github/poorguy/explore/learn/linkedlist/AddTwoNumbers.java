/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.linkedlist;

/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode() {}
 * ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
 * this.next = next; } }
 */
class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode prev = new ListNode(0, null);
        ListNode headPointer = prev;
        while (l1 != null && l2 != null) {
            ListNode t = new ListNode((l1.val + l2.val + carry) % 10, null);
            if (l1.val + l2.val + carry >= 10) {
                carry = 1;
            } else {
                carry = 0;
            }
            prev.next = t;
            prev = prev.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null && l2 == null) {
            ListNode t = new ListNode((l1.val + carry) % 10, null);
            if (l1.val + carry >= 10) {
                carry = 1;
            } else {
                carry = 0;
            }
            prev.next = t;
            prev = prev.next;
            l1 = l1.next;
        }
        while (l1 == null && l2 != null) {
            ListNode t = new ListNode((l2.val + carry) % 10, null);
            if (l2.val + carry >= 10) {
                carry = 1;
            } else {
                carry = 0;
            }
            prev.next = t;
            prev = prev.next;
            l2 = l2.next;
        }
        if (carry == 1) {
            ListNode t = new ListNode(1, null);
            prev.next = t;
            prev = prev.next;
        }
        return headPointer.next;
    }
}
