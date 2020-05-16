package io.github.dreamylost;

/**
 * 19. 删除链表的倒数第N个节点
 *
 * <p>给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 *
 * @author 梦境迷离
 * @time 2018-09-25
 */
public class Leetcode_19_Double_Pointer {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode start = new ListNode(0);
        ListNode slow = start, fast = start;
        slow.next = head;
        // 让快指针先走N步
        for (int i = 1; i <= n + 1; i++) {
            fast = fast.next;
        }
        // 快指针为null时，慢指针在倒数第N个结点前
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        // 进行删除
        slow.next = slow.next.next;
        return start.next;
    }
}
