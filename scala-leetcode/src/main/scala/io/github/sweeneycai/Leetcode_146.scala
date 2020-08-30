/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.sweeneycai

import scala.collection.mutable

/**
  * 146. LRU缓存机制 (Medium)
  *
  * 运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制。
  * 它应该支持以下操作： 获取数据 get 和 写入数据 put 。
  *
  * 获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
  * 写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。
  * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
  *
  * 尽量让所有操作的时间复杂度都为 `O(1)`。
  *
  * @see <a href="https://leetcode-cn.com/problems/lru-cache/">leetcode-cn.com</a>
  */
object Leetcode_146 extends App {

  /**
    * 注意点：
    * 查询一个节点值，插入一个节点值，更新一个节点值都要更新双向链表。
    */
  class LRUCache(_capacity: Int) {

    private val fakeHead = new HNode(0, 0)
    fakeHead.next = fakeHead
    fakeHead.prev = fakeHead

    private class HNode(var _key: Int, var _value: Int) {
      var prev: HNode = _
      var next: HNode = _

      def value: Int = _value

      def key: Int = _key

      def setValue(value: Int): Unit = _value = value

      override def toString: String = s"[${_key}, ${_value}]"
    }

    private val hashMap: mutable.HashMap[Int, HNode] = mutable.HashMap.empty

    def get(key: Int): Int = {
      if (hashMap.contains(key)) {
        moveToHead(hashMap(key))
        hashMap(key).value
      } else -1
    }

    def put(key: Int, value: Int) {
      // 已有该 key
      if (hashMap.contains(key)) {
        hashMap(key).setValue(value)
        moveToHead(hashMap(key))
        return
      }
      val hNode = new HNode(key, value)
      if (hashMap.size == _capacity) {
        val deletedKey = deleteNode()
        hashMap.-=(deletedKey)
      }
      hashMap.+=(key -> hNode)
      addNode(hNode)
    }

    private def moveToHead(hNode: HNode): Unit = {
      if (fakeHead.prev != hNode) {
        // 将hNode节点抽取出来
        hNode.prev.next = hNode.next
        hNode.next.prev = hNode.prev
        addNode(hNode)
      }
    }

    private def addNode(hNode: HNode): Unit = {
      if (fakeHead.next == fakeHead && fakeHead.prev == fakeHead) {
        fakeHead.prev = hNode
        fakeHead.next = hNode
        hNode.next = fakeHead
        hNode.prev = fakeHead
        return
      }
      // 将hNode粘进头结点处
      hNode.next = fakeHead
      hNode.prev = fakeHead.prev
      // 打破头结点处原有的结构
      hNode.prev.next = hNode
      fakeHead.prev = hNode
    }

    // return key of deleted node
    private def deleteNode(): Int = {
      // 获取最旧的节点的键
      val oldKey = fakeHead.next.key
      // 切割最旧节点
      fakeHead.next = fakeHead.next.next
      fakeHead.next.prev = fakeHead
      oldKey
    }

  }

  val cache = new LRUCache(2)
  cache.put(2, 1)
  cache.put(1, 1)
  cache.put(2, 3)
  assert(cache.get(2) == 3)
  cache.put(4, 1)
  assert(cache.get(1) == -1)
  assert(cache.get(2) == 3)
}
