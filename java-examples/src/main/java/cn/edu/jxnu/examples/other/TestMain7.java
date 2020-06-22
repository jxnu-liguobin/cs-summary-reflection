/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.other;

public class TestMain7 {

    private static int j;

    public static void main(String[] args) {
        TestMain7 many = new TestMain7();
        Inc inc = many.new Inc();
        Dec dec = many.new Dec();
        Thread threada = null;
        Thread threadb = null;
        for (int i = 0; i < 5; i++) {
            threada = new Thread(inc);
            threada.start();
            threadb = new Thread(dec);
            threadb.start();
        }

        try {
            threada.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            threadb.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(j);
    }

    private synchronized void inc() {
        j++;
    }

    private synchronized void dec() {
        j--;
    }

    class Inc implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inc();
            }
        }
    }

    class Dec implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                dec();
            }
        }
    }
}
