/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.io;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 梦境迷离
 * @description 使用NIO实现的Echo服务器
 * @time 2018年3月28日
 */
public class MultiThreadNIOEchoServer {
    // 存放socket对应的启动时间，为了计算耗时
    public static Map<Socket, Long> geym_time_stat = new HashMap<Socket, Long>(10240);

    /**
     * @author 梦境迷离
     * @description 一个实例代表一个客户端
     * @time 2018年3月28日
     */
    class EchoClient {
        private LinkedList<ByteBuffer> outq;

        EchoClient() {
            outq = new LinkedList<ByteBuffer>();
        }

        public LinkedList<ByteBuffer> getOutputQueue() {
            return outq;
        }

        public void enqueue(ByteBuffer bb) {
            // 左边入队
            outq.addFirst(bb);
        }
    }

    /**
     * @author 梦境迷离
     * @description 单独是数据处理线程
     * @time 2018年3月28日
     */
    class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) sk.attachment();
            echoClient.enqueue(bb);
            // 需要重新注册感兴趣的消息事件，写将写事件作为感兴趣的，这样在通道准备好写入，就能通知线程
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            // 强迫selector立即返回
            selector.wakeup();
        }
    }

    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();

    /**
     * @author 梦境迷离
     * @description 与客户端建立链接
     * @time 2018年3月28日
     */
    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();
            // 设置为非阻塞的
            clientChannel.configureBlocking(false);
            // 注册可读
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            // 将客户端实例作为附件，附加到表示这个连接 的SelectionKey上，这样整个连接处理都可以共享这个客户端
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);
            // 获取客户端的地址
            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress() + ".");
        } catch (Exception e) {
            System.out.println("Failed to accept new client.");
            e.printStackTrace();
        }
    }

    /**
     * @author 梦境迷离
     * @description 处理读取事件
     * @time 2018年3月28日
     */
    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        // 分配缓冲区
        ByteBuffer bb = ByteBuffer.allocate(2048);
        int len;
        try {
            len = channel.read(bb);
            if (len < 0) {
                // 关闭连接
                disconnect(sk);
                return;
            }
        } catch (Exception e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
            disconnect(sk);
            return;
        }

        // 切换读取数据的模式
        bb.flip();
        // 提交任务
        tp.execute(new HandleMsg(sk, bb));
    }

    /**
     * @author 梦境迷离
     * @description 处理写事件
     * @time 2018年3月28日
     */
    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        // 获取附属品 - 客户端实例
        EchoClient echoClient = (EchoClient) sk.attachment();
        // 获取该客户端实例的缓冲队列【发送内容列表】
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();
        // 头插，所以这里相当于FIFO
        ByteBuffer bb = outq.getLast();
        // 写入数据到客户端
        try {
            int len = channel.write(bb);
            if (len == -1) {
                disconnect(sk);
                return;
            }
            if (bb.remaining() == 0) {
                // 全部发生完成则移除这个缓存对象
                outq.removeLast();
            }
        } catch (Exception e) {
            System.out.println("Failed to write to client.");
            e.printStackTrace();
            disconnect(sk);
        }

        // 避免每次写的时候执行doWork方法而实际上又无数据可写
        if (outq.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void disconnect(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();

        InetAddress clientAddress = channel.socket().getInetAddress();
        System.out.println(clientAddress.getHostAddress() + " disconnected.");

        try {
            channel.close();
        } catch (Exception e) {
            System.out.println("Failed to close client socket channel.");
            e.printStackTrace();
        }
    }

    private void startServer() throws Exception {
        selector = SelectorProvider.provider().openSelector();
        // 创建非阻塞的服务器Socket通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // 绑定到指定的端口
        InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);
        // 注册到选择器，为监听,返回SelectionKey。无任何数据准备好则连接将阻塞
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 轮询
        for (; ; ) {
            selector.select();
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> i = readyKeys.iterator();
            long e = 0;
            while (i.hasNext()) {
                SelectionKey sk = (SelectionKey) i.next();
                // 连接操作处理
                if (sk.isAcceptable()) {
                    doAccept(sk);
                }
                // 可读操作处理
                else if (sk.isValid() && sk.isReadable()) {
                    if (!geym_time_stat.containsKey(((SocketChannel) sk.channel()).socket()))
                        geym_time_stat.put(
                                ((SocketChannel) sk.channel()).socket(),
                                System.currentTimeMillis());
                    doRead(sk);
                }
                // 可写操作处理
                else if (sk.isValid() && sk.isWritable()) {
                    doWrite(sk);
                    e = System.currentTimeMillis();
                    long b = geym_time_stat.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("spend:" + (e - b) + "ms");
                }
                // 一定要取出这个SelectionKey,否则会重复处理相同的SelectionKey
                i.remove();
            }
        }
    }

    // 启动服务器线程
    public static void main(String[] args) {
        MultiThreadNIOEchoServer echoServer = new MultiThreadNIOEchoServer();
        try {
            echoServer.startServer();
        } catch (Exception e) {
            System.out.println("Exception caught, program exiting...");
            e.printStackTrace();
        }
    }
}
