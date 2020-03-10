use std::collections::VecDeque;
use std::ops::Index;

///Leetcode 超简单的算法题目，主要为了熟悉rust语法
pub fn solutions() {
    interview_58_2();
    leetcode_1365();
}

struct Solution;

fn interview_58_2() {
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

fn leetcode_1365() {
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
    for e in result.iter() {
        print!("{}", e);
    }
}