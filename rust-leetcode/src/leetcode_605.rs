use crate::pre_structs::Solution;

///605. 种花问题
impl Solution {
    pub fn can_place_flowers(flowerbed: Vec<i32>, n: i32) -> bool {
        let mut flowerbed = flowerbed;
        let len = flowerbed.len();
        let mut ret = 0;
        //记录前后元素，均为0时，表示可以种植的坑位，只要可种植坑位数大于等于n即可
        let mut pre = 0i32;
        let mut next = 0i32;
        for l in 0..len {
            if flowerbed[l] == 1 {
                continue;
            }
            if l > 0 {
                pre = flowerbed[l - 1];
            }
            if l < len - 1 {
                next = flowerbed[l + 1]
            }

            if pre == 0 && next == 0 {
                ret += 1;
                flowerbed[l] = 1;
            }
        }
        ret >= n
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn can_place_flowers() {
        let ret = Solution::can_place_flowers([1, 0, 0, 0, 1].to_vec(), 1);
        assert!(ret);

        let ret = Solution::can_place_flowers([1, 0, 0, 0, 1].to_vec(), 2);
        assert!(!ret);
    }
}
