---
title: Scala的类型系统和泛型
categories:
- Scala
tags: [Scala]
description: Scala的类型系统和泛型相关基础知识。
---

* 目录
{:toc}

在Scala中，所有的值都有类型，包括数值和函数。下图阐述了类型层次结构的一个子集。

![scala类型系统](../../public/image/unified-types-diagram.svg)

# Scala 类型

Any是所有类型的超类型，也称为顶级类型。它定义了一些通用的方法如 equals、hashCode 和t oString。Any 有两个直接子类：AnyVal 和 AnyRef。

## Scala类型层次结构

AnyVal 代表值类型。有9个预定义的非空的值类型分别是：Double、Float、Long、Int、Short、Byte、Char、Unit和Boolean。其中 String 完全等同 Java String。
Unit是不带任何意义的值类型，它仅有一个实例可以像这样声明：()。所有的函数必须有返回，所以说有时候Unit也是有用的返回类型。

AnyRef 代表引用类型。所有非值类型都被定义为引用类型。在 Scala 中，每个用户自定义的类型都是 AnyRef 的子类型。如果 Scala 被应用在 Java 的运行环境中，AnyRef 相当于 java.lang.Object。

这里有一个例子，说明了字符串、整型、布尔值和函数都是对象，这一点和其他对象一样：

```scala
val list: List[Any] = List(
  "a string",
  732,  // an integer
  'c',  // a character
  true, // a boolean value
  () => "an anonymous function returning a string"
)

list.foreach(element => println(element))
```

这里定义了一个类型 List[Any] 的变量 list。这个列表里由多种类型进行初始化，但是它们都是 scala.Any 的实例，所以可以把它们加入到列表中。

下面是程序的输出：

```
a string
732
c
true
<function>
```

## 类型转换

![scala类型系统](../../public/image/type-casting-diagram.svg)

例如：

```scala
val x: Long = 987654321
val y: Float = x  // 9.8765434E8 (注意，在这种情况下会丢失一些精度)

val face: Char = '☺'
val number: Int = face  // 9786
```

转换是单向，下面这样写将不会通过编译。

```scala
val x: Long = 987654321
val y: Float = x  // 9.8765434E8
val z: Long = y  // 不符合
```

你可以将一个类型转换为子类型，这点将在后面的文章介绍。

## Nothing 和 Null

Nothing 是所有类型的子类型，也称为底部类型。没有一个值是 Nothing 类型的。
它的用途之一是给出非正常终止的信号，如抛出异常、程序退出或者一个无限循环（可以理解为它是一个不对值进行定义的表达式的类型，或者是一个不能正常返回的方法）。

Null 是所有引用类型的子类型（即 AnyRef 的任意子类型）。它有一个单例值由关键字 null 所定义。
Null 主要是使得 Scala 满足和其他 JVM 语言的互操作性，但是几乎不应该在 Scala 代码中使用。我们将在后面的章节中介绍 null 的替代方案。（使用 Option 比较好）

# 泛型类

泛型类指可以接受类型参数的类。泛型类在集合类中被广泛使用。与Java类似，只不过不是使用尖括号，而是中括号。

## 定义一个泛型类

泛型类使用方括号 [] 来接受类型参数。一个惯例是使用字母 A 作为参数标识符，当然你可以使用任何参数名称。

```scala
class Stack[A] {
  //这里的Stack不是尾出尾进，而是头进头出，这样有利于函数式操作，能使用tail head
  private var elements: List[A] = Nil
  def push(x: A) { elements = x :: elements }
  def peek: A = elements.head
  def pop(): A = {
    val currentTop = peek
    elements = elements.tail
    currentTop
  }
}
```

上面的 Stack 类的实现中接受类型参数 A。这表示其内部的列表，var elements: List[A] = Nil，只能够存储类型 A 的元素。
方法 def push 只接受类型 A 的实例对象作为参数（注意：elements = x :: elements 将 elements 放到了一个将元素 x 添加到 elements 的头部而生成的新列表中）。

## 使用

要使用一个泛型类，将一个具体类型放到方括号中来代替 A。

```scala
val stack = new Stack[Int]
stack.push(1)
stack.push(2)
println(stack.pop)  // 输出 2
println(stack.pop)  // 输出 1
```

