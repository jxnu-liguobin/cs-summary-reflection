/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.socket;

import java.io.*;
import java.net.Socket;

public class ClientDemo {
    public static void main(String[] args) throws IOException {
        // 绑定server
        Socket socket = new Socket("127.0.0.1", 22222);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 包装socket输出流
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if ("886".equals(line)) {
                break;
            }
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        // bufferedReader.close();
        // bufferedWriter.close();
        socket.close();
    }
}
