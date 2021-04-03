/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

import scala.util.control.Breaks._
import scala.util.Random

/**
 * 固定层级的Scala跳表实现
 *
 * @see https://stackoverflow.com/questions/6864278/does-java-have-a-skip-list-implementation
 * @author 梦境迷离
 * @version 1.0, 2020-11-03
 */
trait SkippableList[E] {

  type Node

  def delete(target: E): Boolean

  def printList(): Unit

  def insert(value: E): Unit

  def search(value: E): Node
}

class SkipList[E](val level: Int = 5, isPrint: Boolean = true)(implicit ordering: Ordering[E])
    extends SkippableList[E] {

  final private val head = new SkipNode[E](null.asInstanceOf[E])
  final private val rand = new Random

  override type Node = this.SkipNode[E]

  override def insert(value: E): Unit = {
    val insertNode = new SkipNode[E](value)
    var currentLevel = 0
    while (currentLevel < level) {
      // insert with prob = 1/(2^i)
      if (rand.nextInt(Math.pow(2, currentLevel).toInt) == 0) {
        head.insert(insertNode, currentLevel)
      }
      currentLevel += 1
    }
  }

  override def delete(value: E): Boolean = {
    println("Deleting " + value)
    val victim = search(value)
    if (victim == null) return false
    victim.value = null.asInstanceOf[E]
    var currentLevel = 0
    while (currentLevel < level) {
      head.refreshAfterDelete(currentLevel)
      currentLevel += 1
    }
    println("deleted...")
    println()
    true
  }

  override def search(value: E): SkipNode[E] = {
    var result: SkipNode[E] = null
    var currentLevel = level - 1
    breakable(
      // 自顶向下
      while (currentLevel >= 0) {
        result = head.search(value, currentLevel, isPrint)
        if (result != null) {
          if (isPrint) {
            println("Found " + value.toString + " at level " + currentLevel + ", so stopped")
            println()
          }
          break()
        }

        currentLevel -= 1
      }
    )
    result
  }

  override def printList(): Unit = {
    var currentLevel = level - 1
    while (currentLevel >= 0) {
      head.printLevel(currentLevel)
      currentLevel -= 1
    }
    println()
  }

  class SkipNode[V](var value: V)(implicit ordering: Ordering[V]) {

    final private val next: Array[SkipNode[V]] = new Array[SkipNode[V]](level)

    def refreshAfterDelete(level: Int): Unit = {
      var current = this
      while (current != null && current.getNext(level) != null) {
        if (current.getNext(level).value == null) {
          val successor = current.getNext(level).getNext(level)
          current.setNext(successor, level)
          return
        }
        current = current.getNext(level)
      }
    }

    def setNext(next: SkipNode[V], level: Int): Unit = this.next(level) = next

    def getNext(level: Int): SkipNode[V] = this.next(level)

    def search(value: V, level: Int, isPrint: Boolean): SkipNode[V] = {
      if (isPrint) {
        print("Searching for: " + value + " at ")
        printLevel(level)
      }
      var result: SkipNode[V] = null
      var current = this.getNext(level)
      breakable(
        // 自左向右
        while (current != null && ordering.compare(current.value, value) < 1) {
          if (current.value == value) {
            result = current
            break()
          }
          current = current.getNext(level)
        }
      )
      result
    }

    def insert(insertNode: SkipNode[V], level: Int): Unit = {
      // 获取跳表的当前层
      var current = this.getNext(level)
      // 如果当前节点是空的，直接插入并返回即可
      if (current == null) {
        this.setNext(insertNode, level)
        return
      }
      // 如果待插入的数据比当前层的节点小
      if (ordering.compare(insertNode.value, current.value) < 1) {
        // 将待插入的节点设置为新的层
        this.setNext(insertNode, level)
        // 新插入节点的层级设置为新的当前层节点
        insertNode.setNext(current, level)
        return
      }
      // 待插入节点大于当前节点以及它的下个节点
      while (
        current.getNext(level) != null && ordering.compare(current.value, insertNode.value) < 1 &&
        ordering.compare(current.getNext(level).value, insertNode.value) < 1
      ) {
        current = current.getNext(level)
      }
      val successor = current.getNext(level)
      current.setNext(insertNode, level)
      insertNode.setNext(successor, level)
    }

    def printLevel(level: Int): Unit = {
      import scala.collection.mutable.ListBuffer
      val values = ListBuffer[V]()
      var length = 0
      var current = this.getNext(level)
      while (current != null) {
        length += 1
        values.append(current.value)
        current = current.getNext(level)
      }
      println(s"level $level : ${values.mkString("[ ", " -> ", " ]")}, length: ${values.length}")
    }
  }

}

object SkipListTest extends App {

  val sl = new SkipList[Int]
  val data = Array(4, 2, 7, 0, 9, 1, 3, 7, 3, 4, 5, 6, 0, 2, 8)
  for (i <- data) {
    sl.insert(i)
    sl.printList()

  }
  println("======================================")
  sl.printList()
  sl.search(4)
  sl.delete(4)
  println("Inserting 10")
  sl.insert(10)
  sl.printList()
  sl.search(10)
}