实例对象 stack 只能接受整型值。然而，如果类型参数有子类型，子类型可以被传入：

```scala
class Fruit
class Apple extends Fruit
class Banana extends Fruit

val stack = new Stack[Fruit]
val apple = new Apple
val banana = new Banana

stack.push(apple)
stack.push(banana)
```

类 Apple 和类 Banana 都继承自类 Fruit，所以我们可以把实例对象 apple 和 banana 压入栈 Fruit 中。

> 泛型类型的子类型是**不可传导**的。这表示如果我们有一个字母类型的栈 Stack[Char]，那它不能被用作一个整型的栈 Stack[Int]。否则就是不安全的，因为它将使我们能够在字母型的栈中插入真正的整型值。结论就是，只有当类型 B = A 时， Stack[A] 是 Stack[B] 的子类型才成立。因为此处可能会有很大的限制，Scala 提供了一种类型参数注释机制用以控制泛型类型的子类型的行为。这就是型变。

# 型变

型变是复杂类型的子类型关系与其组件类型的子类型关系的相关性。Scala支持泛型类的类型参数的型变注释，允许它们是协变的，逆变的，或在没有使用注释的情况下是不变的。
在类型系统中使用型变允许我们在复杂类型之间建立直观的连接，而缺乏型变则会限制类抽象的重用性。

```scala
class Foo[+A] // 协变
class Bar[-A] // 逆变
class Baz[A]  // 不变
```

## 协变

使用注释 +A，可以使一个泛型类的类型参数 A 成为协变。对于某些类 class List[+A]，使 A 成为协变意味着对于两种类型 A 和 B，如果 A 是 B 的子类型，那么 List[A] 就是 List[B] 的子类型。这允许我们使用泛型来创建非常有用和直观的子类型关系。
考虑以下简单的类结构：

```scala
abstract class Animal {
  def name: String
}
case class Cat(name: String) extends Animal
case class Dog(name: String) extends Animal
```

类型 Cat 和 Dog 都是 Animal 的子类型。Scala 标准库有一个通用的不可变的类 sealed abstract class List[+A]，其中类型参数 A 是协变的。
这意味着 List[Cat] 是 List[Animal]，List[Dog] 也是 List[Animal]。直观地说，猫的列表和狗的列表都是动物的列表是合理的，你应该能够用它们中的任何一个替换 List[Animal]。

在下例中，方法 printAnimalNames 将接受动物列表作为参数，并且逐行打印出它们的名称。如果 List[A] 不是协变的，最后两个方法调用将不能编译，这将严重限制 printAnimalNames 方法的适用性。

```scala
object CovarianceTest extends App {
  def printAnimalNames(animals: List[Animal]): Unit = {
    animals.foreach { animal =>
      println(animal.name)
    }
  }

  val cats: List[Cat] = List(Cat("Whiskers"), Cat("Tom"))
  val dogs: List[Dog] = List(Dog("Fido"), Dog("Rex"))

  printAnimalNames(cats)
  // Whiskers
  // Tom

  printAnimalNames(dogs)
  // Fido
  // Rex
}
```

## 逆变

通过使用注释 -A，可以使一个泛型类的类型参数 A 成为逆变。与协变类似，这会在类及其类型参数之间创建一个子类型关系，但其作用与协变完全相反。也就是说，对于某个类 class Writer[-A] ，使 A 逆变意味着对于两种类型 A 和 B，如果 A 是 B 的子类型，那么 Writer[B] 是 Writer[A] 的子类型。

考虑在下例中使用上面定义的类 Cat，Dog 和 Animal ：

```scala
abstract class Printer[-A] {
  def print(value: A): Unit
}
```

这里 Printer[A] 是一个简单的类，用来打印出某种类型的 A。让我们定义一些特定的子类：

```scala
class AnimalPrinter extends Printer[Animal] {
  def print(animal: Animal): Unit =
    println("The animal's name is: " + animal.name)
}

class CatPrinter extends Printer[Cat] {
  def print(cat: Cat): Unit =
    println("The cat's name is: " + cat.name)
}
```

