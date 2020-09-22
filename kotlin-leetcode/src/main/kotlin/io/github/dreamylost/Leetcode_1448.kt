/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 1448. 统计二叉树中好节点的数目
 *
 * 给你一棵根为 root 的二叉树，请你返回二叉树中好节点的数目。
 * 「好节点」X 定义为：从根到该节点 X 所经过的节点中，没有任何节点的值大于 X 的值。
 * @author 梦境迷离
 * @version 1.0,2020/9/22
 */
class Leetcode_1448 {

    companion object {

        /**
         * 暴力可用Leetcode_JZ_34的方法记录所有路径再filter
         * 456 ms,50.00%
         * 44 MB,83.33%
         */
        fun goodNodes(root: TreeNode?): Int {
            var ret = 0
            fun good(r: TreeNode?, max: Int) {
                if (r == null) return
                var maxTemp = max
                if (r.`val` >= maxTemp) {
                    ret++
                    maxTemp = r.`val`
                }
                good(r.left, maxTemp)
                good(r.right, maxTemp)
            }

            good(root, root?.`val` ?: Int.MIN_VALUE)
            return ret
        }

        /**
         * 递归时判断
         * 执行用时：
         * 504 ms,37.50%
         * 42.4 MB,100.00%
         */
        fun goodNodes_(root: TreeNode?): Int {
            if (root == null) return 0
            var ret = 0

            fun helper(root: TreeNode?, paths: MutableList<Int>): Int {
                if (root == null) return -1
                paths.add(root.`val`)
                var lessThan = true
                for (i in 0 until paths.size) {
                    if (paths[i] > root.`val`) {
                        lessThan = false
                        break
                    }
                }
                if (lessThan) {
                    ret++
                }
                helper(root.left, paths)
                helper(root.right, paths)
                return paths.removeAt(paths.size - 1)
            }

            helper(root, ArrayList())
            return ret
        }
    }
}
