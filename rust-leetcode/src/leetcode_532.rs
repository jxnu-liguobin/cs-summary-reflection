use std::collections::HashMap;

use crate::pre_structs::Solution;

/// 532. 数组中的K-diff数对
/// 给定一个整数数组和一个整数 k, 你需要在数组里找到不同的 k-diff 数对。
/// 这里将 k-diff 数对定义为一个整数对 (i, j), 其中 i 和 j 都是数组中的数字，且两数之差的绝对值是 k.
impl Solution {
    //满足条件的个数：k=0时，求相等对，k!=0时，求k+key是否存在对应值
    //4MS 100%,2.2MB 100%
    pub fn find_pairs(nums: Vec<i32>, k: i32) -> i32 {
        let mut ret = 0;
        let len = nums.len();
        //已知大小避免扩容，使用new需要8ms
        let mut hash_map = HashMap::with_capacity(len);
        for i in 0..len {
            if !hash_map.contains_key(&nums[i]) {
                hash_map.insert(nums[i], 1);
            } else {
                hash_map.insert(nums[i], hash_map[&nums[i]] + 1);
            }
        }

        for (key, &val) in hash_map.iter() {
            if k == 0 && val > 1 {
                ret += 1;
            }
            if k > 0 && hash_map.contains_key(&(key + k)) {
                ret += 1;
            }
        }
        ret
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn find_pairs() {
        let ret = Solution::find_pairs(vec![1, 2, 3, 4, 5].to_vec(), 1);
        let ret2 = Solution::find_pairs(vec![0, 0, 0].to_vec(), 0);
        assert_eq!(ret, 4);
        assert_eq!(ret2, 1);
    }
}
