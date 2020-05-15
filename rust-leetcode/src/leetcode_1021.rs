use std::collections::VecDeque;

use crate::pre_structs::Solution;

///删除最外层的括号
impl Solution {
    pub fn remove_outer_parentheses(s: String) -> String {
        let mut ret_str = String::new();
        let mut le = 0;
        for c in s.chars() {
            if c == ')' {
                le -= 1
            }
            if le >= 1 {
                ret_str.push(c)
            }
            if c == '(' {
                le += 1
            }
        }
        ret_str
    }

    pub fn remove_outer_parentheses2(s: String) -> String {
        let mut stack = VecDeque::new();
        let mut ret_str = String::new();
        for c in s.chars() {
            //括号匹配，忽略最左边和最右边的括号
            if c == '(' {
                stack.push_back(c);
                if stack.len() > 1 {
                    ret_str.push(c);
                }
            } else {
                stack.pop_back();
                if stack.len() != 0 {
                    ret_str.push(c);
                }
            }
        }
        ret_str
    }
}
