package cn.edu.jxnu.jvm.classloader;

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
		// ins.localVarGc1();
		// ins.localVarGc2();
		// ins.localVarGc3();
		// ins.localVarGc4();
		ins.localVarGc5();
	}
}
