use crate::pre_structs::Solution;

///整数的各位积和之差
impl Solution {
    pub fn subtract_product_and_sum(n: i32) -> i32 {
        let mut num = n;
        let mut muti = 1;
        let mut sum = 0;
        while num != 0 {
            let mut tmp = num % 10;
            muti *= tmp;
            sum += tmp;
            num /= 10;
        }
        muti - sum
    }
}
