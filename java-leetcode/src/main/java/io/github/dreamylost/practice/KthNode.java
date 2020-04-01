package io.github.dreamylost.practice;

/**
 * @description 给定一颗二叉搜索树，请找出其中的第k大的结点。例如， 5 / \ 3 7 /\ /\ 2 4 6 8
 *              中，按结点数值大小顺序第三个结点的值为4。
 * @author Mr.Li
 *
 */
public class KthNode {
	// 计数器
	private int index = 0;

	/**
	 * @description 二叉搜索树按照中序遍历的顺序打印出来正好就是排序好的顺序。 所以，按照中序遍历顺序找到第k个结点就是结果。
	 * @param pRoot
	 * @param k
	 * @return
	 */
	TreeNode kThNode(TreeNode pRoot, int k) {
		// 中序遍历寻找第k个
		if (pRoot != null) {
			TreeNode node = kThNode(pRoot.left, k);
			// 左子树中找到符合要求的节点返回
			if (node != null)
				return node;
			// 从最左节点开始，count+1；
			index++;
			if (index == k)
				return pRoot;
			node = kThNode(pRoot.right, k);
			// 左子树中没有找到，在右子树找到了符合要求的节点返回
			if (node != null)
				return node;
		}
		return null;
	}
}
