use crate::pre_structs::Solution;

///Nim 游戏
impl Solution {
    pub fn can_win_nim(n: i32) -> bool {
        if n % 4 == 0 {
            false
        } else {
            true
        }
    }
}
