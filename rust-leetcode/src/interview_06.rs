use crate::pre_structs::{ListNode, Solution};

///从尾到头打印链表
impl Solution {
    pub fn reverse_print(head: Option<Box<ListNode>>) -> Vec<i32> {
        let mut ret = Vec::<i32>::new();
        let mut node = head.as_ref();
        loop {
            if let Some(root) = node {
                ret.push(root.val);
                node = root.next.as_ref();
            } else {
                break;
            }
        }
        ret.reverse();
        ret
    }
}
