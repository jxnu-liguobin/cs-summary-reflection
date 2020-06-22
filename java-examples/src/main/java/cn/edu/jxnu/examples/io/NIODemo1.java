/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.junit.Test;

/**
 * @ClassName: NIODemo1.java
 *
 * @author Mr.Li @Description: 利用通道完成文件的复制（非直接缓冲区） @Date 2018-1-28 下午7:09:28
 * @version V1.0
 */
public class NIODemo1 {
    /**
     * @description 通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。 Channel
     *     本身不存储数据，因此需要配合缓冲区进行传输。
     */
    @Test
    public void test1() {

        long start = System.currentTimeMillis();

        FileInputStream fis = null;
        FileOutputStream fos = null;

        // ①获取通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            // 1G+的exe文件
            fis = new FileInputStream("g:/myeclipse2013.exe");
            fos = new FileOutputStream("g:/JAVA_PROJECT/myeclipse2013.exe");

            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            // ②分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // ③将通道中的数据存入缓冲区中
            while (inChannel.read(buf) != -1) {

                // 切换读取数据的模式
                buf.flip();

                // ④将缓冲区中的数据写入通道中
                outChannel.write(buf);
                // 清空缓冲区
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }
}
