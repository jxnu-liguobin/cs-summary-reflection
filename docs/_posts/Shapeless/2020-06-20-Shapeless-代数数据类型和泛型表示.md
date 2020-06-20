---
title: 代数数据类型和泛型表示
categories:
- Shapeless
tags: [Scala]
description: Scala的泛型编程库Shapeless的入门教程-2
---

* 目录
{:toc}

> 在线电子书 https://dreamylost.gitbook.io/dreamylost/

> shapeless v2.3.2

泛型编程主要思想是指用少量的泛型代码解决多种类型问题，为此shapeless提供了两种工具：

1. 能够在类型级别进行检查（inspected）、访问（traversed）、修改（manipulated）的一系列泛型数据类型；
2. 能够在代数数据类型（简称ADT，Scala中的样例类和密封特质）和泛型表示之间的自动映射。

我们先简单介绍一下代数数据类型（ADT）相关理论以及为什么Scala开发者对它如此熟悉，并以此来开始本章。我们还会讲解shapeless如何使用泛型表示，之后讨论如何使它们与实际的ADT相互映射。最后，我们会介绍能实现上述功能的Generic类型类，并介绍使用Generic进行不同类型之间的相互转换，并以此为简单示例来结束本章。

## 2.1 概述：ADT

不用纠结代数数据类型（Algebraic data type）与抽象数据类型（abstract data type）的简写（ADT）相同，抽象数据类型是另一个计算机术语，它与代数数据类型不同。代数数据类型（ADT，下文简写为ADT）是面向函数编程的概念，它名字奇特但本质很简单，就是我们习惯上用“和”以及“或”来表达数据的方式。例如：

* 一个图形的形状是矩形或圆形
* 矩形有宽和高两个属性
* 圆有一个半径属性

在专有名词ADT中，我们称“矩形和圆形”中的这种“和”为乘积（product）（个人觉得此处他应该是指“宽和高”中的和），“一个图形的形状是矩形或圆形”这种“或”为余积（coproduct）。Scala中通常用样例类代表乘积类型，用密封特质代表余积类型。例如下述中Rectangle和Circle都是乘积类型，而Shape是余积类型：

```scala
sealed trait Shape 
final case class Rectangle(width: Double, height: Double) extends Shape 
final case class Circle(radius: Double) extends Shape

val rect: Shape = Rectangle(3.0, 4.0) 
val circ: Shape = Circle(1.0)
```

ADT之美就在于它是类型安全的。编译器能够完全理解我们定义的代号（alberas的意思是：像矩形和圆这种我们定义的符号；以及这些符号的操作规则或编码方法规则），所以这能帮助我们对自定义类型写出完整的、类型正确的方法。如下代码能正确处理传入的shape类型并计算其面积：

```scala
def area(shape: Shape): Double = 
    shape match {
        case Rectangle(w, h) => w * h 
        case Circle(r) => math.Pi * r * r 
    }

area(rect) 
// res1: Double = 12.0

area(circ) 
// res5: Double = 3.141592653589793
```

### 2.1.1 ADT的其它编码方式

虽然在Scala中编码方式不止一种，但密封特质和样例类毋庸置疑是ADT的最方便的编码方式。比如Scala标准库中就提供了用元组（Tuple）表式的泛型乘积类型以及用或（Either）表示的泛型余积类型。我们可以用这种方式来重写上面定义的Shape类。代码如下：

```scala
type Rectangle2 = (Double, Double)
type Circle2 = Double 
type Shape2 = Either[Rectangle2, Circle2]

val rect2: Shape2 = Left((3.0, 4.0))
val circ2: Shape2 = Right(1.0)
```

尽管这种编码方式没有上面介绍的样例类的方式易读，却有着相同的理念，我们仍然能写出对于Shape2的类型安全的操作方法。代码如下：

```scala
def area2(shape: Shape2): Double =
    shape match {
        case Left((w, h)) => w * h 
        case Right(r) => math.Pi * r * r 
    }

area2(rect2) 
// res4: Double = 12.0

area2(circ2) 
// res5: Double = 3.141592653589793
```

重要的是Shape2是一个比Shape更加“泛型”的编码方式（相比传统的“拥有类型参数的类型”而言，这里使用泛型一词不是那么正式）。如果一段代码能操作一对Double数据那么它也能操作Rectangle2，反之亦然。Scala开发者更倾向使用像Rectangle和Circle这样容易理解、更专业的语义类型，而不是像Rectangle2和Circle2这样的泛型类型。然而在一些情况下泛型方式可能更好，比如，如果我们将数据持久化到硬盘中，我们并不关心一对Dobule类型数据和Rectangle2类型的不同，我们只需给出两个数字即可。

