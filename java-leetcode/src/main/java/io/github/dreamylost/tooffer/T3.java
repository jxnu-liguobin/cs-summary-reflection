package io.github.dreamylost.tooffer;

import java.util.ArrayList;
import java.util.Stack;

/** 输入一个链表，从尾到头打印链表每个节点的值。 1 -> 2 -> 3 3 -> 2 -> 1 */
public class T3 {

    ArrayList<Integer> arrayList = new ArrayList<>();

    public static ArrayList<Integer> printListFromTailToHead1(ListNode listNode) {
        Stack<Integer> stack = new Stack<>();
        // 压栈
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        // 弹栈
        ArrayList<Integer> list = new ArrayList<>();
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        return list;
    }

    public ArrayList<Integer> printListFromTailToHead2(ListNode listNode) {
        if (listNode != null) {
            this.printListFromTailToHead2(listNode.next);
            arrayList.add(listNode.val);
        }
        return arrayList;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);

        listNode1.next = listNode2;
        listNode2.next = listNode3;

        ArrayList<Integer> arrayList = printListFromTailToHead1(listNode1);
        arrayList.forEach(System.out::println);
    }
}
