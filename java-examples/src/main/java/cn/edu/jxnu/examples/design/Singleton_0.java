/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.design;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 *
 * @description: @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午11:11:46
 */
// 懒汉式写法（线程安全）
public class Singleton_0 {
    private static Singleton_0 instance;

    private Singleton_0() {}

    // 没有synchronized则非线程安全
    public static synchronized Singleton_0 getInstance() {
        if (instance == null) {
            instance = new Singleton_0();
        }
        return instance;
    }
}

// thread safe
// 饿汉式写法
class Singleton_1 {
    private static Singleton_1 instance = new Singleton_1();

    private Singleton_1() {}

    public static Singleton_1 getInstance() {
        return instance;
    }
}

// thread safe
// 静态内部类
class Singleton_2 {
    private static class SingletonHolder {
        private static final Singleton_2 INSTANCE = new Singleton_2();
    }

    private Singleton_2() {}

    public static final Singleton_2 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

class Resource {}

// thread safe
// 枚举 Singleton.INSTANCE.getInstance()
enum Singleton_3 {
    INSTANCE;
    private Resource instance;

    Singleton_3() {
        instance = new Resource();
    }

    public Resource getInstance() {
        return instance;
    }
}

// thread safe
class Singleton_4 {

    private static volatile Singleton_4 instance = new Singleton_4();

    private Singleton_4() {}

    public static Singleton_4 getInstance() {
        if (instance == null) {
            synchronized (Singleton_4.class) {
                if (instance == null) instance = new Singleton_4();
            }
        }
        return instance;
    }
}
