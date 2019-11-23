package io.github.dreamylost.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @description 逆序遍历自定义节点
 * @author Mr.Li
 * @time 2018年1月22日19:22:50
 * 
 */
public class ReverseReadLinkedList {
	public static void main(String[] args) {
		/**
		 * 测试
		 */
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
		List<Integer> list = printListFromTailToHead(listNode1);
		for (Integer integer : list) {

			System.out.println(integer);
		}

	}

	/**
	 * 使用头插法
	 * 
	 * @param listNode
	 * @return
	 */
	public static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (listNode != null && listNode.next == null) {
			list.add(listNode.val);
			return list;
		} else if (listNode != null && listNode.next != null) {
			while (listNode.next != null) {
				list.add(0, listNode.val);
				listNode = listNode.next;
			}
			list.add(0, listNode.val);
		}
		return list;

	}

	/**
	 * 使用堆栈
	 * 
	 * @param listNode
	 * @return
	 */
	public static ArrayList<Integer> printListFromTailToHead2(ListNode2 listNode) {

		Stack<Integer> stack = new Stack<>();
		while (listNode != null) {
			stack.push(listNode.val);
			listNode = listNode.next;
		}

		ArrayList<Integer> list1 = new ArrayList<>();
		while (!stack.isEmpty()) {
			list1.add(stack.pop());
		}
		return list1;

	}

}
