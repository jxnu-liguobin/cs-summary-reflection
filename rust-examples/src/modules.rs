///模块
pub fn modules() {
    struct_visibility();
    cfg();
}

fn struct_visibility() {
    mod my {
        //公有结构体和公有字段
        pub struct OpenBox<T> {
            pub contents: T,
        }

        //公有结构体和私有字段
        #[allow(dead_code)] //禁用编译器的 未使用警告
        pub struct ClosedBox<T> {
            contents: T,
        }

        impl<T> ClosedBox<T> {
            //公有的构造函数
            pub fn new(contents: T) -> ClosedBox<T> {
                ClosedBox { contents: contents }
            }
        }
    }

    let open_box = my::OpenBox {
        contents: "public information",
    };
    println!("The open box contains: {}", open_box.contents);

    //ERROR
    //let closed_box = my::ClosedBox { contents: "classified information" };

    //使用构造函数可以
    let _closed_box = my::ClosedBox::new("classified information");

    //ERROR
    //println!("The closed box contains: {}", _closed_box.contents);
}

fn use_declaration() {
    //指定别名
    // use deeply::nested::function as other_function;
    // other_function();

    //相对路径，父级和当前级
    //super 和 self
}

//extern crate rary; 导入library库

fn cfg() {
    // This function only gets compiled if the target OS is linux
    #[cfg(target_os = "linux")]
    fn are_you_on_linux() {
        println!("You are running linux!");
    }

    // And this function only gets compiled if the target OS is *not* linux
    #[cfg(not(target_os = "linux"))]
    fn are_you_on_linux2() {
        println!("You are *not* running linux!");
    }

    println!("Are you sure?");
    if cfg!(target_os = "linux") {
        //target_os由rustc隐式提供，但是自定义条件条件必须使用--cfg标志传递给rustc
        println!("Yes. It's definitely linux!");
    } else {
        println!("Yes. It's definitely *not* linux!");
    }
}
