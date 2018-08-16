package cn.edu.jxnu.practice;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @description 输入n个整数，找出其中最小的K个数。 例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
 * @author Mr.Li
 * 
 */
public class GetLeastNumbers {

	public static void main(String[] args) {
		int[] input = { 4, 5, 1, 6, 2, 7, 2, 8 };
		ArrayList<Integer> list = new GetLeastNumbers().GetLeastNumbers_Solution(
				input, 2);
		 for (Integer integer : list) {
		 System.out.println(integer);
		 }
	}

	public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
		ArrayList<Integer> list = new ArrayList<>();
		if (input == null || input.length == 0)
			return list;
		if (input.length < k || k == 0)
			return list;
		int[] temp = getyMinAndMax(input);
		double vMin = temp[0];
		double vMax = temp[1];
		double vMind = 0;
		// 得到区间 整数间值为1
		while (vMax - vMin > 0.5) {
			vMind = vMin + (vMax - vMin) * 0.5;
			// length个数中寻找最小的K个数->即存在N，有k-1个数比N小->剩余length-k+1比N大
			// 即寻找第length-k+1个数
			if (getMinest(input, vMind) >= input.length - k + 1) {
				vMin = vMind;
			} else {
				vMax = vMind;
			}
		}
		// 得到值
		int result = 0;
		for (int i = 0; i < input.length; i++) {
			if (input[i] >= vMin && input[i] <= vMax) {
				result = input[i];
			}
		}
		for (int i = 0; i < input.length; i++) {
			if (input[i] <= result && !list.contains(input[i])) {
				list.add(input[i]);
			}
		}
		return list;
	}

	/**
	 * @description 获取数组最值
	 */
	private int[] getyMinAndMax(int[] input) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < input.length; j++) {
			if (input[j] > max) {
				max = input[j];
			}
			if (input[j] < min) {
				min = input[j];
			}
		}
		return new int[] { min, max };
	}

	/**
	 * @description 大于n的个数
	 * @param input
	 * @param vMind
	 * @return
	 */
	private int getMinest(int[] input, double vMind) {
		int cout = 0;
		for (int j = 0; j < input.length; j++) {
			if (input[j] >= vMind) {
				cout++;
			}
		}
		return cout;
	}

	/***************************************************************************/
	/**
	 * @description 优先级队列
	 * @param input
	 * @param k
	 * @return
	 */
	@SuppressWarnings("unused")
	public ArrayList<Integer> GetLeastNumbers2_Solution(int[] input, int k) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int length = input.length;
		if (k > length || k == 0) {
			return result;
		}
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k,new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						return o2.compareTo(o1);
					}});
		for (int i = 0; i < length; i++) {
			if (maxHeap.size() != k) {
				maxHeap.offer(input[i]);
			} else if (maxHeap.peek() > input[i]) {
				Integer temp = maxHeap.poll();
				temp = null;
				maxHeap.offer(input[i]);
			}

		}
		for (Integer integer : maxHeap) {
			result.add(integer);
		}
		return result;

	}
}