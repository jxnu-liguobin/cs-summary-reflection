package cn.edu.jxnu.practice;


/**
 * @description 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 *              使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 * @author Mr.Li
 * 
 */
public class ReOrderArray {
	public static void main(String[] args) {
		int a[] = { 1, 2, 3, 4, 5, 6, 7 };
		reOrderArray(a);
	}

	/**
	 * 1.要想保证原有次序，则只能顺次移动或相邻交换。
	 * 2.i从左向右遍历，找到第一个偶数。
	 * 3.j从i+1开始向后找，直到找到第一个奇数。
	 * 4.将[i,...,j-1]的元素整体后移一位，最后将找到的奇数放入i位置，然后i++。
	 * 5.終止條件：j向後遍歷查找失敗。
	 */
	public void reOrderArray3(int[] a) {
		if (a == null || a.length == 0)
			return;
		int i = 0, j;
		while (i < a.length) {
			while (i < a.length && !isEven(a[i]))
				i++;
			j = i + 1;  //从第一个偶数后面找奇数
			while (j < a.length && isEven(a[j]))
				j++;
			if (j < a.length) {
				int tmp = a[j];
				for (int j2 = j - 1; j2 >= i; j2--) {
					a[j2 + 1] = a[j2];
				}
				a[i++] = tmp;
			} else {// 查找失敗
				break;
			}
		}
	}

	boolean isEven(int n) {
		if (n % 2 == 0)
			return true;
		return false;
	}

	/**
	 * 整体思路： 首先统计奇数的个数 然后新建一个等长数组，设置两个指针， 奇数指针从0开始，偶数指针从奇数个数的末尾开始 遍历，填数
	 */
	public void reOrderArray2(int[] array) {
		if (array.length == 0 || array.length == 1)
			return;
		int oddCount = 0, oddBegin = 0;
		int[] newArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			// 奇数
			if ((array[i] & 1) == 1)
				oddCount++;
		}
		for (int i = 0; i < array.length; i++) {
			if ((array[i] & 1) == 1)
				newArray[oddBegin++] = array[i];
			else
				newArray[oddCount++] = array[i];
		}
		for (int i = 0; i < array.length; i++) {
			array[i] = newArray[i];
		}
	}
	/**
	 * @description 直接处理
	 * @param array
	 */
	public static void reOrderArray(int[] array) {
		int left = 0;
		int right = 0;
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			if ((array[i] & 1) == 0) {
				count++;
			}
		}
		int[] a = new int[array.length - count];
		int[] b = new int[count];
		for (int i = 0; i < array.length; i++) {
			if ((array[i] & 1) == 0) {
				b[right++] = array[i];
			} else {
				a[left++] = array[i];
			}
		}
		left = 0;
		right = 0;
		for (int i = 0; i < array.length; i++) {
			if (left < a.length) {
				array[i] = a[left++];
				continue;
			}
			if (right < b.length) {
				array[i] = b[right++];
				continue;
			}

		}
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}
}