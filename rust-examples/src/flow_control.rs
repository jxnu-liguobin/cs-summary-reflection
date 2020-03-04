///流程控制

pub fn flow_control() {
    if_else();
    loop_while();
    for_range();
    match_case();
    match_tuples();
    match_enums();
    match_point();
    match_struct();
    match_guards();
    match_binding();
    if_let();
    while_let();
}

fn if_else() {
    let n = 5;
    if n < 0 {
        print!("{} is negative", n);
    } else if n > 0 {
        print!("{} is positive", n);
    } else {
        print!("{} is zero", n);
    }

    let big_n = if n < 10 && n > -10 {
        println!(", and is a small number, increase ten-fold");
        10 * n
    } else {
        println!(", and is a big number, halve the number");
        n / 2
    };//别忘了在这里加分号！所有“let”绑定都需要它

    println!("{} -> {}", n, big_n);
}

fn loop_while() {
    //loop循环
    let mut count = 0u32;

    println!("Let's count until infinity!");
    loop {
        count += 1;
        if count == 3 {
            println!("three");
            continue;
        }
        println!("{}", count);
        if count == 5 {
            println!("OK, that's enough");
            break;
        }
    }

    //loop嵌套
    'outer: loop {
        println!("Entered the outer loop");
        'inner: loop {
            println!("Entered the inner loop");
            break 'outer;
        }
        println!("This point will never be reached");//永远不会被执行，到break 'outer;就会直接退出外层循环
    }

    println!("Exited the outer loop");


    //从loop返回值
    let mut counter = 0;

    let result = loop {
        counter += 1;

        if counter == 10 {
            break counter * 2;
        }
    };

    assert_eq!(result, 20);

    //while循环
    let mut n = 1;

    while n < 101 {
        if n % 15 == 0 {
            println!("fizzbuzz");
        } else if n % 3 == 0 {
            println!("fizz");
        } else if n % 5 == 0 {
            println!("buzz");
        } else {
            println!("{}", n);
        }
        n += 1;
    }
}

fn for_range() {
    //`n` = 1, 2, ..., 100
    for n in 1..101 {//包含左，不含右，Scala的until不包含右界，to包含右界
        if n % 15 == 0 {
            println!("fizzbuzz");
        } else if n % 3 == 0 {
            println!("fizz");
        } else if n % 5 == 0 {
            println!("buzz");
        } else {
            println!("{}", n);
        }
    }

    //`n` =  1, 2, ..., 100
    for n in 1..=100 {//包含右界
        if n % 15 == 0 {
            println!("fizzbuzz");
        } else if n % 3 == 0 {
            println!("fizz");
        } else if n % 5 == 0 {
            println!("buzz");
        } else {
            println!("{}", n);
        }
    }

    ///迭代器
    let names = vec!["Bob", "Frank", "Ferris"];
    for name in names.iter() {//将在每次迭代中借用集合的每个元素。因此，在循环之后，该集合将保持不变并可供重用。
        match name {
            &"Ferris" => println!("There is a rustacean among us!"),
            _ => println!("Hello {}", name),
        }
    }

    let names = vec!["Bob", "Frank", "Ferris"];
    for name in names.into_iter() {//消耗集合，以便在每次迭代时提供准确的数据。一旦使用完了集合，就不再可以重复使用，因为它已在循环中“移动”。
        match name {
            "Ferris" => println!("There is a rustacean among us!"),
            _ => println!("Hello {}", name),
        }
    }

    let mut names = vec!["Bob", "Frank", "Ferris"];
    for name in names.iter_mut() {//可变地借用了集合的每个元素，从而允许在适当的位置修改集合。
        *name = match name {
            &mut "Ferris" => "There is a rustacean among us!",
            _ => "Hello",
        }
    }

    println!("names: {:?}", names);
}

fn match_case() {
    let number = 13;
    println!("Tell me about {}", number);
    match number {
        1 => println!("One!"),//仅匹配1
        2 | 3 | 5 | 7 | 11 => println!("This is a prime"),//匹配2或3或5或7或11
        13..=19 => println!("A teen"),//匹配13到19的数字
        _ => println!("Ain't special"),//其他
    }

    let boolean = true;
    //match返回表达式
    let binary = match boolean {
        false => 0,
        true => 1,
    };

    println!("{} -> {}", boolean, binary);
}

fn match_tuples() {
    //match解构元祖
    let pair = (0, -2);
    println!("Tell me about {:?}", pair);
    match pair {
        (0, y) => println!("First is `0` and `y` is `{:?}`", y),
        (x, 0) => println!("`x` is `{:?}` and last is `0`", x),
        _ => println!("It doesn't matter what they are"),
    }
}

