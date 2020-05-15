use std::collections::VecDeque;

///化栈为队
struct MyQueue {
    stack1: VecDeque<Option<i32>>,
    stack2: VecDeque<Option<i32>>,
}

impl MyQueue {
    /** Initialize your data structure here. */
    fn new() -> Self {
        MyQueue {
            stack1: VecDeque::new(),
            stack2: VecDeque::new(),
        }
    }

    /** Push element x to the back of queue. */
    fn push(&mut self, x: i32) {
        self.stack1.push_back(Some(x))
    }

    /** Removes the element from in front of queue and returns that element. */
    fn pop(&mut self) -> i32 {
        return MyQueue::peek_pop(self, true);
    }

    fn peek_pop(queue: &mut MyQueue, flag: bool) -> i32 {
        if queue.stack2.is_empty() {
            while !queue.stack1.is_empty() {
                queue.stack2.push_back(queue.stack1.pop_back().unwrap());
            }
        }
        let ret = if flag {
            queue.stack2.pop_back().unwrap()
        } else {
            queue.stack2.back().unwrap().clone()
        };
        ret.unwrap()
    }

    /** Get the front element. */
    fn peek(&mut self) -> i32 {
        return MyQueue::peek_pop(self, false);
    }

    /** Returns whether the queue is empty. */
    fn empty(&mut self) -> bool {
        self.stack1.is_empty() && self.stack2.is_empty()
    }
}
