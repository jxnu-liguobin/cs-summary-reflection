package cn.edu.jxnu.other;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @description
 * 客户端
 *  现分别有一个通过socket发送文件的客户端以及一个通过socket接收文件的服务端： a)
 *              客户端从/tmp/src.data文件中读取文件内容，通过网络socket将文件内容发给服务端 b)
 *              服务端监听10000端口，当10000端口接收到客户端连接请求时，从连接读取文件内容，并写入/tmp/dst.data中
 *              1. 试写出客户端和服务端的具体实现代码（15分）
 *              2.如果客户端需要知道服务端已经完全接收到所有文件数据并成功写入/tmp/dst.data，有何实现方式？请说出你的思路（5分）
 * @author Mr.Li
 * 
 */
public class MyClient {

	public static void main(String[] args) {
		MyClient myClient = new MyClient();
		myClient.sendFile();
	}

	public void sendFile() {
		Socket socket = new Socket();
		SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 10000);
		FileInputStream fileInputStream = null;
		OutputStream outputStream = null;
		// 定义缓冲区
		byte[] buffer = new byte[1024];
		try {
			socket.connect(socketAddress);
			fileInputStream = new FileInputStream("/tmp/src.data");
			outputStream = socket.getOutputStream();
			int length;
			while ((length = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
				outputStream.write(buffer, 0, length);
				outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socket != null && socket.isConnected()) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}