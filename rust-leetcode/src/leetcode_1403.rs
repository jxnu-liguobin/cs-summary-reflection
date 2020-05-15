use crate::pre_structs::Solution;

///非递增顺序的最小子序列
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
