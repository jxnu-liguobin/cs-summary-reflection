///枚举类型
pub fn enum_data_type() {
    ///定义枚举类型
    enum IpAddrKind {
        V4,
        V6,
    }

    let four = IpAddrKind::V4;
    let six = IpAddrKind::V6;

    fn route(ip_kind: IpAddrKind) {}

    ///两个值IpAddrKind::V4和IpAddrKind::V6都具有相同的类型: IpAddrKind
    route(IpAddrKind::V4);
    route(IpAddrKind::V6);

    struct IpAddr {
        //在结构体中使用枚举
        kind: IpAddrKind,
        address: String,
    }

    let home = IpAddr {
        kind: IpAddrKind::V4,
        address: String::from("127.0.0.1"),
    };

    let loopback = IpAddr {
        kind: IpAddrKind::V6,
        address: String::from("::1"),
    };

    ///数值直接放入每个枚举变量中，而不是需要使用结构体 struct IpAddr
    enum IpAddr2 {
        V4(String),
        V6(String),
    }

    let home = IpAddr2::V4(String::from("127.0.0.1"));
    let loopback = IpAddr2::V6(String::from("::1"));

    ///将枚举数值定义为不同类型，此时struct IpAddr无法实现
    enum IpAddr3 {
        V4(u8, u8, u8, u8),
        V6(String),
    }

    let home = IpAddr3::V4(127, 0, 0, 1);

    let loopback = IpAddr3::V6(String::from("::1"));

    ///使用枚举
    enum Message {
        Quit,
        Move { x: i32, y: i32 },
        Write(String),
        ChangeColor(i32, i32, i32),
    }

    ///使用结构体
    struct QuitMessage; // 单位结构
    struct MoveMessage {
        x: i32,
        y: i32,
    }
    struct WriteMessage(String); // 元祖结构
    struct ChangeColorMessage(i32, i32, i32); // 元祖结构

    ///定义一个impl即可对所有枚举值生效，他们都能调用call
    impl Message {
        fn call(&self) {
            // do something
        }
    }

    let m = Message::Write(String::from("hello"));
    m.call();

    ///Option类型
    let some_number = Some(5);
    let some_string = Some("a string");
    //为None时需要指定类型，否则无法推断出类型
    let absent_number: Option<i32> = None;

    let x: i8 = 5;
    let y: Option<i8> = Some(5);
    //Option<i8> 与 i8 是不同的类型  <>是泛型，Option<T>表示任意的类型都可以放进Option
    //let sum = x + y;
}
