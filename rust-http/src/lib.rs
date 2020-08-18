use std::sync::{mpsc, Arc, Mutex};
use std::thread;

///定义线程交互消息
enum Message {
    NewJob(Job),
    Terminate,
}

///定义线程池抽象
pub struct ThreadPool {
    //存储线程工作者而不是直接存储线程
    workers: Vec<Worker>,
    //发送消息用
    sender: mpsc::Sender<Message>,
}

///要调用存储在Box<T>中的FnOnce闭包（这是我们的Job类型别名），闭包需要将自身从Box<T>中移出
trait FnBox {
    fn call_box(self: Box<Self>);
}

///实现FnOnce特质和FnBox特质
impl<F: FnOnce()> FnBox for F {
    //需要self: Box<Self>所有权
    fn call_box(self: Box<F>) {
        //将闭包移出Box<T>并调用闭包
        (*self)()
    }
}

type Job = Box<dyn FnBox + Send + 'static>;

///实现线程池
impl ThreadPool {
    //usize（负数的线程数没有任何意义），0是有效的usize类型
    pub fn new(size: usize) -> ThreadPool {
        assert!(size > 0, "ThreadPool size cannot less than 0");
        let (sender, receiver) = mpsc::channel();
        let receiver = Arc::new(Mutex::new(receiver)); //Arc共享接收器 Mutex任务互斥
                                                       //初始化大小
        let mut workers = Vec::with_capacity(size);
        for id in 0..size {
            //创建一些线程并将其存储在向量中
            workers.push(Worker::new(id, Arc::clone(&receiver))); //共享receiver
        }
        ThreadPool { workers, sender }
    }

    //参考thread::spawn对其参数的限制
    pub fn execute<F>(&self, f: F)
    where
        F: FnOnce() + Send + 'static,
    {
        self.sender.send(Message::NewJob(Box::new(f))).unwrap();
    }
}

///实现Drop特质
impl Drop for ThreadPool {
    fn drop(&mut self) {
        println!("Sending terminate message to all workers.");
        for _ in &mut self.workers {
            //发送消息关闭
            self.sender.send(Message::Terminate).unwrap();
        }
        println!("Shutting down all workers.");
        for worker in &mut self.workers {
            println!("Shutting down worker {}", worker.id);
            if let Some(thread) = worker.thread.take() {
                //take出工作线程并置为None
                thread.join().unwrap(); //等待主线程的
            }
        }
    }
}

///定义工作线程
struct Worker {
    id: usize,
    thread: Option<thread::JoinHandle<()>>,
}

///定义工作线程的实现
impl Worker {
    fn new(id: usize, receiver: Arc<Mutex<mpsc::Receiver<Message>>>) -> Worker {
        let thread = thread::spawn(move || {
            loop {
                let message = receiver.lock().unwrap().recv().unwrap();
                match message {
                    Message::NewJob(job) => {
                        println!("Worker {} got a job; executing.", id);
                        job.call_box();
                    }
                    //发送和接收Message值，如果Worker接收到Terminate则退出循环
                    Message::Terminate => {
                        println!("Worker {} was told to terminate.", id);
                        break;
                    }
                }
            }
        });
        Worker {
            id,
            thread: Some(thread),
        }
    }
}
