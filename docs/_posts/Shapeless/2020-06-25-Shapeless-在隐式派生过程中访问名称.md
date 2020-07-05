---
title: 在隐式派生过程中访问名称
categories:
- Shapeless
tags: [Scala]
description: Scala的泛型编程库Shapeless的入门教程-5
---

* 目录
{:toc}

> 在线电子书 https://dreamylost.gitbook.io/dreamylost/

> shapeless v2.3.2


我们自定义的类型类实例经常不止需要使用字段的类型，有时还希望能够使用字段名称及字段类型名称。在这一章我们将学习通过Generic的变体LabelledGeneric来实现这一点。

开始之前我们先来学习一些理论知识。LabelledGeneric在类型级别使用一些小技巧来提取名称信息。要理解它我们必须先来学习一下字面类型（ literal type）、单例类型（singleton type）、幽灵类型（phantom type）和标记类型（type tagging）。（下文的标记和标签是同一个东西，我的理解是前者偏向动词后者偏向名词）

## 5.1 字面类型

一个Scala值可以有多个类型。例如，“hello”字符串最少有三个类型：String、AnyRef和Any（字符串还有一系列其它类型，像Serializable、Comparable，我们先忽略这些）。具体如下：

```scala
"hello" : String 
// res0: String = hello

"hello" : AnyRef 
// res1: AnyRef = hello

"hello" : Any
// res2: Any = hello
```

有趣的是“hello”同样也是只有一个值的单例类型，与我们定义伴随类得到的单例类型相似。比如定义一个Foo单例类：

```scala
object Foo

Foo 
// res3: Foo.type = Foo$@5c32f469
```

Foo.type的类型是Foo，并且Foo是Foo类型的唯一值。

单例类型附加字面值就被称作字面类型，这在Scala中已经存在了很长世间，但是我们并不经常接触它们，因为编译器默认将字面值转向其最近的非单例类型。例如，以下两个表达式在本质上一致的，第一个"hello"直接转为非单例类型String：

```scala
"hello" 
// res4: String = hello

("hello" : String)
// res5: String = hello
```

shapeless为使用字面类型提供了几个工具。第一，提供了一个名为narrow的宏，实现将一个字面表达式转换为一个类型单例化的字面表达式。下述代码将42这个字面量转换为Int\(42\)类型：

```scala
import shapeless.syntax.singleton._

var x = 42.narrow 
// x: Int(42) = 42
```

注意x变量的类型Int\(42\)，它是字面类型，是Int的子类，该类只有42这一个值，如果我们给x赋其它值的话，编译器会报错。具体如下：

```scala
x = 43 
// <console>:16: error: type mismatch:
//  found   : Int(43)
//  required: Int(42)
//        x = 43
//            ^
```

然而按照普通的继承规则x仍然是一个Int类型，如果对x进行操作将得到一个标准的Int类型。代码如下：

```scala
x + 1 
// res6: Int = 43
```

在Scala中我们能在任何字面值上使用narrow。比如：

```scala
1.narrow 
// res7: Int(1) = 1

true.narrow 
// res8: Boolean(true) = true

"hello".narrow
// res9: String("hello") = hello

// and so on...
```

但是我们并不能在复合表达式上使用narrow，否则会报错。比如：

```scala
math.sqrt(4).narrow 
// <console>:17: error: Expression scala.math.`package`.sqrt(4.0) does 
//    not evaluate to a constant or a stable reference value
//        math.sqrt(4.0).narrow
//                  ^
// <console>:17: error: value narrow is not a member of Double 
// math.sqrt(4.0).narrow
//                ^
```

Scala中的字面类型

截至目前，Scala并没有为字面类型提供专用语法，编译器里存在字面类型但是并不能在代码中直接表达它们。然而，在Lightbend Scala 2.11.9版、2.12.1版以及Typelevel Scala 2.11.8版已经实现了对字面类型的直接支持，在这些Scala版本中我们能直接采用如下方式定义一个数字：

```scala
val theAnswer: 42 = 42
// theAnswer: 42 = 42
```

“42”类型与之前输出中看到的Int\(42\)类型一致，为了向上兼容在输出中你会继续看到Int\(42\)，但是权威的语法应该是“42”。

## 5.2 标记类型与幽灵类型

shapeless使用字面类型来规范样例类字段的名称，通过用字段名称的字面类型标记字段类型的方式来实现这一功能。在学习shapeless如何完成这些之前，我们先来简单实现此功能以此证明这不是什么神奇的事情。假如我们有一个数字：

