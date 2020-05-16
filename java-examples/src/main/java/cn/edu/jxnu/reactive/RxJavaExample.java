package cn.edu.jxnu.reactive;

import io.reactivex.Observable;

/**
 * rxJava 订阅消息
 *
 * @author 梦境迷离
 * @version v1.0
 * @since 2019-12-08
 */
public class RxJavaExample {

    // 订阅事件并在收到时打印
    public void observe(String[] strings) {
        Observable.fromArray(strings).subscribe(s -> System.out.println("Reactived: " + s));
    }
}
