use crate::pre_structs::Solution;

///有效的括号
impl Solution {
    pub fn is_valid(s: String) -> bool {
        let chars: Vec<char> = s.chars().collect();
        let mut stack = Vec::<char>::new();
        for &c in chars.iter() {
            if c == '{' || c == '[' || c == '(' {
                stack.push(c);
            } else {
                if stack.is_empty() {
                    return false;
                }
                let c_stack = stack.pop();
                if let Some(cStack) = c_stack {
                    if c == ')' && cStack != '('
                        || c == ']' && cStack != '['
                        || c == '}' && cStack != '{'
                    {
                        return false;
                    }
                }
            }
        }
        stack.is_empty()
    }
}
