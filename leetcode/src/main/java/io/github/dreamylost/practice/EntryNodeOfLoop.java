package io.github.dreamylost.practice;

/**
 * @description 一个链表中包含环，请找出该链表的环的入口结点。
 * @author Mr.Li
 *
 */
public class EntryNodeOfLoop {

	/**
	 * @description X：链表头结点，Y：还入口结点，Z：第一次相遇结点
	 *              第一次相遇时slow走过的距离：a+b，fast走过的距离：a+b+c+b
	 *              fast走的距离是slow的2倍，2(a+b) = a+b+c+b，可以得到a=c（这个结论很重要！）
	 * @param pHead
	 * @return
	 */
	public ListNode entryNodeOfLoop(ListNode pHead) {

		if (pHead == null || pHead.next == null)
			return null;
		// 第一步，找环中相汇点。分别用p1，p2指向链表头部，p1每次走一步，p2每次走二步，直到p1==p2找到在环中的相汇点。
		ListNode slow = pHead;
		ListNode fast = pHead;
		while (fast != null && fast.next != null) {
			// 先判断有没有环
			slow = slow.next;
			fast = fast.next.next;
			// 相遇了，有环
			if (slow == fast) {
				ListNode p = pHead;
				ListNode q = slow;
				while (p != q) {
					p = p.next;
					q = q.next;
				}
				if (p == q)
					return q;
			}
		}
		// 没有环
		return null;

	}
}