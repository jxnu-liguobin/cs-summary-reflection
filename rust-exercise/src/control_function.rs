///控制流
pub fn control_function() {
    let number = 3;
    //表达式结果必须是bool类型，不像c会自动将非bool转化为bool，if上不能使用文档注释
    if number < 5 {
        println!("condition was true");
    } else {
        println!("condition was false");
    }

    ///处理多个if
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

    ///与Scala一样，可以将if表达式的结果赋值给变量（这里的变量一般是指不可变的变量，虽然绕口，但是确实是事实）
    let condition = true;
    ///从每个分支取得的if的返回值必须是同一类型，否则编译报错
    let number = if condition {
        5
    } else {
        6
    };

    println!("The value of number is: {}", number);

    ///循环
    loop {
        println!("again!");
        break;//这个分号可省
    }
    ///从循环中返回值
    let mut counter = 0;
    //循环赋值给变量
    let result = loop {
        counter += 1;
        if counter == 10 {
            break counter * 2;
        };//这个分号可省
    };

    ///分号的使用还不清晰明确，后面再看
    ///暂时理解为，赋值给变量的代码块需要使用分号短句，不赋值可以不用分号，而表达式本身就是直接返回，使用分号反而不行。（return显示指定返回值）
    println!("The result is {}", result);

    ///while循环
    let mut number = 3;
    ///使用while可以减少大量的if else break
    while number != 0 {
        println!("{}!", number);
        number -= 1;
    };//这个分号可以省略
    println!("LIFTOFF!!!");

    ///while变量数组
    let a = [10, 20, 30, 40, 50];
    let mut index = 0;

    while index < 5 {
        println!("the value is: {}", a[index]);

        index += 1;
    }

    ///使用for循环更加简单
    ///rust常用for，因为rust不会有迭代器失效的问题
    let a = [10, 20, 30, 40, 50];
    for element in a.iter() {
        println!("the value is: {}", element);
    }

    ///使用倒数
    for number in (1..4).rev() {
        //输出3!2!1!LIFTOFF!!!  print是没有换行的，与其他语言一致
        print!("{}!", number);
    }
    println!("LIFTOFF!!!");
}