///闭包
//by reference: &T 默认
//by mutable reference: &mut T
//by value: T
pub fn closures() {
    //闭包是lambda，一个表达式可以省略大括号
    fn function(i: i32) -> i32 {
        i + 1
    }
    let closure_annotated = |i: i32| -> i32 { i + 1 };
    let closure_inferred = |i| i + 1;
    let i = 1;
    println!("function: {}", function(i));
    println!("closure_annotated: {}", closure_annotated(i));
    println!("closure_inferred: {}", closure_inferred(i));
    let one = || 1;
    println!("closure returning one: {}", one());
    capturing();
    let haystack = vec![1, 2, 3];
    let contains = move |needle| haystack.contains(needle);
    println!("{}", contains(&1));
    println!("{}", contains(&4));
    //println!("There're {} elements in vec", haystack.len());

    input_parameters();
    type_anonymity();
    input_functions();
    output_parameters();
    closures_std();
    higher_order_functions();
    diverging_functions();
}

fn capturing() {
    use std::mem;
    let color = "green";
    let print = || println!("`color`: {}", color);
    print();

    let _reborrow = &color; //闭包使用的color能被继续借用
    print(); //直到最后一次使用print

    let _color_moved = color; //最后使用`print`后允许移动或重新借用
    let mut count = 0;
    let mut inc = || {
        count += 1;
        println!("`count`: {}", count);
    };
    inc();
    //let _reborrow = &count;//已经被作为可变的借用出去，不可再借用给不可变的
    inc();
    let _count_reborrowed = &mut count;
    //非copy的，被移动进闭包
    let movable = Box::new(3);
    let consume = || {
        println!("`movable`: {:?}", movable);
        mem::drop(movable);
    };

    consume(); //仅能被使用一次。
}

fn input_parameters() {
    //以闭包作为参数并调用它的函数。
    fn apply<F>(f: F)
    where
        // The closure takes no input and returns nothing.
        F: FnOnce(),
    {
        //按值捕获
        // ^ TODO: Try changing this to `Fn` or `FnMut`.
        f();
    }

    // A function which takes a closure and returns an `i32`.
    fn apply_to_3<F>(f: F) -> i32
    where
        F: Fn(i32) -> i32,
    {
        f(3)
    }

    use std::mem;

    let greeting = "hello";
    //从借来的引用创建自己拥有的数据
    let mut farewell = "goodbye".to_owned();
    let diary = || {
        println!("I said {}.", greeting); //捕获引用
        farewell.push_str("!!!"); //捕获可变引用
        println!("Then I screamed {}.", farewell);
        println!("Now I can sleep. zzzzz");
        mem::drop(farewell); //捕获值
    };
    apply(diary);
    let double = |x| 2 * x;
    println!("3 doubled: {}", apply_to_3(double));
}

fn type_anonymity() {
    // `F`必须是泛型的
    fn apply<F>(f: F)
    where
        F: FnOnce(),
    {
        f();
    }
}

fn input_functions() {
    //无返回值的
    fn call_me<F: Fn()>(f: F) {
        f();
    }

    fn function() {
        println!("I'm a function!");
    }

    //满足Fn的闭包
    let closure = || println!("I'm a closure!");

    call_me(closure);
    call_me(function);
}

fn output_parameters() {
    fn create_fn() -> impl Fn() {
        //必须使用move关键字，强制按值捕获避免函数退出时通过引用捕获的被丢弃而留下无效引用
        let text = "Fn".to_owned();
        move || println!("This is a: {}", text)
    }

    fn create_fnmut() -> impl FnMut() {
        let text = "FnMut".to_owned();
        move || println!("This is a: {}", text)
    }

    fn create_fnonce() -> impl FnOnce() {
        let text = "FnOnce".to_owned();
        move || println!("This is a: {}", text)
    }

    let fn_plain = create_fn();
    let mut fn_mut = create_fnmut();
    let fn_once = create_fnonce();

    fn_plain();
    fn_mut();
    fn_once();
}

