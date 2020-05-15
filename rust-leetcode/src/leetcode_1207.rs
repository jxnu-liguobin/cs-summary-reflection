use std::collections::{HashMap, HashSet};

use crate::pre_structs::Solution;

///独一无二的出现次数
impl Solution {
    pub fn unique_occurrences(arr: Vec<i32>) -> bool {
        let mut map = HashMap::new();
        for num in arr.iter() {
            let n = if map.contains_key(num) {
                map.get(num).unwrap()
            } else {
                &0
            };
            if map.contains_key(num) {
                map.insert(num, *n + 1);
            } else {
                map.insert(num, 1);
            }
        }
        let set: HashSet<i32> = map.values().cloned().collect();
        set.len() == map.len()
    }
}

#[cfg(test)]
mod test {
    use crate::pre_structs::Solution;

    #[test]
    fn unique_occurrences() {}
}
