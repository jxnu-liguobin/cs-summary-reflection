use std::cell::RefCell;
use std::cmp::max;
use std::collections::VecDeque;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///513. 找树左下角的值
///给定一个二叉树，在树的最后一行找到最左边的值。
impl Solution {
    //暴力
    //0 ms,100.00%
    //3 MB,100.00%
    pub fn find_bottom_left_value(root: Option<Rc<RefCell<TreeNode>>>) -> i32 {
        fn max_depth(root: &Option<Rc<RefCell<TreeNode>>>) -> i32 {
            if let Some(root) = root {
                let node = root.borrow();
                return max(max_depth(&(node.left)), max_depth(&(node.right))) + 1;
            } else {
                return 0;
            }
        }
        let depth = max_depth(&root);
        let mut level = 1;
        let mut node_level = VecDeque::new();
        node_level.push_back(root.clone());
        while !node_level.is_empty() {
            let size = node_level.len();
            for i in 0..size {
                let curr_node = node_level.pop_front();
                if let Some(node) = curr_node {
                    if let Some(n) = node {
                        if i == 0 && level == depth {
                            return n.borrow().val;
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
            level += 1;
        }
        0
    }

    //从右开始的层序，最后出队列的元素就是最左的
    //0 ms,100.00%
    //2.9 MB,100.00%
    pub fn find_bottom_left_value2(root: Option<Rc<RefCell<TreeNode>>>) -> i32 {
        let mut node_level = VecDeque::new();
        node_level.push_back(root.clone());
        let mut ret: Option<Rc<RefCell<TreeNode>>> = None;
        while !node_level.is_empty() {
            //题目要求树不能为空，队列元素都是非空
            let mut node = node_level.pop_front().unwrap();
            ret = node.clone();
            if let Some(node) = node {
                if node.borrow().right.is_some() {
                    node_level.push_back(node.borrow().right.clone());
                }
                if node.borrow().left.is_some() {
                    node_level.push_back(node.borrow().left.clone());
                }
            }
        }
        ret.unwrap().as_ref().borrow().val
    }
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{Solution, TreeNode};

    #[test]
    fn find_bottom_left_value() {
        let e2 = Some(Rc::new(RefCell::new(TreeNode {
            val: 2,
            left: Some(Rc::new(RefCell::new(TreeNode {
                val: 1,
                left: None,
                right: None,
            }))),
            right: Some(Rc::new(RefCell::new(TreeNode {
                val: 3,
                left: None,
                right: None,
            }))),
        })));
        let ret = Solution::find_bottom_left_value(e2.clone());
        let ret2 = Solution::find_bottom_left_value(e2);
        assert!(ret == 1);
        assert!(ret2 == 1);
        println!("{}", ret);
        println!("{}", ret2);
    }
}
