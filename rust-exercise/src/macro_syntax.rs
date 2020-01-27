/// 标准库的定义
///#[cfg(not(test))]
///#[macro_export]
///#[stable(feature = "rust1", since = "1.0.0")]
///#[allow_internal_unstable(box_syntax)]
///macro_rules! vec {
///    ($elem:expr; $n:expr) => (
///        $crate::vec::from_elem($elem, $n)
///    );
///    ($($x:expr),*) => (
///        <[_]>::into_vec(box [$($x),*])
///    );
///    ($($x:expr,)*) => ($crate::vec![$($x),*])
///}
///
///
#[macro_export]//＃[macro_export]注解（注释）表示，只要将定义了该宏的板条箱放入范围内，就应使该宏可用。没有此注释，宏将无法进入范围。
macro_rules! Vec {//然后，我们从macro_rules! 开始宏定义！以及我们定义的不带感叹号的宏的名称。该名称（在本例中为Vec）后跟大括号，表示宏定义的正文。
    ( $( $x:expr ),* ) => {//模式与代码块，宏模式是针对Rust代码结构而非值进行匹配的
    //首先一组括号()包括整个模式，这些括号捕获与括号内的模式匹配的值，以供替换代码使用
    //$()中是$x:expr，它与任何Rust表达式匹配，并为表达式指定名称$x
    //*指定该模式与*之前的零个或多个匹配
        {
            let mut temp_vec = Vec::new();
            //$()* 表示匹配0次或多次（等价为每个匹配到的表达式执行下面操作），$x表示匹配上的表达式（用户传进来的）
            $(
                temp_vec.push($x);
            )*
            temp_vec//最终返回集合
        }
    };
}

pub fn marco_function() {
    ///定义自己的宏。（标准库的是vec!）
    let v: Vec<u32> = Vec![1, 2, 3];// $x 匹配 1,2,3 三次
    println!("{}", v.len())//宏定义必须在前面
}