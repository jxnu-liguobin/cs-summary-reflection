package cn.edu.jxnu.utils.other

import com.google.common.base.Charsets
import com.google.common.hash.Hashing

import scala.collection.immutable.TreeMap


/**
 * map、properties、加密传输
 *
 * @author 梦境迷离
 * @since 2019-09-04
 * @version v1.0
 */
object SignUtilities {

  /**
   * 将属性值拼接为查询字符串&拼接
   *
   * @param elements 键值对
   * @return
   */
  def queryString(elements: (String, String)*): String = {
    val tree = TreeMap[String, String](elements: _*)
    tree.map(entry => s"${entry._1}=${entry._2}").mkString("&")
  }

  /**
   * 对键值对拼接为查询字符串后再进行hash
   *
   * @param elements
   * @return
   */
  def sign(elements: (String, String)*): String = {
    Hashing.sha1().hashString(queryString(elements: _*), Charsets.UTF_8).toString
  }

  /**
   * 判断已知的hash值与未hash键值对是否相等
   *
   * @param signature
   * @param elements
   * @return
   */
  def validate(signature: String, elements: (String, String)*): Boolean = {
    signature == sign(elements.filterNot(element => element._1 == "sign"): _*)
  }
}
