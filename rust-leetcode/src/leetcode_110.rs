use std::borrow::{Borrow, BorrowMut};
use std::cell::RefCell;
use std::cmp::max;
use std::ops::Deref;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

/// 110. 平衡二叉树
/// 一个二叉树每个节点的左右两个子树的高度差的绝对值不超过1。
impl Solution {
    //4 ms,17.65%
    //2.7 MB,100.00%
    pub fn is_balanced(root: Option<Rc<RefCell<TreeNode>>>) -> bool {
        let root = &root;
        fn max_depth(node: &Option<Rc<RefCell<TreeNode>>>, ret: &mut bool) -> i32 {
            if node.is_none() {
                return 0;
            }
            let l = max_depth(
                &node.as_ref().borrow().unwrap().as_ref().borrow().left,
                ret.borrow_mut(),
            );
            let r = max_depth(
                &node.as_ref().borrow().unwrap().as_ref().borrow().right,
                ret.borrow_mut(),
            );
            if (l - r).abs() > 1 {
                //本地函数不能捕获外部变量
                let mut t = ret.borrow_mut();
                *t = false;
            }
            return 1 + max(l, r);
        }
        let mut ret = true;
        let ret = &mut ret;
        max_depth(root, ret);
        ret.clone()
    }

    //xian-za-zhi-zao
    //4 ms,17.65%
    //2.7 MB,100.00%
    pub fn is_balanced2(root: Option<Rc<RefCell<TreeNode>>>) -> bool {
        pub fn dfs(root: &Option<Rc<RefCell<TreeNode>>>) -> (bool, i32) {
            match root {
                Some(node) => {
                    let (lok, lf) = dfs(&node.as_ref().borrow().right);
                    let (rok, rf) = dfs(&node.as_ref().borrow().left);
                    if lok && rok && (lf - rf).abs() <= 1 {
                        (true, 1 + lf.max(rf))
                    } else {
                        (false, 1 + lf.max(rf))
                    }
                }
                None => (true, 0),
            }
        }
        let (ans, h) = dfs(&root);
        ans
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

        let ret = Solution::is_balanced(root1.clone());
        let ret2 = Solution::is_balanced2(root1);
        assert!(ret);
        assert!(ret2);
    }
}
