use std::borrow::Borrow;
use std::cell::RefCell;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///面试题 04.12. 求和路径
//给定一棵二叉树，其中每个节点都含有一个整数数值(该值或正或负)。设计一个算法，打印节点数值总和等于某个给定值的所有路径的数量。
// 注意，路径不一定非得从二叉树的根节点或叶节点开始或结束，但是其方向必须向下(只能从父节点指向子节点方向)。
impl Solution {
    pub fn path_sum(root: Option<Rc<RefCell<TreeNode>>>, sum: i32) -> i32 {
        //dfs有点慢
        //12 ms,9.09%
        //2.8 MB,100.00%
        fn path_sum_start_start_parent(root: &Option<Rc<RefCell<TreeNode>>>, sum: i32) -> i32 {
            let mut ret = 0;
            return if let Some(root) = root.borrow() {
                if root.as_ref().borrow().val == sum {
                    ret += 1;
                }
                ret += path_sum_start_start_parent(
                    &root.as_ref().borrow().left,
                    sum - root.as_ref().borrow().val,
                ) + path_sum_start_start_parent(
                    &root.as_ref().borrow().right,
                    sum - root.as_ref().borrow().val,
                );
                ret
            } else {
                0
            };
        }
        return if let Some(root) = root.borrow() {
            path_sum_start_start_parent(&Some(root).cloned(), sum)
                + Solution::path_sum(root.as_ref().borrow().left.clone(), sum)
                + Solution::path_sum(root.as_ref().borrow().right.clone(), sum)
        } else {
            0
        };
    }
    //前缀和的递归回溯思路
    //从当前节点反推到根节点(反推比较好理解，正向其实也只有一条)，有且仅有一条路径，因为这是一棵树
    //如果此前有和为currSum-target,而当前的和又为currSum,两者的差就肯定为target了
    //所以前缀和对于当前路径来说是唯一的，当前记录的前缀和，在回溯结束，回到本层时去除，保证其不影响其他分支的结果
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{Solution, TreeNode};

    #[test]
    fn path_sum() {
        let root = Some(Rc::new(RefCell::new(TreeNode {
            val: 5,
            left: Some(Rc::new(RefCell::new(TreeNode {
                val: 4,
                left: Some(Rc::new(RefCell::new(TreeNode {
                    val: 11,
                    left: Some(Rc::new(RefCell::new(TreeNode {
                        val: 7,
                        left: None,
                        right: None,
                    }))),
                    right: Some(Rc::new(RefCell::new(TreeNode {
                        val: 2,
                        left: None,
                        right: None,
                    }))),
                }))),
                right: None,
            }))),
            right: Some(Rc::new(RefCell::new(TreeNode {
                val: 8,
                left: Some(Rc::new(RefCell::new(TreeNode {
                    val: 13,
                    left: None,
                    right: None,
                }))),
                right: Some(Rc::new(RefCell::new(TreeNode {
                    val: 4,
                    left: Some(Rc::new(RefCell::new(TreeNode {
                        val: 5,
                        left: None,
                        right: None,
                    }))),
                    right: Some(Rc::new(RefCell::new(TreeNode {
                        val: 1,
                        left: None,
                        right: None,
                    }))),
                }))),
            }))),
        })));
        let ret = Solution::path_sum(root, 22);
        print!("{}", ret)
    }
}
