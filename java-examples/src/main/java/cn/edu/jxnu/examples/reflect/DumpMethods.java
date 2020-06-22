/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/** 反射 */
public class DumpMethods {

    public DumpMethods(String s) {
        System.out.println(s + "这是构造方法");
    }

    public DumpMethods() {
        // 自定义的构造将屏蔽默认无参构造
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException,
                    InstantiationException, IllegalAccessException, IllegalArgumentException,
                    InvocationTargetException {

        System.out.println(DumpMethods.class.getName());
        System.out.println("请输入完整的类名：");
        Scanner scanner = new Scanner(System.in);
        String className = scanner.nextLine();

        Class strClass = Class.forName(className);
        // 检索带有指定参数的构造方法
        Class[] strArgsClass = new Class[] {};
        Constructor constructor = strClass.getConstructor(strArgsClass);
        // Constructor:public cn.edu.jxnu.reflect.DumpMethods()
        System.out.println("Constructor:" + constructor.toString());
        // 调用默认的构造方法创建实例对象object
        Object object = constructor.newInstance();
        System.out.println(
                "Object" + object.toString()); // Objectcn.edu.jxnu.reflect.DumpMethods@16d3586
        // 调用有参构造
        String string = "JavaEE";
        Class[] strArgsClass2 = new Class[] {String.class};
        Constructor constructor2 = strClass.getConstructor(strArgsClass2);
        // JavaEE这是构造方法
        // constructor2:public
        // cn.edu.jxnu.reflect.DumpMethods(java.lang.String)cn.edu.jxnu.reflect.DumpMethods@154617c
        System.out.println(
                "constructor2:" + constructor2.toString() + constructor2.newInstance(string));
        scanner.close();
    }
}
