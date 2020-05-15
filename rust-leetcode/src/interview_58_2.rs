use crate::pre_structs::Solution;

///左旋转字符串
impl Solution {
    pub fn reverse_left_words(s: String, n: i32) -> String {
        let mut s1 = String::from(&s[0..n as usize]);
        let s2 = &s[n as usize..s.len()];
        s1.insert_str(0, s2);
        s1.to_owned()
    }
}
