///元祖、数组、切片
pub fn primitives() {
    tuples();
    arrays_and_slices();
}

fn tuples() {
    // 元组可以用作函数参数和返回值
    fn reverse(pair: (i32, bool)) -> (bool, i32) {
        // `let`可用于将元组的成员绑定到变量
        let (integer, boolean) = pair;

        (boolean, integer)
    }

    #[derive(Debug)]
    struct Matrix(f32, f32, f32, f32);

    //一组不同类型的元组
    let long_tuple = (1u8, 2u16, 3u32, 4u64, -1i8, -2i16, -3i32, -4i64, 0.1f32, 0.2f64, 'a', true);

    //可以使用元组索引从元组中提取值
    println!("long tuple first value: {}", long_tuple.0);
    println!("long tuple second value: {}", long_tuple.1);

    //元组可以是元组成员
    let tuple_of_tuples = ((1u8, 2u16, 2u32), (4u64, -1i8), -2i16);

    //元组是可打印的
    println!("tuple of tuples: {:?}", tuple_of_tuples);

    //但长元组无法打印
    // let too_long_tuple = (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
    // println!("too long tuple: {:?}", too_long_tuple);
    // TODO ^ Uncomment the above 2 lines to see the compiler error

    let pair = (1, true);
    println!("pair is {:?}", pair);

    println!("the reversed pair is {:?}", reverse(pair));

    //要创建含有一个元素的元组，需要逗号来区分它们
    // from a literal surrounded by parentheses
    println!("one element tuple: {:?}", (5u32, ));
    println!("just an integer: {:?}", (5u32));

    //元组可以被解构以创建绑定
    let tuple = (1, "hello", 4.5, true);

    let (a, b, c, d) = tuple;
    println!("{:?}, {:?}, {:?}, {:?}", a, b, c, d);

    let matrix = Matrix(1.1, 1.2, 2.1, 2.2);
    println!("{:?}", matrix);
}

fn arrays_and_slices() {
    use std::mem;

    //此函数借用一个切片
    fn analyze_slice(slice: &[i32]) {
        println!("first element of the slice: {}", slice[0]);
        println!("the slice has {} elements", slice.len());
    }

    //固定大小数组（类型签名是多余的）
    let xs: [i32; 5] = [1, 2, 3, 4, 5];

    //所有元素都可以初始化为相同的值
    let ys: [i32; 500] = [0; 500];

    // 索引从0开始
    println!("first element of the array: {}", xs[0]);
    println!("second element of the array: {}", xs[1]);

    //`len`返回数组的大小
    println!("array size: {}", xs.len());

    //数组是堆栈分配的
    println!("array occupies {} bytes", mem::size_of_val(&xs));

    //数组可以自动作为切片借用
    println!("borrow the whole array as a slice");
    analyze_slice(&xs);

    //切片可以指向数组的一个部分，其形式为[起始索引..结束索引]
    println!("borrow a section of the array as a slice");
    analyze_slice(&ys[1..4]);

    //索引越界导致编译错误
    //println!("{}", xs[5]);
}