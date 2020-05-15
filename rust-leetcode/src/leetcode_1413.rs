use std::cmp::max;

use crate::pre_structs::Solution;

///逐步求和得到正数的最小值
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
