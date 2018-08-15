package cn.edu.jxnu.designpattern.visitor;

/**
 * 声明访问者所需要的接口
 * 
 * 它定义了对每一个元素（Element）访问的行为，它的参数就是可以访问的元素，它的方法数理论上来讲与元素个数是一样的
 * 因此，访问者模式要求元素的类族要稳定，如果经常添加、移除元素类，必然会导致频繁地修改Visitor接口，如果这样则不适合使用访问者模式。
 */
public interface Visitor {

	public void visit(ConcreteElement concreteElement);

	public void visit(ConcreteElement2 concreteElement2);

}
