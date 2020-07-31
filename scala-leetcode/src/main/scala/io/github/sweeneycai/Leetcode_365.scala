/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

/**
  * 365. 水壶问题
  */
object WaterPouringProblem extends App {
  def canMeasureWater(x: Int, y: Int, z: Int): Boolean = {
    val game = new Pouring(Vector(x, y))
    game.solutions(z).take(1).nonEmpty
  }

  class Pouring(capacity: Vector[Int]) {
    // States
    type State = Vector[Int]
    val initialState: Vector[Int] = capacity map (x => 0)

    // Moves
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
      def change(state: State): State = {
        val amount = state(from) min (capacity(to) - state(to))
        state updated (from, state(from) - amount) updated (to, state(to) + amount)
      }
    }

    val glasses: Range = capacity.indices

    val moves: Seq[Move] =
      (for (g <- glasses) yield Empty(g)) ++
        (for (g <- glasses) yield Fill(g)) ++
        (for (from <- glasses; to <- glasses if from != to) yield Pour(from, to))

    class Path(history: List[Move], val endState: State) {
      //    def endState: State = (history foldRight initialState) (_ change _)

      //    def endState: State = (history foldRight initialState)((move, state) => move.change(state))
      //    private def trackState(xs: List[Move]): State = xs match {
      //      case Nil => initialState
      //      case move :: xs1 => move change trackState(xs1)
      //    }
      def extend(move: Move): Path = new Path(move :: history, move change endState)

      override def toString: String = (history.reverse mkString " ") + "-->" + endState
    }

    val initialPath = new Path(Nil, initialState)

    def from(paths: Set[Path], explored: Set[State]): Stream[Set[Path]] =
      if (paths.isEmpty) Stream.empty
      else {
        val more = for {
          path <- paths
          next <- moves map path.extend
          if !(explored contains next.endState)
        } yield next
        paths #:: from(more, explored ++ (more map (_.endState)))
      }

    val pathSets = from(Set(initialPath), Set(initialState))

    def solutions(target: Int): Stream[Path] =
      for {
        pathSet <- pathSets
        path <- pathSet
        if (path.endState contains target) || path.endState.sum == target
      } yield path
  }

  val pouring = new Pouring(Vector(3, 5))
  println(pouring.solutions(4).take(1))
}
