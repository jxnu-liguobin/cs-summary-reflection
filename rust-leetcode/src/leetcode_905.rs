use crate::pre_structs::Solution;

///按奇偶排序数组
impl Solution {
    pub fn sort_array_by_parity(a: Vec<i32>) -> Vec<i32> {
        let (mut even, mut odd): (Vec<i32>, Vec<i32>) = a.iter().partition(|&n| n % 2 == 0);
        even.append(&mut odd);
        even
    }
}

#[cfg(test)]
mod test {

    #[test]
    fn sort_array_by_parity() {}
}
