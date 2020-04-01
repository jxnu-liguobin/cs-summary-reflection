package io.github.dreamylost.practice;

/**
 * @description 请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
 * @author Mr.Li
 *
 */
public class IsSymmetrical {
	/**
	 * 
	 * 先求一个二叉树的镜像
	 */
	boolean isSymmetrical(TreeNode pRoot) {
		TreeNode tn = Mirror(pRoot);
		return isSymmetrical(pRoot, tn);
	}

	boolean isSymmetrical(TreeNode pRoot, TreeNode qRoot) {
		if (pRoot == null && qRoot == null)
			return true;
		else if (pRoot == null || qRoot == null) {
			return false;
		}
		if (pRoot.val == qRoot.val) {
			return isSymmetrical(pRoot.left, qRoot.left) && isSymmetrical(pRoot.right, qRoot.right);
		}
		return false;
	}

	private TreeNode Mirror(TreeNode pRoot) {
		if (pRoot == null) {
			return null;
		}
		TreeNode root = new TreeNode(pRoot.val);
		root.right = Mirror(pRoot.left);
		root.left = Mirror(pRoot.right);
		return root;
	}

	public TreeNode Mirror2(TreeNode root) {
		if (root == null)
			return null;
		if (root.left != null || root.right != null) {
			TreeNode temp = root.left;
			root.left = root.right;
			root.right = temp;
			Mirror(root.left);
			Mirror(root.right);
		}
		return root;
	}

	/********************** 直接判断左右孩子 ***************************/
	boolean isSymmetrical1(TreeNode pRoot) {
		if (pRoot == null)
			return true;

		return isSymmetrical2(pRoot.left, pRoot.right);
	}

	boolean isSymmetrical2(TreeNode t1, TreeNode t2) {
		if (t1 == null && t2 == null)
			return true;

		if (t1 != null && t2 != null)
			return t1.val == t2.val && isSymmetrical2(t1.left, t2.right) && isSymmetrical2(t1.right, t2.left);

		return false;
	}
}