fn match_enums() {
    //match解构枚举
    enum Color {
        Red,
        Blue,
        Green,
        RGB(u32, u32, u32),
        HSV(u32, u32, u32),
        HSL(u32, u32, u32),
        CMY(u32, u32, u32),
        CMYK(u32, u32, u32, u32),
    }

    let color = Color::RGB(122, 17, 40);
    println!("What color is it?");
    match color {
        Color::Red => println!("The color is Red!"),
        Color::Blue => println!("The color is Blue!"),
        Color::Green => println!("The color is Green!"),
        Color::RGB(r, g, b) =>
            println!("Red: {}, green: {}, and blue: {}!", r, g, b),
        Color::HSV(h, s, v) =>
            println!("Hue: {}, saturation: {}, value: {}!", h, s, v),
        Color::HSL(h, s, l) =>
            println!("Hue: {}, saturation: {}, lightness: {}!", h, s, l),
        Color::CMY(c, m, y) =>
            println!("Cyan: {}, magenta: {}, yellow: {}!", c, m, y),
        Color::CMYK(c, m, y, k) =>
            println!("Cyan: {}, magenta: {}, yellow: {}, key (black): {}!",
                     c, m, y, k),
    }
}

fn match_point() {
    //取消引用使用*
    //解构使用&, ref, and ref mut
    let reference = &4;
    match reference {
        &val => println!("Got a value via destructuring: {:?}", val),
    }
    match *reference {
        val => println!("Got a value via dereferencing: {:?}", val),
    }
    let _not_a_reference = 3;
    let ref _is_a_reference = 3;
    let value = 5;
    let mut mut_value = 6;
    match value {
        ref r => println!("Got a reference to a value: {:?}", r),
    }

    match mut_value {
        ref mut m => {
            *m += 10;
            println!("We added 10. `mut_value`: {:?}", m);
        }
    }
}

fn match_struct() {
    //解构结构体
    struct Foo {
        x: (u32, u32),
        y: u32,
    }
    let foo = Foo { x: (1, 2), y: 3 };
    match foo {
        Foo { x: (1, b), y } => println!("First of x is 1, b = {},  y = {} ", b, y),
        Foo { y: 2, x: i } => println!("y is 2, i = {:?}", i),
        Foo { y, .. } => println!("y = {}, we don't care about x", y),
    }
}

fn match_guards() {
    //类似Scala的match case中可以使用if过滤
    let pair = (2, -2);
    println!("Tell me about {:?}", pair);
    match pair {
        (x, y) if x == y => println!("These are twins"),
        (x, y) if x + y == 0 => println!("Antimatter, kaboom!"),
        (x, _) if x % 2 == 1 => println!("The first one is odd"),
        _ => println!("No correlation..."),
    }
}

fn match_binding() {
    //类似Scala match case @
    fn age() -> u32 {
        15
    }

    println!("Tell me what type of person you are");
    match age() {
        0 => println!("I'm not born yet I guess"),
        n @ 1..=12 => println!("I'm a child of age {:?}", n),
        n @ 13..=19 => println!("I'm a teen of age {:?}", n),
        n => println!("I'm an old person of age {:?}", n),
    }

    fn some_number() -> Option<u32> {
        Some(42)
    }

    match some_number() {
        //是Some且n=42
        Some(n @ 42) => println!("The Answer: {}!", n),
        Some(n) => println!("Not interesting... {}", n),
        _ => (),
    }
}

fn if_let() {
    //if let为了避免写无用的代码 _ => {}
    let optional = Some(7);
    match optional {
        Some(i) => {
            println!("This is a really long string and `{:?}`", i);
        }
        _ => {}
    };

    //如下
    let number = Some(7);
    let letter: Option<i32> = None;
    let emoticon: Option<i32> = None;
    if let Some(i) = number {
        println!("Matched {:?}!", i);
    }
    if let Some(i) = letter {
        println!("Matched {:?}!", i);
    } else {
        println!("Didn't match a number. Let's go with a letter!");
    }

    let i_like_letters = false;

    if let Some(i) = emoticon {
        println!("Matched {:?}!", i);
    } else if i_like_letters {//lf let没匹配上
        println!("Didn't match a number. Let's go with a letter!");
    } else {
        println!("I don't like letters. Let's go with an emoticon :)!");
    }

    ///匹配枚举
    enum Foo {
        Bar,
        Baz,
        Qux(u32),
    }

    let a = Foo::Bar;
    let b = Foo::Baz;
    let c = Foo::Qux(100);
    if let Foo::Bar = a {
        println!("a is foobar");
    }
    if let Foo::Bar = b {
        println!("b is foobar");
    }
    if let Foo::Qux(value) = c {
        println!("c is {}", value);
    }

    if let Foo::Qux(value @ 100) = c {
        println!("c is one hundred");
    }

    let a = Foo::Bar;
    if let Foo::Bar = a {//枚举没有注解#[derive(PartialEq)]，则无法比较，使用传统的if，则失败，编译不过
        println!("a is foobar");
    }
}

fn while_let() {
    //不使用while let
    let mut optional = Some(0);
    loop {
        match optional {
            Some(i) => {
                if i > 9 {
                    println!("Greater than 9, quit!");
                    optional = None;
                } else {
                    println!("`i` is `{:?}`. Try again.", i);
                    optional = Some(i + 1);
                }
            }
            _ => { break; }
        }
    }

    //使用while let
    let mut optional = Some(0);
    while let Some(i) = optional {
        if i > 9 {
            println!("Greater than 9, quit!");
            optional = None;
        } else {
            println!("`i` is `{:?}`. Try again.", i);
            optional = Some(i + 1);
        }
    }//while let 无子语句
}