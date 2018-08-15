package cn.edu.jxnu.designpattern;

import java.util.HashMap;

/**   
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:享元：定义：使用共享对象可有效地支持大量的细粒度的对象。
 * @Package: cn.jxnu.edu.designpattern 
 * @author: 梦境迷离   
 * @date: 2018年3月20日 上午11:04:42 
 */
/**
对象的信息分为两个部分：内部状态（intrinsic）与外部状态（extrinsic）。
● 内部状态
内部状态是对象可共享出来的信息，存储在享元对象内部并且不会随环境改变而改变。
● 外部状态
外部状态是对象得以依赖的一个标记，是随环境改变而改变的、不可以共享的状态。
● Flyweight——抽象享元角色
它简单地说就是一个产品的抽象类，同时定义出对象的外部状态和内部状态的接口或实现。
● ConcreteFlyweight——具体享元角色
具体的一个产品类，实现抽象角色定义的业务。该角色中需要注意的是内部状态处理应该与环境无关，不应该出现一个操作改变了内部状态，同时修改了外部状态，这是绝对不允许的。
● unsharedConcreteFlyweight——不可共享的享元角色
不存在外部状态或者安全要求（如线程安全）不能够使用共享技术的对象，该对象一般不会出现在享元工厂中。
● FlyweightFactory——享元工厂
职责非常简单，就是构造一个池容器，同时提供从池中获得对象的方法。
 *
 */
// 享元工厂
public class FlyweightFactory {
	// 定义一个池容器
	private static HashMap<String, Flyweight> pool = new HashMap<String, Flyweight>();

	// 享元工厂
	public static Flyweight getFlyweight(String Extrinsic) {
		// 需要返回的对象
		Flyweight flyweight = null;
		// 在池中没有该对象
		if (pool.containsKey(Extrinsic)) {
			flyweight = pool.get(Extrinsic);
		} else {
			// 根据外部状态创建享元对象
			flyweight = new ConcreteFlyweight(Extrinsic);
			// 放置到池中
			pool.put(Extrinsic, flyweight);
		}
		return flyweight;
	}
}

// 抽象享元
abstract class Flyweight {

}

// 具体享元
class ConcreteFlyweight extends Flyweight {

	public ConcreteFlyweight(String extrinsic) {
	}

}

// unsharedConcreteFlyweight 不可共享的享元
// 不存在外部状态或者安全要求（如线程安全）不能够使用共享技术的对象，该对象一般不会出现在享元工厂中。
class unsharedConcreteFlyweight {

}