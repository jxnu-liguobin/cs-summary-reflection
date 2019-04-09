package cn.edu.jxnu.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 一直运行的线程，中断状态为 true
 */
public class InterruptedThread implements Runnable {

	@Override // 可以省略
	public void run() {
		// 一直 run
		while (true) {
		}
	}

	public static void main(String[] args) throws Exception {

		Thread interruptedThread = new Thread(new InterruptedThread(), "InterruptedThread");
		interruptedThread.start();

		TimeUnit.SECONDS.sleep(2);

		interruptedThread.interrupt();
		System.out.println("InterruptedThread interrupted is " + interruptedThread.isInterrupted());

		TimeUnit.SECONDS.sleep(2);
	}
}