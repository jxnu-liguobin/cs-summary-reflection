/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * @author 梦境迷离
 * @description NIO客户端
 * @time 2018年3月28日
 */
public class MultiThreadNIOEchoClient {
    private Selector selector;

    /**
     * @author 梦境迷离
     * @description 客户端初始化操作
     * @time 2018年3月28日
     */
    public void init(String ip, int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false); // 设置非阻塞模式
        this.selector = SelectorProvider.provider().openSelector(); // 取得选择器
        channel.connect(new InetSocketAddress(ip, port)); // 绑定端口
        // 注册事件与通道到选择器中，选择器可以管理该通道，channel数据准备好了，选择器就好收到通知
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    /**
     * @author 梦境迷离
     * @description 客户端业务操作
     * @time 2018年3月28日
     */
    public void work() throws IOException {
        while (true) {
            if (!selector.isOpen()) {
                break;
            }
            selector.select(); // 一个准备好的数据都没有则将会阻塞
            Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isValid() && key.isConnectable()) {
                    // 连接
                    connect(key);
                } else if (key.isValid() && key.isReadable()) {
                    // 读取
                    read(key);
                } // 用完必须去除
                it.remove();
            }
        }
    }

    /**
     * @author 梦境迷离
     * @throws IOException
     * @description 读取
     * @time 2018年3月28日
     */
    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        channel.read(byteBuffer);
        byte[] data = byteBuffer.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到消息:" + msg);
        channel.close();
        key.selector().close();
    }

    /**
     * @author 梦境迷离
     * @description 连接
     * @time 2018年3月28日
     */
    private void connect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 如果正在连接，则完成连接
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap("Hello Server !\r\n".getBytes()));
        // 注册读事件为感兴趣的事件
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException {
        MultiThreadNIOEchoClient cEchoClient = new MultiThreadNIOEchoClient();
        cEchoClient.init("127.0.0.1", 8000);
        cEchoClient.work();
    }
}
