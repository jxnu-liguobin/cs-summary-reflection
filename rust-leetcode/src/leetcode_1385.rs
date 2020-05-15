use crate::pre_structs::Solution;

///两个数组间的距离值
impl Solution {
    //暴力解
    pub fn find_the_distance_value(arr1: Vec<i32>, arr2: Vec<i32>, d: i32) -> i32 {
        let mut c = 0;
        let ret = arr1.iter().for_each(|&x| {
            let mut flag = false;
            arr2.iter().for_each(|&y| {
                if !flag && (x - y).abs() > d {
                } else {
                    flag = true;
                }
            });
            if !flag {
                c += 1;
            }
        });
        c
    }
}
