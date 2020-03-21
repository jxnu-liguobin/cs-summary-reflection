package cn.edu.jxnu.reactive;

import java.io.Serializable;

/**
 * 副作用的演示
 *
 * @author 梦境迷离
 * @version v1.0
 * @since 2019-12-07
 */
public class SideEffecting implements Serializable, Cloneable {
    private int count;

    public SideEffecting(int count) {
        this.count = count;
    }

    //具有副作用的函数（方法）：多次调用，得到的值是不一样的。
    public int next() {
        this.count += Math.incrementExact(this.count);
        return this.count;
    }
}
