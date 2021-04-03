/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

import javax.annotation.Nonnull

/**
 * @author 梦境迷离
 * @since 2021/3/21
 * @version 1.0
 */
object ParameterAnnotation extends App {

  //下面注解均无效
  def test(@Nonnull args: String): Unit = {

    println(args)
  }

  case class A()
  def test1(@Nonnull a: A): Unit = {
    println(a)
  }

  @Nonnull
  def test2(@Nonnull a: A): A = {
    a
  }

  test(null)
  test1(null)
  test2(null)

}
