///变量 表达式
pub fn variables_function() {
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

    ///阴影允许定义变量与前面重名，前者被遮蔽
    ///mut与shadowing区别：后者将创建一个新的变量，因此可以改变类型，使用相同的名称，常见用法如下：
    //let spaces = "   ";
    //let spaces = spaces.len();//使用相同名称但类型已经发生变化
    ///但是对于mut则不能，spaces虽然是可变的，但是类型是字符串类型的
    //let mut spaces = "   ";
    //spaces = spaces.len();
    let i = 5;
    let i = x + 1;
    let i = x * 2;

    println!("The value of x is: {}", i);
}

pub fn try_change_function() {
    ///必须都是mut的，否则编译就会报错，不可变，无法被改变
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
    //在此之后不再能使用r1和r2

    let r3 = &mut s; // 没问题，因为r1 r2进入println! 并且在此之后会失效，与所有权有关。
    println!("{}", r3);
}

pub fn expr_function() {

    //赋值需要返回值，rust语句没有返回值，不同于其他语言赋值可以连用
    // let x = (let y = 6);

    let x = 5;

    let y = {
        let x = 3;
        x + 1 //返回x+1，且不能用分号，有分号表示这个是语句，没有返回值，无法赋值给y
    };

    println!("The value of y is: {}", y);
}