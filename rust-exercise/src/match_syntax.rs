///简洁的控制流语法
pub fn match_syntax2() {
    let some_u8_value = Some(0u8);
    match some_u8_value {
        Some(3) => println!("three"),
        _ => (), //这行是多余的样板代码
    }

    //使用if let 省略上面的样板代码
    if let Some(3) = some_u8_value {
        println!("three");
    }

    #[derive(Debug)]
    enum UsState {
        Alabama,
        Alaska,
    }
    enum Coin {
        Penny,
        Nickel,
        Dime,
        Quarter(UsState),
    }
    //使用if let简化代码
    fn value_in_cents(coin: Coin) -> u8 {
        let mut count = 0;
        if let Coin::Quarter(state) = coin {
            println!("State quarter from {:?}!", state);
        } else {
            count += 1;
        }
        1
    }
}

pub fn match_syntax() {
    ///枚举与match
    enum Coin {
        Penny,
        Nickel,
        Dime,
        Quarter,
    }

    fn value_in_cents(coin: Coin) -> u8 {
        match coin {
            Coin::Penny => 1,
            Coin::Nickel => 5,
            Coin::Dime => 10,
            Coin::Quarter => 25,
        }
    }

    fn value_in_cents2(coin: Coin) -> u8 {
        match coin {
            Coin::Penny => {
                ///代码多时需要使用花括号，并且最后一行返回值不加分号。大括号后面仍是逗号
                println!("Lucky penny!");
                1
            }
            Coin::Nickel => 5,
            Coin::Dime => 10,
            Coin::Quarter => 25,
        }
    }

    ///绑定到值的匹配
    #[derive(Debug)]
    enum UsState {
        Alabama,
        Alaska,
    }

    enum Coin2 {
        Penny,
        Nickel,
        Dime,
        Quarter(UsState),
    }

    fn value_in_cents3(coin: Coin2) -> u8 {
        match coin {
            Coin2::Penny => 1,
            Coin2::Nickel => 5,
            Coin2::Dime => 10,
            Coin2::Quarter(state) => {
                println!("State quarter from {:?}!", state);
                25
            }
        }
    }

    ///Option类型match
    fn plus_one(x: Option<i32>) -> Option<i32> {
        match x {
            //这行代码多余，但是又不能省略，否则编译报错。
            None => None,
            Some(i) => Some(i + 1),
        }
    }

    let five = Some(5);
    let six = plus_one(five);
    let none = plus_one(None);

    ///使用占位符，编译通过
    let some_u8_value = 0u8;
    match some_u8_value {
        1 => println!("one"),
        3 => println!("three"),
        5 => println!("five"),
        7 => println!("seven"),
        _ => (),
    }
}
