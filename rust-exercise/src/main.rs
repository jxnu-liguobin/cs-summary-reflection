///标准库的比较，Ordering枚举包含Less， Greater和Equal
use std::cmp::Ordering;
///导入标准库的输入输出库
use std::io;
use std::ops::{Range, RangeBounds};

///引入rand库的Rng特质
use rand::Rng;

/// 引用和借用：https://dreamylost.cn/rust/Rust-Rust%E5%AD%A6%E4%B9%A0%E4%B9%8B%E5%BC%95%E7%94%A8%E4%B8%8E%E5%80%9F%E7%94%A8.html
/// 所有权：https://dreamylost.cn/rust/Rust-%E6%89%80%E6%9C%89%E6%9D%83.html
/// 切片：https://dreamylost.cn/rust/Rust-%E5%88%87%E7%89%87.html
fn main() {
    println!("Hello, world!");
    variables_function();
    println!("====================");
    simple_array_data_type();
    println!("====================");
    another_function(12);
    println!("====================");
    expr_function();
    println!("====================");
    println!("{}", five());//打印字符串，不能直接println!(five())
    println!("====================");
    control_function();
    println!("====================");
    println!("斐波那契第20项是：{}", fib(20));
    println!("====================");
    println!("斐波那契第20项是：{}", fib_2(20));
    println!("====================");
    string_function();
    println!("====================");
    return_function();
    println!("====================");
    tuple_function();
    println!("====================");
    copy_function();
    println!("====================");
    point_function();
    println!("====================");
    try_change_function();
    println!("====================");
    empty_point_function();
    println!("====================");
    struct_data_type();
    println!("====================");
    method_syntax();
    println!("====================");
    enum_data_type();
    println!("====================");
    match_syntax();
    match_syntax2();
    println!("====================");
    crate_function();
    println!("====================");
    example_guessing_game();
}

pub mod front_of_house;

//引用外部的条板箱
fn crate_function() {
    front_of_house::add_to_waitlist()
}

//pub mod lib;
//
//fn crate_function_lib() {
//    lib::eat_at_restaurant();
//    lib::eat_at_restaurant2();
//}

//简洁的控制流语法
fn match_syntax2() {
    let some_u8_value = Some(0u8);
    match some_u8_value {
        Some(3) => println!("three"),
        _ => () //这行是多余的样板代码
    }

    //使用if let 省略上面的样板代码
    if let Some(3) = some_u8_value {
        println!("three");
    }

    #[derive(Debug)]
    enum UsState {
        Alabama,
        Alaska,
    }
    enum Coin {
        Penny,
        Nickel,
        Dime,
        Quarter(UsState),
    }
    //使用if let简化代码
    fn value_in_cents(coin: Coin) -> u8 {
        let mut count = 0;
        if let Coin::Quarter(state) = coin {
            println!("State quarter from {:?}!", state);
        } else {
            count += 1;
        }
        1
    }
}

fn match_syntax() {
    //枚举与match
    enum Coin {
        Penny,
        Nickel,
        Dime,
        Quarter,
    }

    fn value_in_cents(coin: Coin) -> u8 {
        match coin {
            Coin::Penny => 1,
            Coin::Nickel => 5,
            Coin::Dime => 10,
            Coin::Quarter => 25,
        }
    }

    fn value_in_cents2(coin: Coin) -> u8 {
        match coin {
            Coin::Penny => {
                //代码多时需要使用花括号，并且最后一行返回值不加分号。大括号后面仍是逗号
                println!("Lucky penny!");
                1
            },
            Coin::Nickel => 5,
            Coin::Dime => 10,
            Coin::Quarter => 25,
        }
    }


    //绑定到值的匹配
    #[derive(Debug)]
    enum UsState {
        Alabama,
        Alaska,
    }

    enum Coin2 {
        Penny,
        Nickel,
        Dime,
        Quarter(UsState),
    }

    fn value_in_cents3(coin: Coin2) -> u8 {
        match coin {
            Coin2::Penny => 1,
            Coin2::Nickel => 5,
            Coin2::Dime => 10,
            Coin2::Quarter(state) => {
                println!("State quarter from {:?}!", state);
                25
            },
        }
    }

    //Option类型match
    fn plus_one(x: Option<i32>) -> Option<i32> {
        match x {
            //这行代码多余，但是又不能省略，否则编译报错。
            None => None,
            Some(i) => Some(i + 1),
        }
    }

    let five = Some(5);
    let six = plus_one(five);
    let none = plus_one(None);

    //使用占位符，编译通过
    let some_u8_value = 0u8;
    match some_u8_value {
        1 => println!("one"),
        3 => println!("three"),
        5 => println!("five"),
        7 => println!("seven"),
        _ => ()
    }
}

