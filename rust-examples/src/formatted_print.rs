use std::fmt::{Display, Error, Formatter};
use std::fmt;

//println宏的使用
pub fn formatted_print() {
    println!("Hello, world!");
    println!("{} days", 31);
    println!("{0}, this is {1}. {1}, this is {0}", "Alice", "Bob");
    //命名参数
    println!("{subject} {verb} {object}", object = "the lazy dog", subject = "the quick brown fox", verb = "jumps over");
    // 在冒号后指定特殊输出格式
    println!("{} of {:b} people know binary, the other half doesn't", 1, 2);
    //右对齐，指定宽度6
    println!("{number:>width$}", number = 1, width = 6);
    //右对齐，宽度6，使用0填错
    println!("{number:>0width$}", number = 1, width = 6);

    #[derive(Debug)]
    struct Structure(i32);
    #[derive(Debug)]
    struct Deep(Structure);

    println!("Now {:?} will print!", Structure(3));
    println!("Now {:?} will print!", Deep(Structure(7)));

    #[derive(Debug)]
    struct Person<'a> {
        name: &'a str,
        age: u8,
    }

    //更好看
    let peter = Person { name: "Peter", age: 27 };
    println!("{:#?}", peter);

    //自己实现fmt::Display或Debug特质


    #[derive(Debug)]
    struct MinMax(i64, i64);

    impl fmt::Display for MinMax {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            write!(f, "({}, {})", self.0, self.1)
        }
    }

    //#[derive(Debug)],不使用默认的Debug
    struct Point2D {
        x: f64,
        y: f64,
    }

    //实现自己的Display
    impl fmt::Display for Point2D {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            write!(f, "{} + {}", self.x, self.y)
        }
    }

    //实现自己的Debug
    impl fmt::Debug for Point2D {
        fn fmt(&self, f: &mut Formatter<'_>) -> Result<(), Error> {
            write!(f, "Complex {} real: {}, imag: {} {}", "{", self.x, self.y, "}")
        }
    }

    let minmax = MinMax(0, 14);
    println!("Compare structures:");
    println!("Display: {}", minmax);
    println!("Debug: {:?}", minmax);
    let point = Point2D { x: 3.3, y: 7.2 };
    println!("Compare points:");
    println!("Display: {}", point);
    println!("Debug: {:?}", point);
    println!("Debug: {:?}", point);


    //实现打印集合
    struct List(Vec<i32>);

    impl fmt::Display for List {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            let vec = &self.0;
            write!(f, "[")?;
            for (count, v) in vec.iter().enumerate() {
                if count != 0 { write!(f, ", ")?; }
                write!(f, "{}: {}", count, v)?;//打印索引和数据
            }
            write!(f, "]")
        }
    }

    let v = List(vec![1, 2, 3]);
    println!("{}", v);


    struct City {
        name: &'static str,
        lat: f32,
        lon: f32,
    }

    impl Display for City {
        fn fmt(&self, f: &mut Formatter) -> fmt::Result {
            let lat_c = if self.lat >= 0.0 { 'N' } else { 'S' };
            let lon_c = if self.lon >= 0.0 { 'E' } else { 'W' };
            write!(f, "{}: {:.3}°{} {:.3}°{}", self.name, self.lat.abs(), lat_c, self.lon.abs(), lon_c)
        }
    }

    //#[derive(Debug)]
    struct Color {
        red: u8,
        green: u8,
        blue: u8,
    }
    //RGB (128, 255, 90) 0x80FF5A
    //RGB (0, 3, 254) 0x0030FE
    //RGB (0, 0, 0) 0x000000
    ///https://doc.rust-lang.org/std/fmt/#width
    impl Display for Color {
        fn fmt(&self, f: &mut Formatter<'_>) -> Result<(), Error> {
            //format!("{}", foo) -> "3735928559"
            //format!("0x{:X}", foo) -> "0xDEADBEEF"
            //format!("0o{:o}", foo) -> "0o33653337357"
            write!(f, "RGB ({}, {}, {}) 0x{:0>2X}{:0>2X}{:0>2X} ", self.red, self.green, self.blue, self.red, self.green, self.blue)
            //右对齐，补0，16进制打印
        }
    }

    for city in [
        City { name: "Dublin", lat: 53.347778, lon: -6.259722 },
        City { name: "Oslo", lat: 59.95, lon: 10.75 },
        City { name: "Vancouver", lat: 49.25, lon: -123.1 },
    ].iter() {
        println!("{}", *city);
    }
    for color in [
        Color { red: 128, green: 255, blue: 90 },
        Color { red: 0, green: 3, blue: 254 },
        Color { red: 0, green: 0, blue: 0 },
    ].iter() {
        println!("{:#}", *color);
    }
}
