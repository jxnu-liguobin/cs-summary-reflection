/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost

/**
  * 函数式对象即是：没有任何可变属性的对象
  * ============================方法重载、重写、前置条件检查、隐式转换==============================
  *
  * n:分子
  * d:分母
  *
  * @see 《Scala编程》 P97
  */
class Rantional(val n: Int, val d: Int) {
  //给构造方法加前置条件
  require(d != 0)

  //    println(n + "/" + d) //构造对象时执行
  //重写非抽象方法，必须加override
  override def toString: String = n + "/" + d

  def this(n: Int) {
    this(n, 1)
  }

  //重载
  def *(that: Rantional): Rantional = new Rantional(that.n * n, that.d * d)

  // 只是为了演示
  def *(i: Int): Rantional = new Rantional(n * i, d * i)

  //重载
  def test() = "test"

  //重载
  def test(name: String) = "test" + name

  //    implicit def intToRational(x: Int) = new Rantional(x) //定义在这里，下面访问不到
}

object TestRantional extends App {
  val r = new Rantional(1, 2) // 打印1/2
  println(r)
  println("================定义操作符==============")
  //    val a = new Rantional(1, 0) // 抛出IllegalArgumentException异常，构建对象失败
  val c = new Rantional(1, 2)
  val ret = r * c
  val ret1 = r.*(c)
  println(ret) // 输出1/4
  println(ret1) // 输出1/4
  println("================重载方法===============")
  println(ret.test("name"))
  println(ret.test)
  println("================隐式转换===============")
  val n = new Rantional(2, 2)
  println(n * 3) // 输出2*3/2*3 //这里不需要隐式转换等同 n.*(3)
  implicit def intToRational(x: Int) = new Rantional(x)

  //可以定义隐式类，作为类型的转换
  println(3 * n) // 上面定义了隐式转换，不报错，否则3.*(n) 会提示3是Int没有*方法
  println(3.*(n)) //等同new Rantional(3,1)*Rantional(2,2)

  /**
    * 隐式类
    * 在scala2.10后提供了隐式类，可以使用implicit声明类，但是需要注意以下几点：
    * 1.其所带的构造参数有且只能有一个
    * 2.隐式类必须被定义在类，伴生对象和包对象里
    * 3.隐式类不能是case class（case class在定义会自动生成伴生对象与2矛盾）
    * 4.作用域内不能有与之相同名称的标示符
    *
    * 转换前提
    * 1.不存在二义性（如例1）
    * 2.隐式操作不能嵌套使用，即一次编译只隐式转换一次(One-at-a-time Rule)
    * Scala不会把 x + y 转换成 convert1(convert2(x)) + y
    * 3.代码能够在不使用隐式转换的前提下能编译通过，就不会进行隐式转换。
    *
    * @param name
    */
  implicit class implicitClass(val name: String) //隐式类必须用要有一个有一个参的主构造方法

}

/**
  * 函数字面量与部分应用函数
  */
object Function extends App {

  //函数字面量，一等函数
  val list = List(1, 2, 3, 4, 5, 6, 5)
  val f = (x: Int) => x > 5 // 函数是一个值
  val ret = list.filter(x => x > 5) //(x:Int) => x >5 缩写去掉类型。隐藏了目标的类型推断。整个filter接受一个函数字面量
  val ret2 = list.filter(f)
  for (r <- ret) {
    println(r)
  }
  for (r <- ret2) {
    println(r)
  }
  //以上效果一样
  //更新省略，精简的写法
  // _ 表示占位符
  val ret3 = list.filter(_ > 5)
  for (r <- ret3) {
    println(r)
  }
  // _>5写成独立的函数会编译不过，因为无法确定类型
  //必须指明类型，且括号不能省略
  val ff = (_: Int) > 5
  // 打印
  println(ff)
  //cn.edu.jxnu.scala.Function$$$Lambda$18/4226387@18c7aca
  val ret4 = list.filter(ff)
  for (r <- ret4) {
    println(r)
  }

  //当使用下划线时，实际是编写一个部分应用的函数，当传入任何需要的参数时，实际是将该函数应用到这些参数上
  //部分应用的函数是一个表达式，可给出部分或者不给出任何参数
  def sum(a: Int, b: Int) = a + b

  //fun是一个函数值，它有sum函数的引用
  val fun = sum _
  val fun2 = sum(1, _: Int) //给出一个默认
  println(fun(2, 3)) //底层调用了 fun.apply(2,3) apply方法由编译器自动生成[实际是在混入了2个参数的Function2特质的函数类里面]
  // sum方法缺失2个参数，所以apply方法含有2个参数

}

