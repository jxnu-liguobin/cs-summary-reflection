use std::cmp::{max, min};

use crate::pre_structs::Solution;

/// 581. 最短无序连续子数组
/// 给定一个整数数组，你需要寻找一个连续的子数组，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
/// 你找到的子数组应是最短的，请输出它的长度。
impl Solution {
    //0 ms,100.00%
    //2.1 MB,100.00%
    pub fn find_unsorted_subarray(nums: Vec<i32>) -> i32 {
        let mut max_ = i32::min_value();
        let len = nums.len();
        let mut min_ = i32::max_value();
        let mut l = -1i32;
        let mut r = -2i32;
        for i in 0..len {
            //从左到右找出最后一个破坏递增的数
            max_ = max(max_, nums[i]);
            //从右到左找出最后一个破坏递减的数
            min_ = min(min_, nums[len - 1 - i]);
            if max_ > nums[i] {
                r = i as i32;
            }
            if min_ < nums[len - 1 - i] {
                l = (len - 1 - i) as i32;
            }
        }
        (r - l + 1) as i32
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn find_unsorted_subarray() {
        let ret = Solution::find_unsorted_subarray(vec![2, 6, 4, 8, 10, 9, 15].to_vec());
        assert_eq!(ret, 5)
    }
}
