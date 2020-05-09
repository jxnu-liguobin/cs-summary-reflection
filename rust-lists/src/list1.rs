///一个不好的单向链表栈
use std::mem;

pub struct List {
    head: Link,
}

enum Link {
    Empty,
    More(Box<Node>),
}

struct Node {
    elem: i32,
    next: Link,
}

impl List {
    //列表实现，静态方法，构造函数
    pub fn new() -> Self {
        List { head: Link::Empty }
    }

    //实例方法
    pub fn push(&mut self, elem: i32) {
        let new_node = Box::new(Node {
            elem,
            next: mem::replace(&mut self.head, Link::Empty),
        });

        self.head = Link::More(new_node);
    }
    //实例方法
    pub fn pop(&mut self) -> Option<i32> {
        match mem::replace(&mut self.head, Link::Empty) {
            Link::Empty => None,
            Link::More(node) => {
                self.head = node.next;
                Some(node.elem)
            }
        }
    }
}

//实现drop特质，超出范围时自动回收内存
impl Drop for List {
    fn drop(&mut self) {
        //此处不能使用尾递归，只能使用while
        //https://rust-unofficial.github.io/too-many-lists/first-drop.html
        let mut cur_link = mem::replace(&mut self.head, Link::Empty);
        while let Link::More(mut boxed_node) = cur_link {
            cur_link = mem::replace(&mut boxed_node.next, Link::Empty);
        }
    }
}

#[cfg(test)]
mod test {
    use crate::list1::List;

    #[test]
    fn basics() {
        let mut list = List::new();

        //检查空列表的行为
        assert_eq!(list.pop(), None);

        //填充列表
        list.push(1);
        list.push(2);
        list.push(3);

        //删除列表中的元素
        assert_eq!(list.pop(), Some(3));
        assert_eq!(list.pop(), Some(2));

        //再次填充列表
        list.push(4);
        list.push(5);

        //再次删除列表中的元素
        assert_eq!(list.pop(), Some(5));
        assert_eq!(list.pop(), Some(4));

        //检查删除完
        assert_eq!(list.pop(), Some(1));
        assert_eq!(list.pop(), None);
    }
}