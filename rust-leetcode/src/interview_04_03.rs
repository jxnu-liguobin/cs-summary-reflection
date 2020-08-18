use std::cell::RefCell;
use std::collections::VecDeque;
use std::rc::Rc;

use crate::pre_structs::{ListNode, Solution, TreeNode};

///面试题 04.03. 特定深度节点链表
//给定一棵二叉树，设计一个算法，创建含有某一深度上所有节点的链表（比如，若一棵树的深度为 D，则会创建出 D 个链表）。返回一个包含所有深度的链表的数组。
impl Solution {
    //0 ms,100.00%
    //2.1 MB,100.00%
    pub fn list_of_depth(tree: Option<Rc<RefCell<TreeNode>>>) -> Vec<Option<Box<ListNode>>> {
        if let None = tree {
            return vec![].to_vec();
        }
        let mut node_level: VecDeque<Option<Rc<RefCell<TreeNode>>>> = VecDeque::new();
        node_level.push_back(tree);
        let mut ret = Vec::<Option<Box<ListNode>>>::new();
        while !node_level.is_empty() {
            let size = node_level.len();
            let mut p_head: Option<Box<ListNode>> = Some(Box::new(ListNode {
                val: i32::min_value(),
                next: None,
            }));
            let mut head = &mut p_head;
            for _i in 0..size {
                let curr_node = node_level.pop_front();
                if let Some(node) = curr_node {
                    if let Some(n) = node {
                        if let Some(ref mut _head) = head {
                            _head.next = Some(Box::new(ListNode {
                                val: n.borrow().val,
                                next: None,
                            }));
                            head = &mut _head.next;
                        }
                        if n.borrow().left.is_some() {
                            node_level.push_back(n.borrow().left.clone());
                        }
                        if n.borrow().right.is_some() {
                            node_level.push_back(n.borrow().right.clone());
                        }
                    }
                }
            }
            ret.push(p_head.as_ref().unwrap().next.clone());
        }
        ret
    }
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{Solution, TreeNode};

    #[test]
    fn list_of_depth() {
        let e1 = Some(Rc::new(RefCell::new(TreeNode {
            val: 1,
            right: Some(Rc::new(RefCell::new(TreeNode {
                val: 3,
                left: None,
                right: Some(Rc::new(RefCell::new(TreeNode {
                    val: 7,
                    left: None,
                    right: None,
                }))),
            }))),
            left: Some(Rc::new(RefCell::new(TreeNode {
                val: 2,
                left: Some(Rc::new(RefCell::new(TreeNode {
                    val: 4,
                    left: Some(Rc::new(RefCell::new(TreeNode {
                        val: 8,
                        left: None,
                        right: None,
                    }))),
                    right: None,
                }))),
                right: Some(Rc::new(RefCell::new(TreeNode {
                    val: 5,
                    left: None,
                    right: None,
                }))),
            }))),
        })));
        let ret = Solution::list_of_depth(e1);
        for line in ret {
            println!("{:?}", line)
        }
    }
}
