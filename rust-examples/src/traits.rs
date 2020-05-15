///特质
pub fn traits() {
    struct Sheep {
        naked: bool,
        name: &'static str,
    }

    impl Sheep {
        fn is_naked(&self) -> bool {
            self.naked
        }

        fn shear(&mut self) {
            if self.is_naked() {
                //实现者方法可以使用实现者的trait方法
                println!("{} is already naked...", self.name());
            } else {
                println!("{} gets a haircut!", self.name);

                self.naked = true;
            }
        }
    }

    //特质是为未知类型定义的方法的集合：self。他们可以访问以相同特质声明的其他方法。
    trait Animal {
        //静态方法，self是具体实现者的类型
        fn new(name: &'static str) -> Self;
        //实例方法
        fn name(&self) -> &'static str;
        fn noise(&self) -> &'static str;

        //特质可以提供默认方法实现
        fn talk(&self) {
            println!("{} says {}", self.name(), self.noise());
        }
    }
    // 为`Sheep`实现Animal特质
    impl Animal for Sheep {
        //self的类型是`Sheep`
        fn new(name: &'static str) -> Sheep {
            Sheep {
                name: name,
                naked: false,
            }
        }

        fn name(&self) -> &'static str {
            self.name
        }

        fn noise(&self) -> &'static str {
            if self.is_naked() {
                "baaaaah?"
            } else {
                "baaaaah!"
            }
        }

        //覆盖特质中默认的方法实现
        fn talk(&self) {
            println!("{} pauses briefly... {}", self.name, self.noise());
        }
    }

    //在这种情况下，必须使用类型注释
    let mut dolly: Sheep = Animal::new("Dolly");
    // TODO ^ Try removing the type annotations.

    dolly.talk();
    dolly.shear();
    dolly.talk();

    //
    derive();
    return_dyn();
    operator_overload();
    drop_();
    iterators();
    impl_trait();
    closure_trait();
    clone();
    supertraits();
    overlapping_traits();
}

fn derive() {
    //比较特质: Eq, PartialEq, Ord, PartialOrd.
    //拷贝特质（深拷贝）, to create T from &T via a copy.
    //复制特质 相对于move而言, to give a type 'copy semantics' instead of 'move semantics'.
    //可hash, to compute a hash from &T.
    //默认, to create an empty instance of a data type.
    //调试, to format a value using the {:?} formatter.
    // `Centimeters`, a tuple struct that can be compared
    #[derive(PartialEq, PartialOrd)]
    struct Centimeters(f64);

    //运行以调试模式打印 {:?}
    #[derive(Debug)]
    struct Inches(i32);

    impl Inches {
        fn to_centimeters(&self) -> Centimeters {
            let &Inches(inches) = self;

            Centimeters(inches as f64 * 2.54)
        }
    }

    #[derive(PartialEq, Debug)]
    struct Seconds(i32);

    let _one_second = Seconds(1);

    println!("One second looks like: {:?}", _one_second);

    let _this_is_true = (_one_second == _one_second);

    let foot = Inches(12);

    println!("One foot equals {:?}", foot);

    let meter = Centimeters(100.0);

    let cmp = if foot.to_centimeters() < meter {
        "smaller"
    } else {
        "bigger"
    };

    println!("One foot is {} than one meter.", cmp);
}

fn return_dyn() {
    //返回实现了某一特质的类型的实例
    struct Sheep {}
    struct Cow {}

    trait Animal {
        //实例方法
        fn noise(&self) -> &'static str;
    }

    // 为`Sheep`实现Animal特质
    impl Animal for Sheep {
        fn noise(&self) -> &'static str {
            "baaaaah!"
        }
    }

    // 为`Cow`实现Animal特质
    impl Animal for Cow {
        fn noise(&self) -> &'static str {
            "moooooo!"
        }
    }

    //返回一些实现Animal的结构，但是在编译时我们不知道是哪个结构
    fn random_animal(random_number: f64) -> Box<dyn Animal> {
        if random_number < 0.5 {
            Box::new(Sheep {})
        } else {
            Box::new(Cow {})
        }
    }

    let random_number = 0.234;
    let animal = random_animal(random_number);
    println!(
        "You've randomly chosen an animal, and it says {}",
        animal.noise()
    );
}

fn operator_overload() {
    //运算符重载
    use std::ops;

    struct Foo;
    struct Bar;

    #[derive(Debug)]
    struct FooBar;

    #[derive(Debug)]
    struct BarFoo;

    //为Foo类型实现Add特质，以支持add操作
    impl ops::Add<Bar> for Foo {
        type Output = FooBar;

        fn add(self, _rhs: Bar) -> FooBar {
            println!("> Foo.add(Bar) was called");
            FooBar
        }
    }
    //为Bar类型实现Add特质，以支持add操作
    impl ops::Add<Foo> for Bar {
        type Output = BarFoo;

        fn add(self, _rhs: Foo) -> BarFoo {
            println!("> Bar.add(Foo) was called");

            BarFoo
        }
    }

    println!("Foo + Bar = {:?}", Foo + Bar);
    println!("Bar + Foo = {:?}", Bar + Foo);
}

fn drop_() {
    //对象超出范围时自动调用
    struct Droppable {
        name: &'static str,
    }

    //为Droppable类型实现Drop特质
    impl Drop for Droppable {
        fn drop(&mut self) {
            println!("> Dropping {}", self.name);
        }
    }

    let _a = Droppable { name: "a" };

    // block A
    {
        let _b = Droppable { name: "b" };

        // block B
        {
            let _c = Droppable { name: "c" };
            let _d = Droppable { name: "d" };

            println!("Exiting block B");
        }
        println!("Just exited block B");

        println!("Exiting block A");
    }
    println!("Just exited block A");

    drop(_a);

    println!("end of the main function");
}

