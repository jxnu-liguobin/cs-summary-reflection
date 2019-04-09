package cn.edu.jxnu.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * @description 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}
 *              及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
 *              针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个： {[2,3,4],2,6,2,5,1}，
 *              {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}，
 *              {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
 * @author Mr.Li
 *
 */
public class MaxInWindowSize {

	/**
	 *	滑动窗口应当是队列，但为了得到滑动窗口的最大值，队列序可以从两端删除元素，因此使用双端队列。
	 *     对新来的元素k，将其与双端队列中的元素相比较
	 *     1）前面比k小的，直接移出队列（因为不再可能成为后面滑动窗口的最大值了!）,
	 *     2）前面比k大的X，比较两者下标，判断X是否已不在窗口之内，不在了，直接移出队列
	 *     队列的第一个元素是滑动窗口中的最大值
	 */
	public ArrayList<Integer> maxInWindows(int[] num, int size) {
		ArrayList<Integer> list = new ArrayList<>();
		if (size == 0)
			return list;
		int start = 0;
		 // 用来保存可能是滑动窗口最大值的数字的下标
		ArrayDeque<Integer> index = new ArrayDeque<>();
		for (int i = 0; i < num.length; i++) {
			start = i - size + 1;
			if (index.isEmpty())
				index.add(i);
			// 如果队列的头部元素已经从滑动窗口里滑出，滑出的数字需要从队列的头部删除
			else if (start > index.peekFirst())
				index.pollFirst();
			//数组：{2,3,4,2,6,2,5,1}
			// 如果已有数字小于待存入的数据， 这些数字已经不可能是滑动窗口的最大值
            // 因此它们将会依次地从队尾删除
			while ((!index.isEmpty()) && num[index.peekLast()] <= num[i])
				index.pollLast();
			index.add(i);
			if (start >= 0)
				list.add(num[index.peekFirst()]);
		}
		return list;
	}
	
}