use std::borrow::Borrow;
use std::cell::RefCell;
use std::cmp::max;
use std::rc::Rc;

use crate::pre_structs::{Solution, TreeNode};

///1145. 二叉树着色游戏
//有两位极客玩家参与了一场「二叉树着色」的游戏。游戏中，给出二叉树的根节点 root，树上总共有 n 个节点，且 n 为奇数，其中每个节点上的值从 1 到 n 各不相同。
//游戏从「一号」玩家开始（「一号」玩家为红色，「二号」玩家为蓝色），最开始时，
//「一号」玩家从 [1, n] 中取一个值 x（1 <= x <= n）；
//「二号」玩家也从 [1, n] 中取一个值 y（1 <= y <= n）且 y != x。
//「一号」玩家给值为 x 的节点染上红色，而「二号」玩家给值为 y 的节点染上蓝色。
//之后两位玩家轮流进行操作，每一回合，玩家选择一个他之前涂好颜色的节点，将所选节点一个 未着色 的邻节点（即左右子节点、或父节点）进行染色。
//如果当前玩家无法找到这样的节点来染色时，他的回合就会被跳过。
//若两个玩家都没有可以染色的节点时，游戏结束。着色节点最多的那位玩家获得胜利 ✌️。
//现在，假设你是「二号」玩家，根据所给出的输入，假如存在一个 y 值可以确保你赢得这场游戏，则返回 true；若无法获胜，就请返回 false。
impl Solution {
    //0 ms,100.00%
    //2.1 MB,100.00%
    pub fn btree_game_winning_move(root: Option<Rc<RefCell<TreeNode>>>, n: i32, x: i32) -> bool {
        fn get_node_count(
            root: &Option<Rc<RefCell<TreeNode>>>,
            x: i32,
            left: &mut i32,
            right: &mut i32,
        ) -> i32 {
            return if let Some(root) = root.borrow() {
                let l = get_node_count(&root.as_ref().borrow().left, x, left, right);
                let r = get_node_count(&root.as_ref().borrow().right, x, left, right);
                if root.as_ref().borrow().val == x {
                    *left = l;
                    *right = r;
                };
                return l + r + 1;
            } else {
                0
            };
        }

        let mut left = 0;
        let mut right = 0;
        get_node_count(&root, x, &mut left, &mut right); //可以先search到x节点的位置再分别计算子节点数
                                                         //极客1选了第一个节点后，将树划分为了三个部分（可能为空）
                                                         //第一部分：left 第二部分：right 第三部分：n - (left + right) - 1
                                                         //三个部分中的最大值，极客2就可以获胜 > 只需要总结点的数的一半
        max(n - left - right - 1, max(left, right)) > n / 2
    }
}

#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::Rc;

    use crate::pre_structs::{Solution, TreeNode};

    #[test]
    fn btree_game_winning_move() {
        let e1 = Some(Rc::new(RefCell::new(TreeNode {
            val: 1,
            left: Some(Rc::new(RefCell::new(TreeNode {
                val: 2,
                left: Some(Rc::new(RefCell::new(TreeNode {
                    val: 4,
                    left: Some(Rc::new(RefCell::new(TreeNode {
                        val: 8,
                        left: None,
                        right: None,
                    }))),
                    right: Some(Rc::new(RefCell::new(TreeNode {
                        val: 9,
                        left: None,
                        right: None,
                    }))),
                }))),
                right: Some(Rc::new(RefCell::new(TreeNode {
                    val: 5,
                    left: Some(Rc::new(RefCell::new(TreeNode {
                        val: 10,
                        left: None,
                        right: None,
                    }))),
                    right: Some(Rc::new(RefCell::new(TreeNode {
                        val: 11,
                        left: None,
                        right: None,
                    }))),
                }))),
            }))),
            right: Some(Rc::new(RefCell::new(TreeNode {
                val: 3,
                left: Some(Rc::new(RefCell::new(TreeNode {
                    val: 6,
                    left: None,
                    right: None,
                }))),
                right: Some(Rc::new(RefCell::new(TreeNode {
                    val: 7,
                    left: None,
                    right: None,
                }))),
            }))),
        })));
        let ret = Solution::btree_game_winning_move(e1, 11, 3);
        println!("{}", ret);
    }
}
