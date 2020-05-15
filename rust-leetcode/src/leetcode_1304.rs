use crate::pre_structs::Solution;

///和为零的N个唯一整数
impl Solution {
    //双指针
    pub fn sum_zero(n: i32) -> Vec<i32> {
        let mut ret = vec![0; n as usize];
        let mut i = 0usize;
        let mut j = (n - 1) as usize;
        let mut c = 1;
        loop {
            if i >= j {
                break;
            }
            ret[i] = c;
            ret[j] = -c;
            i += 1;
            j -= 1;
            c += 1;
        }
        ret
    }

    pub fn sum_zero2(n: i32) -> Vec<i32> {
        let mut ret = vec![0; n as usize];
        let mut sum = 0;
        let mut j = 0;
        for i in 0..=n - 2 {
            ret[j] = i;
            j += 1;
            sum += i;
        }
        ret[(n - 1) as usize] = -sum;
        ret
    }
}
