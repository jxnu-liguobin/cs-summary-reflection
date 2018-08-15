package cn.edu.jxnu.designpattern.visitor;

/**
 * 元素接口或者抽象类，它定义了一个接受访问者的方法（Accept），其意义是指每一个元素都要可以被访问者访问。
 */
public interface ElementNode {
	public void doSomeThings();// 做的事情

	public void accept(Visitor visitor);// 接受访问者作为参数传进来

}
