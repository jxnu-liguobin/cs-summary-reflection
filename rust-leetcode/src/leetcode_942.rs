use crate::pre_structs::Solution;

///增减字符串匹配
impl Solution {
    pub fn di_string_match(s: String) -> Vec<i32> {
        let n = s.len();
        //'D'代表着倒序，'I'代表升序，那么如果为'D'，只要取最大值max，同时最大值减一，作为下一个'D'的最大值；如果为'I'，则正好相反，取最小值min，同时加一，
        //作为下一个'I'的最小值；剩下最后一个值，max=min，取啥都行
        let mut arr = vec![0; n + 1];
        let mut max = n as i32;
        let mut min = 0;
        for i in 0..n {
            if s.get(i..=i).unwrap() == "D" {
                arr[i] = max;
                max -= 1;
            } else {
                arr[i] = min;
                min += 1;
            }
        }
        arr[n] = max;
        arr
    }
}
