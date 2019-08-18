package cn.edu.jxnu.utils.other

import java.security.MessageDigest

import org.apache.commons.codec.binary.Base64

/**
 * 简单的实现加密（编码）算法
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

  /** 编码Base64
   *
   * {{{
   *      BASE64 转码过程例子： 3*8=4*6 内存1个字节占8位 转前： s 1 3 先转成ascii：对应 115 49 51 2进制：
   *      01110011 00110001 00110011 6个一组（4组） 011100110011000100110011 然后才有后面的 011100
   *     110011 000100 110011 然后计算机是8位，不够自动就补两个高位0，所以高位补0 最终为 00011100
   *     00110011 00000100 00110011 得到 28 51 4 51 查对下照表 c z E z
   * }}}
   *
   * @param encode 待编码的字符串
   * @return
   */
  def encode(encode: String): String = {
    var b = encode.getBytes
    val base64 = new Base64
    b = base64.encode(b)
    new String(b)
  }

  /** 解码Base64
   *
   * @param decode 已被编码的字符串
   */
  def decode(decode: String): String = {
    var b = decode.getBytes
    val base64 = new Base64
    b = base64.decode(b)
    new String(b)
  }
}
