use crate::pre_structs::Solution;

///统计有序矩阵中的负数
impl Solution {
    //应该将矩阵是排序的考虑进去，从右下角或左下角使用标记
    pub fn count_negatives(grid: Vec<Vec<i32>>) -> i32 {
        let mut count: i32 = 0;
        for r in grid.iter() {
            count += r.iter().filter(|&&x| x < 0).count() as i32
        }
        return count;
    }
}
