---
title: 什么是泛型编程
categories:
- Shapeless
tags: [Scala]
description: Scala的泛型编程库Shapeless的入门教程-1
---

* 目录
{:toc}

> 在线电子书 https://dreamylost.gitbook.io/dreamylost/

> shapeless v2.3.2

类型（Type）很有用，因为它是明确的——它向我们展示不同的代码片段如何组合到一起，帮助我们消除BUG并在编写代码的时候指导我们解决问题。

然而有时类型又太具体导致大量重复编码，所以有些情形下我们想利用不同类型之间的相似性来去除重复编码工作。例如，考虑以下两个类型定义：

```scala
case class Employee(name: String, number: Int, manager: Boolean)

case class IceCream(name: String, numCherries: Int, inCone: Boolean)
```

这两个类代表不同的数据类型，但是它们又非常相似——都包含三个字段且类型相同。假设我们要实现一个对它们都通用的操作，例如序列化到CSV文件。尽管这两类数据相似，但是我们不得不写两个不同的方法。分别如下：

```scala
def employeeCsv(e: Employee): List[String] = 
    List(e.name, e.number.toString, e.manager.toString)

def iceCreamCsv(c: IceCream): List[String] = 
    List(c.name, c.numCherries.toString, c.inCone.toString)
```

泛型编程能够克服像上面这样由于不同数据类型带来的重复操作。shapeless很容易实现将具体的类型泛型化，这样就可以使用同一段代码来操作不同的类型。

比如，我们能用如下代码将Employee和IceCream实例转换成同一类型。如果不理解以下代码也不用担心，本书会在接下来的章节中详细介绍它们。

```scala
import shapeless._

val genericEmployee = Generic[Employee].to(Employee("Dave", 123, false)) 
// genericEmployee: String :: Int :: Boolean :: shapeless.HNil 
// = Dave :: 123 :: false :: HNil

val genericIceCream = Generic[IceCream].to(IceCream("Sundae", 1, false)) 
// genericIceCream: String :: Int :: Boolean :: shapeless.HNil 
//= Sundae :: 1 :: false :: HNil
```

现在两个值变成了相同类型，都是异构的列表（简称HList），它包含一个字符串（String）、一个整型（Int）和一个布尔（Boolean）对象。接下来我们将研究HList类型和它在shapeless中所扮演的重要角色。目前为止关键在于用同一个函数来序列化各自的值，而这些值是上面两种类型被泛型化后的值。代码如下：

```scala
def genericCsv(gen: String :: Int :: Boolean :: HNil): List[String] = 
    List(gen(0), gen(1).toString, gen(2).toString)

genericCsv(genericEmployee) 
// res2: List[String] = List(Dave, 123, false)

genericCsv(genericIceCream) 
// res3: List[String] = List(Sundae, 1, false)
```

这个简单的例子展示了泛型编程的精髓。通过重新探究这些问题，我们不但用泛型代码块解决了问题，而且写出了适用于多种类型的精简代码。使用shapeless进行泛型编程可以消除大量的冗余代码，使Scala应用程序更容易阅读、编写和维护。

听上去是不是很有诱惑？想想这些，让我们一起入坑吧！
