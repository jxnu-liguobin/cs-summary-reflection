use std::borrow::{Borrow, BorrowMut};
use std::cell::RefCell;
use std::cmp::max;
use std::collections::{HashMap, HashSet, VecDeque};
use std::ops::Index;
use std::rc::Rc;

use pre_structs::*;

///Leetcode 超简单的算法题目，主要为了熟悉rust语法
///rust所必须的数据结构
///old_solutions 是第一版，基本都带有测试（后续每个题都使用独立文件，测试暂无）
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
        pub next: Option<Box<ListNode>>, //堆上
    }

    impl ListNode {
        #[inline]
        fn new(val: i32) -> Self {
            ListNode { next: None, val }
        }
    }
}

///二叉树的镜像
fn interview_27() {
    use std::cell::RefCell;
    use std::rc::Rc;
    impl Solution {
        pub fn mirror_tree(root: Option<Rc<RefCell<TreeNode>>>) -> Option<Rc<RefCell<TreeNode>>> {
            fn mirror(root: &mut Option<Rc<RefCell<TreeNode>>>) {
                if let Some(node) = root {
                    let n = node.borrow_mut();
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

    let e1 = Some(Rc::new(RefCell::new(TreeNode {
        val: 1,
        left: None,
        right: None,
    })));
    let e2 = Some(Rc::new(RefCell::new(TreeNode {
        val: 3,
        left: None,
        right: None,
    })));
    let e3 = Some(Rc::new(RefCell::new(TreeNode {
        val: 6,
        left: None,
        right: None,
    })));
    let e4 = Some(Rc::new(RefCell::new(TreeNode {
        val: 9,
        left: None,
        right: None,
    })));
    let e5 = Some(Rc::new(RefCell::new(TreeNode {
        val: 2,
        left: e1,
        right: e2,
    })));
    let e6 = Some(Rc::new(RefCell::new(TreeNode {
        val: 7,
        left: e3,
        right: e4,
    })));
    let e7 = Some(Rc::new(RefCell::new(TreeNode {
        val: 4,
        left: e5,
        right: e6,
    })));
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
            let mut fast = head.as_ref(); //clone也可以，但没有必要，不能copy，没有实现Copy
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

    let nums = vec![
        vec![4, 3, 2, -1],
        vec![3, 2, 1, -1],
        vec![1, 1, -1, -2],
        vec![-1, -1, -2, -3],
    ];
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
                let tmp = num % 10;
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
            }
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
            for (index, _e) in nums.iter().enumerate() {
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
    let num = ['9'; 10]; //10个字符串9，这里10只能是一个常量，无法直接用于本题
    let mut max_num = String::new();
    for i in num.iter() {
        max_num.push(*i)
    }
    println!("{:?}", max_num);

    impl Solution {
        //8ms
        pub fn print_numbers(n: i32) -> Vec<i32> {
            let mut max_num = String::new();
            for _i in 1..=n {
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
            let str = s;
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
            s.chars()
                .scan(0, |acc, e| {
                    *acc = if let 'R' = e { *acc + 1 } else { *acc - 1 };
                    Some(*acc)
                })
                .filter(|&e| e == 0)
                .count() as i32
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
                } else {
                    break;
                }
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
    use std::cell::RefCell;
    use std::rc::Rc;
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
fn leetcode_1021() {
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
    println!("{:?}", ret.unwrap()); // 5 4 3 2 1
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
    use std::cell::RefCell;
    use std::rc::Rc;
    impl Solution {
        ///author 李广胜
        pub fn merge_trees(
            t1: Option<Rc<RefCell<TreeNode>>>,
            t2: Option<Rc<RefCell<TreeNode>>>,
        ) -> Option<Rc<RefCell<TreeNode>>> {
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
                    left: Solution::merge_trees(
                        (*b1.as_ptr()).left.clone(),
                        (*b2.as_ptr()).left.clone(),
                    ),
                    right: Solution::merge_trees(
                        (*b1.as_ptr()).right.clone(),
                        (*b2.as_ptr()).right.clone(),
                    ),
                })))
            }
        }

        ///author 长条人
        pub fn merge_trees2(
            t1: Option<Rc<RefCell<TreeNode>>>,
            t2: Option<Rc<RefCell<TreeNode>>>,
        ) -> Option<Rc<RefCell<TreeNode>>> {
            fn merge(t1: &mut Option<Rc<RefCell<TreeNode>>>, t2: &Option<Rc<RefCell<TreeNode>>>) {
                if let Some(mut n1) = t1.as_ref() {
                    if let Some(n2) = t2 {
                        let n1 = n1.borrow_mut();
                        let n2: &RefCell<TreeNode> = n2.borrow();
                        unsafe {
                            (*n1.as_ptr()).val += (*n2.as_ptr()).val;
                            merge(&mut (*n1.as_ptr()).left, &(*n2.as_ptr()).left);
                            merge(&mut (*n1.as_ptr()).right, &(*n2.as_ptr()).right);
                        }
                    } else {
                    }
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
            str.chars()
                .map(|c| {
                    //说明是大写，+32
                    if c < 'a' && c >= 'A' {
                        (c as u8 + 32 as u8) as char
                    } else {
                        c
                    }
                })
                .collect()
        }
    }
    let ret = Solution::to_lower_case("al&phaBET".to_string());
    println!("{}", ret)
}

///和为零的N个唯一整数
fn leetcode_1304() {
    println!("leetcode_1304");
    impl Solution {
        //双指针
        pub fn sum_zero(n: i32) -> Vec<i32> {
            let mut ret = vec![0; n as usize];
            let mut i = 0usize;
            let mut j = (n - 1) as usize;
            let mut c = 1;
            loop {
                if i >= j {
                    break;
                }
                ret[i] = c;
                ret[j] = -c;
                i += 1;
                j -= 1;
                c += 1;
            }
            ret
        }

        pub fn sum_zero2(n: i32) -> Vec<i32> {
            let mut ret = vec![0; n as usize];
            let mut sum = 0;
            let mut j = 0;
            for i in 0..=n - 2 {
                ret[j] = i;
                j += 1;
                sum += i;
            }
            ret[(n - 1) as usize] = -sum;
            ret
        }
    }
    let ret = Solution::sum_zero(10);
    let ret2 = Solution::sum_zero2(5);
    print_vec(ret);
    print_vec(ret2);
}

///唯一摩尔斯密码词
fn lettcode_804() {
    println!("lettcode_804");
    impl Solution {
        pub fn unique_morse_representations(words: Vec<String>) -> i32 {
            let m = vec![
                ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-",
                ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--",
                "-..-", "-.--", "--..",
            ];
            let mut ret = HashSet::new();
            for w in words.iter() {
                let mut mw = String::new();
                for c in w.chars() {
                    //bad smell
                    let c = m[(c as u8 - 97) as usize];
                    mw.push_str(c);
                }
                ret.insert(mw);
            }
            ret.len() as i32
        }
    }

    let words = vec![
        "gin".to_owned(),
        "zen".to_owned(),
        "gig".to_owned(),
        "msg".to_owned(),
    ];
    let ret = Solution::unique_morse_representations(words);
    println!("{}", ret)
}

///翻转图像
fn leetcode_832() {
    println!("leetcode_832");
    impl Solution {
        pub fn flip_and_invert_image(a: Vec<Vec<i32>>) -> Vec<Vec<i32>> {
            let ret: Vec<Vec<i32>> = a
                .iter()
                .map(|row| -> Vec<i32> {
                    let mut new_row: Vec<i32> = row
                        .iter()
                        .map(|x| -> i32 {
                            let new_x = if let 0 = x { 1 } else { 0 };
                            new_x
                        })
                        .collect();
                    new_row.reverse();
                    new_row
                })
                .collect();
            ret
        }
    }

    let p = vec![vec![1, 1, 0], vec![1, 0, 1], vec![0, 0, 0]];
    let ret = Solution::flip_and_invert_image(p);
    for r in ret.iter() {
        for c in r.iter() {
            print!("{}", c)
        }
        println!("{}", "")
    }
}

///合并两个排序的链表
fn interview_25() {
    println!("interview_25");
    impl Solution {
        pub fn merge_two_lists(
            l1: Option<Box<ListNode>>,
            l2: Option<Box<ListNode>>,
        ) -> Option<Box<ListNode>> {
            let mut result_head: Option<Box<ListNode>> = Some(Box::new(ListNode {
                val: -1,
                next: None,
            }));
            let mut cur = &mut result_head;
            let mut l1 = l1;
            let mut l2 = l2;
            let _next = true;
            while l1.is_some() || l2.is_some() {
                //take去除值，并保留为None
                match (l1.take(), l2.take()) {
                    (Some(_l1), None) => {
                        //可变引用
                        if let Some(ref mut _cur) = cur {
                            _cur.next = Some(_l1);
                        }
                    }
                    (None, Some(_l2)) => {
                        if let Some(ref mut _cur) = cur {
                            _cur.next = Some(_l2);
                        }
                    }
                    (Some(mut _l1), Some(mut _l2)) => {
                        if &_l1.val < &_l2.val {
                            let _next = _l1.next.take();
                            if let Some(ref mut _cur) = cur {
                                //将l1拼接到cur后面
                                _cur.next = Some(_l1);
                                //移动cur本身
                                cur = &mut _cur.next;
                            }
                            //移动链表l1，并将l2恢复
                            l1 = _next;
                            l2 = Some(_l2);
                        } else {
                            let _next = _l2.next.take();
                            if let Some(ref mut _cur) = cur {
                                _cur.next = Some(_l2);
                                cur = &mut _cur.next;
                            }
                            l2 = _next;
                            l1 = Some(_l1);
                        }
                    }
                    (None, None) => {}
                }
            }
            return result_head.unwrap().next;
        }
    }
    let e1_1 = Some(Box::new(ListNode { val: 5, next: None }));
    let e2_1 = Some(Box::new(ListNode { val: 4, next: e1_1 }));
    let e3_1 = Some(Box::new(ListNode { val: 3, next: e2_1 }));
    let e4_1 = Some(Box::new(ListNode { val: 2, next: e3_1 }));
    let e5_1 = Some(Box::new(ListNode { val: 1, next: e4_1 }));

    let e1 = Some(Box::new(ListNode { val: 5, next: None }));
    let e2 = Some(Box::new(ListNode { val: 4, next: e1 }));
    let e3 = Some(Box::new(ListNode { val: 3, next: e2 }));
    let e4 = Some(Box::new(ListNode { val: 2, next: e3 }));
    let e5 = Some(Box::new(ListNode { val: 1, next: e4 }));
    let ret1 = Solution::merge_two_lists(e5, e5_1);
    println!("{:?}", ret1);
}

///上升下降字符串
fn leetcode_1370() {
    println!("leetcode_1370");
    impl Solution {
        pub fn sort_string(s: String) -> String {
            let mut ret = String::new();
            let mut v = vec![0; 26];
            for c in s.chars() {
                v[c as usize - 97] += 1;
            }
            while ret.len() < s.len() {
                for n in 0..26u8 {
                    if v[n as usize] > 0 {
                        ret.push((n + 97) as char);
                        v[n as usize] -= 1;
                    }
                }
                for n in (0..=25u8).rev() {
                    if v[n as usize] > 0 {
                        ret.push((n + 97) as char);
                        v[n as usize] -= 1;
                    }
                }
            }
            ret
        }
    }
    let ret = Solution::sort_string("aaaabbbbcccc".to_owned());
    println!("{}", ret);
}

///化栈为队
fn interview_03_04() {
    println!("interview_03_04");
    struct MyQueue {
        stack1: VecDeque<Option<i32>>,
        stack2: VecDeque<Option<i32>>,
    }
    impl MyQueue {
        /** Initialize your data structure here. */
        fn new() -> Self {
            MyQueue {
                stack1: VecDeque::new(),
                stack2: VecDeque::new(),
            }
        }

        /** Push element x to the back of queue. */
        fn push(&mut self, x: i32) {
            self.stack1.push_back(Some(x))
        }

        /** Removes the element from in front of queue and returns that element. */
        fn pop(&mut self) -> i32 {
            return MyQueue::peek_pop(self, true);
        }

        fn peek_pop(queue: &mut MyQueue, flag: bool) -> i32 {
            if queue.stack2.is_empty() {
                while !queue.stack1.is_empty() {
                    queue.stack2.push_back(queue.stack1.pop_back().unwrap());
                }
            }
            let ret = if flag {
                queue.stack2.pop_back().unwrap()
            } else {
                queue.stack2.back().unwrap().clone()
            };
            ret.unwrap()
        }

        /** Get the front element. */
        fn peek(&mut self) -> i32 {
            return MyQueue::peek_pop(self, false);
        }

        /** Returns whether the queue is empty. */
        fn empty(&mut self) -> bool {
            self.stack1.is_empty() && self.stack2.is_empty()
        }
    }

    let mut obj = MyQueue::new();
    obj.push(1);
    let ret_1: i32 = obj.peek();
    let ret_2: i32 = obj.pop();
    let ret_3: bool = obj.empty();
    println!("{}", ret_1);
    println!("{}", ret_2);
    println!("{}", ret_3);
}

///高度检查器
fn leetcode_1051() {
    println!("leetcode_1051");
    impl Solution {
        pub fn height_checker(heights: Vec<i32>) -> i32 {
            //排序后与原数组的差异个数
            let mut c_heights = heights.clone();
            let mut ret = 0;
            c_heights.sort();
            for i in 0..heights.len() {
                if c_heights[i as usize] != heights[i as usize] {
                    ret += 1;
                }
            }
            ret
        }
    }
    let heights = vec![1, 1, 4, 2, 1, 3];
    let ret = Solution::height_checker(heights);
    println!("{}", ret);
}

///自除数
fn leetcode_728() {
    println!("leetcode_728");
    impl Solution {
        pub fn self_dividing_numbers(left: i32, right: i32) -> Vec<i32> {
            let mut result = Vec::new();
            for num in left..=right {
                if helper(num) {
                    result.push(num)
                }
            }

            fn helper(n: i32) -> bool {
                for c in n.to_string().chars() {
                    if ((c as i32) - 48) == 0 || n % ((c as i32) - 48) != 0 {
                        return false;
                    }
                }
                return true;
            }

            result
        }
    }

    let ret = Solution::self_dividing_numbers(1, 22);
    print_vec(ret);
}

///判定字符是否唯一
fn interview_01_01() {
    println!("interview_01_01");
    impl Solution {
        pub fn is_unique(astr: String) -> bool {
            let cs = astr.chars();
            //题目没有说明，但这样AC了，假定只有大小写字母
            let mut count = vec![0; ('z' as i32 as usize) + 1]; //123
            for c in cs {
                count[(c as i32) as usize] += 1;
            }
            for &c in count.iter() {
                if c > 1 {
                    return false;
                }
            }
            true
        }
        //位运算
        pub fn is_unique2(astr: String) -> bool {
            let cs = astr.chars();
            let mut mark = 0;
            let mut mark_bit = 0;
            for c in cs {
                mark_bit = c as i32 - ('a' as i32);
                if (mark & (1 << mark_bit)) != 0 {
                    return false;
                } else {
                    mark |= 1 << mark_bit
                }
            }
            true
        }
    }

    let ret = Solution::is_unique("abc".to_owned());
    let ret2 = Solution::is_unique2("abc".to_owned());
    println!("{}", ret);
    println!("{}", ret2)
}

///两个数组间的距离值
fn leetcode_1385() {
    println!("leetcode_1385");
    impl Solution {
        //暴力解
        pub fn find_the_distance_value(arr1: Vec<i32>, arr2: Vec<i32>, d: i32) -> i32 {
            let mut c = 0;
            let _ret = arr1.iter().for_each(|&x| {
                let mut flag = false;
                arr2.iter().for_each(|&y| {
                    if !flag && (x - y).abs() > d {
                    } else {
                        flag = true;
                    }
                });
                if !flag {
                    c += 1;
                }
            });
            c
        }
    }
    let ret = Solution::find_the_distance_value(vec![4, 5, 8], vec![10, 9, 1, 8], 2);
    println!("{}", ret);
}

///二叉搜索树的第k大节点
fn interview_54() {
    println!("interview_54");
    use std::cell::RefCell;
    use std::rc::Rc;
    impl Solution {
        pub fn kth_largest(root: Option<Rc<RefCell<TreeNode>>>, k: i32) -> i32 {
            let mut ret = Vec::new();
            let mut nodes = VecDeque::new();
            let mut cur = root.clone();
            while cur.is_some() || !nodes.is_empty() {
                while let Some(c) = cur {
                    nodes.push_back(Some(c.clone()));
                    cur = c.try_borrow().unwrap().left.clone();
                }
                if let Some(n) = nodes.pop_back().unwrap() {
                    ret.push(n.try_borrow().unwrap().val);
                    cur = n.try_borrow().unwrap().right.clone();
                }
            }
            for e in ret.iter() {
                println!("{}", e);
            }
            ret[(ret.len() - k as usize)]
        }
    }
    let e2 = Some(Rc::new(RefCell::new(TreeNode {
        val: 2,
        left: None,
        right: None,
    })));
    let e1 = Some(Rc::new(RefCell::new(TreeNode {
        val: 1,
        left: None,
        right: e2,
    })));
    let e4 = Some(Rc::new(RefCell::new(TreeNode {
        val: 4,
        left: None,
        right: None,
    })));
    let root = Some(Rc::new(RefCell::new(TreeNode {
        val: 3,
        left: e1,
        right: e4,
    })));
    println!("{:?}", Solution::kth_largest(root, 4))
}

///用两个栈实现队列
fn interview_09() {
    println!("interview_09");
    struct CQueue {
        stack1: VecDeque<Option<i32>>,
        stack2: VecDeque<Option<i32>>,
    }

    /**
     * `&self` means the method takes an immutable reference.
     * If you need a mutable reference, change it to `&mut self` instead.
     */
    impl CQueue {
        fn new() -> Self {
            CQueue {
                stack1: VecDeque::new(),
                stack2: VecDeque::new(),
            }
        }

        fn append_tail(&mut self, value: i32) {
            self.stack1.push_back(Some(value))
        }

        fn delete_head(&mut self) -> i32 {
            if self.stack2.is_empty() {
                while !self.stack1.is_empty() {
                    self.stack2.push_back(self.stack1.pop_back().unwrap());
                }
            }
            let e = self.stack2.pop_back();
            if e.is_none() {
                -1
            } else {
                e.unwrap().unwrap()
            }
        }
    }

    //Your CQueue object will be instantiated and called as such:
    let mut obj = CQueue::new();
    obj.append_tail(123);
    let ret_2: i32 = obj.delete_head();
    let ret_3: i32 = obj.delete_head();
    println!("{}", ret_2);
    println!("{}", ret_3)
}

///最大数值
fn interview_16_07() {
    println!("interview_16_07");
    impl Solution {
        //不能使用if-else 比较运算符
        //max(a, b) = ((a + b) + abs(a - b)) / 2。
        pub fn maximum(a: i32, b: i32) -> i32 {
            let mut a = a as i64;
            let mut b = b as i64;
            b = a - b;
            a -= b & (b >> 32);
            a as i32
        }
    }

    let ret = Solution::maximum(10, 2);
    println!("{}", ret)
}

///增减字符串匹配
fn leetcode_942() {
    println!("leetcode_942");
    impl Solution {
        pub fn di_string_match(s: String) -> Vec<i32> {
            let n = s.len();
            //'D'代表着倒序，'I'代表升序，那么如果为'D'，只要取最大值max，同时最大值减一，作为下一个'D'的最大值；如果为'I'，则正好相反，取最小值min，同时加一，
            //作为下一个'I'的最小值；剩下最后一个值，max=min，取啥都行
            let mut arr = vec![0; n + 1];
            let mut max = n as i32;
            let mut min = 0;
            for i in 0..n {
                if s.get(i..=i).unwrap() == "D" {
                    arr[i] = max;
                    max -= 1;
                } else {
                    arr[i] = min;
                    min += 1;
                }
            }
            arr[n] = max;
            arr
        }
    }
    let ret = Solution::di_string_match("IDID".to_owned());
    print_vec(ret)
}

///有序数组的平方
fn leetcode_977() {
    println!("leetcode_977");
    impl Solution {
        //172ms,2.1MB
        pub fn sorted_squares(a: Vec<i32>) -> Vec<i32> {
            let mut a = a;
            a[0] = a[0] * a[0];
            for i in 1..a.len() {
                let tmp = a[i] * a[i];
                let mut j = i;
                while j > 0 && a[j - 1] > tmp {
                    a[j] = a[j - 1];
                    j -= 1
                }
                a[j] = tmp;
            }
            a
        }

        //12ms,2.2MB
        pub fn sorted_squares2(a: Vec<i32>) -> Vec<i32> {
            let mut ret = a;
            for (_i, n) in ret.iter_mut().enumerate() {
                *n = *n * *n;
            }
            ret.sort();
            ret
        }
    }
    let ret = Solution::sorted_squares(vec![-4, -1, 0, 3, 10]);
    let ret2 = Solution::sorted_squares2(vec![-4, -1, 0, 3, 10]);
    print_vec(ret);
    print_vec(ret2);
}

///矩阵中的幸运数
fn leetcode_1380() {
    println!("leetcode_1380");
    use std::cmp::max;
    use std::cmp::min;
    impl Solution {
        //min数组存放每行最小值;man数组存放每列最大值;
        //得到每一行的最小值和每一列的最大值。(行或列上的最大最小值)
        //然后比较第i行最小值和第j列最大值是否为同一个数，即min[i] == max[j]
        pub fn lucky_numbers(matrix: Vec<Vec<i32>>) -> Vec<i32> {
            let m = matrix.len();
            let n = matrix[0].len();
            let mut min_t = vec![i32::max_value(); m];
            let mut max_t = vec![i32::min_value(); n];
            for i in 0..m {
                for j in 0..n {
                    min_t[i] = min(min_t[i], matrix[i][j]);
                    max_t[j] = max(max_t[j], matrix[i][j]);
                }
            }
            let mut result = Vec::new();
            for i in 0..m {
                for j in 0..n {
                    if min_t[i] == max_t[j] {
                        result.push(min_t[i]);
                    }
                }
            }
            result
        }
    }
    let ret = Solution::lucky_numbers(vec![vec![3, 7, 8], vec![9, 11, 13], vec![15, 16, 17]]);
    print_vec(ret);
}

///最近的请求次数
fn leetcode_933() {
    println!("leetcode_933");
    struct RecentCounter {
        queue: VecDeque<i32>,
    }

    /**
     * `&self` means the method takes an immutable reference.
     * If you need a mutable reference, change it to `&mut self` instead.
     */
    impl RecentCounter {
        fn new() -> Self {
            RecentCounter {
                queue: VecDeque::new(),
            }
        }

        fn ping(&mut self, t: i32) -> i32 {
            self.queue.push_back(t);
            while *self.queue.front().unwrap() < t - 3000 {
                self.queue.pop_front();
            }
            self.queue.len() as i32
        }
    }

    let mut obj = RecentCounter::new();
    let ret_1: i32 = obj.ping(3001);
    println!("{}", ret_1);
    let ret_1: i32 = obj.ping(3002);
    println!("{}", ret_1);
    let ret_1: i32 = obj.ping(4000);
    println!("{}", ret_1);
    let ret_1: i32 = obj.ping(5000);
    println!("{}", ret_1);
    let ret_1: i32 = obj.ping(7000);
    println!("{}", ret_1);
}

///数组拆分 I
fn leetcode_561() {
    println!("leetcode_561");
    impl Solution {
        //尽可能保留最大值
        pub fn array_pair_sum(nums: Vec<i32>) -> i32 {
            let mut nums = nums;
            nums.sort();
            let mut sum = 0;
            let mut i = 0;
            while i < nums.len() {
                sum += nums[i as usize];
                i += 2;
            }
            sum
        }
    }

    let ret = Solution::array_pair_sum(vec![1, 4, 3, 2]);
    println!("{}", ret)
}

///将有序数组转换为二叉搜索树
fn leetcode_108() {
    println!("leetcode_108");
    use std::cell::RefCell;
    use std::rc::Rc;
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

    let ret = Solution::sorted_array_to_bst(vec![-10, -3, 0, 5, 9]);
    println!("{:?}", ret);
}

///生成每种字符都是奇数个的字符串
fn leetcode_1374() {
    println!("leetcode_1374");
    impl Solution {
        //偷鸡
        pub fn generate_the_string(n: i32) -> String {
            let mut ret = vec![];
            if n & 1 == 0 {
                ret = vec!['a'; (n - 1) as usize];
                ret.push('b');
            } else {
                ret = vec!['a'; n as usize];
            }
            let mut rs = String::new();
            ret.iter().for_each(|&c| rs.push(c));
            rs
        }
    }

    let ret = Solution::generate_the_string(4);
    println!("{}", ret);
}

///非递增顺序的最小子序列
fn leetcode_1403() {
    println!("leetcode_1403");
    //sum(A) > sum(B)且A的长度要尽可能的短
    impl Solution {
        pub fn min_subsequence(nums: Vec<i32>) -> Vec<i32> {
            let mut nums = nums;
            nums.sort_by(|a, b| b.cmp(a));
            let size = nums.len();
            let mut f = 0;
            let sum: i32 = nums.iter().sum();
            for i in 0..size {
                f += nums[i];
                if f > sum - f {
                    return nums[..i + 1].to_vec();
                }
            }
            nums
        }
    }
    let ret = Solution::min_subsequence(vec![4, 3, 10, 9, 8]);
    print_vec(ret);
}

///反转字符串中的单词 III
fn leetcode_557() {
    println!("leetcode_557");
    impl Solution {
        pub fn reverse_words(s: String) -> String {
            let arr: Vec<&str> = s.split(' ').collect();
            let ret: Vec<String> = arr
                .iter()
                .map(|word| -> String {
                    let c: String = (*word).chars().rev().collect();
                    c
                })
                .collect();
            ret.join(" ").to_string()
        }
    }

    let ret = Solution::reverse_words("Let's take LeetCode contest".to_owned());
    println!("{}", ret);
}

///可以被一步捕获的棋子数
fn leetcode_999() {
    println!("leetcode_999");
    impl Solution {
        pub fn num_rook_captures(board: Vec<Vec<char>>) -> i32 {
            fn burnot(board: &Vec<Vec<char>>, x: i32, y: i32, direction: &Vec<i32>) -> bool {
                let mut i = x;
                let mut j = y;
                let closures = move |i: i32, j: i32| -> bool { i >= 0 && i < 8 && j >= 0 && j < 8 };
                while closures(i, j) {
                    //路被堵死
                    if board[i as usize][j as usize] == 'B' {
                        break;
                    }
                    //是敌军
                    if board[i as usize][j as usize] == 'p' {
                        return true;
                    }
                    i = i + direction[0];
                    j = j + direction[1];
                }
                return false;
            }

            //定义方向数组，可以认为是四个方向向量，在棋盘问题上是常见的做法
            let directions = vec![vec![-1, 0], vec![1, 0], vec![0, 1], vec![0, -1]];
            for x in 0..8 {
                for y in 0..8 {
                    if board[x][y] == 'R' {
                        let mut res = 0;
                        for direction in directions.iter() {
                            if burnot(&board, x as i32, y as i32, direction) {
                                res += 1;
                            }
                        }
                        return res;
                    }
                }
            }

            return 0;
        }
    }

    let d = vec![
        vec!['.', '.', '.', '.', '.', '.', '.', '.'],
        vec!['.', '.', '.', 'p', '.', '.', '.', '.'],
        vec!['.', '.', '.', 'R', '.', '.', '.', 'p'],
        vec!['.', '.', '.', '.', '.', '.', '.', '.'],
        vec!['.', '.', '.', '.', '.', '.', '.', '.'],
        vec!['.', '.', '.', 'p', '.', '.', '.', '.'],
        vec!['.', '.', '.', '.', '.', '.', '.', '.'],
        vec!['.', '.', '.', '.', '.', '.', '.', '.'],
    ];
    let ret = Solution::num_rook_captures(d);
    println!("{}", ret)
}

///Nim 游戏
fn leetcode_292() {
    println!("leetcode_292");
    impl Solution {
        pub fn can_win_nim(n: i32) -> bool {
            if n % 4 == 0 {
                false
            } else {
                true
            }
        }
    }
}

///拼写单词
fn leetcode_1160() {
    println!("leetcode_1160");
    impl Solution {
        pub fn count_characters(words: Vec<String>, chars: String) -> i32 {
            let mut char_count = vec![0; 'z' as usize + 1];
            for c in chars.chars() {
                char_count[c as usize] += 1;
            }
            let mut ret = 0;
            for word in words.iter() {
                let mut word_count = vec![0; 'z' as usize + 1];
                for c in word.chars() {
                    word_count[c as usize] += 1;
                }
                let mut flg = true;
                if word
                    .chars()
                    .map(|tc| -> bool { char_count[tc as usize] < word_count[tc as usize] })
                    .find(|&x| x == true)
                    .is_some()
                {
                    flg = false;
                }
                if flg {
                    ret += word.len()
                }
            }
            ret as i32
        }
    }

    let ret = Solution::count_characters(
        vec![
            "skwgxuuuumkfurejmqrbipvlavdrozjyxhagbwetabjwevfsegqfpllgafm".to_owned(),
            "ufvpzzgpswnk".to_owned(),
            "tcouxmlrnfyoxvkeglchhryykmdvgvdxpookbtiyhuthoqsnqbowewpfgbcy".to_owned(),
            "qwpttmxzazkkfqqtrnkaejifligdvgnyvtmppjbkeuqryxzqyegttvhzolpztvigxygzvsppurijaekb"
                .to_owned(),
            "vbtvbheurzbglzljczmziitkbmtoybiwhoyfrsxvfveaxchebjdzdnnispzwbrgrbcdaistps".to_owned(),
        ]
        .iter()
        .map(|c| c.to_string())
        .collect(),
        "avyteswqppomeojxoybotzriuvxolmllevluauwb".to_owned(),
    );
    println!("{}", ret);
}

///逐步求和得到正数的最小值
fn leetcode_1413() {
    println!("leetcode_1413");
    impl Solution {
        pub fn min_start_value(nums: Vec<i32>) -> i32 {
            let mut start = 1;
            let mut min_sum = 0;
            for &n in nums.iter() {
                min_sum += n;
                //累加和的最小值是正数
                //min_sum + x >= 1
                //x >= 1 - min_sum
                //min(x) = 1 - min_sum
                start = max(start, 1 - min_sum);
            }
            start
        }
    }
    let ret = Solution::min_start_value(vec![-3, 2, -3, 4, 2]);
    println!("{}", ret)
}

///从上到下打印二叉树 II
fn interview_32_2() {
    println!("interview_32_2");
    use std::cell::RefCell;
    use std::rc::Rc;
    impl Solution {
        //leetcode 102
        pub fn level_order(root: Option<Rc<RefCell<TreeNode>>>) -> Vec<Vec<i32>> {
            let mut ret = Vec::new();
            let mut nodes = VecDeque::new();
            let mut row = Vec::new();
            let mut flag = root.clone();
            nodes.push_back(root.clone());
            while !nodes.is_empty() {
                let tmp = nodes.pop_front();
                if let Some(node) = tmp {
                    if let Some(n) = node {
                        row.push(n.try_borrow().unwrap().val);
                        if n.try_borrow().unwrap().left.is_some() {
                            nodes.push_back(n.try_borrow().unwrap().left.clone());
                        }
                        if n.try_borrow().unwrap().right.is_some() {
                            nodes.push_back(n.try_borrow().unwrap().right.clone());
                        }
                        if let Some(f) = flag.borrow() {
                            if f.as_ptr() == n.as_ptr() {
                                //直接back导致as_ptr不等
                                let tail = nodes.pop_back();
                                if tail.is_some() {
                                    flag = tail.clone().unwrap();
                                    nodes.push_back(tail.unwrap());
                                }
                                ret.push(row);
                                row = Vec::new();
                            }
                        }
                    }
                }
            }
            ret
        }
    }

    let e7 = Some(Rc::new(RefCell::new(TreeNode {
        val: 7,
        left: None,
        right: None,
    })));
    let e15 = Some(Rc::new(RefCell::new(TreeNode {
        val: 15,
        left: None,
        right: None,
    })));
    let e20 = Some(Rc::new(RefCell::new(TreeNode {
        val: 20,
        left: e15,
        right: e7,
    })));
    let e9 = Some(Rc::new(RefCell::new(TreeNode {
        val: 9,
        left: None,
        right: None,
    })));
    let e3 = Some(Rc::new(RefCell::new(TreeNode {
        val: 3,
        left: e9,
        right: e20,
    })));
    //    let e2 = Some(Rc::new(RefCell::new(TreeNode { val: 2, left: None, right: None })));
    //    let e1 = Some(Rc::new(RefCell::new(TreeNode { val: 1, left: e2, right: None })));
    let ret = Solution::level_order(e3);
    for v in ret.iter() {
        for c in v.iter() {
            print!("{}", c)
        }
        println!("\n")
    }
}

///删列造序
fn leetcode_944() {
    println!("leetcode_944");
    impl Solution {
        //删除降序的，剩下非降序的
        //12ms
        pub fn min_deletion_size(a: Vec<String>) -> i32 {
            let mut ret = 0;
            let row = a.len();
            let col = a[0].len();
            for j in 0..col {
                for i in 0..row - 1 {
                    if a[i].index(j..=j) > a[i + 1].index(j..=j) {
                        ret += 1;
                        break;
                    }
                }
            }
            ret
        }
    }
    let ret =
        Solution::min_deletion_size(vec!["cba".to_owned(), "daf".to_owned(), "ghi".to_owned()]);
    println!("{}", ret);
}

///回文数
fn leetcode_9() {
    println!("leetcoode_9");
    impl Solution {
        //1.双指针，2.找规律，3.字符串，4.栈
        pub fn is_palindrome(x: i32) -> bool {
            //4ms
            let mut n = x;
            if n < 0 || (n != 0 && n % 10 == 0) {
                return false;
            } else {
                let mut tmp = 0;
                while n > 0 {
                    tmp = tmp * 10 + (n % 10);
                    n /= 10;
                }
                return tmp == x;
            }
        }

        pub fn is_palindrome2(x: i32) -> bool {
            //直接to_string后index需要8ms
            //let mut nums: Vec<char> = x.to_string().chars().map(|x| x.to_owned() ).collect();
            //nums[i] != nums[j] 需要16ms
            let nums = x.to_string();
            let mut i = 0;
            let mut j = nums.len() - 1;
            while i < j {
                if nums.index(i..=i) != nums.index(j..=j) {
                    return false;
                }
                i += 1;
                j -= 1;
            }
            true
        }
    }
    let ret = Solution::is_palindrome(181);
    let ret2 = Solution::is_palindrome2(181);
    println!("{}", ret);
    println!("{}", ret2);
}

///罗马数字转整数
fn leetcode_13() {
    println!("leetcode_13");
    impl Solution {
        pub fn roman_to_int(s: String) -> i32 {
            let mut maps = HashMap::new();
            let chs = vec!["I", "V", "X", "L", "C", "D", "M"];
            let n = vec![1, 5, 10, 50, 100, 500, 1000];
            let mut ret = 0;
            let right = s.len() - 1;
            n.iter().enumerate().for_each(|(x, &y)| {
                maps.insert(chs[x], y);
            });
            for i in 0..right {
                //字不是顺序的，但是数字列表是顺序的
                if maps[s.index(i..=i)] >= maps[s.index(i + 1..=i + 1)] {
                    ret += maps[s.index(i..=i)];
                } else {
                    ret -= maps[s.index(i..=i)];
                }
            }
            ret += maps[s.index(right..=right)];
            ret
        }

        //Legend Lee
        pub fn roman_to_int2(s: String) -> i32 {
            fn roman_to_int_char(c: char) -> Option<i32> {
                match c {
                    'I' => Some(1),
                    'V' => Some(5),
                    'X' => Some(10),
                    'L' => Some(50),
                    'C' => Some(100),
                    'D' => Some(500),
                    'M' => Some(1000),
                    _ => None,
                }
            }

            let mut v = 0i32;
            if s.is_empty() {
                return 0;
            }

            s.chars()
                .zip(s.chars().skip(1))
                .for_each(|(first, second)| {
                    let a = roman_to_int_char(first).unwrap();
                    let b = roman_to_int_char(second).unwrap();
                    v += if a < b { -1 * a } else { a };
                });
            v += roman_to_int_char(s.chars().last().unwrap()).unwrap();

            v
        }

        //闲杂织造
        pub fn roman_to_int3(s: String) -> i32 {
            let tr = vec![
                ('I', 1),
                ('V', 5),
                ('X', 10),
                ('L', 50),
                ('C', 100),
                ('D', 500),
                ('M', 1000),
            ];
            let hash: HashMap<char, i32> = tr.iter().cloned().collect();
            let chars: Vec<char> = s.chars().collect();
            let mut ans = 0;
            let n = chars.len();
            for i in 0..n - 1 {
                let cc = hash[&chars[i]];
                let nc = hash[&chars[i + 1]];
                if cc < nc {
                    ans -= cc;
                } else {
                    ans += cc;
                }
            }
            ans + hash[&chars[n - 1]]
        }
    }

    let ret = Solution::roman_to_int("LVIII".to_owned());
    let ret2 = Solution::roman_to_int2("LVIII".to_owned());
    let ret3 = Solution::roman_to_int3("LVIII".to_owned());
    println!("{}", ret);
    println!("{}", ret2);
    println!("{}", ret3);
}

///链表的中间结点
fn leetcode_876() {
    println!("leetcode_876");
    impl Solution {
        pub fn middle_node(head: Option<Box<ListNode>>) -> Option<Box<ListNode>> {
            let mut q = &head;
            let mut s = &head;
            while q.is_some() && q.as_ref().unwrap().next.is_some() {
                q = &q.as_ref().unwrap().next.as_ref().unwrap().next;
                s = &s.as_ref().unwrap().next;
            }
            s.clone()
        }
    }

    let e1 = Some(Box::new(ListNode { val: 5, next: None }));
    let e2 = Some(Box::new(ListNode { val: 4, next: e1 }));
    let e3 = Some(Box::new(ListNode { val: 3, next: e2 }));
    let e4 = Some(Box::new(ListNode { val: 2, next: e3 }));
    let e5 = Some(Box::new(ListNode { val: 1, next: e4 }));
    let ret = Solution::middle_node(e5);
    println!("{:?}", ret)
}

///键盘行
fn leetcode_500() {
    println!("leetcode_500");
    impl Solution {
        pub fn find_words(words: Vec<String>) -> Vec<String> {
            let map: HashMap<char, i32> = vec![
                ('Q', 0),
                ('W', 0),
                ('E', 0),
                ('R', 0),
                ('T', 0),
                ('Y', 0),
                ('U', 0),
                ('I', 0),
                ('O', 0),
                ('P', 0),
                ('A', 1),
                ('S', 1),
                ('D', 1),
                ('F', 1),
                ('G', 1),
                ('H', 1),
                ('J', 1),
                ('K', 1),
                ('L', 1),
                ('Z', 2),
                ('X', 2),
                ('C', 2),
                ('V', 2),
                ('B', 2),
                ('N', 2),
                ('M', 2),
            ]
            .iter()
            .cloned()
            .collect();
            words
                .iter()
                .filter(|word| {
                    let chars: Vec<char> = word.chars().collect();
                    let index: HashSet<i32> = chars
                        .iter()
                        .map(|c| -> i32 { map[&c.to_ascii_uppercase()] })
                        .collect();
                    index.len() == 1
                })
                .cloned()
                .collect()
        }
    }

    let ret = Solution::find_words(vec![
        "Hello".to_string(),
        "Alaska".to_string(),
        "Dad".to_string(),
        "Peace".to_string(),
    ]);
    print_vec_string(ret);
}

///最长公共前缀
fn leetcode_14() {
    println!("leetcode_14");
    impl Solution {
        //0 ms, 2.1 MB
        pub fn longest_common_prefix(strs: Vec<String>) -> String {
            //选出最小的字符串w，使用w的所有子串去匹配strs中的单词
            let mut min_word: &String = &"".to_owned();
            let mut min_length = usize::max_value();
            if strs.is_empty() {
                return min_word.clone();
            }
            let mut result: &str = "";
            strs.iter().for_each(|word| {
                if min_length > word.len() {
                    min_length = word.len();
                    min_word = word;
                }
            });
            while min_length > 0 {
                let sub_word = &min_word[0..min_length];
                let mut is_max = true;
                for w in strs.iter() {
                    if w.starts_with(sub_word) == false {
                        is_max = false;
                        break;
                    }
                }
                if is_max {
                    result = sub_word;
                    break;
                }
                min_length -= 1;
            }
            result.to_owned()
        }

        //0 ms, 2.1 MB
        pub fn longest_common_prefix2(strs: Vec<String>) -> String {
            let strs = &strs;
            let mut result: String = "".to_owned();
            if strs.is_empty() {
                return result;
            }
            //选取第一个单词w，对w的长度从大到小进行切片，将切片与所有单词进行匹配
            result = strs[0].clone();
            for (_index, word) in strs.iter().enumerate() {
                while !word.starts_with(result.as_str()) {
                    result = result[0..result.len() - 1].to_owned();
                    if result.len() == 0 {
                        return "".to_owned();
                    }
                }
            }
            result
        }

        //4 ms, 2.1 MB
        pub fn longest_common_prefix3(strs: Vec<String>) -> String {
            let strs = &strs;
            let result: String = "".to_owned();
            if strs.is_empty() {
                return result;
            }
            //选取第一个单词w，与其他字符串依次比较对应位置上的字符
            let word = &strs[0].clone();
            let init_word: Vec<char> = word.chars().collect();
            for i in 0..init_word.len() {
                let c: char = init_word[i];
                for j in 1..strs.len() {
                    let cs: Vec<char> = strs[j].chars().collect();
                    if i < cs.len() && c != cs[i] || i == cs.len() {
                        return word[0..i].to_owned();
                    }
                }
            }
            word.to_owned()
        }
    }

    let ret = Solution::longest_common_prefix(vec![
        "flower".to_owned(),
        "flow".to_owned(),
        "flight".to_owned(),
    ]);
    let ret2 = Solution::longest_common_prefix(vec![]);
    println!("{}", ret);
    println!("{}", ret2);

    let ret = Solution::longest_common_prefix2(vec![
        "flower".to_owned(),
        "flow".to_owned(),
        "flight".to_owned(),
    ]);
    let ret2 = Solution::longest_common_prefix2(vec![]);
    println!("{}", ret);
    println!("{}", ret2);

    let ret = Solution::longest_common_prefix3(vec!["aa".to_owned(), "a".to_owned()]);
    let ret2 = Solution::longest_common_prefix3(vec![]);
    println!("{}", ret);
    println!("{}", ret2);
}

///有效的括号
fn leetcode_20() {
    println!("leetcode_20");
    impl Solution {
        pub fn is_valid(s: String) -> bool {
            let chars: Vec<char> = s.chars().collect();
            let mut stack = Vec::<char>::new();
            for &c in chars.iter() {
                if c == '{' || c == '[' || c == '(' {
                    stack.push(c);
                } else {
                    if stack.is_empty() {
                        return false;
                    }
                    let c_stack = stack.pop();
                    if let Some(cStack) = c_stack {
                        if c == ')' && cStack != '('
                            || c == ']' && cStack != '['
                            || c == '}' && cStack != '{'
                        {
                            return false;
                        }
                    }
                }
            }
            stack.is_empty()
        }
    }
    let ret = Solution::is_valid("()[]{}".to_owned());
    println!("{}", ret);
}

///搜索插入位置
fn leetcode_35() {
    println!("leetcode_35");
    impl Solution {
        pub fn search_insert(nums: Vec<i32>, target: i32) -> i32 {
            let nums = nums;
            //找到知己反回索引，没有找到则返回该元素插入后保持数组仍然有序的索引位置，主要用于有序的数组/向量
            let ret = match nums.binary_search(&target) {
                Ok(found_index) => found_index,
                Err(maybe_insert) => maybe_insert,
            };
            ret as i32
        }
    }
    let ret = Solution::search_insert(vec![1, 3, 5, 6], 5);
    println!("{}", ret);
}

///按奇偶排序数组
fn leetcode_905() {
    println!("leetcode_905");
    impl Solution {
        pub fn sort_array_by_parity(a: Vec<i32>) -> Vec<i32> {
            let (mut even, mut odd): (Vec<i32>, Vec<i32>) = a.iter().partition(|&n| n % 2 == 0);
            even.append(&mut odd);
            even
        }
    }
    let ret = Solution::sort_array_by_parity(vec![3, 1, 2, 4]);
    print_vec(ret);
}

///独一无二的出现次数
fn leetcode_1207() {
    println!("leetcode_1207");
    impl Solution {
        pub fn unique_occurrences(arr: Vec<i32>) -> bool {
            let mut map = HashMap::new();
            for num in arr.iter() {
                let n = if map.contains_key(num) {
                    map.get(num).unwrap()
                } else {
                    &0
                };
                if map.contains_key(num) {
                    map.insert(num, *n + 1);
                } else {
                    map.insert(num, 1);
                }
            }
            let set: HashSet<i32> = map.values().cloned().collect();
            set.len() == map.len()
        }
    }
    let ret = Solution::unique_occurrences(vec![1, 2, 2, 1, 1, 3]);
    println!("{}", ret);
}

///外观数列
fn leetcode_38() {
    println!("leetcode_38");
    impl Solution {
        //给一个数，这个数是1
        //描述上一步的数，这个数是1即一个1，故写作11
        //描述上一步的数，这个数是11即两个1，故写作21
        //描述上一步的数，这个数是21即一个2一个1，故写作12-11
        //描述上一步的数，这个数是1211即一个1一个2两个1，故写作11-12-21
        pub fn count_and_say(n: i32) -> String {
            if n == 1 {
                return "1".to_owned();
            }
            let pre_str = Solution::count_and_say(n - 1);
            let mut curr_str = String::new();
            let mut pre_char = None;
            let mut pre_char_count = 0;
            //当n为2及以上时。因为下一个数列是对上面的解释。所以用三个变量，一个代表数量count ,一个代表前一个数字pre，一个代表后一个数字back
            for back_char in pre_str.chars() {
                if pre_char == None {
                    pre_char = Some(back_char);
                }
                if back_char != pre_char.unwrap() {
                    //将pre_char_count个pre_char分别存储
                    curr_str.push_str(&pre_char_count.to_string());
                    curr_str.push(pre_char.unwrap());
                    pre_char = Some(back_char);
                    //不同时，重置个数
                    pre_char_count = 1;
                } else {
                    //相等时计算字符个数
                    pre_char_count += 1;
                }
            }
            //最后一位在前面跳出来了，需要追加
            if pre_char_count > 0 {
                curr_str.push_str(&pre_char_count.to_string());
                curr_str.push(pre_char.unwrap());
            }
            curr_str
        }
    }
    let ret = Solution::count_and_say(4);
    println!("{}", ret);
}

///最后一个单词的长度
fn leetcode_58() {
    println!("leetcode_58");
    impl Solution {
        pub fn length_of_last_word(s: String) -> i32 {
            let chars: Vec<&str> = s.trim().split(' ').collect();
            chars[chars.len() - 1].len() as i32
        }
    }
    let ret = Solution::length_of_last_word("Hello World".to_owned());
    println!("{}", ret);
}

///非递减数列
fn leetcode_665() {
    println!("leetcode_665");
    impl Solution {
        pub fn check_possibility(nums: Vec<i32>) -> bool {
            let mut nums = nums;
            let mut cnt = 0;
            if nums.len() < 2 {
                return true;
            }
            //将局部调整为最优方案
            //当 i 和 i+1 构成逆序时
            for i in 0..nums.len() - 1 {
                if nums[i] > nums[i + 1] {
                    if i == 0 || nums[i - 1] <= nums[i + 1] {
                        //如果 i-1 和 i+1 是升序排列，此时缩小 i 的值
                        nums[i] = nums[i + 1];
                    } else if nums[i - 1] > nums[i + 1] {
                        //如果 i-1 和 i+1 是降序排列，此时增大 i+1 的值
                        nums[i + 1] = nums[i];
                    } else {
                    }
                    //计算需要改动的次数
                    cnt += 1;
                    if cnt > 1 {
                        return false;
                    }
                }
            }
            true
        }
    }

    let ret = Solution::check_possibility(vec![4, 2, 3]);
    println!("{}", ret);
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
    leetcode_1021();
    interview_24();
    leetcode_1252();
    leetcode_1323();
    leetcode_617();
    leetcode_461();
    leetcode_709();
    leetcode_1304();
    lettcode_804();
    leetcode_832();
    interview_25();
    leetcode_1370();
    interview_03_04();
    leetcode_1051();
    leetcode_728();
    interview_01_01();
    leetcode_1385();
    interview_54();
    interview_09();
    interview_16_07();
    leetcode_942();
    leetcode_977();
    leetcode_1380();
    leetcode_933();
    leetcode_561();
    leetcode_108();
    leetcode_1374();
    leetcode_1403();
    leetcode_557();
    leetcode_999();
    leetcode_292();
    leetcode_1160();
    leetcode_1413();
    interview_32_2();
    leetcode_944();
    leetcode_9();
    leetcode_13();
    leetcode_876();
    leetcode_500();
    leetcode_14();
    leetcode_20();
    leetcode_35();
    leetcode_905();
    leetcode_1207();
    leetcode_38();
    leetcode_58();
    leetcode_665();
}

fn print_vec(nums: Vec<i32>) {
    for e in nums.iter() {
        println!("{}", e);
    }
}

fn print_vec_string(nums: Vec<String>) {
    for e in nums.iter() {
        println!("{}", e);
    }
}
