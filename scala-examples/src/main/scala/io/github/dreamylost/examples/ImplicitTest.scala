/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.examples

import java.util.Locale

/**
  * 隐式参数和隐式方法
  *
  * 两者之间在功能上没有差异
  *
  * 隐式参数不会直接在当前的方法体中使用，而是传递给另一个具有相同隐式参数列表的函数
  * 隐式参数是Scala关键字支持的
  *
  * 隐式方法通常用于检查类型的隐式值是否可用，并在这种情况下返回它。
  * 隐式方法是标准库实现的，使用了内联函数
  *
  * 若想使用隐式参数，一般使用implicitly
  * 若只是需要隐式参数，为了满足调用参数列表的语义，而不在方法内使用隐式参数本身，一般使用implicit
  *
  * @author 梦境迷离 dreamylost
  * @since 2020-07-05
  * @version v1.0
  */
object ImplicitTest extends App {

  /**
    * 隐式参数最常用的用途之一：通过柯里化给已知方法增加第二个参数列表，而不影响方法原有参数列表，并且将具体参数与拓展参数分开
    * 国际化在这里是一个后加的参数，且是隐式的。test1函数的调用方可以继续像之前没有locale参数一样，使用test1函数，只需要在方法调用前准备好可用的隐式参数。
    * 当然因为是柯里化的，调用方也可选择显示的传递locale参数，一般不需这样，除非当多个隐式参数可用时，为了消除歧义可能需要这样。
    * 在这里，国际化语言一般从每个用户请求中解析，仅有一个。
    *
    * @param x
    * @param locale
    */
  def test1(x: String)(implicit locale: Locale): Unit = {
    def test1Helper(args: String)(implicit locale: Locale): Unit = {
      println(s"args=$args, locale=$locale")
    }

    test1Helper(x)
  }

  //显示调用隐式参数，隐式参数的柯里化不能使用部分参数传递
  test1("hello world")(Locale.CHINA)

  def test2(x: String)(y: String): Unit = {
    println(s"x=$x, y=$y")
  }

  //对于非隐式参数的柯里化函数，可以仅使用第一个参数列表，此时fun是一个 String => Unit 的函数，还需要接收一个String参数才能执行println
  //后面的下划线表示，对该函数的调用尚未结束，还需继续调用
  val fun = test2("hello-") _

  fun("world")

  //并且对隐式参数，参数名称是无关的，也就是你可以给隐式参数取任意合法的变量名字
  implicit val china = Locale.CHINA
  test1("hello world2")
}

object ImplicitTest2 extends App {

  /** spec 1 * */
  trait Show[T] {
    def show(t: T): String
  }

  //伴生对象中的隐式参数对trait可见
  object Show {
    //与val相同，但是def支持泛型，都称为隐式值，当在参数列表时，称为隐式参数
    implicit def IntShow: Show[Int] = (i: Int) => i.toString

    implicit def StringShow: Show[String] = (s: String) => s

  }

  case class Person(name: String, age: Int)

  object Person {
    def PersonShow(implicit si: Show[Int], ss: Show[String]): Show[Person] =
      //使用了隐式si ss，会处理Int和String类型
      (p: Person) => "Person(name=" + ss.show(p.name) + ", age=" + si.show(p.age) + ")"
  }

  //使用implicitly自动搜索隐式参数，因为implicit si: Show[Int], ss: Show[String]都是在Show的伴生对象中。
  //调用Person的方法手动获取show对象
  //若Show伴生对象无si ss隐式参数，则implicitly会无法编译
  val show1 = Person.PersonShow(si = implicitly, ss = implicitly)
  val ret1 = show1.show(Person("bob", 25))
  println(ret1)

  /** spec 2 * */
  trait Show2[T] {
    def show(t: T): String
  }

  object Show2 {
    //与val相同，但是def支持泛型，都称为隐式值，当在参数列表时，称为隐式参数
    implicit def IntShow: Show2[Int] = (i: Int) => i.toString

