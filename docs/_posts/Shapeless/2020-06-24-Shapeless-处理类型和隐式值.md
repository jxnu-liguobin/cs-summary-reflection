---
title: 处理类型和隐式值
categories:
- Shapeless
tags: [Scala]
description: Scala的泛型编程库Shapeless的入门教程-4
---

* 目录
{:toc}

> 在线电子书 https://dreamylost.gitbook.io/dreamylost/

> shapeless v2.3.2

上一章我们已经学习了shapeless中最吸引人的使用案例：自动派生类型类实例。后续还有大量的更强大的案例。
然而，在学习下一内容之前，我们需要花些时间来介绍一些之前跳过的理论知识，并为编写和调试类型和隐式值占大比重的代码建立一套模式。

## 4.1 依赖类型（dependent type）

上一章我们用了大量的精力来讨论如何使用Generic，Generic实现了ADT和对应的泛型表示之间的相互转换。然而我们还未曾讨论Generic以及shapeless框架中大部分代码都依赖的基础理论：依赖类型。

为了更好的说明依赖类型，我们先来看一下Generic类的细节。以下代码是它的简单定义：

```scala
trait Generic[A] {
    type Repr
    def to(value: A): Repr
    def from(value: Repr): A
}
```

Generic实例与两个类型有关：类型参数A和类型成员Repr。假设我们用下面的代码实现了一个getRepr方法，我们将得到什么类型？

```scala
import shapeless.Generic

def getRepr[A](value: A)(implicit gen: Generic[A]) = 
    gen.to(value)
```

答案是返回类型依赖gen实例。在展开调用getRepr方法的过程中，编译器会搜索Generic\[A\]实例，返回类型是此实例中定义的Repr的类型。来看两个具体的例子：

```scala
case class Vec(x: Int, y: Int) 
case class Rect(origin: Vec, size: Vec)

getRepr(Vec(1, 2)) 
// res1: Int :: Int :: shapeless.HNil = 1 :: 2 :: HNil

getRepr(Rect(Vec(0, 0), Vec(5, 5))) 
// res2: Vec :: Vec :: shapeless.HNil = Vec(0,0) :: Vec(5,5) :: HNil
```

此处演示的就是依赖类型：getRepr的返回类型依赖其值参数中的类型成员。重新定义一个Generic2类型，并假设Repr为Generic2的类型参数而不是类型成员，模仿getRepr定义一个getRepr2方法。代码如下：

```scala
trait Generic2[A, Repr]

def getRepr2[A, R](value: A)(implicit generic: Generic2[A, R]): R = ???
```

在调用getRepr2的时候必须将Repr需要的值以类型参数的方式传递给它，这实际上使getRepr2变得无用。所以通过对比不难得出类型参数多用于输入类型，类型成员多用于输出类型。

## 4.2 依赖类型函数 

shapeless在以下类中使用依赖类型：Generic、Witness（此类将在下一章介绍）以及许多将在第二部分介绍的“ops”类型类。

例如shapeless提供了一个叫做Last的类型类，它能返回HList实例的最后一个元素。以下代码是Last定义的简单版本：

```scala
package shapeless.ops.hlist

trait Last[L <: HList] { 
    type Out 
    def apply(in: L): Out
}
```

在我们的代码中能通过获取Last的实例来检查HList，在下面的两个例子中注意Out类型依赖于HList实例的类型。代码如下：

```scala
import shapeless.{HList, ::, HNil}

import shapeless.ops.hlist.Last

val last1 = Last[String :: Int :: HNil] 
// last1: shapeless.ops.hlist.Last[String :: Int :: shapeless.HNil]{
//   type Out = Int} = shapeless.ops.hlist$Last$$anon$34@12389dd9

val last2 = Last[Int :: String :: HNil] 
// last2: shapeless.ops.hlist.Last[Int :: String :: shapeless.HNil]{
//   type Out = String} = shapeless.ops.hlist$Last$$anon$34@6cb2b0cb
```

一旦有了Last的实例，我们就能通过传递值参数来调用其apply方法。代码如下：

```scala
last1("foo" :: 123 :: HNil)
// res1: last1.Out = 123

last2(321 :: "bar" :: HNil) 
// res2: last2.Out = bar
```

我们提供了两种防止上述代码错误的方式。第一：为Last定义的隐式值确保只有在HList类型至少包含一个元素的时候才能获取其实例，对空的HList类型获取Last实例会报错。结果如下：

```scala
Last[HNil] 
// <console>:15: error: Implicit not found: shapeless.Ops.Last[
//  shapeless.HNil]. shapeless.HNil is empty, so there is no last
//  element.
//        Last[HNil]
//            ^
```

第二：Last实例的类型参数能够检查我们传入的HList对象是否与此实例定义时类型相一致，如果不一致会报错。如下：

```scala
last1(321 :: "bar" :: HNil)
// <console>:16: error: type mismatch;
//  found   : Int :: String :: shapeless.HNil
//  required: String :: Int :: shapeless.HNil
//        last1(321 :: "bar" :: HNil)
//                  ^    
```

