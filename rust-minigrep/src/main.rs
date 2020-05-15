use std::env;
use std::error::Error;
use std::fs;
use std::process;

use rust_minigrep::{run, Config};

///主函数，必须命令行执行（需要传参数）
///cargo run to poem.txt > output.txt 将结果输出到文件中
fn main() {
    //std::env::args_os返回OsString，与平台相关
    //let args: Vec<String> = env::args().collect();
    let args = env::args();
    let config = Config::new(args).unwrap_or_else(|err| {
        //将错误打印为标准错误
        eprintln!("Problem parsing arguments: {}", err);
        process::exit(1);
    });
    //println!("Searching for {}", config.query);
    //println!("In file {}", config.filename);
    if let Err(e) = run(config) {
        eprintln!("Application error: {}", e);
        process::exit(1);
    }
}

///相比下面而言更好的选择是将config作为一个结构体
fn parse_config2(args: &[String]) -> Config {
    let query = args[1].clone(); //clone的运行时成本在这里不考虑
    let filename = args[2].clone();
    let case_sensitive = false;
    Config {
        query,
        filename,
        case_sensitive,
    }
}

fn parse_config(args: &[String]) -> (&str, &str) {
    let query = &args[1];
    let filename = &args[2];
    (query, filename)
}

///测试模块
#[cfg(test)]
mod tests {
    use rust_minigrep::{search, search_case_insensitive};

    use super::*;

    #[test]
    fn one_result() {
        let query = "duct";
        let contents = "\
Rust:
safe, fast, productive.
Pick three.";

        assert_eq!(vec!["safe, fast, productive."], search(query, contents));
    }

    #[test]
    fn case_insensitive() {
        let query = "rUsT";
        let contents = "\
Rust:
safe, fast, productive.
Pick three.
Trust me.";

        assert_eq!(
            vec!["Rust:", "Trust me."],
            search_case_insensitive(query, contents)
        );
    }
}
