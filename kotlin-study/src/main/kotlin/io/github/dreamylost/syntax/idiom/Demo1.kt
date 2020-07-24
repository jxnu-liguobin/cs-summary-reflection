/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.syntax.idiom

import com.google.gson.Gson
import com.google.gson.JsonElement
import java.io.File
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths

/**
 * kotlin 习惯用法
 *
 * @author 梦境迷离
 * @version 1.0,2020/7/24
 */
fun main() {
    val customer = Customer("dreamylost", "dreamylost@outlook.com")
    println(customer)
    println(customer.copy(name = "dreamylost2"))

    foo()
    foo(a = 100)

    // 过滤 list，listOf这种生成的是不可变的
    val list = listOf(1, 2, 3, 4)
    val positives = list.filter { x -> x > 0 }
    val positives2 = list.filter { it > 0 } // 省略写法，相当于Scala的 list.filter(_ > 0)，it与Scala的 _ 作用相同
    println(positives)
    println(positives2)

    // 检测元素是否存在于集合中
    if (1 in list) { // 有点像Python，in在这里调用的是contains，但这里是操作符重载
        println("exists")
    }
    println("=======")
    // 遍历 map/pair型list
    val map = mapOf("key1" to "value1", "key2" to "value2") // 比较特殊，使用了to函数生成Pair
    // 访问 map
    println(map["key"]) // null，rust map[key]必须存在，否则报错

    // 与rust相似
    for ((k, v) in map) {
        println("$k -> $v")
    }
    println("=======")
    // 使用区间，rust中..是开，..=是闭
    for (i in 1..100) {
        // 闭区间：包含 100
        println(i)
    }
    println("=======")
    for (i in 1 until 100) { // scala to是闭，until也是开
        // 半开区间：不包含 100
        println(i)
    }
    println("=======")
    for (x in 2..10 step 2) { // step表示步长 包含2和10，scala 是by
        println(x)
    }
    println("=======")
    for (x in 10 downTo 1) { // 与step相反，逆序，包含10和1
        println(x)
    }
    if (2 in 1..10) {
        println("yes")
    }

    // 延迟属性
    val p: String by lazy {
        "hello"
    }
    println(p)

    // If not null 缩写  ?
    val files = File("Test").listFiles()
    println(files?.size)
    // If not null and else 缩写
    val files2 = File("Test").listFiles()
    println(files2?.size ?: "empty") // ?: 是null，?是不为null，后可以跟表达式
    // if null 执行一个语句
    // 在可能会空的集合中取第一元素
    val emails = listOf<String>()
    val mainEmail = emails.firstOrNull() ?: ""
    println(mainEmail)

    // 对一个对象实例调用多个方法 （with）
    val myTurtle = Turtle()
    with(myTurtle) {
        // 画一个 100 像素的正方形
        penDown()
        for (i in 1..4) {
            forward(100.0)
            turn(90.0)
        }
        penUp()
    }

    // 配置对象的属性（apply）用于配置未出现在对象构造函数中的属性

    // Java 7 的 try with resources
    val stream = Files.newInputStream(Paths.get("./README.md"))
    // Scala2.13提供了using，也可以自己实现贷出模式
    stream.buffered().reader().use { reader ->
        println(reader.readText())
    }

    // 交换两个变量
    var a = 1
    var b = 2
    a = b.also { b = a }

    // TODO()：将代码标记为不完整，相当于Scala的 ??? 方法
    fun calcTaxes(): BigDecimal = TODO("Waiting for feedback from accounting")
}

// 创建 DTOs（POJOs/POCOs）
// 这完全等同于Scala的case class，val仅有get，var还有个set，唯一不同，val时，不能省略val
// equals()
// hashCode()
// toString()
// copy()
data class Customer(val name: String, val email: String)

// 函数的默认参数
fun foo(a: Int = 0, b: String = "") {
    println("$a,$b") // 字符串差值，Scala多个前缀 s"$a,%b"
}

// 创建单例
object Resource {
    val name = "Name"
}

// 返回 when 表达式
fun transform(color: String): Int {
    return when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }
}

// “try/catch”表达式
fun test() {
    val result = try {
        println("hello")
    } catch (e: ArithmeticException) {
        throw IllegalStateException(e)
    }

    // 使用 result
}

class Turtle {
    fun penDown() {}
    fun penUp() {}
    fun turn(degrees: Double) {}
    fun forward(pixels: Double) {}
}

// 对于需要泛型信息的泛型函数的适宜形式
// 在kotlin中一个内联函数（inline）可以被具体化（reified），这意味着我们可以得到使用泛型类型的Class。
// 不需要传Class<T>
// 使用 reified，可以实现不同的返回类型函数重载 @see https://www.jianshu.com/p/24397a9dd428
inline fun <reified T : Any> Gson.fromJson(json: JsonElement): T = this.fromJson(json, T::class.java)
