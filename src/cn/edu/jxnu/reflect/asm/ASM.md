## ASM字节码操纵框架

这里只是简单使用，具体看asm包下的官方例子

### 1.最为流行的字节码操纵框架包括：
	
	ASM
	AspectJ
	BCEL
	Byte Buddy
	CGLIB 封装ASM API
	Cojen
	Javassist
	Serp


### 2.我们为什么应该关注字节码操纵呢？

很多常用的Java库，如Spring和Hibernate，以及大多数的JVM语言甚至我们的IDE，都用到了字节码操纵框架。另外，它也确实非常有趣，所以这是一项很有价值的技术，掌握它之后，我们就能完成一些靠其他技术很难实现或无法完成的任务。

一个很重要的使用场景就是程序分析。例如，流行的bug定位工具FindBugs在底层就使用了ASM来分析字节码并定位bug。有一些软件商店会有一定的代码复杂性规则，比如方法中if/else语句的最大数量以及方法的最大长度。静态分析工具会分析我们的字节码来确定代码的复杂性。

另外一个常见的使用场景就是类生成功能。例如，ORM框架一般都会基于我们的类定义使用代理的机制。或者，在考虑实现应用的安全性时，可能会提供一种语法来添加授权的注解。在这样的场景下，都能很好地运用字节码操纵技术。

像Scala、Groovy和Grails这样的JVM语言都使用了字节码操纵框架。
考虑这样一种场景，我们需要转换库中的类，这些类我们并没有源码，这样的任务通常会由Java profiler来执行。例如，在New Relic，采用了字节码instrumentation技术实现了对方法执行的计时。

借助字节码操纵，我们可以优化或混淆代码，甚至可以引入一些功能，比如为应用添加重要的日志。本文将会关注一个日志样例，这个样例提供使用这些字节码操纵框架的基本工具。

ASM最初是一个博士研究项目，在2002年开源。它的更新非常活跃，从5.x版本开始支持Java 8。ASM包含了一个基于事件的库和一个基于对象的库，分别类似于SAX和DOM XML解析器。

一个Java类是由很多组件组成的，包括超类、接口、属性、域和方法。在使用ASM时，我们可以将其均视为事件。我们会提供一个ClassVisitor实现，通过它来解析类，当解析器遇到每个组件时，ClassVisitor上对应的“visitor”事件处理器方法会被调用（始终按照上述的顺序）。

```
package com.sun.xml.internal.ws.org.objectweb.asm; 
   public interface ClassVisitor { 
       void visit(int version, int access, String name, String signature, 
                                  String superName, String[] interfaces);
       void visitSource(String source, String debug); 
       void visitOuterClass(String owner, String name, String desc); 
       AnnotationVisitor visitAnnotation(String desc, boolean visible); 
       void visitAttribute(Attribute attr); 
       void visitInnerClass(String name, String outerName, 
                            String innerName, int access); 
       FieldVisitor visitField(int access, String name, String desc, 
                               String signature, Object value); 
       MethodVisitor visitMethod(int access, String name, String desc, 
                                 String signature, String[] exceptions); 
       void visitEnd(); 
   } 
```

ClassReader解析过程 - 经典的访问者设计模式应用之处

![ClassReader解析过程](https://github.com/jxnu-liguobin/Java-Learning-Summary/blob/master/Java-Learning-Summary/src/cn/edu/jxnu/reflect/asm/ClassReader%E8%A7%A3%E6%9E%90%E8%BF%87%E7%A8%8B.gif)

### 3.ASM的优劣

	它的内存占用很小；
	它的运行通常会非常快；
	在网上，它的文档很丰富；
	所有的操作码都是可用的，所以可以通过它做很多的事情；
	有很多的社区支持。
	它只有一个不足之处，但这是很大的不足：
	我们编写的是字节码，所以需要理解在幕后是如何运行的，这样的话，开发人员学习的成本就会增加。

### 4.ASM的使用

```
package cn.edu.jxnu.reflect.asm;

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
				"D:\\Scala\\scala_project\\Java-Learning-Summary\\Java-Learning-Summary
				\\Java-Learning-Summary\\src\\cn\\edu\\jxnu\\reflect\\asm\\Example.class");
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
		//约3000000~4000000ns
		System.out.println("MethodVisitor比GeneratorAdapter多花:"+(double)((end1-start1)-(end2-start2))+"ns");

	}
}

```

### 5.ASM与反射、CGLib、JDK代理、Spring

* 反射是读取持久堆上存储的类信息。而 ASM 是直接处理 .class 字节码的小工具（工具虽小，但是功能非常强大！）
* 反射只能读取类信息，而 ASM 除了读还能写。
* 反射读取类信息时需要进行类加载处理，而 ASM 则不需要将类加载到内存中。
* 反射相对于 ASM 来说使用方便，想直接操纵 ASM 的话需要有 JVM 指令基础。（想熟练掌握极难，况且普通开发者也用不着。。。）
* CGLib是一个开源项目，底层依赖ASM API操纵字节码 [CGLib](https://github.com/cglib/cglib)
* Spring的AOP动态代理分为JDK和CGLib




[参考infoq](http://www.infoq.com/cn/articles/Living-Matrix-Bytecode-Manipulation)