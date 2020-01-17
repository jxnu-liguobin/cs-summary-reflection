//use crate::front_of_house::front_of_house.hosting;
//use front_of_house::front_of_house.hosting; //使用相对路径
//use crate::front_of_house::front_of_house.hosting::add_to_waitlist; //将函数直接导入，使用add_to_waitlist即可调用了
/// 使用cargo new --lib restaurant 命令创建新的
/// Cargo遵循一个约定，即src/main.rs是与包同名的二进制板条箱的板条箱根。（crate翻译为板条箱或者木箱？暂时不确定转义术语）
/// 同样，Cargo知道如果包目录包含src/lib.rs，则包包含与包同名的库板条箱，src/lib.rs是其板条箱根目录。Cargo将板条箱根文件传递给rustc以构建库或二进制文件。
/// crate是Rust 中的基本编译单元，可以被编译为可执行文件或库，一个crate是一个文件
pub mod front_of_house;

//以mod关键字开头定义模块，然后指定模块名称，内部可以继续定义mod，该模块在板条箱的模块结构的根目录下被命名为模块树。
//mod front_of_house {
//    //直接编译会报错，因为模块中所有的东西都是私有的，虽然使用pub公开路径，但是公开模块不会公开模块的内容，所以里面的函数仍是私有的。编译仍会报错
//    pub mod front_of_house.hosting {
//        //根据上述规则，需要在函数上加pub才能使得该函数被外部访问到
//        pub fn add_to_waitlist() {}
//
//        fn seat_at_table() {}
//    }
//
//    mod serving {
//        fn take_order() {}
//
//        fn serve_order() {}
//
//        fn take_payment() {}
//    }
//
//    //绝对路径 crate
//    //相对路径 self、super，后面均使用 ::
//}

//使用 pub公开模块的功能
pub fn eat_at_restaurant() {
    // 使用绝对路径，更好
//    crate::front_of_house::hosting::add_to_waitlist_host();
    // 使用相对路径
    front_of_house::hosting::add_to_waitlist_host();
}

fn serve_order() {}

mod back_of_house {
    fn fix_incorrect_order() {
        cook_order();
        //该模块的父模块，在这里实际是crate根，当这部分代码有在未来可能同时移动，则不需要更改代码
        super::serve_order();
    }

    fn cook_order() {}

    //公开结构体不会公开其所有的属性
    pub struct Breakfast {
        pub toast: String,
        //该结构是公开的，且只有toast是公开的属性字段
        seasonal_fruit: String,//无法更改
    }

    impl Breakfast {
        //需要使用关联函数生成 结构体Breakfast的示例，因为其字段有些是私有的，无法在外部生成
        pub fn summer(toast: &str) -> Breakfast {
            Breakfast {
                toast: String::from(toast),
                seasonal_fruit: String::from("peaches"),
            }
        }
    }

    //公开枚举将会公开枚举的所有值
    pub enum Appetizer {
        Soup,
        Salad,
    }
}


//mod front_of_house;//在mod front_of_house之后使用分号（而不是使用块）会告诉Rust从另一个与模块同名的文件中加载模块的内容。

//重新导出，使名称可用于新范围内的任何代码 （因为我们将一个项目放入范围内，同时也使该项目可供其他人进入其范围）
//use std::collections::*; //导入所有内部的类型

pub fn eat_at_restaurant2() {
    let mut meal = back_of_house::Breakfast::summer("Rye");//关联函数 summer 用于构造实例
    meal.toast = String::from("Wheat");
    println!("I'd like {} toast please", meal.toast);
    //下面编译不过，seasonal_fruit是私有的
    //meal.seasonal_fruit = String::from("blueberries");
    //使用公开的枚举类型
    let order1 = back_of_house::Appetizer::Soup;
    let order2 = back_of_house::Appetizer::Salad;

    //使用use导入模块，可以省略模块前缀，使代码简洁
    front_of_house::hosting::add_to_waitlist_host();//front_of_house :: hosting 二级包
    front_of_house::hosting::add_to_waitlist_host();
    front_of_house::hosting::add_to_waitlist_host();

    //use的习惯用法，但是不同模块中的相同函数，需要加模块前缀，如：fmt::Result，io::Result
    use std::collections::HashMap as RustHashMap;//使用as为导入的类型提供别名，一般在最前面使用导入，这里为了方便
    let mut map = RustHashMap::new();
    map.insert(1, 2);
}