use crate::pre_structs::Solution;

///转换成小写字母
impl Solution {
    pub fn to_lower_case(str: String) -> String {
        str.chars()
            .map(|c| {
                //说明是大写，+32
                if c < 'a' && c >= 'A' {
                    (c as u8 + 32 as u8) as char
                } else {
                    c
                }
            })
            .collect()
    }
}
