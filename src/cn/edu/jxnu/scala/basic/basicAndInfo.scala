package cn.edu.jxnu.scala

import scala.collection.mutable.ListBuffer

/**
 * 本包存放scala学习笔记
 *
 * 因为我不是零基础，所以不再列举简单的。只记录稍微重点的。希望能帮助到你们，也当是自己的复习，学习的记录。
 *
 * 目前主要做自动化测试、测试平台开发（Java），测试框架开发（Python）
 *
 * 本人未来的目标不出意外也是做类似的框架或工具开发。
 * 学习scala的初衷并不是为了大数据，而是学习函数式的思想。
 *
 * 注意：为了方便 使用@see 标记相关的代码类名，而具体代码放在本包对象外面，方便运行
 *
 * 实践部分参考Scala User Group的csug库 -> https://github.com/CSUG/csug，尽量说明
 *
 * 书籍《Scala编程第三版》《响应式架构 消息模式Actor实现与Scala、Akka应用集成》
 *
 * 《快学Scala》入门  不是友好
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
     * 模式匹配
     * 性状/特质
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
     * abstract	   case	    catch	     class
     * def         do	      else	     extends
     * false       final	  finally	   for
     * forSome	   if	      implicit	 import
     * lazy	       match	  new	       null
     * object      override package    private
     * protected   return   sealed	   super
     * this	       throw	  trait	     try
     * true	       type	    val	       var
     * while	     with	    yield
     * -	         :	      =	         =>
     * <-	         <:	     <%	         >:
     * #	         @
     *
     */

    /**
     * scala的数据类型 - 全部是类，不存在基础类型
     * 1	Byte	8位有符号值，范围从-128至127    java-byte
     * 2	Short	16位有符号值，范围从-32768至32767 -java-short
     * 3	Int	    32位有符号值，范围从-2147483648至2147483647  java-int
     * 4	Long	64位有符号值，范围从-9223372036854775808至9223372036854775807  java-long
     * 5	Float	32位IEEE 754单精度浮点值  java-float
     * 6	Double	64位IEEE 754双精度浮点值  java-double
     * 7	Char	16位无符号Unicode字符。范围从U+0000到U+FFFF  java-char
     * 8	String	一个Char类型序列 java-String
     * 9	Boolean	文字值true或文字值false java-boolean
     * 10	Unit	对应于无值  类似java-void 执行无返回值方法完全是为了副作用：如打印
     * 11	Null	null或空引用  java-null
     * 12	Nothing	每种其他类型的亚型; 不包括无值
     * 13	Any	    任何类型的超类型; 任何对象的类型为Any
     * 14	AnyRef	任何引用类型的超类型
     */

    /**
     * 方法参数方法参数是在调用该方法时用于传递方法中的值的变量。
     * 方法参数只能从方法内部访问，但是如果从方法外部引用了对象，则可以从外部访问传入的对象。
     * 方法参数始终是不可变的，由val关键字定义。（这里有坑，val是隐式定义的，自己写的时候没有写val也是不可变）
     * 可变变量用var定义。应该尽量使用val @see Test3.scala
     *
     * 不能对数值进行++ --操作，Scala不支持 @see Test2.scala
     *
     */
    /** 需要特别注意scala的访问权限
     * 1、访问控制符
     * Scala 访问修饰符基本和Java的一样，分别有：private，protected，public。
     * 如果没有指定访问修饰符符，默认情况下，Scala 对象的访问级别都是 public。
     * Scala 中的 private 限定符，比 Java 更严格，在嵌套类情况下，外层类不能访问被嵌套类的私有成员。
     * */

    /** 在 scala 中，对保护（Protected）成员的访问比 java 更严格一些。因为它只允许保护成员在定义了该成员的的类的子类中被访问。
     * 而在java中，用protected关键字修饰的成员，除了定义了该成员的类的子类可以访问，同一个包里的其他类也可以进行访问。
     * *
     * Scala中，如果没有指定任何的修饰符，则默认为 public。这样的成员在任何地方都可以被访问。
     * Java默认是default，权限大于private但是小于protected
     * */

    /** 2、作用域保护
     * Scala中，访问修饰符可以通过使用限定词强调。格式为:
     * private[x]
     * 这里的x指代某个所属的包、类或单例对象。如果写成private[x],读作"这个成员除了对[…]中的类或[…]中的包中的类及它们的伴生对像可见外，对其它所有类都是private。
     * 这种技巧在横跨了若干包的大型项目中非常有用，它允许你定义一些在你项目的若干子包中可见但对于项目外部的客户却始终不可见的东西。
     * */

    /** 3、Scala 函数
     * 函数是一组一起执行一个任务的语句。 您可以把代码划分到不同的函数中。如何划分代码到不同的函数中是由您来决定的，但在逻辑上，划分通常是根据每个函数执行一个特定的任务来进行的。
     * Scala 有函数和方法，二者在语义上的区别很小。Scala 方法是类的一部分，而函数是一个对象可以赋值给一个变量。换句话来说在类中定义的函数即是方法。
     * 我们可以在任何地方定义函数，甚至可以在函数内定义函数（内嵌函数）。更重要的一点是 Scala 函数名可以有以下特殊字符：+, ++, ~, &,-, -- , \, /, : 等。
     * 通过使用特殊字符可以模仿操作符重载，使得用户定义的函数可以像scala语言本身关键字、操作符那样方便调用：典型的akka !发送消息
     * */

    /** 4、函数声明
     * Scala 函数声明格式如下：
     * def functionName ([参数列表]) : [return type]
     * 如果你不写等于号和方法主体，那么方法会被隐式声明为"抽象(abstract)"，包含它的类型于是也是一个抽象类型。
     * */

    /** 5、函数定义
     * 方法定义由一个def 关键字开始，紧接着是可选的参数列表，一个冒号"：" 和方法的返回类型，一个等于号"="，最后是方法的主体。
     * Scala 函数定义格式如下：
     * def functionName ([参数列表]) : [return type] = {
     * function body
     * return [expr]
     * }
     * 如果函数没有返回值，可以返回为 Unit，这个类似于 Java 的 void，return可以省略，默认返回最后一个表达式（计算）的值，
     * 返回为空的称作过程，我们只是使用它的副作用，如打印
     * */

    /** 6、函数调用 @see Test3.scala
     * Scala 提供了多种不同的函数调用方式：
     * 以下是调用方法的标准格式：
     * functionName( 参数列表 )
     * 如果函数使用了实例的对象来调用，我们可以使用类似java的格式 (使用 . 号)：
     * [instance.]functionName( 参数列表 )
     * 传名调用（call-by-name）：将未计算的参数表达式直接应用到函数内部
     * */

    /** 7、Scala 高阶函数
     * 高阶函数（Higher-Order Function）就是操作其他函数的函数。
     * Scala 中允许使用高阶函数, 高阶函数可以使用其他函数作为参数，或者使用函数作为输出结果。
     * 以下实例中，apply() 函数使用了另外一个函数 f 和 值 v 作为参数，而函数 f 又调用了参数 v：
     *
     * @see Test1.scala
     **/

    /** 函数式编程核心理念：
     * 函数是一等的值
     * 不可变数据结构、无副作用
     */
    /**
     * =================小目录============
     **/
    /**
     * Scala的main方法执行 @see SingletonObject.scala、Test2.scala
     * Scala的for循环中断和List的交并差 @see LoopExamples.scala
     * Scala的数组 @see Test3.scala、Test4.scala
     * Scala中的方法调用 操作符表示法 @see Test4.scala
     * Scala的列表 @see Test6.scala
     * Scala的元组 @see Test7.scala
     * Scala的集合和映射(map) @see Test8.scala
     * Scala的可变集合主要用于命令式传统编程（当Java用），不可变主要用于函数式编程，推荐使用不可变，减少副作用，也更加安全 @see immutable.png、mutable.png
     * Scala从文件读取 @see IOExamples.scala、Test10.scala、Test11.scala
     * Scala 面向对象的类、单例对象、构造函数、序列化、注解使用等  @see CompanionClass.scala
     **/

}

