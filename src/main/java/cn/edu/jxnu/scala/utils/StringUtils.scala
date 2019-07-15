package cn.edu.jxnu.scala.utils

import scala.collection.mutable

/**
 * @author 梦境迷离
 * @version 1.0, 2019-07-15
 */
object StringUtils {

  //URL参数转Map
  def getToken(body: String, key: String = "access_token"): String = {
    val map = mutable.HashMap[String, String]() //可变 Map
    if (body.isEmpty || !body.contains("&") && !body.contains("=")) ""
    else {
      val params = body.split("&")
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
