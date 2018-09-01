package cn.edu.jxnu.design;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:建造者：定义：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午10:08:25
 */
/**
● Product产品类
通常是实现了模板方法模式，也就是有模板方法和基本方法，例子中的BenzModel和BMWModel就属于产品类。
● Builder抽象建造者
规范产品的组建，一般是由子类实现。例子中的CarBuilder就属于抽象建造者。
● ConcreteBuilder具体建造者
实现抽象类定义的所有方法，并且返回一个组建好的对象。例子中的BenzBuilder和BMWBuilder就属于具体建造者。
● Director导演类
负责安排已有模块的顺序，然后告诉Builder开始建造
使用场景：
● 相同的方法，不同的执行顺序，产生不同的事件结果时，可以采用建造者模式。
● 多个部件或零件，都可以装配到一个对象中，但是产生的运行结果又不相同时，则可以使用该模式。
● 产品类非常复杂，或者产品类中的调用顺序不同产生了不同的效能，这个时候使用建造者模式非常合适。
建造者模式与工厂模式的不同：
建造者模式最主要的功能是基本方法的调用顺序安排，这些基本方法已经实现了，顺序不同产生的对象也不同；
工厂方法则重点是创建，创建零件是它的主要职责，组装顺序则不是它关心的。
 
 *
 */
// 步骤1： 定义组装的过程（Builder）：组装电脑的过程
abstract class BuilderPattern {
	// 第一步：装CPU
	// 声明为抽象方法，具体由子类实现
	public abstract void BuildCPU();

	// 第二步：装主板
	// 声明为抽象方法，具体由子类实现
	public abstract void BuildMainboard();

	// 第三步：装硬盘
	// 声明为抽象方法，具体由子类实现
	public abstract void BuildHD();

	// 返回产品的方法：获得组装好的电脑
	public abstract Computer GetComputer();
}

// 步骤2： 电脑城老板委派任务给装机人员（Director）
class Director {
	// 指挥装机人员组装电脑
	public void Construct(BuilderPattern builder) {

		builder.BuildCPU();
		builder.BuildMainboard();
		builder.BuildHD();
	}
}

// 步骤3： **创建具体的建造者（ConcreteBuilder）:装机人员
class ConcreteBuilder extends BuilderPattern {
	// 创建产品实例
	Computer computer = new Computer();

	// 组装产品
	@Override
	public void BuildCPU() {
		computer.Add("组装CPU");
	}

	@Override
	public void BuildMainboard() {
		computer.Add("组装主板");
	}

	@Override
	public void BuildHD() {
		computer.Add("组装主板");
	}

	// 返回组装成功的电脑
	@Override
	public Computer GetComputer() {
		return computer;
	}
}

// 步骤4： **定义具体产品类（Product）：电脑
class Computer {

	// 电脑组件的集合
	private List<String> parts = new ArrayList<String>();

	// 用于将组件组装到电脑里
	public void Add(String part) {
		parts.add(part);
	}

	public void Show() {
		for (int i = 0; i < parts.size(); i++) {
			System.out.println("组件" + parts.get(i) + "装好了");
		}
		System.out.println("电脑组装完成，请验收");

	}

}
//步骤5： **客户端调用-小成到电脑城找老板买电脑
//public static void main(String[] args) {
// 逛了很久终于发现一家合适的电脑店
// 找到该店的老板和装机人员
//Director director = new Director();
//BuilderPattern builder = new ConcreteBuilder();
//// 沟通需求后，老板叫装机人员去装电脑
//director.Construct(builder);
//// 装完后，组装人员搬来组装好的电脑
//Computer computer = builder.GetComputer();
//// 组装人员展示电脑给小成看
//computer.Show();

