package cn.edu.jxnu.design.decorator;

public abstract class Decorator implements Component {
	private Component instance;// 持有Component的引用，通过构造器注入

	public Decorator() {

	}

	public Decorator(Component inst) {
		this.instance = inst;
	}

	public Component getInstance() {
		return this.instance;
	}

}