```scala
val number = 42
```

number变量在编译时和运行时都是Int类型：在运行时，有一个真实值和一系列方法并能被我们调用；在编译时，编译器根据此类型来推断哪些代码片段工作在一起并用来搜索隐式值。

我们能使用幽灵类型标记number变量使得可以在编译时修改它的类型而又无需修改它的运行时行为。幽灵类型是没有运行时语义的类型。比如一个不包含任何方法的特质：

```scala
trait Cherries
```

我们能使用asInstanceOf标记number变量，类型参数传入一个编译时既是Int类型又是Cherries类型而运行时是Int类型的值。代码如下：

```scala
val numCherries = number.asInstanceOf[Int with Cherries]
// numCherries: Int with Cherries = 42
```

shapeless使用这一技巧在ADT中使用字段名称和子类的名称的单例类型来标记字段和子类自身。为了方便shapeless提供了两种标记语法来避免像asInstanceOf这样的不友好代码。

第一种语法是-&gt;&gt;，使用箭头左侧的字面表达式的单例类型来标记箭头右侧的表达式。如下代码实现使用numCherries标记someNumber变量：

```scala
import shapeless.labelled.{KeyTag, FieldType} 
import shapeless.syntax.singleton._

val someNumber = 123

val numCherries = "numCherries" ->> someNumber
// numCherries: Int with shapeless.labelled.KeyTag[String("numCherries
//  "),Int] = 123
```

相当于使用了下面的幽灵类型标记了someNumber：

```scala
KeyTag["numCherries", Int]
```

KeyTag同时包含了字段的名称和类型，这样的结合对在Repr实例中使用隐式解析搜索入口是很有用的。

第二种语法将标签作为一个类型而不是一个字面值，当我们知道要使用什么标签但是不能在代码中写出具体的字面值（即上面代码中的"numCherries"）的时候这是有用的。代码如下：

```scala
import shapeless.labelled.field

field[Cherries](123)
// res11: shapeless.labelled.FieldType[Cherries,Int] = 123
```

FieldType是一个类型别名，它简化了从被标记的类型中提取标记类型K以及基础类型V。FieldType定义如下：

```scala
type FieldType[K, V] = V with KeyTag[K, V]
```

我们即将看到在shapeless的源码中使用同样的方式实现以字段名称和子类名称标记字段和子类自身。

标签在编译期间很纯净，它也没有运行时表示，那么我们如何将它们转换成在运行时可以使用的值？为此shapeless提供了一个叫做Witness的类型类。将Witness和FieldType相结合就可以从一个被标记的字段中提取字段名称，这是不是很有吸引力。代码如下：

```scala
import shapeless.Witness

val numCherries = "numCherries" ->> 123
// numCherries: Int with shapeless.labelled.KeyTag[String("numCherries
//  "),Int] = 123

// Get the tag from a tagged value:


def getFieldName[K, V](value: FieldType[K, V])
    (implicit witness: Witness.Aux[K]): K = witness.value

getFieldName(numCherries) 
// res13: String = numCherries

// Get the untagged type of a tagged value:

def getFieldValue[K, V](value: FieldType[K, V]): V = value

getFieldValue(numCherries) 
// res15: Int = 123
```

如果我们构建一个带标记元素的HList，我们将获得一个具有Map属性的数据结构。我们能在这个过程中通过标记对字段进行处理，这些处理包含操作和替换它们以及保持所有类型和命名信息。在shapeless中称这种结构为“记录（records）”。

### 5.2.1 记录和LabelledGeneric <a id="521-&#x8BB0;&#x5F55;&#x548C;labelledgeneric"></a>

记录是元素被标记的HList类型。如下：

```scala
import shapeless.{HList, ::, HNil}

val garfield = ("cat" ->> "Garfield") :: ("orange" ->> true) :: HNil 
// garfield: shapeless.::[String with shapeless.labelled.KeyTag[String(" 
    cat"),String],shapeless.::[Boolean with shapeless.labelled.KeyTag[ 
    String("orange"),Boolean],shapeless.HNil]] = Garfield :: true :: HNil
```

清晰起见，我们将garfield的类型分行写成如下形式：

```scala
// FieldType["cat",    String]  ::
// FieldType["orange", Boolean] ::
// HNil
```

