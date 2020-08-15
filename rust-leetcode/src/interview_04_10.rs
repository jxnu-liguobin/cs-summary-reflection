use std::borrow::Borrow;
use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///面试题 04.10. 检查子树
//检查子树。你有两棵非常大的二叉树：T1，有几万个节点；T2，有几万个节点。设计一个算法，判断 T2 是否为 T1 的子树。
//
//如果 T1 有这么一个节点 n，其子树与 T2 一模一样，则 T2 为 T1 的子树，也就是说，从节点 n 处把树砍断，得到的树与 T2 完全相同。
impl Solution {
    //4 ms,100.00%
    //4.6 MB,100.00%
    pub fn check_sub_tree(
        t1: Option<Rc<RefCell<TreeNode>>>,
        t2: Option<Rc<RefCell<TreeNode>>>,
    ) -> bool {
        fn is_sub(t1: &Option<Rc<RefCell<TreeNode>>>, t2: &Option<Rc<RefCell<TreeNode>>>) -> bool {
            match (t1.is_none(), t2.is_none()) {
                (true, true) => true,
                (false, false)
                    if t1.as_ref().unwrap().as_ref().borrow().val
                        == t2.as_ref().unwrap().as_ref().borrow().val =>
                {
                    is_sub(
                        &t1.as_ref().unwrap().as_ref().borrow().left,
                        &t2.as_ref().unwrap().as_ref().borrow().left,
                    ) && is_sub(
                        &t1.as_ref().unwrap().as_ref().borrow().right,
                        &t2.as_ref().unwrap().as_ref().borrow().right,
                    )
                }
                (_, _) => false,
            }
        }
        if let Some(t1) = t1.borrow() {
            is_sub(&Some(t1).cloned(), &t2)
                || Solution::check_sub_tree(
                    t1.as_ref().borrow().left.as_ref().cloned(),
                    t2.as_ref().cloned(),
                )
                || Solution::check_sub_tree(
                    t1.as_ref().borrow().right.as_ref().cloned(),
                    t2.as_ref().cloned(),
                )
        } else {
            return t2.is_none();
        }
    }
}
