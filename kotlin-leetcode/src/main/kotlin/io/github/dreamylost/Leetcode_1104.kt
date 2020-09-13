/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.lang.Math.pow

/**
 * 1104. 二叉树寻路
 *
 * 在一棵无限的二叉树上，每个节点都有两个子节点，树中的节点 逐行 依次按 “之” 字形进行标记。
 * 如下图所示，在奇数行（即，第一行、第三行、第五行……）中，按从左到右的顺序进行标记；
 * 而偶数行（即，第二行、第四行、第六行……）中，按从右到左的顺序进行标记。
 *
 * 给你树上某一个节点的标号 label，请你返回从根节点到该标号为 label 节点的路径，该路径是由途经的节点标号所组成的。
 *
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-09-13
 *  @version 1.0
 */
class Leetcode_1104 {
    companion object {

        /**
         * label/2 的对称节点
         * 192 ms,50.00%
         * 33 MB,100.00%
         */
        fun pathInZigZagTree(label: Int): List<Int> {
            if (label == 1) return mutableListOf(1)
            var laber = label
            val ret = mutableListOf<Int>()
            var level = kotlin.math.log2(laber.toFloat()).toInt()
            while (laber > 0) {
                ret.add(laber)
                laber /= 2
                // 第i层：[2^(i-1), 2^i -1]
                val low = pow(2.toDouble(), (level - 1).toDouble()) // 4
                val high = pow(2.toDouble(), level.toDouble()) - 1 // 7
                laber = (high + low - laber).toInt() // 计算对称点。比如 4 的对称点是 7，7 的对称点是 4
                level--
            }
            ret.reverse()
            return ret
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = pathInZigZagTree(14)
            println(ret)
        }
    }
}
