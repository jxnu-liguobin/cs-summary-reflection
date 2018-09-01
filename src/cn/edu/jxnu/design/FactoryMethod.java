package cn.edu.jxnu.design;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使一个类的实例化延迟到其子类。
 * @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午9:45:12
 */
/**
 * 简单工厂模式： 一个模块仅需要一个工厂类，没有必要把它产生出来，使用静态的方法
 * 多个工厂类： 每个人种（具体的产品类）都对应了一个创建者，每个创建者独立负责创建对应的产品对象，非常符合单一职责原则
 */
/**
 * 代替单例模式： 单例模式的核心要求就是在内存中只有一个对象，通过工厂方法模式也可以只在内存中生产一个对象 延迟初始化：
 * ProductFactory负责产品类对象的创建工作，并且通过prMap变量产生一个缓存，对需要再次被重用的对象保留
 * 使用场景：jdbc连接数据库，硬件访问，降低对象的产生和销毁
 *
 */
public class FactoryMethod extends Creator {
	// Product为抽象产品类负责定义产品的共性，实现对事物最抽象的定义；
	// Creator为抽象创建类，也就是抽象工厂，具体如何创建产品类是由具体的实现工厂FactoryMethod完成的。
	@SuppressWarnings({ "unchecked", "deprecation" })
	public <T extends Product> T createProduct(Class<T> c) {
		Product product = null;
		try {
			product = (Product) Class.forName(c.getName()).newInstance();
		} catch (Exception e) {
			// 异常处理
		}
		return (T) product;
	}
}

class Creator {

}

class Product {

}