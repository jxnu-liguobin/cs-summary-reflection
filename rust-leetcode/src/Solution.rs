use std::collections::VecDeque;
use std::ops::Index;

///Leetcode 超简单的算法题目，主要为了熟悉rust语法
pub fn solutions() {
    interview_58_2();
    leetcode_1365();
    leetcode_1342();
    leetcode_1313();
}

struct Solution;

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