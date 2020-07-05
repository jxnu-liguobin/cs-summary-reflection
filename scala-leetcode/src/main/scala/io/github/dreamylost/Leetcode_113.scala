package io.github.dreamylost

/**
  * 113. 路径总和 II
  *
  * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-05
  * @version v1.0
  */
object Leetcode_113 extends App {

  val ret = pathSum(TreeNodeData.treeData11(), 22)
  val ret2 = pathSum2(TreeNodeData.treeData11(), 22)
  println(ret)
  println(ret2)

  /**
    * 暴力，记录所有的路径，在过滤
    *
    * 812 ms,20.00%
    * 60.9 MB,100.00%
    *
    * @param root
    * @param sum
    * @return
    */
  def pathSum(root: TreeNode, sum: Int): List[List[Int]] = {
    var ret = List[List[Int]]()

    def helper(r: TreeNode, path: List[Int]): Unit = {
      if (r == null) return
      if (r.left == null && r.right == null) {
        ret = ret ++ List(path ::: List(r.value))
        return
      }
      if (r.left != null) helper(r.left, path ::: List(r.value))
      if (r.right != null) helper(r.right, path ::: List(r.value))
    }

    helper(root, List.empty[Int])
    ret.filter(_.sum == sum)

  }

  /**
    * 使用回溯
    *
    * 660 ms,100.00%
    * 53.6 MB,100.00%
    *
    * @param root
    * @param sum
    * @return
    */
  def pathSum2(root: TreeNode, sum: Int): List[List[Int]] = {
    var ret = List[List[Int]]()

    //使用可变数据结构，在叶子上或递归完成时需要删除最后一个，回溯
    //此时append操作可以放在最外面
    def helper(r: TreeNode, path: collection.mutable.ListBuffer[Int], sum: Int): Unit = {
      if (r == null) return
      val tmp = sum - r.value
      path.append(r.value)
      if (tmp == 0 && r.left == null && r.right == null) {
        ret = ret ::: List(path.toList)
        path.remove(path.length - 1)
        return
      }
      helper(r.left, path, tmp)
      helper(r.right, path, tmp)
      path.remove(path.length - 1)
    }

    helper(root, collection.mutable.ListBuffer[Int](), sum)
    ret
  }
}
