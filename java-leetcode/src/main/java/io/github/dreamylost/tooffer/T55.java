/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.tooffer;

import java.util.HashMap;

/** 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。 */
public class T55 {

    /** 两个指针分别从链表头和相遇点出发，最后一定相遇于环入口 https://www.e-learn.cn/content/qita/780537 */
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (null == pHead) {
            return null;
        }
        HashMap<ListNode, Integer> map = new HashMap<>();
        map.put(pHead, 1);
        while (null != pHead.next) {
            if (map.containsKey(pHead.next)) {
                return pHead.next;
            }
            map.put(pHead.next, 1);
            pHead = pHead.next;
        }
        return null;
    }
}
