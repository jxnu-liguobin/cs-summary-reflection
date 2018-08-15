package cn.edu.jxnu.jvm.classloader;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 显示类加载，获取方法签名
 */
public class StringCL {
	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> clzStr = Class.forName("java.lang.String");
		// 返回对象方法数组
		// 返回已加载类声明的所有成员变量的Field对象数组,不包括从父类继承的成员变量.
		Method[] methods = clzStr.getDeclaredMethods();
		for (Method m : methods) {
			// 获取修饰符标志的字符串
			String mod = Modifier.toString(m.getModifiers());
			System.out.print(mod + " " + m.getName() + "(");
			Class<?>[] ps = m.getParameterTypes();
			if (ps.length == 0)
				System.out.print(')');
			for (int i = 0; i < ps.length; i++) {
				char end = i == ps.length - 1 ? ')' : ',';
				System.out.print(ps[i].getSimpleName() + end);
			}
			System.out.println();
		}
	}
}
