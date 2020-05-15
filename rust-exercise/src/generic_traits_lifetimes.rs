use std::fmt::{Debug, Display};

///通用类型，特征和寿命

pub fn largest_function() {
    //在数字列表中查找最大数字的代码
    let number_list = vec![34, 50, 25, 100, 65];
    let mut largest = number_list[0];
    for number in number_list {
        if number > largest {
            largest = number;
        }
    }
    println!("The largest number is {}", largest);

    ///使用通用函数代码
    let number_list = vec![34, 50, 25, 100, 65];
    let result = largest1(&number_list);
    println!("The largest number is {}", result);
    let number_list = vec![102, 34, 6000, 89, 54, 2, 43, 8];
    let result = largest1(&number_list);
    println!("The largest number is {}", result);

    ///使用更加通用的函数代码（泛型）
    let number_list = vec![34, 50, 25, 100, 65];
    let result = largest2(&number_list);
    println!("The largest number is {}", result);
    let char_list = vec!['y', 'm', 'a', 'q'];
    let result = largest2(&char_list);
    println!("The largest char is {}", result);
}

///提取通用逻辑（此时类型都是i32，还可以进一步抽象为通用）
fn largest1(list: &[i32]) -> i32 {
    let mut largest = list[0];
    for &item in list.iter() {
        if item > largest {
            largest = item;
        }
    }
    largest
}

///使用泛型并且限定泛型需要特质
fn largest2<T: PartialOrd + Copy>(list: &[T]) -> T {
    let mut largest = list[0];
    for &item in list.iter() {
        if item > largest {
            largest = item;
        }
    }
    largest
}

///在结构中使用泛型
fn struct_generic() {
    struct Point<T> {
        x: T,
        //由此可见，x/y它们具有相同的通用数据类型T
        y: T,
    }

    let integer = Point { x: 5, y: 10 };
    let float = Point { x: 1.0, y: 4.0 };

    struct Point2<T, U> {
        x: T,
        //x/y类型不同
        y: U,
    }
    let both_integer = Point2 { x: 5, y: 10 };
    let both_float = Point2 { x: 1.0, y: 4.0 };
    let integer_and_float = Point2 { x: 5, y: 4.0 };
}

///在枚举和方法中定义
pub fn enum_generic() {
    enum Option<T> {
        Some(T),
        None,
    }
    struct Point<T> {
        x: T,
        y: T,
    }
    impl<T> Point<T> {
        fn x(&self) -> &T {
            &self.x
        }
    }
    let p = Point { x: 5, y: 10 };
    println!("p.x = {}", p.x());

    ///这意味着后面不能再实现任何其他类型impl
    impl Point<f32> {
        fn distance_from_origin(&self) -> f32 {
            (self.x.powi(2) + self.y.powi(2)).sqrt()
        }
    }
    struct Point2<T, U> {
        x: T,
        y: U,
    }

    ///多种泛型，方法自带了V W
    impl<T, U> Point2<T, U> {
        //V W仅与方法相关，T U是该结构的通用泛型
        fn mixup<V, W>(self, other: Point2<V, W>) -> Point2<T, W> {
            Point2 {
                x: self.x,
                y: other.y,
            }
        }
    }
    let p1 = Point2 { x: 5, y: 10.4 };
    let p2 = Point2 { x: "Hello", y: 'c' };
    let p3 = p1.mixup(p2);
    println!("p3.x = {}, p3.y = {}", p3.x, p3.y);

    //Rust通过在编译时对使用泛型的代码进行单态化来实现这一点。单色化是通过填充编译时使用的具体类型，将通用代码转换为特定代码的过程。
    //所以rust泛型高效
}

