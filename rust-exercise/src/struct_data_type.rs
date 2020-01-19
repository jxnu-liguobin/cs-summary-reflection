///结构体
pub fn struct_data_type() {
    struct User {
        username: String,
        email: String,
        sign_in_count: u64,
        active: bool,
    }

    let mut user1 = User { //必须定义为可变的才能修改结构体的内容
        email: String::from("someone@example.com"),
        username: String::from("someusername123"),
        active: true,
        sign_in_count: 1,
    };

    user1.email = String::from("anotheremail@example.com");

    //对应参数名和结构体属性名称相同的，可以省略以减少代码
    fn build_user(email: String, username: String) -> User {
        User {
            email: email,
            username: username,
            active: true,
            sign_in_count: 1,
        }
    }

    //更好的方法是省略参数名，结构体没有顺序要求，与元祖不同，元祖结构体：仅声明类型的结构体
    fn build_user_better(email: String, username: String) -> User {
        User {
            email,
            username,
            active: true,
            sign_in_count: 1,
        }
    }

    //创建一个新的结构体user2，并使用user1的某些值
    let user2 = User {
        email: String::from("another@example.com"),
        username: String::from("anotherusername567"),
        active: user1.active,
        sign_in_count: user1.sign_in_count,
    };

    //更好的方式是使用 ..语法，其余字段应与给定实例（user1）中的字段具有相同的值
    let user2 = User {
        email: String::from("another@example.com"),
        username: String::from("anotherusername567"),
        ..user1
    };

    //元祖结构体，没有命名属性字段，仅有类型声明
    struct Color(i32, i32, i32);
    struct Point(i32, i32, i32);

    let black = Color(0, 0, 0);
    let origin = Point(0, 0, 0);

    //此结构体存储切片。而上面的结构体存储的是String类型，下面代码编译会报错，因为使用切片时需要指定生命周期。与使用String拥有所有权不同,&str没有获取所有权而只是借用
    //    struct User2 {
    //        username: &str,
    //        email: &str,
    //        sign_in_count: u64,
    //        active: bool,
    //    }

    let width1 = 30;
    let height1 = 50;

    fn area(width: u32, height: u32) -> u32 {
        width * height
    }

    println!("The area of the rectangle is {} square pixels.", area(width1, height1));

    //使用元祖
    fn area_tuple(dimensions: (u32, u32)) -> u32 {
        dimensions.0 * dimensions.1
    }

    let rect1 = (30, 50);

    println!(
        "The area of the rectangle is {} square pixels.",
        area_tuple(rect1)
    );

    //使用结构体赋予更多含义
    #[derive(Debug)] //使得该结构体能在println!中被打印
    struct Rectangle {
        width: u32,
        height: u32,
    }

    fn area_struct(rectangle: &Rectangle) -> u32 {
        rectangle.width * rectangle.height
    }

    let rect1 = Rectangle { width: 30, height: 50 };
    println!(
        "The area of the rectangle is {} square pixels.",
        area_struct(&rect1)
    );

    //这将会报错，因为该结构体不支持显示：`Rectangle` doesn't implement `std::fmt::Display`
    println!("rect1 is {:#?}", rect1);//{:?}使用调试模式打印也会报错：`Rectangle` doesn't implement `std::fmt::Debug`,{:#?} 格式化打印
}