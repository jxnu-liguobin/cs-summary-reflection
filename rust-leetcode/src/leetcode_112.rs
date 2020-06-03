use std::borrow::Borrow;
use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

/// 112. 路径总和
/// 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
impl Solution {
    //0 ms,100.00%
    //2.6 MB,100.00%
    pub fn has_path_sum(root: Option<Rc<RefCell<TreeNode>>>, sum: i32) -> bool {
        fn helper(node: &Option<Rc<RefCell<TreeNode>>>, origin_sum: i32) -> bool {
            match node {
                Some(n) => {
                    match (
                        n.as_ref().borrow().left.is_none(),
                        n.as_ref().borrow().right.is_none(),
                    ) {
                        (true, true) => origin_sum - n.as_ref().borrow().val == 0,
                        (_, _) => {
                            helper(
                                &n.as_ref().borrow().left,
                                origin_sum - n.as_ref().borrow().val,
                            ) || helper(
                                &n.as_ref().borrow().right,
                                origin_sum - n.as_ref().borrow().val,
                            )
                        }
                    }
                }
                None => false,
            }
        }
        helper(&root, sum)
    }
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{
        get_test_tree_2, get_test_tree_3, get_test_tree_5, Solution, TreeNode,
    };

    #[test]
    fn has_path_sum() {
        let ret = Solution::has_path_sum(get_test_tree_2(), 3);
        let ret2 = Solution::has_path_sum(get_test_tree_3(), 3);
        let ret3 = Solution::has_path_sum(get_test_tree_5(), 30);
        assert!(ret);
        assert!(ret2);
        assert!(ret3);
    }
}
