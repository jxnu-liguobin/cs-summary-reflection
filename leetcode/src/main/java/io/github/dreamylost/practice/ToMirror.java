package io.github.dreamylost.practice;

/**
 * @description 操作给定的二叉树，将其变换为源二叉树的镜像。
 * @author Mr.Li
 * 
 */
public class ToMirror {
	/**
	 * @description 使用递归
	 * @param root
	 */
	public void mirror(TreeNode root) {
		if (root == null)
			return;
		if (root.left != null || root.right != null) {
			TreeNode temp = root.left;
			root.left = root.right;
			root.right = temp;
			mirror(root.left);
			mirror(root.right);
		}
	}

}