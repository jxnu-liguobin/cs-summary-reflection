use crate::pre_structs::Solution;

///打印从1到最大的n位数
impl Solution {
    //8ms
    pub fn print_numbers(n: i32) -> Vec<i32> {
        let mut max_num = String::new();
        for _i in 1..=n {
            max_num.push('9')
        }
        let mut ret = Vec::new();
        for i in 1..=max_num.parse::<i32>().unwrap() {
            ret.push(i);
        }
        ret
    }

    //8ms
    pub fn print_numbers2(n: i32) -> Vec<i32> {
        let mut ret = Vec::new();
        let x: i32 = 10;
        for i in 1..x.pow(n as u32) {
            ret.push(i);
        }
        ret
    }

    //20ms
    pub fn print_numbers3(n: i32) -> Vec<i32> {
        //快速幂
        fn pow(mut base: i32, mut index: i32) -> i32 {
            let mut ret = 1;
            while index > 0 {
                if index & 1 == 1 {
                    ret *= base;
                }
                index /= 2;
                base *= base;
            }
            ret
        }

        let mut ret = Vec::new();
        for i in 1..pow(10, n) {
            ret.push(i);
        }
        ret
    }
}
