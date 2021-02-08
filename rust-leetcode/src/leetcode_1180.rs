use crate::pre_structs::Solution;

/// 1480. 一维数组的动态和
/// 给你一个数组 nums 。数组「动态和」的计算公式为：runningSum[i] = sum(nums[0]…nums[i]) 。
impl Solution {
    // 0 ms,100.00%
    // 2.1 MB,18.07%
    pub fn running_sum(nums: Vec<i32>) -> Vec<i32> {
        let mut nums = nums;
        let mut tmp_sum = 0;
        for i in 0..nums.len() {
            tmp_sum += nums[i];
            nums[i] = tmp_sum;
        }

        nums
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::{print_vec, Solution};

    #[test]
    fn running_sum() {
        let ret = Solution::running_sum(vec![3, 1, 2, 10, 1].to_owned());
        print_vec(ret);
    }
}
