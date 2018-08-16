package cn.edu.jxnu.designpattern.visitor;

import java.util.List;
import java.util.Random;

/**
 * 提供一个接口，可以访问所有节点元素 一般作为一个集合特有节点元素的引用
 * 
 * 对象结构是一个抽象表述，它内部管理了元素集合，并且可以迭代这些元素供访问者访问
 * 
 * @author 梦境迷离
 *
 */
class ObjectStruture {

	public static List<ElementNode> getList() {
		List<ElementNode> list = new ArrayList<ElementNode>();// 拥有一系列稳定的数据结构
		Random ran = new Random();
		for (int i = 0; i < 3; i++) {// 随机创建三个节点元素
			int a = ran.nextInt(100);
			if (a > 50) {// 大于50创建节点1，否则节点2
				list.add((ElementNode) new ConcreteElement());
			} else {
				list.add((ElementNode) new ConcreteElement2());
			}
		}
		return list;
	}
}