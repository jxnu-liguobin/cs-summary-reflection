---
title: Option的fold方法使用注意点
categories:
  - Scala
tags:
  - 迁移自简书
---

# 2019-07-11-Scala-Option的fold方法使用注意点

若调用Option的fold方法，柯里化第二个参数的函数返回`Future[Option[String]]`，但是fold的第一个参数（调用者为None时返回此值）却不能使用`Future.successful(None)`? Option类型调用fold时为什么需要`Future.successful[Option[String]](None)`?

这个写法看着很怪，明明是None，None也是Option的，为什么需要加`[Option[String]]`? 可以尝试一下，这写法是必要的。 比如，对于一个图片上传操作，图片URL image 是可能为None的Option类型，并且使用异步Future返回。现在有一个upload方法，定义如下：

```scala
def upload(image:Option[String]):Future[Option[String]] 
//返回图片上传后的地址
```

```scala
//使用fold执行upload方法
val image = "http://xxxxx.xxxxx"
image.fold(Future.successful[Option[String]](None)){ img =>
    upload(img)
}
```

其实这是为了类型推断。 Scala 2.12.8中 List的fold源码

```scala
def fold[A1 >: A](z: A1)(op: (A1, A1) => A1): A1 = foldLeft(z)(op)
```

可以看出 初始化传入的类型A1将被作为返回类型。其本质是使用了左折叠实现。 再重点看 Scala 2.12.8中 Option的fold源码，很简单，定义如下：

```scala
@inline final def fold[B](ifEmpty: => B)(f: A => B): B =
if (isEmpty) ifEmpty else f(this.get)
```

一眼就能看到，为空时返回的类型和不为空时返回的类型需要一致 为 B。

当条件为true，fold会返回调用方传进来的第一个参数ifEmpty，否则继续使用f函数处理。

fold方法最初是为并行计算设计的，内部遍历没有特殊的次序。这意味着完成折叠（初始化/起始）的类型必须是待折叠“集合”的元素类型的超类型才能在完成折叠时正确返回，因为类型可以自动向上转型（类型提升，但无法自动向下转），所以upload方法返回Option类型时，需要超类才能匹配ifEmpty的返回值B，因为Option无可用超类，所以只能是Option。类似返回值是无法用于重载的，返回值同样无法在调用时用于帮助类型推断出真正的类型。

且Option支持协变。定义如下： `sealed abstract class Option[+A] extends Product with Serializable` 所以upload返回 Some/Option 时都是可以使用Option接收。 Some是Option的子类，定义如下： `final case class Some[+A](@deprecatedName('x, "2.12.0") value: A) extends Option[A]` 并且也支持协变。如果使用`Future.successful(None)` 则编译器会期望一个None.type类型，但是很显然，没有一个类型是它的子类，无法通过编译。 None的定义如下：

```scala
case object None extends Option[Nothing] {
  def isEmpty = true
  def get = throw new NoSuchElementException("None.get")
}
```

可见，None是Option类型中的类似null的存在，对None调用get都会报错。

对于协变，若有类型 `A[B]，C[D]`，当且仅当C是A的子类，且D是B的子类，则`C[D]`是`A[B]`的子类，`C[D]`可以通过类型提升到`A[B]`，而不需要强制转换。

逆变相反。统称型变

仅供参考

