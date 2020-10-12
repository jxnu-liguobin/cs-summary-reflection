/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
 * 1466. 重新规划路线
 *
 * n 座城市，从 0 到 n-1 编号，其间共有 n-1 条路线。因此，要想在两座不同城市之间旅行只有唯一一条路线可供选择（路线网形成一颗树）。去年，交通运输部决定重新规划路线，以改变交通拥堵的状况。
 * 路线用 connections 表示，其中 connections[i] = [a, b] 表示从城市 a 到 b 的一条有向路线。
 * 今年，城市 0 将会举办一场大型比赛，很多游客都想前往城市 0 。
 * 请你帮助重新规划路线方向，使每个城市都可以访问城市 0 。返回需要变更方向的最小路线数。题目数据 保证 每个城市在重新规划路线方向后都能到达城市 0 。
 *  @see [[https://github.com/jxnu-liguobin]]
 *  @author 梦境迷离
 *  @since 2020-10-11
 *  @version 1.0
 */
class Leetcode_1466 {

    companion object {
        /**
         * @author 軔Θ車刃
         * 并查集
         * 492 ms,100.00%
         * 61.3 MB,100.00%
         */
        fun minReorder(n: Int, connections: Array<IntArray>): Int {
            val parentArr = Array(n + 1) { i -> i }
            var sum = 0
            var ret: Int
            fun findHelper(now: Int): Int {
                if (now != parentArr[now]) {
                    parentArr[now] = findHelper(parentArr[now])
                }
                return parentArr[now]
            }
            do {
                ret = 0
                for (i in 0 until connections.size) {
                    if (findHelper(connections[i][0]) == 0 && findHelper(connections[i][1]) != 0) {
                        // 如果起始点所连接的根节点是0但是终点的根节点不是0，则把终点的根节点变成起始点的根节点（也就是0），并且sum（修改次数）++
                        parentArr[findHelper(connections[i][1])] = findHelper(connections[i][0])
                        sum++
                        ret = 1
                    } else if (findHelper(connections[i][0]) != 0) {
                        // 如果终点的根节点就是0的话，则使起始点的根节点变为0，sum不变
                        if (findHelper(connections[i][1]) == 0) parentArr[findHelper(connections[i][0])] = findHelper(connections[i][1]) else ret = 1
                    }
                }
            } while (ret != 0)
            return sum
        }

        /**
         *@author Gogo
         * 1216 ms,100.00%
         * 106.7 MB,100.00%
         */
        fun minReorder_(n: Int, connections: Array<IntArray>): Int {
            val visit = HashSet<Int>()
            val graphMap = HashMap<Int, MutableList<Int>>() // 无向图
            val graphMap2 = HashMap<Int, HashSet<Int>>() // 有向图
            (0 until n).forEach {
                graphMap[it] = ArrayList()
                graphMap2[it] = HashSet()
            }
            var ret = 0
            fun reorder(cur: Int) {
                visit.add(cur)
                for (next in graphMap[cur]!!) {
                    if (!visit.contains(next)) {
                        if (graphMap2[cur]!!.contains(next)) {
                            ret++
                        }
                        reorder(next)
                    }
                }
            }

            for (net in connections) {
                graphMap[net[1]]!!.add(net[0])
                graphMap[net[0]]!!.add(net[1])
                graphMap2[net[0]]!!.add(net[1])
            }
            // 初始化有向图和无向图
            reorder(0)
            return ret
        }
    }
}
