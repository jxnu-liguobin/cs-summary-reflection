package io.github.dreamylost.other

import scala.collection.mutable

/**
  * 集合工具
  *
 * @author 梦境迷离
  * @since 2019-09-04
  * @version v1.0
  */
object CollectionUtils {

  implicit class SeqWrapper[A](seq: Seq[A]) {

    /**
      * seq 去重
      *
     * @param f
      * @tparam B
      * @return
      */
    def distinctBy[B](f: A => B): Seq[A] = {
      val result = mutable.ArrayBuffer[A]()
      val seen = mutable.HashSet[B]()
      for (x <- seq) {
        val key = f(x)
        if (!seen(key)) {
          result += x
          seen += key
        }
      }
      result.toIndexedSeq
    }

    /**
      * seq 取交集
      *
     * @param those
      * @param distinctBy
      * @tparam B
      * @return
      */
    def &[B](those: Seq[A])(distinctBy: A => B): Seq[A] =
      seq.filter(s => those.exists(t => distinctBy(t) == distinctBy(s)))
  }

}
