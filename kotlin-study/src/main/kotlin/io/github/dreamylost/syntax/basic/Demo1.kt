/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.syntax.basic

/**
 * kotlin入门语法
 *
 * @author 梦境迷离
 * @version 1.0,2020/7/2
 */
fun main() {

    // 与Java Scala等JVM语言一样，在JVM平台使用Kotlin同样也需要main函数
    // 至于kotlin如何用于Js native，目前不了解。
    println("Hello world! 3")

    /**函数*/
    // 带有两个 Int 参数、返回 Int 的函数
    // 与Scala相似，只不过使用fun定义，返回类型少个=，也支持高阶函数，嵌套函数
    // 与Scala不同，返回类型不是Unit时不能省略，对于内部嵌套函数，这比较麻烦，需要多些很多代码，特别是复杂的嵌套类型无法使用编译器推断
    fun sum(a: Int, b: Int): Int {
        // 有返回值的函数，return 不能省略
        return a + b
    }

    // 无返回值的函数，与Scala相同返回Unit，也可以省略Unit
    fun printSum(a: Int, b: Int) {
        println("sum of $a and $b is ${a + b}")
    }

    /**变量*/
    // 定义只读局部变量使用关键字 val 定义。只能为其赋值一次。
    val a: Int = 1 // 立即赋值
    val b = 2 // 自动推断出 `Int` 类型
    val c: Int // 如果没有初始值类型不能省略，与Scala类似，但Scala中，定义变量不赋值意味着必须使用var且需要占位符 var c: Int = _
    c = 3 // 明确赋值
    //  可重新赋值的变量使用 var 关键字：
    var x = 5 // 自动推断出 `Int` 类型
    x += 1

    // 函数使用外部的变量
    fun incrementX() {
        x += 1
    }

    /**字符串模板*/
    var A = 1
    // 模板中的简单名称
    val s1 = "a is $A" // 与Scala不同，这里仅使用$插值，而Scala是 s"$A"
    A = 2
    // 模板中的任意表达式：
    val s2 = "${s1.replace("is", "was")}, but now is $A"

    /**条件表达式*/
    fun maxOf(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    // 与Scala相同，可以作为表达式赋值给变量
    fun maxOf2(a: Int, b: Int) = if (a > b) a else b

    /**空值与 null 检测*/
    fun parseInt(str: String): Int? {
        // ……
        return str.toIntOrNull() // 可空，与Scala Rust不同，Rust和Scala中是Option
    }

    // 返回可空
    fun printProduct(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)

        // 直接使用 `x * y` 会导致编译错误，因为它们可能为 null
        if (x != null && y != null) {
            // 在空检测后，x 与 y 会自动转换为非空值（non-nullable）
            // 或者== null直接return，后续就可以当做不为空使用
            println(x * y)
        } else {
            println("'$arg1' or '$arg2' is not a number")
        }
    }

    /**类型检测与自动类型转换*/
    // is 运算符检测一个表达式是否某类型的一个实例。
    // 如果一个不可变的局部变量或属性已经判断出为某类型，那么检测后的分支中可以直接当作该类型使用，无需显式转换
    // 与Scala不同，Scala的isInstanceOf不具备该功能，但是match case可以实现类似功能
    fun getStringLength(obj: Any): Int? {
        if (obj is String) {
            // `obj` 在该条件分支内自动转换成 `String`
            return obj.length
        }

        // 在离开类型检测分支后，`obj` 仍然是 `Any` 类型
        return null
    }

    // 或者
    fun getStringLength2(obj: Any): Int? {
        if (obj !is String) return null

        // `obj` 在这一分支自动转换为 `String`
        return obj.length
    }

    // 或者
    fun getStringLength3(obj: Any): Int? {
        // `obj` 在 `&&` 右边自动转换成 `String` 类型
        if (obj is String && obj.length > 0) {
            return obj.length
        }

        return null
    }

    /**for 循环*/
    val items = listOf("apple", "banana", "kiwifruit")
    for (item in items) {
        println(item)
    }
    // 或者
    for (index in items.indices) { // 与Scala相同，具备indices，下标使用[]，Scala使用()，Scala使用for(i <- items){}
        println("item at $index is ${items[index]}")
    }

    /**while 循环*/
    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }

    /**when 表达式*/
    fun describe(obj: Any): String =
        // 与Scala object match {case }相似，Scala的match还可以支持正则，解构等等，kotlin目前还不够了解
        when (obj) {
            1 -> "One"
            "Hello" -> "Greeting"
            is Long -> "Long"
            !is String -> "Not a string"
            else -> "Unknown"
        }

    /**使用区间（range）*/
    val X = 10
    val y = 9
    // 使用 in 运算符来检测某个数字是否在指定区间内：
    if (X in 1..y + 1) { // 与rust比较像，但rust支持 1..y 1..=y 后者包括y本身，而Scala可以使用until和to，后者包括右边界等价rust的..=
        // kotlin的..左右都是闭区间
        println("fits in range")
    }

    val list = listOf("a", "b", "c")

    // 检测某个数字是否在指定区间外:
    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }
    // 区间迭代:
    for (x in 1..5) {
        print(x)
    }

    // 数列迭代，步长为2
    for (x in 1..10 step 2) {
        print(x)
    }
    println()
    for (x in 9 downTo 0 step 3) { // Scala有Range
        print(x)
    }

    /**集合*/
    // 对集合进行迭代:
    for (item in items) {
        // 与rust相同
        println(item)
    }
    // 使用 in 运算符来判断集合内是否包含某实例：
    when {
        "orange" in items -> println("juicy")
        // 这其实等价于Scala的 items match {case x if x.find(y) => z}
        "apple" in items -> println("apple is fine too")
    }

    // 使用 lambda 表达式来过滤（filter）与映射（map）集合：
    val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
    // kotlin虽然省略了lambda的参数 it => 但是大括号不能省略或改小括号，也不能使用占位符，需要写多个无用的it变量名
    fruits.filter { it.startsWith("a") }.sortedBy { it }.map { it.toUpperCase() }.forEach { println(it) }

    /**创建基本类及其实例*/
    val rectangle = Rectangle(5.0, 2.0) // 与Scala的case class提供的apply相似，无new
    val triangle = Triangle(3.0, 4.0, 5.0)
    println("Area of rectangle is ${rectangle.calculateArea()}, its perimeter is ${rectangle.perimeter}")
    println("Area of triangle is ${triangle.calculateArea()}, its perimeter is ${triangle.perimeter}")
}

// 构造函数有1个不可变属性，
abstract class Shape(val sides: List<Double>) {
    // 泛型使用尖括号，与Java相同
    val perimeter: Double get() = sides.sum() // 除了构造函数类还可以定义成员变量，也可以定义抽象的方法，但是kotlin的抽象类的抽象方法要使用abstract修饰

    abstract fun calculateArea(): Double
}

interface RectangleProperties {
    // 接口方法不用abstract修饰
    val isSquare: Boolean
}

class Rectangle(
    // 构造函数有2个可变属性，
  var height: Double,
  var length: Double
) : RectangleProperties, Shape(listOf(height, length, height, length)) {
    // 继承使用: 逗号隔开，只允许一个抽象类，JVM平台目前Java Scala Kotlin等都不支持多继承（类）
    override val isSquare: Boolean get() = length == height

    override fun calculateArea(): Double = height * length
}

class Triangle(
  var sideA: Double,
  var sideB: Double,
  var sideC: Double
) : Shape(listOf(sideA, sideB, sideC)) {
    override fun calculateArea(): Double {
        val s = perimeter / 2
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC))
    }
}
