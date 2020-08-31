/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 889. 根据前序和后序遍历构造二叉树
 *
 * 返回与给定的前序和后序遍历匹配的任何二叉树。
 * pre 和 post 遍历中的值是不同的正整数。
 * @author 梦境迷离
 * @version 1.0,2020/8/31
 */
class Leetcode_889 {
    companion object {

        /**
         * 与Leetcode_JZ_7类似，但前后续构建的答案不唯一
         * 252 ms,75.00%
         * 39.6 MB,100.00%
         */
        fun constructFromPrePost(pre: IntArray, post: IntArray): TreeNode? {
            if (pre.isEmpty()) return null
            val root = TreeNode(pre[0])
            if (pre.size == 1) return root
            var index = 0
            for (i in pre.indices) {
                if (pre[1] == post[i]) {
                    index = i + 1
                    break
                }
            }
            // 直接拷贝，也可以使用下标，节省空间
            root.left = constructFromPrePost(pre.sliceArray(1 until index + 1), post.sliceArray(0 until index))
            root.right = constructFromPrePost(pre.sliceArray(index + 1 until pre.size), post.sliceArray(index until pre.size - 1))
            return root
        }

        @JvmStatic
        fun main(args: Array<String>) {
        }
    }
}
