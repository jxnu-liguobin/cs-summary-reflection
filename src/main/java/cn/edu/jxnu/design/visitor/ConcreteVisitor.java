package cn.edu.jxnu.design.visitor;
/**
 * 根据传来的参数判断使用哪一个方法，重载
 * 
 * */
public class ConcreteVisitor implements Visitor {

	@Override
	public void visit(ConcreteElement concreteElement) {
		concreteElement.doSomeThings();
	}

	@Override
	public void visit(ConcreteElement2 concreteElement2) {
		concreteElement2.doSomeThings();
		
	}

}