///特性：定义共同的行为
pub fn trait_function() {
    ///定义一个特质
    pub trait Summary {
        fn summarize(&self) -> String;
    }

    pub struct NewsArticle {
        pub headline: String,
        pub location: String,
        pub author: String,
        pub content: String,
    }
    ///在NewsArticle类型上，实现特质Summary
    impl Summary for NewsArticle {
        fn summarize(&self) -> String {
            format!("{}, by {} ({})", self.headline, self.author, self.location)
        }
    }
    pub struct Tweet {
        pub username: String,
        pub content: String,
        pub reply: bool,
        pub retweet: bool,
    }
    ///在Tweet类型上，实现特质Summary
    impl Summary for Tweet {
        fn summarize(&self) -> String {
            format!("{}: {}", self.username, self.content)
        }
    }

    ///使用特质Summary的summarize方法
    let tweet = Tweet {
        username: String::from("horse_ebooks"),
        content: String::from("of course, as you probably already know, people"),
        reply: false,
        retweet: false,
    };
    println!("1 new tweet: {}", tweet.summarize());

    //只有特征或类型在板条箱中是本地的时，我们才能对类型实现特征
    //此规则可确保其他人的代码不会破坏您的代码，反之亦然。
    //孤立规则(滑稽，翻译的)：如果没有该规则，则两个包装箱可能会针对同一类型实现相同的特征，而Rust不会知道要使用哪种实现

    ///使用特质的默认使用：impl Summary for NewsArticle {}。

    ///使用特质作为参数
    pub fn notify(item: impl Summary) {
        println!("Breaking news! {}", item.summarize());
    }

    ///特质绑定语法，与上面的效果类似但不等价
    pub fn notify2<T: Summary>(item: T) {
        println!("Breaking news! {}", item.summarize());
    }

    ///使用impl Summary作为参数会更加方便，此时只要求item1和item2参数实现了Summary，而不要求他们类型完全一致
    pub fn notify3(item1: impl Summary, item2: impl Summary) {
        println!("Breaking news! {}", item1.summarize());
    }

    ///需要强制让item1和item2的类型是一致的，则必须要使用特质绑定
    pub fn notify4<T: Summary>(item1: T, item2: T) {
        println!("Breaking news! {}", item1.summarize());
    }

    ///使用+，限制参数必须同时实现多个特质
    pub fn notify5(item: impl Summary + Display) {
        println!("Breaking news! {}", item.summarize());
    }

    ///使用特质绑定，限定T必须实现了两个特质
    pub fn notify6<T: Summary + Display>(item: T) {
        println!("Breaking news! {}", item.summarize());
    }

    ///这样写太麻烦
    fn some_function<T: Display + Clone, U: Clone + Debug>(t: T, u: U) -> i32 {
        1
    }
    //简化
    fn some_function2<T, U>(t: T, u: U) -> i32
    where
        T: Display + Clone,
        U: Clone + Debug,
    {
        1
    }

    ///返回实现特征的类型
    fn returns_summarizable() -> impl Summary {
        Tweet {
            username: String::from("horse_ebooks"),
            content: String::from("of course, as you probably already know, people"),
            reply: false,
            retweet: false,
        }
    }
    //下面是无效的，编译不过
    //由于在编译器中实现impl Trait语法方面的限制，因此不允许返回NewsArticle或Tweet
    //    fn returns_summarizable2(switch: bool) -> impl Summary {
    //        if switch {
    //            NewsArticle {
    //                headline: String::from("Penguins win the Stanley Cup Championship!"),
    //                location: String::from("Pittsburgh, PA, USA"),
    //                author: String::from("Iceburgh"),
    //                content: String::from("The Pittsburgh Penguins once again are the best
    //            hockey team in the NHL."),
    //            }
    //        } else {
    //            Tweet {
    //                username: String::from("horse_ebooks"),
    //                content: String::from("of course, as you probably already know, people"),
    //                reply: false,
    //                retweet: false,
    //            }
    //        }
    //    }

    struct Pair<T> {
        x: T,
        y: T,
    }
    impl<T> Pair<T> {
        fn new(x: T, y: T) -> Self {
            Self { x, y }
        }
    }
    ///始终实现new函数，但是Pair<T>仅在内部类型T实现了实现比较的PartialOrd特质和实现打印的Display特质的情况下，才实现cmp_display方法。
    ///根据特质范围有条件地在泛型类型上实现方法
    impl<T: Display + PartialOrd> Pair<T> {
        fn cmp_display(&self) {
            if self.x >= self.y {
                println!("The largest member is x = {}", self.x);
            } else {
                println!("The largest member is y = {}", self.y);
            }
        }
    }
}

///生命周期
fn lifetimes_function() {
    //使用生命周期验证引用
    //在大多数情况下，生存期是隐式和推断的，就像在大多数情况下一样，推断类型。
    //当可能有多个类型时，必须注释类型。以类似的方式，当引用的生存期可以通过几种不同方式关联时，我们必须注释生存期。
    //Rust要求我们使用通用生命周期参数注释关系，以确保在运行时使用的实际引用绝对有效。
    //生命周期的主要目的是防止引用悬而未决，从而导致程序引用的数据不是其要引用的数据。
    //    {
    //        r和x的生命周期注释，分别命名为'a和'b
    //        let r;                // ---------+-- 'a
    //          |
    //        {                     //          |
    //            let x = 5;        // -+-- 'b  |
    //            r = &x;           //  |       |
    //        }                     // -+       |
    //          |
    //        println!("r: {}", r); //          |
    //    }                         // ----------+

    ///正确代码
    {
        let x = 5; // ----------+-- 'b
                   //           |
        let r = &x; // --+-- 'a  |
                    //   |       |
        println!("r: {}", r); //   |       |
                              // --+       |
    } // ----------+

    ///函数的通用生命周期
    ///过于复杂，建议有时间再研究：https://doc.rust-lang.org/book/ch10-03-lifetime-syntax.html
    //    &i32        // 一个引用
    //    &'a i32     // 具有明确生命周期的引用
    //    &'a mut i32 // 具有显式寿命的可变引用
    ///指定签名中的所有引用必须具有相同的生存期 'a
    fn longest<'a>(x: &'a str, y: &'a str) -> &'a str {
        if x.len() > y.len() {
            x
        } else {
            y
        }
    }
}
