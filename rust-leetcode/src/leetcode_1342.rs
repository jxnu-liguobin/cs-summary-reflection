use crate::pre_structs::Solution;

///将数字变成 0 的操作次数
impl Solution {
    pub fn number_of_steps(num: i32) -> i32 {
        let mut n = num;
        let mut i = 0;
        while n != 0 {
            if n & 1 == 0 {
                n /= 2;
            } else {
                n -= 1;
            }
            i += 1;
        }
        i
    }
}
