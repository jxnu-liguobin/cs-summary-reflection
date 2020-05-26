# 2019-07-15-Scala-Play中序列化操作

title: Play中序列化操作 categories:

* Scala

  tags: \[迁移自简书\]

* 目录 {:toc}
* JsValue可以理解为是Json对象（更准确的其实JsObject才是Json对象， JsValue更像是Json值的表示）
* Ok是Play框架中的响应返回，表示正确响应，可传参数作为响应信息，如Ok\(Json.obj\("Ok" -&gt; "OK"\)\)
* 隐式参数和对象不需要手动传入，只要它们在当前上下文是可见的即可自动引用，一般在伴生对象或包对象中。
* Scala无真正的基本类型，这里说的基本是数值，数值的数组，数值的集合（集合元素也必须数值的）
* 对于不确定的情况需要尽量使用带Opt结尾的方法，否则对None值调用get会报错。
* Scala把所有Java基本类型都映射成了Scala对象，Scala中一切都是对象，都能进行函数调用，其中有的比如String、Class等等是直接使用了Java的String、Class

下面对于给定数据进行说明

```scala
 val queryParamsRequest: Map[String, Seq[String]] = Map("clickId" -> Seq("1", "2", "3", "4"), "currTime" -> Seq("5", "4", "3", "2"))
```

`toJson`可以对简单类型数据直接序列化，而不需任何其他操作

```scala
 val jsValue = Json.toJson(queryParamsRequest)
```

得到的是`JsValue`

查看`toJson`源码，如下

```scala
 def toJson[T](o: T)(implicit tjs: Writes[T]): JsValue = tjs.writes(o)
```

该方法使用隐式参数`Writes`来完成写入。实际上这里是因为Scala默认定义了一些用于基础类型的`Writes`

```scala
trait DefaultWrites extends LowPriorityWrites {
import scala.language.implicitConversions

/**
 * Serializer for Int types.
 */
implicit object IntWrites extends Writes[Int] {
  def writes(o: Int) = JsNumber(o)
  }
......
implicit object DoubleWrites extends Writes[Double] {
  def writes(o: Double) = JsNumber(o)
}
...... 此处省略，还有数组等，
```

其中`JsNumber`是`JsValue`的子类

```scala
case class JsNumber(value: BigDecimal) extends JsValue
```

也就是默认所有的基本类型即使不传入任何参数也是可以使用`toJson`方法将其转换为`JsValue`。反之，如果有不是基本类型的，则需要自己提供隐式的`Writes`，假设有class如下

```scala
case class AppConfig(title: String, baseUri: URI)
```

URI是`java.net.URI`类 那现在肯定无法自动序列化为json，此时可以自定义一个隐式参数如下

```scala
implicit val appConfigWrites = new Writes[AppConfig] {
      def writes(appConfig: AppConfig) = Json.obj(
          "title" -> appConfig.title,
          "baseUri" -> appConfig.baseUri.toString 
          //对于不能转换为String的这里要增加 额外处理
      )
  }
```

PS:如果想从给定的json字符串中读取出AppConfig对象则需要`Reads`，这是反序列化，可能会出错，一般使用`Option`

```scala
implicit val appConfigReads: Reads[AppConfig] = (
    (JsPath \ "title").read[String] and
      (JsPath \ "baseUri").read[String].map(x => new URI((x)))
    ) (AppConfig.apply _)
```

你也可以同时定义序列化和反序列化，有了`Writes`对象或`Format`对象实现了`writes`方法，就可以在网络中将DTO对象进行传输。（以Json字符串传输）

```scala
new Format[AppConfig] {
    override def writes(o: AppConfig): JsValue = ???
//实现这两个方法。代码和上面的类似。如果是DTO对象，一般不实现Reads，可以在
//被调用时就抛出异常
    override def reads(json: JsValue): JsResult[AppConfig] = ???
}
```

如果想直接写入Play的Ok\(\)中，还需要增加一个`Writeable`隐式对象，如下

```scala
implicit def jsonWritable[A](implicit writes: Writes[A], codec: play.api.mvc.Codec): Writeable[A] = {
    implicit val contentType = ContentTypeOf[A](Some(ContentTypes.JSON))
    val transform = Writeable.writeableOf_JsValue.transform compose writes.writes
    Writeable(transform)
  }
```

现在接着上面序列化Map，得到JsValue后可以转换为String

```scala
val jsonQueryParams = Json.stringify(jsValue)
println("序列化成字符串 => " + jsonQueryParams)
```

如果你想字符串是易于观看的，可以选择格式化展现，如下

```scala
val prettyPrint = Json.prettyPrint(jsValue)
println("JsValue格式化成字符串 => " + prettyPrint)
```

这里格式化使用的是JacksonJson 如果想从给定的json字符串反序列成JsValue，可以执行解析操作，如下

```scala
val json = Json.parse(jsonQueryParams)
println("反序列的Json值 => " + json)
```

这里从字符串解析使用的也是JacksonJson，最后想将字符串解析成Map

```scala
val queryParamResult = json.validate[Map[String, Seq[String]]].getOrElse(Map())
```

不是确定类型的情况下最好使用 `validateOpt` 如果想打印map的key和value可以使用for

```scala
for ((k, v) <- queryParamResult) {
   println(k, v)
}
```

上面序列化和反序列化Map的结果

```text
序列化成字符串 => {"clickId":["1","2","3","4"],"currTime":["5","4","3","2"]}
JsValue格式化成字符串 => {
  "clickId" : [ "1", "2", "3", "4" ],
  "currTime" : [ "5", "4", "3", "2" ]
}
反序列的Json值 => {"clickId":["1","2","3","4"],"currTime":["5","4","3","2"]}
```

