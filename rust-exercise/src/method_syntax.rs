///方法语法
pub fn method_syntax() {
    ///方法语法，方法与函数不同
    #[derive(Debug)]
    struct Rectangle {
        width: u32,
        height: u32,
    }

    ///结构体的实现块，方法第一个参数必须是&self，不需要声明类型（impl与struct名称相同，能自动推断self类型，这也是能自动引用、取消引用的原因）
    impl Rectangle {
        //把2个方法放在多个impl实现也是可以的
        fn area(&self) -> u32 {
            self.width * self.height
        }

        //新增方法，有额外参数
        fn can_hold(&self, other: &Rectangle) -> bool {
            self.width > other.width && self.height > other.height
        }

        //关联函数，没有self，类似其他语言的静态方法，但不是rust方法
        fn square(size: u32) -> Rectangle {
            Rectangle {
                width: size,
                height: size,
            }
        }
    }

    let rect1 = Rectangle {
        width: 30,
        height: 50,
    };

    ///c/c++中如果object是一个指针, object->something() 与 (*object).something()等价
    ///Rust没有等效于->运算符；相反，Rust具有称为自动引用和取消引用的功能。调用方法是Rust少数具有这种行为的地方之一。
    ///工作原理如下：当您使用object.something()调用方法时，Rust会自动添加＆，＆mut或*，从而使对象与方法的签名匹配。换句话说，以下是相同的：
    ///p1.distance(&p2);
    ///(&p1).distance(&p2);
    println!(
        "The area of the rectangle is {} square pixels.",
        rect1.area()
    );

    ///方法参数
    let rect1 = Rectangle {
        width: 30,
        height: 50,
    };
    let rect2 = Rectangle {
        width: 10,
        height: 40,
    };
    let rect3 = Rectangle {
        width: 60,
        height: 45,
    };

    println!("Can rect1 hold rect2? {}", rect1.can_hold(&rect2));
    println!("Can rect1 hold rect3? {}", rect1.can_hold(&rect3));

    ///impl块的另一个有用功能是允许我们在不以self为参数的impl块中定义函数。这些之所以称为关联函数，是因为它们与struct相关联。
    ///它们仍然是函数，而不是方法，因为它们没有可使用的结构实例
    ///关联函数通常用于将返回该结构的新实例的构造函数。例如，我们可以提供一个关联的函数，该函数将具有一个维度参数并将其用作宽度和高度，从而使创建矩形矩形变得更加容易，而不必两次指定相同的值：
    let sq = Rectangle::square(3); //类似调用静态方法
    println!("sq is {:#?}", sq);

    ///关联函数与结构体相关，但是没有self实例，他们仍是函数！！！
    struct Test;
    impl Test {
        fn test() -> String {
            String::from("hello world")
        }
    }

    let test = Test::test();
    println!("test is {:#?}", test);
}
