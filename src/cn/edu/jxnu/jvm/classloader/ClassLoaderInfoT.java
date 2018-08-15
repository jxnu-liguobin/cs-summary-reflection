package cn.edu.jxnu.jvm.classloader;

/**
 * 类加载器信息
 */
public class ClassLoaderInfoT {
	public static void main(String[] args) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		System.out.println("当前类加载器：" + loader);
		System.out.println("当前类的父亲加载器（根加载器）：" + loader.getParent());
		System.out.println("当前类父亲的父亲加载器（无）：" + loader.getParent().getParent());
	}
}
