/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode() {}
 * ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
 * this.next = next; } }
 */
class SwapNodesInPairs {
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        ListNode pointer = head;
        ListNode t = swapPairs(pointer.next.next);
        ListNode next = pointer.next;
        next.next = pointer;
        pointer.next = t;
        return next;
    }
}
