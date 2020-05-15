use std::collections::{HashMap, HashSet};

use crate::pre_structs::Solution;

///键盘行
impl Solution {
    pub fn find_words(words: Vec<String>) -> Vec<String> {
        let map: HashMap<char, i32> = vec![
            ('Q', 0),
            ('W', 0),
            ('E', 0),
            ('R', 0),
            ('T', 0),
            ('Y', 0),
            ('U', 0),
            ('I', 0),
            ('O', 0),
            ('P', 0),
            ('A', 1),
            ('S', 1),
            ('D', 1),
            ('F', 1),
            ('G', 1),
            ('H', 1),
            ('J', 1),
            ('K', 1),
            ('L', 1),
            ('Z', 2),
            ('X', 2),
            ('C', 2),
            ('V', 2),
            ('B', 2),
            ('N', 2),
            ('M', 2),
        ]
        .iter()
        .cloned()
        .collect();
        words
            .iter()
            .filter(|word| {
                let chars: Vec<char> = word.chars().collect();
                let index: HashSet<i32> = chars
                    .iter()
                    .map(|c| -> i32 { map[&c.to_ascii_uppercase()] })
                    .collect();
                index.len() == 1
            })
            .cloned()
            .collect()
    }
}
