package cn.edu.jxnu.concurrent;

public class ThreadStateDemo extends Thread {

	public ThreadStateDemo(String name) {
		super(name);
	}

	/**
	 * sleep、wait可以响应中断，wait可以有参数
	 */
	public void run() {
		synchronized (this) {
			try {
				Thread.sleep(1000); // 使当前线阻塞 1 s，确保主程序的 t1.wait(); 执行之后再执行 notify()
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " call notify()");
			// 唤醒当前的wait线程
			this.notify();
		}
	}

	public static void main(String[] args) {
		ThreadStateDemo t1 = new ThreadStateDemo("t1");
		synchronized (t1) {
			// 启动“线程t1”
			System.out.println(Thread.currentThread().getName() + " start t1");
			t1.start();
			// 主线程等待t1通过notify()唤醒。
			System.out.println(Thread.currentThread().getName() + " wait()");
			try {
				t1.wait();
			} catch (java.lang.InterruptedException e) {
				e.printStackTrace();
			} // 不是使t1线程等待，而是当前执行wait的线程等待
			System.out.println(Thread.currentThread().getName() + " continue");

		}
	}
}
