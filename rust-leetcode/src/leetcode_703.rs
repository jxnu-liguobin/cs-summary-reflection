/// 703. 数据流中的第 K 大元素
// 352 ms,6.25%
// 7.1 MB,75.00%
struct KthLargest {
    k: usize,
    data: Vec<i32>,
}

/**
 * `&self` means the method takes an immutable reference.
 * If you need a mutable reference, change it to `&mut self` instead.
 */
impl KthLargest {
    fn new(k: i32, nums: Vec<i32>) -> Self {
        let mut nums = nums;
        nums.sort();
        KthLargest {
            k: k as usize,
            data: nums,
        }
    }

    fn add(&mut self, val: i32) -> i32 {
        self.data.push(val);
        self.data.sort();
        self.data[self.data.len() - self.k]
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * let obj = KthLargest::new(k, nums);
 * let ret_1: i32 = obj.add(val);
 */
#[cfg(test)]
mod test {
    use crate::leetcode_703::KthLargest;

    #[test]
    fn KthLargest() {
        let mut obj = KthLargest::new(3, vec![4, 5, 8, 2].to_owned());
        let ret_1: i32 = obj.add(3);
        let ret_2: i32 = obj.add(5);
        let ret_3: i32 = obj.add(10);
        assert_eq!(ret_1, 4);
        assert_eq!(ret_2, 5);
        assert_eq!(ret_3, 5);
    }
}
