use std::borrow::Borrow;
use std::cell::RefCell;
use std::collections::VecDeque;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///从上到下打印二叉树 II
impl Solution {
    //leetcode 102
    pub fn level_order(root: Option<Rc<RefCell<TreeNode>>>) -> Vec<Vec<i32>> {
        let mut ret = Vec::new();
        let mut nodes = VecDeque::new();
        let mut row = Vec::new();
        let mut flag = root.clone();
        nodes.push_back(root.clone());
        while !nodes.is_empty() {
            let tmp = nodes.pop_front();
            if let Some(node) = tmp {
                if let Some(n) = node {
                    row.push(n.try_borrow().unwrap().val);
                    if n.try_borrow().unwrap().left.is_some() {
                        nodes.push_back(n.try_borrow().unwrap().left.clone());
                    }
                    if n.try_borrow().unwrap().right.is_some() {
                        nodes.push_back(n.try_borrow().unwrap().right.clone());
                    }
                    if let Some(f) = flag.borrow() {
                        if f.as_ptr() == n.as_ptr() {
                            //直接back导致as_ptr不等
                            let tail = nodes.pop_back();
                            if tail.is_some() {
                                flag = tail.clone().unwrap();
                                nodes.push_back(tail.unwrap());
                            }
                            ret.push(row);
                            row = Vec::new();
                        }
                    }
                }
            }
        }
        ret
    }
}
