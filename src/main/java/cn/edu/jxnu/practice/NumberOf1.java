package cn.edu.jxnu.practice;

/**
 * @description 二进制表示中1的 个数
 * @author Mr.Li
 * 
 */
public class NumberOf1 {
	public static void main(String[] args) {
		System.out.println(new NumberOf1().NumberOf1_1(2));
	}

	/**
	 * ---------------最优解--------------------------------
	 * 
	 * @param n
	 * @return
	 */
	public int NumberOf1_1(int n) {
		int count = 0;
		while (n != 0) {
			n = n & (n - 1);
			count++;
		}
		return count;

	}

	/**
	 * 以下来自牛客 ---------------正解--------------------------------
	 * 
	 * @description 不推荐的方法
	 * @param n
	 * @return
	 */
	public int NumberOf1_2(int n) {
		int t = 0;
		char[] ch = Integer.toBinaryString(n).toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] == '1') {
				t++;
			}
		}
		return t;
	}

	/**
	 * ---------------正解--------------------------------
	 * 思想：用1（1自身左移运算，其实后来就不是1了）和n的每位进行位与，来判断1的个数
	 * 
	 * @param n
	 * @return
	 */
	public static int NumberOf1_2_low(int n) {
		int count = 0;
		int flag = 1;
		while (flag != 0) {
			if ((n & flag) != 0) {
				count++;
			}
			flag = flag << 1;
		}
		return count;
	}

}