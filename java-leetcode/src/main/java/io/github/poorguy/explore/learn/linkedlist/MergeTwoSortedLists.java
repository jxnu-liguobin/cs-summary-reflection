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
class MergeTwoSortedLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null){
            return l2;
        }
        if(l2==null){
            return l1;
        }
        ListNode p1=l1;
        ListNode p2=l2;
        ListNode newHead=null;
        if(l1.val<l2.val){
            newHead=l1;
            p1=p1.next;
        }else{
            newHead=l2;
            p2=p2.next;
        }
        ListNode p=newHead;
        while(p1!=null&&p2!=null){
            if(p1.val<p2.val){
                p.next=p1;
                p1=p1.next;
            }else{
                p.next=p2;
                p2=p2.next;
            }
            p=p.next;
        }
        
        if(p1==null){
            p.next=p2;
        }else{
            p.next=p1;
        }
        return newHead;
    }
}