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
class PalindromeLinkedList {
    public boolean isPalindrome(ListNode head) {
        //reverse the last half and compare
        if(head==null||head.next==null){
            return true;
        }
        int count=0;
        ListNode pointer=head;
        while(pointer!=null){
            count++;
            pointer=pointer.next;
        }
        
        ListNode reverseP=head;
        int startReverseCount=count/2;
        int reverseCount=0;
        while(reverseCount<startReverseCount){
            reverseP=reverseP.next;
            reverseCount++;
        }
        ListNode reverseEvenP=reverse(reverseP);
        while(head!=null){
            if(reverseEvenP==null){
                break;
            }
            if(head.val!=reverseEvenP.val){
                return false;
            }
            head=head.next;
            reverseEvenP=reverseEvenP.next;
        }
        return true;
    }
    
    public ListNode reverse(ListNode head){
        if(head==null||head.next==null){
            return head;
        }
        ListNode prev=null;
        ListNode cur=head;
        ListNode t=null;
        while(cur!=null){
            t=cur.next;
            cur.next=prev;
            prev=cur;
            cur=t;
        }
        return prev;
    }
}