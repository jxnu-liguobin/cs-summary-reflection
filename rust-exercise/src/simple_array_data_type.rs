///简单数据类型与数组，所有权
pub fn simple_array_data_type() {
    ///--release模式下，整数溢出将会变为最小值
    ///在u8(0-255)类型下，256变为0，257变为1，依此类推

    ///默认浮点类型是f64，相当于Java double，IEEE754标准
    let x = 2.0; // f64

    let y: f32 = 3.0; // f32

    ///数值运算，与其他语言相同，类型可以自动推断，不用指定类型
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
    ///显示指定类型
    let f: bool = false;

    ///字符类型，Unicode，4bytes
    let c = 'z';
    let z = 'ℤ';
    let heart_eyed_cat = '😻';

    ///元组类型，与Scala基本相同，可以推断出类型
    let tup: (i32, f64, u8) = (500, 6.4, 1);
    let tup = (500, 6.4, 1);
    ///提取出元组的每个值
    let (x, y, z) = tup;
    println!("The value of y is: {}", y);

    ///使用 .获取元组的值，从0下标开始
    let five_hundred = tup.0;
    let six_point_four = tup.1;
    let one = tup.2;

    ///数组类型，一般在只有固定元素个数的时候使用
    let array = [1, 2, 3, 4, 5];

    ///初始化数组的第二种方法
    let a: [i32; 5] = [1, 2, 3, 4, 5];

    ///等价于let a = [3, 3, 3, 3, 3];，意为5个3构成的数组
    let a = [3; 5];

    ///访问数组，同样是从0下标开始
    let first = a[0];
    let second = a[1];

    ///Rust通过立即退出而不是允许内存访问并继续操作来保护您免受此类错误的侵害
    let element = a[0]; //若下标大于数组索引则运行时检查并报错退出"error: index out of bounds: the len is 5 but the index is 10"
}

///rust String比较复杂
pub fn string_function() {
    let mut s = String::from("hello");
    s.push_str(", world!"); // push_str() 将文字附加到字符串

    println!("{}", s); //打印 hello, world!

    let s = String::from("hello"); // s进入范围

    takes_ownership(s); // s的值移动到函数，所以在这里不再有效
                        //println!("{}", s);//编译错误：value borrowed here after move。出借后的s被移动，后续不可用

    let x = 5; // x进入范围
    makes_copy(x); // x将移动到函数
                   //但是i32是Copy，所以之后还可以使用
    println!("{}", x); //正常打印

    fn takes_ownership(some_string: String) {
        println!("{}", some_string);
    } //在这里，some_string超出范围并调用`drop`。内存释放

    fn makes_copy(some_integer: i32) {
        println!("{}", some_integer);
    }
}
