package cn.edu.jxnu.practice;

/**
 * @Description <p>
 *              把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
 *              例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
 *              NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
 *              </p>
 * @author Mr.Li
 * 
 */

public class SearchMinInArray {
	public static void main(String[] args) {
		SearchMinInArray sArray = new SearchMinInArray();
		// 原数组{1，2，3，4，5}
		int[] array = { 3, 4, 5, 1, 2 };
		System.out.println("方法1：" + sArray.minNumberInRotateArray(array));
		System.out.println("方法2：" + sArray.minNumberInRotateArray2(array));
	}

	/**
	 * @description 通过函数单调性 时间复杂度o(n)但简单
	 * @param array
	 * @return
	 */
	public int minNumberInRotateArray(int[] array) {
		if (array.length == 0) {
			return 0;
		}
		if (array.length == 1) {
			return array[0];
		}
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] > array[i + 1]) {
				/* 临界处 */
				return array[i + 1];

			} else {
				if (i == array.length - 1) {
					return array[0];
				}
			}
		}

		return 0;

	}

	/**
	 * @description 二分法查找 时间复杂度log(n) 容易出错
	 * @param array
	 * @return
	 */
	public int minNumberInRotateArray2(int[] array) {
		int low = 0;
		int high = array.length - 1;
		while (low < high) {
			/* 防止溢出 */
			int mid = low + (high - low) / 2;
			if (array[mid] > array[high]) {
				low = mid + 1;
				/*
				 * 特别注意这
				 */
			} else if (array[mid] == array[high]) {
				high = high - 1;
			} else {
				/* 因为只有2个元素的时候，mid指向较小者，即向下取整 */
				high = mid;
			}
		}
		return array[low];
	}

}