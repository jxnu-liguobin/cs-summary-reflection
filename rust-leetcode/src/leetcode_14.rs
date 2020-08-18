use crate::pre_structs::Solution;

///最长公共前缀
impl Solution {
    //0 ms, 2.1 MB
    pub fn longest_common_prefix(strs: Vec<String>) -> String {
        //选出最小的字符串w，使用w的所有子串去匹配strs中的单词
        let mut min_word: &String = &"".to_owned();
        let mut min_length = usize::max_value();
        if strs.is_empty() {
            return min_word.clone();
        }
        let mut result: &str = "";
        strs.iter().for_each(|word| {
            if min_length > word.len() {
                min_length = word.len();
                min_word = word;
            }
        });
        while min_length > 0 {
            let sub_word = &min_word[0..min_length];
            let mut is_max = true;
            for w in strs.iter() {
                if w.starts_with(sub_word) == false {
                    is_max = false;
                    break;
                }
            }
            if is_max {
                result = sub_word;
                break;
            }
            min_length -= 1;
        }
        result.to_owned()
    }

    //0 ms, 2.1 MB
    pub fn longest_common_prefix2(strs: Vec<String>) -> String {
        let strs = &strs;
        let mut result: String = "".to_owned();
        if strs.is_empty() {
            return result;
        }
        //选取第一个单词w，对w的长度从大到小进行切片，将切片与所有单词进行匹配
        result = strs[0].clone();
        for (_index, word) in strs.iter().enumerate() {
            while !word.starts_with(result.as_str()) {
                result = result[0..result.len() - 1].to_owned();
                if result.len() == 0 {
                    return "".to_owned();
                }
            }
        }
        result
    }

    //4 ms, 2.1 MB
    pub fn longest_common_prefix3(strs: Vec<String>) -> String {
        let strs = &strs;
        let result: String = "".to_owned();
        if strs.is_empty() {
            return result;
        }
        //选取第一个单词w，与其他字符串依次比较对应位置上的字符
        let word = &strs[0].clone();
        let init_word: Vec<char> = word.chars().collect();
        for i in 0..init_word.len() {
            let c: char = init_word[i];
            for j in 1..strs.len() {
                let cs: Vec<char> = strs[j].chars().collect();
                if i < cs.len() && c != cs[i] || i == cs.len() {
                    return word[0..i].to_owned();
                }
            }
        }
        word.to_owned()
    }
}
