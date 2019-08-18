package cn.edu.jxnu.utils.config

import com.typesafe.config.Config

import scala.collection.JavaConverters._
import scala.concurrent.duration.{Duration, _}
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

/**
 * 将typesafe config组件获取的值转化为Option值
 *
 * @author 梦境迷离
 * @time 2019-08-18
 * @version v1.0
 */
object ConfigConversions {

  implicit class ConfigWrapper(config: Config) {

    implicit def getIntOpt(path: String): Option[Int] = getOpt(config.getInt(path))

    implicit def getLongOpt(path: String): Option[Long] = getOpt(config.getLong(path))

    implicit def getDoubleOpt(path: String): Option[Double] = getOpt(config.getDouble(path))

    implicit def getStringOpt(path: String): Option[String] = getOpt(config.getString(path))

    implicit def getConfigOpt(path: String): Option[Config] = getOpt(config.getConfig(path))

    implicit def getBooleanOpt(path: String): Option[Boolean] = getOpt(config.getBoolean(path))

    implicit def getStringSeqOpt(path: String): Option[Seq[String]] = getOpt(config.getStringList(path).asScala)

    implicit def getIntSeqOpt(path: String): Option[Seq[Int]] = getOpt(config.getIntList(path).asScala.map(_.intValue()))

    implicit def getDurationOpt(path: String): Option[Duration] = getOpt(config.getDuration(path)).map(d => d.toNanos.nanos)

    implicit def getLongSeqOpt(path: String): Option[Seq[Long]] = getOpt(config.getLongList(path).asScala.map(_.longValue()))

    implicit def getDoubleSeqOpt(path: String): Option[Seq[Double]] = getOpt(config.getDoubleList(path).asScala.map(_.doubleValue()))

    private[this] def getOpt[T](f: => T): Option[T] = {
      Try(f) match {
        case Success(value) => Some(value)
        case Failure(_) => None
      }
    }
  }

}
