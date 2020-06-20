---
title: 自动派生类型类实例
categories:
- Shapeless
tags: [Scala]
description: Scala的泛型编程库Shapeless的入门教程-3
---

* 目录
{:toc}

> 在线电子书 https://dreamylost.gitbook.io/dreamylost/

> shapeless v2.3.2

上一章我们看到了Generic类型类如何实现任意的ADT实例与由HList和Coproduct组成的泛型编码之间进行相互转换。这一章我们将聚焦于第一个真实应用案例：自动派生类型类实例。

## 3.1 概述：类型类（type class）

在深入研究派生类型类实例之前，我们先来浏览一下类型类有哪些重要特征。

类型类是一种从Haskell中借用的编码模式，在Haskell中“类”这个词没有像面向对象编程语言中的实际意义。Scala中使用特质和隐式声明的方式来实现类型类。类型类是带类型参数的特质，是对一些通用功能的分类，使其可以被应用到多种类型。如下创建一个CsvEncoder类型类，实现将A类型实例转换为CSV文件中的一行：

```scala
trait CsvEncoder[A] {
    def encode(value: A): List[String]
}
```

我们为每一个关心的类型实现此类型类实例，如果想让这些实例在我们的作用域内能被自动引入，可以将其放在类型类对应的伴随对象中，或者放到一个独立的库中，由用户手动导入。如下代码实现为Employee对象定义一个隐式CsvEncoder实例——employeeEncoder：

```scala
// Custom data type:
case class Employee(name: String, number: Int, manager: Boolean)

// CsvEncoder instance for the custom data type:
implicit val employeeEncoder: CsvEncoder[Employee] = 
    new CsvEncoder[Employee] {
        def encode(e: Employee): List[String] = 
            List(
                e.name, 
                e.number.toString,
                if(e.manager) "yes" else "no"
            ) 
    }
```

我们使用implicit关键词标记每一个实例，并定义一个或多个入口方法，方法有一个对应类型类的隐式参数。如下代码为CsvEncoder定义一个writeCsv方法：

```scala
def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String = 
    values.map(value => enc.encode(value).mkString(",")).mkString("\n")
```

用一些测试数据来测试writeCsv方法：

```scala
val employees: List[Employee] = List(
    Employee("Bill", 1, true), 
    Employee("Peter", 2, false), 
    Employee("Milton", 3, false) 
)
```

当我们调用writeCsv方法的时候，编译器推断类型参数（type parameter）的类型并在上下文中搜索与其类型一致的隐式CsvEncoder实例。下述代码会自动寻找隐式employeeEncoder对象：

```scala
writeCsv(employees) 
// res4: String =
// Bill,1,yes
// Peter,2,no
// Milton,3,no
```

只要在作用域内有一个与writeCsv的values参数类型一致的隐式CsvEncoder实例，writeCsv方法就能正常运行。如下实现将IceCream实例编码为CSV：

```scala
case class IceCream(name: String, numCherries: Int, inCone: Boolean)

implicit val iceCreamEncoder: CsvEncoder[IceCream] = 
    new CsvEncoder[IceCream] {
        def encode(i: IceCream): List[String] = 
            List(
                i.name, 
                i.numCherries.toString, 
                if(i.inCone) "yes" else "no"
            ) 
    }

val iceCreams: List[IceCream] = List( 
    IceCream("Sundae", 1, false), 
    IceCream("Cornetto", 0, true), 
    IceCream("Banana Split", 0, false) 
)

writeCsv(iceCreams) 
// res7: String =
// Sundae,1,no
// Cornetto,0,yes
// Banana Split,0,no
```

### 3.1.1 解析类型类实例 

类型类虽然灵活，但是需要我们为每一个关心的类型定义对应的隐式实例。幸运的是Scala编译器有一些小窍门可以简化操作，它可以将给定的几套用户自定义规则套接起来解析目标类型类实例。例如假定已经有了A和B类型的CsvEncoder则我们可以为\(A, B\)类型写一个创建CsvEncoder的规则。代码如下：

```scala
implicit def pairEncoder[A, B](
    implicit 
    aEncoder: CsvEncoder[A], 
    bEncoder: CsvEncoder[B] 
): CsvEncoder[(A, B)] = 
    new CsvEncoder[(A, B)] {
        def encode(pair: (A, B)): List[String] = {
            val (a, b) = pair
            aEncoder.encode(a) ++ bEncoder.encode(b) 
        } 
    }
```

