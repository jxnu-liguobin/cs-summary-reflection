use std::cell::RefCell;
use std::collections::VecDeque;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///二叉搜索树的第k大节点
impl Solution {
    pub fn kth_largest(root: Option<Rc<RefCell<TreeNode>>>, k: i32) -> i32 {
        let mut ret = Vec::new();
        let mut nodes = VecDeque::new();
        let mut cur = root.clone();
        while cur.is_some() || !nodes.is_empty() {
            while let Some(c) = cur {
                nodes.push_back(Some(c.clone()));
                cur = c.try_borrow().unwrap().left.clone();
            }
            if let Some(n) = nodes.pop_back().unwrap() {
                ret.push(n.try_borrow().unwrap().val);
                cur = n.try_borrow().unwrap().right.clone();
            }
        }
        ret[(ret.len() - k as usize)]
    }
}
