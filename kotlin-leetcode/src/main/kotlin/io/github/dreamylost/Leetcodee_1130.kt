/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * 1130. 叶值的最小代价生成树
 * 给你一个正整数数组 arr，考虑所有满足以下条件的二叉树：
 * 1.每个节点都有 0 个或是 2 个子节点。
 * 2.数组 arr 中的值与树的中序遍历中每个叶节点的值一一对应。（知识回顾：如果一个节点有 0 个子节点，那么该节点为叶节点。）
 * 3.每个非叶节点的值等于其左子树和右子树中叶节点的最大值的乘积。
 * 在所有这样的二叉树中，返回每个非叶节点的值的最小可能总和。这个和的值是一个 32 位整数。
 *
 * @author 梦境迷离
 * @version 1.0,2020/9/29
 */
class Leetcodee_1130 {
    companion object {

        /**
         * 200 ms,50.00%
         * 33.7 MB,100.00%
         */
        fun mctFromLeafValues(arr: IntArray): Int {
            if (arr.isEmpty()) return 0
            val len = arr.size
            val maxValues = Array(len) { Array(len) { 0 } }
            val dp = Array(len) { Array(len) { 0 } }
            // 计算i,j范围的最大值
            for (i in 0 until len) {
                var m = arr[i]
                for (j in i until len) {
                    m = max(m, arr[j])
                    maxValues[i][j] = m
                }
            }
            // For each possible way to partition the subarray i <= k < j, the answer is max(arr[i]..arr[k]) * max(arr[k+1]..arr[j]) + dp(i, k) + dp(k+1, j).
            for (j in 0 until len) {
                for (i in j downTo 0) { // k是i到j之间的中间某个值,i<=k<j
                    var minValue = Int.MAX_VALUE
                    var k = i
                    while (k + 1 <= j) {
                        minValue = min(minValue, dp[i][k] + dp[k + 1][j] + maxValues[i][k] * maxValues[k + 1][j])
                        dp[i][j] = minValue
                        k++
                    }
                }
            }

            return dp[0][len - 1]
        }

        /**
         * 单调递减栈!! https://leetcode-cn.com/problems/minimum-cost-tree-from-leaf-values/solution/wei-shi-yao-dan-diao-di-jian-zhan-de-suan-fa-ke-xi/
         * 180 ms,100.00%
         * 31.8 MB,100.00%
         * 首先，想让 mct 值最小，那么值较小的叶子节点就要尽量放到底部，值较大的叶子节点要尽量放到靠上的部分。
         * 因为越是底部的叶子节点，被用来做乘法的次数越多。这就决定了我们有必要去寻找一个极小值。
         * 通过维护一个单调递减栈就可以找到一个极小值，因为既然是单调递减栈，左侧节点一定大于栈顶节点，而当前节点（右侧）也大于栈顶节点（因为当前节点小于栈顶的话，就被直接入栈了）。
         */
        fun mctFromLeafValues_(arr: IntArray): Int {
            val st: Stack<Int> = Stack()
            st.push(Int.MAX_VALUE)
            var mct = 0
            for (i in arr.indices) {
                while (arr[i] >= st.peek()) {
                    mct += st.pop() * min(st.peek(), arr[i])
                }
                st.push(arr[i])
            }
            while (st.size > 2) {
                mct += st.pop() * st.peek()
            }
            return mct
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = mctFromLeafValues(arrayOf(6, 2, 4).toIntArray())
            println(ret)
        }
    }
}
