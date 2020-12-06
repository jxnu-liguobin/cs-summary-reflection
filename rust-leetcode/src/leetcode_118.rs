use crate::pre_structs::Solution;

/// 118. 杨辉三角
impl Solution {
    // 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
    // 在杨辉三角中，每个数是它左上方和右上方的数的和。
    // 0 ms,100.00%
    // 2 MB,43.75%
    pub fn generate(num_rows: i32) -> Vec<Vec<i32>> {
        let num_rows = num_rows as usize;
        let mut result = Vec::with_capacity(num_rows);
        for i in 0..num_rows {
            result.push(vec![0; i + 1]);
            for j in 0..=i {
                result[i][j] = if j == 0 || j == i {
                    1
                } else {
                    result[i - 1][j - 1] + result[i - 1][j]
                }
            }
        }
        result
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::{print_vec_without_enter, Solution};

    #[test]
    fn generate() {
        let ret = Solution::generate(5);
        for r in ret {
            print_vec_without_enter(r);
            print!("=======")
        }
    }
}
