package cn.edu.jxnu.practice;

/**
 * @description 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。 就是斐波那契数列
 * @author Mr.Li
 * 
 */
public class FibonacciDemo3 {
	public static void main(String[] args) {
		// System.out.println(new Solution().JumpFloor(50));
		System.out.println(new FibonacciDemo3().jumpFloorII(10));
	}

	/**
	 * @description 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
	 * @param target
	 * @return
	 */
	/**
	 * @description 移位
	 * @param target
	 * @return
	 */
	public int jumpFloorII(int number) {
		// 通过移位计算2的次方
		return 1 << (number - 1);
	}

	/**
	 * @description 递归
	 * @param target
	 * @return
	 */
	public int JumpFloorII2(int target) {
		if (target <= 0) {
			return -1;
		} else if (target == 1) {
			return 1;
		} else {
			return 2 * JumpFloorII2(target - 1);
		}
	}

	/**
	 * 斐波那契
	 */
	/**
	 * 
	 * @param target
	 * @return
	 */
	public int umpFloor(int target) {
		if (target <= 0) {
			return -1;
		} else if (target == 1) {
			return 1;
		} else if (target == 2) {
			return 2;
		} else {
			return umpFloor(target - 1) + umpFloor(target - 2);
		}

	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public int jumpFloor(int number) {
		if (number <= 0) {
			return 0;
		}
		if (number == 1) {
			return 1;
		}
		if (number == 2) {
			return 2;
		}
		int first = 1, second = 2, third = 0;
		for (int i = 3; i <= number; i++) {
			third = first + second;
			first = second;
			second = third;
		}
		return third;
	}
}