当一个隐式方法的所有参数都被标记为implicit时，编译器可以用它作为一个解析规则来从参数的隐式类型类实例创建我们需要的类型类实例。例如，如果调用writeCsv方法时传入一个List\[\(Employee, IceCream\)\]对象，编译器会自动组合pairEncoder、employeeEncoder和iceCreamEncoder三个类型类实例来创建一个CsvEncoder\[\(Employee, IceCream\)\]实例。调用代码如下：

```scala
writeCsv(employees zip iceCreams) 
// res8: String =
// Bill,1,yes,Sundae,1,no
// Peter,2,no,Cornetto,0,yes
// Milton,3,no,Banana Split,0,no
```

编译器为了组合各个实例会对给定的一套隐式值（val）和隐式函数（def）进行搜索，最终生成我们需要的实例，这种特性我们称之为“隐式解析（implicit resolution）”，这是Scala中类型类模式如此强大的原因。

即使隐式解析如此强大，编译器依然不能区分不同的样例类和密封特质，我们仍然需要为各种ADT手动定义实例。shapeless的泛型表示改变了这些，它使得我们可以自由的为任意ADT派生实例。

### 3.1.2 惯用的类型类定义方式

习惯上在Scala中类型类的定义方式包括一个伴随对象，该对象由一些标准的方法组成。代码如下：

```scala
object CsvEncoder { 
    // "Summoner" method
    def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] = enc

    // "Constructor" method
    def instance[A](func: A => List[String]): CsvEncoder[A] = 
        new CsvEncoder[A] {
            def encode(value: A): List[String] = func(value) 
        }

    // Globally visible type class instances  
}
```

apply方法也被称为“召唤员”或者“实现者”，可以为给定的目标类型创建一个类型类实例。如下代码实现创建IceCream的CsvEncoder实例：

```scala
CsvEncoder[IceCream] 
// res9: CsvEncoder[IceCream] = $anon$1@5940ac6a
```

一般情况下apply方法与scala.Predef类中定义的implicitly功能一致。implicitly创建实例方式如下：

```scala
implicitly[CsvEncoder[IceCream]] 
// res10: CsvEncoder[IceCream] = $anon$1@5940ac6a
```

然而我们将在4.2节看到，当我们使用shapeless的时候会碰到一些implicitly不能正确推断出类型的情形，此种情况使用shapeless中定义apply方法的方式依然能正常解决，所以为我们创建的每一个类型类写一个apply方法是值得的。我们也能用shapeless中的特殊的“the”方法（后续详细介绍）来实现。代码如下：

```scala
import shapeless._

the[CsvEncoder[IceCream]] 
// res11: CsvEncoder[IceCream] = $anon$1@5940ac6a
```

在CsvEncoder类的伴随对象中定义的instance方法，有时也称之为纯净方法，能够为创建新的类型类实例提供简洁的语法，去除匿名类语法的不必要的模板代码。如下代码：

```scala
implicit val booleanEncoder: CsvEncoder[Boolean] = 
    new CsvEncoder[Boolean] {
        def encode(b: Boolean): List[String] = 
            if(b) List("yes") else List("no") 
    }
```

可以被简写成如下形式：

```scala
implicit val booleanEncoder: CsvEncoder[Boolean] = 
    instance(b => if(b) List("yes") else List("no"))
```

不幸的是，书中的代码排版限制使得我们不能写有很多方法和实例组成的长单例。因此我们更倾向于离开它们的伴随对象上下文来描述定义。当你阅读或者检查1.2节中的代码库的时候请在脑中牢记这些限制。

## 3.2 为乘积类型（product）派生类型类实例

这一节我们将使用shapeless为乘积类型派生类型类的实例（如样例类），我们将使用以下两个知识点：

1. 如果我们已经有了HList实例的头（head）和尾（tail）的类型类实例，我们就能为整个HList实例派生类型类实例。
2. 如果我们有一个样例类A，一个Generic\[A\]类型的实例gen和gen的Repr的类型类实例，那么我们能将这些组合起来为A创建一个类型类实例。

以CsvEncoder类和IceCream类作为例子，我们可以看到以下几点：

