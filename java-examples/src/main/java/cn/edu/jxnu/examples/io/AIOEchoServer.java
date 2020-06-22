/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 梦境迷离
 * @description AIO 异步非阻塞，读完了再开始通知。【同步非阻塞是准备好了再通知】
 * @time 2018年3月28日
 */
public class AIOEchoServer {
    // 端口
    public static final int PORT = 8000;
    // 声明异步通道
    private AsynchronousServerSocketChannel server;

    public AIOEchoServer() throws IOException {
        // 开启异步通道并且绑定ip的端口
        server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
    }

    public void start() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("Server listen on " + PORT);
        server.accept(
                null,
                new CompletionHandler<AsynchronousSocketChannel, Object>() {
                    final ByteBuffer buffer = ByteBuffer.allocate(1024);

                    public void completed(AsynchronousSocketChannel result, Object attachment) {
                        System.out.println(Thread.currentThread().getName());
                        Future<Integer> writeResult = null;
                        try {
                            buffer.clear();
                            result.read(buffer).get(100, TimeUnit.SECONDS);
                            buffer.flip();
                            writeResult = result.write(buffer);
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                server.accept(null, this);
                                writeResult.get();
                                result.close();
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println("failed: " + exc);
                    }
                });
    }

    public static void main(String args[]) throws Exception {
        new AIOEchoServer().start();
        while (true) {
            Thread.sleep(1000);
        }
    }
}
