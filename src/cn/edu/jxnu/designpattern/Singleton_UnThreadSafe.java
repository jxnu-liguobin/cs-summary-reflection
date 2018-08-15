package cn.edu.jxnu.designpattern;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:线程不安全实例
 * @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午9:45:12
 */
/**
 * 解决办法：在getSingleton方法前加synchronized关键字，也可以在getSingleton方法内增加synchronized来实现。最优的办法是如通用代码那样写。
 * 双重检查锁 不好，可能存在构造溢出
 */
public class Singleton_UnThreadSafe {
	private static Singleton_UnThreadSafe singleton_UnThreadSafe = null;

	private Singleton_UnThreadSafe() {

	} // 通过该方法获得实例对象

	public static Singleton_UnThreadSafe getSingleton() {
		if (singleton_UnThreadSafe == null) {
			singleton_UnThreadSafe = new Singleton_UnThreadSafe();
		}
		return singleton_UnThreadSafe;
	}
}