/**
 * 以下开始都是代码实例（基础语法部分）
 */
object Test1 {
    // scala 数组下标用(),泛型用[]，@see Test3.scala
    def main(args: Array[String]) {
        println(apply(layout, 10))
    }

    // 函数 f 和 值 v 作为参数，而函数 f 又调用了参数 v
    def apply(f: Int => String, v: Int) = f(v)

    def layout[A](x: A) = "[" + x.toString() + "]"
}

object Test2 extends App {
    // 继承App 默认有main方法，自己也可以重写main。或者不继承App，自己写main方法，签名需一致
    val string: String = "ABCabc"
    val hasUpper = string.exists(_.isUpper) //_.isUpper是函数字面量，其中_占位符(args:type)=> func body
    println(hasUpper)

    var i = 0 //scala不能用i++ ++i --i i--
    i += 1
    println(i)

    val str = "hello" +
      "world"; //+操作符放在末尾，而不是java那样推荐在前面
    Console println str


    // string.chars().anyMatch((int ch)-> Character.isUpperCase((char)ch)) //java 8
}

object Test3 extends App {
    val arr = new Array[String](3) //这种方式不是函数式编程推荐的，推荐方法@see Test5.scala
    // arr=new Array(2) val不能被重新赋值，但是本身指向的对象可能发生改变比如：改变arr数组内容
    arr(0) = "hello1" //实际上，Scala数组赋值也是函数调用，arr(i)底层调用了apply(i)方法，这是与其他方法调用一致的通用规则
    arr(1) = "hello2" //arr(i) = "hello" 实际上底层调用了update方法 arr.update(0,"hello")
    arr(2) = "hello3"
    arr.foreach(println)
}

