package io.github.poorguy.explore.learn.linkedlist;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class IntersectionOfTwoLinkedLists {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA==null||headB==null){
            return null;
        }

        int aLen=0;
        int bLen=0;
        ListNode pointerA=new ListNode(0,headA);
        ListNode pointerB=new ListNode(0,headB);
        while(pointerA.next!=null){
            pointerA=pointerA.next;
            aLen++;
        }
        while(pointerB.next!=null){
            pointerB=pointerB.next;
            bLen++;
        }
        if(pointerA!=pointerB){
            return null;
        }
        int distance=Math.abs(aLen-bLen);
        
        pointerA=new ListNode(0,headA);
        pointerB=new ListNode(0,headB);
        if(aLen>bLen){
            for(int i=0;i<distance;i++){
                pointerA=pointerA.next;
            }    
        }else{
            for(int i=0;i<distance;i++){
                pointerB=pointerB.next;
            }
        }
        while(pointerA!=null){
            if(pointerA==pointerB){
                return pointerA;
            }else{
                pointerA=pointerA.next;
                pointerB=pointerB.next;
            }
        }
        return null;
    }
}