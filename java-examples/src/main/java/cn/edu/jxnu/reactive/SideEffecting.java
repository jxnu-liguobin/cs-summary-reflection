/* All Contributors (C) 2020 */
package cn.edu.jxnu.reactive;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 关于什么是副作用可以参考<a href="https://en.wikipedia.org/wiki/Side_effect_(computer_science)">维基百科</a>
 *
 * 副作用应该是来源于函数式编程。指的是函数调用时，除了返回值，函数还会影响函数外部其他东西（外部变量，I/O，参数内容）。
 * 如果函数没有任何副作用，我们称这个函数为<em>纯函数</em>，否则叫<em>非纯函数</em>
 * 纯函数的优点：1.不需要记住状态变量，心智负担小。2.可以缓存执行结果3.无状态，所以线程安全
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

    //1.修改外部变量
    public int next() {
        this.count += Math.incrementExact(this.count);
        return this.count;
    }

    //2.I/O
    public void writeFile(){
        File file = new File("sideEffect.txt");
    }

    //3.改变参数
    public void addInList(List<Integer> countList) {
        if (countList != null) {
            countList.add(1);
        }
    }
}
