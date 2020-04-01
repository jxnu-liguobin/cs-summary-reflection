package io.github.dreamylost.practice;

/**
 * 
 * @description 统计一个数字在排序数组中出现的次数。
 * @author Mr.Li
 * 
 */
public class GetNumberOfK {

	public static void main(String[] args) {
		int[] array = { 1, 2, 3, 4, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7 };
		int s = new GetNumberOfK().GetNumberOfK2(array, 7);
		System.out.println(s);
	}

	/**
	 * @description 暴力
	 * @param array
	 * @param k
	 * @return
	 */
	public int getNumberOfK(int[] array, int k) {
		if (array == null || array.length == 0)
			return 0;
		int result = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == k) {
				result++;
			}
		}
		return result;
	}

	/**
	 * @description 二分法 第一个k减去最后一个k+1
	 * @param array
	 * @param k
	 * @return
	 */
	public int GetNumberOfK2(int[] array, int k) {
		if (array == null || array.length <= 0) {
			return 0;
		}
		int count = 0;
		int start = 0;
		int end = array.length - 1;
		int firstIndex = getFirst(array, start, end, k);
		int lastIndex = getLast(array, start, end, k);
		if (firstIndex != -1 && lastIndex != -1) {
			count = lastIndex - firstIndex + 1;
		}
		return count;
	}

	/**
	 * @description 求第一个k
	 * @param array
	 * @param start
	 * @param end
	 * @param k
	 * @return
	 */
	public static int getFirst(int[] array, int start, int end, int k) {
		if (start > end) {
			return -1;
		}
		int middIndex = start + (end - start) / 2;
		int midNum = array[middIndex];
		// 1、当前元素不是数组的第一个元素，则左边相邻元素互不相同 2、数组的第一个即是
		if (midNum == k) {
			if ((middIndex > 0 && array[middIndex - 1] != k) || middIndex == 0) {
				return middIndex;
			} else {
				end = middIndex - 1;
			}
		} else if (midNum > k) {
			end = middIndex - 1;
		} else if (midNum < k) {
			start = middIndex + 1;
		}
		return getFirst(array, start, end, k);

	}

	/**
	 * @description 求最后一个k
	 * @param array
	 * @param k
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getLast(int[] array, int start, int end, int k) {
		if (start > end) {
			return -1;
		}
		int length = array.length;
		int middIndex = start + (end - start) / 2;
		int midNum = array[middIndex];
		// 1、当前元素不是数组最后一个则右边相邻的元素互不相同，2、或者是数组最后一个元素
		if (midNum == k) {
			if ((middIndex < length - 1 && array[middIndex + 1] != k)
					|| middIndex == length - 1) {
				return middIndex;
			} else {

				start = middIndex + 1;
			}
		} else if (midNum > k) {
			end = middIndex - 1;
		} else if (midNum < k) {
			start = middIndex + 1;
		}

		return getLast(array, start, end, k);
	}
}