///https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.find
fn closures_std() {
    //any
    let vec1 = vec![1, 2, 3];
    let vec2 = vec![4, 5, 6];
    println!("2 in vec1: {}", vec1.iter().any(|&x| x == 2)); //不消耗集合，使用引用捕获
    println!("2 in vec2: {}", vec2.into_iter().any(|x| x == 2)); //消耗集合，使用值捕获
    let array1 = [1, 2, 3];
    let array2 = [4, 5, 6];
    println!("2 in array1: {}", array1.iter().any(|&x| x == 2));
    println!("2 in array2: {}", array2.into_iter().any(|&x| x == 2));

    //find
    let vec1 = vec![1, 2, 3];
    let vec2 = vec![4, 5, 6];
    let mut iter = vec1.iter(); //&i32
    let mut into_iter = vec2.into_iter(); //i32
                                          //解构参数
    println!("Find 2 in vec1: {:?}", iter.find(|&&x| x == 2)); //`&&i32` to `i32`
    println!("Find 2 in vec1: {:?}", iter.find(|x| **x == 2)); //参数是`&&i32`，使用时解构
    println!("Find 2 in vec2: {:?}", into_iter.find(|&x| x == 2)); //`&i32` to `i32`
    let array1 = [1, 2, 3];
    let array2 = [4, 5, 6];
    println!("Find 2 in array1: {:?}", array1.iter().find(|&&x| x == 2));
    println!(
        "Find 2 in array2: {:?}",
        array2.into_iter().find(|&&x| x == 2)
    );

    //position index
    let vec = vec![1, 9, 3, 3, 13, 2];
    let index_of_first_even_number = vec.iter().position(|x| x % 2 == 0);
    assert_eq!(index_of_first_even_number, Some(5));
    let index_of_first_negative_number = vec.iter().position(|x| x < &0);
    assert_eq!(index_of_first_negative_number, None);
    //into_iter这个只能放最后，会消耗集合，会从左到右移动每个元素，之后集合不可用
    let index_of_first_even_number = vec.into_iter().position(|x| x % 2 == 0);
    assert_eq!(index_of_first_even_number, Some(5));
}

fn higher_order_functions() {
    fn is_odd(n: u32) -> bool {
        n % 2 == 1
    }

    println!("Find the sum of all the squared odd numbers under 1000");
    let upper = 1000;
    let mut acc = 0;
    for n in 0.. {
        //无穷大
        let n_squared = n * n;
        if n_squared >= upper {
            break;
        } else if is_odd(n_squared) {
            acc += n_squared;
        }
    }
    println!("imperative style: {}", acc);
    //函数式风格
    let sum_of_squared_odd_numbers: u32 = (0..)
        .map(|n| n * n)
        .take_while(|&n_squared| n_squared < upper)
        .filter(|&n_squared| is_odd(n_squared)) // That are odd
        .fold(0, |acc, n_squared| acc + n_squared); // Sum them
    println!("functional style: {}", sum_of_squared_odd_numbers);
}

fn diverging_functions() {
    fn foo() -> ! {
        panic!("This call never returns.");
    }

    //尽管返回值中没有信息，但该函数照常返回。
    fn some_fn() {
        ()
    }
    let a: () = some_fn();
    println!("This function returns and you can see this line.");
    //该函数永远不会将控制流返回给调用者。直接抛出恐慌
    //let x= panic!("This call never returns.");
    println!("You will never see this line!");
    fn sum_odd_numbers(up_to: u32) -> u32 {
        let mut acc = 0;
        for i in 0..up_to {
            let addition: u32 = match i % 2 == 1 {
                true => i,
                false => continue, //不返还，不违反match
            };
            acc += addition;
        }
        acc
    }
    println!(
        "Sum of odd numbers up to 9 (excluding): {}",
        sum_odd_numbers(9)
    );

    //println!("{}", foo()) //error This call never returns.
}
