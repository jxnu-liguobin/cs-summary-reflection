use crate::pre_structs::Solution;

///替换空格
impl Solution {
    pub fn replace_space(s: String) -> String {
        let mut str = s;
        str.replace(" ", "%20")
    }
}
