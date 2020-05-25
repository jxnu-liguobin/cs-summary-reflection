use std::borrow::Borrow;
use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///101. 对称二叉树
impl Solution {
    ///unsafe一把梭
    pub fn is_symmetric(root: Option<Rc<RefCell<TreeNode>>>) -> bool {
        fn is_mirror(
            left: &Option<Rc<RefCell<TreeNode>>>,
            right: &Option<Rc<RefCell<TreeNode>>>,
        ) -> bool {
            match (left.borrow(), right.borrow()) {
                (Some(l), Some(r)) => unsafe {
                    if (*l.as_ptr()).val == (*r.as_ptr()).val {
                        is_mirror(&(*l.as_ptr()).left, &(*r.as_ptr()).right)
                            && is_mirror(&(*l.as_ptr()).right, &(*r.as_ptr()).left)
                    } else {
                        false
                    }
                },
                (None, None) => true,
                (_, _) => false,
            }
        }
        let l = root.clone();
        let r = root.clone();
        is_mirror(&l, &r)
    }
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{Solution, TreeNode};

    #[test]
    fn is_symmetric() {
        let e2 = Some(Rc::new(RefCell::new(TreeNode {
            val: 2,
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

        let ret = Solution::is_symmetric(root1);
        assert!(ret)
    }
}
