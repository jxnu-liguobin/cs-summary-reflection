use std::cmp::max;
use std::cmp::min;

use crate::pre_structs::Solution;

///矩阵中的幸运数
impl Solution {
    //min数组存放每行最小值;man数组存放每列最大值;
    //得到每一行的最小值和每一列的最大值。(行或列上的最大最小值)
    //然后比较第i行最小值和第j列最大值是否为同一个数，即min[i] == max[j]
    pub fn lucky_numbers(matrix: Vec<Vec<i32>>) -> Vec<i32> {
        let m = matrix.len();
        let n = matrix[0].len();
        let mut min_t = vec![i32::max_value(); m];
        let mut max_t = vec![i32::min_value(); n];
        for i in 0..m {
            for j in 0..n {
                min_t[i] = min(min_t[i], matrix[i][j]);
                max_t[j] = max(max_t[j], matrix[i][j]);
            }
        }
        let mut result = Vec::new();
        for i in 0..m {
            for j in 0..n {
                if min_t[i] == max_t[j] {
                    result.push(min_t[i]);
                }
            }
        }
        result
    }
}
