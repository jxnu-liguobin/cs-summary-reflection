package cn.edu.jxnu.reflect;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;

/**
 * @author Eric Bruneton
 */
public class Compile extends ClassLoader {

	public static void main(final String[] args) throws Exception {
		// 创建相应的表达式树。
		// exp(i) = i > 3 && 6 > i
		Exp exp = new And(new GT(new Var(0), new Cst(3)), new GT(new Cst(6), new Var(0)));
		// 将此表达式编译为表达式类。
		Compile main = new Compile();
		byte[] b = exp.compile("Example");
		FileOutputStream fos = new FileOutputStream("Example.class");
		fos.write(b);
		fos.close();
		Class<?> expClass = main.defineClass("Example", b, 0, b.length);
		// 实例化这个已编译的表达式类.
		@SuppressWarnings("deprecation")
		Expression iexp = (Expression) expClass.newInstance();
		// 并将其用于判断exp(0)到exp(9)
		for (int i = 0; i < 10; ++i) {
			boolean val = iexp.eval(i, 0) == 1;
			System.out.println(i + " > 3 && " + i + " < 6 = " + val);
		}
	}
}

/**
 * 抽象的表达
 * 
 * @author Eric Bruneton
 */
abstract class Exp implements Opcodes {

	/*
	 * 返回与此表达式对应的表达式类的字节代码。
	 */
	@SuppressWarnings("deprecation")
	byte[] compile(final String name) {
		// 类标记
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cw.visit(V1_1, ACC_PUBLIC, name, null, "java/lang/Object", new String[] { Expression.class.getName() });

		// 默认公有构造方法
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

		// Eval方法
		mv = cw.visitMethod(ACC_PUBLIC, "eval", "(II)I", null, null);
		compile(mv);
		mv.visitInsn(IRETURN);
		// 自动计算最大堆栈和最大局部变量，可以指定
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		return cw.toByteArray();
	}

	/*
	 * 编译这个表达式 此方法必须向给定的代码编写器追加字节代码以求值并将此值推送到堆栈上。
	 */
	abstract void compile(MethodVisitor mv);
}

/**
 * 一个常量表达式
 */
class Cst extends Exp {

	int value;

	Cst(final int value) {
		this.value = value;
	}

	@SuppressWarnings("deprecation")
	@Override
	void compile(final MethodVisitor mv) {
		// 将常量的值压入堆栈上
		mv.visitLdcInsn(new Integer(value));
	}
}

/**
 * 一个可变的引用类型表达式
 */
class Var extends Exp {

	int index;

	Var(final int index) {
		this.index = index + 1;
	}

	@Override
	void compile(final MethodVisitor mv) {
		// 将“index”局部变量压入到堆栈上
		mv.visitVarInsn(ILOAD, index);
	}
}

/**
 * 一个抽象的二进制表达式
 */
abstract class BinaryExp extends Exp {

	Exp e1;

	Exp e2;

	BinaryExp(final Exp e1, final Exp e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
}

/**
 * 加法表达式
 */
class Add extends BinaryExp {

	Add(final Exp e1, final Exp e2) {
		super(e1, e2);
	}

	@Override
	void compile(final MethodVisitor mv) {
		// 编译e1、e2，并添加一个指令来添加这两个值
		e1.compile(mv);
		e2.compile(mv);
		mv.visitInsn(IADD);
	}
}

/**
 * 乘法表达式
 */
class Mul extends BinaryExp {

	Mul(final Exp e1, final Exp e2) {
		super(e1, e2);
	}

	@Override
	void compile(final MethodVisitor mv) {
		// 编译e1、e2，并添加将这两个值相乘的指令。 e1.compile(mv);
		e2.compile(mv);
		mv.visitInsn(IMUL);
	}
}

/**
 * 一个“大于”的表达式。
 */
class GT extends BinaryExp {

	GT(final Exp e1, final Exp e2) {
		super(e1, e2);
	}

	@SuppressWarnings("deprecation")
	@Override
	void compile(final MethodVisitor mv) {
		// 编译e1、e2，并添加用于比较这两个值的说明
		e1.compile(mv);
		e2.compile(mv);
		Label iftrue = new Label();
		Label end = new Label();
		mv.visitJumpInsn(IF_ICMPGT, iftrue);
		// e1<=e2：压入false，并跳转到“end”的情况
		mv.visitLdcInsn(new Integer(0));
		mv.visitJumpInsn(GOTO, end);
		// E1>e2：压入true
		mv.visitLabel(iftrue);
		mv.visitLdcInsn(new Integer(1));
		mv.visitLabel(end);
	}
}

/**
 * 短路 一个逻辑的“与”的表达式
 */
class And extends BinaryExp {

	And(final Exp e1, final Exp e2) {
		super(e1, e2);
	}

	@Override
	void compile(final MethodVisitor mv) {
		// 编译e1
		e1.compile(mv);
		// 判断测试，e1是否为false
		mv.visitInsn(DUP);
		Label end = new Label();
		mv.visitJumpInsn(IFEQ, end);
		// e1为true的情况: e1 && e2 is equal to e2
		mv.visitInsn(POP);
		e2.compile(mv);
		// e1为假的情况： e1 && e2 is equal to e1:
		// 我们直接跳到这个标签，而不计算e2
		// e2是否需要计算取决于e1
		mv.visitLabel(end);
	}
}

/**
 * 短路 一个逻辑“或”表达式
 */
class Or extends BinaryExp {

	Or(final Exp e1, final Exp e2) {
		super(e1, e2);
	}

	@Override
	void compile(final MethodVisitor mv) {
		// 编译e1
		e1.compile(mv);
		// 测试是否为 true
		mv.visitInsn(DUP);
		Label end = new Label();
		mv.visitJumpInsn(IFNE, end);
		// e1为false的情况 : e1 || e2 is equal to e2
		// e1为false,此时需要计算e2
		mv.visitInsn(POP);
		e2.compile(mv);
		// if e1 is true, e1 || e2 is equal to e1:
		// e1为true时，我们直接跳到这个标签上，而不计算e2。
		// e2是否需要计算取决于e1
		mv.visitLabel(end);
	}
}

/**
 * 一个逻辑“非”表达式
 */
class Not extends Exp {

	Exp e;

	Not(final Exp e) {
		this.e = e;
	}

	@SuppressWarnings("deprecation")
	@Override
	void compile(final MethodVisitor mv) {
		// 通过计算1-e1来计算!e1
		mv.visitLdcInsn(new Integer(1));
		e.compile(mv);
		mv.visitInsn(ISUB);
	}
}
