use std::borrow::Borrow;
use std::cell::RefCell;
use std::cmp::max;
use std::collections::VecDeque;
use std::ops::{AddAssign, Deref, Index};
use std::rc::Rc;

use pre_structs::*;

///Leetcode 超简单的算法题目，主要为了熟悉rust语法
///rust所必须的数据结构
mod pre_structs {
    use std::cell::RefCell;
    use std::rc::Rc;

    pub(crate) struct Solution;


    //二叉树
    #[derive(Debug, PartialEq, Eq)]
    pub struct TreeNode {
        pub val: i32,
        pub left: Option<Rc<RefCell<TreeNode>>>,
        pub right: Option<Rc<RefCell<TreeNode>>>,
    }

    impl TreeNode {
        #[inline]
        pub fn new(val: i32) -> Self {
            TreeNode {
                val,
                left: None,
                right: None,
            }
        }
    }

    //单链表
    #[derive(PartialEq, Eq, Clone, Debug)]
    pub struct ListNode {
        pub val: i32,
        pub next: Option<Box<ListNode>>,//堆上
    }

    impl ListNode {
        #[inline]
        fn new(val: i32) -> Self {
            ListNode {
                next: None,
                val,
            }
        }
    }
}

pub fn solutions() {
    interview_58_2();
    leetcode_1365();
    leetcode_1342();
    leetcode_1313();
    leetcode_1281();
    interview_04_02();
    interview_55_1();
    leetcode_1351();
    interview_02_02();
}

///返回倒数第 k 个节点
fn interview_02_02() {
    println!("interview_02_02");
    impl Solution {
        pub fn kth_to_last(head: Option<Box<ListNode>>, k: i32) -> i32 {
            let mut nodes = head;
            let mut qs = VecDeque::new();
            loop {
                if let Some(node) = nodes.borrow() {
                    qs.push_back(node.val);
                    nodes = node.next.clone();
                } else {
                    break;
                }
            }

            let i = qs.len() - k as usize;
            let ret = qs.get(i);
            *ret.unwrap()
        }

        //倒数第k个，位置就是len-k。即快指针先走k步，然后2个指针同时走，快指针到达尾时，慢指针的位置就是第len-k个元素。此时快指针刚好走完一圈
        pub fn kth_to_last2(head: Option<Box<ListNode>>, k: i32) -> i32 {
            let mut i = k;
            let mut fast = head.clone();
            let mut slow = head;
            while i > 0 {
                if let Some(node) = fast.borrow() {
                    fast = node.next.clone();
                    i -= 1;
                } else {
                    break;
                }
            }

            while fast != None {
                if let Some(node) = fast.borrow() {
                    fast = node.next.clone();
                    if let Some(node) = slow.borrow() {
                        slow = node.next.clone();
                    }
                }
            }
            slow.unwrap().val
        }
    }

    let e1 = Some(Box::new(ListNode { val: 5, next: None }));
    let e2 = Some(Box::new(ListNode { val: 4, next: e1 }));
    let e3 = Some(Box::new(ListNode { val: 3, next: e2 }));
    let e4 = Some(Box::new(ListNode { val: 2, next: e3 }));
    let e5 = Some(Box::new(ListNode { val: 1, next: e4 }));
    let ret = Solution::kth_to_last(e5.clone(), 2);
    let ret2 = Solution::kth_to_last2(e5, 2);
    println!("{}", ret);
    println!("{}", ret2);
}

///统计有序矩阵中的负数
fn leetcode_1351() {
    println!("leetcode_1351");
    impl Solution {
        //应该将矩阵是排序的考虑进去，从右下角或左下角使用标记
        pub fn count_negatives(grid: Vec<Vec<i32>>) -> i32 {
            let mut count: i32 = 0;
            for r in grid.iter() {
                count += r.iter().filter(|&&x| x < 0).count() as i32
            }
            return count;
        }
    }

    let nums = vec![vec![4, 3, 2, -1], vec![3, 2, 1, -1], vec![1, 1, -1, -2], vec![-1, -1, -2, -3]];
    println!("{}", Solution::count_negatives(nums));
}

