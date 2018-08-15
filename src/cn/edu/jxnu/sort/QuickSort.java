package cn.edu.jxnu.sort;

/**
 * 快速排序 ，分治，递归 对于数组个数<=20，快排不如插入排序 枢纽元选取，一般选第一个，其实是最坏的 安全的方法：随即数生成，随即选取
 * 或者三数中值分割法 减少大约5%时间【一般选取左中右三个数】
 * 
 * @time 2018年3月24日16:34:32
 */
public class QuickSort extends Constant {
	public static void main(String[] args) throws Exception {
		Constant.printResult(new QuickSort().sort(Constant.array, Constant.len));
	}

	@Override
	public Object[] sort(Object[] array, int len) {

		quick(array, 0, len - 1);
		return array;
	}

	public Object[] quick(Object[] list, int low, int high) {
		if (low < high) {
			int middle = QuickSort.quickSortBy3Integers(list, low, high);// 三数中值分割法
			// int middle = quickSort(list, low, high); // 将list数组进行一分为二
			quick(list, low, middle - 1); // 对低字表进行递归排序
			quick(list, middle + 1, high); // 对高字表进行递归排序
			// 快速选择排序，这里需要if判断一下，少一次递归
		}
		return list;
	}

	// 一般方法
	public int quickSort(Object[] list, int low, int high) {
		int tmp = (int) list[low]; // 数组的第一个作为中轴
		while (low < high) {
			while (low < high && (int) list[high] >= tmp) {
				high--;
			}
			list[low] = list[high]; // 比中轴小的记录移到低端
			while (low < high && (int) list[low] <= tmp) {
				low++;
			}
			list[high] = list[low]; // 比中轴大的记录移到高端
		}
		list[low] = tmp; // 中轴记录到尾
		return low;
	}

	// 使用三数中值
	public static int quickSortBy3Integers(Object[] array, int low, int high) {
		// 三数取中
		int mid = low + (high - low) / 2;
		if ((int) array[mid] > (int) array[high]) {
			swap(array[mid], array[high]);
		}
		if ((int) array[low] > (int) array[high]) {
			swap(array[low], array[high]);
		}
		if ((int) array[mid] > (int) array[low]) {
			swap(array[mid], array[low]);
		}
		int key = (int) array[low];

		while (low < high) {
			while ((int) array[high] >= key && high > low) {
				high--;
			}
			array[low] = array[high];
			while ((int) array[low] <= key && high > low) {
				low++;
			}
			array[high] = array[low];
		}
		array[high] = key;
		return high;
	}

	private static void swap(Object a, Object b) {
		Object temp = a;
		a = b;
		b = temp;
	}
}
