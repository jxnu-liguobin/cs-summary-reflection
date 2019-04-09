package cn.edu.jxnu.practice;

/**
 * @description 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 *              例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
 *              由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
 * @author Mr.Li
 * 
 */
public class MoreThanHalfNum {

	public static void main(String[] args) {
		int[] array = { 1, 1, 1};
		int result = new MoreThanHalfNum().MoreThanHalfNum_Solution(array);
		System.out.println(result);
	}

	public int MoreThanHalfNum_Solution(int[] array) {
		boolean result = false;
		int s = find(array, array.length);
		if (s != Integer.MAX_VALUE) {
			result = isExistence(array, s);
		}
		if (result) {
			return s;
		}
		return 0;

	}

	private static int find(int[] arr, int N) {
		if (arr.length == 0 || arr == null)
			return Integer.MAX_VALUE;
		int result = 0;
		int times;
		for (int i = times = 0; i < N; i++) {
			if (times == 0) {
				result = arr[i];
				times = 1;
			} else {
				if (result == arr[i]) {
					times++;
				} else {
					times--;
				}
			}
		}
		return result;
	}

	private static boolean isExistence(int[] arr, int number) {
		// 找到number后，检查number的出现次数是否多于数组的一半
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == number)
				count++;
		}
		if (count > arr.length / 2)
			return true;
		else
			return false;
	}

}