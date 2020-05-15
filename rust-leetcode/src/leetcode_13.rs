use std::collections::HashMap;
use std::ops::Index;

use crate::pre_structs::Solution;

///罗马数字转整数
impl Solution {
    pub fn roman_to_int(s: String) -> i32 {
        let mut maps = HashMap::new();
        let chs = vec!["I", "V", "X", "L", "C", "D", "M"];
        let n = vec![1, 5, 10, 50, 100, 500, 1000];
        let mut ret = 0;
        let right = s.len() - 1;
        n.iter().enumerate().for_each(|(x, &y)| {
            maps.insert(chs[x], y);
        });
        for i in 0..right {
            //字不是顺序的，但是数字列表是顺序的
            if maps[s.index(i..=i)] >= maps[s.index(i + 1..=i + 1)] {
                ret += maps[s.index(i..=i)];
            } else {
                ret -= maps[s.index(i..=i)];
            }
        }
        ret += maps[s.index(right..=right)];
        ret
    }

    //Legend Lee
    pub fn roman_to_int2(s: String) -> i32 {
        fn roman_to_int_char(c: char) -> Option<i32> {
            match c {
                'I' => Some(1),
                'V' => Some(5),
                'X' => Some(10),
                'L' => Some(50),
                'C' => Some(100),
                'D' => Some(500),
                'M' => Some(1000),
                _ => None,
            }
        }

        let mut v = 0i32;
        if s.is_empty() {
            return 0;
        }

        s.chars()
            .zip(s.chars().skip(1))
            .for_each(|(first, second)| {
                let a = roman_to_int_char(first).unwrap();
                let b = roman_to_int_char(second).unwrap();
                v += (if a < b { -1 * a } else { a });
            });
        v += roman_to_int_char(s.chars().last().unwrap()).unwrap();

        v
    }

    //闲杂织造
    pub fn roman_to_int3(s: String) -> i32 {
        let tr = vec![
            ('I', 1),
            ('V', 5),
            ('X', 10),
            ('L', 50),
            ('C', 100),
            ('D', 500),
            ('M', 1000),
        ];
        let mut hash: HashMap<char, i32> = tr.iter().cloned().collect();
        let chars: Vec<char> = s.chars().collect();
        let mut ans = 0;
        let n = chars.len();
        for i in 0..n - 1 {
            let cc = hash[&chars[i]];
            let nc = hash[&chars[i + 1]];
            if cc < nc {
                ans -= cc;
            } else {
                ans += cc;
            }
        }
        ans + hash[&chars[n - 1]]
    }
}
