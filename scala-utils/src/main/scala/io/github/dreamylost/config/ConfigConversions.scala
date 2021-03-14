/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost.config

import com.typesafe.config.Config

import scala.collection.JavaConverters._
import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import scala.language.implicitConversions
import scala.util.Failure
import scala.util.Success
import scala.util.Try

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

    implicit def getStringSeqOpt(path: String): Option[Seq[String]] =
      getOpt(config.getStringList(path).asScala.toSeq)

    implicit def getIntSeqOpt(path: String): Option[Seq[Int]] =
      getOpt(config.getIntList(path).asScala.map(_.intValue()).toSeq)

    implicit def getDurationOpt(path: String): Option[Duration] =
      getOpt(config.getDuration(path)).map(d => d.toNanos.nanos)

    implicit def getLongSeqOpt(path: String): Option[Seq[Long]] =
      getOpt(config.getLongList(path).asScala.map(_.longValue()).toSeq)

    implicit def getDoubleSeqOpt(path: String): Option[Seq[Double]] =
      getOpt(config.getDoubleList(path).asScala.map(_.doubleValue()).toSeq)

    private[this] def getOpt[T](f: => T): Option[T] = {
      Try(f) match {
        case Success(value) => Some(value)
        case Failure(_) => None
      }
    }
  }

}
