use std::collections::VecDeque;
use std::ops::Index;

///Leetcode 超简单的算法题目，主要为了熟悉rust语法
pub fn solutions() {
    interview_58_2();
}

fn interview_58_2() {
    struct Solution;
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