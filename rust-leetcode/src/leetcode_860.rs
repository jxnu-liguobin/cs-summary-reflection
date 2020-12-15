use std::collections::HashMap;

use crate::pre_structs::Solution;

/// 860. 柠檬水找零
impl Solution {
    //在柠檬水摊上，每一杯柠檬水的售价为 5 美元。
    //顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
    //每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
    //注意，一开始你手头没有任何零钱。
    //如果你能给每位顾客正确找零，返回 true ，否则返回 false 。
    //0 ms,100.00%
    //2.1 MB,10.00%
    pub fn lemonade_change(bills: Vec<i32>) -> bool {
        let end = bills.len();
        let mut start = 0;
        let mut sum = 5;
        let mut tempfive = 0;
        let mut tempten = 0;
        while start < end {
            if sum < bills[start] {
                return false;
            }
            if bills[start] == 5 {
                tempfive += 1;
            } else if bills[start] == 10 {
                tempten += 1;
                tempfive -= 1;
            } else {
                if tempten > 0 {
                    tempten -= 1;
                    tempfive -= 1;
                } else {
                    tempfive -= 3;
                }
            }
            if tempfive < 0 {
                return false;
            }
            start += 1;
            sum += 5;
        }
        return true;
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn lemonade_change() {
        let ret = Solution::lemonade_change(vec![5, 5, 5, 10, 20].to_owned());
        let ret1 = Solution::lemonade_change(vec![5, 5, 10].to_owned());
        let ret2 = Solution::lemonade_change(vec![5, 5, 5, 10, 5, 20, 5, 10, 5, 20].to_owned());
        assert!(ret && ret1 & ret2);
    }
}
