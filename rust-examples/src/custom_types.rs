///自定义类型
pub fn custom_types() {
    structures();
    enums();
    testcase_linkedlist();
    constants();
}

fn structures() {
    #[derive(Debug)]
    struct Person<'a> {
        //a定义了一个生命周期
        name: &'a str,
        age: u8,
    }

    //单位结构体
    struct Nil;

    //元祖结构体
    struct Pair(i32, f32);

    //含有2个字段的结构体
    struct Point {
        x: f32,
        y: f32,
    }

    //结构可以包含另一个结构
    #[allow(dead_code)]
    struct Rectangle {
        top_left: Point,
        bottom_right: Point,
    }

    //快速创建结构体，变量名与字段名相同
    let name = "Peter";
    let age = 27;
    let peter = Person { name, age };

    //debug打印
    println!("{:?}", peter);

    let point: Point = Point { x: 10.3, y: 0.4 };

    //访问其中一个字段
    println!("point coordinates: ({}, {})", point.x, point.y);

    //创建新的一个实例，并使用point的其他字段的值
    let bottom_right = Point { x: 5.2, ..point };

    //y与point.y相同，因为是从point创建的新的bottom_right
    println!("second point: ({}, {})", bottom_right.x, bottom_right.y);

    //使用“let”绑定对点进行解构
    let Point {
        x: top_edge,
        y: left_edge,
    } = point;

    let _rectangle = Rectangle {
        //结构体实例也是一个表达式
        top_left: Point {
            x: left_edge,
            y: top_edge,
        },
        bottom_right: bottom_right,
    };

    //创建单元结构体
    let _nil = Nil;

    //创建一个元祖结构体
    let pair = Pair(1, 0.1);

    //访问元祖结构体的字段，与访问元祖一样，使用下标
    println!("pair contains {:?} and {:?}", pair.0, pair.1);

    //解构元组结构体
    let Pair(integer, decimal) = pair;

    println!("pair contains {:?} and {:?}", integer, decimal);
}

fn enums() {
    //每一个都是不同的和独立的。
    enum WebEvent {
        PageLoad,
        PageUnload,
        //元祖结构体
        KeyPress(char),
        Paste(String),
        //经典的类c结构体
        Click { x: i64, y: i64 },
    }

    //以“WebEvent”枚举作为参数并，不返回数据
    fn inspect(event: WebEvent) {
        match event {
            //解构，匹配match
            WebEvent::PageLoad => println!("page loaded"),
            WebEvent::PageUnload => println!("page unloaded"),
            WebEvent::KeyPress(c) => println!("pressed '{}'.", c),
            WebEvent::Paste(s) => println!("pasted \"{}\".", s),
            WebEvent::Click { x, y } => {
                println!("clicked at x={}, y={}.", x, y);
            }
        }
    }

    let pressed = WebEvent::KeyPress('x');
    //从字符串片段创建一个字符串
    let pasted = WebEvent::Paste("my text".to_owned());
    let click = WebEvent::Click { x: 20, y: 80 };
    let load = WebEvent::PageLoad;
    let unload = WebEvent::PageUnload;

    inspect(pressed);
    inspect(pasted);
    inspect(click);
    inspect(load);
    inspect(unload);

    ///类型别名
    enum VeryVerboseEnumOfThingsToDoWithNumbers {
        Add,
        Subtract,
    }

    //与Scala相同
    type Operations = VeryVerboseEnumOfThingsToDoWithNumbers;

    let x = Operations::Add;

    impl VeryVerboseEnumOfThingsToDoWithNumbers {
        //最常见的位置使用self别名的impl块
        fn run(&self, x: i32, y: i32) -> i32 {
            match self {
                Self::Add => x + y,
                Self::Subtract => x - y,
            }
        }
    }

    enum Status {
        Rich,
        Poor,
    }

    enum Work {
        Civilian,
        Soldier,
    }

    use Status::{Poor, Rich};
    use Work::*;
    let status = Poor;
    let work = Civilian;

    match status {
        Rich => println!("The rich have lots of money!"),
        Poor => println!("The poor have no money..."),
    }

    match work {
        Civilian => println!("Civilians work!"),
        Soldier => println!("Soldiers fight!"),
    }

    ///经典的类c结构体
    enum Number {
        Zero,
        //隐式的递增的，从0开始
        One,
        Two,
    }

    enum Color {
        Red = 0xff0000,
        //显示的
        Green = 0x00ff00,
        Blue = 0x0000ff,
    }

    println!("zero is {}", Number::Zero as i32);
    println!("one is {}", Number::One as i32);
    println!("roses are #{:06x}", Color::Red as i32);
    println!("violets are #{:06x}", Color::Blue as i32);
}

fn testcase_linkedlist() {
    use List::*;
    enum List {
        //元组结构体，它包装一个元素和指向下一个节点的指针
        Cons(u32, Box<List>),
        //表示链表结束的节点
        Nil,
    }

    //方法可以附加到枚举
    impl List {
        //创建空的List
        fn new() -> List {
            Nil
        }

        //向前添加元素
        fn prepend(self, elem: u32) -> List {
            Cons(elem, Box::new(self))
        }

        //返回列表的长度
        fn len(&self) -> u32 {
            //必须匹配self，因为此方法的行为
            //取决于“self”的变体。“self”具有类型“&List”，而“*self”具有类型“List”，匹配于具体类型“T”优先于引用的匹配类型“&T”`
            match *self {
                //不能拥有尾，因为“self”是借来的；相反，要引用尾
                Cons(_, ref tail) => 1 + tail.len(),
                Nil => 0,
            }
        }

        //以（堆分配）字符串形式返回列表的表示形式
        fn stringify(&self) -> String {
            match *self {
                Cons(head, ref tail) => {
                    //返回堆分配的字符串，而不是打印到控制台
                    format!("{}, {}", head, tail.stringify())
                }
                Nil => format!("Nil"),
            }
        }
    }

    let mut list = List::new();

    list = list.prepend(1);
    list = list.prepend(2);
    list = list.prepend(3);

    println!("linked list has length: {}", list.len());
    println!("{}", list.stringify()); //3, 2, 1, Nil
}

pub fn constants() {
    ///Rust有两种不同类型的常数，可以在任何范围内声明，包括全局。两者都需要显式类型声明
    /// 不可更改的值（常见情况）
    /// 具有“静态寿命”的可能可变的变量。可以推断静态寿命，而不必指定。访问或修改可变静态变量是不安全的

    static LANGUAGE: &str = "Rust";
    const THRESHOLD: i32 = 10;

    fn is_big(n: i32) -> bool {
        n > THRESHOLD
    }

    let n = 16;

    println!("This is {}", LANGUAGE);
    println!("The threshold is {}", THRESHOLD);
    println!("{} is {}", n, if is_big(n) { "big" } else { "small" });

    // Error! Cannot modify a `const`.
    //THRESHOLD = 5;
    // FIXME ^ Comment out this line
}