如果 Printer[Cat] 知道如何在控制台打印出任意 Cat，并且 Printer[Animal] 知道如何在控制台打印出任意 Animal，那么 Printer[Animal] 也应该知道如何打印出 Cat 就是合理的。反向关系不适用，因为 Printer[Cat] 并不知道如何在控制台打印出任意 Animal。因此，如果我们愿意，我们应该能够用 Printer[Animal] 替换 Printer[Cat]，而使 Printer[A] 逆变允许我们做到这一点。

```scala
object ContravarianceTest extends App {
  val myCat: Cat = Cat("Boots")

  def printMyCat(printer: Printer[Cat]): Unit = {
    printer.print(myCat)
  }

  val catPrinter: Printer[Cat] = new CatPrinter
  val animalPrinter: Printer[Animal] = new AnimalPrinter

  printMyCat(catPrinter)
  printMyCat(animalPrinter)
}
```

这个程序的输出如下：

```scala
The cat's name is: Boots
The animal's name is: Boots
```

## 不变

默认情况下，Scala中的泛型类是不变的。这意味着它们既不是协变的也不是逆变的。在下例中，类 Container 是不变的。Container[Cat] 不是 Container[Animal]，反之亦然。

```scala
class Container[A](value: A) {
  private var _value: A = value
  def getValue: A = _value
  def setValue(value: A): Unit = {
    _value = value
  }
}
```

可能看起来一个 Container[Cat] 自然也应该是一个 Container[Animal]，但允许一个可变的泛型类成为协变并不安全。在这个例子中，Container 是不变的非常重要。假设 Container 实际上是协变的，下面的情况可能会发生：

```scala
val catContainer: Container[Cat] = new Container(Cat("Felix"))
val animalContainer: Container[Animal] = catContainer
animalContainer.setValue(Dog("Spot"))
val cat: Cat = catContainer.getValue // 糟糕，我们最终会将一只狗作为值分配给一只猫
```

幸运的是，编译器在此之前就会阻止我们。

## 其他例子

另一个可以帮助理解型变的例子是 Scala 标准库中的 trait Function1[-T, +R]。Function1 表示具有一个参数的函数，其中第一个类型参数 T 表示参数类型，第二个类型参数 R 表示返回类型。Function1 在其参数类型上是逆变的，并且在其返回类型上是协变的。对于这个例子，我们将使用文字符号 A => B 来表示 Function1[A, B]。

假设前面使用过的类似 Cat，Dog，Animal 的继承关系，加上以下内容：

```scala
abstract class SmallAnimal extends Animal
case class Mouse(name: String) extends SmallAnimal
```

假设我们正在处理接受动物类型的函数，并返回他们的食物类型。如果我们想要一个 Cat => SmallAnimal（因为猫吃小动物），但是给它一个 Animal => Mouse，我们的程序仍然可以工作。直观地看，一个 Animal => Mouse 的函数仍然会接受一个 Cat 作为参数，因为 Cat 即是一个 Animal，并且这个函数返回一个 Mouse，也是一个 SmallAnimal。既然我们可以安全地，隐式地用后者代替前者，我们可以说 Animal => Mouse 是 Cat => SmallAnimal 的子类型。

## 与其他语言的比较

某些与 Scala 类似的语言以不同的方式支持型变。例如，Scala 中的型变注释与 C# 中的非常相似，在定义类抽象时添加型变注释（声明点型变）。但是在Java中，当类抽象被使用时（使用点型变），才会给出型变注释。

# 类型上界

在Scala中，类型参数和抽象类型都可以有一个类型边界约束。这种类型边界在限制类型变量实际取值的同时还能展露类型成员的更多信息。
比如像T <: A这样声明的类型上界表示类型变量T应该是类型A的子类。下面的例子展示了类PetContainer的一个类型参数的类型上界。

```scala
abstract class Animal {
 def name: String
}

abstract class Pet extends Animal {}

class Cat extends Pet {
  override def name: String = "Cat"
}

class Dog extends Pet {
  override def name: String = "Dog"
}

class Lion extends Animal {
  override def name: String = "Lion"
}

class PetContainer[P <: Pet](p: P) {
  def pet: P = p
}

val dogContainer = new PetContainer[Dog](new Dog)
val catContainer = new PetContainer[Cat](new Cat)

// this would not compile
val lionContainer = new PetContainer[Lion](new Lion)
```

