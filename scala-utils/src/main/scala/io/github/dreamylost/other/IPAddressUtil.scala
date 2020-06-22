package io.github.dreamylost.other

/**
  * @author 梦境迷离
  * @since 2019-09-04
  * @version v1.0
  */
object IPAddressUtil {

  val ipv6Regex =
    """(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|
      |([0-9a-fA-F]{1,4}:){1,7}:|
      |([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|
      |([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|
      |([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|
      |([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|
      |([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|
      |[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|
      |:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|
      |::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|
      |([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))""".r

  val min = 0
  val aBegin = getIPNum("10.0.0.0")
  val aEnd = getIPNum("10.255.255.255")
  val bBegin = getIPNum("172.16.0.0")
  val bEnd = getIPNum("172.31.255.255")
  val cBegin = getIPNum("192.168.0.0")
  val cEnd = getIPNum("192.168.255.255")
  val max = getIPNum("255.255.255.255")

  /**
    * if ip is IPV6 or private ipV4, It's invalid
    *
    * @param ipAddress ip 地址
    * @return
    */
  def isValidIP(ipAddress: String): Boolean = {

    if (ipv6Regex.findFirstIn(ipAddress).isEmpty) {
      val ipNum = getIPNum(ipAddress)

      /**
        * 取A类B类的IP
        * A类  10.0.0.0 - 10.255.255.255
        * B类  172.16.0.0 - 172.31.255.255
        * C类  192.168.0.0 - 192.168.255.255
        * 当然，还有127这个网段是环回地址
        */
      ipNum > min && !isInner(ipNum, aBegin, aEnd) && !isInner(ipNum, bBegin, bEnd) &&
      !isInner(ipNum, cBegin, cEnd) && !ipAddress.equals("127.0.0.1") && ipNum < max
    } else {
      false
    }
  }

  /**
    * @param ipAddress ip地址字符串
    * @return
    */
  def getIPNum(ipAddress: String): Long = {
    val nums = ipAddress.split("\\.")
    val a = nums(0).toLong
    val b = nums(1).toLong
    val c = nums(2).toLong
    val d = nums(3).toLong

    a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d
  }

  def isInner(userIP: Long, begin: Long, end: Long): Boolean = (userIP >= begin) && (userIP <= end)

}
