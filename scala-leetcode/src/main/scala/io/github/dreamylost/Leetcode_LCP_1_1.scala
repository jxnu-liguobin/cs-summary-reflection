/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 猜数字
  *
  * @author 梦境迷离
  * @since 2020-03-13
  * @version v1.0
  */
object Leetcode_LCP_1_1 extends App {

  println(game(Array(1, 2, 3), Array(1, 2, 3)))

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
}
