package cn.edu.jxnu.scala.utils

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, TimeZone}

/**
 * 日期处理
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-14
 */
object DateUtils {

  private final val patternComplete = "yyyy-MM-dd HH:mm:ss"

  private final val patternAll = "yyyy-MM-dd HH:mm:ss SSS"

  private final val patternPart = "yyyy-MM-dd"

  private final lazy val zone = TimeZone.getTimeZone("GMT+8:00")

  /**
   * 时区默认北京的部分解析器
   *
   * @param timeZone
   */
  private def partSimpleDateFormat(timeZone: TimeZone = zone): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(patternPart)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * 时区默认北京的完整解析器
   *
   * @param timeZone
   */
  private def completeSimpleDateFormat(timeZone: TimeZone = zone): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(patternComplete)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * 时区默认北京的精确解析器
   *
   * @param timeZone
   */
  private def allSimpleDateFormat(timeZone: TimeZone = zone): SimpleDateFormat = {
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
  def simpleDateFormat(timeZone: TimeZone = zone, pattern: String): SimpleDateFormat = {
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
}