* IceCream类的泛型Repr类型为String :: Int :: Boolean :: HNil。
* 这个Repr由一个String、一个Int、一个Boolean和一个HNil组成，如果我们有了这些类型的CsvEncoder实例，那么我们就能为Repr创建一个CsvEncoder实例。
* 如果我们能派生此Repr的CsvEncoder实例，那么我们就能为IceCream创建一个CsvEncoder实例。

### 3.2.1 为HList派生类型类实例

首先来定义一个实例构造子并为String、Int、Boolean类型创建CsvEncoder实例。代码如下：

```scala
def createEncoder[A](func: A => List[String]): CsvEncoder[A] = 
    new CsvEncoder[A] {
        def encode(value: A): List[String] = func(value) 
    }

implicit val stringEncoder: CsvEncoder[String] = 
    createEncoder(str => List(str))

implicit val intEncoder: CsvEncoder[Int] = 
    createEncoder(num => List(num.toString))

implicit val booleanEncoder: CsvEncoder[Boolean] = 
    createEncoder(bool => List(if(bool) "yes" else "no"))
```

组合以上的代码为HList创建一个CsvEncoder实例，将用到两个规则：一个是为HNil生成CsvEncoder实例，另一个是为::生成CsvEncoder实例。我们来看代码：

```scala
import shapeless.{HList, ::, HNil}


implicit val hnilEncoder: CsvEncoder[HNil] = 
    createEncoder(hnil => Nil)


implicit def hlistEncoder[H, T <: HList]( 
    implicit
    hEncoder: CsvEncoder[H], 
    tEncoder: CsvEncoder[T] 
): CsvEncoder[H :: T] =
    createEncoder {
        case h :: t => 
            hEncoder.encode(h) ++ tEncoder.encode(t) 
    }
```

（上面的createEncoder定义，经过我的测试也可以写为createEncoder\(ht =&gt; hEncoder.encode\(ht.head\) ++ tEncoder.encode\(ht.tail\)\)，二者并没有什么不同，猜测是上述case这样减少了解析的时间）

将上面代码中的5个CsvEncoder实例组合，我们就能为任何包含String、Int、Boolean类型的HList创建一个CsvEncoder实例。代码如下：

```scala
val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = 
    implicitly

reprEncoder.encode("abc" :: 123 :: true :: HNil) 
// res9: List[String] = List(abc, 123, yes)
```

（implicitly会自动搜寻上述中的隐式值创建目标类型类实例）

### 3.2.2 为具体的乘积类派生类型类实例

可以将我们的上述派生规则与Generic实例组合来为IceCream类创建一个CsvEncoder实例。代码如下：

```scala
import shapeless.Generic

implicit val iceCreamEncoder: CsvEncoder[IceCream] = { 
    val gen = Generic[IceCream] 
    val enc = CsvEncoder[gen.Repr] 
    createEncoder(iceCream => enc.encode(gen.to(iceCream))) 
}
```

然后用以下方式调用它：

```scala
writeCsv(iceCreams) 
// res11: String =
// Sundae,1,no
// Cornetto,0,yes
// Banana Split,0,no
```

这个解决方案只是针对IceCream类的，理想情况下我们希望能有一个单一的规则来处理所有的样例类，当然这个样例类要有一个Generic实例以及与之匹配的CsvEncoder实例。让我们一步步的来实现这一点。第一步代码如下：

```scala
implicit def genericEncoder[A](
    implicit
    gen: Generic[A], 
    enc: CsvEncoder[???] 
): CsvEncoder[A] = createEncoder(a => enc.encode(gen.to(a)))
```

我们遇到的第一个问题就是选择一个类型替换上述代码中的???，可以用gen.Repr来替换，但是不幸的是编译器会报错。具体如下:

```scala
implicit def genericEncoder[A](
    implicit 
    gen: Generic[A], 
    enc: CsvEncoder[gen.Repr] 
): CsvEncoder[A] = createEncoder(a => enc.encode(gen.to(a))) 

// <console>:24: error: illegal dependent method type: 
// parameter may only be referenced in a subsequent parameter section
//          gen: Generic[A],
//          ^
```

导致这个问题的原因是作用域，在一个方法中，一个参数不能涉及另一个参数的类型成员。解决这个问题的最好方式是为我们的方法引入一个新的类型参数并将其关联到每一个相关的参数。代码如下：

