///线程 IO 文件 等
pub fn misc() {
    threads();
    thread_testcase();
    channels();
    path();
    file_open();
    file_create();
    file_read_lines();
    child_processes();
    child_processes_pipes();
    child_processes_wait();
    filesystem_operations();
    program_arguments();
    argument_parsing();
    foreign_function_interface();
}

fn threads() {
    use std::thread;

    static NTHREADS: i32 = 10;

    //使用一个向量来容纳生成的子线程处理闭包。
    let mut children = vec![];

    for i in 0..NTHREADS {
        children.push(thread::spawn(move || {
            println!("this is thread number {}", i);
        }));
    }

    for child in children {
        //等待线程完成，返回结果。_ 匿名变量，不需要这个结果
        let _ = child.join();//按顺序从0~9打印
    }
}

//计算所有数字的总和
fn thread_testcase() {
    use std::thread;
    // TODO: see what happens to the output if you insert spaces!
    // 准备数据
    let data = "86967897737416471853297327050364959
11861322575564723963297542624962850
70856234701860851907960690014725639
38397966707106094172783238747669219
52380795257888236525459303330302837
58495327135744041048897885734297812
69920216438980873548808413720956532
16278424637452589860345374828574668";

    let mut children = vec![];

    //将我们的数据分为多个部分以进行单独计算
    //每个块都是对实际数据的引用（＆str）
    let chunked_data = data.split_whitespace();

    for (i, data_segment) in chunked_data.enumerate() {
        println!("data segment {} is \"{}\"", i, data_segment);
        // 在单独的线程中处理每个数据段
        // spawn()返回新线程的句柄，我们必须保持访问返回值的方式
        // TODO: try removing the 'move' and see what happens
        children.push(thread::spawn(move || -> u32 {
            //转化为数字并计算此段的中间和
            let result = data_segment.chars().map(|c| c.to_digit(10).expect("should be a digit")).sum();
            println!("processed segment {}, result={}", i, result);
            //每个子线程都返回中间结果
            result
        }));
    }


    //将每个线程的中间结果收集到一个新的Vec中
    let mut intermediate_sums = vec![];
    for child in children {
        //收集每个子线程的返回值
        let intermediate_sum = child.join().unwrap();
        intermediate_sums.push(intermediate_sum);
    }

    //将所有中间总和合并为一个最终总和
    let final_result = intermediate_sums.iter().sum::<u32>();

    println!("Final sum result: {}", final_result);
}

//通道
fn channels() {
    use std::sync::mpsc::{Sender, Receiver};
    use std::sync::mpsc;
    use std::thread;

    static NTHREADS: i32 = 3;

    //通道有两个端点 `Sender<T>` 和 `Receiver<T>`,
    //其中“ T”是要传输的消息的类型
    //这里的类型说明是多余的
    let (tx, rx): (Sender<i32>, Receiver<i32>) = mpsc::channel();
    let mut children = Vec::new();

    for id in 0..NTHREADS {
        //可以复制发送者端点
        let thread_tx = tx.clone();
        //每个线程将通过通道发送其ID
        let child = thread::spawn(move || {
            //线程拥有对`thread_tx`的所有权
            //每个线程在通道中排队一条消息
            thread_tx.send(id).unwrap();
            //发送是非阻塞操作，线程将在发送其消息后立即继续
            println!("thread {} finished", id);
        });

        children.push(child);
    }

    //收集所有消息
    let mut ids = Vec::with_capacity(NTHREADS as usize);
    for _ in 0..NTHREADS {
        //`recv`方法从通道中选择一条消息
        //如果没有可用消息，`recv`将阻塞当前线程
        ids.push(rx.recv());
    }

    //等待线程完成所有剩余的工作
    for child in children {
        child.join().expect("oops! the child thread panicked");
    }

    //显示消息发送的顺序
    println!("{:?}", ids);
}

fn path() {
    use std::path::Path;

    // Create a `Path` from an `&'static str`
    let path = Path::new(".");

    // The `display` method returns a `Show`able structure
    let _display = path.display();

    // `join` merges a path with a byte container using the OS specific
    // separator, and returns the new path
    let new_path = path.join("a").join("b");

    // Convert the path into a string slice
    match new_path.to_str() {
        None => panic!("new path is not a valid UTF-8 sequence"),
        Some(s) => println!("new path is {}", s),
    }
}

