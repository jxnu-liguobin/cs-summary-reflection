/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * @desciption 输入两个链表，找出它们的第一个公共结点。
 * @author Mr.Li
 */
public class FindFirstCommon {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) return null;
        int len1 = getLength(pHead1);
        int len2 = getLength(pHead2);
        if (len1 > len2) {
            int len = len1 - len2;
            while (len-- > 0) pHead1 = pHead1.next;

        } else {
            int len = len2 - len1;
            while (len-- > 0) pHead2 = pHead2.next;
        }
        while (pHead1 != null && pHead2 != null) {
            if (pHead1.val == pHead2.val) return pHead1;
            else {
                pHead1 = pHead1.next;
                pHead2 = pHead2.next;
            }
        }
        return null;
    }

    public int getLength(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    /** ****************************************************** */
    /** */
    ListNode FindFirstCommonNode2(ListNode pHead1, ListNode pHead2) {
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        while (p1 != p2) {
            p1 = (p1 == null ? pHead2 : p1.next);
            p2 = (p2 == null ? pHead1 : p2.next);
        }
        return p1;
    }

    /**
     * @param pHead1
     * @param pHead2
     * @return
     */
    public ListNode FindFirstCommonNode3(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) {
            return null;
        } else {
            List<ListNode> list = new ArrayList<ListNode>();
            while (pHead1 != null) {
                list.add(pHead1);
                pHead1 = pHead1.next;
            }
            while (pHead2 != null) {
                if (list.contains(pHead2)) {
                    return pHead2;
                } else {
                    pHead2 = pHead2.next;
                }
            }
            return null;
        }
    }
}
