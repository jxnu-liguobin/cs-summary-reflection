Leetcode Rust 实现
--

超简单的算法题目，主要为了熟悉rust语法。源码在Solution.rs，并包含部分测试（均AC，90%是双100）

根据优先级，当LeetCode题目本身不支持（或不方便实现，比如Rust TreeNode）才会选择Java，并在java-leetcode项目下实现。

无注明，默认是LeetCode系列

* 1403 非递增顺序的最小子序列
```rust
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
```
* 561 数组拆分 I
```rust
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
```
* 933 最近的请求次数
```rust
struct RecentCounter {
    queue: VecDeque<i32>
}

/**
 * `&self` means the method takes an immutable reference.
 * If you need a mutable reference, change it to `&mut self` instead.
 */
impl RecentCounter {
    fn new() -> Self {
        RecentCounter { queue: VecDeque::new() }
    }

    fn ping(&mut self, t: i32) -> i32 {
        self.queue.push_back(t);
        while *self.queue.front().unwrap() < t - 3000 {
            self.queue.pop_front();
        }
        self.queue.len() as i32
    }
}
```
* 面试题 02.02 返回倒数第 k 个节点值
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
* 面试题22 链表中倒数第k个节点
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
* 1351 统计有序矩阵中的负数
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
* 面试题55 - I 二叉树的深度
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
* 面试题 04.02 最小高度树
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
* 1281 整数的各位积和之差
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
* 面试题58 - II 左旋转字符串
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
* 1365 有多少小于当前数字的数字
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
* 1342 将数字变成 0 的操作次数
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
* 1313 解压缩编码列表
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
* 面试题17 打印从1到最大的n位数
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
* 面试题05 替换空格
```rust
impl Solution {
    pub fn replace_space(s: String) -> String {
        let mut str = s;
        str.replace(" ", "%20")
    }
}
```
* 1221 分割平衡字符串
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
* 面试题06 从尾到头打印链表
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
* 938 二叉搜索树的范围和
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
* 1021 删除最外层的括号
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
* 面试题24 反转链表
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
* 1252 奇数值单元格的数目
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
* 1323 6 和 9 组成的最大数字
```rust
impl Solution {
    pub fn maximum69_number(num: i32) -> i32 {
        num.to_string().replacen('6', "9", 1).parse().unwrap()
    }
}
```
* 617 合并二叉树
```rust
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
```
* 461 汉明距离
```rust
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
```
* 709 转换成小写字母
```rust
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
```
* 1304 和为零的N个唯一整数
```rust
impl Solution {
    //双指针 平均分布，不同解决得到的结果不同
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
    //[sum=1~n-2,-sum]
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
```
* 804 唯一摩尔斯密码词
```rust
impl Solution {
    pub fn unique_morse_representations(words: Vec<String>) -> i32 {
        let m = vec![".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."];
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
```
* 832 翻转图像
```rust
impl Solution {
    pub fn flip_and_invert_image(a: Vec<Vec<i32>>) -> Vec<Vec<i32>> {
        let ret: Vec<Vec<i32>> = a.iter().map(|mut row| -> Vec<i32> {
            let mut new_row: Vec<i32> = row.iter().map(|x| -> i32 {
                let new_x = if let 0 = x {
                    1
                } else { 0 };
                new_x
            }).collect();
            new_row.reverse();
            new_row
        }).collect();
        ret
    }
}
```
* 面试题25 合并两个排序的链表
```rust
pub fn merge_two_lists(l1: Option<Box<ListNode>>, l2: Option<Box<ListNode>>) -> Option<Box<ListNode>> {
    let mut result_head: Option<Box<ListNode>> = Some(Box::new(ListNode { val: -1, next: None }));
    let mut cur = &mut result_head;
    let mut l1 = l1;
    let mut l2 = l2;
    let mut next = true;
    while l1.is_some() || l2.is_some() {
        //take去除值，并保留为None
        match (l1.take(), l2.take()) {
            (Some(_l1), None) => {
                //可变引用
                if let Some(ref mut _cur) = cur {
                    _cur.next = Some(_l1);
                }
            },
            (None, Some(_l2)) => {
                if let Some(ref mut _cur) = cur {
                    _cur.next = Some(_l2);
                }
            },
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
            },
            (None, None) => {},
        }
    }
    return result_head.unwrap().next;
}
```
* 1370 上升下降字符串
```rust
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
```
* 面试题 03.04 化栈为队
```rust
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
        return MyQueue::peek_pop(self, true)
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
        return MyQueue::peek_pop(self, false)
    }

    /** Returns whether the queue is empty. */
    fn empty(&mut self) -> bool {
        self.stack1.is_empty() && self.stack2.is_empty()
    }
}
```
* 1051 高度检查器
```rust
impl Solution {
    pub fn height_checker(heights: Vec<i32>) -> i32 {
        //排序后与原数组的差异个数
        let mut c_heights = heights.clone();
        let mut ret = 0;
        c_heights.sort();
        for i in 0 ..heights.len() {
            if c_heights[i as usize] != heights[i as usize] {
                ret += 1;
            }
        }
        ret
    }
}
```
* 728 自除数
```rust
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
```
* 面试题 01.01 判定字符是否唯一
```rust
impl Solution {
    pub fn is_unique(astr: String) -> bool {
        let cs = astr.chars();
        //题目没有说明，但这样AC了，假定只有大小写字母
        let mut count = vec![0; ('z' as i32 as usize) + 1];//123
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
```
* 1385 两个数组间的距离值
```rust
impl Solution {
    //暴力解
    pub fn find_the_distance_value(arr1: Vec<i32>, arr2: Vec<i32>, d: i32) -> i32 {
        let mut c = 0;
        let ret = arr1.iter().for_each(|&x| {
            let mut flag = false;
            arr2.iter().for_each(|&y| {
                if !flag && (x - y).abs() > d {} else {
                    flag = true;
                }
            }
            );
            if !flag {
                c += 1;
            }
        });
        c
    }
}
```
* 面试题 54 二叉搜索树的第k大节点
```rust
use std::rc::Rc;
use std::cell::RefCell;
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
        };
        ret[(ret.len() - k as usize)]
    }
}
```
* 面试题 09 用两个栈实现队列
```rust
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
        if e.is_none() { -1 } else { e.unwrap().unwrap() }
    }
}
```
* 面试题 16.07 最大数值
```rust
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
```
* 942 增减字符串匹配
```rust
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
```
* 977 有序数组的平方
```rust
impl Solution {
    //172ms,2.1MB
    pub fn sorted_squares(a: Vec<i32>) -> Vec<i32> {
        let mut a = a;
        a[0] = a[0] * a[0];
        for i in 1..a.len() {
            let mut tmp = a[i] * a[i];
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
        for (i, n) in ret.iter_mut().enumerate() {
            *n = *n * *n;
        }
        ret.sort();
        ret
    }
}
```
* 1380 矩阵中的幸运数
```rust
use std::cmp::max;
use std::cmp::min;
impl Solution {
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
```
* 1374 生成每种字符都是奇数个的字符串
```rust
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
        ret.iter().for_each(|&c| { rs.push(c) });
        rs
    }
}
```
* 557 反转字符串中的单词 III
```rust
impl Solution {
    pub fn reverse_words(s: String) -> String {
        let mut arr: Vec<&str> = s.split(' ').collect();
        let ret: Vec<String> = arr.iter_mut().map(|word| -> String {
            let c: String = (*word).chars().rev().collect();
            c
        }).collect();
        ret.join(" ").to_string()
    }
}
```