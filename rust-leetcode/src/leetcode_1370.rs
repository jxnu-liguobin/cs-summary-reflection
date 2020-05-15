use crate::pre_structs::Solution;

///上升下降字符串
impl Solution {
    pub fn sort_string(s: String) -> String {
        let mut ret = String::new();
        let mut v = vec![0; 26];
        for c in s.chars() {
            v[c as usize - 97] += 1;
        }
        while ret.len() < s.len() {
            for n in 0..26u8 {
                if v[n as usize] > 0 {
                    ret.push((n + 97) as char);
                    v[n as usize] -= 1;
                }
            }
            for n in (0..=25u8).rev() {
                if v[n as usize] > 0 {
                    ret.push((n + 97) as char);
                    v[n as usize] -= 1;
                }
            }
        }
        ret
    }
}
