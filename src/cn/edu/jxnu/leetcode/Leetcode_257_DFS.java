package cn.edu.jxnu.leetcode;

import java.util.ArrayList;
import java.util.List;
/**
 * @author 梦境迷离
 * @description 输出二叉树中所有从根到叶子的路径
 * 
 *             1 
 *            / \ 
 *           2  3 
 *           \ 
 *            5 
 *           
 *           ["1->2->5", "1->3"]
 * @time 2018年4月9日
 */
public class Leetcode_257_DFS {
	
	public List<String> binaryTreePaths(TreeNode root) {
		List<String> ret = new ArrayList<String>();
		if (root == null)
			return ret;
		dfs(root, "", ret);
		return ret;
	}

	/**
	 * @author 梦境迷离
	 * @description 前序遍历
	 * @time 2018年4月9日
	 */
	private void dfs(TreeNode root, String prefix, List<String> ret) {
		if (root == null)
			return;
		if (root.left == null && root.right == null) {
			ret.add(prefix + root.value);
			return;
		}
		prefix += (root.value + "->");
		dfs(root.left, prefix, ret);
		dfs(root.right, prefix, ret);
	}
}