这里我们不需要深入研究记录，其实它是LabelledGeneric用来进行泛型表示所得到的结果。LabelledGeneric使用具体的ADT（尽管字段名称和类型名称被展示为Symbol类型而不是字符串）实例中相应的字段和类型名称标记乘积或余积类型中的每一个元素。shapeless对记录实现了一系列类似Map的操作，我们将在6.4节中介绍其中的一部分。现在先让我们使用LabelledGeneric派生一些类型类。

## 5.3 使用LabelledGeneric为乘积类型派生实例

我们将实现一个能运行的JSON编码实例，以此展示LabelledGeneric。我们定义一个能把数据值转换为JSON抽象语法树（AST）的JsonEncoder类型类，这与Argonaut、Circe、Play JSON、Spray JSON等其它Scala语言写的JSON类库采用的方式相似。

首先定义JSON对应的数据类型。代码如下：

```scala
sealed trait JsonValue 
case class JsonObject(fields: List[(String, JsonValue)]) extends JsonValue 
case class JsonArray(items: List[JsonValue]) extends JsonValue 
case class JsonString(value: String) extends JsonValue 
case class JsonNumber(value: Double) extends JsonValue 
case class JsonBoolean(value: Boolean) extends JsonValue 
case object JsonNull extends JsonValue
```

然后为将数据值编码为JSON定义一个类型类JsonEncoder。代码如下：

```scala
trait JsonEncoder[A] { 
    def encode(value: A): JsonValue 
}

object JsonEncoder {
    def apply[A](implicit enc: JsonEncoder[A]): JsonEncoder[A] = enc 
}
```

接下来创建几种基础类型的JsonEncoder实例。代码如下：

```scala
def createEncoder[A](func: A => JsonValue): JsonEncoder[A] = 
    new JsonEncoder[A] {
        def encode(value: A): JsonValue = func(value) 
    }

implicit val stringEncoder: JsonEncoder[String] = 
    createEncoder(str => JsonString(str))

implicit val doubleEncoder: JsonEncoder[Double] = 
    createEncoder(num => JsonNumber(num))

implicit val intEncoder: JsonEncoder[Int] = 
    createEncoder(num => JsonNumber(num))

implicit val booleanEncoder: JsonEncoder[Boolean] = 
    createEncoder(bool => JsonBoolean(bool))
```

再通过组合规则创建几个JsonEncoder实例。代码如下：

```scala
implicit def listEncoder[A] 
    (implicit enc: JsonEncoder[A]): JsonEncoder[List[A]] = 
    createEncoder(list => JsonArray(list.map(enc.encode)))

implicit def optionEncoder[A]
    (implicit enc: JsonEncoder[A]): JsonEncoder[Option[A]] = 
    createEncoder(opt => opt.map(enc.encode).getOrElse(JsonNull))
```

理想情况下当我们将ADT编码为JSON的时候在输出的JSON中最好是正确的字段名称。比如在以下例子中的字段name、numCherries、inCone：

```scala
case class IceCream(name: String, numCherries: Int, inCone: Boolean)

val iceCream = IceCream("Sundae", 1, false)


val iceCreamJson: JsonValue = 
    JsonObject(List(
        "name"        -> JsonString("Sundae"), 
        "numCherries" -> JsonNumber(1), 
        "inCone"      -> JsonBoolean(false) 
    ))
```

这就需要靠LabelledGeneric来实现。下面来为IceCream类型创建JsonEncoder实例并看一下它所产生的泛型表示的类型。具体如下：

```scala
import shapeless.LabelledGeneric

val gen = LabelledGeneric[IceCream].to(iceCream) 
// gen: shapeless.::[String with shapeless.labelled.KeyTag[Symbol with 
//    shapeless.tag.Tagged[String("name")],String],shapeless.::[Int with 
//    shapeless.labelled.KeyTag[Symbol with shapeless.tag.Tagged[String(" 
//    numCherries")],Int],shapeless.::[Boolean with shapeless.labelled. 
//    KeyTag[Symbol with shapeless.tag.Tagged[String("inCone")],Boolean], 
//    shapeless.HNil]]] = Sundae :: 1 :: false :: HNil
```

可以看到生成的HList实例的类型是：

```scala
// String with KeyTag[Symbol with Tagged["name"], String] ::
// Int with KeyTag[Symbol with Tagged["numCherries"], Int] ::
// Boolean with KeyTag[Symbol with Tagged["inCone"], Boolean] ::
// HNil
```

