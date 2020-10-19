/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.other

import java.util.concurrent.atomic.AtomicReference

/**
  * @see [[https://github.com/jxnu-liguobin]]
  * @author 梦境迷离
  * @since 2020-10-18
  * @version 1.0
  */
@unchecked
object AtomicLogic {

  def compareAndSetSync[T](ref: AtomicReference[T])(logic: T => T) {
    while (true) {
      val snapshot = ref.get
      val update = logic(snapshot) //无副作用的函数
      if (ref.compareAndSet(snapshot, update)) return
    }
  }

  def compareSync[T, V](ref: AtomicReference[T])(logic: T => V): V = {
    var snapshot = ref.get
    var result = logic(snapshot)
    while (snapshot != ref.get) {
      snapshot = ref.get
      result = logic(snapshot)
    }
    result
  }

}
