use crate::pre_structs::Solution;

///非递减数列
impl Solution {
    pub fn check_possibility(nums: Vec<i32>) -> bool {
        let mut nums = nums;
        let mut cnt = 0;
        if nums.len() < 2 {
            return true;
        }
        //将局部调整为最优方案
        //当 i 和 i+1 构成逆序时
        for i in 0..nums.len() - 1 {
            if nums[i] > nums[i + 1] {
                if i == 0 || nums[i - 1] <= nums[i + 1] {
                    //如果 i-1 和 i+1 是升序排列，此时缩小 i 的值
                    nums[i] = nums[i + 1];
                } else if nums[i - 1] > nums[i + 1] {
                    //如果 i-1 和 i+1 是降序排列，此时增大 i+1 的值
                    nums[i + 1] = nums[i];
                } else {
                }
                //计算需要改动的次数
                cnt += 1;
                if cnt > 1 {
                    return false;
                }
            }
        }
        true
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn check_possibility() {}
}
