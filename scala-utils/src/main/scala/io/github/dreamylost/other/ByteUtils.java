/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.other;

/**
 * 字节 2进制 16进制
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-20
 */
public class ByteUtils {

    /** 2进制转16进制 */
    private static final String HEX = "0123456789abcdef";

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
     * 字节数组 -> 16进制字符串
     *
     * @param bytes
     * @return
     */
    public static String bytes2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) { // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(
                    HEX.charAt(
                            (b >> 4)
                                    & 0x0f)); // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(b & 0x0f));
        }

        return sb.toString();
    }

    /**
     * byte -> 字符串形式的bit
     *
     * @param b
     * @return
     */
    public static String byteToBitString(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1)
                + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1)
                + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1)
                + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1)
                + (byte) ((b >> 0) & 0x1);
    }

    /**
     * byte -> 长度为8的byte数组 数组每个值代表1bit
     *
     * @param b
     * @return
     */
    public static byte[] getByteArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }
}
