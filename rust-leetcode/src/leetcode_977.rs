use crate::pre_structs::Solution;

///有序数组的平方
impl Solution {
    //172ms,2.1MB
    pub fn sorted_squares(a: Vec<i32>) -> Vec<i32> {
        let mut a = a;
        a[0] = a[0] * a[0];
        for i in 1..a.len() {
            let tmp = a[i] * a[i];
            let mut j = i;
            while j > 0 && a[j - 1] > tmp {
                a[j] = a[j - 1];
                j -= 1
            }
            a[j] = tmp;
        }
        a
    }

    //12ms,2.2MB
    pub fn sorted_squares2(a: Vec<i32>) -> Vec<i32> {
        let mut ret = a;
        for (_i, n) in ret.iter_mut().enumerate() {
            *n = *n * *n;
        }
        ret.sort();
        ret
    }
}
