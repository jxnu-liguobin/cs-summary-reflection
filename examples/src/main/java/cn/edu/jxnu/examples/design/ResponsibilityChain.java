package cn.edu.jxnu.examples.design;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:责任链：定义：使多个对象都有机会处理请求，从而避免了请求的发送者和接受者之间的耦合关系。将这些对象连成一条链，并沿着这条链传递该请求，直到有对象处理它为止。
 * @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午10:38:23
 */
/**
 * 抽象的处理者实现三个职责： 一是定义一个请求的处理方法handleMessage，唯一对外开放的方法；
 * 二是定义一个链的编排方法setNext，设置下一个处理者；
 * 三是定义了具体的请求者必须实现的两个方法：定义自己能够处理的级别getHandlerLevel和具体的处理任务echo。 
 * 注意事项：
 * 链中节点数量需要控制，避免出现超长链的情况，一般的做法是在Handler中设置一个最大节点数量，在setNext方法中判断是否已经是超过其阈值，
 * 超过则不允许该链建立，避免无意识地破坏系统性能。
 *
 */
public abstract class ResponsibilityChain {
	private ResponsibilityChain nextResponsibilityChain;

	// 每个处理者都必须对请求做出处理
	public final Response handleMessage(Request request) {
		Response response = null;
		// 判断是否是自己的处理级别
		if (this.getHandlerLevel().equals(request.getRequestLevel())) {
			response = this.echo(request);
		} else { // 不属于自己的处理级别
					// 判断是否有下一个处理者
			if (this.nextResponsibilityChain != null) {
				//即在这里可能需要判断链长阈值
				response = this.nextResponsibilityChain.handleMessage(request);
			} else {
				// 没有适当的处理者，业务自行处理
			}
		}
		return response;
	}// 设置下一个处理者是谁

	public void setNext(ResponsibilityChain _handler) {
		this.nextResponsibilityChain = _handler;
	}

	// 每个处理者都有一个处理级别
	protected abstract Level getHandlerLevel();

	// 每个处理者都必须实现处理任务
	protected abstract Response echo(Request request);
}

class Level {

}

class Response {

}

class Request {

	public Object getRequestLevel() {
		return null;
	}

}
