use std::borrow::BorrowMut;
use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///二叉树的镜像
impl Solution {
    pub fn mirror_tree(root: Option<Rc<RefCell<TreeNode>>>) -> Option<Rc<RefCell<TreeNode>>> {
        fn mirror(root: &mut Option<Rc<RefCell<TreeNode>>>) {
            if let Some(node) = root {
                let n = node.borrow_mut();
                unsafe {
                    //FUCK YOU
                    let lt = std::mem::replace(&mut (*n.as_ptr()).left, None);
                    let rt = std::mem::replace(&mut (*n.as_ptr()).right, lt);
                    std::mem::replace(&mut (*n.as_ptr()).left, rt);
                    mirror(&mut (*n.as_ptr()).right);
                    mirror(&mut (*n.as_ptr()).left);
                }
            }
        }
        let mut root = root;
        mirror(&mut root);
        root
    }
}
