use crate::pre_structs::Solution;

///汉明距离
impl Solution {
    pub fn hamming_distance(x: i32, y: i32) -> i32 {
        let mut nums = x ^ y;
        //二进制中1的个数
        let mut c = 0;
        while nums != 0 {
            if nums & 1 == 1 {
                c += 1;
            }
            nums = nums >> 1;
        }
        c
    }
}
