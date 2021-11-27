Scala基础语法学习笔记
---

本次仅整理，时间久远，可能不准确

# Scala 入门概念
     
* Scala是面向对象语言
 
 
    Scala是一种纯粹的面向对象语言，每一个值都是一个对象。 对象的类型和行为由类和特征描述。
    类通过子类化和基于灵活的基于混合组合机制进行扩展，作为多重继承的干净替代。

* Scala是函数式编程语言

    
    Scala也是一种函数式语言，每个函数都是一个值，每个值都是一个对象，所以每个函数都是一个对象。
    Scala提供了一个轻量级的语法来定义匿名函数，它支持高阶函数，它允许函数嵌套，并支持currying。

* Scala是静态类型的


    Scala与其他静态类型语言(C，Pascal，Rust等)不同，它不提供冗余类型的信息。 在大多数情况下，您不需要指定类型，当然减少了不必的重复。

* Scala运行在JVM上


    Scala代码被编译成由Java虚拟机(JVM)执行的Java字节代码，这意味着Scala和Java具有通用的运行时平台。因此，可以轻松地从Java迁移到Scala。
    Scala编译器将Scala代码编译成Java字节代码，然后可以通过scala命令执行。scala命令类似于java命令，因为它执行编译Scala代码。
    （PS：混合编程或迁移Java实际会出现不少问题）

* Scala可以执行Java代码
    
    
    Scala能够使用Java SDK的所有类以及自定义Java类，或您最喜欢的Java开源项目。

* Scala可以做并发和同步处理
        

    Scala允许您以有效的方式表达一般的编程模式。它减少了线路数量，并帮助程序员以类型安全的方式进行编码。
    它允许您以不变的方式编写代码，这使得应用并发和并行性(Synchronize)变得容易。

* Scala与JavaScala具有与Java完全不同的一组功能，其中的一些如下（所有类型都是对象）

```
类型推断
嵌套函数
函数是对象
DSL
模式匹配
性状/特质
型变
闭包
并发支持
灵感来自Erlang
```

* Scala Web框架
        
```        
Lift Framework
Play Framework
Bowler Framework
```

* Scala中的保留字
  
```
abstract    case       catch      class
def         do         else       extends
false       final      finally    for
forSome     if         implicit   import
lazy	    match      new	  null
object      override   package    private
protected   return     sealed     super
this	    throw      trait      try
true	    type       val	  var
while       with       yield
-	      :         =         =>
<-	      <:        <%        >:
#	      @
```
     
* scala的数据类型 - 全部是类，不存在基础类型

```
1	Byte	8位有符号值，范围从-128至127    java-byte
2	Short   16位有符号值，范围从-32768至32767 -java-short
3	Int     32位有符号值，范围从-2147483648至2147483647  java-int
4	Long	64位有符号值，范围从-9223372036854775808至9223372036854775807  java-long
5	Float	32位IEEE 754单精度浮点值  java-float
6	Double	64位IEEE 754双精度浮点值  java-double
7	Char	16位无符号Unicode字符。范围从U+0000到U+FFFF  java-char
8	String	一个Char类型序列 java-String，完全等价
9	Boolean	文字值true或文字值false java-boolean
10	Unit	对应于无值  类似java-void 执行无返回值方法完全是为了副作用：如打印。唯一值 ()
11	Null	null或空引用  java-null
12	Nothing	每种其他类型的亚型; 不包括无值
13	Any     任何类型的超类型; 任何对象的类型为Any
14	AnyRef	任何引用类型的超类型
```

Scala类型系统继承结构图

![Scala类型系统继承结构图](https://github.com/jxnu-liguobin/cs-summary-reflection/blob/master/docs/public/image/scala%E7%B1%BB%E5%9E%8B%E7%B3%BB%E7%BB%9F%E7%BB%93%E6%9E%84.jpg)

方法参数方法参数是在调用该方法时用于传递方法中的值的变量。
方法参数只能从方法内部访问，但是如果从方法外部引用了对象，则可以从外部访问传入的对象。
方法参数始终是不可变的，由val关键字定义。（这里有坑，val是隐式定义的，自己写的时候没有写val也是不可变，函数每次调用的时候val有重写的值，所以val不能说是常量，绝对的常量应该加final）
可变变量用var定义。应该尽量使用val，不能对数值进行++ --操作，Scala不支持这个操作符以及三目运算符。

* 访问控制符

Scala 访问修饰符基本和Java的一样，分别有：private，protected，public。
如果没有指定访问修饰符符，默认情况下，Scala 对象的访问级别都是 public。
Scala 中的 private 限定符，比 Java 更严格，在嵌套类情况下，外层类不能访问被嵌套类的私有成员。（内部可以访问外层的私有，不如内部/层就没意义了。。。）

在 scala 中，对保护（Protected）成员的访问比 java 更严格一些。因为它只允许保护成员在定义了该成员的的类的子类中被访问。
而在java中，用protected关键字修饰的成员，除了定义了该成员的类的子类可以访问，同一个包里的其他类也可以进行访问。

Scala中，如果没有指定任何的修饰符，则默认为 public。这样的成员在任何地方都可以被访问。
Java默认是default，权限大于private但是小于protected

* 作用域保护

Scala中，访问修饰符可以通过使用限定词强调。格式为:
private[x]
这里的x指代某个所属的包、类或单例对象。如果写成private[x],读作"这个成员除了对[…]中的类或[…]中的包中的类及它们的伴生对像可见外，对其它所有类都是private。
这种技巧在横跨了若干包的大型项目中非常有用，它允许你定义一些在你项目的若干子包中可见但对于项目外部的客户却始终不可见的东西。

* Scala 函数

函数是一组一起执行一个任务的语句。 您可以把代码划分到不同的函数中。如何划分代码到不同的函数中是由您来决定的，但在逻辑上，划分通常是根据每个函数执行一个特定的任务来进行的。
Scala 有函数和方法，二者在语义上的区别很小。Scala 方法是类的一部分，而函数是一个对象可以赋值给一个变量。换句话来说在类中定义的函数即是方法。
我们可以在任何地方定义函数，甚至可以在函数内定义函数（内嵌函数）。更重要的一点是 Scala 函数名可以有以下特殊字符：+, ++, ~, &,-, -- , \, /, : 等。
通过使用特殊字符可以模仿操作符重载，使得用户定义的函数可以像scala语言本身关键字、操作符那样方便调用：典型的akka ! 发送消息


* 三要素
    
```
函数是一等的值
不可变数据结构
无副作用
```   
     
此图由playscala.cn作者作，并授权使用

Scala基本语法图解
![Scala基本语法图解](https://github.com/jxnu-liguobin/jxnu-liguobin.github.io/blob/master/public/image/Scala%E8%AF%AD%E6%B3%95%E5%9B%BE%E8%A7%A3.png)

不可变集合继承结构
![不可变集合继承结构](https://github.com/jxnu-liguobin/jxnu-liguobin.github.io/blob/master/public/image/immutable.png)

可变集合继承结构
![可变集合继承结构](https://github.com/jxnu-liguobin/jxnu-liguobin.github.io/blob/master/public/image/mutable.png)
