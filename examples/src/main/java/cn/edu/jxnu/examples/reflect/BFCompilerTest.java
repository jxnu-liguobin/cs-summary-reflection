package cn.edu.jxnu.examples.reflect;

import org.objectweb.asm.ClassWriter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A naive implementation of compiler for Brain**** language.
 * http://www.muppetlabs.com/~breadbox/bf/ *
 * 
 * @author Eugene Kuleshov
 */
public class BFCompilerTest {

	private BFCompiler bc;

	private ClassWriter cw;

	public static void main(String[] args) throws Throwable {
		new BFCompilerTest().testCompileHelloWorld();
		new BFCompilerTest().testCompileEcho();
		new BFCompilerTest().testCompileYaPi();
		new BFCompilerTest().testCompileTest1();
	}

	public BFCompilerTest() throws Exception {
		bc = new BFCompiler();
		cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
	}

	public void testCompileHelloWorld() throws Throwable {
		assertEquals("Hello World!\n",
				execute("Hello",
						">+++++++++[<++++++++>-]<.>+++++++[<++++>-]<+.+++++++..+++.[-]>++++++++[<++++>-]"
								+ "<.#>+++++++++++[<+++++>-]<.>++++++++[<+++>-]<.+++.------.--------.[-]>++++++++["
								+ "<++++>-]<+.[-]++++++++++.",
						""));
	}

	public void testCompileEcho() throws Throwable {
		assertEquals("AAA", execute("Echo", ",+[-.,+]", "AAA"));
	}

	public void testCompileYaPi() throws Throwable {
		assertEquals("3.1415926\n", execute("YaPi", ">+++++[<+++++++++>-]>>>>>>\r\n\r\n+++++ +++ (7 "
				+ "digits)\r\n\r\n[<<+>++++++++++>-]<<+>>+++<[->>+" + "<-[>>>]>[[<+>-]>+>>]<<<<<]>[-]>[-]>[<+>-]<[>+<["
				+ "-\r\n>>>>>>>+<<<<<<<]>[->+>>>>>>+<<<<<<<]>>>>++" + ">>-]>[-]<<<[<<<<<<<]<[->>>>>[>>>>>>>]<\r\n<<<<<"
				+ "<[>>>>[-]>>>>>>>[-<<<<<<<+>>>>>>>]<<<<<<<<[<<++" + "++++++++>>-]>[<<<<[>+>>+<<<-\r\n]>>>[<<<+>>>-]>"
				+ "-]<<<<[>>++>+<<<-]>>->[<<<+>>>-]>[-]<<<[->>+<-[" + ">>>]>[[<+>-]>+>>]<\r\n<<<<]>[-]<<<<<<<<<]>+>>>>"
				+ ">>->>>>[<<<<<<<<+>>>>>>>>-]<<<<<<<[-]++++++++++" + "<[->>+<-\r\n[>>>]>[[<+>-]>+>>]<<<<<]>[-]>[>>>>>"
				+ "+<<<<<-]>[<+>>+<-]>[<+>-]<<<+<+>>[-[-[-[-[-[-\r" + "\n[-[-[-<->[-<+<->>[<<+>>[-]]]]]]]]]]]]<[+++++["
				+ "<<<<++++++++>>>>>++++++++<-]>+<<<<-\r\n>>[>+>-<" + "<<<<+++++++++>>>-]<<<<[>>>>>>+<<<<<<-]<[>>>>>>>"
				+ ".<<<<<<<<[+.[-]]>>]>[<]<+\r\n>>>[<.>-]<[-]>>>>>" + "[-]<[>>[<<<<<<<+>>>>>>>-]<<-]]>>[-]>+<<<<[-]<]+"
				+ "+++++++++.", ""));
	}

	public void testCompileTest1() throws Throwable {
		assertEquals("H\n", execute("Test1",
				"[]++++++++++[>++++++++++++++++++>+++++++>+<<<-]A;?@![#>>" + "+<<]>[>++<[-]]>.>.", ""));
	}

	public static void assertEquals(String s1, String s2) {
		if (!s1.equals(s2)) {
			System.out.println("ERROR: expected '" + s1 + "' but got '" + s2 + "'");
		}
	}

	private String execute(final String name, final String code, final String input) throws Throwable {
		bc.compile(new StringReader(code), name, name, cw);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		InputStream is = System.in;
		PrintStream os = System.out;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(bos));

		try {
			TestClassLoader cl = new TestClassLoader(getClass().getClassLoader(), name, cw.toByteArray());
			Class<?> c = cl.loadClass(name);
			Method m = c.getDeclaredMethod("main", new Class<?>[] { String[].class });
			m.invoke(null, new Object[] { new String[0] });

		} catch (InvocationTargetException ex) {
			throw ex.getCause();
		} finally {
			System.setIn(is);
			System.setOut(os);
		}

		String output = new String(bos.toByteArray(), "ASCII");

		System.out.println(code + " WITH INPUT '" + input + "' GIVES " + output);

		return output;
	}

	/**
	 * 自定义的类加载器
	 */
	private static final class TestClassLoader extends ClassLoader {

		// 类名
		private final String className;

		// 类加载器
		private final ClassLoader cl;

		// 字节码二进制数组
		private final byte[] bytecode;

		// 全参构造
		public TestClassLoader(final ClassLoader cl, final String className, final byte[] bytecode) {
			super();
			this.cl = cl;
			this.className = className;
			this.bytecode = bytecode;
		}

		/**
		 * Class.forName(className)方法，内部实际调用的方法是Class.forName(className,true,classloader);
		 * 
		 * 第2个boolean参数表示类是否需要初始化， Class.forName(className)默认是需要初始化。 一旦初始化，就会触发目标对象的
		 * static块代码执行，static参数也也会被再次初始化。
		 * 
		 * ClassLoader.loadClass(className)方法，内部实际调用的方法是ClassLoader.loadClass(className,false);
		 * 
		 * 第2个 boolean参数，表示目标对象是否进行链接，false表示不进行链接，由上面介绍可以，
		 * 
		 * 不进行链接意味着不进行包括初始化等一些列步骤，那么静态块和静态对象就不会得到执行
		 * 
		 * JDBC使用Class.forName(classname)才能在反射回去类的时候执行static块。
		 */
		// 实现loadClass方法
		@Override
		public Class<?> loadClass(final String name) throws ClassNotFoundException {
			if (className.equals(name)) {
				return super.defineClass(className, bytecode, 0, bytecode.length);
			}
			return cl.loadClass(name);// 先在parent或bootstrap中查找，有则给jvm加载。没有则按照findClass方法查找。
			// findClass();默认抛出一个ClassNotFoundException，如果需要自己重新覆盖实现。
			// defineClass();是将你定义的字节码文件经过字节数组流解密之后，将该字节流数组生成字节码文件，也就是该类的文件的类名.class。
			// 通常用在重写findClass中，返回一个Class。如果不想要把class加载到jvm中，也可以单独使用getConstructor和newInstance来实例化一个对象。
		}

	}
}
