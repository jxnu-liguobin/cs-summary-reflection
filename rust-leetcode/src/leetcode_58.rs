use crate::pre_structs::Solution;

///最后一个单词的长度
impl Solution {
    pub fn length_of_last_word(s: String) -> i32 {
        let chars: Vec<&str> = s.trim().split(' ').collect();
        chars[chars.len() - 1].len() as i32
    }
}

#[cfg(test)]
mod test {

    #[test]
    fn length_of_last_word() {}
}
