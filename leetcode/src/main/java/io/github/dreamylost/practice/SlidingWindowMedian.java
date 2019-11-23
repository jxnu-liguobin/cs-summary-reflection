package io.github.dreamylost.practice;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * 给定一个包含 n 个整数的数组，和一个大小为 k
 * 的滑动窗口,从左到右在数组中滑动这个窗口，找到数组中每个窗口内的中位数。(如果数组个数是偶数，则在该窗口排序数字后，返回第 N/2 个数字。)
 * 
 * @author 梦境迷离.
 * @time 2018年8月2日
 * @version v1.0
 */
public class SlidingWindowMedian {
	public ArrayList<Integer> medianSlidingWindow(int[] nums, int k) {
		int n = nums.length;
		TreeSet<Node> minheap = new TreeSet<Node>();
		TreeSet<Node> maxheap = new TreeSet<Node>();
		ArrayList<Integer> result = new ArrayList<Integer>();

		if (k == 0)
			return result;

		int half = (k + 1) / 2;
		for (int i = 0; i < k - 1; i++) {
			add(minheap, maxheap, half, new Node(i, nums[i]));
		}
		for (int i = k - 1; i < n; i++) {
			add(minheap, maxheap, half, new Node(i, nums[i]));
			result.add(minheap.last().val);
			remove(minheap, maxheap, new Node(i - k + 1, nums[i - k + 1]));
		}
		return result;
	}

	void add(TreeSet<Node> minheap, TreeSet<Node> maxheap, int size, Node node) {
		if (minheap.size() < size) {
			minheap.add(node);
		} else {
			maxheap.add(node);
		}
		if (minheap.size() == size) {
			if (maxheap.size() > 0 && minheap.last().val > maxheap.first().val) {
				Node s = minheap.last();
				Node b = maxheap.first();
				minheap.remove(s);
				maxheap.remove(b);
				minheap.add(b);
				maxheap.add(s);
			}
		}
	}

	void remove(TreeSet<Node> minheap, TreeSet<Node> maxheap, Node node) {
		if (minheap.contains(node)) {
			minheap.remove(node);
		} else {
			maxheap.remove(node);
		}
	}
}

class Node implements Comparable<Node> {
	int id;
	int val;

	Node(int id, int val) {
		this.id = id;
		this.val = val;
	}

	public int compareTo(Node other) {
		Node a = (Node) other;
		if (this.val == a.val) {
			return this.id - a.id;
		}
		return this.val - a.val;
	}
}
