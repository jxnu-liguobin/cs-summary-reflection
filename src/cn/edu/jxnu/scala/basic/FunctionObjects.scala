package cn.edu.jxnu.scala.basic

/**
 * 函数式对象即是：没有任何可变属性的对象
 *
 * @author 梦境迷离
 * @time 2018-12-17
 */
object FunctionObjects {

}

/**
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