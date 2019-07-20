package cn.edu.jxnu.scala.utils

import java.security.MessageDigest

/**
 * 简单的实现加密算法
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-20
 */
object EncrypUtils {

  /** 获取密文
   *
   * @param content   待加密字符
   * @param algorithm 加密算法
   * @return
   */
  def encryption(content: String, algorithm: String): String = {
    val hexDigits = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    val btInput = content.getBytes("UTF-8")
    // 获得algorithm摘要算法的MessageDigest对象，提供信息摘要功能 MessageDigest--消息摘要
    val mdInst = MessageDigest.getInstance(algorithm)
    // 使用输入的字节更新摘要
    mdInst.update(btInput)
    // 获得密文
    val code = mdInst.digest
    // 把密文转换成十六进制的字符串形式
    val strChar = new Array[Char](code.length * 2)
    var k = 0
    for (i <- 0 until code.length) {
      val byte1 = code(i)
      strChar(k) = hexDigits(byte1 >>> 4 & 0xf) // 同理得到高四位
      k += 1
      strChar(k) = hexDigits(byte1 & 0xf) // 任何数字与0xf相与，得到低四位
      k += 1
    }
    new String(strChar)
  }
}
