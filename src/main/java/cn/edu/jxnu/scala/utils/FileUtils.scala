package cn.edu.jxnu.scala.utils

import java.io._

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source
import scala.language.reflectiveCalls
import scala.util.control.Exception._

/**
 * 文件处理工具
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-15
 */
object FileUtils {

  /**
   * 必须有close方法才能使用
   */
  type Closable = {
    def close(): Unit
  }

  /** 使用贷出模式的调用方不需要处理关闭资源等操作
   *
   * @param resource 资源
   * @param f        处理函数
   * @tparam R 资源类型
   * @tparam T 返回类型
   * @return 返回T类型
   */
  def usingIgnore[R <: Closable, T](resource: => R)(f: R => T): T = {
    try {
      f(resource)
    } finally {
      ignoring(classOf[Throwable]) apply {
        resource.close()
      }
    }
  }

  /** 不忽略异常
   *
   * @param resource 资源
   * @param f        处理函数
   * @tparam R 资源类型
   * @tparam T 返回类型
   * @return 返回T类型
   */
  def using[R <: Closable, T](resource: => R)(f: R => T): T = {
    try {
      f(resource)
    } finally {
      if (resource != null) {
        resource.close()
      }
    }
  }

  /** 基于Future的贷出模式
   *
   * @param resource 资源
   * @param f        处理函数
   * @param ec       线程上下文
   * @tparam R 资源类型
   * @tparam T 返回类型
   * @return 返回T类型
   */
  def usingFuture[R <: Closable, T](resource: R)(f: R => Future[T])(implicit ec: ExecutionContext): Future[T] = {
    f(resource) andThen { case _ => resource.close() } //任何时候都将关闭
  }

  /** 文件写入指定内容
   * 字符流
   *
   * @param file    文件对象
   * @param content 待写入内容
   */
  def writer(file: File, content: String): Unit = {
    using(new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
      _.write(content)
    }
  }

  /** 文件写入指定内容
   * 字节流
   *
   * @param file    文件目录
   * @param content 待写入字节数组
   */
  def writer(file: String, content: Array[Byte]): Unit = {
    using(new BufferedOutputStream(new FileOutputStream(file))) {
      _.write(content)
    }
  }

  /** 文件读取为字节数组并转化为字符
   * 缓冲流
   *
   * @param file    文件对象
   * @param charset 期望编码
   * @return 字符串
   */
  def reader(file: File, charset: String): String = {
    //buffer默认8192
    val array: Array[Byte] = using(new BufferedInputStream(new FileInputStream(file))) {
      bf => Stream.continually(bf.read).takeWhile(-1 !=).map(_.toByte).toArray
    }
    new String(array, charset)
  }

  /** 文件读取并转化为字符
   * 缓冲流
   *
   * @param file    文件名
   * @param charset 期望编码
   * @return 字符串
   */
  def reader(file: String, charset: String): String = {
    val sb = new StringBuilder()
    using(Source.fromInputStream(new BufferedInputStream(new FileInputStream(file)), charset)) {
      lines => lines.getLines().foreach(sb.append)
    }
    sb.toString()
  }

  /** 文件读取并转化为字节数组
   * 缓冲流
   *
   * @param file 文件对象
   * @return 字节数组
   */
  def reader(file: File): Array[Byte] = {
    //buffer默认8192
    using(new BufferedInputStream(new FileInputStream(file))) {
      bf => Stream.continually(bf.read).takeWhile(-1 !=).map(_.toByte).toArray
    }
  }
}
