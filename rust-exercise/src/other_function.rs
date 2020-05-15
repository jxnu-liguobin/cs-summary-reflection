use rust_exercise::front_of_house;

//引用外部的条板箱
pub fn crate_function() {
    front_of_house::add_to_waitlist()
}

//pub mod lib;
//
//fn crate_function_lib() {
//    lib::eat_at_restaurant();
//    lib::eat_at_restaurant2();
//}

pub fn return_function() {
    let s1 = gives_ownership(); // lets_ownership移动其返回值到s1中

    let s2 = String::from("hello"); // s2进入范围

    let s3 = takes_and_gives_back(s2); // s2被移入takes_and_gives_back,  takes_and_gives_back的返回值被移动到s3
    println!("{},{}", s1, s3);

    fn gives_ownership() -> String {
        // gives_ownership会其返回值移动到调用它的函数中
        let some_string = String::from("hello"); // some_string进入范围
        some_string // 返回some_string字符串并移到调用函数
    }

    // take_and_gives_back将获取一个String并返回一个
    fn takes_and_gives_back(a_string: String) -> String {
        // a_string进入范围
        a_string // 返回a_string并移至调用函数
    }
}

pub fn fib(n: i32) -> i32 {
    if n == 0 {
        0
    } else if n == 1 {
        1
    } else {
        fib(n - 1) + fib(n - 2)
    }
}

pub fn fib_2(n: i32) -> i32 {
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

//具有返回值的rust函数
pub fn five() -> i32 {
    ///这里同样，由于需要返回值为i32类型，增加了分号表示语句，没有返回值（实际是空括号），所以导致类型不一致，编译会报错
    5
}

pub fn another_function(x: i32) {
    //传参数的rust函数，与Scala一样，名称: 类型
    println!("The value of x is: {}", x);
}
