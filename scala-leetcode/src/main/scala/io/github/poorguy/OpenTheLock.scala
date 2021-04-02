package io.github.poorguy

import io.github.poorguy.`trait`.{Bfs_, Queue_}

import scala.collection.mutable

object OpenTheLock extends App with Queue_ with Bfs_ {
  def openLock(deadends: Array[String], target: String): Int = {
    val origin = "0000"
    if (origin.equals(target)) {
      return 0
    }

    val deadSet = deadends.toSet
    val directions = List.range(0, 8)
    var depth = 0
    var sameDepthCount = 0
    val queue = mutable.Queue[String]("0000")
    while (queue.nonEmpty) {
      if (sameDepthCount == 0) depth = depth + 1
      else sameDepthCount -= 1

      val value = queue.dequeue()
      for (direct <- directions) {
        val newVal = tune(value, deadSet, direct)
        if (deadSet.contains(newVal)) {

        } else if (newVal.equals(target)) {
          return depth
        } else {
          queue.enqueue(newVal)
          sameDepthCount = sameDepthCount + 1
        }
      }
    }
    -1
  }

  def tune(origin: String, deadSet: Set[String], direction: Int): String = {
    val upDown = if (direction % 2 == 0) 1 else -1
    val index = origin.length() - 1 - direction / 2
    val value = origin.charAt(index).toString.toInt
    val originChars = origin.toCharArray

    if (upDown > 0 && value == 9) {
      originChars(index) = '0'
    } else if (upDown < 0 && value == 0) {
      originChars(index) = '9'
    } else {
      originChars(index) = (value + upDown).toString.charAt(0)
    }
    originChars.mkString
  }

  val list = Array("0201", "0101", "0102", "1212", "2002")
  print(openLock(list, "0202"))
}
