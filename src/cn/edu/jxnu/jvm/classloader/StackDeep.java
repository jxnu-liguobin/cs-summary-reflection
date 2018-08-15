package cn.edu.jxnu.jvm.classloader;

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