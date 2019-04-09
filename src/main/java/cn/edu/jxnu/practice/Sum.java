package cn.edu.jxnu.practice;

/**
 * @description 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、
 *              case等关键字及条件判断语句（A?B:C）。
 * @author Mr.Li
 *
 */
public class Sum {
	public static void main(String[] args) {
		System.out.println(new Sum().sum_Solution(10));
	}

	public int sum_Solution(int n) {
		int sum = (int) (Math.pow(n, 2) + n);
		return sum >> 1;
	}

	public int Sum_Solution2(int n) {
		int ans = n;
		@SuppressWarnings("unused")
		boolean flag = (n > 0) && ((ans += sum_Solution(n - 1)) > 0);
		return ans;
	}
}