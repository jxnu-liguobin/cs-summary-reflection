use crate::pre_structs::Solution;

///加一
impl Solution {
    pub fn plus_one(digits: Vec<i32>) -> Vec<i32> {
        let mut dig = digits;
        let mut flag = false;
        for i in (0..dig.len()).rev() {
            dig[i] = match dig[i] {
                _ if flag || i == dig.len() - 1 => {
                    if dig[i] == 9 {
                        flag = true;
                        0
                    } else {
                        flag = false;
                        dig[i] + 1
                    }
                }
                _ => dig[i],
            }
        }
        //处理最高位
        if flag {
            let mut ret = vec![0; dig.len() + 1];
            ret[0] = 1;
            ret
        } else {
            dig
        }
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn plus_one() {
        let ret1 = Solution::plus_one(vec![8, 8, 9]);
        let ret2 = Solution::plus_one(vec![9, 8, 9]);
        let ret3 = Solution::plus_one(vec![9, 9, 9]);
        let ret4 = Solution::plus_one(vec![9, 9, 0]);
        assert!(ret1 == vec![8, 9, 0]);
        assert!(ret2 == vec![9, 9, 0]);
        assert!(ret3 == vec![1, 0, 0, 0]);
        assert!(ret4 == vec![9, 9, 1]);
    }
}
