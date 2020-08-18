use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

/// 257. 二叉树的所有路径
/// 给定一个二叉树，返回所有从根节点到叶子节点的路径。
impl Solution {
    pub fn binary_tree_paths(root: Option<Rc<RefCell<TreeNode>>>) -> Vec<String> {
        //这里不能使用&mut String，会导致路径被传递
        //0 ms,100.00%
        //2 MB,100.00%
        fn dfs(root: &Option<Rc<RefCell<TreeNode>>>, mut path: String, ret: &mut Vec<String>) {
            if let Some(root) = root {
                //到达叶节点了
                if root.as_ref().borrow().left.is_none() && root.as_ref().borrow().right.is_none() {
                    path += &root.as_ref().borrow().val.to_string();
                    ret.push(path.to_string());
                    return;
                }
                path += &root.as_ref().borrow().val.to_string();
                path += &"->".to_string();
                //必须使用move而不是borrow
                dfs(&root.as_ref().borrow().left, path.clone(), ret);
                dfs(&root.as_ref().borrow().right, path, ret);
            } else {
                return;
            }
        }
        let p = "".to_owned();
        let mut ps = Vec::new();
        let ps = &mut ps;
        dfs(&root, p, ps);
        ps.to_vec()
    }
}

#[cfg(test)]
mod test {

    use crate::pre_structs::{get_test_tree_5, print_vec, Solution};

    #[test]
    fn binary_tree_paths() {
        let ret = Solution::binary_tree_paths(get_test_tree_5());
        print_vec(ret);
    }
}