///二叉树的深度 = leetcode 104
fn interview_55_1() {
    println!("interview_55_1");
    impl Solution {
        pub fn max_depth(root: Option<Rc<RefCell<TreeNode>>>) -> i32 {
            fn get_depth(root: &Option<Rc<RefCell<TreeNode>>>) -> i32 {
                if let Some(root) = root {
                    let node = root.try_borrow().unwrap();
                    return max(get_depth(&node.left), get_depth(&node.right)) + 1;
                } else {
                    return 0;
                }
            }
            get_depth(&root)
        }
    }

    let tree = Some(Rc::new(RefCell::new(TreeNode::new(1))));
    let ret = Solution::max_depth(tree);
    println!("{}", ret)
}

///最小高度树
fn interview_04_02() {
    println!("interview_04_02");
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
    let nums = vec![-10, -3, 0, 5, 9];
    println!("{:?}", Solution::sorted_array_to_bst(nums))
}

///整数的各位积和之差
fn leetcode_1281() {
    println!("leetcode_1281");
    impl Solution {
        pub fn subtract_product_and_sum(n: i32) -> i32 {
            let mut num = n;
            let mut muti = 1;
            let mut sum = 0;
            while num != 0 {
                let mut tmp = num % 10;
                muti *= tmp;
                sum += tmp;
                num /= 10;
            }
            muti - sum
        }
    }

    println!("{}", Solution::subtract_product_and_sum(234));
}

///左旋转字符串
fn interview_58_2() {
    println!("interview_58_2");
    impl Solution {
        pub fn reverse_left_words(s: String, n: i32) -> String {
            let mut s1 = String::from(&s[0..n as usize]);
            let s2 = &s[n as usize..s.len()];
            s1.insert_str(0, s2);
            s1.to_owned()
        }
    }

    let result = Solution::reverse_left_words("abcdefg".to_owned(), 2);
    println!("{}", result);
}

///有多少小于当前数字的数字
fn leetcode_1365() {
    println!("leetcode_1365");
    impl Solution {
        pub fn smaller_numbers_than_current(nums: Vec<i32>) -> Vec<i32> {
            let mut ret = Vec::with_capacity(nums.len());
            for i in 0..nums.len() {
                let mut count = 0;
                for j in 0..nums.len() {
                    if nums[i] > nums[j] {
                        count += 1;
                    }
                }
                ret.push(count);
            }
            ret
        }
    }

    let nums = vec![8, 1, 2, 2, 3];
    let result = Solution::smaller_numbers_than_current(nums);
    print_vec(result);
}

///将数字变成 0 的操作次数
fn leetcode_1342() {
    println!("leetcode_1342");
    impl Solution {
        pub fn number_of_steps(num: i32) -> i32 {
            let mut n = num;
            let mut i = 0;
            while n != 0 {
                if n & 1 == 0 {
                    n /= 2;
                } else {
                    n -= 1;
                }
                i += 1;
            };
            i
        }
    }
    let result = Solution::number_of_steps(8);
    println!("{}", result);
}

///解压缩编码列表
fn leetcode_1313() {
    println!("leetcode_1313");
    impl Solution {
        pub fn decompress_rl_elist(nums: Vec<i32>) -> Vec<i32> {
            let mut rets = Vec::new();
            for (index, e) in nums.iter().enumerate() {
                if index & 1 == 0 {
                    let mut freq = nums[index];
                    let value = nums[index + 1];
                    while freq != 0 {
                        rets.push(value);
                        freq -= 1;
                    }
                }
            }
            rets
        }
    }

    let nums = vec![1, 2, 3, 4];

    let result = Solution::decompress_rl_elist(nums);
    print_vec(result);
}

fn print_vec(nums: Vec<i32>) {
    for e in nums.iter() {
        println!("{}", e);
    }
}