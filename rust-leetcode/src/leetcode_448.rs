use crate::pre_structs::Solution;

/// 448. 找到所有数组中消失的数字
/// 给定一个范围在1 ≤ a[i] ≤ n (n = 数组大小 ) 的 整型数组，数组中的元素一些出现了两次，另一些只出现一次。
/// 找到所有在 [1, n] 范围之间没有出现在数组中的数字。
/// 您能在不使用额外空间且时间复杂度为O(n)的情况下完成这个任务吗? 你可以假定返回的数组不算在额外空间内。
impl Solution {
    //12 ms,84.09%
    //2.6 MB,45.45%
    pub fn find_disappeared_numbers(nums: Vec<i32>) -> Vec<i32> {
        let mut nums = nums;
        let mut ret = Vec::<i32>::new();
        let len = nums.len() as i32;
        for i in 0..len as usize {
            let idx = nums[i] - 1;
            nums[(idx % len) as usize] += len;
        }
        for i in 0..len as usize {
            if nums[i] <= len {
                ret.push(i as i32 + 1);
            }
        }
        ret
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::{print_vec, Solution};

    #[test]
    fn find_disappeared_numbers() {
        let ret = Solution::find_disappeared_numbers(vec![4, 3, 2, 7, 8, 2, 3, 1].to_owned());
        print_vec(ret);
    }
}
