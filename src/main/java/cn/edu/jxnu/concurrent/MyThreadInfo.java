package cn.edu.jxnu.concurrent;

/**
 * 线程实例对象的属性值
 */
public class MyThreadInfo extends Thread {

	@Override // 可以省略
	public void run() {
		System.out.println("MyThreadInfo 的线程实例正在执行任务");
		// System.exit(1);
	}

	public static void main(String[] args) {
		MyThreadInfo thread = new MyThreadInfo();
		thread.start();

		System.out.print("MyThreadInfo 的线程对象 \n" + "线程唯一标识符：" + thread.getId() + "\n" + "线程名称：" + thread.getName()
				+ "\n" + "线程状态：" + thread.getState() + "\n" + "线程优先级：" + thread.getPriority());
	}
}