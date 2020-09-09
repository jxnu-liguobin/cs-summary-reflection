/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 1305. 两棵二叉搜索树中的所有元素
 *
 * 给你 root1 和 root2 这两棵二叉搜索树。请你返回一个列表，其中包含 两棵树 中的所有整数并按 升序 排序。.
 * @author liguobin@growingio.com
 * @version 1.0,2020/9/9
 */
class Leetcode_1305 {

    companion object {

        /**
         * 576 ms,100.00%
         * 38.2 MB,100.00%
         */
        fun getAllElements(root1: TreeNode?, root2: TreeNode?): List<Int> {
            if (root1 == null && root2 == null) return mutableListOf()
            fun inOrder(list: MutableList<Int>, r1: TreeNode?) {
                if (r1 == null) return
                inOrder(list, r1.left)
                list.add(r1.`val`)
                inOrder(list, r1.right)
            }

            val list1 = mutableListOf<Int>()
            val list2 = mutableListOf<Int>()
            inOrder(list1, root1)
            inOrder(list2, root2)
            // 归并排序
            var first = 0
            var second = 0
            val mergeList: MutableList<Int> = mutableListOf()
            while (first < list1.size && second < list2.size) {
                if (list1[first] <= list2[second]) {
                    mergeList.add(list1[first])
                    first++
                } else {
                    mergeList.add(list2[second])
                    second++
                }
            }
            while (first < list1.size) {
                mergeList.add(list1[first])
                first++
            }
            while (second < list2.size) {
                mergeList.add(list2[second])
                second++
            }
            return mergeList
        }
    }
}
