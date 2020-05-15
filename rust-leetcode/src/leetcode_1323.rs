use crate::pre_structs::Solution;

///6 和 9 组成的最大数字
impl Solution {
    pub fn maximum69_number(num: i32) -> i32 {
        num.to_string().replacen('6', "9", 1).parse().unwrap()
    }
}
