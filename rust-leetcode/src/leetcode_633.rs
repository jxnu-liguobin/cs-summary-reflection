use crate::pre_structs::Solution;

///633. 平方数之和
impl Solution {
    //费马平方和定理：取决于p是4n+1(有一组解)还是4n+3(无整数解)的素数「题目没有要求奇质数」
    //奇质数能表示为两个平方数之和的充分必要条件是该质数被４除余１
    //一个非负整数 c 能够表示为两个整数的平方和，当且仅当 c 的所有形如 4k+3 的质因子的幂次均为偶数。
    pub fn judge_square_sum(c: i32) -> bool {
        //2.1MB, 0MS
        let mut c = c;
        let mut i = 2;
        while i * i < c {
            let mut cnt = 0;
            if c % i == 0 {
                while c % i == 0 {
                    cnt += 1;
                    c /= i
                }
                if cnt & 1 == 1 && i % 4 == 3 {
                    return false;
                }
            }
            i += 1;
        }
        c % 4 != 3
    }

    //1.9MB, 0MS
    pub fn judge_square_sum2(c: i32) -> bool {
        if c <= 1 {
            return true;
        }
        for i in 0..=f32::sqrt(c as f32) as i32 {
            let a = f32::sqrt((c - i * i) as f32) as i32;
            if a * a + i * i == c {
                return true;
            }
        }
        false
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn judge_square_sum() {
        let ret = Solution::judge_square_sum(6);
        let ret2 = Solution::judge_square_sum2(6);
        assert!(ret == ret2);
    }
}
