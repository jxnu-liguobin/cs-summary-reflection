use crate::pre_structs::{ListNode, Solution};

///反转链表 leetcode 206
impl Solution {
    pub fn reverse_list(head: Option<Box<ListNode>>) -> Option<Box<ListNode>> {
        let mut pre = None;
        let mut tmp = head;
        loop {
            //每次让head指向的节点指向pre指向的节点
            if let Some(mut head) = tmp {
                tmp = head.next;
                head.next = pre;
                pre = Some(head);
            } else {
                break;
            }
        }
        pre
    }
}
