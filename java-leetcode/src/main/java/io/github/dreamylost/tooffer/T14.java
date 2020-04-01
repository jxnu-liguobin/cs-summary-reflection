package io.github.dreamylost.tooffer;

import java.util.Stack;

/**
 * 输入一个链表，输出该链表中倒数第k个结点。
 * [1,2,3,4,5,6,7,8]
 * k = 2
 */
public class T14 {

    //使用栈
    public ListNode FindKthToTail(ListNode head, int k) {
        if (k <= 0) {
            return null;
        }
        if (head == null) {
            return null;
        }
        Stack<ListNode> stack = new Stack<>();
        while (head.next != null) {
            stack.push(head);
            head = head.next;
        }
        stack.push(head);
        int temp = 0;
        while (!stack.empty()) {
            ListNode listNode = stack.pop();
            temp++;
            if (temp == k) {
                return listNode;
            }
        }
        return null;
    }

    /**
     * 最佳代码：Java代码，通过校验。代码思路如下：两个指针，先让第一个指针和第二个指针都指向头结点，然后再让第一个指正走(k-1)步，到达第k个节点。
     * 然后两个指针同时往后移动，当第一个结点到达末尾的时候，第二个结点所在位置就是倒数第k个节点了
     */
    public ListNode FindKthToTail2(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        ListNode pre = head;
        ListNode last = head;
        for (int i = 1; i < k; i++) {
            if (pre.next != null) {
                pre = pre.next;
            } else {
                return null;
            }
        }
        while (pre.next != null) {
            pre = pre.next;
            last = last.next;
        }
        return last;
    }

}
