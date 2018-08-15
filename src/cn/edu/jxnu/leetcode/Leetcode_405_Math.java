package cn.edu.jxnu.leetcode;

/**
 * 16 进制
 * 
 * 405. Convert a Number to Hexadecimal (Easy)
 * 
 * Input: 26
 * 
 * Output: "1a"
 * 
 * Input: -1
 * 
 * Output: "ffffffff" 负数要用它的补码形式。
 * 
 * @author 梦境迷离.
 * @time 2018年6月22日
 * @version v1.0
 */
public class Leetcode_405_Math {

	public static void main(String[] args) {
		String ret = Leetcode_405_Math.toHex(26);
		System.out.println(ret);
	}

	public static String toHex(int num) {
		char[] map = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		if (num == 0)
			return "0";
		StringBuilder sb = new StringBuilder();
		while (num != 0) {
			sb.append(map[num & 0b1111]);
			num >>>= 4; // 因为考虑的是补码形式，因此符号位就不能有特殊的意义，需要使用无符号右移，左边填 0
		}
		return sb.reverse().toString();
	}
}
