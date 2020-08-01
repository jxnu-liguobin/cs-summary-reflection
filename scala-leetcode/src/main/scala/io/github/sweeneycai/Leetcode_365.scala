/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 365. 水壶问题(Medium)
  * 有两个容量分别为 x升 和 y升 的水壶以及无限多的水。能否通过使用这两个水壶，从而可以得到恰好 z升 的水？
  *
  * 如果可以，最后请用以上水壶中的一或两个来盛放取得的 z升 水。
  *
  * 允许的操作：
  *
  * 装满任意一个水壶
  * 清空任意一个水壶
  * 从一个水壶向另外一个水壶倒水，直到装满或者倒空
  *
  * @author sweeneycai
  * @since 2020-3-23
  */
object Leetcode_365 extends App {
  def canMeasureWater(x: Int, y: Int, z: Int): Boolean = {
    val game = new Pouring(Vector(x, y))
    // 使用`take(1)`在找到一个符合条件的路径之后就停止计算
    game.solutions(z).take(1).nonEmpty
  }

  class Pouring(capacity: Vector[Int]) {
    // States
    type State = Vector[Int]
    // 初始状态，每个杯子都是空的
    val initialState: State = capacity map (_ => 0)

    // 定义了清空、填满、倾倒三种操作
    trait Move {
      def change(state: State): State
    }

    case class Empty(glass: Int) extends Move {
      def change(state: State): State = state updated (glass, 0)
    }

    case class Fill(glass: Int) extends Move {
      def change(state: State): State = state updated (glass, capacity(glass))
    }

    case class Pour(from: Int, to: Int) extends Move {
      // 倾倒数量取决于当前杯子的剩余水量和目标杯子的剩余容量
      def change(state: State): State = {
        val amount = state(from) min (capacity(to) - state(to))
        state updated (from, state(from) - amount) updated (to, state(to) + amount)
      }
    }

    val glasses: Range = capacity.indices

    // 对于每一个杯子，可以采取的动作是：清空杯子、填满杯子、将该杯子的水倒入其他杯子中
    val moves: Seq[Move] =
      (for (g <- glasses) yield Empty(g)) ++
        (for (g <- glasses) yield Fill(g)) ++
        (for (from <- glasses; to <- glasses if from != to) yield Pour(from, to))

    class Path(history: List[Move], val endState: State) {
      // 每一个状态都可以执行上述定义的`moves`以产生下一个状态
      def extend(move: Move): Path = new Path(move :: history, move change endState)

      override def toString: String =
        (history.reverse mkString " ") + " --> final state: " + endState
    }

    val initialPath = new Path(Nil, initialState)

    def from(paths: Set[Path], explored: Set[State]): Stream[Set[Path]] =
      if (paths.isEmpty) Stream.empty
      else {
        // 根据当前的paths产生下一系列可能路径
        val more = for {
          path <- paths
          next <- moves map path.extend // 根据当前状态应用定义的操作以产生下一系列状态
          if !(explored contains next.endState) // 过滤掉出现过的状态
        } yield next
        // 递归产生路径，并记录每一条路径的`endState`，由于使用了`Stream`会惰性计算
        paths #:: from(more, explored ++ (more map (_.endState)))
      }

    private val pathSets = from(Set(initialPath), Set(initialState))

    def solutions(target: Int): Stream[Path] = {
      // 注意在计算的时候，`pathSets`每产生一个状态，都会运行一次过滤条件判断是否包含最终状态
      // 如果包含最终状态就抛出该路径
      for {
        pathSet <- pathSets
        path <- pathSet
        // 从当前生成的路径中找出符合要求的路径
        if (path.endState contains target) || path.endState.sum == target
      } yield path
    }
  }

  val pouring = new Pouring(Vector(3, 5))
  println(pouring.solutions(6).toList)
  println(canMeasureWater(3, 5, 1))
}
