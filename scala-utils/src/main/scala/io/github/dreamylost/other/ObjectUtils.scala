package io.github.dreamylost.other

/**
  * 对象工具
  *
 * @author 梦境迷离
  * @time 2019-08-18
  * @version v2.0
  */
object ObjectUtils {

  /**
    * 获取对象的所有属性转换为Map
    *
   * @param obj
    * @param excludes
    * @return
    */
  def properties(obj: Any, excludes: String*): Map[String, Any] = {
    val fields = obj.getClass.getDeclaredFields.map { field ⇒
      field.setAccessible(true)
      field.getName → field.get(obj)
    }.toMap
    if (excludes.nonEmpty) {
      fields.filterNot(entry ⇒ excludes.contains(entry._1))
    } else {
      fields
    }
  }

}
