/* All Contributors (C) 2020 */
package io.github.dreamylost;

/**
 * 请判断一个链表是否为回文链表。
 *
 * @author 梦境迷离
 * @time 2018-09-20
 */
public class Leetcode_234_Double_Pointer {

    /**
     * This can be solved by reversing the 2nd half and compare the two halves. Let's start with an
     * example [1, 1, 2, 1]. In the beginning, set two pointers fast and slow starting at the head.
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast != null) {
            slow = slow.next;
        }
        // 颠倒慢指针
        slow = reverse(slow);
        fast = head;

        while (slow != null) {
            if (fast.value != slow.value) {
                return false;
            }
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }

    public ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
