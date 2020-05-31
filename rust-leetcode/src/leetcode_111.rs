use std::borrow::{Borrow, BorrowMut};
use std::cell::RefCell;
use std::cmp::{max, min};
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

/// 111. 二叉树的最小深度
/// 给定一个二叉树，找出其最小深度。
/// 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
impl Solution {
    //0 ms,100.00%
    //2.8 MB,100.00%
    pub fn min_depth(root: Option<Rc<RefCell<TreeNode>>>) -> i32 {
        fn min_depth_(node: &Option<Rc<RefCell<TreeNode>>>) -> i32 {
            match node {
                Some(n) => {
                    return match (
                        n.as_ref().borrow().borrow().left.is_some(),
                        n.as_ref().borrow().borrow().right.is_some(),
                    ) {
                        (true, true) => {
                            min(
                                min_depth_(&n.as_ref().borrow().borrow().left),
                                min_depth_(&n.as_ref().borrow().borrow().right),
                            ) + 1
                        }
                        (false, true) => min_depth_(&n.as_ref().borrow().borrow().right) + 1,
                        (true, false) => min_depth_(&n.as_ref().borrow().borrow().left) + 1,
                        (false, false) => 1,
                        _ => 0,
                    };
                }
                None => 0,
            }
        }

        min_depth_(&root)
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
    fn min_depth() {
        let ret = Solution::min_depth(get_test_tree_2());
        let ret2 = Solution::min_depth(get_test_tree_3());
        let ret3 = Solution::min_depth(get_test_tree_5());
        println!("{}", ret3);
        assert!(ret == 2);
        assert!(ret2 == 2);
        assert!(ret3 == 2);
    }
}
