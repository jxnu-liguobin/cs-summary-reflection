package io.github.dreamylost.other

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{ LocalDateTime, ZoneId, ZoneOffset, ZonedDateTime }
import java.util.{ Calendar, Date, Locale, TimeZone }

/**
 * 日期处理工具
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-14
 */
object DateUtils {

  private final val patternComplete = "yyyy-MM-dd HH:mm:ss"

  private final val patternAll = "yyyy-MM-dd HH:mm:ss SSS"

  private final val patternPart = "yyyy-MM-dd"

  private final lazy val default_zone = TimeZone.getTimeZone("GMT+8:00")

  implicit private final val default_zoneId = ZoneId.of(default_zone.getID)

  private final lazy val default_local = Locale.CHINA

  /**
   * 获取当前时间对应的utc时间，以及下一个小时时间点
   */
  def getTimePosOfHour = {
    import java.time.format.DateTimeFormatter
    val timeStart = ZonedDateTime.now(ZoneOffset.UTC)
    val timeEnd = timeStart.plusHours(1)
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHH00")
    val startPos = timeStart.format(formatter)
    val stopPos = timeEnd.format(formatter)
    startPos -> stopPos
  }


  /**
   * 时区默认北京的部分解析器
   *
   * @param timeZone
   */
  private def partSimpleDateFormat(timeZone: TimeZone = default_zone): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(patternPart)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * LocalDateTime的精确匹配解析器
   *
   * @param local 默认China
   */
  private def allDateTimeFormatter(implicit local: Locale) = DateTimeFormatter.ofPattern(patternAll, local)

  /**
   * LocalDateTime的完全匹配解析器（不含秒后的）
   *
   * @param local 默认China
   */
  private def completeDateTimeFormatter(implicit local: Locale) = DateTimeFormatter.ofPattern(patternComplete, local)

  /**
   * 时区默认北京的完整解析器
   *
   * @param timeZone
   */
  private def completeSimpleDateFormat(timeZone: TimeZone = default_zone): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(patternComplete)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * 时区默认北京的精确解析器
   *
   * @param timeZone
   */
  private def allSimpleDateFormat(timeZone: TimeZone = default_zone): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(patternAll)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * 时区默认北京的自定义解析器
   *
   * @param timeZone
   * @param pattern
   * @return
   */
  def simpleDateFormat(timeZone: TimeZone = default_zone, pattern: String): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(pattern)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * 获取格式化后的当前时间 yyyy-MM-dd
   */
  def partFormatCurrentDate = partSimpleDateFormat().format(new Date)

  /**
   * 获取特定格式的当前时间 yyyy-MM-dd
   */
  def partParseCurrentDate = partSimpleDateFormat().parse(partFormatCurrentDate)

  /**
   * 获取特定格式的当前时间
   *
   * @param pattern 自定义格式
   */
  def formatCurrentDate(pattern: String) = simpleDateFormat(pattern = pattern).format(new Date)

  /**
   * 获取当前时间 yyyy-MM-dd HH:mm:ss
   */
  def completeFormatCurrentDate = completeSimpleDateFormat().format(new Date)

  /**
   * 获取特定格式的当前时间 yyyy-MM-dd HH:mm:ss
   */
  def completeParseCurrentDate = completeSimpleDateFormat().parse(completeFormatCurrentDate)

  /**
   * 获取当前时间Long类型
   */
  def getCurrentTimeStamp = new Date().getTime

  /**
   * 获取当前时间戳的 yyyy-MM-dd HH:mm:ss 表示
   *
   * @param timeStamp 时间戳
   */
  def completeFormatTimeStamp(timeStamp: Long) = completeSimpleDateFormat().format(new Date(timeStamp))

  /**
   * 使用自定义的格式串，格式化指定的日期
   *
   * @param date    日期
   * @param pattern 日期格式
   */
  def formatDate(date: Date, pattern: String) = simpleDateFormat(pattern = pattern).format(date)

  /**
   * 使用自定义的格式串，格式化指定的日期字符串
   *
   * @param dateStr
   * @param pattern
   */
  def parseDate(dateStr: String, pattern: String) = simpleDateFormat(pattern = pattern).parse(dateStr)

  /**
   * 用 yyyy-MM-dd HH:mm:ss 格式化指定的日期
   *
   * @param date 时间对象
   */
  def completeFormatDate(date: Date) = completeSimpleDateFormat().format(date)

  /**
   * 从日期字符串解析出完整的日期（有时分秒）
   *
   * @param dateStr 时间字符串
   */
  def completeParseDate(dateStr: String) = completeSimpleDateFormat().parse(dateStr)

  /**
   * 从日期字符串仅解析出日期（无时分秒）
   *
   * @param dateStr 日期字符串
   */
  def partParseDate(dateStr: String) = partSimpleDateFormat().parse(dateStr)

