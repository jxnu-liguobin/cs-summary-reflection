package cn.edu.jxnu.design;

import java.util.Vector;

/**   
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:观察者：定义：定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖于它的对象都会得到通知并被自动更新。
 * @Package: cn.jxnu.edu.designpattern 
 * @author: 梦境迷离   
 * @date: 2018年3月20日 上午10:52:55 
 */
/**
● Subject被观察者
定义被观察者必须实现的职责，它必须能够动态地增加、取消观察者。它一般是抽象类或者是实现类，仅仅完成作为被观察者必须实现的职责：管理观察者并通知观察者。
● Observer观察者
观察者接收到消息后，即进行update（更新方法）操作，对接收到的信息进行处理。
● ConcreteSubject具体的被观察者
定义被观察者自己的业务逻辑，同时定义对哪些事件进行通知。
● ConcreteObserver具体的观察者
每个观察在接收到消息后的处理反应是不同，各个观察者有自己的处理逻辑。
 */
/**
使用场景：
● 关联行为场景。需要注意的是，关联行为是可拆分的，而不是“组合”关系。
● 事件多级触发场景。
● 跨系统的消息交换场景，如消息队列的处理机制。
注意：
● 广播链的问题
在一个观察者模式中最多出现一个对象既是观察者也是被观察者，也就是说消息最多转发一次（传递两次）。
● 异步处理问题
观察者比较多，而且处理时间比较长，采用异步处理来考虑线程安全和队列的问题。
 *
 */
// 观察者
public interface Observer {

	public abstract void update();

}

// 被观察者
abstract class SubjectForOberver {
	// 定义一个观察者数组
	private Vector<Observer> obsVector = new Vector<Observer>();

	// 增加一个观察者
	public void addObserver(Observer o) {
		this.obsVector.add(o);
	}

	// 删除一个观察者
	public void delObserver(Observer o) {
		this.obsVector.remove(o);
	}

	// 通知所有观察者
	public void notifyObservers() {
		for (Observer o : this.obsVector) {
			o.update();
		}
	}

}
//具体的被观察者
class ConcreteSubject extends SubjectForOberver {

}
//具体的观察者
class ConcreteObserver implements Observer {

	@Override
	public void update() {

	}

}