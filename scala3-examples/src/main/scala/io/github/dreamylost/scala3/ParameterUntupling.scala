package io.github.dreamylost.scala3

/**
  * 参数解组: https://dotty.epfl.ch/docs/reference/other-new-features/parameter-untupling.html
  */
object ParameterUntupling:

  def test(): Unit =
    val xs: List[String] = List("d", "o", "t", "t", "y")

    /**
      * Current behaviour in Scala 2.12.2 :
      * error: missing parameter type
      * Note: The expected type requires a one-argument function accepting a 2-Tuple.
      * Consider a pattern matching anonymous function, `{ case (s, i) =>  ... }`
      */
    xs.zipWithIndex.map((s, i) => println(s"$i: $s"))