  /**
   * 用 yyyy-MM-dd 格式化指定的日期
   */
  def partFormatDate(date: Date) = partSimpleDateFormat().format(date)

  /**
   * 精确解析时间字符串（毫秒级）
   *
   * @param dateStr 包含毫秒的时间字符串
   */
  def allParseDate(dateStr: String) = allSimpleDateFormat().parse(dateStr)

  /**
   * 用 yyyy-MM-dd HH:mm:ss sss 格式化指定的日期
   *
   * @param date 包含毫秒的时间
   */
  def allFormatDate(date: Date) = allSimpleDateFormat().format(date)

  /**
   * 获取当前时间戳的 yyyy-MM-dd HH:mm:ss SSS 表示
   *
   * @param timeStamp 时间戳
   */
  def allFormatTimeStamp(timeStamp: Long) = allSimpleDateFormat().format(new Date(timeStamp))

  /**
   * 获取星期几
   */
  def dayOfWeek = (dateStr: String) => {
    val date = partSimpleDateFormat().parse(dateStr)
    val cal = Calendar.getInstance()
    cal.setTime(date)
    var w = cal.get(Calendar.DAY_OF_WEEK) - 1
    //星期天 默认为0
    if (w <= 0)
      w = 7
    w
  }

  /**
   * 判断是否是周末
   */
  def isWeekendDay = (dateStr: String) => {
    val dayNumOfWeek = dayOfWeek(dateStr)
    dayNumOfWeek == 6 || dayNumOfWeek == 7
  }

  /**
   * 获取前/后n天日期
   *
   * @param daysAgo 为负数时表示前n天
   * @return 基于北京时区的前/后n天的日期字符串
   */
  def daysAgo(daysAgo: Int): String = {
    val calendar = Calendar.getInstance
    calendar.setTime(new Date)
    calendar.add(Calendar.DAY_OF_YEAR, daysAgo)
    allSimpleDateFormat().format(calendar.getTime)
  }

  /**
   * 将LocalDateTime转换成Date
   *
   * @param localDateTime
   */
  implicit def localDateTimeToDate(localDateTime: LocalDateTime)(implicit zoneId: ZoneId = default_zoneId) = {
    val zdt = localDateTime.atZone(zoneId)
    Date.from(zdt.toInstant)
  }

  /**
   * 将Date转换成LocalDateTime
   *
   * @param date
   */
  implicit def dateToLocalDateTime(date: Date)(implicit zoneId: ZoneId = default_zoneId) = {
    val instant = date.toInstant
    instant.atZone(zoneId).toLocalDateTime
  }

  /**
   * 将LocalDateTime转化为Long类型
   * 精确时间 毫秒
   *
   * @param localDateTime
   * @param zoneId
   */
  implicit def localDateTimeToLong(localDateTime: Option[LocalDateTime])(implicit zoneId: ZoneId = default_zoneId) = {
    localDateTime match {
      case None => 0L
      case Some(l) => l.atZone(zoneId).toEpochSecond * 1000
    }
  }

  /**
   * 将Long类型转化为LocalDateTime
   *
   * 精确时间 999毫秒
   *
   * @param timestamp
   * @param zoneId
   */
  implicit def longToLocalDateTime(timestamp: Option[Long])(implicit zoneId: ZoneId = default_zoneId) = {
    timestamp match {
      case Some(t) =>
        val instant = new Date(t).toInstant
        Option(instant.atZone(zoneId).toLocalDateTime)
      case None => None
    }
  }

  /**
   * 将LocalDateTime对象转化为精确的时间字符串
   *
   * @param localDateTime
   */
  implicit def localDateTimeToString(localDateTime: Option[LocalDateTime])(implicit locale: Locale = default_local) = {
    localDateTime match {
      case Some(l) => Option(allDateTimeFormatter(locale).format(l))
      case None => None
    }
  }

  /**
   * 将LocalDateTime对象转化为完整的时间字符串，无秒之后的
   *
   * @param localDateTime
   */
  implicit def localDateTimeToStringNoMS(localDateTime: Option[LocalDateTime])(implicit locale: Locale = default_local) = {
    localDateTime match {
      case Some(l) => Option(completeDateTimeFormatter(locale).format(l))
      case None => None
    }
  }

  implicit def toLong(localDateTime: Option[LocalDateTime]) = localDateTimeToLong(localDateTime)

  implicit def toLocalDateTime(long: Option[Long]) = longToLocalDateTime(long)

  implicit def toStr(localDateTime: Option[LocalDateTime]) = localDateTimeToString(localDateTime)

  implicit def toStrNoMS(localDateTime: Option[LocalDateTime]) = localDateTimeToStringNoMS(localDateTime)
}
