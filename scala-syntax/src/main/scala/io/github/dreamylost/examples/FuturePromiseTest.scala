package io.github.dreamylost.examples

/**
 *
 * @author liguobin@growingio.com
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