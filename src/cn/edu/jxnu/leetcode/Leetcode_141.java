package cn.edu.jxnu.leetcode;

/**
 * @author 梦境迷离
 * @description 判断链表是否存在环 使用双指针，一个指针每次移动一个节点，一个指针每次移动两个节点，如果存在环，那么这两个指针一定会相遇。
 * @time 2018年4月7日
 */
public class Leetcode_141 {
	
	public boolean hasCycle(ListNode head) {
		if (head == null)
			return false;
		ListNode l1 = head, l2 = head.next;
		while (l1 != null && l2 != null) {
			if (l1 == l2)
				return true;
			l1 = l1.next;
			if (l2.next == null)
				break;
			l2 = l2.next.next;
		}
		return false;
	}
}
