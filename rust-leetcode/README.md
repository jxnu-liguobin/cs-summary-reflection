Leetcode Rust 实现
--

超简单的算法题目，主要为了熟悉rust语法。源码在Solution.rs，并包含测试

不区分具体的LeetCode系列

* 返回倒数第 k 个节点值
```rust
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
```
* 链表中倒数第k个节点
```rust
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
```
* 统计有序矩阵中的负数
```rust
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
```
* 二叉树的深度
```rust
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
```
* 最小高度树
```rust
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
```
* 整数的各位积和之差
```rust
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
```
* 左旋转字符串
```rust
impl Solution {
    pub fn reverse_left_words(s: String, n: i32) -> String {
        let mut s1 = String::from(&s[0..n as usize]);
        let s2 = &s[n as usize..s.len()];
        s1.insert_str(0, s2);
        s1.to_owned()
    }
}
```
* 有多少小于当前数字的数字
```rust
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
```
* 将数字变成 0 的操作次数
```rust
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
```
* 解压缩编码列表
```rust
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
```
* 打印从1到最大的n位数
```rust
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
```
* 替换空格
```rust
impl Solution {
    pub fn replace_space(s: String) -> String {
        let mut str = s;
        str.replace(" ", "%20")
    }
}
```
* 分割平衡字符串
```rust
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
``` 
* 从尾到头打印链表
```rust
impl Solution {
    pub fn reverse_print(head: Option<Box<ListNode>>) -> Vec<i32> {
        let mut ret = Vec::<i32>::new();
        let mut node = head.as_ref();
        loop {
            if let Some(root) = node {
                ret.push(root.val);
                node = root.next.as_ref();
            } else { break }
        }
        ret.reverse();
        ret
    }
}
```
* 二叉搜索树的范围和
```rust
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
```
* 删除最外层的括号
```rust
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
   //内存占用小
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
```
* 反转链表
```rust
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
```
* 奇数值单元格的数目
```rust
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
```
* 6 和 9 组成的最大数字
```rust
impl Solution {
    pub fn maximum69_number(num: i32) -> i32 {
        num.to_string().replacen('6', "9", 1).parse().unwrap()
    }
}
```
