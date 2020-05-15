use crate::pre_structs::Solution;

///分割平衡字符串
impl Solution {
    pub fn balanced_string_split(s: String) -> i32 {
        let mut l = 0;
        let mut ret = 0;
        for c in s.chars() {
            if c == 'L' {
                l += 1;
            }
            if c == 'R' {
                l -= 1;
            }
            if l == 0 {
                ret += 1;
            }
        }
        ret
    }
    //函数式
    pub fn balanced_string_split2(s: String) -> i32 {
        s.chars()
            .scan(0, |acc, e| {
                *acc = if let 'R' = e { (*acc + 1) } else { (*acc - 1) };
                Some(*acc)
            })
            .filter(|&e| e == 0)
            .count() as i32
    }
}
