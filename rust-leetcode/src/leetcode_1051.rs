use crate::pre_structs::Solution;

///高度检查器
impl Solution {
    pub fn height_checker(heights: Vec<i32>) -> i32 {
        //排序后与原数组的差异个数
        let mut c_heights = heights.clone();
        let mut ret = 0;
        c_heights.sort();
        for i in 0..heights.len() {
            if c_heights[i as usize] != heights[i as usize] {
                ret += 1;
            }
        }
        ret
    }
}
