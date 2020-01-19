///指针（引用）
pub fn empty_point_function() {

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

pub fn point_function() {
    fn calculate_length(s: &String) -> usize {
        s.len()
    }
    let s1 = String::from("hello");
    ///类似c/c++传递指针/引用
    let len = calculate_length(&s1);
    ///s1在之后还能使用
    println!("The length of '{}' is {}.", s1, len);
}

pub fn copy_function() {
    let x = 5;
    ///基本类型在移动时使用copy，x不会失效。
    let y = x;
    println!("x = {}, y = {}", x, y);

    ///使用clone克隆数据，目前先理解为深拷贝
    let s1 = String::from("hello");
    let s2 = s1.clone();

    println!("s1 = {}, s2 = {}", s1, s2);
}

pub fn tuple_function() {
    let s1 = String::from("hello");
    let (s2, len) = calculate_length(s1);
    println!("The length of '{}' is {}.", s2, len);
    fn calculate_length(s: String) -> (String, usize) {
        let length = s.len(); // len() 返回字符串的长度
        (s, length)
    }
}
