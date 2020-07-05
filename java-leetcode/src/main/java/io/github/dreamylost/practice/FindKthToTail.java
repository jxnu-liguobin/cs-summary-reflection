/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * @description 输入一个链表，输出该链表中倒数第k个结点。
 * @author Mr.Li
 */
public class FindKthToTail {
    public static void main(String[] args) {

        ListNode2 listNode2 = new ListNode2(0);
        ListNode2 listNode3 = new ListNode2(1);
        listNode2.next = listNode3;

        ListNode2 listNode4 = new ListNode2(3);
        listNode3.next = listNode4;

        ListNode2 listNode5 = new ListNode2(5);
        listNode4.next = listNode5;

        ListNode2 listNode6 = new ListNode2(7);
        listNode5.next = listNode6;
        FindKthToTail solution = new FindKthToTail();
        System.out.println(solution.FindKthToTail3(listNode2, 2).val);
    }

    /**
     * @description p运动k， p再次运行length-k,head也运动length-k,则剩余k个 即 倒数第k个
     * @param head
     * @param k
     * @return
     */
    public ListNode2 FindKthToTail3(ListNode2 head, int k) {

        ListNode2 p = head;
        while (k-- != 0) {
            if (p == null) return null;
            p = p.next;
        }
        while (p != null) {
            p = p.next;
            head = head.next;
        }
        return head;
    }

    public ListNode2 FindKthToTail2(ListNode2 listNode2, int k) {
        if (listNode2 == null) {
            return null;
        }
        int count = 0;
        java.util.Stack<ListNode2> stack = new java.util.Stack<>();
        ListNode2 node2 = null;
        while (listNode2 != null) {
            stack.push(listNode2);
            listNode2 = listNode2.next;
            count++;
        }
        if (k > count) {
            return null;
        }
        while (k-- > 0) {
            node2 = stack.pop();
        }
        return node2;
    }
}

class ListNode2 {
    int val;
    ListNode2 next = null;

    ListNode2(int val) {
        this.val = val;
    }
}