这个对象比我们之前看到的要复杂一点，shapeless不是使用字面字符串类型表示字段名称而是使用Symbol with Tagged\["field name"\]类型来表示字段名称。实现的细节不是特别重要，我们仍然能使用Witness和FieldType来提取它们，但是得到的结果是Symbol类型而不是字符串（将来的版本也许会使用字符串作为标签）。

### 5.3.1 为HList派生JsonEncoder类型类实例

下面为HNil和::定义JsonEncoder实例。这些JsonEncoder将生成和操作JsonObject对象，所以我们将创建一个新的JsonObjectEncoder类型来使这些操作变的更加容易。代码如下：

```scala
trait JsonObjectEncoder[A] extends JsonEncoder[A] {
    def encode(value: A): JsonObject 
}

def createObjectEncoder[A](fn: A => JsonObject): JsonObjectEncoder[A] =
    new JsonObjectEncoder[A] { 
        def encode(value: A): JsonObject = 
            fn(value) 
    }
```

为HNil定义相应的JsonObjectEncoder实例就水到渠成。代码如下：

```scala
import shapeless.{HList, ::, HNil, Lazy}

implicit val hnilEncoder: JsonObjectEncoder[HNil] = 
    createObjectEncoder(hnil => JsonObject(Nil))
```

hlistEncoder的定义包含几个部分，我们来一个个的解决它们。首先按照Generic的方式来完成初步定义。代码如下：

```scala
implicit def hlistObjectEncoder[H, T <: HList]( 
    implicit
    hEncoder: Lazy[JsonEncoder[H]],
    tEncoder: JsonObjectEncoder[T] 
): JsonEncoder[H :: T] = ???
```

LabelledGeneric将给我们一个类型被标记的HList，所以先来为FieldType的key类型引入一个新的类型变量。代码如下：

```scala
import shapeless.Witness 
import shapeless.labelled.FieldType

implicit def hlistObjectEncoder[K, H, T <: HList]( 
    implicit
    hEncoder: Lazy[JsonEncoder[H]], 
    tEncoder: JsonObjectEncoder[T]
): JsonObjectEncoder[FieldType[K, H] :: T] = ???
```

在此方法体内需要获取与K相关的值，可以添加一个隐式的Witness来实现这些。代码如下：

```scala
implicit def hlistObjectEncoder[K, H, T <: HList]( 
    implicit 
    witness: Witness.Aux[K], 
    hEncoder: Lazy[JsonEncoder[H]], 
    tEncoder: JsonObjectEncoder[T] 
): JsonObjectEncoder[FieldType[K, H] :: T] = {
    val fieldName = witness.value
    ??? 
}
```

可以使用witness.value得到K的值，但是编译器无法知道会得到什么类型的标签，即无法知道fieldName的类型，由于LabelledGeneric得到的泛型表示的Key类型为Symbol，所以我们将对K设置一个边界并使用symbol.name将它转成字符串。具体如下：

```scala
implicit def hlistObjectEncoder[K <: Symbol, H, T <: HList]( 
    implicit 
    witness: Witness.Aux[K],
    hEncoder: Lazy[JsonEncoder[H]], 
    tEncoder: JsonObjectEncoder[T] 
): JsonObjectEncoder[FieldType[K, H] :: T] = { 
    val fieldName: String = witness.value.name 
    ??? 
}
```

剩下的部分使用在第三章介绍的原则即可完成。具体如下：

```scala
implicit def hlistObjectEncoder[K <: Symbol, H, T <: HList]( 
    implicit
    witness: Witness.Aux[K], 
    hEncoder: Lazy[JsonEncoder[H]], 
    tEncoder: JsonObjectEncoder[T] 
): JsonObjectEncoder[FieldType[K, H] :: T] = { 
    val fieldName: String = witness.value.name 
    createObjectEncoder { hlist => 
        val head = hEncoder.value.encode(hlist.head)
        val tail = tEncoder.encode(hlist.tail) 
        JsonObject((fieldName, head) :: tail.fields) 
    } 
}
```

### 5.3.2 为具体的乘积类型派生JsonEncoder类型类实例

最后我们转回泛型实例，与之前定义的方式相同，唯一不同的是这里使用LabelledGeneric代替Generic。代码如下：

```scala
import shapeless.LabelledGeneric

implicit def genericObjectEncoder[A, H <: HList]( 
    implicit 
    generic: LabelledGeneric.Aux[A, H], 
    hEncoder: Lazy[JsonObjectEncoder[H]] 
): JsonEncoder[A] = 
    createObjectEncoder { value => 
        hEncoder.value.encode(generic.to(value)) 
    }
```

