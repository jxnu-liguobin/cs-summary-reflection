use crate::pre_structs::{ListNode, Solution};

///合并两个排序的链表
impl Solution {
    pub fn merge_two_lists(
        l1: Option<Box<ListNode>>,
        l2: Option<Box<ListNode>>,
    ) -> Option<Box<ListNode>> {
        let mut result_head: Option<Box<ListNode>> = Some(Box::new(ListNode {
            val: -1,
            next: None,
        }));
        let mut cur = &mut result_head;
        let mut l1 = l1;
        let mut l2 = l2;
        let _next = true;
        while l1.is_some() || l2.is_some() {
            //take去除值，并保留为None
            match (l1.take(), l2.take()) {
                (Some(_l1), None) => {
                    //可变引用
                    if let Some(ref mut _cur) = cur {
                        _cur.next = Some(_l1);
                    }
                }
                (None, Some(_l2)) => {
                    if let Some(ref mut _cur) = cur {
                        _cur.next = Some(_l2);
                    }
                }
                (Some(mut _l1), Some(mut _l2)) => {
                    if &_l1.val < &_l2.val {
                        let _next = _l1.next.take();
                        if let Some(ref mut _cur) = cur {
                            //将l1拼接到cur后面
                            _cur.next = Some(_l1);
                            //移动cur本身
                            cur = &mut _cur.next;
                        }
                        //移动链表l1，并将l2恢复
                        l1 = _next;
                        l2 = Some(_l2);
                    } else {
                        let _next = _l2.next.take();
                        if let Some(ref mut _cur) = cur {
                            _cur.next = Some(_l2);
                            cur = &mut _cur.next;
                        }
                        l2 = _next;
                        l1 = Some(_l1);
                    }
                }
                (None, None) => {}
            }
        }
        return result_head.unwrap().next;
    }
}
