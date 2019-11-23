package io.github.dreamylost.tooffer;

/**
 * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，
 * 重复的结点不保留，返回链表头指针。
 * 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
 */
public class T56 {

    public ListNode deleteDuplication(ListNode pHead) {
        // 只有 0 个或 1 个节点，则返回。
        if (null == pHead || pHead.next == null) {
            return pHead;
        }
        // 当前节点是重复节点
        if (pHead.val == pHead.next.val) {
            ListNode pNode = pHead.next;
            while (pNode != null && pHead.val == pNode.val) {
                pNode = pNode.next;
            }
            return deleteDuplication(pNode);
        } else {
            // 当前节点不是重复节点。
            // 保留当前节点，从下一个节点开始递归
            pHead.next = deleteDuplication(pHead.next);
            return pHead;
        }
    }
}
