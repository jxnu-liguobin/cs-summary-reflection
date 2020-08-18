use crate::pre_structs::Solution;

///面试题10- I. 斐波那契数列
impl Solution {
    //2.1MB 0MS
    pub fn fib(n: i32) -> i32 {
        if n < 2 {
            return n;
        }
        if n == 2 {
            return 1;
        }
        let mut i = 0;
        let mut j = 1;
        let mut cur = 1;
        for _m in 2..=n {
            cur = i + j;
            if cur >= 1000000007 {
                cur -= 1000000007
            }
            i = j;
            j = cur;
        }
        cur
    }

    //超时
    pub fn fib2(n: i32) -> i32 {
        let ret = if n < 2 {
            n
        } else {
            let mut cur = Solution::fib2(n - 1) + Solution::fib2(n - 2);
            if cur >= 1000000007 {
                cur -= 1000000007
            }
            cur
        };
        ret
    }

    //2.1MB 0MS
    pub fn fib3(n: i32) -> i32 {
        if n < 2 {
            return n;
        }
        let mut i = 0;
        let mut j = 1;
        for _m in 1..n {
            //向后移动i j时，利用 j=i+j,i=(i+j)-i,少个变量
            j = i + j;
            i = j - i;
            if j >= 1000000007 {
                j -= 1000000007
            }
        }
        j
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn buddy_strings() {
        let ret = Solution::fib(5);
        let ret2 = Solution::fib2(5);
        let ret3 = Solution::fib3(5);
        assert!(ret == ret2 && ret == ret3);
    }
}
