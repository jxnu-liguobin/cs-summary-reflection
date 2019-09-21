---
title: 虚拟机相关使用测试代码
categories:
- Java虚拟机
---

### JVM 相关代码 （JVM知识参考《深入理解Java虚拟机》）

```java
/**
* 打印类加载器
*/
public class ClassLoaderInfoT {
	public static void main(String[] args) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		System.out.println("当前类加载器：" + loader);
		System.out.println("当前类的父亲加载器（根加载器）：" + loader.getParent());
		System.out.println("当前类父亲的父亲加载器（无）：" + loader.getParent().getParent());
	}
}
```

结果：

    当前类加载器：sun.misc.Launcher$AppClassLoader@b4aac2
    当前类的父亲加载器（根加载器）：sun.misc.Launcher$ExtClassLoader@1193f2d
    当前类父亲的父亲加载器（无）：null

```java
/**
 * 输出浮点数在虚拟机的实际表示
 */
public class FloatInJvm {
	public static void main(String[] args) {
		float a = -5;
		// 输出-5的补码，即虚拟机内部实际表示
		System.out.println(Integer.toBinaryString(Float.floatToRawIntBits(a)));
	}
}
```

结果：

    11000000101000000000000000000000
   
```java
class Parent {
	static {
		System.out.println("Parent init");
	}
	public static int v = 100;
}

class Child extends Parent {
	static {
		System.out.println("Child  init");
	}
}

public class InitMain {
	public static void main(String[] args) {
		new Child();// new关键字初始化 注释开启和未开启作比较
		System.out.println("======");
		// 通过子类调用从父类继承过来的静态属性，不会引起子类初始化
		System.out.println(Child.v); // 此时Child已经被加载，但未被初始化
	}
}
```

结果：

    Parent init
    Child  init
    ======
    100

```java
/**
 * 负整形数在Jvm中的表示
 * 
 */
public class IntegerInJvm {
	public static void main(String[] args) {
		int a = -10;
		for (int i = 0; i < 32; i++) {
			int t = (a & 0x80000000 >>> i) >>> (31 - i);
			System.out.print(t);
		}
		System.out.println();
		System.out.println(Integer.toBinaryString(a));
	}
}
```

结果：

    11111111111111111111111111110110
    11111111111111111111111111110110

```java
/**
 * 打印堆内存 input: java -Xmx256m org.jvmcore.heap.JvmXmxArgs a b 打印始终小于 256M ,
 * 因为GC在不同区域采用不同回收算法，可用内存的减少为了其使用空间换时间的策略。
 */
public class JvmXmxArgs {
	
	public static void main(String[] args) {
		for (String arg : args)
			System.out.println("参数为" + arg);
		// 堆内存
		System.out.println("-Xmx:" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
	}
}
```

结果：

    -Xmx:247M
    
```java
/**
 * 打印GC信息 input: -XX:+PrintGC
 */
@SuppressWarnings("unused")
public class LocalVarGC {

	public void localVarGc1() {
		byte[] bytes = new byte[6 * 1024 * 1024];
		System.gc();
	}

	public void localVarGc2() {
		byte[] bytes = new byte[6 * 1024 * 1024];
		bytes = null;
		System.gc();
	}

	public void localVarGc3() {
		{
			byte[] bytes = new byte[6 * 1024 * 1024];
		}
		System.gc();
	}

	public void localVarGc4() {
		{
			byte[] bytes = new byte[6 * 1024 * 1024];
		}
		int a = 4;
		System.gc();
	}

	public void localVarGc5() {
		localVarGc1();
		System.gc();
	}

	public static void main(String[] args) {
		LocalVarGC ins = new LocalVarGC();
		 ins.localVarGc1();
		 ins.localVarGc2();
		 ins.localVarGc3();
		 ins.localVarGc4();
		ins.localVarGc5();
	}
}
```

结果（注意：需要添加参数-XX:+PrintGC启动）：

    [Full GC (System.gc())  8384K->651K(15872K), 0.0024131 secs]
    [Full GC (System.gc())  6795K->651K(15936K), 0.0015233 secs]
    [Full GC (System.gc())  6885K->651K(15936K), 0.0016029 secs]
    [Full GC (System.gc())  6795K->597K(15936K), 0.0018010 secs]
    [Full GC (System.gc())  6741K->597K(15936K), 0.0017124 secs]
    [Full GC (System.gc())  597K->597K(15936K), 0.0012238 secs]
    
