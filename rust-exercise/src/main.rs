///标准库的比较，Ordering枚举包含Less， Greater和Equal
use std::cmp::Ordering;
use std::error::Error;
use std::fs;
use std::fs::File;
///导入标准库的输入输出库
use std::io;
use std::io::ErrorKind;
use std::io::Read;
use std::ops::{Range, RangeBounds};

///引入rand库的Rng特质
use rand::Rng;

use closures_syntax::*;
use collection_function::*;
use control_function::*;
use enum_data_type::*;
use example_guessing_game::example_guessing_game;
use generic_traits_lifetimes::*;
use iterator_demonstration::*;
use macro_syntax::*;
use match_syntax::*;
use method_syntax::*;
use other_function::*;
use panic_function::panic_function;
use point_function::*;
use simple_array_data_type::*;
use smart_point::*;
use struct_data_type::*;
use thread_syntax::*;
use variables_function::*;

pub mod closures_syntax;
pub mod collection_function;
pub mod control_function;
pub mod enum_data_type;
pub mod example_guessing_game;
pub mod generic_traits_lifetimes;
pub mod iterator_demonstration;
pub mod macro_syntax;
pub mod match_syntax;
pub mod method_syntax;
pub mod other_function;
pub mod panic_function;
pub mod point_function;
pub mod simple_array_data_type;
pub mod smart_point;
pub mod struct_data_type;
pub mod thread_syntax;
pub mod variables_function;

/// 引用和借用：https://dreamylost.cn/rust/Rust-Rust%E5%AD%A6%E4%B9%A0%E4%B9%8B%E5%BC%95%E7%94%A8%E4%B8%8E%E5%80%9F%E7%94%A8.html
/// 所有权：https://dreamylost.cn/rust/Rust-%E6%89%80%E6%9C%89%E6%9D%83.html
/// 切片：https://dreamylost.cn/rust/Rust-%E5%88%87%E7%89%87.html
/// main方法，引用了其他rs文件
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
    println!("{}", five()); //打印字符串，不能直接println!(five())
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
    collection_function();
    println!("====================");
    panic_function();
    println!("====================");
    largest_function();
    println!("====================");
    enum_generic();
    println!("====================");
    trait_function();
    println!("====================");
    closures_syntax();
    println!("====================");
    iterator_function();
    println!("====================");
    box_function();
    println!("====================");
    create_thread();
    println!("====================");
    join_thread();
    println!("====================");
    use_var_thread();
    println!("====================");
    channel_thread();
    println!("====================");
    send_multi_msg();
    println!("====================");
    copy_tx();
    println!("====================");
    mutex_thread();
    println!("====================");
    mutex_multi_thread();
    println!("====================");
    marco_function();
    println!("====================");
    //example_guessing_game();
}
