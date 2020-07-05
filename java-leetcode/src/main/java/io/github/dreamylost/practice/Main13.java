/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.concurrent.locks.ReentrantLock;

public class Main13 {
    private static int i = 0;
    private static ThreadRunnableToAdd add = new ThreadRunnableToAdd();
    private static ThreadRunnableSub sub = new ThreadRunnableSub();
    // 多余， 实际只需要对i加一个锁
    // // 对i整体进行互斥
    // static synchronized int get() {
    // return i;
    // }
    //
    // // 对i整体进行互斥
    // static synchronized void set(int ii) {
    // i = ii;
    // }

    static ReentrantLock lock1 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(add, "add_1");
        Thread t2 = new Thread(add, "add_2");
        Thread t3 = new Thread(sub, "sub_1");
        Thread t4 = new Thread(sub, "sub_2");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        // 让main线程等待
        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }

    /**
     * 对i减法
     *
     * @author Mr.Li
     */
    static class ThreadRunnableSub implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                lock1.lock();
                // 对四个线程直接的取值和更新值进行同步 多余
                // set(get() - 1);\
                i--;
                System.out.println("currName:" + Thread.currentThread().getName() + " i:" + i);
            } catch (Exception e) {
            } finally {
                lock1.unlock();
            }
        }
    }

    /**
     * 对i加法
     *
     * @author Mr.Li
     */
    static class ThreadRunnableToAdd implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                lock1.lock();
                // 对四个线程直接的取值和更新值进行同步 多余
                // set(get() + 1);
                i++;
                System.out.println("currName:" + Thread.currentThread().getName() + " i:" + i);
            } catch (Exception e) {
            } finally {
                lock1.unlock();
            }
        }
    }
}
