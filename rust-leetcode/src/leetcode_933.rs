use std::collections::VecDeque;

///最近的请求次数
struct RecentCounter {
    queue: VecDeque<i32>,
}

/**
 * `&self` means the method takes an immutable reference.
 * If you need a mutable reference, change it to `&mut self` instead.
 */
impl RecentCounter {
    fn new() -> Self {
        RecentCounter {
            queue: VecDeque::new(),
        }
    }

    fn ping(&mut self, t: i32) -> i32 {
        self.queue.push_back(t);
        while *self.queue.front().unwrap() < t - 3000 {
            self.queue.pop_front();
        }
        self.queue.len() as i32
    }
}