//循环中断@see LoopExamples.scala
object Test4 extends App {

    for (i <- 0 to 2) //to包含了右边界  to实际是(0).to(2)的缩写，to返回一种包含了0、1、2的序列。对于单参数方法的调用时  () . 可以被省略
        println(i)
    println("==========================")
    for (i <- 0 until 2) //until不包含右边界 其他同
    // println(i)
        Console println i //省略括号需要显示的给出方法调用的目标对象才有效，此时println是操作符
    //实际上在Scala中任何操作符都是一种方法调用，而任何方法也可以是操作符，但是在多参数的情况下，操作符表示法必须用括号:strings indexOf ('a',startIndex)
    val s = "hello"
    s toLowerCase; //无参，无副作用不用括号,使用后缀需要隔断，用分号
    println() //有副作用用括号
    var num = -1 //-是前缀操作符，实际也是方法调用 可用的前缀操作符：！ + - ~  都是一元的
    var num2 = 1.unary_-
    if (num == num2) println(true) //true 都是-1，Scala的==比较的是值的相等性，不同于Java的==（比较引用的地址或者基本类型的值）
    //若想要比较引用地址可用eq/ne方法，不过这只对Scala对象直接映射到Java对象的对象有效。比如String
    println("======================new eq=====================")
    val str1 = new String("hello")
    val str2 = new String("hello")
    var str3 = "hello"
    var str4 = "hello"
    if (str1 eq str2) println("比较地址") //无输出
    if (str1 == str2) println("==比较内容") //输出
    if (str1 equals str2) println("equals比较内容") //输出
    println("======================常量 eq=====================")

    if (str3 eq str4) println("比较地址") //输出，指向同一个常量池的引用
    if (str3 == str4) println("==比较内容") //输出
    if (str3 equals str4) println("equals比较内容") //输出


}

object Test5 extends App {
    val arr = Array("hello", "world")
    arr.foreach(print)
}