    implicit def StringShow: Show2[String] = (s: String) => s

  }

  case class Person2(name: String, age: Int)

  object Person2 {
    //对于PersonShow2本身也是隐式参数的，可以直接使用隐式方法implicitly，自动得到Show[Person2]对象
    implicit def PersonShow2(implicit si: Show2[Int], ss: Show2[String]): Show2[Person2] =
      (p: Person2) => "Person2(name=" + ss.show(p.name) + ", age=" + si.show(p.age) + ")"
  }

  val show2: Show2[Person2] = implicitly[Show2[Person2]]
  val ret2 = show2.show(Person2("bob", 25))
  println(ret2)

  /** spec 3 * */
  trait Show3[T] {
    def show(t: T): String
  }

  object Show3 {
    //与val相同，但是def支持泛型，都称为隐式值，当在参数列表时，称为隐式参数
    implicit def IntShow: Show3[Int] = (i: Int) => i.toString

    implicit def StringShow: Show3[String] = (s: String) => s

  }

  case class Person3(name: String, age: Int)

  object Person3 {
    def PersonShow3(implicit si: Show3[Int], ss: Show3[String]): Show3[Person3] =
      (p: Person3) => "Person3(name=" + ss.show(p.name) + ", age=" + si.show(p.age) + ")"
  }

  val show3 = Person3.PersonShow3 //这里不用使用参数也可以，隐式参数在Show3伴生对象中
  val ret3 = show3.show(Person3("bob", 25))
  println(ret3)

  /** spec 4 * */
  trait Show4[T] {
    def show(t: T): String
  }

  case class Person4(name: String, age: Int)

  object Person4 {
    def PersonShow4(implicit si: Show4[Int], ss: Show4[String]): Show4[Person4] =
      (p: Person4) => "Person4(name=" + ss.show(p.name) + ", age=" + si.show(p.age) + ")"
  }

  implicit val stringShow: Show4[Int] = (s: Int) => s.toString
  implicit val intShow: Show4[String] = (s: String) => s
  val show4 = Person4.PersonShow4
  val ret4 = show4.show(Person4("bob", 25))
  println(ret4)

  /** spec 5 * */
  case class Person5(name: String, age: Int)

  trait Show5[T] {
    def show(t: T): String
  }

  object Person5 {
    def PersonShow5(implicit si: Show5[Int], ss: Show5[String]): Show5[Person5] =
      (p: Person5) => "Person5(name=" + ss.show(p.name) + ", age=" + si.show(p.age) + ")"
  }

  //在这里，使用def与泛型，只需要定义一个隐式值
  implicit def typeShow[T]: Show5[T] = (s: T) => s"hello implicit 5 ${s.toString}"

  //此时根据最相近匹配，会继续使用 stringShow2 intShow2的，typeShow隐式无效
  implicit val stringShow2: Show5[Int] = (s: Int) => s.toString
  implicit val intShow2: Show5[String] = (s: String) => s
  val show5 = Person5.PersonShow5
  val ret5 = show5.show(Person5("bob", 25))
  println(ret5)

  /** spec 6 * */
  trait Show6[T] {
    def show(t: T): String
  }

  case class Person6(name: String, age: Int)

  object Person6 {
    def PersonShow6[T](implicit si: Show6[Int], ss: Show6[String]): Show6[Person6] =
      (p: Person6) => "Person6(name=" + ss.show(p.name) + ", age=" + si.show(p.age) + ")"
  }

  implicit def typeShow2[T]: Show6[T] = (s: T) => s"hello implicit 6 ${s.toString}"

  val show6 = Person6.PersonShow6
  val ret6 = show6.show(Person6("bob", 25))
  println(ret6)
}

/** 输出
  * Person(name=bob, age=25)
  * Person2(name=bob, age=25)
  * Person3(name=bob, age=25)
  * Person4(name=bob, age=25)
  * Person5(name=bob, age=25)
  * Person6(name=hello implicit 6 bob, age=hello implicit 6 25)
  */