fn enum_data_type() {
    //定义枚举类型
    enum IpAddrKind {
        V4,
        V6,
    }

    //使用
    let four = IpAddrKind::V4;
    let six = IpAddrKind::V6;

    fn route(ip_kind: IpAddrKind) {}

    //两个值IpAddrKind::V4和IpAddrKind::V6都具有相同的类型: IpAddrKind
    route(IpAddrKind::V4);
    route(IpAddrKind::V6);

    struct IpAddr {
        //在结构体中使用枚举
        kind: IpAddrKind,
        address: String,
    }

    let home = IpAddr {
        kind: IpAddrKind::V4,
        address: String::from("127.0.0.1"),
    };

    let loopback = IpAddr {
        kind: IpAddrKind::V6,
        address: String::from("::1"),
    };


    //数值直接放入每个枚举变量中，而不是需要使用结构体 struct IpAddr
    enum IpAddr2 {
        V4(String),
        V6(String),
    }

    let home = IpAddr2::V4(String::from("127.0.0.1"));
    let loopback = IpAddr2::V6(String::from("::1"));

    //将枚举数值定义为不同类型，此时struct IpAddr无法实现
    enum IpAddr3 {
        V4(u8, u8, u8, u8),
        V6(String),
    }

    let home = IpAddr3::V4(127, 0, 0, 1);

    let loopback = IpAddr3::V6(String::from("::1"));

    //使用枚举
    enum Message {
        Quit,
        Move { x: i32, y: i32 },
        Write(String),
        ChangeColor(i32, i32, i32),
    }

    //使用结构体
    struct QuitMessage; // 单位结构
    struct MoveMessage {
        x: i32,
        y: i32,
    }
    struct WriteMessage(String); // 元祖结构
    struct ChangeColorMessage(i32, i32, i32); // 元祖结构

    //定义一个impl即可对所有枚举值生效，他们都能调用call
    impl Message {
        fn call(&self) {
            // do something
        }
    }

    let m = Message::Write(String::from("hello"));
    m.call();

    //Option类型
    let some_number = Some(5);
    let some_string = Some("a string");
    //为None时需要指定类型，否则无法推断出类型
    let absent_number: Option<i32> = None;

    let x: i8 = 5;
    let y: Option<i8> = Some(5);
    //Option<i8> 与 i8 是不同的类型  <>是泛型，Option<T>表示任意的类型都可以放进Option
    //let sum = x + y;
}

//下面为了编译，将错误或多余代码注释了。
fn method_syntax() {
    //方法语法，方法与函数不同
    #[derive(Debug)]
    struct Rectangle {
        width: u32,
        height: u32,
    }

    //结构体的实现块，方法第一个参数必须是&self，不需要声明类型（impl与struct名称相同，能自动推断self类型，这也是能自动引用、取消引用的原因）
    impl Rectangle {
        //把2个方法放在多个impl实现也是可以的
        fn area(&self) -> u32 {
            self.width * self.height
        }

        //新增方法，有额外参数
        fn can_hold(&self, other: &Rectangle) -> bool {
            self.width > other.width && self.height > other.height
        }

        //关联函数，没有self，类似其他语言的静态方法，但不是rust方法
        fn square(size: u32) -> Rectangle {
            Rectangle { width: size, height: size }
        }
    }

    let rect1 = Rectangle { width: 30, height: 50 };

    //c/c++中如果object是一个指针, object->something() 与 (*object).something()等价
    //Rust没有等效于->运算符；相反，Rust具有称为自动引用和取消引用的功能。调用方法是Rust少数具有这种行为的地方之一。
    //工作原理如下：当您使用object.something()调用方法时，Rust会自动添加＆，＆mut或*，从而使对象与方法的签名匹配。换句话说，以下是相同的：
    //p1.distance(&p2);
    //(&p1).distance(&p2);
    println!(
        "The area of the rectangle is {} square pixels.",
        rect1.area()
    );

    //方法参数
    let rect1 = Rectangle { width: 30, height: 50 };
    let rect2 = Rectangle { width: 10, height: 40 };
    let rect3 = Rectangle { width: 60, height: 45 };

    println!("Can rect1 hold rect2? {}", rect1.can_hold(&rect2));
    println!("Can rect1 hold rect3? {}", rect1.can_hold(&rect3));

    //impl块的另一个有用功能是允许我们在不以self为参数的impl块中定义函数。这些之所以称为关联函数，是因为它们与struct相关联。
    //它们仍然是函数，而不是方法，因为它们没有可使用的结构实例
    //关联函数通常用于将返回该结构的新实例的构造函数。例如，我们可以提供一个关联的函数，该函数将具有一个维度参数并将其用作宽度和高度，从而使创建矩形矩形变得更加容易，而不必两次指定相同的值：
    let sq = Rectangle::square(3);//类似调用静态方法
    println!("sq is {:#?}", sq);

    //关联函数与结构体相关，但是没有self实例，他们仍是函数！！！
    struct Test;
    impl Test {
        fn test() -> String {
            String::from("hello world")
        }
    }

    let test = Test::test();
    println!("test is {:#?}", test);
}