```scala
implicit def genericEncoder[A, R](
    implicit 
    gen: Generic[A] { type Repr = R }, 
    enc: CsvEncoder[R] 
): CsvEncoder[A] = 
        createEncoder(a => enc.encode(gen.to(a)))
```

我们将在下一章详细介绍这种编码模式，它能够正常编译和工作，并能满足我们用于任何样例类的预期。简单的说，这种定义实现的方式是：给定一个类型A、一个HList的类型R、一个从A映射到R的隐式Generic实例以及一个为类型R准备的CsvEncoder，这样我们就能为A创建一个CsvEncoder实例。

我们现在有了一个能处理任何样例类的完整系统。调用方式如下：

```scala
writeCsv(iceCreams)
```

上述代码以如下方式调用了我们的派生规则：

```scala
writeCsv(iceCreams)(
    genericEncoder(
        Generic[IceCream], 
        hlistEncoder(stringEncoder, 
            hlistEncoder(intEncoder, 
                hlistEncoder(booleanEncoder, hnilEncoder)))))
```

当然编译器可以为许多不同的乘积类型推导出其对应的扩展，但是不用手动写这些代码就能正常运行是不是很棒！

Aux类型别名

像Generic\[A\] { type Repr = L }这种类型的定义非常冗长并且难读，所以shapeless提供了一个类型别名Generic.Aux可以将类型成员变为类型参数。代码如下：

```scala
package shapeless

object Generic {
    type Aux[A, R] = Generic[A] { type Repr = R } 
}
```

使用了别名之后就可以得到一个更易读的CsvEncoder实例定义方式。代码如下：

```scala
implicit def genericEncoder[A, R](
    implicit 
    gen: Generic.Aux[A, R],
    env: CsvEncoder[R] 
): CsvEncoder[A] =
    createEncoder(a => env.encode(gen.to(a)))
```

注意Aux类型别名不会改变任何语义，只是使得代码更易读。这种“Aux模式”在shapeless的基础代码中经常被使用。

### 3.2.3 真正的问题

以上部分看上去像是漂亮的魔法，但是请接受一个重大的现实，如果代码出错，编译器并不擅长告诉我们错误原因。

有两个主要原因可能会导致上述代码编译错误。第一个是编译器不能找到Generic实例。例如对非样例类调用writeCsv方法。下面的代码直接对普通的Foo类进行操作，由于无法获取Generic实例而报错：

```scala
class Foo(bar: String, baz: Int)

writeCsv(List(new Foo("abc", 123)))
// <console>:26: error: could not find implicit value for parameter
//   encoder: CsvEncoder[Foo]
//        writeCsv(List(new Foo("abc", 123)))
//                ^
```

这种情况错误提示很容易理解，如果shapeless不能推断出Generic实例，那就意味着此时处理的数据类型不是一个ADT，而是在代数的某个地方它既不是样例类也不是密封的抽象类型。

编译器不能为HList推断出CsvEncoder实例也是潜在的错误类型，一般情况造成这个错误的原因可能是缺少了ADT中的某个字段对应类型的CsvEncoder实例。例如，如果我们没有为java.util.Date定义隐式CsvEncoder实例，下面的代码就会编译失败：

```scala
import java.util.Date

case class Booking(room: String, date: Date)

writeCsv(List(Booking("Lecture hall", new Date())))
// <console>:28: error: could not find implicit value for parameter
//     encoder: CsvEncoder[Booking]
// writeCsv(List(Booking("Lecture hall", new Date())))
//         ^
```

这个错误提示对我们来说并不是很有帮助，编译器只知道它尝试了大量的隐式组合但是并不能得到正确的CsvEncoder实例。它也不清楚哪一个结合是接近需要的类型的，所以它不能告诉我们失败的具体原因。

并没有什么好办法能解决这个问题，只能逐个排除。在3.5节中我们会讨论编译技巧。至此比较好的一面是隐式解析总在编译时出错，极少有编译成功而运行过程中出现错误的情况。

## 3.3 为余积类型派生类型类实例

上一节我们创建了一套规则，实现了为任何乘积类型自动化的派生一个CsvEncoder实例。这一节我们将对余积类型做同样的事情。我们继续以之前介绍的Shape类型为例：

```scala
sealed trait Shape 
final case class Rectangle(width: Double, height: Double) extends Shape 
final case class Circle(radius: Double) extends Shape
```

