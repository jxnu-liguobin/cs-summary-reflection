package io.github.dreamylost

/**
  * 563. 二叉树的坡度
  *
 * 给定一个二叉树，计算整个树的坡度。
  *
 * 一个树的节点的坡度定义即为，该节点左子树的结点之和和右子树结点之和的差的绝对值。空结点的的坡度是0。
  *
 * 整个树的坡度就是其所有节点的坡度之和。
  *
 * @author 梦境迷离 dreamylost
  * @since 2020-06-13
  * @version v1.0
  */
object Leetcode_563 extends App {

  val ret = findTilt(TreeNodeData.treeData3())
  println(ret)

  //1.二叉树中序递归的通用高阶函数
  def helper(root: TreeNode)(cur: TreeNode => Unit): Unit = {
    if (root == null) return
    helper(root.left)(cur)
    cur(root)
    helper(root.right)(cur)
  }

  //2.对当前树进行求左右子树和的差值的绝对值
  def subTreeAbs(root: TreeNode): Int = {
    if (root == null) return 0
    var sumLeft = 0
    var sumRight = 0
    helper(root.left)(cur => sumLeft += cur.value)
    helper(root.right)(cur => sumRight += cur.value)
    math.abs(sumLeft - sumRight)
  }

  /**
    * 972 ms,100.00%
    * 52.9 MB,100.00%
    *
   * @param root
    * @return
    */
  def findTilt(root: TreeNode): Int = {
    //3.对当前所有节点进行求度
    var allSum = Seq[Int]()
    helper(root) { cur =>
      allSum = allSum ++ Seq(subTreeAbs(cur))
    }
    allSum.sum
  }

  /**
    * 668 ms,100.00%
    * 52 MB,100.00%
    *
   * @param root
    * @return
    */
  def findTilt2(root: TreeNode): Int = {
    var sum = 0

    //每次返回左右子树，每次使用左右子树和进行更新sum
    def dfs(rt: TreeNode): Int = {
      if (rt == null) return 0
      val l: Int = dfs(rt.left)
      val r: Int = dfs(rt.right)
      sum += Math.abs(l - r)
      rt.value + l + r
    }

    dfs(root)
    sum
  }

}
