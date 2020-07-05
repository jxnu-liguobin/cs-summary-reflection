/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.reflect;

import java.lang.reflect.Field;

/** 反射 */
public class ReflectTest {
    private String name;
    private String age;

    public ReflectTest(String name, String age) {
        this.setName(name);
        this.setAge(age);
    }

    public static void main(String[] args) {
        try {
            ReflectTest rt = new ReflectTest("李四", "24");
            fun(rt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fun(Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        System.out.println("替换之前的:");
        for (Field field : fields) {
            System.out.println(field.getName() + "=" + field.get(obj));
            if (field.getType().equals(String.class)) {
                field.setAccessible(true); // 必须设置为true才可以修改成员变量
                String org = (String) field.get(obj);
                field.set(obj, org.replace("李", "b"));
            }
        }
        System.out.println("替换之后的：");
        for (Field field : fields) {
            System.out.println(field.getName() + "=" + field.get(obj));
        }
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the age */
    public String getAge() {
        return age;
    }

    /** @param age the age to set */
    public void setAge(String age) {
        this.age = age;
    }
}
