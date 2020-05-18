package cn.edu.jxnu.examples.lambda;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 *
 * @description:我这测试的LongAdder不如AtomicLong.可能是写法问题 @Package: cn.edu.jxnu.lambda
 * @author: 梦境迷离
 * @date: 2018年3月28日 上午8:18:25
 */

// 修改数据发送冲突时，使用cell数组策略，分离热点，如果cell上的更新依然发送冲突，系统会尝试创建新的cell或者将cell的数量加倍
// 把long分为小的数据存放在数组
public class LongAdderDemo {
    private static final int MAX_THREADS = 3; // 线程数
    private static final int TASK_COUNT = 3; // 任务数
    private static final int TARGET = 100000000; // 目标总数
    private static AtomicLong acount = new AtomicLong(0l); // 无锁原子操作
    private static LongAdder lacount = new LongAdder();
    private long count = 0;
    static CountDownLatch cdlsync = new CountDownLatch(TASK_COUNT);
    static CountDownLatch cdlatomic = new CountDownLatch(TASK_COUNT);
    static CountDownLatch cdladdr = new CountDownLatch(TASK_COUNT);

    /** @description:有锁加法 */
    protected synchronized long inc() {
        return count++;
    }

    /** @description:有锁 */
    protected synchronized long getCount() {
        return count;
    }

    /**
     * 使用锁同步的
     *
     * @author liguobin
     */
    static class SyncThread implements Runnable {
        protected String name;
        protected long starttime;
        protected LongAdderDemo out;

        public SyncThread(LongAdderDemo longAdderDemo, Long startTime) {
            this.out = longAdderDemo;
            this.starttime = startTime;
        }

        @Override
        public void run() {
            long v = out.getCount();
            while (v < TARGET) {
                v = out.inc();
            }
            long endtime = System.currentTimeMillis();
            System.out.println("SyncThread spend: " + (endtime - starttime) + "ms" + " v=" + v);
            cdlsync.countDown();
        }
    }

    /**
     * 使用原子操作
     *
     * @author liguobin
     */
    static class AtomicThread implements Runnable {
        protected String name;
        protected long starttime;

        public AtomicThread(Long startTime) {
            this.starttime = startTime;
        }

        @Override
        public void run() {
            long v = acount.get();
            while (v < TARGET) {
                v = acount.incrementAndGet();
            }
            long endtime = System.currentTimeMillis();
            System.out.println("AtomicThread spend: " + (endtime - starttime) + "ms" + " v=" + v);
            cdlatomic.countDown();
        }
    }

    /**
     * 使用LongAdder 避免伪共享，但不是使用padding而是使用@sun.misc.Contended
     *
     * @author liguobin
     */
    static class LongAddrThread implements Runnable {
        protected String name;
        protected long starttime;

        public LongAddrThread(Long startTime) {
            this.starttime = startTime;
        }

        @Override
        public void run() {
            long v = lacount.sum();
            while (v < TARGET) {
                lacount.increment();
                v = lacount.sum(); // 热点分离，需要求和
            }
            long endtime = System.currentTimeMillis();
            // v值会比其他两个方法大一点.....
            System.out.println("LongAddrThread spend: " + (endtime - starttime) + "ms" + " v=" + v);
            cdladdr.countDown();
        }
    }

    /** @description:测试三个方法 */
    public static void main(String[] args) throws InterruptedException {
        // 锁
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime = System.currentTimeMillis();
        SyncThread syncThread = new SyncThread(new LongAdderDemo(), startTime);
        for (int i = 0; i < TASK_COUNT; i++) {
            executorService.submit(syncThread); // 提交线程开始计算
        }
        cdlsync.await();
        executorService.shutdown();
        // atomic
        ExecutorService executorService2 = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime2 = System.currentTimeMillis();
        AtomicThread atomicThread = new AtomicThread(startTime2);
        for (int i = 0; i < TASK_COUNT; i++) {
            executorService2.submit(atomicThread); // 提交线程开始计算
        }
        cdlatomic.await();
        executorService2.shutdown();
        // LongAdder
        ExecutorService executorService3 = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime3 = System.currentTimeMillis();
        LongAddrThread longAddrThread = new LongAddrThread(startTime3);
        for (int i = 0; i < TASK_COUNT; i++) {
            executorService3.submit(longAddrThread); // 提交线程开始计算
        }
        cdladdr.await();
        executorService3.shutdown();
    }
}
