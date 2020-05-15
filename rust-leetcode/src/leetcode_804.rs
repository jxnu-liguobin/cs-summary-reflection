use std::collections::HashSet;

use crate::pre_structs::Solution;

///唯一摩尔斯密码词
impl Solution {
    pub fn unique_morse_representations(words: Vec<String>) -> i32 {
        let m = vec![
            ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..",
            "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
            "-.--", "--..",
        ];
        let mut ret = HashSet::new();
        for w in words.iter() {
            let mut mw = String::new();
            for c in w.chars() {
                //bad smell
                let c = m[(c as u8 - 97) as usize];
                mw.push_str(c);
            }
            ret.insert(mw);
        }
        ret.len() as i32
    }
}
