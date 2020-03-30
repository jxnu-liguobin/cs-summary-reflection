---

早期学习时写的LeetCode、编程之美、剑指offer等（非全）

补充后期Scala、Java实现的LeetCode，此处只有Scala实现的（不同时期写法不同）

* 爬楼梯
```scala
  def climbStairs(number: Int): Int = {
    if (number <= 0)
      return 0
    if (number == 1 || number == 2)
      return number
    var (first, second, third) = (1, 2, 0)
    for (i <- 3 to number) {
      third = first + second
      first = second
      second = third
    }
    third
  }
```
* 最大面值（LintCode 669）
```scala
  def coinChange(coins: Array[Int], amount: Int): Int = {
      if (amount < 1) return 0
      val dp = new Array[Int](amount + 1)
      var sum = 1
      while (sum <= amount) { //硬币和
          var min = -1
          for (coin <- coins) { //每个硬币
              if (sum >= coin && dp(sum - coin) != -1) { //硬币必须小于sum,需要的硬币没被用过
                  val temp = dp(sum - coin) + 1
                  min = if (min < 0) temp else (math.min(temp, min)) //min=temp,或者min=temp,min
              }
          }
          dp(sum) = min
          sum = sum + 1
      }
      dp(amount)
  }
```
* 比特位计数
```scala
  def countBits(num: Int): Array[Int] = {
    val f = new Array[Int](num + 1);
    for (i <- 1 to num) {
      f(i) = f(i >> 1) + (i & 1)
    }
    f
  }
```
* 树的遍历
```scala
  //前
  def qiandfs(root: TreeNode) {
    visit(root)
    qiandfs(root.left)
    qiandfs(root.right)
  }

  //中
  def zhongdfs(root: TreeNode) {
    zhongdfs(root.left)
    visit(root)
    zhongdfs(root.right)
  }

  //后
  def houdfs(root: TreeNode) {
    houdfs(root.left)
    houdfs(root.right)
    visit(root)
  }

  val visit = (root: TreeNode) => {
    println(root.value)
  }

  //前，144. Binary Tree Preorder Traversal (Medium)
  def preorderTraversal(root: TreeNode): mutable.Seq[Int] = {
    val ret = mutable.Seq[Int]()
    val stack = mutable.Stack[TreeNode]()
    stack.push(root)
    while (stack.nonEmpty) {
      val node = stack.pop()
      if (node != null) {
        ret.:+(node.value)
        stack.push(node.right) // 先右后左，保证左子树先遍历
        stack.push(node.left)
      }
    }
    ret
  }

  //中， 94. Binary Tree Inorder Traversal (Medium)
  def inorderTraversal(root: TreeNode): mutable.Seq[Int] = {
    val ret = mutable.Seq[Int]()
    val stack = mutable.Stack[TreeNode]()
    if (root == null) return ret
    var cur = root
    while (cur != null || stack.nonEmpty) {
      while (cur != null) {
        stack.push(cur)
        cur = cur.left
      }
      val node = stack.pop()
      ret.:+(node.value)
      cur = node.right
    }
    ret
  }

  /**
   * [因为是栈，先左子树出栈，后右子树出栈]
   * 前序遍历为 root -> left -> right，后序遍历为 left -> right -> root。
   * 可以修改前序遍历成为 root -> right -> left，那么这个顺序就和后序遍历正好相反。
   */
  //后，145. Binary Tree Postorder Traversal (Medium)
  def postorderTraversal(root: TreeNode): mutable.Seq[Int] = {
    val ret = mutable.Seq[Int]()
    val stack = mutable.Stack[TreeNode]()
    stack.push(root)
    while (stack.nonEmpty) {
      val node = stack.pop()
      if (node != null) {
        ret.:+(node.value)
        stack.push(node.left) // 先右后左，保证左子树先遍历
        stack.push(node.right)
      }
    }
    ret.reverse
  }

  //层序
  def levelTraverse(root: TreeNode): mutable.Seq[Int] = {
    if (root == null) return mutable.Seq()
    val list = mutable.Seq[Int]();
    //Scala的Seq将是Java的List，Scala的List将是Java的LinkedList。
    val queue = mutable.Queue[TreeNode]() //层序遍历时保存结点的队列，可以省略new或者省略()
    queue.enqueue(root)
    //初始化
    while (queue.nonEmpty) {
      val node = queue.dequeue
      list.:+(node.value)
      if (node.left != null) queue.enqueue(node.left)
      if (node.right != null) queue.enqueue(node.right)
    }

    list
  }
```
* 打家劫舍
```scala
  def rob(nums: Array[Int]): Int = {
    if (nums == null || nums.length == 0) return 0
    if (nums.length == 1) return nums(0)
    val memo = new Array[Int](nums.length)
    memo(0) = nums(0)
    memo(1) = math.max(nums(0), nums(1))
    for (i <- 2 until nums.length) {
      memo(i) = math.max(memo(i - 1), memo(i - 2) + nums(i))
    }
    memo(nums.length - 1)
  }
```
* 镜像二叉树非递归
```scala
  def mirrorTree(root: TreeNode): TreeNode = {
    import scala.collection.mutable
    val queue = mutable.Queue[TreeNode]()
    queue.enqueue(root)
    while (queue.nonEmpty) {
      val tmp = queue.dequeue()
      if (tmp != null) {
        val left = tmp.left
        tmp.left = tmp.right
        tmp.right = left
        queue.enqueue(tmp.right)
        queue.enqueue(tmp.left)
      }
    }
    root
  }
```
* 反转数字
```scala
  def reverse(x: Int): Int = {
    var n = x
    var m = 0
    var tmp = 0
    while (n != 0) {
      m = m * 10 + n % 10
      if (m / 10 != tmp) {
        return 0
      }
      tmp = m
      n = n / 10
    }
    m
  }
```
* 求S中符合J的元素的个数
```scala
  def numJewelsInStones(J: String, S: String): Int = {
    S.toSeq.count(J.toSeq.contains)
  }
```
* IP 地址无效化  
```scala
  def defangIPaddr(address: String): String = {
    address.flatMap(c => if (c == '.') List('[', '.', ']') else List(c))
    //    address.replace(".","[.]") //
  }
```
* 访问所有点的最小时间  
```scala
  //切比雪夫距离
  //[[1,1],[3,4],[-1,0]],先对所有点提取同轴坐标，两两计算同轴再求abs和max、sum
  def minTimeToVisitAllPoints(points: Array[Array[Int]]): Int = {
    points.zipWithIndex.collect { //为了函数式而函数式(滑稽)
      case (num, index) if index < points.length - 1 =>
        Array(Math.max(Math.abs(num(0) - points(index + 1)(0)), Math.abs(num(1) - points(index + 1)(1))))
    }.flatten.sum
  }
```
* 整数的各位积和之差
```scala
  def subtractProductAndSum(n: Int): Int = {
    val f = n.toString.toCharArray.map(c => Integer.parseInt(c + ""))
    f.product - f.sum
  }
```
* 统计位数为偶数的数字  
```scala
  def findNumbers(nums: Array[Int]): Int = {
    val isEvenNum = (n: Int) => {
      var i = 0
      var j = n
      while (j > 0) {
        i += 1
        j = j / 10
      }
      if (i % 2 == 0) true else false
    }
    //_.toString.length == 2 (滑稽)
    nums.count(isEvenNum)
  }
```
* 将每个元素替换为右侧最大元素  
```scala
  //[1,0,0,1,0,0,1,1,1,0,0,0,0,0,0]
  /**
   * 如果一个十进制的数字，542542要和11拼接，需要542*10+1 = 5421542∗10+1=5421
   * 如果一个二进制的数字，101101要和11拼接，需要101*10+1 = 1011101∗10+1=1011 （注意按照二进制的方法计算）
   */
  def getDecimalValue(head: ListNode): Int = {
    var tmp: ListNode = head
    var `val` = 0
    //链表移动到右侧下一个节点的过程，其实就是二进制数左移1位的结果。
    while (tmp != null) {
      `val` = `val` << 1 | tmp.x
      tmp = tmp.next
    }
    `val`
  }
```
* 统计有序矩阵中的负数  
```scala
  def countNegatives(grid: Array[Array[Int]]): Int = {
    grid.flatten.count(_ < 0) //应该将矩阵是排序的考虑进去，从右下角或左下角使用标记位
  }
```
* 两数之和  
```scala
  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    import scala.collection.mutable
    val hash = mutable.HashMap[Int, Int]()
    val indexs = new Array[Int](2)
    for (i <- nums.indices) {
      if (hash.contains(nums(i))) {
        indexs(0) = hash(nums(i))
        indexs(1) = i
        indexs
      } else hash += (target - nums(i) -> i)
    }
    indexs
  }
```
* 两数相加  
```scala
  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    var link1 = l1
    var link2 = l2
    val root = new ListNode(0)
    var cursor = root
    var carry = 0
    while (link1 != null || link2 != null || carry != 0) {
      val l1Val = if (link1 != null) link1.value else 0
      val l2Val = if (link2 != null) link2.value else 0
      val sumVal = l1Val + l2Val + carry
      carry = sumVal / 10
      val sumNode = new ListNode(sumVal % 10)
      cursor.next = sumNode
      cursor = sumNode
      if (link1 != null) link1 = link1.next
      if (link2 != null) link2 = link2.next
    }
    root.next
  }
```
* 归并两个有序的链表
```scala
  def mergeTwoLists(l1: ListNode, l2: ListNode): ListNode = {
      if (l1 == null) return l2
      if (l2 == null) return l1
      if (l1.value < l2.value) {
          l1.next = mergeTwoLists(l1.next, l2)
          return l1
      } else {
          l2.next = mergeTwoLists(l1, l2.next)
          return l2
      }
  }
```
* 删除排序数组中的重复项II
```scala
  def removeDuplicates(nums: Array[Int]): Int = {
      var i = 0
      for (n <- nums) {
          if (i < 2 || n > nums(i - 2)) {
              nums(i) = n
              i += 1
          }
      }
      i
  }
```
* 从有序链表中删除重复节点
```scala
  def deleteDuplicates(head: ListNode): ListNode = {
     if (head == null || head.next == null) return head
      head.next = deleteDuplicates(head.next)
      return if (head.value == head.next.value) head.next else head
  }
```
* 树的对称
```scala
  def isSymmetric(root: TreeNode): Boolean = {
     if (root == null) return true
      return isSymmetric(root.left, root.right)
  }

  private def isSymmetric(t1: TreeNode, t2: TreeNode): Boolean = {
      if (t1 == null && t2 == null) return true
      if (t1 == null || t2 == null) return false
      if (t1.value != t2.value) return false
      return isSymmetric(t1.left, t2.right) && isSymmetric(t1.right, t2.left)
  }
```
* 树的高度
```scala
  def maxDepth(root: TreeNode): Int = {
      if (root == null) return 0
      return math.max(maxDepth(root.left), maxDepth(root.right)) + 1
  }
```
* 判断平衡树
```scala
  private var result = true
  def isBalanced(root: TreeNode): Boolean = {
    maxDepth(root)
    result
  }

  //@tailrec
  def maxDepth(root: TreeNode): Int = {
    if (root == null)
      return 0
    val l = maxDepth(root.left)
    val r = maxDepth(root.right)
    if (Math.abs(l - r) > 1)
      result = false
    return 1 + math.max(l, r)
  }
```
* 最小路径
```scala
  def minDepth(root: TreeNode): Int = {
    if (root == null) return 0
    var left = minDepth(root.left)
    var right = minDepth(root.right)
    if (left == 0 || right == 0) return left + right + 1
    return math.min(left, right) + 1
  }
```
* 判断路径和是否等于一个数
```scala
  //路径和定义为从 root 到 leaf 的所有节点的和
  def hasPathSum(root: TreeNode, sum: Int): Boolean = {
      if (root == null)
          return false
      if (root.left == null && root.right == null && root.value == sum)
          return true
      return hasPathSum(root.left, sum - root.value)|| hasPathSum (root.right, sum - root.value)
  }
```
* 相交链表
```scala
  /**
   * 设 A 的长度为 a + c，B 的长度为 b + c，其中 c 为尾部公共部分长度，可知 a + c + b = b + c + a。
   * 当访问 A 链表的指针访问到链表尾部时，令它从链表 B 的头部开始访问链表 B；同样地，当访问 B 链表的指针访问到链表尾部时，令它从链表 A 的头部开始访问链表 A。
   * 这样就能控制访问 A 和 B 两个链表的指针能同时访问到交点。
   */
  def getIntersectionNode(headA: ListNode, headB: ListNode): ListNode = {
    var l1 = headA
    var l2 = headB
    while (l1 != l2) {
      l1 = if (l1 == null) headB else l1.next
      l2 = if (l2 == null) headA else l2.next
    }
    l1.next = null
    l1
  }
```
* 链表反转
```scala
  def reverseList(head: ListNode): ListNode = {
    if (head == null || head.next == null) {
      return head
    }
    val next = head.next
    val newHead = reverseList(next)
    next.next = head
    head.next = null
    newHead
  }
```
* 翻转树
```scala
  def invertTree(root: TreeNode): TreeNode = {
      if (root == null)
          return null
      val left = root.left // 后面的操作会改变 left 指针，因此先保存下来
      root.left = invertTree(root.right)
      root.right = invertTree(left)
      root
  }
```
* 搜索二维矩阵II
```scala
  def searchMatrix(matrix: Array[Array[Int]], target: Int): Boolean = {
      if (matrix == null || matrix.length == 0 || matrix(0).length == 0)
          return false
      val m = matrix.length
      val n = matrix(0).length
      var row = 0
      var col = n - 1
      while (row < m && col >= 0) {
          if (target == matrix(row)(col))
              return true
          else if (target < matrix(row)(col))
              col = col - 1
          else row = row + 1
      }
      false
  }
```
* 找出数组中重复的数
```scala
    def findDuplicate(nums: Array[Int]): Int = {
        var l = 1
        var h = nums.length - 1
        while (l <= h) {
            val mid = l + (h - l) / 2
            var cnt = 0
            for (i <- 0 until nums.length) {
                if (nums(i) <= mid)
                    cnt = cnt + 1
            }
            if (cnt > mid)
                h = mid - 1
            else
                l = mid + 1
        }
        l
    }
  def findDuplicate2(nums: Array[Int]): Int = {
      var slow = nums(0)
      var fast = nums(nums(0))
      while (slow != fast) {
          slow = nums(slow)
          fast = nums(nums(fast))
      }
      fast = 0
      while (slow != fast) {
          slow = nums(slow)
          fast = nums(fast)
      }
      slow
  }
```
* 间隔遍历
```scala
  def rob(root: TreeNode): Int = {
    if (root == null) return 0
    var vart = root.value
    if (root.left != null) vart += rob(root.left.left) + rob(root.right.right)
    if (root.right != null) vart += rob(root.right.right) + rob(root.right.right)
    var valt = rob(root.left) + rob(root.right)
    math.max(valt, vart)
  }
```
* 有序矩阵的 Kth Element
```scala
  def kthSmallest(matrix: Array[Array[Int]], k: Int): Int = {
      val m = matrix.length
      val n = matrix(0).length
      var lo = matrix(0)(0)
      var hi = matrix(m - 1)(n - 1)
      while (lo <= hi) {
          val mid = lo + (hi - lo) / 2
          var cnt = 0
          for (i ← 0 until m) {
              for (j ← 0 until n if matrix(i)(j) <= mid) {
                  cnt += 1 //scala没++  --
              }
          }
          if (cnt < k) lo = mid + 1
          else hi = mid - 1
      }
      lo
  }

  class Tuple(var x: Int, var y: Int, var v: Int) extends Comparable[Tuple] {

      override def compareTo(that: Tuple): Int = {
          return this.v - that.v
      }
  }

  def kthSmallest(matrix: Array[Array[Int]], k: Int): Int = {
      val m = matrix.length
      val n = matrix(0).length
      val pq = new PriorityQueue[Tuple]
      for (j ← 0 until n) {
          pq.enqueue(new Tuple(0, j, matrix(0)(j)))
          // pq.offer(new Tuple(0, j, matrix(0)(j))
      }
      for (i ← 0 until k - 1) { // 小根堆，去掉 k - 1 个堆顶元素，此时堆顶元素就是第 k 的数
          val t = pq.dequeue()
          import util.control.Breaks._
          breakable(
              if (t.x == m - 1) break)
          pq.enqueue(new Tuple(t.x + 1, t.y, matrix(t.x + 1)(t.y)))
      }
      return pq.dequeue().v
  }
```
* 统计左叶子节点的和
```scala
  def sumOfLeftLeaves(root: TreeNode): Int = {
    if (root == null) return 0
    if (isLeaf(root.left))
      return root.left.value + sumOfLeftLeaves(root.right)
    return sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right)
  }

  private def isLeaf(node: TreeNode): Boolean = {
    if (node == null)
      return false
    return node.left == null && node.right == null
  }
```
* 统计路径和等于一个数的路径数量
```scala
  /**
   * 路径不一定以 root 开头，也不一定以 leaf 结尾，但是必须连续。
   */
  def pathSum(root: TreeNode, sum: Int): Int = {
      if (root == null) return 0
      val ret = pathSumStartWithRoot(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum)
      ret
  }

  /**
   * 参考leetcode112，计算或判断路径与sum相等
   */
  private def pathSumStartWithRoot(root: TreeNode, sum: Int): Int = {
      if (root == null) return 0
      var ret = 0
      if (root.value == sum) ret += 1
      ret += pathSumStartWithRoot(root.left, sum - root.value) + pathSumStartWithRoot(root.right, sum - root.value)
      ret
  }
```
* 两节点的最长路径
```scala
  //编程之美解法参见GetMaximumDistance
  def diameterOfBinaryTree(root: TreeNode): Int = {
      depth(root)
      max
  }

  private def depth(root: TreeNode): Int = {
      if (root == null)
          return 0
      val leftDepth = depth(root.left)
      val rightDepth = depth(root.right)
      max = math.max(max, leftDepth + rightDepth)
      return math.max(leftDepth, rightDepth) + 1
  }
```
* 嵌套数组
```scala
  def arrayNesting(nums: Array[Int]): Int = {
      var max = 0
      for (i <- 0 until nums.length) {
          var cnt = 0
          var j = i
          while ((nums(j) != -1)) {
              cnt += 1
              val t = nums(j)
              // 标记该位置已经被访问
              nums(j) = -1
              j = t
          }
          max = math.max(max, cnt)
      }
      return max
  }

  /**
   * 使用dfs
   */
  def arrayNesting2(nums: Array[Int]): Int = {
      if (nums == null || nums.length == 0) return 0
      val cArr = new Array[Int](nums.length)
      val visited = new Array[Boolean](nums.length)
      var max = 0
      for (i <- 0 until nums.length) {
          dfs(i, nums, cArr, visited)
          max = Math.max(max, cArr(i))
      }
      return max
  }

  def dfs(i: Int, nums: Array[Int], cArr: Array[Int], visited: Array[Boolean]) {
      if (cArr(i) > 0 || visited(i)) return
      var next = nums(i)
      if (cArr(next) > 0) {
          cArr(i) = cArr(next) + 1
          return
      }
      visited(i) = true
      dfs(next, nums, cArr, visited)
      cArr(i) = cArr(next) + 1
      visited(i) = false
  }
```
* 判断子树
```scala
  def isSubtree(s: TreeNode, t: TreeNode): Boolean = {
      if (s == null) return false
      isSubtreeWithRoot(s, t) || isSubtreeWithRoot(s.left, t) || isSubtreeWithRoot(s.right, t)
  }

  private def isSubtreeWithRoot(s: TreeNode, t: TreeNode): Boolean = {

      if (s == null && t == null) return true
      if (s == null || t == null) return false
      if (s.value != t.value) return false
      isSubtreeWithRoot(s.left, t.left) && isSubtreeWithRoot(s.right, t.right)

  }
```
* 合并二叉树  
```scala
  def mergeTrees(t1: TreeNode, t2: TreeNode): TreeNode = {
      if (t1 == null && t2 == null) return null
      if (t1 == null) return t2
      if (t2 == null) return t1
      val root = new TreeNode(t1.value + t2.value)
      root.left = mergeTrees(t1.left, t2.left)
      root.right = mergeTrees(t1.right, t2.right)
      return root
  }
```
* 二叉树的层平均值  
```scala
  def averageOfLevels(root: TreeNode): List[Double] = {
    val ret = List()
    if (root == null) return ret
    val queue = Queue[TreeNode]()
    queue :+ root
    while (!queue.isEmpty) {
      var cnt = queue.size
      var sum = 0
      for (i <- 0 until cnt) {
        val node = queue.dequeue()
        sum += node.value
        if (node.left != null) queue :+ node.left//向尾部追加
        if (node.right != null) queue :+ node.right
      }
      ret.+:(sum / cnt)
    }
    ret
  }
```
* 数组相邻差值的个数
```scala
  /**
   * 题目描述：数组元素为 1~n 的整数，要求构建数组，使得相邻元素的差值不相同的个数为 k。
   * 让前 k+1 个元素构建出 k 个不相同的差值，序列为：1 k+1 2 k 3 k-1 ... k/2 k/2+1.
   */
  def constructArray(n: Int, k: Int): Array[Int] = {
    val ret = new Array[Int](n)
    ret(0) = 1
    var interval = k
    for (i <- 1 to k) {
      ret(i) = if (i % 2 == 1) ret(i - 1) + interval else ret(i - 1) - interval
      interval -= 1
    }
    for (i <- k + 1 until n) {
      ret(i) = i + 1
    }
    ret
  }
```
* 找出二叉树中第二小的节点
```scala
  def findSecondMinimumValue(root: TreeNode): Int = {
    if (root == null) return -1
    if (root.left == null && root.right == null) return -1
    var leftVal = root.left.value
    var rightVal = root.right.value
    if (leftVal == root.value) leftVal = findSecondMinimumValue(root.left)
    if (rightVal == root.value) rightVal = findSecondMinimumValue(root.right)
    if (leftVal != -1 && rightVal != -1) return Math.min(leftVal, rightVal)
    if (leftVal != -1) return leftVal
    return rightVal
  }
```
* 数组的度
```scala
  def findShortestSubArray(nums: Array[Int]): Int = {
    val map = new HashMap[Integer, Array[Int]]
    //record分别记录[最大频率，最小索引，最大索引]
    var record: Array[Int] = null
    var maxFreq = 0
    for (i <- 0 until nums.length) {
      if (!map.contains(nums(i))) {
        map.put(nums(i), Array(1, i, i))
      } else {
        record = map.get(nums(i)).get
        map.put(nums(i), Array(record(0) + 1, record(1), i))
      }
      //更新最大频率
      record = map.get(nums(i)).get
      maxFreq = math.max(maxFreq, record(0))
    }
    var minLen = Int.MaxValue
    for (n <- nums) {
      record = map.get(n).get
      if (record(0) == maxFreq) {
        if (record(2) - record(1) < minLen) {
          minLen = record(2) - record(1) + 1
        }
      }
    }

    minLen
  }
```
* 对角元素相等的矩阵
```scala
  def isToeplitzMatrix(matrix: Array[Array[Int]]): Boolean = {
      for (i ← 0 until matrix(0).length) {
          if (!check(matrix, matrix(0)(i), 0, i)) {
              return false
          }
      }

      for (i ← 0 until matrix.length) {
          if (!check(matrix, matrix(i)(0), i, 0)) {
              return false
          }
      }

      true

  }
  def check(matrix: Array[Array[Int]], expectValue: Int, row: Int, col: Int): Boolean = {
      if (row >= matrix.length || col >= matrix(0).length) {
          return true
      }
      if (matrix(row)(col) != expectValue) {
          return false
      }
      check(matrix, expectValue, row + 1, col + 1)
  }
```
* 最多能完成排序的块  
```scala
  /**
   * 其基本思想是使用max[]数组跟踪最大值直到当前位置，并将其与排序数组(索引从0到arr.ength-1)进行比较。如果max[i]等于排序数组中索引i处的元素，则最后计数+。
   * 更新：正如@AF8EJFE所指出的，数字从0到arr.ength-1不等。因此，没有必要对ARR进行排序，我们可以简单地使用索引进行比较。
   */
  def maxChunksToSorted(arr: Array[Int]): Int = {
    if (arr == null || arr.length == 0) return 0
    var count = 0
    var max = 0
    for (i <- 0 until arr.length) {
      max = Math.max(max, arr(i))
      if (max == i) {
        count += 1
      }
    }
    count
  }
```
* 猜数字
```scala
  def game(guess: Array[Int], answer: Array[Int]): Int = {
    var ret = 0
    guess.zipWithIndex.map {
      case (d, i) =>
        answer.zipWithIndex.map {
          case (d1, i1) => {
            if (i == i1 && d == d1) {
              ret += 1
            }
          }
        }
    }
    ret
  }

  def game2(guess: Array[Int], answer: Array[Int]): Int = {
    var r = 0
    for (i <- guess.indices) {
      if (guess(i) == answer(i)) {
        r += 1
      }
    }
    r
  }
```
* 股票的最大利润
```scala
  def maxProfit(prices: Array[Int]): Int = {

    if (prices.length == 0) return 0
    var max = 0
    var temp = prices(0)
    for (i <- 1 until prices.length) {
      if (temp > prices(i)) {
        temp = prices(i)
      } else {
        max = math.max(max, prices(i) - temp)
      }
    }
    max
  }
``` 
* 子数组之和的最大值
```scala
  def maxSubArray(nums: Array[Int]): Int = {

    var nStart = nums(nums.length - 1)
    var nAll = nums(nums.length - 1)
    for (i <- (0 to nums.length - 2).reverse) {
      nStart = math.max(nums(i), nStart + nums(i))
      nAll = math.max(nStart, nAll)
    }
    nAll
  }
```
* 使用最小花费爬楼梯  
```scala
  def minCostClimbingStairs(cost: Array[Int]): Int = {

    if (cost.length == 1) return 0

    var s1 = cost(cost.length - 1)
    var s2 = 0
    var currval = 0
    // S1和S2跟踪下一步最小成本（一步两步）
    // 从倒数第二步开始到首步
    var i = cost.length - 2
    while (i >= 0) {
      currval = cost(i) + math.min(s1, s2)
      s2 = s1
      s1 = currval
      i -= 1
    }
    return math.min(s1, s2) // 1步或者两步

  }
``` 
* 最短路径
```scala
def minPathSum(grid: Array[Array[Int]]): Int = {

    if (grid == null || grid.length == 0 || grid(0).length == 0) {
      return 0
    }

    val M = grid.length
    val N = grid(0).length
    val sum = Array.ofDim[Int](M, N)
    sum(0)(0) = grid(0)(0)
    //对行进行求和
    for (i <- 1 until M) {
      sum(i)(0) = sum(i - 1)(0) + grid(i)(0)
    }
    //对列进行求和
    for (i <- 1 until N) {
      sum(0)(i) = sum(0)(i - 1) + grid(0)(i)
    }
    //直接用已有的2维数组 一步步叠加后，寻找最小
    //每次只能向下或者向右移动一步。
    for (i <- 1 until M) {
      for (j <- 1 until N) {
        sum(i)(j) = math.min(sum(i - 1)(j), sum(i)(j - 1)) + grid(i)(j)
      }
    }
    return sum(M - 1)(N - 1)
  }
```
* 区域和检索
```scala
  //牺牲空间
  for (i <- 1 until _nums.length)
    _nums(i) += _nums(i - 1);
  var nums = _nums;

  def sumRange(i: Int, j: Int): Int = {
    if (i == 0) return nums(j)
    nums(j) - nums(i - 1)
  }
```