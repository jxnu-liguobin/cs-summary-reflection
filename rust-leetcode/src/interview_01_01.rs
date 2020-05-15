use crate::pre_structs::Solution;

///判定字符是否唯一
impl Solution {
    pub fn is_unique(astr: String) -> bool {
        let cs = astr.chars();
        //题目没有说明，但这样AC了，假定只有大小写字母
        let mut count = vec![0; ('z' as i32 as usize) + 1]; //123
        for c in cs {
            count[(c as i32) as usize] += 1;
        }
        for &c in count.iter() {
            if c > 1 {
                return false;
            }
        }
        true
    }
    //位运算
    pub fn is_unique2(astr: String) -> bool {
        let cs = astr.chars();
        let mut mark = 0;
        let mut mark_bit = 0;
        for c in cs {
            mark_bit = c as i32 - ('a' as i32);
            if (mark & (1 << mark_bit)) != 0 {
                return false;
            } else {
                mark |= 1 << mark_bit
            }
        }
        true
    }
}
