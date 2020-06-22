package io.github.dreamylost.examples

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration

/**
  * @author liguobin@growingio.com
  * @version 1.0,2020/3/25
  */
object TestFold extends App {

  val d = Option(111)
  val empty = None

  //fold[_]是不行的，无法推断类型。
  val f = d.fold[Future[Option[_]]](Future.successful(None))(f => Future.successful(Option(f)))
  val f2 = empty.fold[Future[Option[_]]](Future.successful(None))(f => Future.successful(Option(f)))
  println(Await.result(f, Duration.Inf))
  println(Await.result(f2, Duration.Inf))
}