shapeless在这两种定义方式中都做的很好：默认情况下我们能友好的使用语义类型，当我们需要互用性（下文具体介绍）时又可以切换到泛型表示方式。当然，shapeless也使用自定义的数据类型来表示泛型的乘积和余积类型，而不是使用Tuple和Either。在下一节会介绍这些类型。

## 2.2 乘积类型（product）泛型编码 

上一节我们介绍了使用元组作为乘积类型的泛型表示。遗憾的是，Scala的内置元组类型有一系列的缺点，这些缺点有悖于使用shapeless的初衷：

1. 不同大小的元组有不同的、无关的类型，难以抛开大小进行抽象编码。
2. 没有长度为0的元组，而表示0字段的乘积类型却是非常重要的。可以使用Unit，但我们希望所有的泛型表示有一个合理的公共父类型，Unit和Tuple2的根类型是Any，因此结合两者使用是不明智的。

由于以上原因，shapeless为乘积类型提供了一套不同的泛型编码方式——异构列表（HList）（在命名上Product可能比HList更好，但是Scala的标准库中已经有了scala.Product类型，所以我们选择了HList）。

HList可以是一个空列表HNil也可以是一对::\[H, T\]，其中H是任意类型，T是另一个HList。因为每一个::类型都有H和T，所以HList中的每一个元素都是独立的。如下：

```scala
import shapeless.{HList, ::, HNil}

val product: String :: Int :: Boolean :: HNil = 
    "Sunday" :: 1 :: false :: HNil
```

上述中的HList的类型和值相互对应，其对应了三种类型：字符串、整型和布尔。我们能提取头和尾元素及其类型。代码如下：

```scala
val first = product.head 
// first: String = Sunday

val second = product.tail.head 
// second: Int = 1

val rest = product.tail.tail 
// rest: Boolean :: shapeless.HNil = false :: HNil
```

编译器知道每一个HList对象的准确长度，所以如果取空列表的head和tail就会造成编译错误。如下：

```scala
product.tail.tail.tail.head 
// <console>:15: error: could not find implicit value for 
// parameter c: shapeless.ops.hlist.IsHCons[shapeless.HNil]
// product.tail.tail.tail.head
//                        ^
```

我们能对HList对象进行操纵和转换，还包括检查和遍历。例如，我们能用::方法将元素插入到列表的最前端。再次注意，元素个数及元素具体类型是如何反映到结果中的（下面将42L通过::与product相连接，可以看到返回的newProduct的元素个数及元素具体类型）。如下：

```scala
val newProduct = 42L :: product
//Long :: String :: Int :: Boolean :: HNil
```

shapless也为HList提供了更多的复杂操作，如：映射（map）、过滤（filter）以及拼接列表（concatenating list）。我们会在第二部分具体讨论。

HList对象的这些行为一点也不神奇，我们已经用\(A, B\)元组以及Unit实现了所有这些功能，::和HNil只是实现这些功能的另一种选择。然而使表示和我们应用中的语义相分离是有好处的，HList恰好提供了分离能力。

### 2.2.1 使用Generic转换泛型表示 

shapeless提供了一个叫Generic的类型类，它可以在具体的ADT对象和其泛型表示对象之间进行相互转换。得益于一些幕后的宏魔法，使得我们无需冗余代码即可获取Generic实例。如下实现获取IceCream类的Generic对象：

```scala
import shapeless.Generic

case class IceCream(name: String, numCherries: Int, inCone: Boolean)

val iceCreamGen = Generic[IceCream] 

// iceCreamGen: shapeless.Generic[IceCream]{type Repr = String :: Int
// :: Boolean :: shapeless.HNil} = anon$macro$4$1@6b9323fe
```

注意Generic实例有一个Repr类型成员，Repr是Generic实例的泛型表示的类型。上面的代码中iceCreamGen实例的Repr类型为String :: Int :: Boolean :: HNil。Generic实例有两个方法：一个将原始对象转换为Repr类型，另一个将Repr类型转为原始对象。如下：

```scala
val iceCream = IceCream("Sundae", 1, false)
// iceCream: IceCream = IceCream(Sundae,1,false)

val repr = iceCreamGen.to(iceCream) 
// repr: iceCreamGen.Repr = Sundae :: 1 :: false :: HNil

val iceCream2 = iceCreamGen.from(repr) 
// iceCream2: IceCream = IceCream(Sundae,1,false)
```

如果两个ADT对象Repr类型相同，则我们可以使用它们的Generic实例进行相互转换。如下实现Employee和IceCream对象之间的转换：

```scala
case class Employee(name: String, number: Int, manager: Boolean)

// Create an employee from an ice cream:
val employee = Generic[Employee].from(Generic[IceCream].to(iceCream)) 
// employee: Employee = Employee(Sundae,1,false)
```

