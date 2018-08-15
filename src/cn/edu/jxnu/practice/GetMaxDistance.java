package cn.edu.jxnu.practice;

/**
 * @description 输入一棵二叉树，求该树的深度。 从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径， 最长路径的长度为树的深度
 * @author Mr.Li
 * 
 */
public class GetMaxDistance {

	/**
	 * @description 封装
	 * @author Mr.Li
	 * 
	 */
	static class Result {
		int maxDepth;

		public Result(int nMaxDepth) {
			super();
			this.maxDepth = nMaxDepth;
		}

		public Result() {
		}
	}

	/**
	 * @description 求最大深度递归函数 参考编程之美求：最大的两个节点的距离
	 * @param root
	 * @return
	 */
	public static Result getMaximumDistance(TreeNode root) {
		if (root == null) {
			Result empty = new Result(0);
			return empty;
		}
		Result lhs = getMaximumDistance(root.left);// left tree
		Result rhs = getMaximumDistance(root.right);// right tree
		Result result = new Result();
		result.maxDepth = Math.max(lhs.maxDepth + 1, rhs.maxDepth + 1);
		return result;
	}

	/**
	 * @description 求最大深度
	 * @param root
	 * @return
	 */
	public int TreeDepth(TreeNode root) {
		if (root == null)
			return 0;
		Result s = getMaximumDistance(root);
		return s.maxDepth;
	}
}