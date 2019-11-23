package io.github.dreamylost.practice;


/**
 * @description 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），
 *              返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
 * @author Mr.Li
 * 
 */
public class RandomListDemo {
	/*
	 * public RandomListNode Clone(RandomListNode pHead) {
	 * 
	 * if (pHead == null) return null; RandomListNode pCloneHead = new
	 * RandomListNode(pHead.label); pCloneHead.random = pHead.random;
	 * pCloneHead.next = Clone(pHead.next); return pCloneHead;
	 * 
	 * }
	 */

	/**
	 * @description 1、复制每个节点，如：复制节点A得到A1，将A1插入节点A后面           2、遍历链表，A1->random
	 *              = A->random->next; 3、将链表拆分成原链表和复制后的链表     
	 */
	public RandomListNode Clone(RandomListNode pHead) {
		if (pHead == null)
			return null;
		RandomListNode pCur = pHead;
		// 复制next A-B-C ->A-A-B-B-C-C
		while (pCur != null) {
			RandomListNode node = new RandomListNode(pCur.label);
			node.next = pCur.next;
			pCur.next = node;
			pCur = node.next;
		}
		pCur = pHead;
		// 复制randomp pCur原来链表的节点，pCur.next是复制pCur的节点
		while (pCur != null) {
			if (pCur.random != null) {
				pCur.next.random = pCur.random.next;
			}
			pCur = pCur.next.next;
		}
		RandomListNode head = pHead.next;
		RandomListNode cur = head;
		pCur = pHead;
		// 拆分链表
		while (pCur != null) {
			pCur.next = pCur.next.next;
			if (cur.next != null) {
				cur.next = cur.next.next;
			}
			cur = cur.next;
			pCur = pCur.next;
		}
		return head;
	}

	public RandomListNode Clone1(RandomListNode pHead) {
		if (pHead == null) {
			return null;
		}
		// 使用弱引用
		java.lang.ref.WeakReference<RandomListNode> sr = new java.lang.ref.WeakReference<RandomListNode>(
				pHead);
		java.util.List<RandomListNode> list = new java.util.LinkedList<>();
		java.lang.ref.WeakReference<java.util.List<RandomListNode>> ss = new java.lang.ref.WeakReference<java.util.List<RandomListNode>>(
				list);
		RandomListNode p = sr.get();
		while (p != null) {
			RandomListNode node = new RandomListNode(p.label);
			node.next = null;
			node.random = p.random;
			ss.get().add(node);
			p = p.next;
		}
		// 弱引用无论内存是否足都将被清除
		sr = null;
		RandomListNode head;
		head = ss.get().get(0);
		for (int i = 1; i < ss.get().size(); i++) {
			head = addNode(head, ss.get().get(i));
		}
		ss = null;
		// System.gc();
		return head;

	}

	private RandomListNode addNode(RandomListNode head, RandomListNode node) {
		RandomListNode temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = node;
		return head;
	}

}