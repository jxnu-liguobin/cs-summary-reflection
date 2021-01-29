package io.github.poorguy.explore.learn.linkedlist;

import java.util.HashSet;

public class LinkedListCycleII {
    public ListNode detectCycle(ListNode head) {
        HashSet set=new HashSet();
        while(head!=null){
            if(set.contains(head)){
                return head;
            }else{
                set.add(head);
                head=head.next;
            }
        }
        return null;
    }
}