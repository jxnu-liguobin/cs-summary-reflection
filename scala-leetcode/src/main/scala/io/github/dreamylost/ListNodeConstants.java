/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost;

public class ListNodeConstants {

    static ListNode nodep = new ListNode(1);
    static ListNode nodef = new ListNode(9);
    static ListNode nodeh = new ListNode(3);

    static ListNode node = new ListNode(7);
    static ListNode node1 = new ListNode(8);
    static ListNode node2 = new ListNode(90);
    static ListNode node3 = new ListNode(0);
    static ListNode node4 = new ListNode(4);
    static ListNode node5 = new ListNode(10);
    static ListNode node6 = new ListNode(2);
    static ListNode node7 = new ListNode(6);

    // 通用测试链表--短
    public static ListNode getListNodeOne() {

        node.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = null;
        return node;
    }

    // 通用测试使用的链表--长
    public static ListNode getListNodeTwo() {

        nodep.next = nodef;
        nodef.next = nodeh;
        nodeh.next = node;
        node.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = null;
        return node;
    }

    // 有序链表A
    public static ListNode getOrderListA() {
        node.next = node1;
        node1.next = nodef;
        nodef.next = node5;
        node5.next = null;
        return node;
    }

    // 有序链表B
    public static ListNode getOrderListB() {
        nodep.next = nodeh;
        nodeh.next = node4;
        node4.next = node7;
        node7.next = null;
        return nodep;
    }

    // 包含重复的链表
    public static ListNode getHasDuplicate() {
        ListNode node8 = new ListNode(6);
        ListNode node9 = new ListNode(6);

        nodep.next = nodeh;
        nodeh.next = node4;
        node4.next = node7;
        node7.next = node8;
        node8.next = node9;
        node9.next = null;

        return nodep;
    }
}
