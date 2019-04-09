package cn.edu.jxnu.sort;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:
 * @Package: cn.edu.jxnu.sort
 * @author: 梦境迷离
 * @date: 2018年3月28日 上午9:52:25
 */

public class BubbleSort extends Constant {

	private static long time = 0l;
	private static long time2 = 0l;

	public static void main(String[] args) throws Exception {
		printResult(new BubbleSort().sort(array2, len));// array:13474,array2:12832 相差明显
		printResult(new BubbleSort().sort2(array2, len));// array:1284,array2:1283
		System.out.println("没有优化：" + time);
		System.out.println("优化：" + time2);
	}

	/**
	 * 原始版
	 */
	@Override
	public Object[] sort(Object[] array, int len) {
		long t = System.nanoTime();
		for (int i = 0; i < array.length - 1; i++) {// 外层循环控制排序趟数
			for (int j = 0; j < array.length - 1 - i; j++) {// 内层循环控制每一趟排序多少次
				if ((int) array[j] > (int) array[j + 1]) {
					Object temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
		time = System.nanoTime() - t;
		return array;
	}

	/**
	 * 优化版
	 */
	@Override
	public Object[] sort2(Object[] array, int len) {
		long t = System.nanoTime();
		boolean flag = false;
		for (int i = 0; i < array.length; i++) {
			flag = true;
			// 思路是每次进下一趟冒泡的时候给flag设置true,如果被修改说明还有元素没有被排序，继续重复操作
			// 如果经过一趟下来，没有元素被交换【没有设置flag=false】,此时说明元素全部有序，直接退出
			for (int j = 0; j < array.length - 1 - i; j++) {
				if ((int) array[j] > (int) array[j + 1]) {
					Object temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
					flag = false;
				}
			}
			if (flag) {
				break;
			}
		}
		time2 = System.nanoTime() - t;
		return array;
	}

}