类PetContainer接受一个必须是Pet子类的类型参数P。因为Dog和Cat都是Pet的子类，所以可以构造PetContainer[Dog]和PetContainer[Cat]。但在尝试构造PetContainer[Lion]的时候会得到下面的错误信息：

```
type arguments [Lion] do not conform to class PetContainer's type parameter bounds [P <: Pet]
```

这是因为Lion并不是Pet的子类。

# 类型下界

类型上界 将类型限制为另一种类型的子类型，而类型下界将类型声明为另一种类型的超类型。术语 B >: A 表示类型参数 B 或抽象类型 B 是类型 A 的超类型。在大多数情况下，A 将是类的类型参数，而 B 将是方法的类型参数。

下面看一个适合用类型下界的例子：

```scala
trait Node[+B] {
  def prepend(elem: B): Node[B]
}

case class ListNode[+B](h: B, t: Node[B]) extends Node[B] {
  def prepend(elem: B): ListNode[B] = ListNode(elem, this)
  def head: B = h
  def tail: Node[B] = t
}

case class Nil[+B]() extends Node[B] {
  def prepend(elem: B): ListNode[B] = ListNode(elem, this)
}
```

该程序实现了一个单链表。Nil 表示空元素（即空列表）。class ListNode 是一个节点，它包含一个类型为 B (head) 的元素和一个对列表其余部分的引用 (tail)。class Node 及其子类型是协变的，因为我们定义了 +B。

但是，这个程序不能编译，因为方法 prepend 中的参数 elem 是协变的 B 类型。这会出错，因为函数的参数类型是逆变的，而返回类型是协变的。

要解决这个问题，我们需要将方法 prepend 的参数 elem 的型变翻转。我们通过引入一个新的类型参数 U 来实现这一点，该参数具有 B 作为类型下界。

> 在Scala中，函数其实也是对象，它是scala.Function0 -> Scala.Function22的对象。既然是对象，那么它就可以有不同的实现。在这些trait中，定义了apply方法，apply接受的参数就是函数的参数，而apply的返回值就是函数的返回值。

函数的泛型定义如下

```scala
trait Function1[-T1, +R] {
  def apply(v1: T1): R
}
```

可以看到，对于任何Scala函数，参数类型都是逆变的，而返回类型是协变的。

```scala
trait Node[+B] {
  def prepend[U >: B](elem: U): Node[U]
}

case class ListNode[+B](h: B, t: Node[B]) extends Node[B] {
  def prepend[U >: B](elem: U): ListNode[U] = ListNode(elem, this)
  def head: B = h
  def tail: Node[B] = t
}

case class Nil[+B]() extends Node[B] {
  def prepend[U >: B](elem: U): ListNode[U] = ListNode(elem, this)
}
```

现在我们像下面这么做：

```scala
trait Bird
case class AfricanSwallow() extends Bird
case class EuropeanSwallow() extends Bird


val africanSwallowList= ListNode[AfricanSwallow](AfricanSwallow(), Nil())
val birdList: Node[Bird] = africanSwallowList
birdList.prepend(EuropeanSwallow())
```

可以为 Node[Bird] 赋值 africanSwallowList，然后再加入一个 EuropeanSwallow。

# 抽象类型

特质和抽象类可以包含一个抽象类型成员，意味着实际类型可由具体实现来确定。例如：

```scala
trait Buffer {
  type T
  val element: T
}
```

这里定义的抽象类型T是用来描述成员element的类型的。通过抽象类来扩展这个特质后，就可以添加一个类型上边界来让抽象类型T变得更加具体。

```scala
abstract class SeqBuffer extends Buffer {
  type U
  type T <: Seq[U]
  def length = element.length
}
```

注意这里是如何借助另外一个抽象类型U来限定类型上边界的。通过声明类型T只可以是 Seq[U] 的子类（其中U是一个新的抽象类型），这个SeqBuffer类就限定了缓冲区中存储的元素类型只能是序列。