fn struct_data_type() {
    struct User {
        username: String,
        email: String,
        sign_in_count: u64,
        active: bool,
    }

    let mut user1 = User { //必须定义为可变的才能修改结构体的内容
        email: String::from("someone@example.com"),
        username: String::from("someusername123"),
        active: true,
        sign_in_count: 1,
    };

    user1.email = String::from("anotheremail@example.com");

    //对应参数名和结构体属性名称相同的，可以省略以减少代码
    fn build_user(email: String, username: String) -> User {
        User {
            email: email,
            username: username,
            active: true,
            sign_in_count: 1,
        }
    }

    //更好的方法是省略参数名，结构体没有顺序要求，与元祖不同，元祖结构体：仅声明类型的结构体
    fn build_user_better(email: String, username: String) -> User {
        User {
            email,
            username,
            active: true,
            sign_in_count: 1,
        }
    }

    //创建一个新的结构体user2，并使用user1的某些值
    let user2 = User {
        email: String::from("another@example.com"),
        username: String::from("anotherusername567"),
        active: user1.active,
        sign_in_count: user1.sign_in_count,
    };

    //更好的方式是使用 ..语法，其余字段应与给定实例（user1）中的字段具有相同的值
    let user2 = User {
        email: String::from("another@example.com"),
        username: String::from("anotherusername567"),
        ..user1
    };

    //元祖结构体，没有命名属性字段，仅有类型声明
    struct Color(i32, i32, i32);
    struct Point(i32, i32, i32);

    let black = Color(0, 0, 0);
    let origin = Point(0, 0, 0);

    //此结构体存储切片。而上面的结构体存储的是String类型，下面代码编译会报错，因为使用切片时需要指定生命周期。与使用String拥有所有权不同,&str没有获取所有权而只是借用
    //    struct User2 {
    //        username: &str,
    //        email: &str,
    //        sign_in_count: u64,
    //        active: bool,
    //    }

    let width1 = 30;
    let height1 = 50;

    fn area(width: u32, height: u32) -> u32 {
        width * height
    }

    println!("The area of the rectangle is {} square pixels.", area(width1, height1));

    //使用元祖
    fn area_tuple(dimensions: (u32, u32)) -> u32 {
        dimensions.0 * dimensions.1
    }

    let rect1 = (30, 50);

    println!(
        "The area of the rectangle is {} square pixels.",
        area_tuple(rect1)
    );

    //使用结构体赋予更多含义
    #[derive(Debug)] //使得该结构体能在println!中被打印
    struct Rectangle {
        width: u32,
        height: u32,
    }

    fn area_struct(rectangle: &Rectangle) -> u32 {
        rectangle.width * rectangle.height
    }

    let rect1 = Rectangle { width: 30, height: 50 };
    println!(
        "The area of the rectangle is {} square pixels.",
        area_struct(&rect1)
    );

    //这将会报错，因为该结构体不支持显示：`Rectangle` doesn't implement `std::fmt::Display`
    println!("rect1 is {:#?}", rect1);//{:?}使用调试模式打印也会报错：`Rectangle` doesn't implement `std::fmt::Debug`,{:#?} 格式化打印
}

