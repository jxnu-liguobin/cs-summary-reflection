use std::sync::{Arc, mpsc, Mutex};
use std::thread;
use std::time::Duration;

pub fn create_thread() {
    ///新线程将在主线程结束时停止，无论它是否已完成运行
    thread::spawn(|| {
        for i in 1..10 {
            println!("hi number {} from the spawned thread!", i);
            thread::sleep(Duration::from_millis(1));
        }
    });

    for i in 1..5 {
        println!("hi number {} from the main thread!", i);
        thread::sleep(Duration::from_millis(1));//短暂停止，最终仍取决于操作系统如何调度线程。
    }
}

pub fn join_thread() {
    //返回值保存在变量handle中
    let handle = thread::spawn(|| {
        for i in 1..10 {
            println!("hi number {} from the spawned thread!", i);
            thread::sleep(Duration::from_millis(1));
        }
    });

    //handle.join().unwrap(); //阻塞当前线程，先执行thread::spawn，再执行main（main中for在后面）
    for i in 1..5 {
        println!("hi number {} from the main thread!", i);
        thread::sleep(Duration::from_millis(1));
    }
    ///阻塞当前正在运行的线程，直到由该句柄表示的线程终止
    handle.join().unwrap();//即thread::spawn先执行，main后执行
}

pub fn use_var_thread() {
    ///尝试在另一个线程中使用由主线程创建的向量
    let v = vec![1, 2, 3];
    let handle = thread::spawn(move || {//必须在闭包前加move，强制获取v变量的所有权而不是借用这个值
        println!("Here's a vector: {:?}", v);
    });
    //v不能再被主线程使用，所有权已经转移
    handle.join().unwrap();
}

pub fn channel_thread() {
    ///发送器和接收器
    let (tx, rx) = mpsc::channel();
    //产生的线程需要拥有通道的发送端才能通过通道发送消息。
    thread::spawn(move || {
        //tx移动到新线程，并开始发送val值
        let val = String::from("hi");
        //在此之后val被移到tx，无法再次使用
        tx.send(val).unwrap();
    });
    //使用rx接收器接收消息
    let received = rx.recv().unwrap();//try_recv方法不会阻塞
    println!("Got: {}", received);
}

pub fn send_multi_msg() {
    ///使用channel间隔着发送多个消息
    let (tx, rx) = mpsc::channel();
    thread::spawn(move || {
        let vals = vec![
            String::from("hi"),
            String::from("from"),
            String::from("the"),
            String::from("thread"),
        ];

        for val in vals {
            tx.send(val).unwrap();
            thread::sleep(Duration::from_secs(1));
        }
    });

    ///使用rx接收，将rx视为迭代器
    for received in rx {
        println!("Got: {}", received);
    }
}

pub fn copy_tx() {
    let (tx, rx) = mpsc::channel();
    ///克隆一个发送器
    let tx1 = mpsc::Sender::clone(&tx);
    thread::spawn(move || {
        let vals = vec![
            String::from("hi"),
            String::from("from"),
            String::from("the"),
            String::from("thread"),
        ];

        for val in vals {
            tx1.send(val).unwrap();
            thread::sleep(Duration::from_secs(1));
        }
    });

    thread::spawn(move || {
        let vals = vec![
            String::from("more"),
            String::from("messages"),
            String::from("for"),
            String::from("you"),
        ];

        for val in vals {
            tx.send(val).unwrap();
            thread::sleep(Duration::from_secs(1));
        }
    });

    for received in rx {
        println!("Got: {}", received);
    }
}

pub fn mutex_thread() {
    let m = Mutex::new(5);
    {
        let mut num = m.lock().unwrap();
        *num = 6;
    }

    println!("m = {:?}", m);
}

pub fn mutex_multi_thread() {
    ///Arc是原子的，Rc不是
    let counter = Arc::new(Mutex::new(0));
    //Mutex Arc 均实现了send sync两个接口，这两个接口都是标记接口（特质）
    //实现send特质可以在线程间转移同一变量的所有权
    //实现sync特质可以允许多个线程访问同一变量
    let mut handles = vec![];
    for _ in 0..10 {
        let counter = Arc::clone(&counter);
        let handle = thread::spawn(move || {
            let mut num = counter.lock().unwrap();
            *num += 1;
        });
        handles.push(handle);
    }
    for handle in handles {
        handle.join().unwrap();
    }
    println!("Result: {}", *counter.lock().unwrap());
}

