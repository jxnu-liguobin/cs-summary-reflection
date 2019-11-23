package cn.edu.jxnu.examples.lambda;

/**
 * 
 * 骡，可以实现为Ihorse,同时骡也是动物 
 * 多个接口拥有相同的默认实例方法，则会报错
 * 可以显示调用 IHorse.super.run();
 */
public class Mule implements Ihorse, IAnimal {

	public static void main(String[] args) {
		Mule mule = new Mule();
		mule.run();
		mule.breath();
		mule.eat();
	}

	@Override
	public void eat() {
		System.out.println("Mule eat");

	}

}
