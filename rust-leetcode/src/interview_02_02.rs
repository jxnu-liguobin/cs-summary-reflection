use std::borrow::Borrow;
use std::collections::VecDeque;

use crate::pre_structs::{ListNode, Solution};

///返回倒数第 k 个节点
impl Solution {
    pub fn kth_to_last(head: Option<Box<ListNode>>, k: i32) -> i32 {
        let mut nodes = head;
        let mut qs = VecDeque::new();
        loop {
            if let Some(node) = nodes.borrow() {
                qs.push_back(node.val);
                nodes = node.next.clone();
            } else {
                break;
            }
        }

        let i = qs.len() - k as usize;
        let ret = qs.get(i);
        *ret.unwrap()
    }

    //倒数第k个，位置就是len-k。即快指针先走k步，然后2个指针同时走，快指针到达尾时，慢指针的位置就是第len-k个元素。此时快指针刚好走完一圈
    pub fn kth_to_last2(head: Option<Box<ListNode>>, k: i32) -> i32 {
        let mut i = k;
        let mut fast = head.as_ref(); //clone也可以，但没有必要，不能copy，没有实现Copy
        let mut slow = head.as_ref();
        while i > 0 {
            if let Some(node) = fast.borrow() {
                fast = node.next.as_ref();
                i -= 1;
            } else {
                break;
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
        slow.unwrap().val
    }
}
