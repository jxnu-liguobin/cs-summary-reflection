use std::borrow::{Borrow, BorrowMut};
use std::collections::HashMap;

use crate::pre_structs::{ListNode, Solution};

///83.删除排序链表中的重复元素
impl Solution {
    //40ms
    pub fn delete_duplicates(head: Option<Box<ListNode>>) -> Option<Box<ListNode>> {
        let mut count_map = HashMap::<i32, i32>::new();
        let mut head = head;
        let mut result_head: Option<Box<ListNode>> = Some(Box::new(ListNode {
            val: -1,
            next: None,
        }));
        let mut cur = &mut result_head;
        while head.is_some() {
            match head.take() {
                Some(mut h) if !count_map.contains_key(&h.val) => {
                    count_map.insert(h.val, 1);
                    if let Some(ref mut _cur) = cur {
                        let mut new_node = h.clone();
                        let _next = h.next.take();
                        head = _next;
                        new_node.next = None;
                        _cur.next = Some(new_node);
                        cur = &mut _cur.next;
                    }
                }
                Some(mut h) => {
                    let _next = h.next.take();
                    if let Some(mut _h) = _next {
                        head = Some(_h);
                    }
                }
                None => {
                    if let Some(ref mut _cur) = cur {
                        _cur.next = None;
                    }
                }
            }
        }
        result_head.unwrap().next
    }

    //因为是有序，去掉hashmap  28ms
    pub fn delete_duplicates_(head: Option<Box<ListNode>>) -> Option<Box<ListNode>> {
        let mut head = head;
        let mut result_head: Option<Box<ListNode>> = Some(Box::new(ListNode {
            val: -1,
            next: None,
        }));
        let mut cur = &mut result_head;
        let mut pre_val = i32::min_value();
        while head.is_some() {
            match head.take() {
                Some(mut h) if pre_val != h.val => {
                    let _h = h.clone();
                    pre_val = _h.val;
                    if let Some(ref mut _cur) = cur {
                        let mut new_node = _h;
                        let _next = h.next.take();
                        head = _next;
                        new_node.next = None;
                        _cur.next = Some(new_node);
                        cur = &mut _cur.next;
                    }
                }
                Some(mut h) => {
                    let _next = h.next.take();
                    if let Some(mut _h) = _next {
                        head = Some(_h);
                    }
                }
                None => {
                    if let Some(ref mut _cur) = cur {
                        _cur.next = None;
                    }
                }
            }
        }
        result_head.unwrap().next
    }

    //author Jack_
    pub fn delete_duplicates2(head: Option<Box<ListNode>>) -> Option<Box<ListNode>> {
        if head.is_none() {
            return None;
        }
        let mut node = head.unwrap();
        let next_node = Self::delete_duplicates(node.next.clone());
        if next_node.is_some() {
            let next_node_box = next_node.unwrap();
            if node.val == next_node_box.val {
                node.next = next_node_box.next;
            } else {
                node.next = Some(next_node_box);
            }
        }
        Some(node)
    }
}


#[cfg(test)]
mod test {
    use crate::pre_structs::{ListNode, Solution};

    #[test]
    fn delete_duplicates() {
        let e1 = Some(Box::new(ListNode { val: 0, next: None }));
        let e2 = Some(Box::new(ListNode { val: 0, next: e1 }));
        let e3 = Some(Box::new(ListNode { val: 0, next: e2 }));
        let e4 = Some(Box::new(ListNode { val: 0, next: e3 }));
        let e5 = Some(Box::new(ListNode { val: 0, next: e4 }));
        let mut ret = Solution::delete_duplicates_(e5);
        let mut rs = Vec::new();
        while ret.is_some() {
            println!("{}", ret.clone().unwrap().val);
            rs.push(ret.clone().unwrap().val);
            ret = ret.unwrap().next
        }
        assert!(rs == vec![0]);
    }
}
