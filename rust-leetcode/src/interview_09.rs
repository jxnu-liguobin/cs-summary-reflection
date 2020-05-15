use std::collections::VecDeque;

///用两个栈实现队列
struct CQueue {
    stack1: VecDeque<Option<i32>>,
    stack2: VecDeque<Option<i32>>,
}

/**
 * `&self` means the method takes an immutable reference.
 * If you need a mutable reference, change it to `&mut self` instead.
 */
impl CQueue {
    fn new() -> Self {
        CQueue {
            stack1: VecDeque::new(),
            stack2: VecDeque::new(),
        }
    }

    fn append_tail(&mut self, value: i32) {
        self.stack1.push_back(Some(value))
    }

    fn delete_head(&mut self) -> i32 {
        if self.stack2.is_empty() {
            while !self.stack1.is_empty() {
                self.stack2.push_back(self.stack1.pop_back().unwrap());
            }
        }
        let e = self.stack2.pop_back();
        if e.is_none() {
            -1
        } else {
            e.unwrap().unwrap()
        }
    }
}
