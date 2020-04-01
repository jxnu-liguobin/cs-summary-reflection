package io.github.dreamylost.practice;

import java.io.*;

/**
 * @description 文件复制
 */
public class Main12 {

	public static void main(String[] args) throws IOException {
		System.out.println("请依次输入：源文件路径 目标文件路径！");
		if (args.length < 2) {
			System.out.println("参数不合法！");
			System.exit(0);
		}
		String path = args[0];
		String target = args[1];
		String resultString = Main12.readFile(path);
		Main12.writeToFile(target, resultString);
	}

	public static String readFile(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("源文件不存在！");
			System.exit(1);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		char[] buf = new char[1024];// 设置一个1024字符的缓冲区
		StringBuffer stringBuffer = new StringBuffer();
		try {
			while (br.read(buf) > 0) {
				stringBuffer.append(buf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	/**
	 * @description 复制的路径，和文件字节流
	 * @param path
	 * @param bytes
	 * @throws IOException
	 */
	public static void writeToFile(String path, String result) throws IOException {
		File f = new File(path);
		PrintWriter outPrintWriter = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8")));
		outPrintWriter.write(result);
		outPrintWriter.flush();// 迫使缓冲区被输出
		outPrintWriter.close();

	}

}
