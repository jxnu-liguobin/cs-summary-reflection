package cn.edu.jxnu.scala.utils

import scala.collection.mutable

/**
 * @author 梦境迷离
 * @version 1.0, 2019-07-15
 */
object StringUtils {

  /** 获取Get的查询参数集
   *
   * @param queryParams ?后面的查询参数
   * @return
   */
  def query(queryParams: String): mutable.HashMap[String, String] = {
    val map = mutable.HashMap[String, String]() //可变 Map
    if (queryParams.isEmpty || !queryParams.contains("&") && !queryParams.contains("=")) map
    else {
      val params = queryParams.split("&")
      for (p <- params) {
        val kv = p.split("=")
        if (kv.length == 2) {
          map.+=(kv(0) -> kv(1))
        }
      }
      map
    }
  }

  /** 获取指定的value
   *
   * @param queryParams ?后面的查询参数
   * @param key         GET的参数key
   * @return
   */
  def query(queryParams: String, key: String): String = {
    val map = mutable.HashMap[String, String]()
    if (queryParams.isEmpty || !queryParams.contains("&") && !queryParams.contains("=")) ""
    else {
      val params = queryParams.split("&")
      for (p <- params) {
        val kv = p.split("=")
        if (kv.length == 2) {
          map.+=(kv(0) -> kv(1))
        }
      }
      map.getOrElse(key, "")
    }
  }
}
