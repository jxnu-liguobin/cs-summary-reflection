package cn.edu.jxnu.utils.http

/**
 * undertow 处理器接口
 *
 * 一般restful覆盖route和methods即可
 *
 * @author 梦境迷离
 * @time 2019-08-18
 * @version v1.0
 */
trait RoutingHandler {

  //uri
  def route: String

  //POST GET DELETE PUT
  def methods: Set[String]

  def pattern: Option[String] = None

  def single(method: String): Set[String] = Set(method)

  def multi(methods: String*): Set[String] = methods.toSet

}
