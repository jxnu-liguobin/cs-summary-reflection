/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.other

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

/**
  * 消除嵌套的Seq[Future]
  *
  * @author 梦境迷离
  * @since 2019-09-04
  * @version v1.0
  */
object LiftedUtil {

  /** {{{
    * use for  Seq[Future[Option[T]]] => Future[Seq[T]]
    * }}}
    *
    * @param futures
    * @tparam T
    * @return
    */
  def futureOptionListToFuture[T](
      futures: Seq[Future[Option[T]]]
  )(implicit executionContext: ExecutionContext) =
    Future.sequence(futures).map(_.flatten)
}