进一步，我们来实现一个自定义类型类，为其取名Second，用它来获取HList实例的第二个元素。定义如下：

```scala
trait Second[L <: HList] { 
    type Out
    def apply(value: L): Out 
}

object Second {
    type Aux[L <: HList, O] = Second[L] { type Out = O }

    def apply[L <: HList](implicit inst: Second[L]): Aux[L, inst.Out] =
        inst
}
```

上述代码使用了3.1.2节所描述的惯用设计方式，我们在Second的伴随类中为获取结果定义了Aux类型。

召唤者方法（summoner method）、“implicitly”和“the”

注意上述代码中的apply方法的返回值类型是Aux\[L, O\]而不是Second\[L\]，这一点很重要，返回Aux类型确保apply方法不被编译器擦除所获取实例的Out类型成员。如果返回Second\[L\]类型，则返回值的Out类型成员将会被擦除，Second将不能正常工作。

scala.Predef包中的implicitly方法在获取实例的时候同样会檫除类型成员信息。将使用implicitly获取的Last实例的类型与使用Last.apply获取的实例的类型相比较。对比如下：

```scala
implicitly[Last[String :: Int :: HNil]] 
// res6: shapeless.ops.hlist.Last[String :: Int :: shapeless.
//   HNil] = shapeless.ops.hlist$Last$$anon$34@651110a2
Last[String :: Int :: HNil] 
// res7: shapeless.ops.hlist.Last[String :: Int :: shapeless.
//   HNil]{type Out = Int} = shapeless.ops.
```

通过以上对比可以看到implicitly召唤的实例的类型没有Out类型成员。由此可知当使用类型依赖函数进行编码时应避免使用implicitly，可以使用自定义apply方法或者使用shapeless中的the方法。使用the方法的代码如下：

```scala
import shapeless._

the[Last[String :: Int :: HNil]] 
// res8: shapeless.ops.hlist.Last[String :: Int :: shapeless. HNil]{type Out = Int} = shapeless.ops. 
//   hlist$Last$$anon$34@1cd22a69
```

我们只需要为至少包含两个元素的HList定义一个隐式方法即可。代码如下，其中A为第一个元素，B为第二个元素，Rest为其余元素：

```scala
implicit def hlistSecond[A, B, Rest <: HList]: Aux[A :: B :: Rest, B] = 
    new Second[A :: B :: Rest] {
        type Out = B 
        def apply(value: A :: B :: Rest): B = 
          value.tail.head 
    }
```

定义好此隐式方法之后，即可使用Second.apply方法获取Second实例。代码如下：

```scala
val second1 = Second[String :: Boolean :: Int :: HNil] 
// second1: Second[String :: Boolean :: Int :: shapeless.HNil]{type
//   Out = Boolean} = $anon$1@668168cd

val second2 = Second[String :: Int :: Boolean :: HNil] 
// second2: Second[String :: Int :: Boolean :: shapeless.HNil]{type
//   Out = Int} = $anon$1@2ddf467d
```

获取Second实例与获取Last实例有相同的限制条件。如果我们尝试为成员个数不能匹配的HList获取Second实例，解析将会失败并报错。结果如下：

```scala
Second[String :: HNil] 
// <console>:26: error: could not find implicit value for parameter inst: Second[String :: shapeless.HNil]
//        Second[String :: HNil]
//              ^
```

同样，所获取的实例通过传入相匹配的HList对象来调用apply方法，如果不匹配会报错。代码如下：

```scala
second1("foo" :: true :: 123 :: HNil) 
// res11: second1.Out = true

second2("bar" :: 321 :: false :: HNil) 
// res12: second2.Out = 321

second1("baz" :: HNil) 
// <console>:27: error: type mismatch;
// found : String :: shapeless.HNil
// required: String :: Boolean :: Int :: shapeless.HNil 
//       esecond1("baz" :: HNil)
//                      ^    
```

## 4.3 链接依赖类型函数

依赖类型函数提供从其它类型推断目标类型的手段，我们能链接多个依赖类型函数来执行包含多个步骤的计算。例如可以使用Generic获取一个样例类的Repr，然后使用Last来获取其最后一个元素的类型。代码如下：

```scala
def lastField[A](input: A)(
    implicit
    gen: Generic[A], 
    last: Last[gen.Repr]
): last.Out = last.apply(gen.to(input)) 

// <console>:28: error: illegal dependent method type: parameter may only be referenced in a subsequent parameter section
//          gen: Generic[A],
//          ^
```

不幸的是这段代码并不能通过编译，这跟我们3.2.2节中genericEncoder的定义所碰到的问题一样，可以通过将类型变量转换为类型参数的方式来解决。同理我们可以将上述代码修改如下：

```scala
def lastField[A, Repr <: HList](input: A)( 
    implicit 
    gen: Generic.Aux[A, Repr],
    last: Last[Repr] 
): last.Out = last.apply(gen.to(input))

lastField(Rect(Vec(1, 2), Vec(3, 4)))
// res14: Vec = Vec(3,4)
```

