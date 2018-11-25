package cn.edu.jxnu.scala

/**
  * 本包存放scala学习笔记
  *
  * 因为我不是零基础，所以不再列举简单的。只记录稍微重点的。因为能帮助到你们
  *
  * 目前主要做自动化测试、测试平台开发，测试框架开发、测试工具开发
  *
  * 本人未来的目标不出意外也是做类似的框架或工具开发。
  * 学习scala的初衷并不是为了大数据，而是学习函数式的思想。
  *
  * @author 梦境迷离
  * @time 2018-11-24
  */
package object basic {

    /**
      * scala特点
      *
      * Scala是面向对象语言
      * Scala是一种纯粹的面向对象语言，每一个值都是一个对象。 对象的类型和行为由类和特征描述。
      * 类通过子类化和基于灵活的基于混合组合机制进行扩展，作为多重继承的干净替代。
      *
      * Scala是函数式编程语言
      * Scala也是一种函数式语言，每个函数都是一个值，每个值都是一个对象，所以每个函数都是一个对象。
      * Scala提供了一个轻量级的语法来定义匿名函数，它支持高阶函数，它允许函数嵌套，并支持currying。
      *
      * Scala是静态类型的
      * Scala与其他静态类型语言(C，Pascal，Rust等)不同，它不提供冗余类型的信息。 在大多数情况下，您不需要指定类型，当然减少了不必的重复。
      *
      * Scala运行在JVM上
      * Scala代码被编译成由Java虚拟机(JVM)执行的Java字节代码，这意味着Scala和Java具有通用的运行时平台。因此，可以轻松地从Java迁移到Scala。
      * Scala编译器将Scala代码编译成Java字节代码，然后可以通过scala命令执行。scala命令类似于java命令，因为它执行编译Scala代码。
      * （PS：混合编程或迁移Java实际会出现不少问题）
      *
      * Scala可以执行Java代码
      * Scala能够使用Java SDK的所有类以及自定义Java类，或您最喜欢的Java开源项目。
      *
      * Scala可以做并发和同步处理
      * Scala允许您以有效的方式表达一般的编程模式。它减少了线路数量，并帮助程序员以类型安全的方式进行编码。
      * 它允许您以不变的方式编写代码，这使得应用并发和并行性(Synchronize)变得容易。
      */

    /**
      * Scala与JavaScala具有与Java完全不同的一组功能，其中的一些如下
      * -所有类型都是对象
      * 类型推断
      * 嵌套函数
      * 函数是对象
      * 域特定语言(DSL)支持
      * 性状
      * 闭包
      * 并发支持
      * 灵感来自Erlang
      */

    /**
      * Scala Web框架
      *
      * Lift Framework
      * Play Framework
      * Bowler Framework
      */

    /** 闭包 - 闭包是一个函数，其返回值取决于在此函数之外声明的一个或多个变量的值。
      *
      * Scala中的保留字(关键字)，这些保留字不能用作常量或变量或任何其他标识符名称。
      * abstract	case	 catch	    class
      * def         do	     else	    extends
      * false       final	 finally	for
      * forSome	    if	     implicit	import
      * lazy	    match	 new	    null
      * object      override package    private
      * protected   return   sealed	    super
      * this	    throw	 trait	    try
      * true	    type	 val	    var
      * while	    with	 yield
      * -	         :	      =	         =>
      * <-	         <:	     <%	         >:
      * #	         @
      *
      */

    /**
      * scala的数据类型 - 全部是类，不存在基础类型
      * 1	Byte	8位有符号值，范围从-128至127
      * 2	Short	16位有符号值，范围从-32768至32767
      * 3	Int	    32位有符号值，范围从-2147483648至2147483647
      * 4	Long	64位有符号值，范围从-9223372036854775808至9223372036854775807
      * 5	Float	32位IEEE 754单精度浮点值
      * 6	Double	64位IEEE 754双精度浮点值
      * 7	Char	16位无符号Unicode字符。范围从U+0000到U+FFFF
      * 8	String	一个Char类型序列
      * 9	Boolean	文字值true或文字值false
      * 10	Unit	对应于无值
      * 11	Null	null或空引用
      * 12	Nothing	每种其他类型的亚型; 不包括无值
      * 13	Any	    任何类型的超类型; 任何对象的类型为Any
      * 14	AnyRef	任何引用类型的超类型
      */

    /**
      * 方法参数方法参数是在调用该方法时用于传递方法中的值的变量。
      * 方法参数只能从方法内部访问，但是如果从方法外部引用了对象，则可以从外部访问传入的对象。
      * 方法参数始终是不可变的，由val关键字定义。（这里有坑，val是隐式定义的，自己写的时候没有写val也是不可变）
      */
}
