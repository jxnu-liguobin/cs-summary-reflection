use std::ops::Deref;
use std::rc::Rc;

///智能指针
//将智能指针与普通结构区分开的特征是，智能指针实现了Deref和Drop特质。
//Deref特质允许智能指针结构的实例的行为类似于引用，因此您可以编写可与引用或智能指针一起使用的代码。
//Drop特质可让您自定义当智能指针的实例超出范围时运行的代码。（超出范围时指向的堆数据也会被清除）
//标准库中最常见的智能指针
//Box<T> 用于在堆上分配值
//Rc<T>，这是一种启用多种所有权的引用计数类型
//Ref<T>以及RefMut<T>通过访问RefCell<T>的类型，该类型在运行时而不是编译时强制执行借用规则

pub fn box_function() {
    ///将5存储在堆上
    let b = Box::new(5);
    println!("b = {}", b);

    ///使用box解决无限递归
    enum List {
        Cons(i32, Box<List>),
        //堆上分配数据，子集合使用指针，对已知类型指针的大小是固定的（usize），不会因为数据而变化，所以该递归可计算需要的存储空间
        Nil,
    }
    use List::{Cons, Nil};
    let list = Cons(1, Box::new(Cons(2, Box::new(Cons(3, Box::new(Nil))))));

    ///使用Deref特质将智能指针视为常规引用
    //定义自己的智能指针
    struct MyBox<T>(T);
    impl<T> MyBox<T> {
        fn new(x: T) -> MyBox<T> {
            MyBox(x)
        }
    }
    let x = 5;
    let y = MyBox::new(x);
    assert_eq!(5, x);
    //assert_eq!(5, *y);//编译报错，没有实现Deref特质
    impl<T> Deref for MyBox<T> {
        //通过实现Deref特质，将MyBox结构作为引用
        type Target = T;
        fn deref(&self) -> &T {
            &self.0
        }
    }
    assert_eq!(5, *y);//*(y.deref())，如果该deref方法直接返回该值而不是对该值的引用，则该值将移出self。因为我们*操作时一般不需要拥有这个所有权

    ///隐式的强制实现deref特质
    fn hello(name: &str) {
        println!("Hello, {}!", name);
    }
    let m = MyBox::new(String::from("Rust"));
    hello(&m);
    let m = MyBox::new(String::from("Rust"));
    hello(&(*m)[..]);//如果Rust没有实现deref，代码应该写成这样（虽然两者都可用，但是这个更麻烦）

    //由于有借用规则，如果您有可变引用，则该可变引用必须是对该数据的唯一引用（否则，程序将无法编译）。
    //将一个可变引用转换为一个不可变引用将永远不会违反借用规则。
    //将不可变引用转换为可变引用将需要对该数据只有一个不可变引用，而借用规则并不能保证这一点。
    //因此，Rust无法做出将不可变引用转换为可变引用的假设
    //当T: Deref<Target=U> 从 &T 到 &U  上面就是这个遵从了 &String => &str
    //当T: DerefMut<Target=U> 从 &mut T 到 &mut U
    //当T: Deref<Target=U> 从 &mut T 到 &U


    ///在实例超出范围时将打印该结构
    struct CustomSmartPointer {
        data: String,
    }
    impl Drop for CustomSmartPointer {
        //实现Drop特质的CustomSmartPointer结构
        //Rust中的drop函数是一种特定的析构函数。不允许主动显示的调用
        fn drop(&mut self) {
            println!("Dropping CustomSmartPointer with data `{}`!", self.data);
        }
    }
    let c = CustomSmartPointer { data: String::from("my stuff") };
    let d = CustomSmartPointer { data: String::from("other stuff") };
    println!("CustomSmartPointers created.");

    let c = CustomSmartPointer { data: String::from("some data") };
    println!("CustomSmartPointer created.");
    drop(c);//希望早点被删除，调用std::mem::drop在值超出范围之前显式删除它。与c++析构函数相同，drop只被调用一次
    println!("CustomSmartPointer dropped before the end of main.");

    ///使用Rc<T>（引用计数）智能指针共享数据
    enum ListRc {
        ConsRc(i32, Rc<ListRc>),
        NilRc,
    }
    use ListRc::*;
    let a = Rc::new(ConsRc(5, Rc::new(ConsRc(10, Rc::new(NilRc)))));
    println!("count after creating a = {}", Rc::strong_count(&a));//引用计数=1
    let b = ConsRc(3, Rc::clone(&a));//clone无额外性能开销，实际仅增加了引用计数而不是真的深拷贝数据
    println!("count after creating b = {}", Rc::strong_count(&a));//引用计数=2
    {
        let c = ConsRc(4, Rc::clone(&a));
        println!("count after creating c = {}", Rc::strong_count(&a));//引用计数=3
    }//超出范围了，引用计数变为2
    println!("count after c goes out of scope = {}", Rc::strong_count(&a));//Weak 弱引用计数

    //运行时执行检查不可变或可变的借用，RefCell<T>，因为RefCell<T>允许在运行时检查可变借位，所以即使RefCell<T>不可变，您也可以更改RefCell<T>内部的值。
    //Box<T>允许在编译时检查不可变或可变的借用
    //Rc<T> 只允许在编译时检查不可变借用
    //即时引用不可变，引用指向的数据还是可以变的。
    //运行时跟踪借用会造成一些性能损失
}

pub trait Messenger {
    fn send(&self, msg: &str);
}

pub struct LimitTracker<'a, T: Messenger> {
    messenger: &'a T,
    value: usize,
    max: usize,
}

impl<'a, T> LimitTracker<'a, T> where T: Messenger {
    pub fn new(messenger: &T, max: usize) -> LimitTracker<T> {
        LimitTracker {
            messenger,
            value: 0,
            max,
        }
    }


    pub fn set_value(&mut self, value: usize) {
        self.value = value;
        let percentage_of_max = self.value as f64 / self.max as f64;
        if percentage_of_max >= 1.0 {
            self.messenger.send("Error: You are over your quota!");
        } else if percentage_of_max >= 0.9 {
            self.messenger.send("Urgent warning: You've used up over 90% of your quota!");
        } else if percentage_of_max >= 0.75 {
            self.messenger.send("Warning: You've used up over 75% of your quota!");
        }
    }
}

///智能指针 引用计数 https://doc.rust-lang.org/book/ch15-06-reference-cycles.html
#[cfg(test)]
mod tests {
    use std::cell::RefCell;

    use crate::smart_point::{LimitTracker, Messenger};

    struct MockMessenger {
        //sent_messages: Vec<String>,
        sent_messages: RefCell<Vec<String>>,

    }

    impl MockMessenger {
        fn new() -> MockMessenger {
            //MockMessenger { sent_messages: vec![] }
            MockMessenger { sent_messages: RefCell::new(vec![]) }
        }
    }

    impl Messenger for MockMessenger {
        fn send(&self, message: &str) {
            //self.sent_messages.push(String::from(message));//编译报错
            self.sent_messages.borrow_mut().push(String::from(message));//可变借用
        }
    }

    #[test]
    fn it_sends_an_over_75_percent_warning_message() {
        let mock_messenger = MockMessenger::new();
        let mut limit_tracker = LimitTracker::new(&mock_messenger, 100);
        limit_tracker.set_value(80);
        //assert_eq!(mock_messenger.sent_messages.len(), 1);
        assert_eq!(mock_messenger.sent_messages.borrow().len(), 1);
    }
}
