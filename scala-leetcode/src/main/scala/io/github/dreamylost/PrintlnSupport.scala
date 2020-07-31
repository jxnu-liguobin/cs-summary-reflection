/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
  * @author 梦境迷离 dreamylost
  * @since 2020-07-12
  * @version v1.0
  */
trait PrintlnSupport {

  private val gson: Gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create

  def prettyPrinting(any: Any) = gson.toJson(any)

}
