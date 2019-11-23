package cn.edu.jxnu.examples.design;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:原型：定义：用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。
 * @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午10:25:28
 */
/**
 * 原型模式通用代码：
 *
 */
/**
 原型模式实际上就是实现Cloneable接口，重写clone()方法。
使用原型模式的优点：
● 性能优良
原型模式是在内存二进制流的拷贝，要比直接new一个对象性能好很多，特别是要在一个循环体内产生大量的对象时，原型模式可以更好地体现其优点。
● 逃避构造函数的约束
这既是它的优点也是缺点，直接在内存中拷贝，构造函数是不会执行的（参见13.4节）。
使用场景：
● 资源优化场景
类初始化需要消化非常多的资源，这个资源包括数据、硬件资源等。
● 性能和安全要求的场景
通过new产生一个对象需要非常繁琐的数据准备或访问权限，则可以使用原型模式。
● 一个对象多个修改者的场景
一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时，可以考虑使用原型模式拷贝多个对象供调用者使用。
浅拷贝和深拷贝：
深拷贝不会影响拷贝对象，浅拷贝会，因为此时引用类型指向相同的地址
 *
 */
public class Prototype implements Cloneable {
	// 覆写父类Object方法
	@Override
	public Prototype clone() {
		Prototype prototypeClass = null;
		try {
			prototypeClass = (Prototype) super.clone();
		} catch (CloneNotSupportedException e) {
			// 异常处理
		}
		return prototypeClass;
	}
}
