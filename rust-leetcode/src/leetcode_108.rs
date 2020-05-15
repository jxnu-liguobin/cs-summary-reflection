use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///将有序数组转换为二叉搜索树
impl Solution {
    //与最小高度树 interview_04_02 一样
    pub fn sorted_array_to_bst2(nums: Vec<i32>) -> Option<Rc<RefCell<TreeNode>>> {
        fn buildTree(nums: &Vec<i32>, l: i32, r: i32) -> Option<Rc<RefCell<TreeNode>>> {
            if l > r {
                return None;
            }
            let mid = l + (r - l) / 2;
            let mut root = TreeNode::new(nums[mid as usize]);
            root.left = buildTree(nums, l, mid - 1);
            root.right = buildTree(nums, mid + 1, r);
            return Some(Rc::new(RefCell::new(root)));
        }
        buildTree(&nums, 0, nums.len() as i32)
    }
}
