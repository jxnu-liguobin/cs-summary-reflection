package cn.edu.jxnu.design;

import java.awt.*;
import java.util.ArrayList;

/**   
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:组合：定义：将对象组合成树形结构以表示“部分-整体”的层次结构，使得用户对单个对象和组合对象的使用具有一致性。
 * @Package: cn.jxnu.edu.designpattern 
 * @author: 梦境迷离   
 * @date: 2018年3月20日 上午10:51:01 
 */
/**
● Component抽象构件角色
定义参加组合对象的共有方法和属性，可以定义一些默认的行为或属性，比如我们例子中的getInfo就封装到了抽象类中。
● Leaf叶子构件
叶子对象，其下再也没有其他的分支，也就是遍历的最小单位。
● Composite树枝构件
树枝对象，它的作用是组合树枝节点和叶子节点形成一个树形结构。
 树枝构件的通用代码：
 */

/**
 使用场景：
● 维护和展示部分-整体关系的场景，如树形菜单、文件和文件夹管理。
● 从一个整体中能够独立出部分模块或功能的场景。
注意：
只要是树形结构，就考虑使用组合模式。
 *
 */
public class Composite extends Component {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3897036970443594939L;
	// 构件容器
	private ArrayList<Component> componentArrayList = new ArrayList<Component>();

	// 增加一个叶子构件或树枝构件
	public void add(Component component) {
		this.componentArrayList.add(component);
	}

	// 删除一个叶子构件或树枝构件
	public void remove(Component component) {
		this.componentArrayList.remove(component);
	}

	// 获得分支下的所有叶子构件和树枝构件
	public ArrayList<Component> getChildren() {
		return this.componentArrayList;
	}
}
