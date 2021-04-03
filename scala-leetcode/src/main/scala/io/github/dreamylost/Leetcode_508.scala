/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
 * 508. 出现次数最多的子树元素和
 *
 * 给你一个二叉树的根结点，请你找出出现次数最多的子树元素和。一个结点的「子树元素和」定义为以该结点为根的二叉树上所有结点的元素之和（包括结点本身）。
 *
 * 你需要返回出现次数最多的子树元素和。如果有多个元素出现的次数相同，返回所有出现次数最多的子树元素和（不限顺序）。
 *
 * @see https://github.com/jxnu-liguobin
 * @author 梦境迷离
 * @since 2020-08-03
 * @version 1.0
 */
object Leetcode_508 extends App {

  val ret = findFrequentTreeSum(TreeNodeData.treeData3_7())
  ret foreach println

  /**
   * 700 ms,100.00%
   * 53.3 MB,100.00%
   *
   * @param root
   * @return
   */
  def findFrequentTreeSum(root: TreeNode): Array[Int] = {
    var maxCount = 0
    val sumCount = scala.collection.mutable.HashMap.empty[Int, Int]

    def sumTree(r: TreeNode): Int = {
      if (r == null) return 0
      val sum = sumTree(r.left) + sumTree(r.right) + r.value
      val count = if (sumCount.contains(sum)) {
        val addCount = sumCount(sum) + 1
        sumCount.update(sum, addCount)
        addCount
      } else {
        sumCount.put(sum, 1)
        1
      }
      maxCount = math.max(maxCount, count)
      sum
    }

    sumTree(root)
    sumCount.filter(_._2 == maxCount).keys.toArray

  }

}
