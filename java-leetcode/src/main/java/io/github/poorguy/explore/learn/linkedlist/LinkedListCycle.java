/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.linkedlist;

/** quick slow pointer */
public class LinkedListCycle {
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode quick = head.next;
        while (quick != null && quick.next != null) {
            if (slow == quick) {
                return true;
            } else {
                slow = slow.next;
                quick = quick.next.next;
            }
        }
        return false;
    }
}