fn empty_point_function() {

    //let reference_to_nothing = dangle();
    let reference_to_nothing = no_dangle();
    fn no_dangle() -> String {
        String::from("hello")// 直接反回函数的值，不能加分号
    }
    //编译报错，因为s是在dangle内部创建的，所以当dangle的代码完成时，将释放s。但是我们试图返回对它的引用。这意味着该引用将指向无效的String。Rust不允许我们这样做。
    //    fn dangle() -> &String {
    //        let s = String::from("hello");
    //        &s
    //    }
}

fn try_change_function() {
    //必须都是mut的，否则编译就会报错，不可变，无法被改变
    fn change(some_string: &mut String) {
        some_string.push_str(", world");
    }
    let mut s = String::from("hello");
    change(&mut s);


    let mut s = String::from("hello");
    let r1 = &mut s;
    //let r2 = &mut s;//可变引用只能被出借一次，这里将会编译报错
    //println!("{}, {}", r1, r2);


    let mut s = String::from("hello");
    {
        let r1 = &mut s;
    } // r1在这里超出范围，因此我们可以毫无问题地进行新引用。
    let r2 = &mut s;//正常使用，虽然上面已经用过s


    let mut s = String::from("hello");
    let r1 = &s;   // 没问题，与上面两次mut出借不一样，这里是没有mut，所以对于不可变引用，可以使用多次次，且不可在拥有不可变引用时同时拥有可变引用
    let r2 = &s;   // 没问题
    //let r3 = &mut s;    // 有问题，不可变在后面却是可变，不允许，编译报错
    //println!("{}, {}, and {}", r1, r2, r3);


    let mut s = String::from("hello");

    let r1 = &s; // 没问题
    let r2 = &s; // 没问题
    println!("{} and {}", r1, r2);
    //在此之后不再使用r1和r2

    let r3 = &mut s; // 没问题，因为r1 r2进入println! 并且在此之后会失效，与所有权有关。
    println!("{}", r3);
}

fn point_function() {
    fn calculate_length(s: &String) -> usize {
        s.len()
    }
    let s1 = String::from("hello");
    //类似c/c++传递指针/引用
    let len = calculate_length(&s1);
    //s1在之后还能使用
    println!("The length of '{}' is {}.", s1, len);
}

fn copy_function() {
    let x = 5;
    //基本类型在移动时使用copy，x不会失效。
    let y = x;
    println!("x = {}, y = {}", x, y);

    //使用clone克隆数据，目前先理解为深拷贝
    let s1 = String::from("hello");
    let s2 = s1.clone();

    println!("s1 = {}, s2 = {}", s1, s2);
}

fn tuple_function() {
    let s1 = String::from("hello");
    let (s2, len) = calculate_length(s1);
    println!("The length of '{}' is {}.", s2, len);
    fn calculate_length(s: String) -> (String, usize) {
        let length = s.len(); // len() 返回字符串的长度
        (s, length)
    }
}

fn return_function() {
    let s1 = gives_ownership();           // lets_ownership移动其返回值到s1中

    let s2 = String::from("hello");     // s2进入范围

    let s3 = takes_and_gives_back(s2);  // s2被移入takes_and_gives_back,  takes_and_gives_back的返回值被移动到s3
    println!("{},{}", s1, s3);

    fn gives_ownership() -> String {             // gives_ownership会其返回值移动到调用它的函数中
        let some_string = String::from("hello"); // some_string进入范围
        some_string                              // 返回some_string字符串并移到调用函数
    }

    // take_and_gives_back将获取一个String并返回一个
    fn takes_and_gives_back(a_string: String) -> String { // a_string进入范围
        a_string                                    // 返回a_string并移至调用函数
    }
}

fn string_function() {
    let mut s = String::from("hello");
    s.push_str(", world!"); // push_str() 将文字附加到字符串

    println!("{}", s); //打印 hello, world!


    let s = String::from("hello");  // s进入范围

    takes_ownership(s);            // s的值移动到函数，所以在这里不再有效
    //println!("{}", s);//编译错误：value borrowed here after move。出借后的s被移动，后续不可用

    let x = 5;                         // x进入范围
    makes_copy(x);               // x将移动到函数
    // 但是i32是Copy，所以之后还可以使用
    println!("{}", x);//正常打印

    fn takes_ownership(some_string: String) {
        println!("{}", some_string);
    } //在这里，some_string超出范围并调用`drop`。内存释放

    fn makes_copy(some_integer: i32) {
        println!("{}", some_integer);
    }
}

