use std::cmp::Ordering;
use std::io;

use rand::Rng;

///简单的猜数字
pub fn example_guessing_game() {
    println!("Guess the number!");

    ///thread_rng一个在当前执行线程本地且由操作系统播种的随机数生成器
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