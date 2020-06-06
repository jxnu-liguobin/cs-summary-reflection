package io.github.dreamylost

/**
统计路径和等于一个数的路径数量
437. Path Sum III (Easy)

root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1
Return 3. The paths that sum to 8 are:
1.  5 -> 3
2.  5 -> 2 -> 1
3. -3 -> 11
  *
  * @author 梦境迷离
  * @time 2018年7月18日
  * @version v1.0
  */
object Leetcode_437_Tree extends App {

  /**
    * 路径不一定以 root 开头，也不一定以 leaf 结尾，但是必须连续。
    * 664 ms,12.50%
    * 51.4 MB,100.00%
    */
  def pathSum(root: TreeNode, sum: Int): Int = {
    if (root == null) return 0
    pathSumStartWithRoot(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum)
  }

  /**
    * 参考leetcode112，计算或判断路径与sum相等
    */
  private def pathSumStartWithRoot(root: TreeNode, sum: Int): Int = {
    if (root == null) return 0
    var ret = 0
    if (root.value == sum) ret += 1
    ret += pathSumStartWithRoot(root.left, sum - root.value) + pathSumStartWithRoot(
      root.right,
      sum - root.value
    )
    ret
  }
}
