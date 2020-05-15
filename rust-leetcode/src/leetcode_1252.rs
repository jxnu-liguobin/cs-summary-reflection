use crate::pre_structs::Solution;

///奇数值单元格的数目
impl Solution {
    pub fn odd_cells(n: i32, m: i32, indices: Vec<Vec<i32>>) -> i32 {
        let mut arr = vec![vec![0; m as usize]; n as usize];
        let mut res = 0;
        for row in indices {
            for i in 0..n {
                arr[i as usize][row[1] as usize] += 1;
            }
            for j in 0..m {
                arr[row[0] as usize][j as usize] += 1;
            }
        }
        for i in 0..n {
            for j in 0..m {
                if arr[i as usize][j as usize] & 1 == 1 {
                    res += 1;
                }
            }
        }
        res
    }
}
