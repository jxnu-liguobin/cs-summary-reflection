/* All Contributors (C) 2021 */
package io.github.poorguy.explore.learn.recursion;

/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode() {}
 * ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
 * this.next = next; } }
 */
class ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        return reverseListRecursively(head);
    }

    // 自己在思考的时候，链表结点不移动，变的只有next。1->2变成1<-2这样，而不是想成2->1
    public ListNode reverseListIteratively(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    public ListNode reverseListRecursively(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node = reverseListRecursively(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }
}
