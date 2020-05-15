use std::borrow::Borrow;

use crate::pre_structs::{ListNode, Solution};

///链表中倒数第k个节点
impl Solution {
    pub fn get_kth_from_end(head: Option<Box<ListNode>>, k: i32) -> Option<Box<ListNode>> {
        let mut i = k;
        let mut fast = head.as_ref();
        let mut slow = head.as_ref();
        while i > 0 {
            if let Some(node) = fast.borrow() {
                fast = node.next.as_ref();
                i -= 1;
            }
        }

        while fast != None {
            if let Some(node) = fast.borrow() {
                fast = node.next.as_ref();
                if let Some(node) = slow.borrow() {
                    slow = node.next.as_ref();
                }
            }
        }
        Some(slow.unwrap().clone())
    }
}
