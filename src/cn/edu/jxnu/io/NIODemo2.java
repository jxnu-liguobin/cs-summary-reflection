package cn.edu.jxnu.io;

import org.junit.Test;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @ClassName: NIODemo2.java
 * @author Mr.Li
 * @Description: 使用直接缓冲区完成文件的复制(内存映射文件) JDK1.7 NIO.2版
 */
public class NIODemo2 {

	// 只支持Byte 不能由程序来控制结束的时间 由os决定
	@Test
	public void test2() throws IOException {
		long start = System.currentTimeMillis();
		// myeclipse2013.exe 1G -> out of memory 因为是直接使用内存 我的机器是4G可用不足
		// 使用小的图片测试
		FileChannel inChannel = FileChannel.open(Paths.get("g:/复杂度.png"),
				StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(
				Paths.get("g:/JAVA_PROJECT/复杂度.png"), StandardOpenOption.WRITE,
				StandardOpenOption.READ, StandardOpenOption.CREATE);

		// 内存映射文件
		MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0,
				inChannel.size());
		MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0,
				inChannel.size());

		// 直接对缓冲区进行数据的读写操作
		byte[] dst = new byte[inMappedBuf.limit()];
		inMappedBuf.get(dst);
		outMappedBuf.put(dst);

		inChannel.close();
		outChannel.close();

		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
	}
}