object Test6 extends App {
    val list = List(1, 2, 3) //不需要new，使用函数风格的调用，底层调用了List的伴生对象的工厂方法List.apply()
    //val list = 1::2::3::4::Nil 更麻烦的初始化方法，必须用Nil，因为4是整形没有::方法
    list.foreach(print)
    println()

    val list1 = List(1, 2, 3)
    println("列表拼接")
    val list2 = list ::: list1
    list2.foreach(print)
    println()

    println("向列表头部追加元素")
    val list3 = 2 :: list //以冒号结尾的操作符，方法调用发生在右边，其他的都发生在左边
    //list3.:+(2)//效率低，每次都需要从头部移动到尾部，可以使用ListBuffer可变列表或者使用头部追加再反转列表
    list3.foreach(print)
    println()

    println("=================列表操作================")
    val list4 = List("hello", "world", "12344")
    val ret = list4.count(s => s.length == 5)
    println("列表中字符串长度为5的个数：" + ret)

    println("按照首字母排序")
    //不改变原列表
    val list5 = ListBuffer("hello", "world", "hello")
    val list6 = list5.sortWith((s, t) => s.charAt(0).toUpper < t.charAt(0).toUpper) //与元组不同，List从0开始
    list6.foreach(print)
    println()
}

object Test7 extends App {
    val tuple1 = (11, "hello") //可见，类型可以不同。同样元组也是不可变
    print(tuple1._1) //打印第一个，从1开始，跟随Haskell、ML等静态类型元组设定的语言传统
    print(tuple1._2) //打印第一个，标准库最大22个元组(从概念上可以创建任意长度)
    // print(tuple1(1))//不能使用下标，因为apply是只能返回同种类型

}

object Test8 extends App {

    println("================Set集===========")
    //创建并初始化，不可变Set集合，类似数组，也是调用了伴生对象的apply方法
    var set = Set("hello", "world") //默认集合都是不可变的， 其他集：var hashSet = HashSet("hello","world")
    set += "hhh" //实际是“+”方法[set = set + "hhh"]， 创建并返回新集合，无论是可变还是不可变集
    println(set.contains("hello"))
    // 注意：因为对不可变集使用+=实际是调用+=方法set1.+=("hhh")，所以表达式不需要重新赋值，set1可以是val
    // 而对于可变集，+=实际是+调用后再进行赋值，所以不能为val
    //对于只有一个参数的方法调用可以省略 . () ，所以set1.+=("hhh") ===> set1 += "hhh"
    val set1 = scala.collection.mutable.Set("hello", "world") //提供+=方法
    set1 += "hhh" //实际是调用方法
    println(set1.contains("hhh"))
    println("===============Set遍历=============")
    set1.foreach(print) //遍历
    println
    println("================Map集===========")
    //创建并初始化可变的Map
    val map = scala.collection.mutable.Map[String, String]()
    map += ("a" -> "b") //实际是(a).->("b")方法调用，同样也是省略了. ()
    map += ("b" -> "c") //底层也是+=的方法调用，所以map也可以是val
    map += ("c" -> "d") //Map("hello"->"world","a"->"b")默认是不可变，不再解释
    map.foreach { case (k, v) => println(k + v) } //用小括号报错，，，
    println(map("b"))
    println("=============单独获取key、value============")
    //获取key
    val keys = map.map(_._1) //前面一个占位符表示map的任意元素，后面一个表示任意元素的第一个元素，即 key
    keys.foreach(print)
    println
    //获取value
    val valus = map.map(_._2) //类似
    valus.foreach(print)
    println
    println("===========将两个list转化为一个map===============")
    //反向操作可以使用zip，其中一个list作为key，另一个作为value
    val list = List(1, 2, 3, 4)
    //以少的为基准，不够舍弃
    val scores = list.zip(valus).toMap //list作为key,values作为value
    scores foreach { case (k, v) => println(k + v) }

}

