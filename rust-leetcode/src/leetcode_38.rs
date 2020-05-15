use crate::pre_structs::Solution;

///外观数列
impl Solution {
    //给一个数，这个数是1
    //描述上一步的数，这个数是1即一个1，故写作11
    //描述上一步的数，这个数是11即两个1，故写作21
    //描述上一步的数，这个数是21即一个2一个1，故写作12-11
    //描述上一步的数，这个数是1211即一个1一个2两个1，故写作11-12-21
    pub fn count_and_say(n: i32) -> String {
        if n == 1 {
            return "1".to_owned();
        }
        let pre_str = Solution::count_and_say(n - 1);
        let mut curr_str = String::new();
        let mut pre_char = None;
        let mut pre_char_count = 0;
        //当n为2及以上时。因为下一个数列是对上面的解释。所以用三个变量，一个代表数量count ,一个代表前一个数字pre，一个代表后一个数字back
        for back_char in pre_str.chars() {
            if pre_char == None {
                pre_char = Some(back_char);
            }
            if back_char != pre_char.unwrap() {
                //将pre_char_count个pre_char分别存储
                curr_str.push_str(&pre_char_count.to_string());
                curr_str.push(pre_char.unwrap());
                pre_char = Some(back_char);
                //不同时，重置个数
                pre_char_count = 1;
            } else {
                //相等时计算字符个数
                pre_char_count += 1;
            }
        }
        //最后一位在前面跳出来了，需要追加
        if pre_char_count > 0 {
            curr_str.push_str(&pre_char_count.to_string());
            curr_str.push(pre_char.unwrap());
        }
        curr_str
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn count_and_say() {}
}
