package io.github.dreamylost

/**
 * 两节点的最长路径
 * 543. Diameter of Binary Tree (Easy)
 * Input:
 *
 * 1
 * / \
 * 2  3
 * / \
 * 4   5
 *
 * Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
 * @author 梦境迷离
 * @time 2018年7月28日
 * @version v1.0
 */
object Leetcode_543_Tree extends App {

    private var max = 0

    //编程之美解法参见GetMaximumDistance
    def diameterOfBinaryTree(root: TreeNode): Int = {
        depth(root)
        max
    }

    private def depth(root: TreeNode): Int = {
        if (root == null)
            return 0
        val leftDepth = depth(root.left)
        val rightDepth = depth(root.right)
        max = math.max(max, leftDepth + rightDepth)
        return math.max(leftDepth, rightDepth) + 1
    }

}