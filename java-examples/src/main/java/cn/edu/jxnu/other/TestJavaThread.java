/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.other;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * H1,H2,H2_1,H3 四个线程 H1和H2,H3三个是异步的，但前三个与H4是同步关系
 * 又由于H2有子线程H2_1,依赖于H2,则等价于，H1,H2_1,H3是并行，若H2_1执行之前，H2没有完成，应该阻塞，待H2完成，且H1,H2_1,H3全部完成，才可以执行H4
 *
 * @author 梦境迷离
 * @date 21点51分
 */
public class TestJavaThread {

    private static final String H1 = "H1任务";
    private static final String H2 = "H2任务";
    private static final String H2_1 = "H2_1任务";
    private static final String H3 = "H3任务";
    private static final String H4 = "H4任务";

    static ThreadB taskB = new ThreadB(H2); // B同步
    static Thread threadB = new Thread(taskB);

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        CallableThread taskA = new CallableThread(H1); // A异步
        CallableThread taskC = new CallableThread(H3); // C异步
        CallableThread taskB_1 = new CallableThread(H2_1); // B-1异步

        // 依此启动线程
        Future<String> futureA = executor.submit(taskA);
        threadB.start();
        Future<String> futureC = executor.submit(taskC);
        Future<String> futureB_1 = executor.submit(taskB_1);

        while (true) {
            if (futureA.isDone() && futureC.isDone() && futureB_1.isDone()) {
                // 保证前面四个线程已经执行了
                System.out.println(H4); // 开启新的线程并执行H4即可
                break;
            }
        }
        System.out.println("finished");
    }

    // H1,H2_1,H3是并行的
    static class CallableThread implements Callable<String> {
        private String id;

        public CallableThread(String id) {
            this.id = id;
        }

        public String call() {
            // 如果此时执行H2_1，前提需要保证开启H2线程，并且H2执行完成才可以继续执行本（H2_1）线程
            if (H2_1.equals(this.id)) {
                try {
                    // 不安全
                    threadB.join();
                    // 模拟时间
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(this.id);
            return this.id;
        }
    }

    // H2
    static class ThreadB implements Runnable {
        private String id;

        public ThreadB(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.id);
        }
    }
}
