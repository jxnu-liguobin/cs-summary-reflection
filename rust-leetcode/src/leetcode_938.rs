use std::cell::RefCell;
use std::collections::VecDeque;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///二叉搜索树的范围和
impl Solution {
    pub fn range_sum_bst(root: Option<Rc<RefCell<TreeNode>>>, l: i32, r: i32) -> i32 {
        let mut ret = 0;
        let mut nodes = VecDeque::new();
        nodes.push_back(root);
        while !nodes.is_empty() {
            let tmp = nodes.pop_back();
            if let Some(node) = tmp {
                if let Some(n) = node {
                    if n.try_borrow().unwrap().val >= l && n.try_borrow().unwrap().val <= r {
                        ret += n.try_borrow().unwrap().val
                    }
                    //满足条件继续查找
                    if n.try_borrow().unwrap().val > l {
                        nodes.push_back(n.try_borrow().unwrap().left.clone());
                    }
                    if n.try_borrow().unwrap().val < r {
                        nodes.push_back(n.try_borrow().unwrap().right.clone());
                    }
                }
            }
        }
        ret
    }

    pub fn range_sum_bst2(root: Option<Rc<RefCell<TreeNode>>>, l: i32, r: i32) -> i32 {
        let mut ret = 0;
        fn bst(root: Option<Rc<RefCell<TreeNode>>>, l: i32, r: i32, ret: &mut i32) {
            if let Some(node) = root {
                if node.try_borrow().unwrap().val >= l && node.try_borrow().unwrap().val <= r {
                    *ret += node.try_borrow().unwrap().val
                }
                if node.try_borrow().unwrap().val > l {
                    bst(node.try_borrow().unwrap().left.clone(), l, r, ret)
                }
                if node.try_borrow().unwrap().val < r {
                    bst(node.try_borrow().unwrap().right.clone(), l, r, ret)
                }
            }
        }
        //可变借用，修改外函数的变量ret
        bst(root, l, r, &mut ret);
        ret
    }
}
