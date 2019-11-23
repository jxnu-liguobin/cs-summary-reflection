package cn.edu.jxnu.examples.reflect;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author Eric Bruneton
 */
public class Adapt extends ClassLoader {

	@Override
	protected synchronized Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		if (name.startsWith("java.")) {
			System.err.println("Adapt: loading class '" + name + "' without on the fly adaptation");
			return super.loadClass(name, resolve);
		} else {
			System.err.println("Adapt: loading class '" + name + "' with on the fly adaptation");
		}

		// 获取用于读取类的字节码的输入流。
		String resource = name.replace('.', '/') + ".class";
		InputStream is = getResourceAsStream(resource);
		byte[] b;

		// 动态调整类
		try {
			ClassReader cr = new ClassReader(is);
			ClassWriter cw = new ClassWriter(0);
			ClassVisitor cv = new TraceFieldClassAdapter(cw);
			cr.accept(cv, 0);
			b = cw.toByteArray();
		} catch (Exception e) {
			throw new ClassNotFoundException(name, e);
		}

		// 可选：将适配类存储在磁盘上
		try {
			FileOutputStream fos = new FileOutputStream(resource + ".adapted");
			fos.write(b);
			fos.close();
		} catch (IOException e) {
		}

		// 返回已调整的类
		return defineClass(name, b, 0, b.length);
	}

	public static void main(final String args[]) throws Exception {
		// 使用Adapt类加载器加载应用程序类
		ClassLoader loader = new Adapt();
		Class<?> c = loader.loadClass(args[0]);
		// 调用该类的“main”静态方法
		// 应用程序参数，(args[1].args[n]中)作为参数
		Method m = c.getMethod("main", new Class<?>[] { String[].class });
		String[] applicationArgs = new String[args.length - 1];
		System.arraycopy(args, 1, applicationArgs, 0, applicationArgs.length);
		m.invoke(null, new Object[] { applicationArgs });
	}
}

/**
 * ClassVisitor被定义为一个能接收并解析ClassReader发来的信息的类.当你让ClassVisitor访问ClassReader时,
 * ClassReader会开始字节码的解析工作,并将结果源源不断通过调用各种方法输入ClassVisitor.其中只有visit方法只会并且一定会被调用一次,
 * 其它都不定.比如visitMethod方法,每当ClassReader解析出一个方法的字节码时,都会调用一次visitMethod方法.由它生成一个MethodVisitor(方法访问者).
 * 这个MethodVisitor不会消停下来,而是会被反馈回ClassReader,由ClassReader向MethodVisitor输入更多的信息(如操作码内容).
 * 这也是访问者模式的另一个优势,将工作分隔开,ClassVisitor只处理和类相关的事,方法的事情被外包给MethodVisitor处理.
 * 我们之前说过对象模型是事件模型的一个封装.其实,ClassNode就是ClassVisitor的一个派生类.它将ClassReader发来的信息分类储存.
 * MethodNode也是MethodVisitor的一个派生类,它将ClassReader发来的操作码信息串成一个列表来储存.
 * ClassWriter也是ClassVisitor的一个派生类,不过它不会储存信息,它会将收到的信息立即转译成字节码,并随时输出它们.
 * ClassReader则与一切都无关系,如果你再深入了解访问者模式的话,你会知道访问者模式中还存在一个叫ObjectStructure的东西,
 * 可以看做所有元素的集合以及事件源.在这里ClassReader一定程度上充当了它的作用.
 */
class TraceFieldClassAdapter extends ClassVisitor implements Opcodes {

	private String owner;

	public TraceFieldClassAdapter(final ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	/**
	 * visit方法只会并且一定会被调用一次
	 */
	@Override
	public void visit(final int version, final int access, final String name, final String signature,
			final String superName, final String[] interfaces) {
		owner = name;
		super.visit(version, access, name, signature, superName, interfaces);
	}

	/**
	 * 同MethodVisitor
	 */
	@SuppressWarnings({ "deprecation" })
	@Override
	public FieldVisitor visitField(final int access, final String name, final String desc, final String signature,
			final Object value) {
		FieldVisitor fv = super.visitField(access, name, desc, signature, value);
		if ((access & ACC_STATIC) == 0) {
			Type t = Type.getType(desc);
			int size = t.getSize();

			// 生成getter方法
			String gDesc = "()" + desc;
			MethodVisitor gv = cv.visitMethod(ACC_PRIVATE, "_get" + name, gDesc, null, null);
			gv.visitFieldInsn(GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
			gv.visitLdcInsn("_get" + name + " called");
			gv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
			gv.visitVarInsn(ALOAD, 0);
			gv.visitFieldInsn(GETFIELD, owner, name, desc);
			gv.visitInsn(t.getOpcode(IRETURN));
			gv.visitMaxs(1 + size, 1);
			gv.visitEnd();

			// 生成setter方法
			String sDesc = "(" + desc + ")V";
			MethodVisitor sv = cv.visitMethod(ACC_PRIVATE, "_set" + name, sDesc, null, null);
			sv.visitFieldInsn(GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
			sv.visitLdcInsn("_set" + name + " called");
			sv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
			sv.visitVarInsn(ALOAD, 0);
			sv.visitVarInsn(t.getOpcode(ILOAD), 1);
			sv.visitFieldInsn(PUTFIELD, owner, name, desc);
			sv.visitInsn(RETURN);
			sv.visitMaxs(1 + size, 1 + size);
			sv.visitEnd();
		}
		return fv;
	}

	/**
	 * 每当ClassReader解析出一个方法的字节码时,都会调用一次visitMethod方法 由它生成一个MethodVisitor(方法访问者).
	 */
	@Override
	public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
			final String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		return mv == null ? null : new TraceFieldCodeAdapter(mv, owner);
	}
}

class TraceFieldCodeAdapter extends MethodVisitor implements Opcodes {

	private String owner;

	public TraceFieldCodeAdapter(final MethodVisitor mv, final String owner) {
		super(Opcodes.ASM4, mv);
		this.owner = owner;
	}

	/**
	 * visitCode执行方法之前 visitFieldInsn执行方法之后
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
		if (owner.equals(this.owner)) {
			if (opcode == GETFIELD) {
				// 用INVOKESPECIAL_getf替换GETFIELD f
				String gDesc = "()" + desc;
				visitMethodInsn(INVOKESPECIAL, owner, "_get" + name, gDesc);
				return;
			} else if (opcode == PUTFIELD) {
				// 将PUTFIELD替换为INVOKESPECIAL_SETF
				String sDesc = "(" + desc + ")V";
				visitMethodInsn(INVOKESPECIAL, owner, "_set" + name, sDesc);
				return;
			}
		}
		super.visitFieldInsn(opcode, owner, name, desc);
	}
}
