/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

/**
 * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
 *
 * <p>你应当保留两个分区中每个节点的初始相对位置。【稳定】
 *
 * <p>示例:
 *
 * <p>输入: head = 1->4->3->2->5->2, x = 3 输出: 1->2->2->4->3->5
 *
 * @author 梦境迷离
 * @time 2018-09-24
 */
public class Leetcode_86_Double_Pointer {

    /**
     * 基本思想是维护两个队列，第一个队列存储val小于x的所有节点，第二个队列存储所有剩余节点。 然后将这两个队列连接起来，将第二个队列的尾设置为NULL，否则U将得到TLE。
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {

        ListNode dummy1 = new ListNode(0), dummy2 = new ListNode(0); // 两个链表
        ListNode curr1 = dummy1, curr2 = dummy2; // 两个链表的当前尾部
        while (head != null) {
            if (head.value < x) {
                curr1.next = head;
                curr1 = head;
            } else {
                curr2.next = head;
                curr2 = head;
            }
            head = head.next;
        }
        curr2.next = null; // 避免链表中的循环
        curr1.next = dummy2.next; // 连接两个链表
        return dummy1.next;
    }
}