> 其它乘积类型
>
> 值得注意的是在Scala中Tuple实际上也是一种样例类，所以Generic也能应用于Tuple。如下：
>
> ```scala
> val tupleGen = Generic[(String, Int, Boolean)]
>
> tupleGen.to(("Hello", 123, true)) 
>
>
> tupleGen.from("Hello" :: 123 :: true :: HNil)
>
> ```
>
> Generic也能用于超过22个字段的样例类。如下：
>
> ```scala
> case class BigData( 
>     a:Int,b:Int,c:Int,d:Int,e:Int,f:Int,g:Int,h:Int,i:Int,j:Int, 
>     k:Int,l:Int,m:Int,n:Int,o:Int,p:Int,q:Int,r:Int,s:Int,t:Int, 
>     u:Int,v:Int,w:Int)
> Generic[BigData].from(Generic[BigData].to(BigData( 
>     1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23))) 
>
> // res6: BigData = 
> // BigData (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23)
> ```

在Scala2.10及更早版本中，Scala的样例类限制为22个字段。 此限制在2.11中移除（元组和函数仍有限制），但是使用HLists将有助于避免Scala中的22个字段的限制。可参考[limitations of 22 fields in Scala](https://underscore.io/blog/posts/2016/10/11/twenty-two.html)。

## 2.3 余积类型（coproduct）泛型编码 

我们已经学习了shapeless如何编码乘积类型，那么余积类型是怎么样的？之前我们学习了使用Either的操作方式，但是它跟元组有相同的缺点。因此，shapeless也提供了与HList相似的编码方式，名为Coproduct。如下：

```scala
import shapeless.{Coproduct, :+:, CNil, Inl, Inr}

case class Red() 
case class Amber() 
case class Green()

type Light = Red :+: Amber :+: Green :+: CNil
```

简单来说余积的形式是A :+: B :+: C :+: CNil，其意思是“A或B或C”，“:+:”可以被近似地解释为Either。一个余积的总类型编码了所有可能类型，但是每一个具体的余积实例只是其中的一种类型。“:+:”有两个子类：Inl和Inr，与Left和Right相似。通过嵌套Inl和Inr的构造函数来创建一个余积实例。如下：

```scala
val red: Light = Inl(Red())
// red: Light = Inl(Red())

val green: Light = Inr(Inr(Inl(Green())))
// green: Light = Inr(Inr(Inl(Green())))
```

每一个余积类型均以CNil结束，CNil是一个没有值的空类型，与Nothing相似。我们不能实例化CNil或创建一个只有Inr实例的余积类型，在每一个值中均应有一个Inl。

再次强调，余积类型并不特别，以上功能均可通过Either和Nothing实现。尽管使用Nothing存在技术困难，但是我们能使用其它任意一个无意义的单例类型来代替CNil。

## 2.3.1 使用Generic转换泛型编码 

余积类型看似很难解析，然而我们能看到它们非常适合较大的泛型编码场景。除了能解析样例类和样例对象，shapeless的Generic类型类还能解析密封特质和抽象类。如下代码将Generic应用于密封特质：

```scala
import shapeless.Generic

sealed trait Shape 
final case class Rectangle(width: Double, height: Double) extends Shape 
final case class Circle(radius: Double) extends Shape

val gen = Generic[Shape] 

// gen: shapeless.Generic[Shape]{type Repr = Rectangle :+: Circle :+:
// shapeless.CNil} = anon$macro$1$1@1a28fc61
```

Shape的Generic实例（gen）的Repr类型是“Rectangle :+: Circle :+: CNil”，它是密封特质Shape的子类的余积。我们能用gen的to和from方法在Shape的子类实例和gen.Repr之间进行相互转换。代码如下：

```scala
gen.to(Rectangle(3.0, 4.0))
// res3: gen.Repr = Inl(Rectangle(3.0,4.0))

gen.to(Circle(1.0)) 
// res4: gen.Repr = Inr(Inl(Circle(1.0)))
```

## 2.4 小结 

这一章我们讨论了Scala中shapeless为ADT提供的泛型表示：用HList表示乘积类型和用Coproduct表示余积类型。也介绍了通过Generic类型类进行ADT实例和它们的泛型表示之间的相互转换。目前，还没有讨论为什么泛型编码如此具有吸引力。本章介绍的ADT之间的相互转换这一使用案例很有趣但是并不是很有用。

HList和Coproduct的强大之处源于它们的递归结构（此处递归的意思是像::\[H, T\]这样，T同样代表一个新的::\[H, T\]，所以称之为递归），我们可以遍历泛型表示并根据它们的组成元素计算值。下一章我们将聚焦于第一个实际应用：自动派生类型类实例。