fn fib(n: i32) -> i32 {
    if n == 0 {
        0
    } else if n == 1 {
        1
    } else {
        fib(n - 1) + fib(n - 2)
    }
}

fn fib_2(n: i32) -> i32 {
    let mut a = 0;
    let mut b = 1;
    let mut c = 0;
    if n == 0 || n == 1 {
        n
    } else {
        for number in 2..(n + 1) {
            c = a + b;
            a = b;
            b = c;
        }
        c
    }
}

//控制流
fn control_function() {
    let number = 3;
    //表达式结果必须是bool类型，不像c会自动将非bool转化为bool
    if number < 5 {
        println!("condition was true");
    } else {
        println!("condition was false");
    }

    //处理多个if
    let number = 6;//阴影，遮盖了前面的number

    if number % 4 == 0 {
        println!("number is divisible by 4");
    } else if number % 3 == 0 {
        println!("number is divisible by 3");
    } else if number % 2 == 0 {
        println!("number is divisible by 2");
    } else {
        println!("number is not divisible by 4, 3, or 2");
    }

    //与Scala一样，可以将if表达式的结果赋值给变量（这里的变量一般是指不可变的变量，虽然绕口，但是确实是事实）
    let condition = true;
    //从每个分支取得的if的返回值必须是同一类型，否则编译报错
    let number = if condition {
        5
    } else {
        6
    };

    println!("The value of number is: {}", number);

    //循环
    loop {
        println!("again!");
        break;//这个分号可省
    }
    //从循环中返回值
    let mut counter = 0;
    //循环赋值给变量
    let result = loop {
        counter += 1;
        if counter == 10 {
            break counter * 2;
        };//这个分号可省
    };

    //分号的使用还不清晰明确，后面再看
    //暂时理解为，赋值给变量的代码块需要使用分号短句，不赋值可以不用分号，而表达式本身就是直接返回，使用分号反而不行。（return显示指定返回值）
    println!("The result is {}", result);

    //while循环
    let mut number = 3;
    //使用while可以减少大量的if else break
    while number != 0 {
        println!("{}!", number);
        number -= 1;
    };//这个分号可以省略
    println!("LIFTOFF!!!");

    //while变量数组
    let a = [10, 20, 30, 40, 50];
    let mut index = 0;

    while index < 5 {
        println!("the value is: {}", a[index]);

        index += 1;
    }

    //使用for循环更加简单
    //rust常用for，因为rust不会有迭代器失效的问题
    let a = [10, 20, 30, 40, 50];
    for element in a.iter() {
        println!("the value is: {}", element);
    }

    //使用倒数
    for number in (1..4).rev() {
        //输出3!2!1!LIFTOFF!!!  print是没有换行的，与其他语言一致
        print!("{}!", number);
    }
    println!("LIFTOFF!!!");
}

//具有返回值的rust函数
fn five() -> i32 {
    //这里同样，由于需要返回值为i32类型，增加了分号表示语句，没有返回值（实际是空括号），所以导致类型不一致，编译会报错
    5
}

fn expr_function() {

    //赋值需要返回值，rust语句没有返回值，不同于其他语言赋值可以连用
    // let x = (let y = 6);

    let x = 5;

    let y = {
        let x = 3;
        x + 1 //返回x+1，且不能用分号，有分号表示这个是语句，没有返回值，无法赋值给y
    };

    println!("The value of y is: {}", y);
}

fn another_function(x: i32) {
    //传参数的rust函数，与Scala一样，名称: 类型
    println!("The value of x is: {}", x);
}

