package cn.edu.jxnu.utils.other

import scala.collection.mutable
import scala.util.Try

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
  @deprecated
  def query(queryParams: String): mutable.HashMap[String, String] = {
    val map = mutable.HashMap[String, String]() //可变 Map
    if (queryParams.isEmpty || !queryParams.contains("=")) map
    else {
      val params = queryParams.split("&")
      params.foreach {
        p =>
          if (params.isEmpty) {
            Try(map += (queryParams.split("=")(0) -> queryParams.split("=")(1)))
          } else {
            val kv = p.split("=")
            if (kv.length == 2) {
              map.+=(kv(0) -> kv(1))
            }
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
    //不用Option
    query(queryParams).getOrElse(key, null)
  }

  def toOption(str: String): Option[String] = {
    if (java.util.Objects.isNull(str) || str.trim.isEmpty) None else Option(str)
  }

  def avoidBlank(stringOpt: Option[String]): Option[String] = {
    stringOpt.flatMap(s => if (s.trim.isEmpty) None else stringOpt)
  }

  def nonEmpty(stringOpt: Option[String]): Boolean = !isEmpty(stringOpt)

  def isEmpty(stringOpt: Option[String]): Boolean = avoidBlank(stringOpt).isEmpty
}
