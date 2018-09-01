package cn.edu.jxnu.design.visitor;

/**
 * 具体的元素类，它提供接受访问方法的具体实现，而这个具体的实现，通常情况下是使用访问者提供的访问该元素类的方法
 */
public class ConcreteElement implements ElementNode {

	@Override
	public void doSomeThings() {
		System.out.println("this is an element1 .....");

	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
