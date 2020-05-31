use std::cell::RefCell;
use std::rc::Rc;

pub(crate) struct Solution;

//二叉树
#[derive(Debug, PartialEq, Eq)]
pub struct TreeNode {
    pub val: i32,
    pub left: Option<Rc<RefCell<TreeNode>>>,
    pub right: Option<Rc<RefCell<TreeNode>>>,
}

impl TreeNode {
    #[inline]
    pub fn new(val: i32) -> Self {
        TreeNode {
            val,
            left: None,
            right: None,
        }
    }
}

//单链表
#[derive(PartialEq, Eq, Clone, Debug)]
pub struct ListNode {
    pub val: i32,
    pub next: Option<Box<ListNode>>, //堆上
}

impl ListNode {
    #[inline]
    fn new(val: i32) -> Self {
        ListNode { next: None, val }
    }
}

pub fn get_test_tree_5() -> Option<Rc<RefCell<TreeNode>>> {
    let e7 = Some(Rc::new(RefCell::new(TreeNode {
        val: 7,
        left: None,
        right: None,
    })));
    let e15 = Some(Rc::new(RefCell::new(TreeNode {
        val: 15,
        left: None,
        right: None,
    })));
    let e20 = Some(Rc::new(RefCell::new(TreeNode {
        val: 20,
        left: e15,
        right: e7,
    })));
    let e9 = Some(Rc::new(RefCell::new(TreeNode {
        val: 9,
        left: None,
        right: None,
    })));
    let e3 = Some(Rc::new(RefCell::new(TreeNode {
        val: 3,
        left: e9,
        right: e20,
    })));

    e3
}

pub fn get_test_tree_3() -> Option<Rc<RefCell<TreeNode>>> {
    let e2 = Some(Rc::new(RefCell::new(TreeNode {
        val: 2,
        left: None,
        right: None,
    })));
    let e1 = Some(Rc::new(RefCell::new(TreeNode {
        val: 2,
        left: None,
        right: None,
    })));
    let root1 = Some(Rc::new(RefCell::new(TreeNode {
        val: 1,
        left: e1,
        right: e2,
    })));

    root1
}

pub fn get_test_tree_2() -> Option<Rc<RefCell<TreeNode>>> {
    let e1 = Some(Rc::new(RefCell::new(TreeNode {
        val: 2,
        left: None,
        right: None,
    })));
    let root1 = Some(Rc::new(RefCell::new(TreeNode {
        val: 1,
        left: None,
        right: e1,
    })));

    root1
}
