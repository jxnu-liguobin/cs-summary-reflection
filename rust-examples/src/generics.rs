use std::fmt::Display;

///泛型
pub fn generics() {
    struct A;
    struct Single(A);
    //T属性是泛型
    struct SingleGen<T>(T);

    let _s = Single(A);
    let _char: SingleGen<char> = SingleGen('a');
    let _t = SingleGen(A);
    let _i32 = SingleGen(6);
    let _char = SingleGen('a');
    generic_function();
    implementation();
    traits();
    bounds();
    where_clauses();
    associated_types();
    phantom_type_parameters();
}

//函数参数是泛型的
fn generic_function() {
    struct A;
    struct S(A);
    struct SGen<T>(T);
    fn reg_fn(_s: S) {}
    fn gen_spec_t(_s: SGen<A>) {}
    fn gen_spec_i32(_s: SGen<i32>) {}
    fn generic<T>(_s: SGen<T>) {}
    reg_fn(S(A));
    gen_spec_t(SGen(A));
    gen_spec_i32(SGen(6));
    generic::<char>(SGen('a'));
    generic(SGen('c'));
}

//结构体实现 泛型
fn implementation() {
    struct S;
    struct GenericVal<T>(T);
    impl GenericVal<f32> {}
    impl GenericVal<S> {}
    impl<T> GenericVal<T> {}

    struct Val {
        val: f64,
    }

    impl Val {
        fn value(&self) -> &f64 {
            &self.val
        }
    }

    struct GenVal<T> {
        gen_val: T,
    }
    //泛型实现
    impl<T> GenVal<T> {
        fn value(&self) -> &T {
            &self.gen_val
        }
    }

    let x = Val { val: 3.0 };
    let y = GenVal { gen_val: 3i32 };

    println!("{}, {}", x.value(), y.value());
}

//特质实现 泛型
fn traits() {
    //不可复制的类型
    struct Empty;
    struct Null;

    //泛型特质
    trait DoubleDrop<T> {
        fn double_drop(self, _: T);
    }

    //为任何泛型参数“T”和调用方“U”实现“DoubleDrop<T>”
    impl<T, U> DoubleDrop<T> for U {
        //此方法同时拥有两个传入参数的所有权
        fn double_drop(self, _: T) {}
    }
    let empty = Empty;
    let null = Null;

    empty.double_drop(null);
    //empty;
    //null;
}

//界限
fn bounds() {
    //实现Debug特质，支持`{:?}`功能
    use std::fmt::Debug;
    trait HasArea {
        fn area(&self) -> f64;
    }
    impl HasArea for Rectangle {
        fn area(&self) -> f64 {
            self.length * self.height
        }
    }
    #[derive(Debug)]
    struct Rectangle {
        length: f64,
        height: f64,
    }
    #[allow(dead_code)]
    struct Triangle {
        length: f64,
        height: f64,
    }
    //泛型“T”必须实现“Debug”
    fn print_debug<T: Debug>(t: &T) {
        println!("{:?}", t);
    }

    //必须实现HasArea
    fn area<T: HasArea>(t: &T) -> f64 {
        t.area()
    }

    let rectangle = Rectangle {
        length: 3.0,
        height: 4.0,
    };
    let _triangle = Triangle {
        length: 3.0,
        height: 4.0,
    };
    print_debug(&rectangle);
    println!("Area: {}", area(&rectangle));
    //print_debug(&_triangle);
    //println!("Area: {}", area(&_triangle)); // | Error: Does not implement either `Debug` or `HasArea`.

    //不含函数
    struct Cardinal;
    struct BlueJay;
    struct Turkey;

    trait Red {}
    trait Blue {}

    impl Red for Cardinal {}
    impl Blue for BlueJay {}

    //这些函数仅对实现这些特性的类型有效。这些特征都是空的，这一事实无关紧要。
    fn red<T: Red>(_: &T) -> &'static str {
        "red"
    }
    fn blue<T: Blue>(_: &T) -> &'static str {
        "blue"
    }
    let cardinal = Cardinal;
    let blue_jay = BlueJay;
    let _turkey = Turkey;
    println!("A cardinal is {}", red(&cardinal));
    println!("A blue jay is {}", blue(&blue_jay));
    //println!("A turkey is {}", red(&_turkey));，没有实现Red，报错

    //多界限
    use std::fmt::Display;
    fn compare_prints<T: Debug + Display>(t: &T) {
        println!("Debug: `{:?}`", t);
        println!("Display: `{}`", t);
    }
    fn compare_types<T: Debug, U: Debug>(t: &T, u: &U) {
        println!("t: `{:?}`", t);
        println!("u: `{:?}`", u);
    }
    let string = "words";
    let array = [1, 2, 3];
    let vec = vec![1, 2, 3];
    compare_prints(&string);
    //compare_prints(&array);
    compare_types(&array, &vec);
}

