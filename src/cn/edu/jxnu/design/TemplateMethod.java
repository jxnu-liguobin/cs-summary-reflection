package cn.edu.jxnu.design;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:模板方法：定义一个操作中的算法的框架，而将一些步骤延迟到子类中。使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
 * @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午9:59:18
 */
/**
 * AbstractClass叫做抽象模板，它的方法分为两类： 
 * ● 基本方法 基本方法也叫做基本操作，是由子类实现的方法，并且在模板方法被调用。 
 * ● 模板方法
 * 可以有一个或几个，一般是一个具体方法，也就是一个框架，实现对基本方法的调度，完成固定的逻辑。 注意：
 * 为了防止恶意的操作，一般模板方法都加上final关键字，不允许被覆写。
 * 具体模板：ConcreteClass1和ConcreteClass2属于具体模板，实现父类所定义的一个或多个抽象方法，也就是父类定义的基本方法在子类中得以实现
 * 使用场景： 
 * ● 多个子类有公有的方法，并且逻辑基本相同时。 
 * ● 重要、复杂的算法，可以把核心算法设计为模板方法，周边的相关细节功能则由各个子类实现。
 * ●重构时，模板方法模式是一个经常使用的模式，把相同的代码抽取到父类中，然后通过钩子函数（见“模板方法模式的扩展”）约束其行为。
 * 
 */
public abstract class TemplateMethod {
	// 模板方法，用来控制炒菜的流程 （炒菜的流程是一样的-复用）
	// 申明为final，不希望子类覆盖这个方法，防止更改流程的执行顺序
	final void cookProcess() {
		// 第一步：倒油
		this.pourOil();
		// 第二步：热油
		this.HeatOil();
		// 第三步：倒蔬菜
		this.pourVegetable();
		// 第四步：倒调味料
		this.pourSauce();
		// 第五步：翻炒
		this.fry();
		// 定义结构里哪些方法是所有过程都是一样的可复用的，哪些是需要子类进行实现的
	}

	// 第一步：倒油是一样的，所以直接实现
	void pourOil() {
		System.out.println("倒油");
	}

	// 第二步：热油是一样的，所以直接实现
	void HeatOil() {
		System.out.println("热油");
	}

	// 第三步：倒蔬菜是不一样的（一个下包菜，一个是下菜心）
	// 所以声明为抽象方法，具体由子类实现
	abstract void pourVegetable();

	// 第四步：倒调味料是不一样的（一个下辣椒，一个是下蒜蓉）
	// 所以声明为抽象方法，具体由子类实现
	abstract void pourSauce();

	// 第五步：翻炒是一样的，所以直接实现
	void fry()

	{
		System.out.println("炒啊炒啊炒到熟啊");
	}
}

// 炒手撕包菜的类
class ConcreteClass_BaoCai extends TemplateMethod {

	@Override
	public void pourVegetable() {
		System.out.println("下锅的蔬菜是包菜");
	}

	@Override
	public void pourSauce() {
		System.out.println("下锅的酱料是辣椒");
	}
}

// 炒蒜蓉菜心的类
class ConcreteClass_CaiXin extends TemplateMethod {

	@Override
	public void pourVegetable() {
		System.out.println("下锅的蔬菜是菜心");
	}

	@Override
	public void pourSauce() {
		System.out.println("下锅的酱料是蒜蓉");
	}
}
