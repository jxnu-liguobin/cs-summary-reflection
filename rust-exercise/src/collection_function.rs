use std::string::ToString;

///https://doc.rust-lang.org/stable/nomicon/vec.html.
///集合
pub fn collection_function() {
    let mut v: Vec<i32> = Vec::new(); //Vec类似ArrayList，称为向量？
    v.push(12); //想要修改必须定义为mut可变的
    println!("{:#?}", v);
    let v = vec![1, 2, 3];
    println!("{:#?}", v);
    let v = vec![1, 2, 3, 4, 5];
    let third = &v[2]; //获取元素
    println!("The third element is {}", third);
    match v.get(2) {
        Some(third) => println!("The third element is {}", third),
        None => println!("There is no third element."),
    }

    //let does_not_exist = &v[100];//恐慌，编译时直接退出程序
    ///当将get方法传递给向量之外的索引时，它将返回None且不会出现惊慌。如果在正常情况下偶尔访问超出向量范围的元素，则可以使用此方法。
    ///您的代码将需要具有处理Some(＆element)或None的逻辑，
    let does_not_exist = v.get(100);
    ///当程序具有有效的引用时，借位检查器将执行所有权和借用规则
    let mut v = vec![1, 2, 3, 4, 5];
    let first = v[0]; //使用引用&v[0]将会报错
    v.push(6); //尝试保留第一个元素的引用的同时向向量添加元素将会出现编译错误，此时引用可能改变
    println!("The first element is: {}", first);

    ///遍历向量集合
    let v = vec![100, 32, 57];
    for i in &v {
        println!("{}", i); //直接打印 i，而不需要使用c语言中的*i，省略了*
    }
    let mut v = vec![100, 32, 57];
    for i in &mut v {
        *i += 50; //对可变的向量进行操作，让每个元素都增加50
        println!("{}", i)
    }

    ///定义一个枚举以将不同类型的值存储在一个向量中
    enum SpreadsheetCell {
        Int(i32),
        Float(f64),
        Text(String),
    }
    ///Rust需要知道在编译时向量中将包含哪些类型，因此Rust确切知道要存储每个元素需要多少内存。第二个优点是，我们可以明确说明此向量允许哪些类型。
    ///如果Rust允许向量保留任何类型，则一个或多个类型可能会导致对向量元素执行的操作出错。使用枚举加上match表达式意味着Rust将确保在编译时处理所有可能的情况
    ///在编写程序时，如果您不知道该程序在运行时会存储到向量中的所有可能类型，则枚举技术将不起作用。相反，您可以使用trait对象
    let row = vec![
        SpreadsheetCell::Int(3),
        SpreadsheetCell::Text(String::from("blue")),
        SpreadsheetCell::Float(10.12),
    ];

    ///使用字符串存储UTF-8编码文本
    ///Rust在核心语言中只有一种字符串类型，即字符串切片str，通常以借用的形式＆str获取。
    ///字符串类型是Rust的标准库提供的，而不是编码为核心语言的字符串类型，它是一种可增长，可变，拥有的，以UTF-8编码的字符串类型。
    ///当rust开发者在Rust中引用“字符串”时，它们通常是指String和字符串切片＆str类型，而不仅仅是这些类型之一。
    ///Rust的标准库还包括许多其他字符串类型，例如OsString，OsStr，CString和CStr。Library crates可以提供更多用于存储字符串数据的选项。
    ///看看这些名称如何全部以String或Str结尾？它们是指拥有和借用的变体，就像您之前看到的String和str类型一样。
    ///例如，这些字符串类型可以用不同的编码存储文本，或以不同的方式在内存中表示。
    let data = "initial contents";
    let s = data.to_string();
    println!("{}", s);
    let s = "initial contents".to_string();
    println!("{}", s);
    let s = String::from("initial contents"); //从字符串文字(字符串常量 切片 &str类型)创建字符串(对象/String类型)
    println!("{}", s);
    //其他编码的文字
    let hello = String::from("السلام عليكم");
    let hello = String::from("Dobrý den");
    let hello = String::from("Hello");
    let hello = String::from("שָׁלוֹם");
    let hello = String::from("नमस्ते");
    let hello = String::from("こんにちは");
    let hello = String::from("안녕하세요");
    let hello = String::from("你好");
    let hello = String::from("Olá");
    let hello = String::from("Здравствуйте");
    let hello = String::from("Hola");

    ///修改字符串，使用push_str方法将字符串切片附加到String
    let mut s = String::from("foo");
    s.push_str("bar");
    let mut s1 = String::from("foo");
    let s2 = "bar";
    s1.push_str(s2);
    println!("s2 is {}", s2); //s2被加到s1之后，再次使用s2
    let mut s = String::from("lo");
    s.push('l'); //使用push向字符串值添加一个字符

    ///合并字符串
    let s1 = String::from("Hello, ");
    let s2 = String::from("world!");
    let s3 = s1 + &s2; //s1被移动，之后无法再次使用
                       //+ 方法 使用add方法 fn add(self, s: &str) -> String {
                       //所以+组合字符串第二个参数必须是 &str的，但是这是因为编译器将&String转化为&str了。
    let s1 = String::from("tic");
    let s2 = String::from("tac");
    let s3 = String::from("toe");
    ///如果需要连接多个字符串，则+运算符的行为会变得笨拙：
    let s = s1 + "-" + &s2 + "-" + &s3;

    ///对于更复杂的字符串组合，我们可以使用format! 宏
    let s1 = String::from("tic");
    let s2 = String::from("tac");
    let s3 = String::from("toe");
    let s = format!("{}-{}-{}", s1, s2, s3);
    println!("{}", s);

    ///Rust字符串不支持索引
    //let h = s1[0]; //编译报错
    ///与字符串内部的实现相关，字符串是Vec<u8>的包装
    let len = String::from("Hola").len();
    println!("{}", len);
    let len = String::from("Здравствуйте").len();
    println!("{}", len);
    ///当问到字符串有多长时，您可能会说12。但是，Rust的答案是24：这就是在UTF-8中编码“Здравствуйте”所需的字节数，因为该字符串中的每个Unicode标量值都占用2个字节的存储空间。
    ///因此，字符串字节的索引并不总是与有效的Unicode标量值相关。为了演示，请考虑以下无效的Rust代码：
    let hello = "Здравствуйте";
    //let answer = &hello[0];//编译报错
    ///Rust不允许我们索引到String中以获取字符的最后一个原因是索引操作总是需要恒定的时间（O(1)）。
    ///但是用String不能保证性能，因为Rust必须从头到尾遍历所有内容以确定有多少个有效字符。
    ///有效的Unicode标量值可能由1个以上的字节组成，从rust字符串中获取字素簇很复杂，标准库并未提供此功能
    for c in "नमस्ते".chars() {
        println!("{}", c); //न म स ् त े ，一个字符由2个char组成
    }
    for b in "नमस्ते".bytes() {
        println!("{}", b); //返回字节，很多个
    }

    ///hash map
    use std::collections::HashMap;
    let mut scores = HashMap::new();
    scores.insert(String::from("Blue"), 10);
    scores.insert(String::from("Yellow"), 50);
    //从teams和initial_scores中创建map
    let teams = vec![String::from("Blue"), String::from("Yellow")];
    let initial_scores = vec![10, 50];
    let scores: HashMap<_, _> = teams.iter().zip(initial_scores.iter()).collect();

    ///所有权
    let field_name = String::from("Favorite color");
    let field_value = String::from("Blue");
    let mut map = HashMap::new();
    map.insert(field_name, field_value); //此时field_name和field_value无效,已经被移动到map中
    ///获取key对应的value
    let team_name = String::from("Blue");
    let score = scores.get(&team_name);
    ///编译map
    for (key, value) in &scores {
        println!("{}: {}", key, value);
    }

    ///更新map
    //insert 覆盖旧值，scores.entry(String::from("Yellow")).or_insert(50);key不存在时插入
    //根据旧值更新值
    let text = "hello world wonderful world";
    let mut map = HashMap::new();
    for word in text.split_whitespace() {
        let count = map.entry(word).or_insert(0);
        *count += 1;
    }

    println!("{:?}", map);
} //v超出范围并在此处释放