含有抽象类型成员的特质或类（classes）经常和匿名类的初始化一起使用。为了能够阐明问题，下面看一段程序，它处理一个涉及整型列表的序列缓冲区。

```scala
abstract class IntSeqBuffer extends SeqBuffer {
  type U = Int
}


def newIntSeqBuf(elem1: Int, elem2: Int): IntSeqBuffer =
  new IntSeqBuffer {
       type T = List[U]
       val element = List(elem1, elem2)
     }
val buf = newIntSeqBuf(7, 8)
println("length = " + buf.length)
println("content = " + buf.element)
```

这里的工厂方法 newIntSeqBuf 使用了 IntSeqBuf 的匿名类实现方式，其类型T被设置成了 List[Int]。

把抽象类型成员转成类的类型参数或者反过来，也是可行的。如下面这个版本只用了类的类型参数来转换上面的代码：

```scala
abstract class Buffer[+T] {
  val element: T
}
abstract class SeqBuffer[U, +T <: Seq[U]] extends Buffer[T] {
  def length = element.length
}

def newIntSeqBuf(e1: Int, e2: Int): SeqBuffer[Int, Seq[Int]] =
  new SeqBuffer[Int, List[Int]] {
    val element = List(e1, e2)
  }

val buf = newIntSeqBuf(7, 8)
println("length = " + buf.length)
println("content = " + buf.element)
```

需要注意的是为了隐藏从方法newIntSeqBuf返回的对象的具体序列实现的类型，这里的型变标号（+T <: Seq[U]）是必不可少的。此外要说明的是，有些情况下用类型参数替换抽象类型是行不通的。

# 复合类型

在Scala中，这可以表示成复合类型，即多个类型的交集。

假设我们有两个特质 Cloneable 和 Resetable：

```scala
trait Cloneable extends java.lang.Cloneable {
  override def clone(): Cloneable = {
    super.clone().asInstanceOf[Cloneable]
  }
}
trait Resetable {
  def reset: Unit
}
```

现在假设我们要编写一个方法cloneAndReset，此方法接受一个对象，克隆它并重置原始对象：

```scala
def cloneAndReset(obj: ?): Cloneable = {
  val cloned = obj.clone()
  obj.reset
  cloned
}
```

obj 如果类型是 Cloneable 那么参数对象可以被克隆 clone，但不能重置 reset; 这里出现一个问题，参数的类型是什么。如果类型是 Resetable 我们可以重置 reset 它，却没有克隆 clone 操作。为了避免在这种情况下进行类型转换，我们可以将obj类型的同时指定为 Cloneable 和 Resetable。这种复合类型在 Scala 中写成：Cloneable with Resetable。

以下是更新后的方法：

```scala
def cloneAndReset(obj: Cloneable with Resetable): Cloneable = {
  //...
}
```

复合类型可以由多个对象类型构成，这些对象类型可以有少量细化，用于替换现有对象成员的签名。A with B with C ... { //... }

# 自类型

自类型用于声明一个特质必须混入其他特质，尽管该特质没有直接扩展其他特质。这使得所依赖的成员可以在没有导入的情况下使用。

自类型是一种细化 this 或 this 别名之类型的方法。语法看起来像普通函数语法，但是意义完全不一样。

要在特质中使用自类型，写一个标识符，跟上要混入的另一个特质，以及 =>（例如 someIdentifier: SomeOtherTrait =>）。

```scala
trait User {
  def username: String
}

trait Tweeter {
  this: User =>  // 重新赋予 this 的类型
  def tweet(tweetText: String) = println(s"$username: $tweetText")
}

class VerifiedTweeter(val username_ : String) extends Tweeter with User {  // 我们混入特质 User 因为 Tweeter 需要
	def username = s"real $username_"
}

val realBeyoncé = new VerifiedTweeter("Beyoncé")
realBeyoncé.tweet("Just spilled my glass of lemonade")  // 打印出 "real Beyoncé: Just spilled my glass of lemonade"
```

因为我们在特质 trait Tweeter 中定义了 this: User =>，现在变量 username 可以在 tweet 方法内使用。这也意味着，由于 VerifiedTweeter 继承了 Tweeter，它还必须混入 User（使用 with User）。cake pattern 就是基于这个。