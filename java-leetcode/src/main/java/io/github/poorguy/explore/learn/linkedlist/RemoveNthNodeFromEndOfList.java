package io.github.poorguy.explore.learn.linkedlist;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class RemoveNthNodeFromEndOfList {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int size=0;
        ListNode countPointer=new ListNode(0,head);
        while(countPointer.next!=null){
            countPointer=countPointer.next;
            size++;
        }
        ListNode removePointer=new ListNode(0,head);
        int moveCount=size-n;
        if(moveCount==0){
            return head.next;
        }
        while(moveCount>0){
            removePointer=removePointer.next;
            moveCount--;
        }
        removePointer.next=removePointer.next.next;
        
        return head;
    }
}