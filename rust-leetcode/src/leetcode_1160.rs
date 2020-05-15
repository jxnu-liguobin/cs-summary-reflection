use crate::pre_structs::Solution;

///拼写单词
impl Solution {
    pub fn count_characters(words: Vec<String>, chars: String) -> i32 {
        let mut char_count = vec![0; 'z' as usize + 1];
        for c in chars.chars() {
            char_count[c as usize] += 1;
        }
        let mut ret = 0;
        for word in words.iter() {
            let mut word_count = vec![0; 'z' as usize + 1];
            for c in word.chars() {
                word_count[c as usize] += 1;
            }
            let mut flg = true;
            if word
                .chars()
                .map(|tc| -> bool { char_count[tc as usize] < word_count[tc as usize] })
                .find(|&x| x == true)
                .is_some()
            {
                flg = false;
            }
            if flg {
                ret += word.len()
            }
        }
        ret as i32
    }
}