这正是我们需要的，有了这些定义我们就能将任何样例类的实例输出为JSON并在结果中保存字段名称。调用代码如下：

```scala
JsonEncoder[IceCream].encode(iceCream) 

// res14: JsonValue = JsonObject(List((name,JsonString(Sundae)), ( 
//  numCherries,JsonNumber(1.0)), (inCone,JsonBoolean(false))))
```

## 5.4 使用LabelledGeneric为余积类型派生类型类实例

将LabelledGeneric与Coproducts结合使用涉及我们已经介绍的概念的混合。 首先，我们检查一下LabelledGeneric派生的Coproduct类型。 我们将从第3章重新介绍Shape ADT：

```scala
import shapeless.LabelledGeneric

sealed trait Shape
final case class Rectangle(width: Double, height: Double) extends Shape
final case class Circle(radius: Double) extends Shape

LabelledGeneric[Shape].to(Circle(1.0))
// res5: Rectangle with shapeless.labelled.KeyTag[Symbol with 
//  shapeless.tag.Tagged[String("Rectangle")],Rectangle] :+: Circle with 
//  shapeless.labelled.KeyTag[Symbol with shapeless.tag.Tagged[ 
//  String("Circle")],Circle] :+: shapeless.CNil = Inr(Inl(Circle (1.0)))
```

这是更易于阅读的Coproduct类型的格式

```scala
// Rectangle with KeyTag[Symbol with Tagged["Rectangle"], Rectangle] 
//  :+:
// Circle with KeyTag[Symbol with Tagged["Circle"], Circle] 
//  :+:
// CNil
```

如您所见，结果是Shape子类型的余积，每个子类型都用类型名称标记。 我们可以使用此信息为:+:和CNil编写JsonEncoders：

```scala
import shapeless.{Coproduct, :+:, CNil, Inl, Inr, Witness, Lazy} 
import shapeless.labelled.FieldType

implicit val cnilObjectEncoder: JsonObjectEncoder[CNil] = createObjectEncoder(cnil => throw new Exception("Inconceivable!"))

implicit def coproductObjectEncoder[K <: Symbol, H, T <: Coproduct](implicit
  witness: Witness.Aux[K],
  hEncoder: Lazy[JsonEncoder[H]],
  tEncoder: JsonObjectEncoder[T]
): JsonObjectEncoder[FieldType[K, H] :+: T] = {
  val typeName = witness.value.name
  createObjectEncoder {
    case Inl(h) =>
      JsonObject(List(typeName -> hEncoder.value.encode(h)))
    case Inr(t) =>
      tEncoder.encode(t)
  }
}
```

coproductEncoder与hlistEncoder遵循相同的模式。我们有三个类型参数：K表示类型名称，H表示HList的头部（类型）值，T表示结尾的（类型）值。 我们在结果类型中使用FieldType和:+:来声明这三个之间的关系，并使用Witness来访问类型名称的运行时值。 结果是一个包含单个键/值对的对象，键是类型名称，值是结果：

```scala
val shape: Shape = Circle(1.0)

JsonEncoder[Shape].encode(shape)
// res8: JsonValue = JsonObject(List((Circle,JsonObject(List((radius, 
//   JsonNumber(1.0)))))))
```

其他的编码可能需要更多的工作。 例如，我们可以在输出中添加“类型”字段，甚至允许用户配置格式。Sam Halliday’s [spray-json-shapeless](https://github.com/milessabin/spray-json-shapeless)是一个出色的代码库示例，该代码库在可实现的同时还提供了极大的灵活性。

## 5.5 小结

这一章我们讨论了LabelledGeneric，它是Generic类的一个变体，用来在其泛型表示中提取类型名称和字段名称。

LabelledGeneric提取的名称被编码为类型级别的标签，所以我们能在隐式解析的过程中找到它们。这一章我们以讨论字面类型和shapeless在它的标签中如何使用字面类型开始，也介绍了Witness类型类，它用来将字面类型具体化为值。

最后，我们组合LabelledGeneric、字面类型和Witness创建了一个JsonEncoder库，它能在输出中保持字段的名称。

本章的关键点在于这些代码都不是通过运行时反射的方式实现的，是用类型、隐式值和几个shapeless内部的宏来实现的，所以这些代码在运行时速度快、可靠性高。
