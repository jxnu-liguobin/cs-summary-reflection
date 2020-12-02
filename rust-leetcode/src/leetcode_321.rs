use std::cmp::max;
use std::collections::VecDeque;

use crate::pre_structs::Solution;

/// 321. 拼接最大数
impl Solution {
    /// 1.从num1中取出i个数，从num2中取出k-i个数拼接成最终结果
    /// 2.维护一个单调递减的栈，从num中依次往栈中添加或删除数据，直到栈中保留的数据量为期望值
    /// 3.拼接过程类似归并排序，值大的取出来放在前面，值小的放在后面
    //12 ms,50.00%
    //2 MB,100.00%
    pub fn max_number(nums1: Vec<i32>, nums2: Vec<i32>, k: i32) -> Vec<i32> {
        let mut result: Vec<i32> = vec![];
        for i in 0..=nums1.len() as i32 {
            if i <= k {
                if k - i <= nums2.len() as i32 {
                    let v1 = Solution::get_max_sub_array(nums1.as_ref(), i);
                    let v2 = Solution::get_max_sub_array(nums2.as_ref(), k - i);
                    let vv = Solution::merge_vec(v1.as_ref(), v2.as_ref());
                    result = max(result, vv);
                }
            }
        }
        return result;
    }

    ///使用单调递减的栈，获取指定个数的子数组
    fn get_max_sub_array(array: &Vec<i32>, k: i32) -> Vec<i32> {
        let mut res: VecDeque<i32> = Default::default();
        let mut drop = array.len() - k as usize;
        for &digit in array {
            while (drop > 0) && (res.len() > 0) && (res[res.len() - 1] < digit) {
                res.pop_back();
                drop -= 1;
            }
            res.push_back(digit);
        }
        while drop > 0 {
            res.pop_back();
            drop -= 1;
        }
        return Vec::from(res);
    }

    ///合并列表
    fn merge_vec(v1: &Vec<i32>, v2: &Vec<i32>) -> Vec<i32> {
        let mut res: VecDeque<i32> = Default::default();
        let mut i = 0;
        let mut j = 0;
        let v1_len = v1.len();
        let v2_len = v2.len();
        while i < v1_len && j < v2_len {
            if v1[i..v1_len] > v2[j..v2_len] {
                res.push_back(v1[i]);
                i += 1;
            } else {
                res.push_back(v2[j]);
                j += 1;
            }
        }
        while i < v1_len {
            res.push_back(v1[i]);
            i += 1;
        }
        while j < v2_len {
            res.push_back(v2[j]);
            j += 1;
        }
        return Vec::from(res);
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::{print_vec, Solution};

    #[test]
    fn max_number() {
        let ret = Solution::max_number([3, 4, 6, 5].to_vec(), [9, 1, 2, 5, 8, 3].to_vec(), 5);
        print_vec(ret)
    }
}
