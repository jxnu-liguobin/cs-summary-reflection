package cn.edu.jxnu.examples.lambda;

/** 马 接口可以拥有默认方法， 可以包含若干个实例方法 */
public interface Ihorse {
    void eat();

    default void run() {
        System.out.println("horse run");
    }
}
