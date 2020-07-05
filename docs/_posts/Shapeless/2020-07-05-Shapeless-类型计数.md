---
title: 类型计数
categories:
- Shapeless
tags: [Scala]
description: Scala的泛型编程库Shapeless的入门教程-8
---

* 目录
{:toc}

> 在线电子书 https://dreamylost.gitbook.io/dreamylost/

> shapeless v2.3.2

有时我们需要在类型级别进行计数。例如，计算一个HList实例的长度或者在一次计算中已经展开的表达式的数目（不太明确）。用数值表示数量非常容易，但是由于隐式解析都是在类型层面进行操作，所以如果我们想影响隐式解析就需要在类型级别表示数量。这一章我们讲解类型计数背后的理论并为类型类派生提供一些具有吸引力的使用案例。

## 8.1 将数量表示为类型 

shapeless使用“[Church encoding](https://en.wikipedia.org/wiki/Church_encoding)”的方式在类型层面表示自然数字并提供了一个有两个子类的Nat类型，两个子类型为：\_0代表0、Succ\[N\]代表N+1，其中N也是Nat类型。具体如下：

```scala
import shapeless.{Nat, Succ}

type Zero = Nat._0 
type One = Succ[Zero]
type Two = Succ[One] 
// etc...
```

shapeless以Nat.\_N的方式预定义了前22个Nat。具体如下：

```scala
Nat._1
Nat._2
Nat._3
// etc...
```

Nat没有运行时语义，我们必须使用ToInt类型类将Nat转换为运行时的Int。代码如下：

```scala
import shapeless.ops.nat.ToInt

val toInt = ToInt[Two]

toInt.apply() 
// res7: Int = 2
```

Nat.toInt方法为调用toInt.apply\(\)方法提供了一个方便的简写。它以隐式参数的方式接受ToInt的实例。代码如下：

```scala
Nat.toInt[Nat._3] 
// res8: Int = 3
```

## 8.2 泛型表示的元素数目

Nat的其中一个使用案例是计算HList和Coproduct的元素数目。shapeless为此分别提供了shapeless.ops.hlist.Length和shapeless.ops.coproduct.Length两个类型类。使用方式如下：

```scala
import shapeless._ 
import shapeless.ops.{hlist, coproduct, nat}

val hlistLength = hlist.Length[String :: Int :: Boolean :: HNil] 
// hlistLength: shapeless.ops.hlist.Length[String :: Int :: Boolean ::
//    shapeless.HNil]{type Out = shapeless.Succ[shapeless.Succ[ 
//    shapeless.Succ[shapeless._0]]]} = shapeless.ops. 
//    hlist$Length$$anon$3@55cfe482

val coproductLength = coproduct.Length[Double :+: Char :+: CNil]
// coproductLength: shapeless.ops.coproduct.Length[Double :+: Char :+: 
//    shapeless.CNil]{type Out = shapeless.Succ[shapeless.Succ[
//    shapeless._0]]} = shapeless.ops.
//    coproduct$Length$$anon$29@5e23a2f7
```

Length的实例有一个类型成员Out，它以Nat类型表示结果长度。取出Int类型结果方式如下：

```scala
Nat.toInt[hlistLength.Out] 
// res0: Int = 3

Nat.toInt[coproductLength.Out] 
// res1: Int = 2
```

下面让我们在具体的例子中运用类型计数。我们将创建一个SizeOf类型类来计算样例类的字段个数并返回一个Int类型。代码如下：

```scala
trait SizeOf[A] { 
    def value: Int 
}

def sizeOf[A](implicit size: SizeOf[A]): Int = size.value
```

我们需要做三件事情来创建SizeOf的实例：

1. 一个Generic来获取样例类对应的HList类型；
2. 一个Length类型类来计算HList的元素数目并返回一个Nat类型；
3. 一个ToInt类型类将Nat转换为Int。

下面是按照第四章中介绍的方式所写的实现方案：

```scala
implicit def genericSizeOf[A, L <: HList, N <: Nat]( 
    implicit 
    generic: Generic.Aux[A, L],
    size: hlist.Length.Aux[L, N],
    sizeToInt: nat.ToInt[N]
): SizeOf[A] = 
    new SizeOf[A] { 
        val value = sizeToInt.apply()
    }
```

使用下面的代码进行测试：

```scala
case class IceCream(name: String, numCherries: Int, inCone: Boolean)

sizeOf[IceCream] 
// res3: Int = 3
```

## 8.3 样例学习：随机值生成器 

像[ScalaCheck](https://scalacheck.org/)这样基于属性的测试库都使用类型类来为单元测试生成随机数据。例如，ScalaCheck提供了Arbitrary类型类，我们可以通过如下方式使用它：

```scala
import org.scalacheck._

for(i <- 1 to 3) println(Arbitrary.arbitrary[Int].sample) 
// Some(1)
// Some(1813066787)
// Some(1637191929)

for(i <- 1 to 3) println(Arbitrary.arbitrary[(Boolean, Byte)].sample) 
// Some((true,127))
// Some((false,83))
// Some((false,-128))
```

ScalaCheck为更多的标准Scala类型提供了内置的Arbitrary实例。然而为用户自定义ADT创建Arbitrary实例仍然是一个耗时的手动过程。这使得像[scalacheck-shapeless](https://github.com/alexarchambault/scalacheck-shapeless)这样使用shapeless进行整合的测试类库非常有吸引力。

现在我们将创建一个简单的Random类型类来为用户自定义ADT生成随机值，并展示Length和Nat如何在此实现中发挥重要作用。像之前一样我们以Random类型类的定义开始。代码如下：

```scala
trait Random[A] {
    def get: A 
}

def random[A](implicit r: Random[A]): A = r.get
```

### 8.3.1 基础类型的Random实例 

先来定义几个简单的Random实例。代码如下：

```scala
// Instance constructor:
def createRandom[A](func: () => A): Random[A] = 
    new Random[A] { 
        def get = func()
    }

// Random numbers from 0 to 9:
implicit val intRandom: Random[Int] = 
    createRandom(() => scala.util.Random.nextInt(10))

// Random characters from A to Z:
implicit val charRandom: Random[Char] = 
    createRandom(() => ('A'.toInt + scala.util.Random.nextInt(26)).toChar)

// Random booleans:
implicit val booleanRandom: Random[Boolean] = 
    createRandom(() => scala.util.Random.nextBoolean)
```

我们能通过random方法使用这些简单的随机值生成器。具体如下：

```scala
for(i <- 1 to 3) println(random[Int]) 
// 0
// 8
// 9

for(i <- 1 to 3) println(random[Char]) 
// V
// N
// J
```

### 8.3.2 乘积类型的Random实例 

我们能使用第三章中介绍的Generic和HList技巧为乘积类型创建Random实例。代码如下：

```scala
import shapeless._

implicit def genericRandom[A, R]( 
    implicit
    gen: Generic.Aux[A, R],
    random: Lazy[Random[R]]
): Random[A] = 
    createRandom(() => gen.from(random.value.get))

implicit val hnilRandom: Random[HNil] = 
    createRandom(() => HNil)

implicit def hlistRandom[H, T <: HList]( 
    implicit
    hRandom: Lazy[Random[H]],
    tRandom: Random[T]
): Random[H :: T] = 
    createRandom(() => hRandom.value.get :: tRandom.get)
```

这样就可以得到样例类的Random实例。假设定义一个Cell样例类，并生成其随机值。代码如下：

```scala
case class Cell(col: Char, row: Int)

for(i <- 1 to 5) println(random[Cell]) 
// Cell(H,1)
// Cell(D,4)
// Cell(D,7)
// Cell(V,2)
// Cell(R,4)
```

### 8.3.3 余积类型的Random实例 

这个问题才开始让我们感觉有点难度：生成余积类型的Random实例，并随机选择一个子类。先来为余积类型定义一些基础实现。代码如下：

```scala
implicit val cnilRandom: Random[CNil] = 
    createRandom(() => throw new Exception("Inconceivable!"))

implicit def coproductRandom[H, T <: Coproduct]( 
    implicit 
    hRandom: Lazy[Random[H]], 
    tRandom: Random[T] 
): Random[H :+: T] = 
    createRandom { () =>
        val chooseH = scala.util.Random.nextDouble < 0.5 
        if(chooseH) Inl(hRandom.value.get) else Inr(tRandom.get) 
    }
```

在上述的实现过程中计算chooseH的过程会使得Inl、Inr各占一半的概率，这看似平均其实会导致一个不平均的分布。考虑下述类型：

```scala
sealed trait Light 
case object Red extends Light
case object Amber extends Light 
case object Green extends Light
```

Light的Repr类型是Red :+: Amber :+: Green :+: CNil，此类型的Random实例有50%的概率选中Red，剩下50%的概率会选中Amber :+: Green :+: CNil，然而正确的结果应该是33%的概率选中Red，67%的概率选中Amber :+: Green :+: CNil。

这还不是全部问题，如果我们分析所有子类的概率分布就会得到更加震惊的结果。其被选中的概率分布如下：

* Red：1/2
* Amber：1/4
* Green：1/8
* CNil：1/16

Random余积实例有6.75%的概率会抛出异常！多次运行就很有可能报错：

```scala
for(i <- 1 to 100) random[Light] 
// java.lang.Exception: Inconceivable!
//   ...
```

要解决这个问题就要修改选中H或T的概率，正确的分布应该是选中H的概率在1/n，n是余积类型的元素数目，这能确保余积的子类能够以相等概率被选中，也确保在只有一个子类的余积类型中能够以100%的概率选中头元素，而不会选中CNil，也就不会调用cnilProduct.get方法。下面是更新后的实现，使用coproduct.Length来计算T中元素的个数，此处用了递归的原理，因为T又会分解成新的H和T，直到CNil，据此可保证子类选择的平均分布。代码如下：

```scala
import shapeless.ops.coproduct 
import shapeless.ops.nat.ToInt

implicit def coproductRandom[H, T <: Coproduct, L <: Nat]( 
    implicit 
    hRandom: Lazy[Random[H]], 
    tRandom: Random[T],
    tLength: coproduct.Length.Aux[T, L], 
    tLengthAsInt: ToInt[L] 
): Random[H :+: T] = { 
    createRandom { () => 
        val length = 1 + tLengthAsInt()
        val chooseH = scala.util.Random.nextDouble < (1.0 / length) 
        if(chooseH) Inl(hRandom.value.get) else Inr(tRandom.get) 
    } 
}
```

有了这些修改就可以为任意的乘积或者余积类型生成随机值。余积类型测试如下：

```scala
for(i <- 1 to 5) println(random[Light]) 
// Green
// Red
// Red
// Red
// Green
```

通常情况下为ScalaCheck生成测试数据需要大量的冗余代码，而Nat是shapeless组成的重要组件，所以对于shapeless来说生成随机值是一个非常有诱惑力的使用案例。

## 8.4 涉及Nat的其它操作

shapeless提供了一套基于Nat的操作，HList和Coproduct实例的apply方法可以接受一个Nat类型的参数或者类型参数，实现读取Nat实例对应位置的元素。使用方式如下：

```scala
import shapeless._

val hlist = 123 :: "foo" :: true :: 'x' :: HNil

hlist.apply[Nat._1]
// res1: String = foo

hlist.apply(Nat._3)
// res2: Char = x
```

shapeless中也提供了像take、drop、slice和upadateAt的其它操作。例如：

```scala
hlist.take(Nat._3).drop(Nat._1)
// res3: String :: Boolean :: shapeless.HNil = foo :: true :: HNil

hlist.updatedAt(Nat._1, "bar").updatedAt(Nat._2, "baz") 
// res4: Int :: String :: String :: Char :: shapeless.HNil = 123 ::
//   bar :: baz :: x :: HNil
```

这些操作和它们对应的类型类对于操作乘积和余积类型内部的个别元素非常有帮助。

## 8.5 小结

这一章我们介绍了如何使用shapeless表示原始数字以及如何在类型类中使用它们。本章我们还看到了一些预定义的ops类型类，它们能像计算长度和根据索引获取元素一样进行操作，还看到了以其它方式使用Nat创建我们自己的类型类。

在后面几章介绍的Nat、Poly和各种各样的类型中，它们也只是shapeless.ops中提供的一小部分工具，还有非常多的ops类型类，它们提供了综合性的基础功能，基于它们我们能实现自己的功能。学过的这些理论也足以使我们理解大多数能够用来派生自定义的类型类的ops操作，shapeless.ops包中的源码也足够让我们掌握那些未介绍过的并且有用的ops操作。

## 准备发射！（恭喜晋级） 

学习完第二部分的shapeless.ops之后，我们已经结束了本书的学习。希望此书能够对读者理解shapeless这款迷人的和强大的类库有所帮助，并希望你们能在将来的Scala类型操作中得心应手！

作为函数化程序员我们重视抽象高于一切。像函子（functor）和单子（monad）这样的概念是多年写代码、总结规律和抽象去冗余等编程科学研究的结果。shapeless提高了Scala中的抽象性，像Generic和LabelledGeneric这样的工具为抽象非常独特的数据类型提供了一个接口。

对于刚接触shapeless框架的信心满满的程序员来说存在两个障碍：首先，丰富的理论知识和实施细节要求我们理解需要处理的模式，此书有幸在这方面有所帮助；其次，他们对一个“学术的”和“高级的”类库往往感到害怕和不确定，我们用共享知识的方式——使用案例、利弊分析、实现策略等等——来加深对这些有价值的工具的理解以克服这些问题，所以请将此书分享给你的朋友并让我们一起来努力去除不必要的冗余代码。