//where 分开泛型和界限
fn where_clauses() {
    //impl<A: TraitB + TraitC, D: TraitE + TraitF> MyTrait<A, D> for YourType {}
    //使用where
    //impl<A, D> MyTrait<A, D> for YourType where
    //  A: TraitB + TraitC,
    //  D: TraitE + TraitF {}
    use std::fmt::Debug;
    trait PrintInOption {
        fn print_in_option(self);
    }

    impl<T> PrintInOption for T
    where
        Option<T>: Debug,
    {
        fn print_in_option(self) {
            println!("{:?}", Some(self));
        }
    }
    let vec = vec![1, 2, 3];
    vec.print_in_option();
}

//关联类型示例
fn associated_types() {
    //不使用关联类型
    fn example1() {
        /*start*/
        struct Container(i32, i32);
        trait Contains<A, B> {
            fn contains(&self, _: &A, _: &B) -> bool;
            fn first(&self) -> i32;
            fn last(&self) -> i32;
        }

        impl Contains<i32, i32> for Container {
            fn contains(&self, number_1: &i32, number_2: &i32) -> bool {
                (&self.0 == number_1) && (&self.1 == number_2)
            }
            fn first(&self) -> i32 {
                self.0
            }
            fn last(&self) -> i32 {
                self.1
            }
        }
        //必须显示声明所有泛型
        fn difference<A, B, C>(container: &C) -> i32
        where
            C: Contains<A, B>,
        {
            container.last() - container.first()
        }
        let number_1 = 3;
        let number_2 = 10;
        let container = Container(number_1, number_2);
        println!(
            "Does container contain {} and {}: {}",
            &number_1,
            &number_2,
            container.contains(&number_1, &number_2)
        );
        println!("First number: {}", container.first());
        println!("Last number: {}", container.last());
        println!("The difference is: {}", difference(&container));
        /*end*/
    }
    //使用关联类型改善
    fn example2() {
        /*start*/
        struct Container(i32, i32);
        trait Contains {
            type A;
            type B;
            fn contains(&self, _: &Self::A, _: &Self::B) -> bool;
            fn first(&self) -> i32;
            fn last(&self) -> i32;
        }

        impl Contains for Container {
            type A = i32;
            type B = i32;
            fn contains(&self, number_1: &i32, number_2: &i32) -> bool {
                (&self.0 == number_1) && (&self.1 == number_2)
            }
            fn first(&self) -> i32 {
                self.0
            }
            fn last(&self) -> i32 {
                self.1
            }
        }
        //使用关联类型后定义更加简洁
        fn difference<C: Contains>(container: &C) -> i32 {
            container.last() - container.first()
        }
        let number_1 = 3;
        let number_2 = 10;
        let container = Container(number_1, number_2);
        println!(
            "Does container contain {} and {}: {}",
            &number_1,
            &number_2,
            container.contains(&number_1, &number_2)
        );
        println!("First number: {}", container.first());
        println!("Last number: {}", container.last());
        println!("The difference is: {}", difference(&container));
        /*end*/
    }
}

//PhantomData作用：
//并不使用的类型；
//型变；
//标记拥有关系；
//自动trait实现（send/sync）；
fn phantom_type_parameters() {
    use std::marker::PhantomData;
    use std::ops::Add;
    #[derive(Debug, Clone, Copy)]
    enum Inch {}
    #[derive(Debug, Clone, Copy)]
    enum Mm {}
    /// `Length` is a type with phantom type parameter `Unit`,
    /// and is not generic over the length type (that is `f64`).
    ///
    /// `f64` already implements the `Clone` and `Copy` traits.
    #[derive(Debug, Clone, Copy)]
    struct Length<Unit>(f64, PhantomData<Unit>);

    /// The `Add` trait defines the behavior of the `+` operator.
    impl<Unit> Add for Length<Unit> {
        type Output = Length<Unit>;
        //反回之和
        fn add(self, rhs: Length<Unit>) -> Length<Unit> {
            Length(self.0 + rhs.0, PhantomData)
        }
    }
    let one_foot: Length<Inch> = Length(12.0, PhantomData);
    let one_meter: Length<Mm> = Length(1000.0, PhantomData);
    let two_feet = one_foot + one_foot;
    let two_meters = one_meter + one_meter;
    println!("one foot + one_foot = {:?} in", two_feet.0);
    println!("one meter + one_meter = {:?} mm", two_meters.0);
    //let one_feter = one_foot + one_meter;//error 2个相加的类型的泛型不同
}