//rust不关注函数与main的顺序问题
fn simple_array_data_type() {

    //--release模式下，整数溢出将会变为最小值
    //在u8(0-255)类型下，256变为0，257变为1，依此类推


    //默认浮点类型是f64，相当于Java double，IEEE754标准
    let x = 2.0; // f64

    let y: f32 = 3.0; // f32

    //数值运算，与其他语言相同，类型可以自动推断，不用指定类型
    // addition
    let sum = 5 + 10;

    // subtraction
    let difference = 95.5 - 4.3;

    // multiplication
    let product = 4 * 30;

    // division
    let quotient = 56.7 / 32.2;

    // remainder
    let remainder = 43 % 5;

    let t = true;
    //显示指定类型
    let f: bool = false;

    //字符类型，Unicode，4bytes
    let c = 'z';
    let z = 'ℤ';
    let heart_eyed_cat = '😻';

    //元组类型，与Scala基本相同，可以推断出类型
    let tup: (i32, f64, u8) = (500, 6.4, 1);
    let tup = (500, 6.4, 1);
    //提取出元组的每个值
    let (x, y, z) = tup;
    println!("The value of y is: {}", y);

    //使用 .获取元组的值，从0下标开始
    let five_hundred = tup.0;
    let six_point_four = tup.1;
    let one = tup.2;

    //数组类型，一般在只有固定元素个数的时候使用
    let array = [1, 2, 3, 4, 5];

    //初始化数组的第二种方法
    let a: [i32; 5] = [1, 2, 3, 4, 5];

    //等价于let a = [3, 3, 3, 3, 3];，意为5个3构成的数组
    let a = [3; 5];

    //访问数组，同样是从0下标开始
    let first = a[0];
    let second = a[1];

    //Rust通过立即退出而不是允许内存访问并继续操作来保护您免受此类错误的侵害
    let element = a[0];//若下标大于数组索引则运行时检查并报错退出"error: index out of bounds: the len is 5 but the index is 10"
}

fn variables_function() {
    //默认i32，带符号32位整数
    let x = 5;
    println!("The value of x is: {}", x);
    // x = 6; 不可变的，编译不过
    // println!("The value of x is: {}", x);

    let mut y = 6;
    println!("The value of y is: {}", y);
    y = 7;//可变的变量
    println!("The value of y is: {}", y);
    //常量，必须指定类型，不可省略
    const MAX_POINTS: u32 = 100_000;
    println!("The value of const value is: {}", MAX_POINTS);

    // 阴影允许定义变量与前面重名，前者被遮蔽
    //mut与shadowing区别：后者将创建一个新的变量，因此可以改变类型，使用相同的名称，常见用法如下：
    //let spaces = "   ";
    // let spaces = spaces.len();//使用相同名称但类型已经发生变化
    // 但是对于mut则不能，spaces虽然是可变的，但是类型是字符串类型的
    // let mut spaces = "   ";
    // spaces = spaces.len();
    let i = 5;
    let i = x + 1;
    let i = x * 2;

    println!("The value of x is: {}", i);
}

fn example_guessing_game() {
    println!("Guess the number!");

    //thread_rng一个在当前执行线程本地且由操作系统播种的随机数生成器
    let secret_number = rand::thread_rng().gen_range(1, 101);

    //println!("The secret number is: {}", secret_number);
    loop {
        println!("Please input your guess.");

        //变量默认是不可变的。使用mut表示变量是可变的，定义成let foo = 5; 则是不可变。
        let mut guess = String::new();//关联函数，在类型上实现。一些语言称为静态方法。该函数创建了一个空串

        //没有使用use，则这里需要写成 std::io::stdin
        //&表示该参数是一个引用，Rust的主要优势之一是使用引用的安全性和便捷性
        //&使您代码的多个部分可以访问同一条数据，而无需将该数据多次复制到内存中
        io::stdin().read_line(&mut guess).expect("Failed to read line");

        //无法比较数值与字符串需要转化为数值，Rust默认为i32
        //Rust允许我们用新的值遮盖以前的值guess。此功能通常用于要将值从一种类型转换为另一种类型的情况。
        //阴影使我们可以重用guess变量名，而不是强迫我们创建两个唯一变量，例如guess_str和guess。
        //前面的guess是可变的，这个是不可变的。
        //let guess: u32 = guess.trim().parse().expect("Please type a number!");//类型不明确，必须指定具体类型
        //println!是宏
        println!("You guessed: {}", guess);

        let guess: u32 = match guess.trim().parse() {
            Ok(num) => num,
            //遇到无效输入直接跳过
            Err(_) => continue,
        };

        println!("Please input your guess.");

        match guess.cmp(&secret_number) {
            Ordering::Less => println!("Too small!"),
            Ordering::Greater => println!("Too big!"),
            Ordering::Equal => {
                println!("You win!");
                //猜到正确数字后退出循环
                break;
            }
        }
    }
}
