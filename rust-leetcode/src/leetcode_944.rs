use std::ops::Index;

use crate::pre_structs::Solution;

///删列造序
impl Solution {
    //删除降序的，剩下非降序的
    //12ms
    pub fn min_deletion_size(a: Vec<String>) -> i32 {
        let mut ret = 0;
        let row = a.len();
        let col = a[0].len();
        for j in 0..col {
            for i in 0..row - 1 {
                if a[i].index(j..=j) > a[i + 1].index(j..=j) {
                    ret += 1;
                    break;
                }
            }
        }
        ret
    }
}
