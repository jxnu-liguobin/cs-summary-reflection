package cn.edu.jxnu.other;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Tool {

	public final static String MD5(String str) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', };
		byte[] btInput = str.getBytes();
		try {
			// 获得MD5摘要算法的MessageDigest对象，提供信息摘要功能 MessageDigest--消息摘要
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用输入的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] code = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			char strChar[] = new char[code.length * 2];
			int k = 0;
			for (int i = 0; i < code.length; i++) {
				byte byte1 = code[i];
				strChar[k++] = hexDigits[byte1 >>> 4 & 0xf];// 同理得到高四位
				strChar[k++] = hexDigits[byte1 & 0xf]; // 任何数字与0xf相与，得到低四位
			}
			return new String(strChar);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(MD5Tool.MD5("加密"));
		System.out.println(MD5Tool.MD5("加密"));
		System.out.println(MD5Tool.MD5("加密1"));
		System.out.println("---------------");
		System.out.println(MD5Tool.MD5("abcdefg"));
		System.out.println(MD5Tool.MD5("abcdefg"));
		System.out.println(MD5Tool.MD5("abcdefh"));
	}
}
