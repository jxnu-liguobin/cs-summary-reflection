use crate::pre_structs::Solution;

///自除数
impl Solution {
    pub fn self_dividing_numbers(left: i32, right: i32) -> Vec<i32> {
        let mut result = Vec::new();
        for num in left..=right {
            if helper(num) {
                result.push(num)
            }
        }

        fn helper(n: i32) -> bool {
            for c in n.to_string().chars() {
                if ((c as i32) - 48) == 0 || n % ((c as i32) - 48) != 0 {
                    return false;
                }
            }
            return true;
        }

        result
    }
}