fn iterators() {
    //在数组或向量上迭代
    struct Fibonacci {
        curr: u32,
        next: u32,
    }

    //为Fibonacci类型实现迭代器特质
    impl Iterator for Fibonacci {
        type Item = u32;

        //只需要实现一个next方法
        fn next(&mut self) -> Option<u32> {
            let new_next = self.curr + self.next;
            self.curr = self.next;
            self.next = new_next;
            //斐波那契没有终点，所以next不会返回None
            Some(self.curr)
        }
    }

    //返回一个斐波那契序列生成器
    fn fibonacci() -> Fibonacci {
        Fibonacci { curr: 0, next: 1 }
    }

    // `0..3` = 0,1,2
    let mut sequence = 0..3;

    println!("Four consecutive `next` calls on 0..3");
    println!("> {:?}", sequence.next());
    println!("> {:?}", sequence.next());
    println!("> {:?}", sequence.next());
    println!("> {:?}", sequence.next());

    println!("Iterate through 0..3 using `for`");
    for i in 0..3 {
        println!("> {}", i);
    }

    //拿前4个
    println!("The first four terms of the Fibonacci sequence are: ");
    for i in fibonacci().take(4) {
        println!("> {}", i);
    }

    //跳过前4个，再拿4个
    println!("The next four terms of the Fibonacci sequence are: ");
    for i in fibonacci().skip(4).take(4) {
        println!("> {}", i);
    }

    let array = [1u32, 3, 3, 7];

    println!("Iterate the following array {:?}", &array);
    for i in array.iter() {
        println!("> {}", i);
    }
}

fn impl_trait() {
    //返回 trait类型
    use std::iter;
    use std::vec::IntoIter;

    //组合vec
    fn combine_vecs_explicit_return_type(
        v: Vec<i32>,
        u: Vec<i32>,
    ) -> iter::Cycle<iter::Chain<IntoIter<i32>, IntoIter<i32>>> {
        v.into_iter().chain(u.into_iter()).cycle()
    }

    //简化返回类型
    fn combine_vecs(v: Vec<i32>, u: Vec<i32>) -> impl Iterator<Item = i32> {
        v.into_iter().chain(u.into_iter()).cycle()
    }

    let v1 = vec![1, 2, 3];
    let v2 = vec![4, 5];
    let mut v3 = combine_vecs(v1, v2);
    assert_eq!(Some(1), v3.next());
    assert_eq!(Some(2), v3.next());
    assert_eq!(Some(3), v3.next());
    assert_eq!(Some(4), v3.next());
    assert_eq!(Some(5), v3.next());
    println!("all done");
}

fn closure_trait() {
    fn make_adder_function(y: i32) -> impl Fn(i32) -> i32 {
        let closure = move |x: i32| x + y;
        closure
    }

    let plus_one = make_adder_function(1);
    assert_eq!(plus_one(2), 3);

    fn double_positives<'a>(numbers: &'a Vec<i32>) -> impl Iterator<Item = i32> + 'a {
        numbers.iter().filter(|x| x > &&0).map(|x| x * 2)
    }
}

fn clone() {
    //单元结构体
    #[derive(Debug, Clone, Copy)]
    struct Nil;

    //元祖结构体
    #[derive(Clone, Debug)]
    struct Pair(Box<i32>, Box<i32>);

    let nil = Nil;
    let copied_nil = nil;

    println!("original: {:?}", nil);
    println!("copy: {:?}", copied_nil);

    let pair = Pair(Box::new(1), Box::new(2));
    println!("original: {:?}", pair);

    let moved_pair = pair;
    println!("copy: {:?}", moved_pair);

    //println!("original: {:?}", pair);

    let cloned_pair = moved_pair.clone();

    drop(moved_pair);

    //println!("copy: {:?}", moved_pair);

    println!("clone: {:?}", cloned_pair);
}

fn supertraits() {
    trait Person {
        fn name(&self) -> String;
    }

    // Student 是 Person
    // 实现 Student 也需要实现 Person.
    trait Student: Person {
        fn university(&self) -> String;
    }

    trait Programmer {
        fn fav_language(&self) -> String;
    }

    // 实现CompSciStudent需要同时实现Programmer Student
    // CompSciStudent既是Programmer也是Student
    trait CompSciStudent: Programmer + Student {
        fn git_username(&self) -> String;
    }

    fn comp_sci_student_greeting(student: &dyn CompSciStudent) -> String {
        format!(
            "My name is {} and I attend {}. My Git username is {}",
            student.name(),
            student.university(),
            student.git_username()
        )
    }
}

fn overlapping_traits() {
    //消除多个歧义的方法
    trait UsernameWidget {
        fn get(&self) -> String;
    }

    trait AgeWidget {
        fn get(&self) -> u8;
    }

    struct Form {
        username: String,
        age: u8,
    }

    impl UsernameWidget for Form {
        fn get(&self) -> String {
            self.username.clone()
        }
    }

    impl AgeWidget for Form {
        fn get(&self) -> u8 {
            self.age
        }
    }

    //Form 实现了UsernameWidget和AgeWidget，两个特质均有方法get
    let form = Form {
        username: "rustacean".to_owned(),
        age: 28,
    };

    //不可以这样，编译出错：multiple `get` found
    // println!("{}", form.get());
    let username = <Form as UsernameWidget>::get(&form);
    assert_eq!("rustacean".to_owned(), username);

    let age = <Form as AgeWidget>::get(&form);
    assert_eq!(28, age);
}
