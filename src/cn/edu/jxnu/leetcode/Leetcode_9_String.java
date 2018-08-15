package cn.edu.jxnu.leetcode;

/**
 * 判断一个整数是否是回文数
 * 
 * 9. Palindrome Number (Easy) 要求不能使用额外空间，也就不能将整数转换为字符串进行判断。
 * 
 * @author 梦境迷离.
 * @time 2018年7月8日
 * @version v1.0
 */
public class Leetcode_9_String {

	public static void main(String[] args) {

	}

	/**
	 * 将整数分成左右两部分，右边那部分需要转置，然后判断这两部分是否相等。
	 */
	public boolean isPalindrome(int x) {
		if (x == 0) {
			return true;
		}
		if (x < 0 || x % 10 == 0) {
			return false;
		}
		int right = 0;
		while (x > right) {// x=12,right=0,x=1,right=2 || x=11,right=1,x=1,right=11
			right = right * 10 + x % 10;
			x /= 10;
		}
		return x == right || x == right / 10;
	}

	public boolean isPalindrome2(int x) {
		if (x < 0) {
			return false;// 负数不能
		}
		if (x < 9) {
			return true;// 0~9都可以
		}
		long ret = 0;// 反转后的值,可能溢出，可以加下面判断
		int originalNum = x;// 保存原x值
		// 反转数字. eg: 321 --> 123
		// if (ret > Integer.MAX_VALUE / 10 || ret < Integer.MIN_VALUE / 10)
		// return false;
		while (x != 0) {
			ret = ret * 10 + x % 10;
			x /= 10;
		}
		return ret == originalNum;

	}

}