/**
  * 其他函数特性
  *
  * 闭包
  */
object Function2 extends App {

  val m = 1
  //引用外部变量
  // 因为闭包实际捕获的是变量本身，所以m被修改后会体现到fun中，即闭包里面仍可以观察到闭包外面的m被修改后的值
  //反过来闭包中对变量的修改，在闭包外也能看到
  val fun = (x: Int) => x + m

  val nums = List(1, 2, 3, 4, 5, 5, 3)

  var sum = 0
  nums.foreach(sum += _)

  println(sum)

  // (x: Int) => x + m  是一个闭包，每次m都是新传入的值
  def sum(m: Int) = (x: Int) => x + m

  // (x: Int) => x + m  是一个闭包，每次m都是固定值
  def sum2(x: Int) = (x: Int) => x + m // 即使方法已经调用并返回了，但是scala编译器会重新组织安排，让被捕获的参数在堆上继续存活
  //无论是val var 或者是参数

}

/**
  * 其他函数特性
  *
  * 重复参数，带名参数，缺省参数
  */
object Function3 extends App {

  //重复参数
  def prt(args: String*) = for (arg <- args) println(arg)

  prt("hello", "world")

  val arr = Array("hello", "world")
  //    prt(arr)//编译报错
  prt(arr: _*) //OK，告诉编译器将arr的每个元素作为参数传进去，而不是将arr作为一个整体

  //带名字参数,字面量/匿名函数不能使用带名参数
  val su = (x: Int, y: Int) => x + y //    val suu = su(y = 1, x = 2)

  def sum(x: Int, y: Int) = x + y

  val s = sum(y = 1, x = 2) //参数顺序不再固定

  //缺省函数，y有默认值1
  def sum2(x: Int, y: Int = 1) = x + y

}

/**
  * 定义自己的值类型
  *
  * @author 梦境迷离
  * @time 2019-01-23
  */
class Dollars(val amount: Int) extends AnyVal {

  override def toString: String = "$" + amount

}

//有多个字符串类型的参数，在传参的顺序不正确的时候编译器不会给出提示，如果使用值类型，编译器会给出编译错误
/**
  * 控制抽象
  * 柯里化与贷出模式
  * 传名参数
  */
object Function4 extends App {

  import java.io.File
  import java.io.PrintWriter

  //柯里化
  def sum(x: Int)(y: Int) = x + y

  //sum与sum1效果一致，传入x=1，返回(y:Int)=>1+y 这个函数值，该函数值赋值给某个变量则该变量可以进行二次调用，如result(1)
  def sum1(x: Int) = (y: Int) => x + y

  //result此时是一个部分应用函数
  val result = sum(2) _

  //继续使用上面的部分应用函数，ret0=2+1
  val ret0 = result(1)

  //使用柯里化函数，ret=x+y=3
  val ret = sum(1)(2)

  //普通函数
  def sum2(x: Int, y: Int) = x + y

  //普通函数调用
  val ret2 = sum2(1, 2)

  // 贷出模式，不会忘记关闭流
  def withPrintWriter(file: File, op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      //将资源贷出给函数op
      op(writer)
    } finally {
      //不再需要带入的资源了
      writer.close()
    }
  }

  //调用
  withPrintWriter(
    new File("text.txt"),
    withPrintWriter =>
      withPrintWriter.println {
        //单个参数的方法可以使用花括号代替圆括号
        new java.util.Date()
      }
  )

  //使用柯里化定义贷出模式
  def withPrintWriter2(file: File)(op: PrintWriter => Unit) = {
    import java.io.PrintWriter
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  //调用柯里化的贷出模式
  val file = new File("test.txt")
  withPrintWriter2(file) { writer =>
    writer.println(new java.util.Date())
  }

  //传名参数
  // 只能用于参数声明，不能用于传名变量或传名字段
  def arrert1(predicate: => Boolean) = if (!predicate) throw new AssertionError

  //调用
  arrert1(2 > 3)

  //不使用传名参数
  def arrert(predicate: () => Boolean) = if (!predicate()) throw new AssertionError

  //直接传入Boolean，无法处理断言被禁用的特殊情况，断言被禁用将会看到表达式中的副作用，如：异常
  //我觉得有时候这可能是一种好的方法
  def arrert2(predicate: Boolean) = if (!predicate) throw new AssertionError

  //调用
  arrert(() => 4 > 2)

}
