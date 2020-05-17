use crate::pre_structs::Solution;

///67. 二进制求和
impl Solution {
    //逐位相加参考66题，这里懒得写
    pub fn add_binary(a: String, b: String) -> String {
        let a: i128 = i128::from_str_radix(a.as_str(), 2).ok().unwrap();
        let b: i128 = i128::from_str_radix(b.as_str(), 2).ok().unwrap();
        format!("{:b}", a + b)
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn add_binary() {
        let ret = Solution::add_binary("10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101".to_owned(),
                                       "110101001011101110001111100110001010100001101011101010000011011011001011101111001100000011011110011".to_owned());
        assert!(ret.as_str() == "110111101100010011000101110110100000011101000101011001000011011000001100011110011010010011000000000")
    }
}
