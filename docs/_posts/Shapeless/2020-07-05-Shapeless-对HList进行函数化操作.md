---
title: 对HList进行函数化操作
categories:
- Shapeless
tags: [Scala]
description: Scala的泛型编程库Shapeless的入门教程-7
---

* 目录
{:toc}

> 在线电子书 https://dreamylost.gitbook.io/dreamylost/

> shapeless v2.3.2

普通的Scala程序大量使用像map和flatMap这样的函数化操作。学习到此我们脑子中不免会产生一个问题，能否对HList实例执行相同的函数化操作呢？尽管我们必须做一些与普通的Scala函数化操作稍微不同的事情，但是答案是肯定的，可以使用类型类的技巧并且shapeless提供了一套ops类型类来帮助我们实现这一功能。

在我们深入学习这些类型类之前，我们需要先学习一下shapeless中如何定义多态函数（polymorphic function），多态函数能够实现对异构数据的map操作。

## 7.1 动机：对HList进行映射（map）操作

我们通过学习map方法的原理来引出多态函数。图7.1展示了普通list对象的映射操作。如果我们有一个List\[A\]对象，并提供一个“A=&gt;B”的函数，据此可得List\[B\]对象。

![&#x56FE;7.1 &#x666E;&#x901A;list&#x5BF9;&#x8C61;&#x7684;map&#x64CD;&#x4F5C;&#xFF08;&#x5355;&#x4E00;&#x6620;&#x5C04;&#xFF09;](http://images2015.cnblogs.com/blog/704456/201701/704456-20170131231437089-532586052.png)

HList对象元素的异构类型导致这一方式不能正常运行，Scala函数修复了输入和输出类型使得map的结果的每一个元素都必须拥有相同的类型。

理想情况下我们需要一个像图7.2中的map操作，它判断每一个输入的类型并决定每一个输出的类型，最终得到一个封闭的、能保持HList异构本质的组合变换。

![&#x56FE;7.2 &#x5F02;&#x6784;list&#x5BF9;&#x8C61;&#x7684;map&#x64CD;&#x4F5C;&#xFF08;&#x591A;&#x6001;&#x6620;&#x5C04;&#xFF09;](http://images2015.cnblogs.com/blog/704456/201701/704456-20170131231445870-282014479.png)

不幸的是我们不能使用Scala函数来实现这种操作，需要一些新的方式，这种方式就是多态函数。

## 7.2 多态函数（polymorphic function） 

shapeless为实现多态函数提供了一个叫做Poly的类型。简单解释多态函数如何工作即为其结果类型依赖参数类型。注意下一节中不包含真实的shapeless代码——为了灵活性和易用性我们省略了很多不重要的代码。下面结合实际的shapeless中的Poly类来创建一个简单的API以展示我们实现函数化操作的目的。

### 7.2.1 Poly如何工作 

Poly类的核心代码是一个泛型的apply方法，它除了有一个普通A类型的参数，还接受一个Case\[P, A\]（原文为Case\[A\]）类型的隐式参数。Case和Poly的定义如下：

```scala
// This is not real shapeless code.
// It's just for demonstration.

trait Case[P, A] { 
    type Result 
    def apply(a: A): Result 
}

trait Poly { 
    def apply[A](arg: A)(implicit cse: Case[this.type, A]): cse.Result = 
        cse.apply(arg)
}
```

当我们实现一个实际的Poly类的时候，需要为每一个关心的参数类型提供Case实例，Case实例的apply方法定义了对此种类型的数据做何种映射。下面代码实现了实际的函数体：

```scala
// This is not real shapeless code.
// It's just for demonstration.

object myPoly extends Poly { 
    implicit def intCase = 
        new Case[this.type, Int] {
            type Result = Double 
            def apply(num: Int): Double = num / 2.0 
        }

    implicit def stringCase = 
        new Case[this.type, String] {
            type Result = Int
            def apply(str: String): Int = str.length
        }
}
```

当我们调用myPoly.apply时，编译器搜索对应的隐式Case实例并调用它。例如下述代码会调用隐式的intCase：

```scala
myPoly.apply(123)
// res8: Double = 61.5
```

我们可以使用一些微妙的作用域技巧，使编译器能够自动定位Case实例而不用任何多余的引入。Case有一个额外的类型参数P，该参数引用Poly的单例类型。Case\[P, A\]的隐式作用域包括Case、P和A的伴随对象。我们把P设置为myPoly.type，myPoly.type的伴随对象就是myPoly自身。换句话说，不管在哪里调用myPoly.apply方法Poly里定义的隐式Case实例总是处在作用域内。

### 7.2.2 Poly语法规则

上面的代码不是实际的shapeless代码，但是幸运的是，shapeless提供了更加容易的Poly实例定义方式。下面是以正确的语法定义的myPoly：

```scala
import shapeless._

object myPoly extends Poly1 { 
    implicit val intCase: Case.Aux[Int, Double] = 
        at(num => num / 2.0)

    implicit val stringCase: Case.Aux[String, Int] = 
        at(str => str.length)
}
```

此处与之前定义的样例语法有几个关键的不同之处：

1. 我们继承自Poly1特质而不是Poly，shapeless提供了Poly类型和其一系列的子类，从Poly1到Poly22，它们支持不同参数个数的多态函数。
2. Case.Aux类型与Poly的单例类型看上去没有关联，Case.Aux是Poly1里定义的一个类型别名，其实二者是有关联的，在Poly1的定义中我们可以清晰的看到。
3. 我们使用了一个名叫at的辅助方法来定义case隐式实例，这与3.1.2节中介绍的实例构造子的作用相同，可以减少冗余代码。

除了语法差异，使用shapeless真实代码定义的myPoly在功能上与样例中定义的myPoly是一致的，我们能给其提供一个Int或String类型的参数，得到一个相应类型的返回结果。具体如下：

```scala
myPoly.apply(123) 
// res10: myPoly.intCase.Result = 61.5

myPoly.apply("hello") 
// res11: myPoly.stringCase.Result = 5
```

shapeless同样支持多个参数的Poly，下面是两个参数的例子：

```scala
object multiply extends Poly2 { 
    implicit val intIntCase: Case.Aux[Int, Int, Int] = 
        at((a, b) => a * b)

    implicit val intStrCase: Case.Aux[Int, String, String] = 
        at((a, b) => b.toString * a)
}

multiply(3, 4) 
// res12: multiply.intIntCase.Result = 12

multiply(3, "4") 
// res13: multiply.intStrCase.Result = 444
```

因为Case实例只是隐式值，我们能基于类型类定义Case实例并实现在前面章节介绍过的所有高级隐式解析。下面是不同上下文环境下的求数字之和的简单的例子：

```scala
import scala.math.Numeric

object total extends Poly1 { 
    implicit def base[A](implicit num: Numeric[A]): 
        Case.Aux[A, Double] = 
      at(num.toDouble)

    implicit def option[A](implicit num: Numeric[A]): 
        Case.Aux[Option[A], Double] =
      at(opt => opt.map(num.toDouble).getOrElse(0.0))

    implicit def list[A](implicit num: Numeric[A]): 
        Case.Aux[List[A], Double] = 
      at(list => num.toDouble(list.sum))
}

total(10) 
// res15: Double = 10.0

total(Option(20.0)) 
// res16: Double = 20.0

total(List(1L, 2L, 3L)) 
// res17: Double = 6.0
```

类型推断特质

Poly将Scala的类型推断移出了编译器的舒适区，只需要让编译器一次多做几个类型推断就能很容易迷惑它。比如下面的代码能够正常编译：

```scala
val a = myPoly.apply(123) 
val b: Double = a
```

然而，将上述两行代码组合成一行就会报错。如下：

```scala
val a: Double = myPoly.apply(123) 
// <console>:17: error: type mismatch;
//  found   : Int(123)
//  required: myPoly.ProductCase.Aux[shapeless.HNil,?] 
//    (which expands to) shapeless.poly.Case[myPoly.type,
//   shapeless.HNil]{type Result = ?}
//        val a: Double = myPoly.apply(123)
//                                         ^
```

如果我们增加一个类型注释，编译正常。如下：

```scala
val a: Double = myPoly.apply[Int](123)
// a: Double = 61.5
```

这种行为很让人困惑和讨厌，也并没有固定的规则能让我们避免这一问题，唯一的方法就是试着不要过度约束编译器，一次只解决一个约束并在编译器报错的时候补充一点提示信息。

## 7.3 使用Poly完成map和flatMap操作 

shapeless提供了一套基于Poly的函数化操作，每一个都是作为一个ops类型类来实现的。此处我们以map和flatMap操作为例。下面是map操作的代码：

```scala
import shapeless._

object sizeOf extends Poly1 { 
    implicit val intCase: Case.Aux[Int, Int] = 
        at(identity)

    implicit val stringCase: Case.Aux[String, Int] = 
        at(_.length)

    implicit val booleanCase: Case.Aux[Boolean, Int] = 
        at(bool => if(bool) 1 else 0)
}

(10 :: "hello" :: true :: HNil).map(sizeOf) 

// res1: Int :: Int :: Int :: shapeless.HNil = 10 :: 5 :: 1 :: HNil
```

注意结果HList的元素类型与sizeOf里的Case实例的输出类型相匹配。只需为HList实例准备一个Poly对象，在此Poly对象中对该HList的所有类型都提供相应的Case实例，就能对该HList实例调用map函数。但是如果编译器不能为某个成员找到其对应的Case实例那么就会报错。如下：

```scala
(1.5 :: HNil).map(sizeOf) 
// <console>:17: error: could not find implicit value for parameter 
//    mapper: shapeless.ops.hlist.Mapper[sizeOf.type,Double :: 
//    shapeless.HNil]
// (1.5 :: HNil).map(sizeOf) 
//                  ^
```

我们也能对HList实例进行flatMap操作，只要在定义的Poly实例中使每一个Case实例返回的是HList类型即可。代码如下：

```scala
object valueAndSizeOf extends Poly1 {
    implicit val intCase: Case.Aux[Int, Int :: Int :: HNil] = 
        at(num => num :: num :: HNil)

    implicit val stringCase: Case.Aux[String, String :: Int :: HNil] = 
        at(str => str :: str.length :: HNil)

    implicit val booleanCase: Case.Aux[Boolean, Boolean :: Int :: HNil] =
        at(bool => bool :: (if(bool) 1 else 0) :: HNil)
}

(10 :: "hello" :: true :: HNil).flatMap(valueAndSizeOf) 

// res3: Int :: Int :: String :: Int :: Boolean :: Int :: shapeless.
//    HNil = 10 :: 10 :: hello :: 5 :: true :: 1 :: HNil
```

再次强调，如果调用flatMap的HList实例有某个元素类型所对应的Case实例没有定义或者其对应的Case实例返回的结果不是HList类型那么编译器就会报错。如下：

```scala
// Using the wrong Poly with flatMap:
(10 :: "hello" :: true :: HNil).flatMap(sizeOf) 
// <console>:18: error: could not find implicit value for parameter
//    mapper: shapeless.ops.hlist.FlatMapper[sizeOf.type,Int :: String
//    :: Boolean :: shapeless.HNil]
//         (10 :: "hello" :: true :: HNil).flatMap(sizeOf)
//                                 ^
```

map和flatMap分别基于Mapper和FlatMapper类型类，我们将在7.5节中看到一个直接使用Mapper进行操作的例子。

## 7.4 使用Poly完成Fold操作 

除了map和flatMap之外，shapeless还提供了基于Poly2的foldLeft和foldRight操作，其区别在于Case实例需要对两个变量进行处理。代码如下：

```scala
import shapeless._

object sum extends Poly2 { 
    implicit val intIntCase: Case.Aux[Int, Int, Int] = 
        at((a, b) => a + b)

    implicit val intStringCase: Case.Aux[Int, String, Int] = 
        at((a, b) => a + b.length)
}

(10 :: "hello" :: 100 :: HNil).foldLeft(0)(sum) 
// res7: Int = 115
```

同样还能完成reduceLeft、reduceRight、foldMap等操作，每一个操作都有与之相对应的类型类，我们将把研究这些可用的操作作为练习留给读者。

## 7.5 使用Poly定义类型类

我们能以Poly和像Mapper、FlatMapper一样的类型类为基础，定义我们自己的类型类。作为例子，我们定义一个实现从一个样例类到另一个样例类的map操作的类型类ProductMapper。代码如下：

```scala
trait ProductMapper[A, B, P] { 
    def apply(a: A): B 
}
```

我们能用一个Mapper参数和一对Generic参数来创建一个ProductMapper的实例。代码如下：

```scala
import shapeless._ 
import shapeless.ops.hlist

implicit def genericProductMapper[ 
    A, B,
    P <: Poly,
    ARepr <: HList,
    BRepr <: HList 
](
    implicit 
    aGen: Generic.Aux[A, ARepr],
    bGen: Generic.Aux[B, BRepr], 
    mapper: hlist.Mapper.Aux[P, ARepr, BRepr] 
): ProductMapper[A, B, P] = 
    new ProductMapper[A, B, P] { 
      def apply(a: A): B = 
        bGen.from(mapper.apply(aGen.to(a))) 
}
```

有趣的是，尽管定义了一个Poly类型的类型参数P，但是在我们的代码的任何位置均不涉及类型P的值。Mapper类型类使用隐式解析来寻找Case实例，所以编译器只需要知道P的单例类型就能从其中加载定义好的相关Case实例。

我们来创建一个扩展方法以使ProductMapper更容易被调用，用户在调用的时候只需要指定B的类型。可以使用一些间接的方式来让编译器从值参数推导Poly的类型。此处定义了一个Builder类，并为该类提供一个泛型的apply方法，传入一个poly变量和一个隐式的ProductMapper对象，这样就能自动的根据B的类型推断出P的类型。代码如下：

```scala
implicit class ProductMapperOps[A](a: A) {
    class Builder[B] {
        def apply[P <: Poly](poly: P) 
            (implicit pm: ProductMapper[A, B, P]): B =
            pm.apply(a) 
    }

    def mapTo[B]: Builder[B] = new Builder[B]
}
```

下面是上述方法的使用样例：

```scala
object conversions extends Poly1 {
    implicit val intCase: Case.Aux[Int, Boolean] = at(_ > 0) 
    implicit val boolCase: Case.Aux[Boolean, Int] = at(if(_) 1 else 0)
    implicit val strCase: Case.Aux[String, String] = at(identity) 
}

case class IceCream1(name: String, numCherries: Int, inCone: Boolean) 
case class IceCream2(name: String, hasCherries: Boolean, numCones: Int)

IceCream1("Sundae", 1, false).mapTo[IceCream2](conversions)
// res2: IceCream2 = IceCream2(Sundae,true,0)
```

mapTo语法看上去像一个单一的调用，但实际上是两次：一次调用mapTo确定B类型参数，另一次调用Builder.apply方法来指定Poly的类型。一些shapeless的内置的ops扩展方法使用相似的技巧为用户提供方便。

## 7.6 小结 

这一章我们讨论了多态函数，它的返回类型基于参数类型的变化而变化。我们看到了shapeless的Poly类型是如何定义的以及如何用它来实现像map、flatMap、foldLeft和foldRight这样的函数化操作。

每一个对HList实例的操作都是作为扩展方法而实现的，并基于对应的类型类：Mapper、FlatMapper、LeftFolder等等。我们能使用这些类型类、Poly类型和在4.3节中介绍的技巧来创建我们自己的类型类，它涉及复杂的转换过程，最终达到我们想要的效果。