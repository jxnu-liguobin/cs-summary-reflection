use std::borrow::{Borrow, BorrowMut};
use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///100. 相同的树
impl Solution {
    //unsafe 一把梭
    pub fn is_same_tree(
        p: Option<Rc<RefCell<TreeNode>>>,
        q: Option<Rc<RefCell<TreeNode>>>,
    ) -> bool {
        if p.is_none() && q.is_some() {
            return false;
        }
        if p.is_some() && q.is_none() {
            return false;
        }
        if p.is_none() && q.is_none() {
            return true;
        }
        let b1: Rc<RefCell<TreeNode>> = p.unwrap();
        let b1: &RefCell<TreeNode> = b1.borrow();
        let b2: Rc<RefCell<TreeNode>> = q.unwrap();
        let b2: &RefCell<TreeNode> = b2.borrow();
        unsafe {
            if (*b1.as_ptr()).val == (*b2.as_ptr()).val {
                Solution::is_same_tree((*b1.as_ptr()).left.clone(), (*b2.as_ptr()).left.clone())
                    && Solution::is_same_tree(
                        (*b1.as_ptr()).right.clone(),
                        (*b2.as_ptr()).right.clone(),
                    )
            } else {
                return false;
            }
        }
    }
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{Solution, TreeNode};

    #[test]
    fn is_same_tree() {
        let e2 = Some(Rc::new(RefCell::new(TreeNode {
            val: 3,
            left: None,
            right: None,
        })));
        let e1 = Some(Rc::new(RefCell::new(TreeNode {
            val: 2,
            left: None,
            right: None,
        })));
        let root1 = Some(Rc::new(RefCell::new(TreeNode {
            val: 1,
            left: e1,
            right: e2,
        })));

        let mut root1 = root1.clone();
        let mut root2 = root1.clone();
        let ret = Solution::is_same_tree(root1, root2);
        assert!(ret)
    }
}
