package cn.edu.jxnu.practice;

/**
 * @description 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
 * @author Mr.Li
 *
 */
public class PrintTreeByLevel {

	/**
	 * 使用游标记录当前层的上层
	 * 
	 * @param root
	 * @return
	 */
	ArrayList<ArrayList<Integer>> print(TreeNode root) {
		ArrayList<Integer> sortedList = new ArrayList<>();
		ArrayList<TreeNode> list = new ArrayList<>();
		ArrayList<ArrayList<Integer>> res = new ArrayList<>();
		if (root == null) {
			// res.add(sortedList);
			return res;
		}
		list.add(root);
		// 当前访问的节点
		int current = 0;
		// 标记某层最后一个节点的下一个位置
		int last = 1;
		while (current < list.size()) {
			// 每次重新获取新的一行的last位置
			last = list.size();
			// 当last==current 表示访问该层结束了
			sortedList = new ArrayList<>();
			while (current < last) {
				sortedList.add(list.get(current).val);
				if (list.get(current).left != null) {
					// 左节点不为空则压入
					list.add(list.get(current).left);
				}
				if (list.get(current).right != null) {
					// 右节点不为空则压入
					list.add(list.get(current).right);
				}
				// 该层节点左到右移动：当前访问节点
				current++;
			}
			res.add(sortedList);
			// 换行 因为泛型无法打印
		}
		return res;

	}
}