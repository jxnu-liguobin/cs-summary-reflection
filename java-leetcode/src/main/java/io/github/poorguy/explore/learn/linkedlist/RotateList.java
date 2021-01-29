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
class RotateList {
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null||head.next==null){
            return head;
        }
        int length=0;
        ListNode p=head;
        while(p!=null){
            length++;
            p=p.next;
        }
        k=k%length;
        int startIndex=(length-k)%length;
        if(startIndex==0){
            return head;
        }else{
            p=new ListNode(0,head);
            for(int i=0;i<startIndex;i++){
                p=p.next;
            }
            ListNode t=p.next;
            p.next=null;
            p=t;
            while(p.next!=null){
                p=p.next;
            }
            p.next=head;
            return t;
        }
    }
}