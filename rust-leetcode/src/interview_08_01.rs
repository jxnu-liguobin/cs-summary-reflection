use crate::pre_structs::Solution;

/// 面试题 08.01. 三步问题
/// 三步问题。有个小孩正在上楼梯，楼梯有n阶台阶，小孩一次可以上1阶、2阶或3阶。实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模1000000007。
impl Solution {
    //对于最后一步有三种可能，相加即可F(n-1)+F(n-2)+F(n-3)
    //递归 n=61超时
    pub fn ways_to_step(n: i32) -> i32 {
        if n == 1 {
            return 1;
        }
        if n == 2 {
            return 2;
        }
        if n == 3 {
            return 4;
        }
        return {
            let mut ret = Solution::ways_to_step(n - 1)
                + Solution::ways_to_step(n - 2)
                + Solution::ways_to_step(n - 3);
            if ret > 1000000007 {
                ret -= 1000000007;
            } else {
                ret;
            }
            ret
        };
    }

    //8MS  80%,9.1MB 100%
    pub fn ways_to_step_(n: i32) -> i32 {
        if n == 1 {
            return 1;
        }
        if n == 2 {
            return 2;
        }
        if n == 3 {
            return 4;
        }
        let mut dp = vec![0i64; (n + 1) as usize];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;
        for i in 4usize..=n as usize {
            let mut t = dp[i - 1] + dp[i - 2] + dp[i - 3];
            if t > 1000000007 {
                t %= 1000000007;
            } else {
                t;
            }
            dp[i] = t;
        }
        (dp[n as usize]) as i32
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn ways_to_step() {
        let ret = Solution::ways_to_step(5);
        println!("{}", ret);
        let ret2 = Solution::ways_to_step_(61);
        println!("{}", ret2);
        let ret3 = Solution::ways_to_step_(5);
        assert_eq!(ret, 13);
        assert_eq!(ret2, 752119970);
        assert_eq!(ret3, 13);
    }
}
