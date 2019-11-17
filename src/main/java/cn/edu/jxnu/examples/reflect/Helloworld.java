package cn.edu.jxnu.examples.reflect;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * 演示两种方式动态生成类，该类的main方法打印Hello World
 * 
 * 根据生成的字节码构造对象，启动main
 * 
 * ASM的Opcodes类已经定义了各个修饰符的常量
 * 
 */
public class Helloworld extends ClassLoader implements Opcodes {

	@SuppressWarnings("deprecation")
	public static void main(final String args[]) throws Exception {

		long start1 = System.nanoTime();
		// 创建一个Example类的 ClassWriter
		// which inherits from Object
		ClassWriter cw = new ClassWriter(0);
		cw.visit(V1_1, ACC_PUBLIC, "Example", null, "java/lang/Object", null);
		// 创建默认构造方法的MethodVisitor，调用ClassWriter的visitMethod得到MethodVisitor，操纵方法相关的字节码
		MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		// 将this压入栈
		mw.visitVarInsn(ALOAD, 0);
		// 调超类构造
		mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		// return
		mw.visitInsn(RETURN);
		// 此代码最多使用一个堆栈元素和一个局部变量
		mw.visitMaxs(1, 1);
		// 类构造方法结束
		mw.visitEnd();
		// 为‘main’方法创建一个方法编写器
		mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
		// System的out入栈
		mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		// "Hello World!" 字符串常量入栈
		mw.visitLdcInsn("Hello world!");
		// 调用println方法
		mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
		// return
		mw.visitInsn(RETURN);
		// 此代码最多使用两个堆栈元素和两个本地元素。0，0表示自动计算
		mw.visitMaxs(2, 2);
		// 方法结束
		mw.visitEnd();
		// 获取示例类的字节码，并动态加载它。
		byte[] code = cw.toByteArray();
		// 保存到文件
		FileOutputStream fos = new FileOutputStream(
				"D:\\Scala\\scala_project\\Java-Learning-Summary\\Java-Learning-Summary\\Java-Learning-Summary\\src\\cn\\edu\\jxnu\\reflect\\asm\\Example.class");
		fos.write(code);
		fos.close();
		Helloworld loader = new Helloworld();
		Class<?> exampleClass = loader.defineClass("Example", code, 0, code.length);
		// 使用动态生成的类打印“HelloWorld”
		exampleClass.getMethods()[0].invoke(null, new Object[] { null });
		long end1 = System.nanoTime();
		
		// ------------------------------------------------------------------------
		// 与GeneratorAdapter相同的示例(更方便，但更慢)
		// ------------------------------------------------------------------------
		long start2 = System.nanoTime();
		cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cw.visit(V1_1, ACC_PUBLIC, "Example", null, "java/lang/Object", null);
		// 创建默认构造器的GeneratorAdapter
		Method m = Method.getMethod("void <init> ()");
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
		mg.loadThis();
		mg.invokeConstructor(Type.getType(Object.class), m);
		mg.returnValue();
		mg.endMethod();
		m = Method.getMethod("void main (String[])");
		mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
		mg.getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
		mg.push("Hello world!");
		mg.invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println (String)"));
		mg.returnValue();
		mg.endMethod();
		cw.visitEnd();
		code = cw.toByteArray();
		loader = new Helloworld();
		exampleClass = loader.defineClass("Example", code, 0, code.length);
		exampleClass.getMethods()[0].invoke(null, new Object[] { null });
		long end2 = System.nanoTime();
		
		System.out.println("MethodVisitor:"+(end1-start1));
		System.out.println("GeneratorAdapter:"+ (end2-start2));
		System.out.println("MethodVisitor比GeneratorAdapter多花:"+(double)((end1-start1)-(end2-start2))+"ns");//约3000000~4000000ns

	}
}
