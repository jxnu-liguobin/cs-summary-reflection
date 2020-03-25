use std::borrow::{Borrow, BorrowMut};
use std::cell::RefCell;
use std::cmp::max;
use std::collections::VecDeque;
use std::ops::{AddAssign, Deref, Index};
use std::rc::Rc;
use std::str::Chars;

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

///二叉树的镜像
fn interview_27() {
    use std::rc::Rc;
    use std::cell::RefCell;
    impl Solution {
        pub fn mirror_tree(root: Option<Rc<RefCell<TreeNode>>>) -> Option<Rc<RefCell<TreeNode>>> {
            fn mirror(root: &mut Option<Rc<RefCell<TreeNode>>>) {
                if let Some(node) = root {
                    let mut n = node.borrow_mut();
                    unsafe {
                        //FUCK YOU
                        let lt = std::mem::replace(&mut (*n.as_ptr()).left, None);
                        let rt = std::mem::replace(&mut (*n.as_ptr()).right, lt);
                        std::mem::replace(&mut (*n.as_ptr()).left, rt);
                        mirror(&mut (*n.as_ptr()).right);
                        mirror(&mut (*n.as_ptr()).left);
                    }
                }
            }
            let mut root = root;
            mirror(&mut root);
            root
        }
    }


    let e1 = Some(Rc::new(RefCell::new(TreeNode { val: 1, left: None, right: None })));
    let e2 = Some(Rc::new(RefCell::new(TreeNode { val: 3, left: None, right: None })));
    let e3 = Some(Rc::new(RefCell::new(TreeNode { val: 6, left: None, right: None })));
    let e4 = Some(Rc::new(RefCell::new(TreeNode { val: 9, left: None, right: None })));
    let e5 = Some(Rc::new(RefCell::new(TreeNode { val: 2, left: e1, right: e2 })));
    let e6 = Some(Rc::new(RefCell::new(TreeNode { val: 7, left: e3, right: e4 })));
    let e7 = Some(Rc::new(RefCell::new(TreeNode { val: 4, left: e5, right: e6 })));
//    println!("{:?}", Solution::mirror_tree(e7));
    println!("{:?}", Solution::mirror_tree(e7))
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
            let mut fast = head.as_ref();//clone也可以，但没有必要，不能copy，没有实现Copy
            let mut slow = head.as_ref();
            while i > 0 {
                if let Some(node) = fast.borrow() {
                    fast = node.next.as_ref();
                    i -= 1;
                } else {
                    break;
                }
            }

            while fast != None {
                if let Some(node) = fast.borrow() {
                    fast = node.next.as_ref();
                    if let Some(node) = slow.borrow() {
                        slow = node.next.as_ref();
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

///链表中倒数第k个节点
fn interview_22() {
    println!("interview_22");
    impl Solution {
        pub fn get_kth_from_end(head: Option<Box<ListNode>>, k: i32) -> Option<Box<ListNode>> {
            let mut i = k;
            let mut fast = head.as_ref();
            let mut slow = head.as_ref();
            while i > 0 {
                if let Some(node) = fast.borrow() {
                    fast = node.next.as_ref();
                    i -= 1;
                }
            }

            while fast != None {
                if let Some(node) = fast.borrow() {
                    fast = node.next.as_ref();
                    if let Some(node) = slow.borrow() {
                        slow = node.next.as_ref();
                    }
                }
            }
            Some(slow.unwrap().clone())
        }
    }

    let e1 = Some(Box::new(ListNode { val: 5, next: None }));
    let e2 = Some(Box::new(ListNode { val: 4, next: e1 }));
    let e3 = Some(Box::new(ListNode { val: 3, next: e2 }));
    let e4 = Some(Box::new(ListNode { val: 2, next: e3 }));
    let e5 = Some(Box::new(ListNode { val: 1, next: e4 }));
    let ret = Solution::get_kth_from_end(e5, 2);
    println!("{:?}", ret);
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

///打印从1到最大的n位数
fn interview_17() {
    println!("interview_17");
    let num = ['9'; 10];//10个字符串9，这里10只能是一个常量，无法直接用于本题
    let mut max_num = String::new();
    for i in num.iter() {
        max_num.push(*i)
    }
    println!("{:?}", max_num);

    impl Solution {
        //8ms
        pub fn print_numbers(n: i32) -> Vec<i32> {
            let mut max_num = String::new();
            for i in 1..=n {
                max_num.push('9')
            }
            let mut ret = Vec::new();
            for i in 1..=max_num.parse::<i32>().unwrap() {
                ret.push(i);
            }
            ret
        }

        //8ms
        pub fn print_numbers2(n: i32) -> Vec<i32> {
            let mut ret = Vec::new();
            let x: i32 = 10;
            for i in 1..x.pow(n as u32) {
                ret.push(i);
            }
            ret
        }

        //20ms
        pub fn print_numbers3(n: i32) -> Vec<i32> {
            //快速幂
            fn pow(mut base: i32, mut index: i32) -> i32 {
                let mut ret = 1;
                while index > 0 {
                    if index & 1 == 1 {
                        ret *= base;
                    }
                    index /= 2;
                    base *= base;
                }
                ret
            }

            let mut ret = Vec::new();
            for i in 1..pow(10, n) {
                ret.push(i);
            }
            ret
        }
    }

    let p = Solution::print_numbers(1);
    let p2 = Solution::print_numbers2(1);
    let p3 = Solution::print_numbers3(1);
    print_vec(p);
    print_vec(p2);
    print_vec(p3)
}

///替换空格
fn interview_05() {
    println!("interview_05");
    impl Solution {
        pub fn replace_space(s: String) -> String {
            let mut str = s;
            str.replace(" ", "%20")
        }
    }

    let ret = Solution::replace_space("We are happy.".to_string());
    println!("{}", ret)
}

///分割平衡字符串
fn leetcode_1221() {
    println!("leetcode_1221");
    impl Solution {
        pub fn balanced_string_split(s: String) -> i32 {
            let mut l = 0;
            let mut ret = 0;
            for c in s.chars() {
                if c == 'L' {
                    l += 1;
                }
                if c == 'R' {
                    l -= 1;
                }
                if l == 0 {
                    ret += 1;
                }
            }
            ret
        }
        //函数式
        pub fn balanced_string_split2(s: String) -> i32 {
            s.chars().scan(0, |acc, e| {
                *acc = if let 'R' = e {
                    (*acc + 1)
                } else {
                    (*acc - 1)
                };
                Some(*acc)
            }).filter(|&e| e == 0).count() as i32
        }
    }
    let ret = Solution::balanced_string_split("RLRRLLRLRL".to_string());
    let ret2 = Solution::balanced_string_split2("RLRRLLRLRL".to_string());
    println!("{}", ret);
    println!("{}", ret2)
}

///从尾到头打印链表
fn interview_06() {
    println!("interview_06");
    impl Solution {
        pub fn reverse_print(head: Option<Box<ListNode>>) -> Vec<i32> {
            let mut ret = Vec::<i32>::new();
            let mut node = head.as_ref();
            loop {
                if let Some(root) = node {
                    ret.push(root.val);
                    node = root.next.as_ref();
                } else { break; }
            }
            ret.reverse();
            ret
        }
    }
    let e3 = Some(Box::new(ListNode { val: 2, next: None }));
    let e4 = Some(Box::new(ListNode { val: 3, next: e3 }));
    let e5 = Some(Box::new(ListNode { val: 1, next: e4 }));
    let ret = Solution::reverse_print(e5);
    println!("{:?}", ret);
}

///二叉搜索树的范围和
fn leetcode_938() {
    println!("leetcode_938");
    use std::rc::Rc;
    use std::cell::RefCell;
    impl Solution {
        pub fn range_sum_bst(root: Option<Rc<RefCell<TreeNode>>>, l: i32, r: i32) -> i32 {
            let mut ret = 0;
            let mut nodes = VecDeque::new();
            nodes.push_back(root);
            while !nodes.is_empty() {
                let tmp = nodes.pop_back();
                if let Some(node) = tmp {
                    if let Some(n) = node {
                        if n.try_borrow().unwrap().val >= l && n.try_borrow().unwrap().val <= r {
                            ret += n.try_borrow().unwrap().val
                        }
                        //满足条件继续查找
                        if n.try_borrow().unwrap().val > l {
                            nodes.push_back(n.try_borrow().unwrap().left.clone());
                        }
                        if n.try_borrow().unwrap().val < r {
                            nodes.push_back(n.try_borrow().unwrap().right.clone());
                        }
                    }
                }
            }
            ret
        }

        pub fn range_sum_bst2(root: Option<Rc<RefCell<TreeNode>>>, l: i32, r: i32) -> i32 {
            let mut ret = 0;
            fn bst(root: Option<Rc<RefCell<TreeNode>>>, l: i32, r: i32, ret: &mut i32) {
                if let Some(node) = root {
                    if node.try_borrow().unwrap().val >= l && node.try_borrow().unwrap().val <= r {
                        *ret += node.try_borrow().unwrap().val
                    }
                    if node.try_borrow().unwrap().val > l {
                        bst(node.try_borrow().unwrap().left.clone(), l, r, ret)
                    }
                    if node.try_borrow().unwrap().val < r {
                        bst(node.try_borrow().unwrap().right.clone(), l, r, ret)
                    }
                }
            }
            //可变借用，修改外函数的变量ret
            bst(root, l, r, &mut ret);
            ret
        }
    }
}

///删除最外层的括号
fn lettcode_1021() {
    println!("lettcode_1021");
    impl Solution {
        pub fn remove_outer_parentheses(s: String) -> String {
            let mut ret_str = String::new();
            let mut le = 0;
            for c in s.chars() {
                if c == ')' {
                    le -= 1
                }
                if le >= 1 {
                    ret_str.push(c)
                }
                if c == '(' {
                    le += 1
                }
            }
            ret_str
        }

        pub fn remove_outer_parentheses2(s: String) -> String {
            let mut stack = VecDeque::new();
            let mut ret_str = String::new();
            for c in s.chars() {
                //括号匹配，忽略最左边和最右边的括号
                if c == '(' {
                    stack.push_back(c);
                    if stack.len() > 1 {
                        ret_str.push(c);
                    }
                } else {
                    stack.pop_back();
                    if stack.len() != 0 {
                        ret_str.push(c);
                    }
                }
            }
            ret_str
        }
    }



    let ret = Solution::remove_outer_parentheses("(()())(())".to_string());
    println!("{}", ret)
}

///反转链表 leetcode 206
fn interview_24() {
    println!("interview_24");
    impl Solution {
        pub fn reverse_list(head: Option<Box<ListNode>>) -> Option<Box<ListNode>> {
            let mut pre = None;
            let mut tmp = head;
            loop {
                //每次让head指向的节点指向pre指向的节点
                if let Some(mut head) = tmp {
                    tmp = head.next;
                    head.next = pre;
                    pre = Some(head);
                } else {
                    break;
                }
            }
            pre
        }
    }
    let e1 = Some(Box::new(ListNode { val: 5, next: None }));
    let e2 = Some(Box::new(ListNode { val: 4, next: e1 }));
    let e3 = Some(Box::new(ListNode { val: 3, next: e2 }));
    let e4 = Some(Box::new(ListNode { val: 2, next: e3 }));
    let e5 = Some(Box::new(ListNode { val: 1, next: e4 }));
    let ret = Solution::reverse_list(e5);
    println!("{:?}", ret.unwrap());// 5 4 3 2 1
}

///奇数值单元格的数目
fn leetcode_1252() {
    println!("leetcode_1252");
    impl Solution {
        pub fn odd_cells(n: i32, m: i32, indices: Vec<Vec<i32>>) -> i32 {
            let mut arr = vec![vec![0; m as usize]; n as usize];
            let mut res = 0;
            for row in indices {
                for i in 0..n {
                    arr[i as usize][row[1] as usize] += 1;
                }
                for j in 0..m {
                    arr[row[0] as usize][j as usize] += 1;
                }
            }
            for i in 0..n {
                for j in 0..m {
                    if arr[i as usize][j as usize] & 1 == 1 {
                        res += 1;
                    }
                }
            }
            res
        }
    }

    let ret = Solution::odd_cells(2, 3, vec![vec![0, 1], vec![1, 1]]);
    println!("{}", ret)
}

///6 和 9 组成的最大数字
fn leetcode_1323() {
    println!("leetcode_1323");
    impl Solution {
        pub fn maximum69_number(num: i32) -> i32 {
            num.to_string().replacen('6', "9", 1).parse().unwrap()
        }
    }

    let ret = Solution::maximum69_number(996);
    println!("{}", ret)
}

///合并二叉树
fn leetcode_617() {
    println!("leetcode_617");
    use std::rc::Rc;
    use std::cell::RefCell;
    impl Solution {
        ///author 李广胜
        pub fn merge_trees(t1: Option<Rc<RefCell<TreeNode>>>, t2: Option<Rc<RefCell<TreeNode>>>) -> Option<Rc<RefCell<TreeNode>>> {
            if t1.is_none() {
                return t2;
            }
            if t2.is_none() {
                return t1;
            }
            let b1: Rc<RefCell<TreeNode>> = t1.unwrap();
            let b1: &RefCell<TreeNode> = b1.borrow();
            let b2: Rc<RefCell<TreeNode>> = t2.unwrap();
            let b2: &RefCell<TreeNode> = b2.borrow();
            unsafe {
                //直接b2.val编译错误
                Some(Rc::new(RefCell::new(TreeNode {
                    val: (*b1.as_ptr()).val + (*b2.as_ptr()).val,
                    left: Solution::merge_trees((*b1.as_ptr()).left.clone(), (*b2.as_ptr()).left.clone()),
                    right: Solution::merge_trees((*b1.as_ptr()).right.clone(), (*b2.as_ptr()).right.clone()),
                })))
            }
        }

        ///author 长条人
        pub fn merge_trees2(t1: Option<Rc<RefCell<TreeNode>>>, t2: Option<Rc<RefCell<TreeNode>>>) -> Option<Rc<RefCell<TreeNode>>> {
            fn merge(t1: &mut Option<Rc<RefCell<TreeNode>>>, t2: &Option<Rc<RefCell<TreeNode>>>) {
                if let Some(mut n1) = t1.as_ref() {
                    if let Some(n2) = t2 {
                        let mut n1 = n1.borrow_mut();
                        let n2: &RefCell<TreeNode> = n2.borrow();
                        unsafe {
                            (*n1.as_ptr()).val += (*n2.as_ptr()).val;
                            merge(&mut (*n1.as_ptr()).left, &(*n2.as_ptr()).left);
                            merge(&mut (*n1.as_ptr()).right, &(*n2.as_ptr()).right);
                        }
                    } else {}
                } else {
                    *t1 = t2.clone();
                }
            }
            let mut t1 = t1;
            merge(&mut t1, &t2);
            t1
        }
    }
}

///汉明距离
fn leetcode_461() {
    println!("leetcode_461");
    impl Solution {
        pub fn hamming_distance(x: i32, y: i32) -> i32 {
            let mut nums = x ^ y;
            //二进制中1的个数
            let mut c = 0;
            while nums != 0 {
                if nums & 1 == 1 {
                    c += 1;
                }
                nums = nums >> 1;
            }
            c
        }
    }

    let ret = Solution::hamming_distance(1577962638, 1727613287);
    println!("{}", ret)
}

///转换成小写字母
fn leetcode_709() {
    println!("leetcode_709");
    impl Solution {
        pub fn to_lower_case(str: String) -> String {
            str.chars().map(|c| {
                //说明是大写，+32
                if c < 'a' && c >= 'A' {
                    (c as u8 + 32 as u8) as char
                } else { c }
            }).collect()
        }
    }
    let ret = Solution::to_lower_case("al&phaBET".to_string());
    println!("{}", ret)
}

///所有方法调用
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
    interview_22();
    interview_17();
    interview_27();
    interview_05();
    leetcode_1221();
    interview_06();
    leetcode_938();
    lettcode_1021();
    interview_24();
    leetcode_1252();
    leetcode_1323();
    leetcode_617();
    leetcode_461();
    leetcode_709();
}

fn print_vec(nums: Vec<i32>) {
    for e in nums.iter() {
        println!("{}", e);
    }
}