//打开 读取文件
fn file_open() {
    use std::error::Error;
    use std::fs::File;
    use std::io::prelude::*;
    use std::path::Path;

    let path = Path::new("./hello.txt");
    let display = path.display();

    let mut file = match File::open(&path) {
        Err(why) => panic!("couldn't open {}: {}", display, why.description()),
        Ok(file) => file,
    };

    let mut s = String::new();
    match file.read_to_string(&mut s) {
        Err(why) => panic!("couldn't read {}: {}", display, why.description()),
        Ok(_) => print!("{} contains:\n{}", display, s),
    }
}

//创建 写入文件
fn file_create() {
    static LOREM_IPSUM: &str = "hello world 3";

    use std::error::Error;
    use std::fs::File;
    use std::io::prelude::*;
    use std::path::Path;

    let path = Path::new("./lorem_ipsum.txt");
    let display = path.display();
    //create静态方法以只写模式打开文件。如果文件已经存在，则旧内容将被销毁。否则，将创建一个新文件。
    let mut file = match File::create(&path) {
        Err(why) => panic!("couldn't create {}: {}", display, why.description()),
        Ok(file) => file,
    };

    match file.write_all(LOREM_IPSUM.as_bytes()) {
        Err(why) => panic!("couldn't write to {}: {}", display, why.description()),
        Ok(_) => println!("successfully wrote to {}", display),
    }
}

//按行读取文件
fn file_read_lines() {
    use std::fs::File;
    use std::io::{self, BufRead};
    use std::path::Path;

    if let Ok(lines) = read_lines("./world.txt") {
        for line in lines {
            if let Ok(ip) = line {
                println!("{}", ip);
            }
        }
    }

    fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>> where P: AsRef<Path>, {
        let file = File::open(filename)?;
        //返回文件上的迭代器
        Ok(io::BufReader::new(file).lines())
    }
}

//子进程
fn child_processes() {
    use std::process::Command;

    let output = Command::new("rustc").arg("--version").output().unwrap_or_else(|e| {
        panic!("failed to execute process: {}", e)
    });

    if output.status.success() {
        let s = String::from_utf8_lossy(&output.stdout);
        print!("rustc succeeded and stdout was:\n{}", s);
    } else {
        let s = String::from_utf8_lossy(&output.stderr);

        print!("rustc failed and stderr was:\n{}", s);
    }
}

fn child_processes_pipes() {
    use std::error::Error;
    use std::io::prelude::*;
    use std::process::{Command, Stdio};

    static PANGRAM: &'static str = "the quick brown fox jumped over the lazy dog\n";

    //wc 执行命令
    let process = match Command::new("wc")
        .stdin(Stdio::piped())
        .stdout(Stdio::piped())
        .spawn() {
        Err(why) => panic!("couldn't spawn wc: {}", why.description()),
        Ok(process) => process,
    };

    //执行wc命令，提供输入PANGRAM字符串
    match process.stdin.unwrap().write_all(PANGRAM.as_bytes()) {
        Err(why) => panic!("couldn't write to wc stdin: {}", why.description()),
        Ok(_) => println!("sent pangram to wc"),
    }

    let mut s = String::new();
    match process.stdout.unwrap().read_to_string(&mut s) {
        Err(why) => panic!("couldn't read wc stdout: {}", why.description()),
        Ok(_) => print!("wc responded with:\n{}", s),
    }
}

fn child_processes_wait() {
    use std::process::Command;

    //如果您想等待process::Child完成，则必须调用Child::wait，它将返回一个process::ExitStatus
    let mut child = Command::new("sleep").arg("5").spawn().unwrap();
    let _result = child.wait().unwrap();

    println!("reached end of main");
}

