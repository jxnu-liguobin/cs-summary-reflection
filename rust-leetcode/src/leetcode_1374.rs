use crate::pre_structs::Solution;

///生成每种字符都是奇数个的字符串
impl Solution {
    //偷鸡
    pub fn generate_the_string(n: i32) -> String {
        let mut ret = vec![];
        if n & 1 == 0 {
            ret = vec!['a'; (n - 1) as usize];
            ret.push('b');
        } else {
            ret = vec!['a'; n as usize];
        }
        let mut rs = String::new();
        ret.iter().for_each(|&c| rs.push(c));
        rs
    }
}
