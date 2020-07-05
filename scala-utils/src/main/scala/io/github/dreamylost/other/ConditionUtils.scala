/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.other

import scala.concurrent.Future

/**
  * 条件处理工具
  *
  * @author 梦境迷离
  * @version 1.0, 2019-07-15
  */
object ConditionUtils {

  /** for推断中抛出异常
    *
    * {{{
    * for{
    *       _ <- failCondition(user.isEmpty, InternalErrorException(s"user not found ${p.userId}"))
    * }
    * }}}
    *
    * @param condition
    * @param PlayException
    * @return
    */
  def failCondition(condition: Boolean, PlayException: => Exception): Future[Unit] =
    if (condition) Future.failed(PlayException) else Future.successful(())

}
