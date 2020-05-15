use crate::pre_structs::Solution;

///可以被一步捕获的棋子数
impl Solution {
    pub fn num_rook_captures(board: Vec<Vec<char>>) -> i32 {
        fn burnot(board: &Vec<Vec<char>>, x: i32, y: i32, direction: &Vec<i32>) -> bool {
            let mut i = x;
            let mut j = y;
            let closures = move |i: i32, j: i32| -> bool { i >= 0 && i < 8 && j >= 0 && j < 8 };
            while closures(i, j) {
                //路被堵死
                if board[i as usize][j as usize] == 'B' {
                    break;
                }
                //是敌军
                if board[i as usize][j as usize] == 'p' {
                    return true;
                }
                i = i + direction[0];
                j = j + direction[1];
            }
            return false;
        }

        //定义方向数组，可以认为是四个方向向量，在棋盘问题上是常见的做法
        let directions = vec![vec![-1, 0], vec![1, 0], vec![0, 1], vec![0, -1]];
        for x in 0..8 {
            for y in 0..8 {
                if board[x][y] == 'R' {
                    let mut res = 0;
                    for direction in directions.iter() {
                        if burnot(&board, x as i32, y as i32, direction) {
                            res += 1;
                        }
                    }
                    return res;
                }
            }
        }

        return 0;
    }
}