Shape的泛型表示类型是Rectangle :+: Circle :+: CNil，3.2.2节中我们为Rectangle类和Circle类定义了乘积类型的CsvEncoder实例。现在我们来用与HList同样的规则为:+:和CNil定义一个泛型CsvEncoder实例。代码如下：

```scala
import shapeless.{Coproduct, :+:, CNil, Inl, Inr}


implicit val cnilEncoder: CsvEncoder[CNil] = 
    createEncoder(cnil => throw new Exception("Inconceivable!"))


implicit def coproductEncoder[H, T <: Coproduct](
    implicit 
    hEncoder: CsvEncoder[H],
    tEncoder: CsvEncoder[T]
): CsvEncoder[H :+: T] = createEncoder { 
    case Inl(h) => hEncoder.encode(h) 
    case Inr(t) => tEncoder.encode(t) 
}
```

需要特别指出以下两点：

1. 余积类型是所有类型的分离（意为其只能是子类的一种，此处即为Rectangle或Circle），为:+:创建CsvEncoder实例必须要考虑编码:+:左边的值还是右边的值，所以我们对:+:的两个子类进行模式匹配，使用Inl匹配左边的值，使用Inr匹配右边的值。
2. 为CNil创建CsvEncoder实例会抛出异常，但是由于我们并不能创建CNil实例，所以抛出异常的代码是一块死代码，我们的代码永远不会运行到那。

如果我们将下述代码放在3.2节中的乘积类型的CsvEncoder实例（genericEncoder）的旁边，那么我们就能序列化一个shape类型组成的列表对象。代码如下：

```scala
val shapes: List[Shape] = List( 
    Rectangle(3.0, 4.0), 
    Circle(1.0)
)

writeCsv(shapes) 
// <console>:26: error: could not find implicit value for parameter encoder: CsvEncoder[Shape]
//        writeCsv(shapes)
//                ^
```

不幸的是，代码报错了，报的错误与3.2.3节中介绍的一样，毫无线索。经过分析可以知道失败的原因是我们并没有为Double类型定义CsvEncoder隐式实例。解决方案很简单，定义一个就可以了。代码如下：

```scala
implicit val doubleEncoder: CsvEncoder[Double] = 
    createEncoder(d => List(d.toString))
```

加上这段代码之后，一切正常运行。结果如下：

```scala
writeCsv(shapes) 
// res7: String =
// 3.0,4.0
// 1.0
```

SI-7046 BUG

