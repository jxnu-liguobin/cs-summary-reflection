package cn.edu.jxnu.examples.design;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:
 * @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午11:11:46
 */
// 懒汉式写法（线程安全）
public class Singleton_others {
	private static Singleton_others instance;

	private Singleton_others() {
	}

	public static synchronized Singleton_others getInstance() {
		if (instance == null) {
			instance = new Singleton_others();
		}
		return instance;
	}
}

// 饿汉式写法
class Singleton_1 {
	private static Singleton_1 instance = new Singleton_1();

	private Singleton_1() {
	}
	public static Singleton_1 getInstance() {
		return instance;
	}

}

// 静态内部类
class Singleton_2 {
	private static class SingletonHolder {
		private static final Singleton_2 INSTANCE = new Singleton_2();
	}

	private Singleton_2() {
	}

	public static final Singleton_2 getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
}

class Resource{
	
}

// 枚举 Singleton.INSTANCE.getInstance()
enum Singleton {
	
	INSTANCE;
	private Resource instance;
	Singleton() {
		 instance = new Resource();
	}
    public Resource getInstance() {
        return instance;
    }
}
