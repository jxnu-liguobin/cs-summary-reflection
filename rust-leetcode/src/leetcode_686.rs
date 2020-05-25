use crate::pre_structs::Solution;

///686. 重复叠加字符串匹配
impl Solution {
    //超时！！
    pub fn repeated_string_match(a: String, b: String) -> i32 {
        let tmp_a = a.clone();
        let tmp_a = tmp_a.as_str();
        let mut mut_a = a.clone();
        let b = b.as_str();
        //a.len>b.len 2
        //a.len<b.len b/a+a
        return if mut_a.len() > b.len() {
            if mut_a.contains(b) {
                1
            } else {
                mut_a.push_str(tmp_a);
                if mut_a.contains(b) {
                    2
                } else {
                    -1
                }
            }
        } else {
            let mut m = (b.len() / mut_a.len()) as i32;
            let mut count = 1;
            while m > 0 {
                m -= 1;
                if mut_a.contains(b) {
                    return count;
                } else {
                    mut_a.push_str(tmp_a);
                    count += 1;
                    if mut_a.contains(b) {
                        return count;
                    }
                }
            }
            if !mut_a.contains(b) {
                mut_a.push_str(tmp_a);
                if mut_a.contains(b) {
                    count += 1;
                } else {
                    count = -1
                }
            }
            count
        };
    }

    //784MS,2.1MB
    pub fn repeated_string_match2(a: String, b: String) -> i32 {
        let mut cnt = 0;
        let tmp_a = a.clone();
        let b = b.as_str();
        let tmp_a = tmp_a.as_str();
        let mut s = String::new();
        while s.len() <= 2 * a.len() + b.len() {
            if !s.contains(b) {
                cnt += 1;
            } else {
                return cnt;
            }
            s.push_str(tmp_a)
        }
        -1
    }
    //120MS,2.2MB
    pub fn repeated_string_match3(a: String, b: String) -> i32 {
        let a: Vec<char> = a.chars().collect();
        let b: Vec<char> = b.chars().collect();
        for i in 0..a.len() {
            if a[i] == b[0] {
                let mut k = i;
                let mut j = 0;
                let mut count = 1;
                while a[k] == b[j] {
                    k += 1;
                    j += 1;
                    //a长b短
                    if j >= b.len() {
                        return count;
                    }
                    //a短b长,重复使用a
                    if k >= a.len() {
                        k = 0;
                        count += 1;
                    }
                }
            }
        }
        -1
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn repeated_string_match() {
        let ret = Solution::repeated_string_match("abaabaa".to_owned(), "abaababaab".to_owned());
        let ret2 = Solution::repeated_string_match("a".to_owned(), "aa".to_owned());
        let ret3 = Solution::repeated_string_match("ab".to_owned(), "ba".to_owned());
        let ret4 = Solution::repeated_string_match3("a".to_owned(), "a".to_owned());
        let ret5 = Solution::repeated_string_match2("abcd".to_owned(), "cdabcdab".to_owned());
        assert!(ret == -1);
        assert!(ret2 == 2);
        assert!(ret3 == 2);
        assert!(ret4 == 1);
        assert!(ret5 == 3);
    }
}
