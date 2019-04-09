package cn.edu.jxnu.encode;//package cn.edu.jxnu.encode;
//
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//
///**
// * 
// * BASE64 转码过程例子： 3*8=4*6 内存1个字节占8位 转前： s 1 3 先转成ascii：对应 115 49 51 2进制：
// * 01110011 00110001 00110011 6个一组（4组） 011100110011000100110011 然后才有后面的 011100
// * 110011 000100 110011 然后计算机是8位8位的存数 6不够，自动就补两个高位0了 所有有了 高位补0 科学计算器输入 00011100
// * 00110011 00000100 00110011 得到 28 51 4 51 查对下照表 c z E z
// * 
// * @author: 梦境迷离
// * @version 1.0 @time. 2018年4月17日
// */
//@SuppressWarnings("restriction")
//public class BASE {
//	/**
//	 * BASE解密
//	 * 
//	 * @param key
//	 * @return
//	 * @throws Exception
//	 */
//	public static byte[] decryptBASE(String key) throws Exception {
//		return (new BASE64Decoder()).decodeBuffer(key);
//	}
//
//	/**
//	 * BASE加密
//	 * 
//	 * @param key
//	 * @return
//	 * @throws Exception
//	 */
//	public static String encryptBASE(byte[] key) throws Exception {
//		return (new BASE64Encoder()).encodeBuffer(key);
//	}
//
//	public static void main(String[] args) {
//		String str = "Hello world";
//		try {
//			String result = BASE.encryptBASE(str.getBytes());
//			System.out.println("result-->加密数据:" + result);
//			byte result1[] = BASE.decryptBASE(result);
//			String str1 = new String(result1);
//			System.out.println("str-->解密数据:" + str1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}