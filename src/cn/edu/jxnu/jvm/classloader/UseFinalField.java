package cn.edu.jxnu.jvm.classloader;

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
