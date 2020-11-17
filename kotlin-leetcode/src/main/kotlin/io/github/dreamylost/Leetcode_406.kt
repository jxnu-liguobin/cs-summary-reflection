/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import java.util.ArrayList
import java.util.Comparator

/**
 * 406. 根据身高重建队列
 * 假设有打乱顺序的一群人站成一个队列。 每个人由一个整数对(h, k)表示，其中h是这个人的身高，k是排在这个人前面且身高大于或等于h的人数。
 * 编写一个算法来重建这个队列。
 * @author 梦境迷离
 * @version 1.0,2020/11/16
 */
class Leetcode_406 {

    companion object {
        /**
         * 先对输入数组排序，按照h降序，k升序。
         * 324 ms,66.67%
         * 44.5 MB,16.67%
         */
        fun reconstructQueue(people: Array<IntArray>): Array<IntArray> {
            if (people.isEmpty() || people[0].isEmpty())
                return emptyArray()
            people.sortWith(
                Comparator { o1, o2 ->
                    if (o1[0] == o2[0]) o1[1] - o2[1] else o2[0] - o1[0]
                }
            )

            val ans: MutableList<IntArray> = ArrayList()
            for (person in people) {
                // 身高低的插入到身高高的前面，不会影响身高高的k值
                ans.add(person[1], person)
            }
            return ans.toTypedArray()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val ret = reconstructQueue(
                arrayOf(
                    arrayOf(5, 0).toIntArray(),
                    arrayOf(7, 0).toIntArray(), arrayOf(5, 2).toIntArray(),
                    arrayOf(6, 1).toIntArray(), arrayOf(4, 4).toIntArray(),
                    arrayOf(7, 1).toIntArray()
                )
            )
            ret.forEach {
                it.forEach {
                    print(it)
                }
                println()
            }
        }
    }
}
