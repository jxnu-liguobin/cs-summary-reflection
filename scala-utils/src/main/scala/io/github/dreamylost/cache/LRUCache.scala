package io.github.dreamylost.cache

import java.{ util => ju }

import scala.collection.JavaConverters._

/**
  * this lru-cache use LinkedHashMap don't resolve ConcurrentModificationException
  * 1. use local var to save values when put in other's threads
  * 2. use google's ConcurrentLinkedHashMap
  *
 * @author 梦境迷离
  * @since 2019-09-28
  * @version v1.0
  */
class LRUCache[K, V](val maxSize: Int, underlying: ju.Map[K, V]) {

  def put(key: K, value: V): Unit = {
    this.underlying.put(key, value)
  }

  def get(key: K): Option[V] = {
    Option(this.underlying.get(key))
  }

  //can't use it  when put in other's threads
  def values(): Seq[V] = {
    this.underlying.values().asScala.toSeq
  }

  def contains(key: K): Boolean = {
    this.underlying.containsKey(key)
  }

  def batchPut(values: Seq[(K, V)]): Unit = {
    values.foreach {
      case (key, value) =>
        put(key, value)
    }
  }

  def clear(): Unit = {
    this.underlying.clear()
  }
}

object LRUCache {

  //use it to create a cache
  def apply[K, V](maxSize: Int) = new LRUCache(maxSize, LRUCache.makeUnderlying[K, V](maxSize))

  // initial capacity and load factor are the normal defaults for LinkedHashMap
  def makeUnderlying[K, V](maxSize: Int): ju.Map[K, V] =
    new ju.LinkedHashMap[K, V](
      maxSize, /* initial capacity */
      0.75f, /* load factor */
      true /* access order (as opposed to insertion order) */
    ) {
      override protected def removeEldestEntry(eldest: ju.Map.Entry[K, V]): Boolean = {
        this.size() > maxSize
      }
    }
}
