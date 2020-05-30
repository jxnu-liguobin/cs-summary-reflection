use std::borrow::Borrow;
use std::cell::RefCell;
use std::collections::VecDeque;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

/// 107. 二叉树的层次遍历 II
/// 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
/// 例如：
/// 给定二叉树 [3,9,20,null,null,15,7],
impl Solution {
    pub fn level_order_bottom(root: Option<Rc<RefCell<TreeNode>>>) -> Vec<Vec<i32>> {
        let mut ret = Vec::new();
        let mut node_level = VecDeque::new();
        let mut level = Vec::new();
        let mut flag = root.clone();
        node_level.push_back(root.clone());
        while !node_level.is_empty() {
            let curr_node = node_level.pop_front();
            if let Some(node) = curr_node {
                if let Some(n) = node {
                    level.push(n.try_borrow().unwrap().val);
                    if n.try_borrow().unwrap().left.is_some() {
                        node_level.push_back(n.try_borrow().unwrap().left.clone());
                    }
                    if n.try_borrow().unwrap().right.is_some() {
                        node_level.push_back(n.try_borrow().unwrap().right.clone());
                    }
                    if let Some(f) = flag.borrow() {
                        if f.as_ptr() == n.as_ptr() {
                            //直接back导致as_ptr不等
                            let tail = node_level.pop_back();
                            if tail.is_some() {
                                flag = tail.clone().unwrap();
                                node_level.push_back(tail.unwrap());
                            }
                            ret.insert(0, level);
                            level = Vec::new();
                        }
                    }
                }
            }
        }
        ret
    }
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{ListNode, Solution, TreeNode};

    #[test]
    fn level_order_bottom() {
        let e7 = Some(Rc::new(RefCell::new(TreeNode {
            val: 7,
            left: None,
            right: None,
        })));
        let e15 = Some(Rc::new(RefCell::new(TreeNode {
            val: 15,
            left: None,
            right: None,
        })));
        let e20 = Some(Rc::new(RefCell::new(TreeNode {
            val: 20,
            left: e15,
            right: e7,
        })));
        let e9 = Some(Rc::new(RefCell::new(TreeNode {
            val: 9,
            left: None,
            right: None,
        })));
        let e3 = Some(Rc::new(RefCell::new(TreeNode {
            val: 3,
            left: e9,
            right: e20,
        })));
        let ret = Solution::level_order_bottom(e3);
        assert!(ret.len() == 3 && ret[0].len() == 2);
    }
}
