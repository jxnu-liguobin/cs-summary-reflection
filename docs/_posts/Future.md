---
title: Future
categories:
- Scala
---

Scala Future 异步工具类解读
---

将任务提交到线程池，来达到异步执行到效果

流程解析
---

先看一个 Future 用法的 例子
```scala
Future { println("start future") }
```
在 scala 的语法里，以下三种写法作用一样
```scala
Future {}
Future ()
Future.apply()
```
进入到 Future.apply 方法
```scala
def apply[T](body: =>T)(implicit @deprecatedName('execctx) executor: ExecutionContext): Future[T] =
    unit.map(_ => body)
```
unit 为
```scala
val unit: Future[Unit] = successful(())
```
再看 successful
```scala
def successful[T](result: T): Future[T] = Promise.successful(result).future
```
进入到 Promise.successful()
```scala
def successful[T](result: T): Promise[T] = fromTry(Success(result))

def fromTry[T](result: Try[T]): Promise[T] = impl.Promise.KeptPromise[T](result)
```
进入到 Promise.KeptPromise apply方法
```scala
def apply[T](result: Try[T]): scala.concurrent.Promise[T] =
  resolveTry(result) match {
    case s @ Success(_) => new Successful(s)
    case f @ Failure(_) => new Failed(f)
  }
```
最终构造了一个 Kept 对象 Kept 是 Promise 的子类，Promise 是 Future 的子类
```scala
Kept[T] extends Promise[T] 

Promise[T] extends scala.concurrent.Promise[T] with scala.concurrent.Future[T]
```
执行任务 body: => T
我们重回 Future.apply, 看看 unit.map(_ => body) 的逻辑
```scala
def map[S](f: T => S)(implicit executor: ExecutionContext): Future[S] = transform(_ map f)
```
transform 是一个抽象方法，所以我们去看子类 Promise.transform 的实现
```scala
import scala.concurrent.Future
import scala.concurrent.impl.Promise.DefaultPromise

override def transform[S](f: Try[T] => Try[S])(implicit executor: ExecutionContext): Future[S] = {
  val p = new DefaultPromise[S]() 
  onComplete { result => p.complete(try f(result) catch { case NonFatal(t) => Failure(t) }) }
  p.future
}
```
根据 DefaultPromise 类的定义
```scala
class DefaultPromise[T] extends AtomicReference[AnyRef](Nil) with Promise[T]

val p = new DefaultPromise[S]() 
//实际上是初始化了一个 AtomicReference[AnyRef](空的list)
Nil = List.empty
```
接着我们看 DefaultPromise.onComplete 的实现, DefaultPromise 是 AtomicReference 无锁的对象引用的子类
```scala
final def onComplete[U](func: Try[T] => U)(implicit executor: ExecutionContext): Unit = dispatchOrAddCallback(new CallbackRunnable[T](executor.prepare(), func))

@tailrec
private def dispatchOrAddCallback(runnable: CallbackRunnable[T]): Unit = {
  get() match {
    case r: Try[_]          => runnable.executeWithValue(r.asInstanceOf[Try[T]])
    case dp: DefaultPromise[_] => compressedRoot(dp).dispatchOrAddCallback(runnable)
    case listeners: List[_] => if (compareAndSet(listeners, runnable :: listeners)) ()
                                else dispatchOrAddCallback(runnable)
  }
}
```
最终 runable.executeWithValue 执行，也就是 CallbackRunnable.executeWithValue 提交任务到线程池去执行
```scala
private final class CallbackRunnable[T](val executor: ExecutionContext, val onComplete: Try[T] => Any) extends Runnable with OnCompleteRunnable {
  // must be filled in before running it
  var value: Try[T] = null

  override def run() = {
    require(value ne null) // must set value to non-null before running!
    try onComplete(value) catch { case NonFatal(e) => executor reportFailure e }
  }

  def executeWithValue(v: Try[T]): Unit = {
    require(value eq null) // can't complete it twice
    value = v
    // Note that we cannot prepare the ExecutionContext at this point, since we might
    // already be running on a different thread!
    try executor.execute(this) catch { case NonFatal(t) => executor reportFailure t }
  }
}
```
看callbackRunnalbe的定义, 函数onComplete的实现为 Promise.transform 的实现中的代码
```scala
result => p.complete(try f(result) catch { case NonFatal(t) => Failure(t) })
```
最后 p.complete 会返回一个 Promise 对象也就是 Future对象本身
```scala
def complete(result: Try[T]): this.type =
    if (tryComplete(result)) this else throw new IllegalStateException("Promise already completed.")
```
总结
---

创建Future 交给 Promise 对象管理，并将线程池引用传入到 Promise 对象中
Promise 对 Future 里的任务进行调度执行


[reference from](https://wtog.github.io/2019/03/30/scala-future.html)
