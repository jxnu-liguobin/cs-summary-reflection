use crate::pre_structs::Solution;

///翻转图像
impl Solution {
    pub fn flip_and_invert_image(a: Vec<Vec<i32>>) -> Vec<Vec<i32>> {
        let ret: Vec<Vec<i32>> = a
            .iter()
            .map(|row| -> Vec<i32> {
                let mut new_row: Vec<i32> = row
                    .iter()
                    .map(|x| -> i32 {
                        let new_x = if let 0 = x { 1 } else { 0 };
                        new_x
                    })
                    .collect();
                new_row.reverse();
                new_row
            })
            .collect();
        ret
    }
}
