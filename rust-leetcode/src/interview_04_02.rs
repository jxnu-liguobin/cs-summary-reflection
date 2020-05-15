use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///最小高度树
impl Solution {
    pub fn sorted_array_to_bst(nums: Vec<i32>) -> Option<Rc<RefCell<TreeNode>>> {
        fn buildTree(nums: &Vec<i32>, l: i32, r: i32) -> Option<Rc<RefCell<TreeNode>>> {
            if l > r {
                return None;
            }
            if l == r {
                return Some(Rc::new(RefCell::new(TreeNode::new(nums[l as usize]))));
            }
            let mid = l + (r - l) / 2;
            let mut root = TreeNode::new(nums[mid as usize]);
            root.left = buildTree(nums, l, mid - 1);
            root.right = buildTree(nums, mid + 1, r);
            return Some(Rc::new(RefCell::new(root)));
        }

        return buildTree(&nums, 0, (nums.len() - 1) as i32);
    }
}
