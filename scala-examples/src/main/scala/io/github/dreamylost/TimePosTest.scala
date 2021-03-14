/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

import java.time.ZonedDateTime
import java.time.ZoneOffset

/**
  * @author 梦境迷离
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
