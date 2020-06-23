package io.github.dreamylost.examples

import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.util.Success

/**
  * @author 梦境迷离
  * @version 1.0,2019/11/8
  */
object FuturePromiseTest extends App {

  var i = 0

  Await.result(promise(), Duration.Inf)

  def produceNumber() = {
    (1 to 500).foreach(_ => i += 1)
    i
  }

  def consumerNumber() = {
    (1 to 500).foreach(_ => i -= 1)
    i
  }

  def promise() = {
    val p = Promise[Unit]()
    val f = p.future

    val producer = Future {
      val r = produceNumber()
      println(r)
      p success r
    }

    val consumer = Future {
      f onComplete {
        case Success(_) =>
          val r = consumerNumber()
          println(r)
      }
    }

    for {
      _ <- producer
      _ <- consumer
    } yield ()
  }

}
