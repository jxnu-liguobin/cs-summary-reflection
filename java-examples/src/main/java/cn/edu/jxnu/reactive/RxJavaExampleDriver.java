/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.reactive;

/**
 * rxJava 生产消息
 *
 * @author 梦境迷离
 * @version v1.0
 * @since 2019-12-08
 */
public class RxJavaExampleDriver {

    private static final RxJavaExample rx_java_example = new RxJavaExample();

    public static void main(String[] args) {
        // 生产事件
        String[] strings = {"a", "b", "c"};
        rx_java_example.observe(strings);
    }
}
