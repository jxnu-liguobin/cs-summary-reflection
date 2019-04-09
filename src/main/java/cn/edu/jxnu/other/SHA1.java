package cn.edu.jxnu.other;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 总结2进制<----->16进制 注释的是sha的部分
 * 
 * @author 梦境迷离
 *
 */
public class SHA1 {

	public static void main(String[] args) {
		// String string = "hello world";
		// String enString = SHA1.getSha1(string);
		// System.out.println(string + "加密->" + enString.toUpperCase());

		String i = "5F";// 1byte 8位，用16进制表示只占2位 最大127 最小-128 补码最高位是符号位
		System.out.println("16进制：" + i);
		byte[] bytes = toBytes(i);
		StringBuilder stringBuilder = new StringBuilder();
		for (byte b : bytes) {
			System.out.println("二进制01表示字符串：" + byteToBit(b));
			stringBuilder.append(b);
		}
		System.out.println("二进制字节数组输出值：" + stringBuilder.toString());
		String ss = null;
		ss = bytes2hex(bytes);
		System.out.println("二进制转化位16进制字符串：" + ss);
	}

	/**
	 * 将16进制转化为二进制
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] toBytes(String str) {
		if (str == null || str.trim().equals("")) {
			return new byte[0];
		}

		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			String subStr = str.substring(i * 2, i * 2 + 2);
			bytes[i] = (byte) Integer.parseInt(subStr, 16);
		}

		return bytes;
	}

	/**
	 * 2进制转16进制
	 */
	private static final String HEX = "0123456789abcdef";

	public static String bytes2hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) { // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			sb.append(HEX.charAt((b >> 4) & 0x0f)); // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			sb.append(HEX.charAt(b & 0x0f));
		}

		return sb.toString();
	}

	/**
	 * 把byte转为字符串的bit
	 */
	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
				+ (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1)
				+ (byte) ((b >> 0) & 0x1);
	}

	/**
	 * 将byte转换为一个长度为8的byte数组，数组每个值代表1bit
	 */
	public static byte[] getBooleanArray(byte b) {
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}
		return array;
	}

	public static String getSha1(String str) {
		if (null == str || 0 == str.length()) {
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			// 得到SHA1的MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			// 使用输入的字节更新摘要
			mdTemp.update(str.getBytes("UTF-8"));
			// 获得加密信息
			byte[] md = mdTemp.digest();

			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			// 将字节转化为16进制
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				// hexDigits[byte0 >>> 4 & 0xf] ,byte0无符右移4位,取与获得高四位(<=15),
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				// hexDigits[byte0 & 0xf]获取低四位,这也是char str[] = new char[j *
				// 2];的原因,
				buf[k++] = hexDigits[byte0 & 0xf];
			}

			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
