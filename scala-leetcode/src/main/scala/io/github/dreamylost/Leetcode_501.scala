/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

import scala.collection.mutable

/**
  * 501. 二叉搜索树中的众数
  *
  * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-06-07
  * @version v1.0
  */
object Leetcode_501 extends App {

  /**
    * 没有利用到搜索树的特性
    * 3336 ms,50.00%
    * 54 MB,100.00%
    *
    * @param root
    * @return
    */
  def findMode(root: TreeNode): Array[Int] = {
    lazy val map: mutable.Map[Int, Int] = new mutable.HashMap[Int, Int]()

    def helper(root: TreeNode): Unit = {
      Option(root).fold(()) { root =>
        if (map.contains(root.value)) {
          map.put(root.value, 1 + map(root.value))
        } else {
          map.put(root.value, 1)
        }
        helper(root.left)
        helper(root.right)
      }
    }

    helper(root)
    map.filter(_._2 == map.values.max).keys.toArray
  }

  /**
    * 中序遍历，利用有序特性
    * 768 ms,100.00%
    * 52.9 MB,100.00%
    *
    * @param root
    * @return
    */
  def findMode2(root: TreeNode): Array[Int] = {
    var preNode: TreeNode = null
    var curCount = 0
    var max = 0
    var ret = Array[Int]()

    def inOrder(root: TreeNode): Unit = {
      if (root == null) return
      inOrder(root.left)
      if (preNode != null && preNode.value == root.value) {
        curCount += 1
      } else {
        curCount = 1
      }
      if (curCount == max) {
        ret = ret ++ Array(root.value)
      } else if (curCount > max) {
        max = curCount
        ret = Array[Int](root.value)
      }
      preNode = root
      inOrder(root.right)
    }

    inOrder(root)
    ret
  }

  val ret2 = findMode2(TreeNodeData.treeData3())
  ret2.foreach(println(_))
}
