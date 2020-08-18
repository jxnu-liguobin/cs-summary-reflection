use crate::pre_structs::Solution;

///解压缩编码列表
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
