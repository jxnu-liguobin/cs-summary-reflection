use std::borrow::{Borrow, BorrowMut};
use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///合并二叉树
impl Solution {
    ///author 李广胜
    pub fn merge_trees(
        t1: Option<Rc<RefCell<TreeNode>>>,
        t2: Option<Rc<RefCell<TreeNode>>>,
    ) -> Option<Rc<RefCell<TreeNode>>> {
        if t1.is_none() {
            return t2;
        }
        if t2.is_none() {
            return t1;
        }
        let b1: Rc<RefCell<TreeNode>> = t1.unwrap();
        let b1: &RefCell<TreeNode> = b1.borrow();
        let b2: Rc<RefCell<TreeNode>> = t2.unwrap();
        let b2: &RefCell<TreeNode> = b2.borrow();
        unsafe {
            //直接b2.val编译错误
            Some(Rc::new(RefCell::new(TreeNode {
                val: (*b1.as_ptr()).val + (*b2.as_ptr()).val,
                left: Solution::merge_trees(
                    (*b1.as_ptr()).left.clone(),
                    (*b2.as_ptr()).left.clone(),
                ),
                right: Solution::merge_trees(
                    (*b1.as_ptr()).right.clone(),
                    (*b2.as_ptr()).right.clone(),
                ),
            })))
        }
    }

    ///author 长条人
    pub fn merge_trees2(
        t1: Option<Rc<RefCell<TreeNode>>>,
        t2: Option<Rc<RefCell<TreeNode>>>,
    ) -> Option<Rc<RefCell<TreeNode>>> {
        fn merge(t1: &mut Option<Rc<RefCell<TreeNode>>>, t2: &Option<Rc<RefCell<TreeNode>>>) {
            if let Some(mut n1) = t1.as_ref() {
                if let Some(n2) = t2 {
                    let n1 = n1.borrow_mut();
                    let n2: &RefCell<TreeNode> = n2.borrow();
                    unsafe {
                        (*n1.as_ptr()).val += (*n2.as_ptr()).val;
                        merge(&mut (*n1.as_ptr()).left, &(*n2.as_ptr()).left);
                        merge(&mut (*n1.as_ptr()).right, &(*n2.as_ptr()).right);
                    }
                } else {
                }
            } else {
                *t1 = t2.clone();
            }
        }
        let mut t1 = t1;
        merge(&mut t1, &t2);
        t1
    }
}
