use std::borrow::Borrow;
use std::cell::RefCell;
use std::cmp::max;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

/// 687. 最长同值路径
/// 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
impl Solution {
    //24 ms,77.78%
    //3 MB,100.00%
    pub fn longest_univalue_path(root: Option<Rc<RefCell<TreeNode>>>) -> i32 {
        fn helper(root: &Option<Rc<RefCell<TreeNode>>>, res: &mut i32) -> i32 {
            if let Some(root) = root {
                let mut left = helper(&root.as_ref().borrow().left, res);
                let mut right = helper(&root.as_ref().borrow().right, res);
                if root.as_ref().borrow().left.as_ref().is_some()
                    && root
                        .as_ref()
                        .borrow()
                        .left
                        .as_ref()
                        .unwrap()
                        .as_ref()
                        .borrow()
                        .val
                        == root.as_ref().borrow().val
                {
                    left += 1
                } else {
                    left = 0;
                }
                if root.as_ref().borrow().right.as_ref().is_some()
                    && root
                        .as_ref()
                        .borrow()
                        .right
                        .as_ref()
                        .unwrap()
                        .as_ref()
                        .borrow()
                        .val
                        == root.as_ref().borrow().val
                {
                    right += 1
                } else {
                    right = 0;
                }

                *res = (*res).max(left + right);
                left.max(right)
            } else {
                0
            }
        }
        let mut res = 0i32;
        helper(&root, &mut res);
        res
    }
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{get_test_tree_4, Solution, TreeNode};

    #[test]
    fn longest_univalue_path() {
        let ret = Solution::longest_univalue_path(get_test_tree_4());
        assert!(ret == 2);
    }
}
