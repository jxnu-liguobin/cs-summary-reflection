/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

import scala.io.StdIn

object SecondLargeNumbers extends App {

  def secondLargeNumbers: Int = {
    var max = Integer.MIN_VALUE
    var sec = Integer.MIN_VALUE
    var n = StdIn.readInt()
    while (n > 0) {
      n -= 1
      var temp = StdIn.readInt()
      if (temp >= max) {
        sec = max // sec=min,max=1 sec=1,max=2,sec=2,max=3...
        max = temp
      } else if (temp >= sec) {
        sec = temp
      }
    }
    sec
  }
}
