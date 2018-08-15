package cn.edu.jxnu.practice;

/**
 * 使用位操作和减法求解最大公约数
 * 
 * 编程之美：2.7
 * 
 * 对于 a 和 b 的最大公约数 f(a, b)，有：
 * 
 * 如果 a 和 b 均为偶数，f(a, b) = 2*f(a/2, b/2); 如果 a 是偶数 b 是奇数，f(a, b) = f(a/2, b); 如果
 * b 是偶数 a 是奇数，f(a, b) = f(a, b/2); 如果 a 和 b 均为奇数，f(a, b) = f(b, a-b); 乘 2 和除 2
 * 都可以转换为移位操作。
 * 
 * @author 梦境迷离.
 * @time 2018年6月22日
 * @version v1.0
 */
public class GetGcd {

	public static void main(String[] args) {
		int a = 3, b = 5;
		int ret = GetGcd.gcd(a, b);
		System.out.println(ret);
	}

	public static int gcd(int a, int b) {
		if (a < b) {
			return gcd(b, a);
		}
		if (b == 0) {
			return a;
		}
		boolean isAEven = isEven(a), isBEven = isEven(b);
		if (isAEven && isBEven) {
			return 2 * gcd(a >> 1, b >> 1);
		} else if (isAEven && !isBEven) {
			return gcd(a >> 1, b);
		} else if (!isAEven && isBEven) {
			return gcd(a, b >> 1);
		} else {
			return gcd(b, a - b);
		}
	}

	// 判断a是否为偶数，是返回true
	private static boolean isEven(int a) {
		return (a & 1) == 0;
	}

	// 最大公约数
	// 1、b == 0,返回a
	// 2、a = b,b = a % b
	public static int gcd2(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	// 最小公倍数为两数的乘积除以最大公约数。
	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	// 避免大数取模
	public static int gcd3(int a, int b) {
		if (a < b) {
			return gcd3(b, a);
		}
		return b == 0 ? a : gcd3(a - b, b);

	}
}
