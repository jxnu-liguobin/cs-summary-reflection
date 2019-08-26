package cn.edu.jxnu.utils.other

import cn.edu.jxnu.utils.other.DateUtils._

/**
 * 自定义隐式转化
 *
 * @author 梦境迷离
 * @since 2019-08-26
 * @version v1.0
 */
object CustomConversions {

  import java.time.LocalDateTime

  implicit class LocalDateTimeOpt2StringOPt(localDateTime: Option[LocalDateTime]) {
    def toStrOpt = localDateTimeToString(localDateTime)

    def toStrNoMsOpt = localDateTimeToStringNoMS(localDateTime) //无毫秒
  }

  implicit class LongOpt2LocalDateTimeOpt(timestamp: Option[Long]) {
    def toLocalDateTimeOpt = longToLocalDateTime(timestamp)
  }

  implicit class LocalDateTimeOpt2Long(localDateTime: Option[LocalDateTime]) {
    def toLong = localDateTimeToLong(localDateTime) //default = 0L
  }

  //Long转Hash
  //  implicit class id2HashWrapper (any: AnyVal) {
  //    def toHash = MD5Utils.md5(any + "")
  //  }
  //
  //  //可能为空的Long转Hash
  //  implicit class idOpt2HashWrapper (long: Option[AnyVal]) {
  //    def toHashOpt = {
  //      long match {
  //        case Some(l) => MD5Utils.md5(l + "")
  //        case _ => throw GlobalException(CodeMsg.HASH_ERROR)
  //      }
  //    }
  //  }

}