```java
/**
 * 堆栈溢出的理解 input: // -Xss128K
 */
public class StackDeep {
	private static int count = 0;

	public static void recursion() {
		count++;
		recursion();
	}

	@SuppressWarnings("unused")
	public static void recursion(long a, long b, long c) {
		long e = 1, f = 2, g = 3, h = 4, j = 5, k = 6, l = 7, q = 8, w = 10, r = 9;
		count++;
		recursion(a, b, c);
	}

	public static void main(String[] args) {
		try {
			// recursion(0L,0L,0L);
			recursion();
		} catch (Throwable e) {
			System.out.println("counts = " + count);
			e.printStackTrace();
		}
	}
}
```

结果（注意：需要添加参数-Xss128K启动）
    
    counts = 2502
    java.lang.StackOverflowError
        at cn.edu.jxnu.jvm.classloader.StackDeep.recursion(StackDeep.java:10)

```java
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
```

结果：

    public equals(Object)
    public toString()
    public hashCode()
    public compareTo(String)
    public volatile compareTo(Object)
    public indexOf(String,int)
    public indexOf(String)
    public indexOf(int,int)
    public indexOf(int)
    static indexOf(char[],int,int,char[],int,int,int)
    static indexOf(char[],int,int,String,int)
    public static valueOf(int)
    public static valueOf(long)
    public static valueOf(float)
    public static valueOf(boolean)
    public static valueOf(char[])
    public static valueOf(char[],int,int)
    public static valueOf(Object)
    public static valueOf(char)
    public static valueOf(double)
    public charAt(int)
    private static checkBounds(byte[],int,int)
    public codePointAt(int)
    public codePointBefore(int)
    public codePointCount(int,int)
    public compareToIgnoreCase(String)
    public concat(String)
    public contains(CharSequence)
    public contentEquals(CharSequence)
    public contentEquals(StringBuffer)
    public static copyValueOf(char[])
    public static copyValueOf(char[],int,int)
    public endsWith(String)
    public equalsIgnoreCase(String)
    public static transient format(Locale,String,Object[])
    public static transient format(String,Object[])
    public getBytes(int,int,byte[],int)
    public getBytes(Charset)
    public getBytes(String)
    public getBytes()
    public getChars(int,int,char[],int)
     getChars(char[],int)
    private indexOfSupplementary(int,int)
    public native intern()
    public isEmpty()
    public static transient join(CharSequence,CharSequence[])
    public static join(CharSequence,Iterable)
    public lastIndexOf(int)
    public lastIndexOf(String)
    static lastIndexOf(char[],int,int,String,int)
    public lastIndexOf(String,int)
    public lastIndexOf(int,int)
    static lastIndexOf(char[],int,int,char[],int,int,int)
    private lastIndexOfSupplementary(int,int)
    public length()
    public matches(String)
    private nonSyncContentEquals(AbstractStringBuilder)
    public offsetByCodePoints(int,int)
    public regionMatches(int,String,int,int)
    public regionMatches(boolean,int,String,int,int)
    public replace(char,char)
    public replace(CharSequence,CharSequence)
    public replaceAll(String,String)
    public replaceFirst(String,String)
    public split(String)
    public split(String,int)
    public startsWith(String,int)
    public startsWith(String)
    public subSequence(int,int)
    public substring(int)
    public substring(int,int)
    public toCharArray()
    public toLowerCase(Locale)
    public toLowerCase()
    public toUpperCase()
    public toUpperCase(Locale)
    public trim()

```java
/**
 * final的static字段比普通static字段初始化快【final实际上是在类加载的准备阶段初始化
 * static在此阶段只会初始化为0/null值，在解析之后的初始化阶段【类的cinit方法】才是真正使用用户给的值进行初始化】
 */
class FinalFieldClass {
	public static final String CONST_STR = "CONSTSTR";

	static {
		System.out.println("FinalFieldClass init");
	}
}

public class UseFinalField {
	public static void main(String[] args) {
		System.out.println(FinalFieldClass.CONST_STR);
	}
}
```

结果：

    CONSTSTR         PS:特别注意这个
 
```java
/**
 * 实现自己的类加载器
 */
public class PathClassLoader extends URLClassLoader {
	private String packageName = "cn.edu.jxnu.classloader";

	PathClassLoader(URL[] classPath, ClassLoader parent) {
		super(classPath, parent);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> aClass = findLoadedClass(name);
		if (aClass != null) {
			return aClass;
		}
		if (!packageName.startsWith(name)) {
			return super.loadClass(name);
		} else {
			return findClass(name);
		}
	}
}
```

 参考【Jeff Lee】