//文件系统 常见操作
fn filesystem_operations() {
    use std::fs;
    use std::fs::{File, OpenOptions};
    use std::io;
    use std::io::prelude::*;
    use std::os::unix;
    use std::path::Path;

    fn cat(path: &Path) -> io::Result<String> {
        let mut f = File::open(path)?;
        let mut s = String::new();
        match f.read_to_string(&mut s) {
            Ok(_) => Ok(s),
            Err(e) => Err(e),
        }
    }

    //使用 ?
    fn cat2(path: &Path) -> io::Result<String> {
        let mut f = File::open(path)?;
        let mut s = String::new();
        f.read_to_string(&mut s)?;
        Ok(s)
    }

    fn echo(s: &str, path: &Path) -> io::Result<()> {
        let mut f = File::create(path)?;
        f.write_all(s.as_bytes())
    }

    fn touch(path: &Path) -> io::Result<()> {
        match OpenOptions::new().create(true).write(true).open(path) {
            Ok(_) => Ok(()),
            Err(e) => Err(e),
        }
    }

    println!("`mkdir a`");
    match fs::create_dir("a") {
        Err(why) => println!("! {:?}", why.kind()),
        Ok(_) => {}
    }

    println!("`echo hello > a/b.txt`");
    echo("hello", &Path::new("a/b.txt")).unwrap_or_else(|why| {
        println!("! {:?}", why.kind());
    });

    println!("`mkdir -p a/c/d`");
    fs::create_dir_all("a/c/d").unwrap_or_else(|why| {
        println!("! {:?}", why.kind());
    });

    println!("`touch a/c/e.txt`");
    touch(&Path::new("a/c/e.txt")).unwrap_or_else(|why| {
        println!("! {:?}", why.kind());
    });

    println!("`ln -s ../b.txt a/c/b.txt`");
    if cfg!(target_family = "unix") {
        unix::fs::symlink("../b.txt", "a/c/b.txt").unwrap_or_else(|why| {
            println!("! {:?}", why.kind());
        });
    }

    println!("`cat a/c/b.txt`");
    match cat(&Path::new("a/c/b.txt")) {
        Err(why) => println!("! {:?}", why.kind()),
        Ok(s) => println!("> {}", s),
    }

    println!("`ls a`");
    // Read the contents of a directory, returns `io::Result<Vec<Path>>`
    match fs::read_dir("a") {
        Err(why) => println!("! {:?}", why.kind()),
        Ok(paths) => for path in paths {
            println!("> {:?}", path.unwrap().path());
        },
    }

    println!("`rm a/c/e.txt`");
    fs::remove_file("a/c/e.txt").unwrap_or_else(|why| {
        println!("! {:?}", why.kind());
    });

    println!("`rmdir a/c/d`");
    fs::remove_dir("a/c/d").unwrap_or_else(|why| {
        println!("! {:?}", why.kind());
    });
}

//获取命令行传进来的参数
fn program_arguments() {
    use std::env;

    let args: Vec<String> = env::args().collect();

    //第一个参数是用于调用程序的路径。
    println!("My path is {}.", args[0]);

    //其余参数是传递的命令行参数。
    //./args arg1 arg2
    println!("I got {:?} arguments: {:?}.", args.len() - 1, &args[1..]);
}

//参数解析
fn argument_parsing() {
    use std::env;

    fn increase(number: i32) {
        println!("{}", number + 1);
    }

    fn decrease(number: i32) {
        println!("{}", number - 1);
    }

    fn help() {
        println!("usage:
match_args <string>
    Check whether given string is the answer.
match_args {{increase|decrease}} <integer>
    Increase or decrease given integer by one.");
    }
    let args: Vec<String> = env::args().collect();

    match args.len() {
        1 => {
            println!("My name is 'match_args'. Try passing some arguments!");
        }
        2 => {
            match args[1].parse() {
                Ok(42) => println!("This is the answer!"),
                _ => println!("This is not the answer."),
            }
        }
        3 => {
            let cmd = &args[1];
            let num = &args[2];
            // parse the number
            let number: i32 = match num.parse() {
                Ok(n) => {
                    n
                }
                Err(_) => {
                    eprintln!("error: second argument not an integer");
                    help();
                    return;
                }
            };
            match &cmd[..] {
                "increase" => increase(number),
                "decrease" => decrease(number),
                _ => {
                    eprintln!("error: invalid command");
                    help();
                }
            }
        }
        _ => {
            help();
        }
    }
}

//使用外部函数
fn foreign_function_interface() {
    use std::fmt;

    //这个外部块链接到libm库
    #[link(name = "m")]
    extern {
        fn csqrtf(z: Complex) -> Complex;

        fn ccosf(z: Complex) -> Complex;
    }

    //调用外部函数是不安全的，需要unsafe
    fn cos(z: Complex) -> Complex {
        unsafe { ccosf(z) }
    }

    //单精度复数的最小实现
    #[repr(C)]
    #[derive(Clone, Copy)]
    struct Complex {
        re: f32,
        im: f32,
    }

    impl fmt::Debug for Complex {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            if self.im < 0. {
                write!(f, "{}-{}i", self.re, -self.im)
            } else {
                write!(f, "{}+{}i", self.re, self.im)
            }
        }
    }

    // z = -1 + 0i
    let z = Complex { re: -1., im: 0. };

    let z_sqrt = unsafe { csqrtf(z) };

    println!("the square root of {:?} is {:?}", z, z_sqrt);

    println!("cos({:?}) = {:?}", z, cos(z));
}