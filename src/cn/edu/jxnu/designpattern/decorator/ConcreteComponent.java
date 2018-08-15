package cn.edu.jxnu.designpattern.decorator;

public class ConcreteComponent implements Component {

	@Override
	public void methodA() {
		System.out.println("被装饰类，实现了抽象组件的功能");
	}

}