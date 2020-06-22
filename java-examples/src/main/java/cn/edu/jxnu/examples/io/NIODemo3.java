/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.io;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.junit.Test;

/**
 * @ClassName: NIODemo3.java
 *
 * @author Mr.Li @Description: 通道之间的数据传输(直接缓冲区)
 */
public class NIODemo3 {

    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("g:/复杂度.png"), StandardOpenOption.READ);
        FileChannel outChannel =
                FileChannel.open(
                        Paths.get("g:/JAVA_PROJECT/复杂度.png"),
                        StandardOpenOption.WRITE,
                        StandardOpenOption.READ,
                        StandardOpenOption.CREATE);

        // inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }
}
