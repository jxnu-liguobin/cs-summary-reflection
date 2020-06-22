/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

/**
 * @description 合并2个有序链表
 * @author Mr.Li
 */
public class MergeLinkList {
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = null;
        ListNode listNode11 = new ListNode(1);
        ListNode listNode22 = new ListNode(2);
        ListNode listNode33 = new ListNode(3);
        ListNode listNode44 = new ListNode(4);
        ListNode listNode55 = new ListNode(5);
        listNode11.next = listNode22;
        listNode22.next = listNode33;
        listNode33.next = listNode44;
        listNode44.next = listNode55;
        listNode55.next = null;
        ListNode listNode = new MergeLinkList().Merge(listNode1, listNode11);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    /**
     * @description 普通方法
     * @param list1
     * @param list2
     * @return
     */
    public ListNode Merge(ListNode list1, ListNode list2) {
        ListNode head = null;
        if (list1 == null || list2 == null) {
            return (list1 == null) ? list2 : (list2 == null) ? list1 : null;
        }
        if (list1.val <= list2.val) {
            head = list1;
            list1 = list1.next;
        } else {
            head = list2;
            list2 = list2.next;
        }
        ListNode temp = head;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                temp.next = list1;
                list1 = list1.next;
            } else {
                temp.next = list2;
                list2 = list2.next;
            }
            temp = temp.next;
        }
        // 处理最后一个
        if (list1 != null) {
            temp.next = list1;
        }
        if (list2 != null) {
            temp.next = list2;
        }
        return head;
    }

    /**
     * @description 递归 来自牛客
     * @param list1
     * @param list2
     * @return
     */
    public ListNode Merge2(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        }
        ListNode pMergeHead = null;
        if (list1.val < list2.val) {
            pMergeHead = list1;
            pMergeHead.next = Merge(list1.next, list2);
        } else {
            pMergeHead = list2;
            pMergeHead.next = Merge(list1, list2.next);
        }
        return pMergeHead;
    }

    /**
     * @description 二路归并
     * @param L1
     * @param L2
     * @param L3
     */
}
