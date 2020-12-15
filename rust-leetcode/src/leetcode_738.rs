use crate::pre_structs::Solution;

///738. 单调递增的数字
impl Solution {
    //给定一个非负整数 N，找出小于或等于 N 的最大的整数，同时这个整数需要满足其各个位数上的数字是单调递增。
    //（当且仅当每个相邻位数上的数字 x 和 y 满足 x <= y 时，我们称这个整数是单调递增的。）
    //0 ms,100.00%
    //1.9 MB,100.00%
    pub fn monotone_increasing_digits(n: i32) -> i32 {
        //从右向左遍历，当左边的高位大于右边的低位，则将高位-1，低位置为9
        let mut i = 1;
        let mut res = n;
        while i <= res / 10 {
            let n = res / i % 100;
            i *= 10;
            if n / 10 > n % 10 {
                //如1332 循环第一次变为1330-1=1329 第二次变为1300-1=1299
                res = res / i * i - 1;
            }
        }
        return res;
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::{Solution};

    #[test]
    fn monotone_increasing_digits() {
        let ret1 = Solution::monotone_increasing_digits(10);
        let ret2 = Solution::monotone_increasing_digits(1234);
        let ret3 = Solution::monotone_increasing_digits(332);
        assert_eq!(ret1, 9);
        assert_eq!(ret2, 1234);
        assert_eq!(ret3, 299);
    }
}
