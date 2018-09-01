package cn.edu.jxnu.design;
/**   
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:策略：定义：定义一组算法，将每个算法都封装起来，并且使它们之间可以互换。
 * @Package: cn.jxnu.edu.designpattern 
 * @author: 梦境迷离   
 * @date: 2018年3月20日 上午10:47:40 
 */
/**
● Context封装角色
它也叫做上下文角色，起承上启下封装作用，屏蔽高层模块对策略、算法的直接访问，封装可能存在的变化。
● Strategy抽象策略角色
策略、算法家族的抽象，通常为接口，定义每个策略或算法必须具有的方法和属性。各位看官可能要问了，类图中的AlgorithmInterface是什么意思，嘿嘿，algorithm是“运算法则”的意思，结合起来意思就明白了吧。
● ConcreteStrategy具体策略角色（多个）
实现抽象策略中的操作，该类含有具体的算法。
使用场景：
● 多个类只有在算法或行为上稍有不同的场景。
● 算法需要自由切换的场景。
● 需要屏蔽算法规则的场景。
注意事项：具体策略数量超过4个，则需要考虑使用混合模式
 */
//策略模式扩展：策略枚举
//定义：
//● 它是一个枚举。
//● 它是一个浓缩了的策略模式的枚举。
//注意：
//受枚举类型的限制，每个枚举项都是public、final、static的，扩展性受到了一定的约束，因此在系统开发中，策略枚举一般担当不经常发生变化的角色。
//致命缺陷：
//所有的策略都需要暴露出去，由客户端决定使用哪一个策略。
public enum Strategy {
	// 加法运算
	ADD("+") {
		public int exec(int a, int b) {
			return a + b;
		}
	},
	// 减法运算
	SUB("-") {
		public int exec(int a, int b) {
			return a - b;
		}
	};
	String value = "";

	// 定义成员值类型
	private Strategy(String _value) {
		this.value = _value;
	}

	// 获得枚举成员的值
	public String getValue() {
		return this.value;
	}

	// 声明一个抽象函数
	public abstract int exec(int a, int b);
}
