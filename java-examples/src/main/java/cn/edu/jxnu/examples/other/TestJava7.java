/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.other;

/**
 * 测试
 *
 * @author 梦境迷离
 * @time 2018-08-15
 */
public class TestJava7 extends Thread {

    public static void main(String[] args) {
        TestJava7 t = new TestJava7();
        TestJava7 s = new TestJava7();
        t.start();
        System.out.println("one.");
        s.start();
        System.out.println("two.");
    }

    public void run() {
        System.out.println("Thread");
    }
}
