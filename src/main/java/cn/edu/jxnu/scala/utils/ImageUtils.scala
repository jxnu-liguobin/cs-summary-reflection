package cn.edu.jxnu.scala.utils

import java.io.ByteArrayInputStream
import java.util.Base64
import java.util.regex.Pattern

import javax.imageio.ImageIO
import play.api.Logger

/**
 * 图片工具
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-14
 */
object ImageUtils {

  private val image_base64_regex = "^(data:image\\/(jpg|jpeg|png);base64,\\W+.*)"
  //需要限定外站，修改此正则
  private val image_url_regex = "https?://.+\\.(jpeg|jpg|png)"

  //先编译
  private val image = Pattern.compile(image_base64_regex)
  private val url = Pattern.compile(image_url_regex)

  /**
   * 验证是否为Image(base64)
   *
   * 不是Image时还需要二次判断是否为URL
   *
   * @return true/false
   */
  def verifyImg = (imageString: String) => {
    try {
      val imageVerify = image.matcher(imageString).matches()
      //验证前缀
      if (imageVerify) {
        val decode = (data: String) => Base64.getDecoder.decode(data.split(',')(1))
        val bytes = decode(imageString)
        //解码并验证图片
        val inputStream = new ByteArrayInputStream(bytes)
        val bufferImage = ImageIO.read(inputStream)
        if (bufferImage.getHeight == 0 || bufferImage.getWidth == 0) false else true
      } else false

    } catch {
      case e: Exception =>
        Logger.info("图片Base64文件验证失败，可能是非法文件！", e.getCause)
        false
    }
  }

  /**
   * 验证是否为URL
   *
   * @return true/false
   */
  def verifyUrl = (imageString: String) => url.matcher(imageString).matches()

  /**
   * 验证是否为URL或Image(base64)
   *
   * @return true/false
   */
  def verifyUrlOrImg = (imageString: String) => verifyImg(imageString) || verifyUrl(imageString)
}