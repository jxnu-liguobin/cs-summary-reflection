use std::borrow::Borrow;
use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{ListNode, Solution, TreeNode};

///1367. 二叉树中的列表
impl Solution {
    //暴力干
    //20 ms,50.00%
    //3.8 MB,100.00%
    pub fn is_sub_path(head: Option<Box<ListNode>>, root: Option<Rc<RefCell<TreeNode>>>) -> bool {
        fn dfs_get_paths(
            root: &Option<Rc<RefCell<TreeNode>>>,
            mut path: String,
            ret: &mut Vec<String>,
        ) {
            if let Some(root) = root {
                if root.as_ref().borrow().left.is_none() && root.as_ref().borrow().right.is_none() {
                    path += &root.as_ref().borrow().val.to_string();
                    ret.push(path.to_string());
                    return;
                }
                path += &root.as_ref().borrow().val.to_string();
                path += &"".to_string();
                dfs_get_paths(&root.as_ref().borrow().left, path.clone(), ret);
                dfs_get_paths(&root.as_ref().borrow().right, path, ret);
            } else {
                return;
            }
        }
        let p = "".to_owned();
        let mut ps = Vec::new();
        dfs_get_paths(&root, p, &mut ps);
        let mut target: String = String::new();
        let mut h = head.as_ref();
        while h.borrow().is_some() {
            target.push_str(&h.borrow().unwrap().val.to_string());
            h = h.borrow().unwrap().next.as_ref();
        }

        for list in ps.iter() {
            if list.contains(&target) {
                return true;
            }
        }

        false
    }

    //使用类似处理is_sub的方法
    //52 ms,50.00%
    //7.3 MB,100.00%
    pub fn is_sub_path2(head: Option<Box<ListNode>>, root: Option<Rc<RefCell<TreeNode>>>) -> bool {
        fn is_sub(head: &Option<Box<ListNode>>, root: &Option<Rc<RefCell<TreeNode>>>) -> bool {
            if head.is_none() {
                return true;
            }
            if root.is_none() {
                return false;
            }
            unsafe {
                if head.borrow().as_ref().unwrap().val
                    != (*root.borrow().as_ref().unwrap().as_ptr()).val
                {
                    return false;
                }
                return is_sub(
                    &head.borrow().as_ref().unwrap().next,
                    &(*root.borrow().as_ref().unwrap().as_ptr()).left,
                ) || is_sub(
                    &head.borrow().as_ref().unwrap().next,
                    &(*root.borrow().as_ref().unwrap().as_ptr()).right,
                );
            }
        }
        let root = &root;
        if head.is_none() {
            return true;
        }
        if root.is_none() {
            return false;
        }
        unsafe {
            is_sub(&head, root)
                || Solution::is_sub_path2(
                    head.clone(),
                    (*root.borrow().as_ref().unwrap().as_ptr()).right.clone(),
                )
                || Solution::is_sub_path2(
                    head,
                    (*root.borrow().as_ref().unwrap().as_ptr()).left.clone(),
                )
        }
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::{get_test_list_1, get_test_tree_5, Solution};

    #[test]
    fn is_sub_path() {
        let ret = Solution::is_sub_path(get_test_list_1(), get_test_tree_5());
        println!("{}", ret);
    }

    #[test]
    fn is_sub_path2() {
        let ret = Solution::is_sub_path2(get_test_list_1(), get_test_tree_5());
        println!("{}", ret);
    }
}
