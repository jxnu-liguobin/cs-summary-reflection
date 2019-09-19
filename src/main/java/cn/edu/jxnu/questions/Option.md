* 目录
{:toc}

### Option类型的使用
```scala
/** *
   * 下面是常用的Option类型的操作
   */
  //有一个Option类型，无法估计Option值不存在时，是否有默认类型，但是可以预估到经过函数F后的默认类型，这种情况很适合使用fold（play的表单验证）
  val opt = Option("ssss")
  //不清楚opt的默认值，但是知道opt经过F函数后一定是true或者false
  val resOpt = opt.fold(false)(_ != "sss")
  println(resOpt) //opt为空时返回false，否则返回_ != "sss"的值，这里返回true

  //如果可以预估Option的默认值更加简单，这个opt就不会有空指针。而直接使用get则不行
  val resGetOrElse = opt.getOrElse("-1") != "sss"
  println(resGetOrElse) //默认是 -1与sss的比较，也是true

  //如果有默认值与当前类型是相同的Option类型，则可以使用orElse，这种一般用在三个Option时,需要与getOrElse连用： A orElse B getOrElse "-1"
  val resOrElse = opt.orElse(Option("-1")).getOrElse("-2") != "sss"
  println(resOrElse) //默认是 -1或-2与sss的比较，也是true

结果
true
true
true
```
查看fold方法源码
```scala
  @inline final def fold[B](ifEmpty: => B)(f: A => B): B =
    if (isEmpty) ifEmpty else f(this.get)
```
这是一个内联的函数，第一个参数是如果为空则返回B，也就是本例的false，这种情况下第二个函数也必须反回的是Boolean，而 Scala的 ==  != 实际已经具备equals的功能，能直接对字符串进行比较。

### 去除Json中的空值
当我们在Scala中大量使用Option类型时，有时候需要将这些空值过滤掉
这里举例使用的是基于play-json库序列化的json，其他雷同。
```scala
 //测试类
  case class TestJson(id: String, name: Option[String])

  object TestJson {
    implicit val writer = new Writes[TestJson] {
      override def writes(o: TestJson): JsValue = {
        val fields = Json.obj(
          "id" -> o.id,
          "name" -> o.name
        ).fields.filterNot(_._2 == JsNull)
        Json.obj().copy(fields.toMap)
      }
    }
  }

  //去掉空值
  println(Json.toJson(TestJson("iddd", None)))
```
这里使用重写writer类型，自定义序列化的格式。filterNot JsNull会去掉所有序列化之前字段为null的属性。
如果你对顺序有要求，可能你需要下面这个方法。这个方法我将它封装为隐式类，可以在任何地方调用。
```scala
  /**
   * {{{
   *   Json.obj().removeNull
   * }}}
   *
   * @param jsValue
   */
  implicit class filterJsonWrapper(jsValue: JsObject) {
    //去除json中的空值，并保证顺序
    def removeNull = {
      val fields = jsValue.fields.filterNot(_._2 == JsNull)
      val maps = new mutable.LinkedHashMap[String, JsValue]()
      fields.map(field => maps.+=(field._1 -> field._2))
      Json.obj().copy(maps)
    }
  }
```
正如我在秒杀项目中的使用
```scala
//引入
 Json.obj(
      "seckillStatus" -> goodsDetailPresenter.seckillStatus,
      "remainSeconds" -> goodsDetailPresenter.remainSeconds,
      "user" -> user.removeNull,
      "goodsVo" -> goodsVo.removeNull
    )
```
这种无法处理嵌套的Json，如需要则要在内嵌Json中也调用removeNull，或者在implicit class filterJsonWrapper中增加一个处理嵌套的函数。
当然这些处理只是自己的总结，可能不是最好的写法，毕竟这是Scala。这些仅供参考。
本人另一篇 [Option 的 fold 方法 使用注意点
](https://www.jianshu.com/p/1037a1dc6d6c)


