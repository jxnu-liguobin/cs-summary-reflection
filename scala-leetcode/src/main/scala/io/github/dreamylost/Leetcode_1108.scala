/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * 给你一个有效的 IPv4 地址 address，返回这个 IP 地址的无效化版本。
  *
  * 所谓无效化 IP 地址，其实就是用 "[.]" 代替了每个 "."。
  *
  * @author 梦境迷离
  * @since 2020-01-06
  * @version v1.0
  */
object Leetcode_1108 extends App {

  println(defangIPaddr("255.100.50.0"))

  def defangIPaddr(address: String): String = {
    address.flatMap(c => if (c == '.') List('[', '.', ']') else List(c))
    //    address.replace(".","[.]") //有点丢人，不好意思用
  }
}
