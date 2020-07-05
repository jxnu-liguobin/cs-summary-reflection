/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

/**
 * @description 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表 1->2->3->3->4->4 ->5 处理后为
 *     1->2->5
 */
public class DeleteDuplication {
    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null) {
            return null;
        }
        // 保存结果
        ListNode result;
        // 当前执行节点
        ListNode curr = pHead;
        // 头节点
        ListNode head = new ListNode(0);
        // 记住头节点位置
        head.next = pHead;
        result = head;
        while (curr != null) {
            if (curr.next != null && curr.next.val == curr.val) {
                // 寻找最后一个不等值的位置
                while (curr.next != null && curr.next.val == curr.val) {
                    curr = curr.next;
                }
                curr = curr.next;
                head.next = curr;
            } else {
                head = head.next;
                curr = curr.next;
            }
        }
        return result.next;
    }
}
