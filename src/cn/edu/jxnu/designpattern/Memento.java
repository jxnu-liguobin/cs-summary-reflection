package cn.edu.jxnu.designpattern;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;


/**   
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:备忘录：定义：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。这样以后就可将该对象恢复到原先保存的状态。
 * @Package: cn.jxnu.edu.designpattern 
 * @author: 梦境迷离   
 * @date: 2018年3月20日 上午11:00:04 
 */
/**
 * ● Originator发起人角色
记录当前时刻的内部状态，负责定义哪些属于备份范围的状态，负责创建和恢复备忘录数据。
● Memento备忘录角色（简单的javabean）
负责存储Originator发起人对象的内部状态，在需要的时候提供发起人需要的内部状态。
● Caretaker备忘录管理员角色（简单的javabean）
对备忘录进行管理、保存和提供备忘录。
使用场景：
● 需要保存和恢复数据的相关状态场景。
● 提供一个可回滚（rollback）的操作。
● 需要监控的副本场景中。
● 数据库连接的事务管理就是用的备忘录模式。
注意：
●备忘录的生命期
●备忘录的性能
不要在频繁建立备份的场景中使用备忘录模式（比如一个for循环中）。    
clone方式备忘录：
● 发起人角色融合了发起人角色和备忘录角色，具有双重功效
多状态的备忘录模式
● 增加了一个Memento类，其中backupProp是把发起人的所有属性值转换到HashMap中，方便备忘录角色存储。
restoreProp方法则是把HashMap中的值返回到发起人角色中。
 */
public class Memento {
	// 把bean的所有属性及数值放入到Hashmap中
	public static HashMap<String, Object> backupProp(Object bean) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			// 获得Bean描述
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			// 获得属性描述
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			// 遍历所有属性
			for (PropertyDescriptor des : descriptors) {
				// 属性名称
				String fieldName = des.getName();
				// 读取属性的方法
				Method getter = des.getReadMethod();
				// 读取属性值
				Object fieldValue = getter.invoke(bean, new Object[] {});
				if (!fieldName.equalsIgnoreCase("class")) {
					result.put(fieldName, fieldValue);
				}
			}
		} catch (Exception e) {
			// 异常处理
		}
		return result;
	}

	// 把HashMap的值返回到bean中
	public static void restoreProp(Object bean, HashMap<String, Object> propMap) {
		try {
			// 获得Bean描述
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			// 获得属性描述
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			// 遍历所有属性
			for (PropertyDescriptor des : descriptors) {
				// 属性名称
				String fieldName = des.getName();
				// 如果有这个属性
				if (propMap.containsKey(fieldName)) {
					// 写属性的方法
					Method setter = des.getWriteMethod();
					setter.invoke(bean, new Object[] { propMap.get(fieldName) });
				}
			}
		} catch (Exception e) {
			// 异常处理
			System.out.println("shit");
			e.printStackTrace();
		}
	}
}
