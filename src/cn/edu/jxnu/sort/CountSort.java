package cn.edu.jxnu.sort;

/**
 * @author 梦境迷离
 * @description 计数排序(基数)
 * 
 *              计数排序 计数排序适用数据范围 计数排序需要占用大量空间，它仅适用于数据比较集中的情况。比如
 *              [0~100]，[10000~19999] 这样的数据。
 *              最佳情况：T(n) = O(n+k) 最差情况：T(n) = O(n+k) 平均情况：T(n) = O(n+k)
 * 
 * @time 2018年4月8日
 */
public class CountSort extends Constant {

	private static long time = 0l;

	public static void main(String[] args) throws Exception {
		Constant.printResult(new CountSort().sort(Constant.array2, Constant.len));
		System.out.println("耗费时间："+time);//array1：18606,array2：18927 几乎相同

	}

	@Override
	public Object[] sort(Object[] array, int len) {

		return countSort(array);
	}

	/**
	 * 计数排序
	 * 
	 * 1.得到最大值与最小值，并计算最大值与最小值之差，制造桶，此时桶的下标i并非真正的元素，而是元素与min之差 
	 * 2.统计每个元素出现的次数，并记录在桶中
	 * 3.对桶中的元素进行取出，注意有重复的元素，而此时元素是i与min之和
	 * 
	 */
	public static Object[] countSort(Object[] arr) {
		long t = System.nanoTime();

		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		// 找出数组中的最大最小值
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, (int) arr[i]);
			min = Math.min(min, (int) arr[i]);
		}
		// 比max大1
		int help[] = new int[max - min + 1];
		// 找出每个数字出现的次数
		for (int i = 0; i < arr.length; i++) {
			int mapPos = (int) arr[i] - min;
			help[mapPos]++;
		}
		int index = 0;
		for (int i = 0; i < help.length; i++) {
			// 因为可能有重复的数据，所以需要循环
			while (help[i]-- > 0) {
				arr[index++] = i + min;
			}
		}

		time = System.nanoTime() - t;
		return arr;
	}
}
