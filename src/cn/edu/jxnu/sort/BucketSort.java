package cn.edu.jxnu.sort;

import java.util.Collections;

/**
 * @author 梦境迷离
 * @description 桶排序
 * 
 *              顺序从待排数组中取出下一个数字,此时2被取出,将其放入2号桶,是几就放几号桶
 * @time 2018年4月8日
 */
public class BucketSort extends Constant {

	public static void main(String[] args) throws Exception {
		Constant.printResult(new BucketSort().sort(Constant.array, Constant.len));

	}

	@Override
	public Object[] sort(Object[] array, int len) {

		return bucketSort(array);
	}

	/**
	 * @author 梦境迷离
	 * @return
	 * @description 1）待排序列的值处于一个可枚举的范围内 2）待排序列所在可枚举范围不应太大，不然开销会很大。
	 * 
	 *              桶排序的基本思想是： 把数组 arr 划分为n个大小相同子区间（桶），每个子区间各自排序，最后合并 。
	 *              计数排序是桶排序的一种特殊情况，可以把计数排序当成每个桶里只有一个元素的情况。
	 *              1.找出待排序数组中的最大值max、最小值min
	 * 
	 *              2.我们使用 动态数组ArrayList 作为桶，桶里放的元素也用 ArrayList
	 *              存储。桶的数量为(max-min)/arr.length+1
	 * 
	 *              3.遍历数组 arr，计算每个元素 arr[i] 放的桶
	 * 
	 *              4.每个桶各自排序
	 * 
	 *              5.遍历桶数组，把排序好的元素放进输出数组
	 * 
	 * @time 2018年4月8日
	 */
	public static Object[] bucketSort(Object[] arr) {

		/**
		 * 得到最值
		 */
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, (int) arr[i]);
			min = Math.min(min, (int) arr[i]);
		}
		// 桶数
		int bucketNum = (max - min) / arr.length + 1;
		ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
		for (int i = 0; i < bucketNum; i++) {
			bucketArr.add(new ArrayList<Integer>());
		}
		// 将每个元素放入桶
		for (int i = 0; i < arr.length; i++) {
			int num = ((Integer) arr[i] - (Integer) min) / (arr.length);
			bucketArr.get(num).add((Integer) arr[i]);
		}
		// 对每个桶进行排序
		for (int i = 0; i < bucketArr.size(); i++) {
			Collections.sort(bucketArr.get(i));
		}
		// System.out.println(bucketArr.toString());
		int i = 0;
		for (ArrayList<Integer> arrayList : bucketArr) {
			for (Integer integer : arrayList) {
				arr[i++] = integer;
				if (i == arr.length - 1)
					break;
			}
		}
		System.out.println("使用toString()方法：" + bucketArr.toString());
		return arr;

	}

}
