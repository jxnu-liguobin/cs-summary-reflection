use crate::pre_structs::Solution;

/// 840. 矩阵中的幻方
/// 3 x 3 的幻方是一个填充有从 1 到 9 的不同数字的 3 x 3 矩阵，其中每行，每列以及两条对角线上的各数之和都相等。
/// 给定一个由整数组成的 grid，其中有多少个 3 × 3 的 “幻方” 子矩阵？（每个子矩阵都是连续的）。
impl Solution {
    pub fn num_magic_squares_inside(grid: Vec<Vec<i32>>) -> i32 {
        //因为不能重复，1~9的和即为子矩阵的和
        fn magic(vals: Vec<i32>) -> bool {
            let mut count = vec![0; 16];
            vals.iter().for_each(|x| count[*x as usize] += 1);
            for v in 1..=9 {
                //去掉重复
                if count[v] != 1 {
                    return false;
                }
            }
            return vals[0] + vals[1] + vals[2] == 15
                && vals[3] + vals[4] + vals[5] == 15
                && vals[6] + vals[7] + vals[8] == 15
                && vals[0] + vals[3] + vals[6] == 15
                && vals[1] + vals[4] + vals[7] == 15
                && vals[2] + vals[5] + vals[8] == 15
                && vals[0] + vals[4] + vals[8] == 15
                && vals[2] + vals[4] + vals[6] == 15;
        }

        if grid.len() < 3 || grid[0].len() < 3 {
            return 0;
        }
        let mut cnt = 0;
        for r in 0..grid.len() - 2 {
            for c in 0..grid[0].len() - 2 {
                if grid[r + 1][c + 1] != 5 {
                    continue;
                }
                if magic(vec![
                    grid[r][c],
                    grid[r][c + 1],
                    grid[r][c + 2],
                    grid[r + 1][c],
                    grid[r + 1][c + 1],
                    grid[r + 1][c + 2],
                    grid[r + 2][c],
                    grid[r + 2][c + 1],
                    grid[r + 2][c + 2],
                ]) {
                    cnt += 1;
                }
            }
        }
        cnt
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn num_magic_squares_inside() {
        let v1 = vec![vec![4, 3, 8, 4], vec![9, 5, 1, 9], vec![2, 7, 6, 2]];
        let ret = Solution::num_magic_squares_inside(v1);
        assert_eq!(ret, 1);
    }
}
