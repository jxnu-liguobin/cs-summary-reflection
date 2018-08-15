package cn.edu.jxnu.practice;

/**
 * <p>
 * Title: TreeDemo6.java
 * </p>
 * <p>
 * Description:二叉树中节点的最大距离
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * School: jxnu
 * </p>
 * 
 * @author Mr.Li
 * @date 2018-2-7
 * @version 1.0
 */

public class GetMaximumDistance {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		TreeNode node1 = new TreeNode(6);
		TreeNode node2 = new TreeNode(7);
		root.left = node1;
		root.right = node2;
		TreeNode node3 = new TreeNode(8);
		TreeNode node4 = new TreeNode(9);
		node1.left = node3;
		node1.right = node4;
		TreeNode node5 = new TreeNode(10);
		node2.left = node5;
		System.out.println("tree:"
				+ PringTreeFromTopToBottom.PrintFromTopToBottom(root));
		RESULT result = getMaximumDistance(root);
		System.out.println("max depth-->" + result.maxDepth);
		System.out.println("max distance-->" + result.maxDistance);

	}

	/**
	 * @description result = leftMaxDistance+rightMaxDistance+2
	 * @param root
	 * @return
	 */
	public static RESULT getMaximumDistance(TreeNode root) {
		if (root == null) {
			RESULT empty = new RESULT(0, -1);
			return empty;
		}
		RESULT lhs = getMaximumDistance(root.left);// left tree
		RESULT rhs = getMaximumDistance(root.right);// right tree
		RESULT result = new RESULT();
		result.maxDepth = Math.max(lhs.maxDepth + 1, rhs.maxDepth + 1);
		result.maxDistance = Math.max(
				Math.max(lhs.maxDistance, rhs.maxDistance), lhs.maxDepth + rhs.maxDepth + 2);
		return result;
	}
}

class RESULT {
	int maxDistance;
	int maxDepth;

	public RESULT(int nMaxDistance, int nMaxDepth) {
		super();
		this.maxDistance = nMaxDistance;
		this.maxDepth = nMaxDepth;
	}

	public RESULT() {
	}

	public int getnMaxDistance() {
		return maxDistance;
	}

	public void setnMaxDistance(int nMaxDistance) {
		this.maxDistance = nMaxDistance;
	}

	public int getnMaxDepth() {
		return maxDepth;
	}

	public void setnMaxDepth(int nMaxDepth) {
		this.maxDepth = nMaxDepth;
	}
}
