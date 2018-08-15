package cn.edu.jxnu.designpattern;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:抽象工厂：为创建一组相关或相互依赖的对象提供一个接口，而且无须指定它们的具体类
 * @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午9:54:19
 */
/**
 * 使用场景： 一个对象族（或是一组没有任何关系的对象）都有相同的约束。 涉及不同操作系统的时候，都可以考虑使用抽象工厂模式
 * 
 */
public abstract class AbstractFactoryMethod {
	// 创建A产品家族
	public abstract AbstractProductA createProductA();

	// 创建B产品家族
	public abstract AbstractProductB createProductB();
}

class AbstractProductA {

}

class AbstractProductB {

}