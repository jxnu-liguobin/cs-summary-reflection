use std::ops::Index;

use crate::pre_structs::Solution;

///回文数
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
        let mut nums = x.to_string();
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