这是一个通用规则，我们经常以这种方式来写代码。通过将所有的自由变量编码为类型参数，使得编译器能用合适的类型统一这些类型参数。这也同样适用于那些有更刁钻限制的场景。假设我们想为只有一个字段的样例类获取Generic实例。可以采用下面的代码：

```scala
def getWrappedValue[A, H](input: A)(
    implicit
    gen: Generic.Aux[A, H :: HNil] 
): H = gen.to(input).head
```

上面的代码错误很隐蔽，此方法能通过编译但是当我们调用它的时候会提示找不到隐式值。具体如下：

```scala
case class Wrapper(value: Int)

getWrappedValue(Wrapper(42)) 
// <console>:30: error: could not find implicit value for parameter 
//   gen: shapeless.Generic.Aux[Wrapper,H :: shapeless.HNil]
//        getWrappedValue(Wrapper(42)) 
//                       ^
```

错误提示很清楚，表面看来线索在于H这个类型，它是方法中的一个类型参数的名称，它不应该出现在编译器正在尝试去统一的类型中。问题是gen这个参数的类型被过度限制了，编译器不能找到对应的隐式Repr实例，其与约束类型的类型相同同时元素个数也相同。当出现编译器不能统一协变的类型参数的情况，就会将协变参数认定为公共子类Nothing，所以Nothing也能为错误提供线索。

此问题的解决方案是将隐式解析分为以下两步：

1. 为A找到一个有与之适应的Repr类型的Generic实例，即只约束Repr的类型而不约束其元素个数；
2. 用H约束此Repr的头元素类型。

以下是getWrappedValue方法使用=:=限制Repr类型之后修订的版本：

```scala
def getWrappedValue[A, Repr <: HList, Head, Tail <: HList](input: A)(
    implicit
    gen: Generic.Aux[A, Repr],
    ev: (Head :: Tail) =:= Repr 
): Head = gen.to(input).head 

// <console>:30: error: could not find implicit value for parameter c: 
//    shapeless.ops.hlist.IsHCons[gen.Repr]
//         ): Head = gen.to(input).head 
//                                 ^
```

不幸的是又报错了，原因在于此方法中调用的head方法需要一个IsHCons类型的隐式参数，完善起来很容易——只需要使用shapeless的工具箱中的IsHCons工具即可。IsHCons是一个shapeless中的类型类，它用于将HList对象分为Head和Tail两部分，所以我们只需要使用IsHCons替换=:=即可解决错误。代码如下：

```scala
import shapeless.ops.hlist.IsHCons

def getWrappedValue[A, Repr <: HList, Head, Tail <: HList](in: A)( 
    implicit
    gen: Generic.Aux[A, Repr],
    isHCons: IsHCons.Aux[Repr, Head, Tail]
): Head = gen.to(in).head
```

问题解决，getWrappedValue方法和对其调用都能通过编译。结果如下：

```scala
getWrappedValue(Wrapper(42)) 
// res17: Int = 42
```

shapeless提供了一系列像IsHCons这样的工具（将会在第6到8章详细介绍），并且在需要的时候我们还能用自定义的类型类补充该工具箱。所以此处的关键点不在于我们使用IsHCons解决了问题，而在于要理解这个过程，在这个过程中我们学会了编写能通过编译的代码和具备了解决问题的能力。下面将以逐步的总结我们到目前为止的收获来结束这一章。

## 4.4 小结

当使用shapeless编写程序的时候，我们经常尝试去找到一个依赖我们代码中的值的目标类型，此之谓依赖类型。

涉及依赖类型的问题能够很方便的通过使用隐式搜索进行表达，只要在调用的地方给定一个起始点编译器就能自动推断中间类型和目标类型。

我们经常需要使用多个步骤来计算结果（例如使用Generic来获取Repr，然后使用另一个类型类来获取其它类型）。当我们这样做的时候，可以采取下述几个规则来确保我们的代码正常编译和工作。

1. **我们应该将每一个中间类型提取到外面作为一个类型参数。很多类型参数不会在结果中被使用，但是编译器需要用它们来推断哪些类型需要它去统一。**
2. **在一个方法中编译器按照从左到右的顺序分解隐式变量，如果不能找到联合的隐式值编译器就会返回，所以在方法中我们应该以需要的顺序定义隐式变量，使用一个或更多的类型变量来联系当前隐式变量与之前的隐式变量。**
3. **编译器同一时间只能处理一个限制，所以我们不能过度限制任何一个单一隐式参数。**
4. **我们应该以指定任何可能在其它地方需要的类型参数和类型成员的方式来显式声明返回类型。类型成员往往更重要，所以我们需要在适当的地方使用Aux类型来保存它们。如果我们没有在返回类型中声明类型成员，对于编译器来说进行深层次隐式解析时类型成员将不可用。**
5. **Aux类型别名模式对于保持代码可读是有用的，当使用shapeless工具箱中的工具时我们应该对Aux别名加以小心并对我们自己的依赖类型函数实现Aux别名。**

当找到一个有用的依赖类型操作链时，我们能把它们捕获为一个单一的类型类，有时这被称之为“引理”模式（这是从数学证明中借用的一个词），我们将在6.2节中看到这种模式的具体示例。