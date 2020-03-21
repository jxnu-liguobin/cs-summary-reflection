package io.github.dreamylost

import java.time.{ ZonedDateTime, ZoneOffset }

/**
 *
 * @author liguobin@growingio.com
 * @version 1.0,2019/9/30
 */
object TimePosTest extends App {

  def getTimePosOfHour = {
    import java.time.format.DateTimeFormatter
    val timeStart = ZonedDateTime.now(ZoneOffset.UTC)
    val timeEnd = timeStart.plusHours(1)
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHH00")
    val startPos = timeStart.format(formatter)
    val stopPos = timeEnd.format(formatter)
    startPos -> stopPos
  }

  println(getTimePosOfHour)
}