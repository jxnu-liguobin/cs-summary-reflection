use crate::pre_structs::Solution;

///204. 计数质数
impl Solution {
    //772MS,2.1MB
    pub fn count_primes(n: i32) -> i32 {
        fn is_primes(num: i32) -> bool {
            let _cnt = 0;
            if num < 2 {
                return false;
            }
            if num == 2 {
                return true;
            }
            for i in 2..=f32::sqrt(num as f32) as i32 {
                if num % i == 0 {
                    return false;
                }
            }
            true
        }
        let mut cnt = 0;
        for i in 0..n {
            if is_primes(i) {
                cnt += 1;
            }
        }
        cnt
    }

    //排除法 埃拉托色尼筛选法
    //12MS,3.4MB
    pub fn count_primes2(n: i32) -> i32 {
        if n < 2 {
            return 0;
        }
        let mut bit_set = vec![true; n as usize];
        let mut i = 2;
        while i * i < n {
            if bit_set[i as usize] {
                let mut j = i * i;
                while j < n {
                    bit_set[j as usize] = false;
                    j += i;
                }
            }
            i += 1;
        }
        let cnt = bit_set
            .iter()
            .enumerate()
            .filter(|(i, &x)| i >= &2 && i < &(n as usize) && x == true)
            .count();
        cnt as i32
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn count_primes() {
        let ret = Solution::count_primes(10);
        let ret2 = Solution::count_primes2(10);
        assert!(ret == ret2);
    }
}
