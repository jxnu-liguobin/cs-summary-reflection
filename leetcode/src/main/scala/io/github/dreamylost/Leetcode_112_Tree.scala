package io.github.dreamylost

/**
 * 判断路径和是否等于一个数
    *Leetcdoe : 112. Path Sum (Easy)
    *Given the below binary tree and sum = 22,
                  5
                 / \
                4   8
               /   / \
              11  13  4
             /  \      \
            7    2      1
    *return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 *
 * @author 梦境迷离
 * @time 2018年7月30日
 * @version v1.0
 */
object Leetcode_112_Tree extends App {
    //路径和定义为从 root 到 leaf 的所有节点的和
    def hasPathSum(root: TreeNode, sum: Int): Boolean = {
        if (root == null)
            return false
        if (root.left == null && root.right == null && root.value == sum)
            return true
        return hasPathSum(root.left, sum - root.value)|| hasPathSum (root.right, sum - root.value)
    }

}