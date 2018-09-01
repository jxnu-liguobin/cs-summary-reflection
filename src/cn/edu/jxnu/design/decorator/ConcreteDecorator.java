package cn.edu.jxnu.design.decorator;

public class ConcreteDecorator extends Decorator {
	// 实现了抽象装饰器定义的功能
	public ConcreteDecorator() {
		super();
	}

	/**
	 * //构造传入组件, super(inst); public Decorator(Component inst) { 
	 * this.instance = inst; }
	 * 
	 */
	public ConcreteDecorator(Component inst) {
		super(inst);

	}

	@Override
	public void methodA() {
		this.getInstance().methodA();
		this.doAdditionalResponsibility();
		// 在原有组件功能上，添加功能，在外界看来，就想使用了methodA方法所做的事情更多了，接口没有变，与适配器的区别
	}

	private void doAdditionalResponsibility() {
		System.out.println("被包装的功能");
	}

}