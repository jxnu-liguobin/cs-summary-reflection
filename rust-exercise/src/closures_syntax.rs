use std::thread;
use std::time::Duration;

///闭包语法
pub fn closures_syntax() {
    fn simulated_expensive_calculation(intensity: u32) -> u32 {
        println!("calculating slowly...");
        //暂停两秒钟
        thread::sleep(Duration::from_secs(2));
        intensity
    }

    let simulated_user_specified_value = 10;
    let simulated_random_number = 7;

    generate_workout4(simulated_user_specified_value, simulated_random_number);

    ///初始版本
    fn generate_workout(intensity: u32, random_number: u32) {
        if intensity < 25 {
            println!(
                "Today, do {} pushups!",
                simulated_expensive_calculation(intensity)
            );
            println!(
                "Next, do {} situps!",
                simulated_expensive_calculation(intensity)
            );
        } else {
            if random_number == 3 {
                println!("Take a break today! Remember to stay hydrated!");
            } else {
                println!(
                    "Today, run for {} minutes!",
                    simulated_expensive_calculation(intensity)
                );
            }
        }
    }

    ///将调研提取到一个地方，存储在expensive_result变量中
    fn generate_workout2(intensity: u32, random_number: u32) {
        let expensive_result = simulated_expensive_calculation(intensity);

        if intensity < 25 {
            println!("Today, do {} pushups!", expensive_result);
            println!("Next, do {} situps!", expensive_result);
        } else {
            if random_number == 3 {
                println!("Take a break today! Remember to stay hydrated!");
            } else {
                println!("Today, run for {} minutes!", expensive_result);
            }
        }
    }

    ///使用闭包重构以存储代码
    fn generate_workout3(intensity: u32, random_number: u32) {
        ///定义一个闭包并将其存储在 expensive_closure变量中
        let expensive_closure = |num| {
            //如果我们有多个参数，则将它们用逗号分隔，例如|param1, param2|，显示定义类型：|num: u32| -> u32
            println!("calculating slowly...");
            thread::sleep(Duration::from_secs(2));
            num
        };

        if intensity < 25 {
            println!("Today, do {} pushups!", expensive_closure(intensity));
            println!("Next, do {} situps!", expensive_closure(intensity));
        } else {
            if random_number == 3 {
                println!("Take a break today! Remember to stay hydrated!");
            } else {
                println!("Today, run for {} minutes!", expensive_closure(intensity));
            }
        }
    }

    ///使用通用参数和Fn特质存储闭包
    fn generate_workout4(intensity: u32, random_number: u32) {
        let mut expensive_result = Cacher::new(|num| {
            println!("calculating slowly...");
            thread::sleep(Duration::from_secs(2));
            num
        });
        if intensity < 25 {
            println!("Today, do {} pushups!", expensive_result.value(intensity));
            println!("Next, do {} situps!", expensive_result.value(intensity));
        } else {
            if random_number == 3 {
                println!("Take a break today! Remember to stay hydrated!");
            } else {
                println!(
                    "Today, run for {} minutes!",
                    expensive_result.value(intensity)
                );
            }
        }
    }

    fn add_one_v1(x: u32) -> u32 {
        x + 1
    } //显示函数定义
    let add_one_v2 = |x: u32| -> u32 { x + 1 }; //完整注释的闭包定义
    let add_one_v3 = |x: u32| x + 1; //删除类型，没有使用该闭包时不加u32会报错
    let add_one_v4 = |x: u32| x + 1; //删除括号，没有使用该闭包时不加u32会报错

    ///两次使用闭包传入了不同类型参数，将会报错
    ///如果尝试对同一闭包使用其他不同类型，则会收到类型错误。
    let example_closure = |x| x;
    let s = example_closure(String::from("hello"));
    //let n = example_closure(5);

    //捕获非传入参数x
    //闭包可以通过三种方式从其环境中捕获值，它们直接映射到函数可以采用参数的三种方式：获得所有权FnOnce，可变借入FnMut和不可变借入Fn
    let x = 4;
    let equal_to_x = |z| z == x;
    let y = 4;
    assert!(equal_to_x(y));

    //函数不支持
    //    let x = 4;
    //    fn equal_to_x(z: i32) -> bool { z == x }
    //    let y = 4;
    //    assert!(equal_to_x(y));
}

///定义一个包含闭包的结构，闭包类型Fn(u32) -> u32
struct Cacher<T>
where
    T: Fn(u32) -> u32,
{
    calculation: T,
    value: Option<u32>,
}

///实现结构
impl<T> Cacher<T>
where
    T: Fn(u32) -> u32,
{
    fn new(calculation: T) -> Cacher<T> {
        //没有执行时默认是None
        Cacher {
            calculation,
            value: None,
        }
    }

    ///没有时才计算，否则返回已有值
    fn value(&mut self, arg: u32) -> u32 {
        match self.value {
            Some(v) => v,
            None => {
                //缺陷，可能在不同参数调用时返回相同值，需要使用HashMap来根据key判断是否可以使用旧值
                let v = (self.calculation)(arg);
                self.value = Some(v);
                v
            }
        }
    }
}

///测试
#[test]
fn call_with_different_values() {
    let mut c = Cacher::new(|a| a);

    let v1 = c.value(1);
    //失败
    let v2 = c.value(2);

    assert_ne!(v2, 2); //不等！！！！
}
