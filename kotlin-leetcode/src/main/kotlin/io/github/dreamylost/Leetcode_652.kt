/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 652. 寻找重复的子树
 *
 * 给定一棵二叉树，返回所有重复的子树。对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。
 * 两棵树重复是指它们具有相同的结构以及相同的结点值。
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-08-22
 *  @version 1.0
 */
object Leetcode_652 {

    /**
     *
     */
    fun findDuplicateSubtrees(root: TreeNode?): List<TreeNode?> {
        val hashmap = hashMapOf<String, Int>()
        val ret = mutableListOf<TreeNode>()
        // 必须先从子节点着手才能知道重复项，且需要拼接一个分隔符区分空节点
        fun findDuplicateSubtreesHelper(root: TreeNode?): String {
            if (root == null) return ""
            val str = root.`val`.toString() + "," + findDuplicateSubtreesHelper(root.left) + "," + findDuplicateSubtreesHelper(root.right)
            if (hashmap.containsKey(str) && hashmap[str] == 1) {
                ret.add(root)
            }
            hashmap[str] = hashmap.getOrDefault(str, 0) + 1
            return str
        }
        findDuplicateSubtreesHelper(root)
        return ret
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ret = findDuplicateSubtrees(TreeNodeData.treeNode7_1())
        println(ret)
    }
}
