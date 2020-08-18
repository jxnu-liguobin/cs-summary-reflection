use crate::pre_structs::Solution;

///搜索插入位置
impl Solution {
    pub fn search_insert(nums: Vec<i32>, target: i32) -> i32 {
        let nums = nums;
        //找到知己反回索引，没有找到则返回该元素插入后保持数组仍然有序的索引位置，主要用于有序的数组/向量
        let ret = match nums.binary_search(&target) {
            Ok(found_index) => found_index,
            Err(maybe_insert) => maybe_insert,
        };
        ret as i32
    }
}

#[cfg(test)]
mod test {

    #[test]
    fn search_insert() {}
}
