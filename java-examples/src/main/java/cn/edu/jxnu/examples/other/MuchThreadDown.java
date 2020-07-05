/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.other;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/** 多线程下载 和 断点续传 */
public class MuchThreadDown {

    // //下载路径
    private String path = null;
    private String targetFilePath = "/"; // 下载文件存放目录
    private int threadCount = 3; // 线程数量

    /**
     * 构造方法
     *
     * @param path 要下载文件的网络路径
     * @param targetFilePath 保存下载文件的目录
     * @param threadCount 开启的线程数量,默认为 3
     */
    public MuchThreadDown(String path, String targetFilePath, int threadCount) {
        this.path = path;
        this.targetFilePath = targetFilePath;
        this.threadCount = threadCount;
    }

    /** 下载文件 */
    public void download() throws Exception {
        // 连接资源
        URL url = new URL(path);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);

        int code = connection.getResponseCode();
        if (code == 200) {
            // 获取资源大小
            int connectionLength = connection.getContentLength();
            System.out.println(connectionLength);
            // 在本地创建一个与资源同样大小的文件来占位
            RandomAccessFile randomAccessFile =
                    new RandomAccessFile(new File(targetFilePath, getFileName(url)), "rw");
            randomAccessFile.setLength(connectionLength);
            /*
             * 将下载任务分配给每个线程
             */
            int blockSize = connectionLength / threadCount; // 计算每个线程理论上下载的数量.
            for (int threadId = 0; threadId < threadCount; threadId++) { // 为每个线程分配任务
                int startIndex = threadId * blockSize; // 线程开始下载的位置
                int endIndex = (threadId + 1) * blockSize - 1; // 线程结束下载的位置
                if (threadId == (threadCount - 1)) { // 如果是最后一个线程,将剩下的文件全部交给这个线程完成
                    endIndex = connectionLength - 1;
                }

                new DownloadThread(threadId, startIndex, endIndex).start(); // 开启线程下载
            }
            randomAccessFile.close();
        }
    }

    // 下载的线程
    private class DownloadThread extends Thread {

        private int threadId;
        private int startIndex;
        private int endIndex;

        public DownloadThread(int threadId, int startIndex, int endIndex) {
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            System.out.println("线程" + threadId + "开始下载");
            try {
                // 分段请求网络连接,分段将文件保存到本地.
                URL url = new URL(path);

                // 加载下载位置的文件 临时文件
                File downThreadFile = new File(targetFilePath, "downThread_" + threadId + ".dt");
                RandomAccessFile downThreadStream = null;
                if (downThreadFile.exists()) { // 如果文件存在
                    downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
                    String startIndex_str = downThreadStream.readLine();
                    if (null == startIndex_str || "".equals(startIndex_str)) { // 网友
                        // imonHu
                        this.startIndex = Integer.parseInt(startIndex_str);
                    } else {
                        this.startIndex = Integer.parseInt(startIndex_str) - 1; // 设置下载起点
                    }
                } else {
                    downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
                }

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);

                // 设置分段下载的头信息。 Range:做分段数据请求用的。格式: Range bytes=0-1024 或者
                // bytes:0-1024
                connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

                System.out.println(
                        "线程_" + threadId + "的下载起点是 " + startIndex + "  下载终点是: " + endIndex);

                if (connection.getResponseCode() == 206) { // 200：请求全部资源成功，
                    // 206代表部分资源请求成功
                    InputStream inputStream = connection.getInputStream(); // 获取流
                    RandomAccessFile randomAccessFile =
                            new RandomAccessFile(
                                    new File(targetFilePath, getFileName(url)),
                                    "rw"); // 获取前面已创建的文件.
                    randomAccessFile.seek(startIndex); // 文件写入的开始位置.

                    /*
                     * 将网络流中的文件写入本地
                     */
                    byte[] buffer = new byte[1024];
                    int length = -1;
                    int total = 0; // 记录本次下载文件的大小
                    while ((length = inputStream.read(buffer)) > 0) {
                        randomAccessFile.write(buffer, 0, length);
                        total += length;
                        /*
                         * 将当前现在到的位置保存到文件中
                         */
                        downThreadStream.seek(0);
                        downThreadStream.write((startIndex + total + "").getBytes("UTF-8"));
                    }

                    downThreadStream.close();
                    inputStream.close();
                    randomAccessFile.close();
                    cleanTemp(downThreadFile); // 删除临时文件
                    System.out.println("线程" + threadId + "下载完毕");
                } else {
                    System.out.println("响应码是" + connection.getResponseCode() + ". 服务器不支持多线程下载");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 删除线程产生的临时文件
    private synchronized void cleanTemp(File file) {
        file.delete();
    }

    // 获取下载文件的名称
    private String getFileName(URL url) {
        String filename = url.getFile();
        String[] s = filename.split("\\?");
        String s2 = s[0].substring(s[0].lastIndexOf("."));
        System.out.println("后缀名：" + s2);
        return filename.substring(filename.lastIndexOf("sourceid") + 9) + s2;
    }

    public static void main(String[] args) {
        try {
            new MuchThreadDown(null, null, 3).download();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
