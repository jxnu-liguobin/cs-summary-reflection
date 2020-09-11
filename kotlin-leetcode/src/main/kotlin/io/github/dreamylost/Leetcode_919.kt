/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.util.*
import kotlin.collections.ArrayList

/**
 * 919. 完全二叉树插入器
 *
 * 完全二叉树是每一层（除最后一层外）都是完全填充（即，节点数达到最大）的，并且所有的节点都尽可能地集中在左侧。
 * 设计一个用完全二叉树初始化的数据结构 CBTInserter，它支持以下几种操作：
 *
 * CBTInserter(TreeNode root) 使用头节点为 root 的给定树初始化该数据结构；
 * CBTInserter.insert(int v)  向树中插入一个新节点，节点类型为 TreeNode，值为 v 。使树保持完全二叉树的状态，并返回插入的新节点的父节点的值；
 * CBTInserter.get_root() 将返回树的头节点。
 *
 * @author liguobin@growingio.com
 * @version 1.0,2020/9/11
 */
class Leetcode_919 {

    companion object {

        /**
         *
         * 284 ms,100.00%
         * 38.1 MB,100.00%
         */
        class CBTInserter(root: TreeNode?) {

            private val r = root
            private val queue = LinkedList<TreeNode>()
            private val res = ArrayList<TreeNode>()

            init {
                if (r != null) {
                    queue.add(r)
                    while (!queue.isEmpty()) {
                        val size = queue.size
                        for (i in 0 until size) {
                            val node = queue.pollFirst()
                            res.add(node!!)
                            if (node.left != null) queue.addLast(node.left)
                            if (node.right != null) queue.addLast(node.right)
                        }
                    }
                }
            }

            fun insert(v: Int): Int {
                val count = res.size
                val root = TreeNode(v)
                // 最后一个父节点：(n-1)/2，因为最后一个节点编号为n，问题转化为求编号n的父节点，即总节点数/2的向下取整值
                val parent: TreeNode = res[(count - 1) / 2]
                // 基数，左空，偶数右空
                if (count.and(1) == 1) {
                    parent.left = root
                } else {
                    parent.right = root
                }
                res.add(root)
                return parent.`val`
            }

            fun get_root(): TreeNode? {
                return r
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val obj = CBTInserter(TreeNodeData.treeNode_5_1())
            val param_1 = obj.insert(1)
            println(param_1)
            val param_2 = obj.get_root()
            println(param_2)
        }
    }
}
