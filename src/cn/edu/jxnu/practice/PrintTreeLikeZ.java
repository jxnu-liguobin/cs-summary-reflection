package cn.edu.jxnu.practice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @description 请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印，
 *              其他行以此类推。
 * @author Mr.Li
 *
 */
public class PrintTreeLikeZ {
	public ArrayList<ArrayList<Integer>> print(TreeNode pRoot) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<>();
		if (pRoot == null)
			return result;
		ArrayList<Integer> list = new ArrayList<>();
		LinkedList<TreeNode> queue = new LinkedList<>();
		queue.addLast(null);// 层分隔符
		queue.addLast(pRoot);
		boolean leftToRight = true;
		while (queue.size() != 1) {
			TreeNode node = queue.removeFirst();
			if (node == null) {// 到达层分隔符
				Iterator<TreeNode> iter = null;
				if (leftToRight) {
					iter = queue.iterator();// 从前往后遍历
				} else {
					iter = queue.descendingIterator();// 从后往前遍历
				}
				leftToRight = !leftToRight;
				while (iter.hasNext()) {
					TreeNode temp = (TreeNode) iter.next();
					list.add(temp.val);
				}
				result.add(new ArrayList<Integer>(list));
				list.clear();//清空
				queue.addLast(null);// 添加层分隔符
				continue;// 一定要continue
			}
			if (node.left != null) {
				queue.addLast(node.left);
			}
			if (node.right != null) {
				queue.addLast(node.right);
			}
		}
		return result;
	}
}