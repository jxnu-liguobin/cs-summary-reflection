package cn.edu.jxnu.examples.concurrent;

import java.util.concurrent.TimeUnit;

/** 安全终止线程 */
public class ThreadSafeStop {

    public static void main(String[] args) throws Exception {
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();
        // 睡眠 1 秒，通知 CountThread 中断，并终止线程
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();

        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();
        // 睡眠 1 秒，然后设置线程停止状态，并终止线程
        TimeUnit.SECONDS.sleep(1);
        two.stopSafely();
    }

    private static class Runner implements Runnable {

        private long i;

        // 终止状态，volatile是必须的
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                // 线程执行具体逻辑
                i++;
            }
            System.out.println("Count i = " + i);
        }

        public void stopSafely() {
            on = false;
        }
    }
}
