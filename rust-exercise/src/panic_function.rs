use std::error::Error;
use std::fs::File;
use std::io::{ErrorKind, Read};
use std::{fs, io};

///恐慌使用（error）
pub fn panic_function() {
    let f = File::open("hello.txt");
    let f = match f {
        Ok(file) => file,
        Err(error) => match error.kind() {
            ErrorKind::NotFound => match File::create("hello.txt") {
                Ok(fc) => fc,
                Err(e) => panic!("Problem creating the file: {:?}", e), //panic!打印错误信息，panic是不可恢复的错误
            },
            other_error => panic!("Problem opening the file: {:?}", other_error),
        },
    };

    ///使用闭包简化代码
    //展开一个结果，得到一个[`Ok`]的内容。如果值是一个[`Err`]，则用它的值调用'op'。
    let f = File::open("hello.txt").unwrap_or_else(|error| {
        if error.kind() == ErrorKind::NotFound {
            File::create("hello.txt").unwrap_or_else(|error| {
                panic!("Problem creating the file: {:?}", error);
            })
        } else {
            panic!("Problem opening the file: {:?}", error);
        }
    });

    ///如果Result值为Ok变体，unwrap则将在中返回该值Ok。如果Result是Err变体，unwrap将为panic!我们调用宏。
    let f = File::open("hello.txt").unwrap();
    //一样，但是是自己传递错误信息给panic!宏
    let f = File::open("hello.txt").expect("Failed to open hello.txt");

    ///将错误返回到调用代码的函数
    fn read_username_from_file() -> Result<String, io::Error> {
        let f = File::open("hello.txt");
        let mut f = match f {
            Ok(file) => file,
            Err(e) => return Err(e),
        };
        let mut s = String::new();
        match f.read_to_string(&mut s) {
            Ok(_) => Ok(s),
            Err(e) => Err(e),
        }
    }

    ///传播错误的捷径：?运算符
    fn read_username_from_file2() -> Result<String, io::Error> {
        let mut f = File::open("hello.txt")?; //使用?运算符将错误返回到调用代码的函数,将错误自身转换为返回的错误类型。（From特质 from函数）
        let mut s = String::new();
        f.read_to_string(&mut s)?;
        Ok(s)
    }

    fn read_username_from_file3() -> Result<String, io::Error> {
        let mut s = String::new();
        File::open("hello.txt")?.read_to_string(&mut s)?;
        Ok(s)
    }

    fn read_username_from_file4() -> Result<String, io::Error> {
        fs::read_to_string("hello.txt")
    }

    //下面是在main方法中使用?的示例
    //    fn main() -> Result<(), Box<dyn Error>> {
    //        let f = File::open("hello.txt")?;
    //
    //        Ok(())
    //    }

    ///创建一个新类型并将验证放入函数中以创建该类型的实例
    //这样做的目的是，其他代码中不需要混入对该类型的检查
    pub struct Guess {
        value: i32,
    }
    impl Guess {
        pub fn new(value: i32) -> Guess {
            if value < 1 || value > 100 {
                panic!("Guess value must be between 1 and 100, got {}.", value);
            }
            Guess { value }
        }
        pub fn value(&self) -> i32 {
            self.value
        }
    }
}
