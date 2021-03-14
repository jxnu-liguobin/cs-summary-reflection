/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 654. 最大二叉树
 *
 * 给定一个不含重复元素的整数数组。一个以此数组构建的最大二叉树定义如下：
 * 二叉树的根是数组中的最大元素。左子树是通过数组中最大值左边部分构造出的最大二叉树。右子树是通过数组中最大值右边部分构造出的最大二叉树。
 * 通过给定的数组构建最大二叉树，并且输出这个树的根节点。
 *
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-08-23
 *  @version 1.0
 */
class Leetcode_654 {
    companion object {

        /**
         * 352 ms,28.57%
         * 40 MB,100.00%
         */
        fun constructMaximumBinaryTree(nums: IntArray): TreeNode? {
            if (nums.isEmpty()) {
                return null
            }
            val max = nums.maxOrNull()
            val index = max!!.let { nums.indexOf(max) }
            val root = TreeNode(max)
            root.left = constructMaximumBinaryTree(nums.sliceArray(0 until index))
            root.right = constructMaximumBinaryTree(nums.sliceArray(index + 1 until nums.size))
            return root
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = constructMaximumBinaryTree(arrayOf(3, 2, 1, 6, 0, 5).toIntArray())
            println(ret)
        }
    }
}
