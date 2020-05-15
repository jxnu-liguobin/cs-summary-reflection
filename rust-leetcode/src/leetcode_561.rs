use crate::pre_structs::Solution;

///数组拆分 I
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
