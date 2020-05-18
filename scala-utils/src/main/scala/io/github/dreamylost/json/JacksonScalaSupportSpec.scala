package io.github.dreamylost.json

/**
  *
 * @author liguobin@growingio.com
  * @version 1.0,2020/4/30
  */
object JacksonScalaSupportSpec extends App {

  case class Filter(
      name: String,
      key: String,
      value: String,
      exprs: Option[Seq[Filter]] = None,
      values: Seq[String] = Seq.empty
  )

  val filter = Filter(
    name = "filter",
    "key",
    value = "value",
    exprs = Some(Seq(Filter(name = "filter-sub", "key-sub", value = "value-sub")))
  )
  println(filter)
  val json = JacksonScalaSupport.mapper.writeValueAsString(filter)
  println(json)

  println("=============================")
  val obj = JacksonScalaSupport.mapper.readValue(json, classOf[Filter])

  println(obj)

}
