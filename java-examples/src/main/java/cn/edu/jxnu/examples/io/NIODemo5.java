/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.Test;

/**
 * @ClassName: NIODemo5.java
 *
 * @author Mr.Li @Description: 解码与编码
 */
public class NIODemo5 {
    // 字符集
    @Test
    public void test6() throws IOException {
        Charset cs1 = Charset.forName("UTF-8");

        // 获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        // 获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("哈哈哈哈！");
        cBuf.flip();

        // 编码
        ByteBuffer bBuf = ce.encode(cBuf);

        for (int i = 0; i < 12; i++) {
            System.out.println(bBuf.get());
        }

        // 解码
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());

        System.out.println("------------------------------------------------------");
        // 乱码
        Charset cs2 = Charset.forName("GBK");
        bBuf.flip();
        CharBuffer cBuf3 = cs2.decode(bBuf);
        System.out.println(cBuf3.toString());
    }

    @Test
    public void test5() {
        Map<String, Charset> map = Charset.availableCharsets();

        Set<Entry<String, Charset>> set = map.entrySet();

        for (Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
