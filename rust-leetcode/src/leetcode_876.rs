use crate::pre_structs::{ListNode, Solution};

///链表的中间结点
impl Solution {
    pub fn middle_node(head: Option<Box<ListNode>>) -> Option<Box<ListNode>> {
        let mut q = &head;
        let mut s = &head;
        while q.is_some() && q.as_ref().unwrap().next.is_some() {
            q = &q.as_ref().unwrap().next.as_ref().unwrap().next;
            s = &s.as_ref().unwrap().next;
        }
        s.clone()
    }
}
