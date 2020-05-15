use crate::pre_structs::Solution;

///最大数值
impl Solution {
    //不能使用if-else 比较运算符
    //max(a, b) = ((a + b) + abs(a - b)) / 2。
    pub fn maximum(a: i32, b: i32) -> i32 {
        let mut a = a as i64;
        let mut b = b as i64;
        b = a - b;
        a -= b & (b >> 32);
        a as i32
    }
}
