use std::collections::HashSet;

use crate::pre_structs::Solution;

///新代码使用rust自带的单测
///859. 亲密字符串
impl Solution {
    pub fn buddy_strings(a: String, b: String) -> bool {
        let mut vect: Vec<usize> = Vec::new();
        if a.len() != b.len() {
            return false;
        }
        let mut tmp = HashSet::new();
        if a == b {
            let cs: Vec<char> = a.chars().collect();
            cs.iter().for_each(|c| {
                tmp.insert(c);
            });

            //有重复元素
            if tmp.len() < a.len() {
                return true;
            } else {
                return false;
            }
        }

        for i in 0..a.len() {
            //不同的字符的下标进数组
            if a[i..=i] != b[i..=i] {
                vect.push(i);
            }
        }
        //不同的字符的下标数超过两个
        if vect.len() != 2 {
            return false;
        }

        if (a[vect[0]..=vect[0]] != b[vect[1]..=vect[1]])
            || (a[vect[1]..=vect[1]] != b[vect[0]..=vect[0]])
        {
            return false;
        }

        return true;
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn buddy_strings() {
        assert!(Solution::buddy_strings("ab".to_owned(), "ba".to_owned()));
        assert!(!Solution::buddy_strings("ab".to_owned(), "ab".to_owned()));
    }
}
