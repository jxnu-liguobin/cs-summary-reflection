package cn.edu.jxnu.practice;

/**
 * @description 输入一个链表，反转链表后，输出链表的所有元素。
 * @author Mr.Li
 * 
 */
public class ReverseLinkedList {
	public static void main(String[] args) {
		ListNode head = new ListNode(0);
		ListNode listNode3 = new ListNode(1);
		head.next = listNode3;

		ListNode listNode4 = new ListNode(3);
		listNode3.next = listNode4;

		ListNode listNode5 = new ListNode(5);
		listNode4.next = listNode5;

		ListNode listNode6 = new ListNode(7);
		listNode5.next = listNode6;
		new ReverseLinkedList();
		ListNode hListNode = ReverseLinkedList.reverseList3(head);
		while (hListNode != null) {
			System.out.println(hListNode.val);
			hListNode = hListNode.next;

		}
	}

	/**
	 * @description 递归法
	 * @param pHead
	 * @return
	 */
	public static ListNode reverseList(ListNode pHead) {
		// 如果链表为空或者链表中只有一个元素
		if (pHead == null || pHead.next == null)
			return pHead;
		// 先反转后面的链表，走到链表的末端结点
		ListNode pReverseNode = reverseList(pHead.next);
		// 再将当前节点设置为后面节点的后续节点
		pHead.next.next = pHead;
		pHead.next = null;
		return pReverseNode;
	}

	/**
	 * @description 循环法
	 * @param pHead
	 * @return
	 */
	public static ListNode reverseList2(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode pNode = head;// 1、当前指针
		ListNode newHead = null;// 新链表的头指针
		ListNode preNode = null;// 当前指针的前一个结点
		while (pNode != null) {
			ListNode pNewNode = pNode.next;// 2、链断开之前一定要保存断开位置后边的结点
			pNode.next = preNode;
			// 如果当前节点的下一个节点为空，那么当前节点就是原链表的尾节点，也即反转后新链表的头节点
			if (pNewNode == null) { // 3、当pNewNode为空时，说明当前结点为尾节点
				newHead = pNode;
			}
			preNode = pNode;
			pNode = pNewNode;
		}
		return newHead;

	}

	/**
	 * 第三种方法 每次都将原第一个结点之后的那个结点放在新的表头后面。 对12345，先保存1的下一个节点，新链表变为1345，然后把2链接在1前面即可
	 */
	public static ListNode reverseList3(ListNode pHead) {
		if (pHead == null)
			return pHead;
		ListNode res, first, temp;
		res = new ListNode(-1);
		res.next = pHead;// 无数据的头结点
		first = res.next; // first 为第一个结点=1 带头结点的新链表：[-1,1,2,3,4,5]
		while (first.next != null) // temp为当前等待插入的
		{
			// 起始，1，2，3，4，5
			// 第一次，2，1，3，4，5
			// 第二次，3，2，1，4，5
			temp = first.next;// 1、temp保存当前第一个的下一个节点,temp=2，删除2 之前先保存2，避免断链
			first.next = temp.next;// 2、将当前节点指向当前节点的下一个节点，类似删除2，first.next=3 ,新链表：[-1,1,3,4,5]
			temp.next = res.next;// 将temp变为第一个节点，并将头节点指向temp,类似头插法，temp.next=1，temp：[1,3,4,5]
			res.next = temp;// res->temp，temp:[2],2变为除(-1)外的头结点
		}

		return res.next;
	}
}