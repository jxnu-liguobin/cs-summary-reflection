use std::cmp::{max, min};

use crate::pre_structs::Solution;

///475. 供暖器
impl Solution {
    //8ms 2.3MB
    pub fn find_radius(houses: Vec<i32>, heaters: Vec<i32>) -> i32 {
        let mut heaters = heaters;
        heaters.sort();
        let mut res = 0i32;
        for h in houses.iter() {
            match heaters.binary_search(h) {
                Err(should_insert_index) => {
                    let left_dist = if should_insert_index > 0 {
                        h - heaters[should_insert_index - 1]
                    } else {
                        i32::max_value()
                    };
                    let right_dist = if should_insert_index <= heaters.len() - 1 {
                        heaters[should_insert_index] - h
                    } else {
                        i32::max_value()
                    };
                    res = max(res, min(left_dist, right_dist));
                }
                _ => {}
            }
        }
        res
    }

    //双指针 4ms 2.4MB
    pub fn find_radius2(houses: Vec<i32>, heaters: Vec<i32>) -> i32 {
        let mut houses = houses;
        let mut heaters = heaters;
        houses.sort();
        heaters.sort();
        let mut r = 0;
        let mut i = 0usize;
        for &h in houses.iter() {
            while i < heaters.len() && h > heaters[i] {
                i += 1;
            }
            if i == 0 {
                r = max(r, heaters[i] - h);
            } else if i == heaters.len() {
                return max(r, houses[houses.len() - 1] - heaters[heaters.len() - 1]);
            } else {
                r = max(r, min(heaters[i] - h, h - heaters[i - 1]));
            }
        }
        r
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn find_radius() {
        let ret = Solution::find_radius([1, 2, 3, 4].to_vec(), [1, 4].to_vec());
        assert!(ret == 1);

        let ret = Solution::find_radius2([1, 2, 3, 4].to_vec(), [1, 4].to_vec());
        assert!(ret == 1);
    }
}
