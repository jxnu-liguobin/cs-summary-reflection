package cn.edu.jxnu.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试主程序
 * 
 * @author 梦境迷离 
 * @time. 2017-7-27 下午3:48:01
 * @version V1.0
 */
public class ConnectionPoolTest {
	/**
	 * 可以看出，在资源一定的情况下（线程池中10个连接）随着客户线程的逐步增加，客户端出现超时无法获取连接的比率不断升高，虽然客户端会出现无法连接的情况
	 * ，但是按时返回，并告知客户端连接获取出现问题是一种保护机制。针对昂贵资源的获取，都应该加以超时限制。比如数据库连接。
	 * 
	 */
	static ConnectionPool pool = new ConnectionPool(10);
	// 保证所有ConnectionRunner同时开始
	// 具体参考CountDownLatch -->http://www.importnew.com/15731.html
	static CountDownLatch start = new CountDownLatch(1);
	// main线程将会等待所有ConnectionRunner结束后才能继续执行
	static CountDownLatch end;

	public static void main(String[] args) throws InterruptedException {
		// 线程数量，可以修改线程数量进行观察
		int threadCount = 10;
		end = new CountDownLatch(threadCount);
		int count = 10;
		// 一个提供原子操作的Integer的类
		AtomicInteger got = new AtomicInteger();
		AtomicInteger notGot = new AtomicInteger();
		for (int i = 0; i < threadCount; i++) {
			Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
			thread.start();
		}
		start.countDown();
		end.await();// wait()和notify()必须在synchronized的代码块中使用
					// 因为只有在获取当前对象的锁时才能进行这两个操作 否则会报异常
					// 而await()和signal()一般与Lock()配合使用
		System.out.println("total invoke :" + (threadCount * count));
		System.out.println("got connection :" + got);
		System.out.println("not got connection " + notGot);

	}

	static class ConnectionRunner implements Runnable {
		int count;
		AtomicInteger got;
		AtomicInteger notGot;

		public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
			this.count = count;
			this.got = got;
			this.notGot = notGot;

		}

		@Override
		public void run() {
			try {
				start.await();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			while (count > 0) {
				Connection connection = null;
				// 从线程池中获取连接，如果1000ms内无法获取，返回null
				// 分别统计连接获取的数量got和未获取到的数量notGot

				try {
					connection = pool.fetchConnection(1000);

					if (connection != null) {
						try {
							connection.createStatement();
							connection.commit();
						} catch (SQLException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} finally {
							pool.releaseConnection(connection);
							got.incrementAndGet();
						}

					} else {
						notGot.incrementAndGet();// 返回的是新值（即加1后的值）
					}
				} catch (InterruptedException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} finally {
					count--;
				}
			}

			end.countDown();

		}

	}

}