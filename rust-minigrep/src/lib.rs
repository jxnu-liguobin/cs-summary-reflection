use std::env;
use std::error::Error;
use std::fs;

//rust的迭代器和闭包不影响运行时性能
///定义数据结构
pub struct Config {
    pub query: String,
    pub filename: String,
    pub case_sensitive: bool,
}

///实现结构体，带有new函数用于构造，使得代码更加通用
impl Config {
    ///使用迭代器重构 new函数
    pub fn new(mut args: std::env::Args) -> Result<Config, &'static str> {
        args.next();
        let query = match args.next() {
            Some(arg) => arg,
            None => return Err("Didn't get a query string"),
        };
        let filename = match args.next() {
            Some(arg) => arg,
            None => return Err("Didn't get a file name"),
        };
        let case_sensitive = env::var("CASE_INSENSITIVE").is_err();
        Ok(Config {
            query,
            filename,
            case_sensitive,
        })
    }
    //    pub fn new(args: &[String]) -> Result<Config, &'static str> {
    //        //构造时限制参数
    //        if args.len() < 3 {
    //            //panic!("not enough arguments");
    //            return Err("not enough arguments");
    //        }
    //        let query = args[1].clone();
    //        let filename = args[2].clone();
    //        //获取环境变量
    //        let case_sensitive = env::var("CASE_INSENSITIVE").is_err();
    //        Ok(Config { query, filename, case_sensitive })
    //    }
}

///现在，只知道这Box<dyn Error>意味着函数将返回实现该Error特征的类型，但是我们不必指定返回值将是哪种特定类型。
///这使我们可以灵活地返回在不同错误情况下可能属于不同类型的错误值。该dyn关键字是“dynamic”的缩写。//trait对象 Box<dyn Error>
pub fn run(config: Config) -> Result<(), Box<dyn Error>> {
    let contents = fs::read_to_string(config.filename)?;
    let results = if config.case_sensitive {
        search(&config.query, &contents)
    } else {
        search_case_insensitive(&config.query, &contents)
    };
    for line in results {
        println!("{}", line);
    }
    Ok(())
}

pub fn search<'a>(query: &str, contents: &'a str) -> Vec<&'a str> {
    let mut results = Vec::new();
    for line in contents.lines() {
        if line.contains(query) {
            results.push(line);
        }
    }
    results
}

///忽略大小写的查询
pub fn search_case_insensitive<'a>(query: &str, contents: &'a str) -> Vec<&'a str> {
    let query = query.to_lowercase();
    let mut results = Vec::new();
    for line in contents.lines() {
        if line.to_lowercase().contains(&query) {
            results.push(line);
        }
    }

    results
}

///使用迭代器和闭包的简洁查询函数
pub fn search2<'a>(query: &str, contents: &'a str) -> Vec<&'a str> {
    contents
        .lines()
        .filter(|line| line.contains(query))
        .collect()
}
