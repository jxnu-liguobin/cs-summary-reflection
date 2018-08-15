//package cn.edu.jxnu.other;
//
//import java.io.FileOutputStream;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//
//import sun.misc.ProxyGenerator;
//
//@SuppressWarnings("restriction")
//class RealSubject implements Subject {
//	public void doSomething() {
//		System.out.println("call doSomething()");
//	}
//}
//
//interface Subject {
//	public void doSomething();
//}
//
//public class DynamicProxy {
//	// 执行被代理类的方法；而
//	// Proxy.newProxyInstance()方法用于得到这个接口的实例
//	// //write proxySubject class binary data to
//	// file createProxyClassFile();
//	public static void main(String args[]) {
//		RealSubject real = new RealSubject();
//		Subject proxySubject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(),
//				new Class[] { Subject.class }, new ProxyHandler(real));
//
//		proxySubject.doSomething();
//
//	}
//
//	public static void createProxyClassFile() {
//		String name = "ProxySubject";
//		// 下面只能在jdk1.7，需要开启eclipse的sum包允许
//		@SuppressWarnings("restriction")
//		byte[] data = ProxyGenerator.generateProxyClass(name, new Class[] { Subject.class });
//		try {
//			FileOutputStream out = new FileOutputStream(name + ".class");
//			out.write(data);
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
///**
// * @description 代理类
// * @author Mr.Li
// *
// */
//class ProxyHandler implements InvocationHandler {
//	private Object proxied;
//
//	public ProxyHandler(Object proxied) {
//		this.proxied = proxied;
//	}
//
//	/**
//	 * @description 预处理
//	 */
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		// 在转调具体目标对象之前，可以执行一些功能处理
//		// 转调具体目标对象的方法
//		return method.invoke(proxied, args);
//		// 在转调具体目标对象之后，可以执行一些功能处理 }
//	}
//}
