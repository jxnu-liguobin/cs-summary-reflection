use std::cell::RefCell;
use std::cmp::max;
use std::collections::VecDeque;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///515. 在每个树行中找最大值
///您需要在二叉树的每一行中找到最大的值。
impl Solution {
    //0 ms,100.00%
    //3 MB,100.00%
    pub fn largest_values(root: Option<Rc<RefCell<TreeNode>>>) -> Vec<i32> {
        if let None = root {
            return vec![].to_vec();
        }
        let mut node_level: VecDeque<Option<Rc<RefCell<TreeNode>>>> = VecDeque::new();
        node_level.push_back(root);
        let mut ret = Vec::<i32>::new();
        let mut max_val = i32::min_value();
        while !node_level.is_empty() {
            let size = node_level.len();
            for i in 0..size {
                let curr_node = node_level.pop_front();
                if let Some(node) = curr_node {
                    //应该尽量避免使用unwrap
                    if let Some(n) = node {
                        max_val = max(max_val, n.borrow().val);
                        if n.borrow().left.is_some() {
                            node_level.push_back(n.borrow().left.clone());
                        }
                        if n.borrow().right.is_some() {
                            node_level.push_back(n.borrow().right.clone());
                        }
                    }
                }
            }
            ret.push(max_val);
            max_val = i32::min_value();
        }
        ret
    }
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{print_vec, Solution, TreeNode};

    #[test]
    fn largest_values() {
        let e1 = Some(Rc::new(RefCell::new(TreeNode {
            val: 1,
            left: Some(Rc::new(RefCell::new(TreeNode {
                val: 3,
                left: Some(Rc::new(RefCell::new(TreeNode {
                    val: 5,
                    left: None,
                    right: None,
                }))),
                right: Some(Rc::new(RefCell::new(TreeNode {
                    val: 3,
                    left: None,
                    right: None,
                }))),
            }))),
            right: Some(Rc::new(RefCell::new(TreeNode {
                val: 2,
                left: None,
                right: Some(Rc::new(RefCell::new(TreeNode {
                    val: 9,
                    left: None,
                    right: None,
                }))),
            }))),
        })));
        let ret = Solution::largest_values(e1.clone());
        print_vec(ret);
    }
}