Scala编译器有一个叫做[SI-7046](https://issues.scala-lang.org/browse/SI-7046)的BUG，它能引起余积类型的泛型解析失败。造成此BUG的原因是shapeless依赖的部分宏API对我们源代码中定义的顺序非常敏感。所以可以通过调整代码顺序或者重命名文件来避开这个BUG，但是这种解决方案并不是那么稳定和可靠。

如果你正在用Lightbend Scala 2.11.8或之前版本并且余积解析对你失败了，那么你可以考虑升级Lightbend Scala版本到2.11.9或者升级Typelevel Scala版本至 2.11.8。在这些版本中SI-7046均已修复。

### 3.3.1 美化CSV输出

目前我们的CSV编码方式并不实用，Rectangle类型和Circle类型的字段在输出中占据了相同的列。为了解决这个问题我们需要修改CsvEncoder的定义来具体化数据类型的宽度并为输出增加相应的空白字段，如果是Inl则在其输出的右边增加对应的空白字段，如果是Inr则在其左端增加对应的空白字段。1.3（原文为1.2，可能是个错误）节中所介绍的我们的样例仓库中包含了一个完整的CsvEncoder版本解决了这个问题。

## 3.4 为递归类型派生类型类实例

我们来尝试一些更困有挑战的例子——比如操作一个二进制树类型。相关类型定义如下：

```scala
sealed trait Tree[A] 
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A] 
case class Leaf[A](value: A) extends Tree[A]
```

理论上根据前面的代码我们已经能够为Tree类型正确的创建一个CsvEncoder实例。然而，上述代码会造成编译错误：

```scala
CsvEncoder[Tree[Int]]
// <console>:23: error: could not find implicit value for parameter enc: CsvEncoder[Tree[Int]]
//        CsvEncoder[Tree[Int]]
//                  ^
```

造成上述问题的原因是Tree是递归类型，编译器感知到对隐式参数的无限循环调用所以它放弃了寻找并报错。

### 3.4.1 隐式分歧

隐式解析是一个搜索过程，编译器采用探索式的方式来决定问题是否逼近解决方案。如果探索未能对搜索的一个特定分支生成一个良好的结果，编译器会假设该分支不匹配并移动到下一个分支。

探索式是用来避免无限循环的，如果编译器在搜索一个特定分支的时候两次看到同一个目标类型就会放弃并移到下一分支。分析CsvEncode\[Tree\[Int\]\]的展开过程就会看到这种情况。其隐式解析处理过程如下：

```scala
CsvEncoder[Tree[Int]]                            //1                      
CsvEncoder[Branch[Int] :+: Leaf[Int] :+: CNil]   //2
CsvEncoder[Branch[Int]]                          //3
CsvEncoder[Tree[Int] :: Tree[Int] :: HNil]       //4
CsvEncoder[Tree[Int]]                            //5 uh oh
```

可以看到Tree\[A\]在第一行和第五行出现了两次，所以编译器移动到下一搜索分支，最终结果就是找不到一个合适的隐式值。

实际上情况比这复杂的多，如果同一类型构造器被编译器发现两次并且类型参数的复杂性有所增加，编译器就会假设搜索的分支是有分歧的。这对shapeless来说是一个问题，因为像::\[H, T\]和:+:\[H, T\]的类型会在编译器展开泛型表示的时候出现很多次（因为T又是::\[H, T\]或:+:\[H, T\]类型），导致编译器即使坚持使用同样的扩展方法能找到解决方案也会过早放弃。考虑下述类型：

```scala
case class Bar(baz: Int, qux: String)
case class Foo(bar: Bar)
```

Foo类型的展开过程如下：

```scala
CsvEncoder[Foo]                       //1                
CsvEncoder[Bar :: HNil]               //2        
CsvEncoder[Bar]                       //3       
CsvEncoder[Int :: String :: HNil]     //4 uh oh
```

编译器在这个搜索的分支中两次尝试解析CsvEncoder\[::\[H, T\]\]，分别在上述过程中的第2行和第4行，T这个类型参数在第4行比第2行更加复杂，所以编译器假设这个搜索的分支有歧义，会再次移到下一分支，结果就是无法生成一个合适的实例。

### 3.4.2 懒加载（Lazy）

隐式歧义对于像shapeless这样的类库来说将是致命的。幸运的是shapeless中提供了一个叫做Lazy的解决方案。Lazy完成以下两件事情：

1. 通过监控前述的过度防御的收敛探测算法，在编译期间阻止了隐式冲突的发生；
2. 在运行期间推迟隐式参数的评估，从而允许隐式推导自引用。（大概是说自己找自己，隐式搜索）

Lazy的使用方式是以具体的隐式参数的类型为其类型参数。根据以往经验，最好对任何HList或Coprouduct类型的head类型和任何Generic实例的Repr类型参数采用此操作。示例代码如下：

```scala
implicit def hlistEncoder[H, T <: HList](
    implicit
    hEncoder: Lazy[CsvEncoder[H]], // wrap in Lazy
    tEncoder: CsvEncoder[T] 
): CsvEncoder[H :: T] = createEncoder {
    case h :: t => 
        hEncoder.value.encode(h) ++ tEncoder.encode(t) 
}

implicit def coproductEncoder[H, T <: Coproduct](
    implicit
    hEncoder: Lazy[CsvEncoder[H]], // wrap in Lazy
    tEncoder: CsvEncoder[T] 
): CsvEncoder[H :+: T] = createEncoder {
    case Inl(h) => hEncoder.value.encode(h) 
    case Inr(t) => tEncoder.encode(t) 
}

implicit def genericEncoder[A, R](
    implicit
    gen: Generic.Aux[A, R], 
    rEncoder: Lazy[CsvEncoder[R]] // wrap in Lazy
): CsvEncoder[A] = createEncoder { value => 
    rEncoder.value.encode(gen.to(value)) 
}
```

这可以阻止编译器过早放弃，并且使解决方案能够在像Tree类型一样的复杂的或递归的类型中正常起作用。Tree类型的正常调用情况如下：

```scala
CsvEncoder[Tree[Int]] 
// res2: CsvEncoder[Tree[Int]] = $anon$1@2199aca1
```

## 3.5 调试隐式解析

隐式解析中的失败常常令人困惑和沮丧。当隐式参数不起作用时可以用下面的一些技巧。

### 3.5.1 使用implicitly进行调试

当编译器以简单的不能找到一个隐式值而失败的时候我们能做些什么呢？失败可能是由任何一个需要的隐式值的解析造成的。例如如下请况：

```scala
case class Foo(bar: Int, baz: Float)

CsvEncoder[Foo]
// <console>:29: error: could not find implicit value for parameter enc: CsvEncoder[Foo]
//        CsvEncoder[Foo]
//                  ^
//       
```

失败的原因是我们没有为Float定义一个CsvEncoder隐式实例，然而这在应用中可能并不明显，但是能通过分析期望的展开序列来找到错误的原因。在错误代码之前插入一段CsvEncoder.apply或者implicitly的代码来看代码是否能够编译通过。还是以Foo类型的泛型表示为例：

```scala
CsvEncoder[Int :: Float :: HNil] 
// <console>:27: error: could not find implicit value for parameter enc: CsvEncoder[Int :: Float :: shapeless.HNil]
//        CsvEncoder[Int :: Float :: HNil]
//                  ^
```

这段代码不能通过编译，所以需要沿着展开方向继续深入搜索问题，下一步就是尝试分析HList的各个部分。代码如下：

```scala
CsvEncoder[Int]
// <console>:27: error: could not find implicit value for parameter enc: CsvEncoder[Float]
//        CsvEncoder[Float]
//                  ^
```

可以看到Int通过了编译，而Float未能通过，CsvEncoder\[Float\]是我们的展开树的一个叶子，所以可以补充Float的CsvEncoder隐式实例来使代码正常编译。当然如果增加了Float的CsvEncoder隐式实例仍然不能正常编译，那么我们需要继续重复上述过程直到找到下一个错误点。

### 3.5.2 使用reify进行调试

scala.reflect包中提供的reify方法以Scala表达式为参数并返回一个与输入参数相对应的展开树对象（[AST](https://en.wikipedia.org/wiki/Abstract_syntax_tree) 抽象语法树），配有类型注释。示例如下：

```scala
import scala.reflect.runtime.universe._

println(reify(CsvEncoder[Int])) 

// Expr[CsvEncoder[Int]]($read.$iw.$iw.$iw.$iw.CsvEncoder.apply[Int]( 
// $read.$iw.$iw.$iw.$iw.intEncoder))
```

隐式解析过程中的类型推断能提供问题的线索。隐式解析之后，类似上面展开树对象中任何像A或者T那样的依旧存在的自定义类型都表明代码出错了。同样像Any或者Nothing那样的顶层或者底层类型也是代码出错的表现。

## 3.6 小结

这一章我们讨论了如何使用Generic、HList和Coproduct来自动派生类型类实例。并讲述了使用Lazy类型来处理复杂或者递归类型。综合以上知识我们能写出一个通用的派生类型类实例的骨架。步骤如下：

第一步，定义一个类型类：

```scala
trait MyTC[A]
```

第二步，定义一些基础的类型类实例：

```scala
implicit def intInstance: MyTC[Int] = ??? 
implicit def stringInstance: MyTC[String] = ???
implicit def booleanInstance: MyTC[Boolean] = ???
```

第三步，为HList定义类型类实例：

```scala
import shapeless._

implicit def hnilInstance: MyTC[HNil] = ???

implicit def hlistInstance[H, T <: HList](
    implicit
    hInstance: Lazy[MyTC[H]], // wrap in Lazy
    tInstance: MyTC[T] 
): MyTC[H :: T] = ???
```

第四步，如果需要的话为Coproduct定义类型类实例：

```scala
implicit def cnilInstance: MyTC[CNil] = ???

implicit def coproductInstance[H, T <: Coproduct](
    implicit
    hInstance: Lazy[MyTC[H]], // wrap in Lazy
    tInstance: MyTC[T] 
): MyTC[H :+: T] = ???
```

最后，为Generic定义类型类实例：

```scala
implicit def genericInstance[A, R]( 
    implicit
    generic: Generic.Aux[A, R],
    rInstance: Lazy[MyTC[R]] // wrap in Lazy
): MyTC[A] = ???
```

下一章我们将学习一些有用的理论和编程模式来帮助我们采用上述方式编写代码。第五章我们将学习使用Generic的变体来实现类型类派生，它允许我们检查ADT中的字段和类型名称。