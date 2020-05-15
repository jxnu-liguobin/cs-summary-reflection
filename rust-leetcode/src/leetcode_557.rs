use crate::pre_structs::Solution;

///反转字符串中的单词 III
impl Solution {
    pub fn reverse_words(s: String) -> String {
        let arr: Vec<&str> = s.split(' ').collect();
        let ret: Vec<String> = arr
            .iter()
            .map(|word| -> String {
                let c: String = (*word).chars().rev().collect();
                c
            })
            .collect();
        ret.join(" ").to_string()
    }
}
