package cn.edu.jxnu.scala.basic

/**
 * 面向对象的类、单例对象、构造函数、序列化、注解使用等
 *
 * @author 梦境迷离
 * @time 2018-12-14
 */
class CompanionClass {


    private val str1 = "hello";

    def print(): Unit = {
        println(CompanionClass.str2)
    }


}

//伴生对象与伴生类同名，同一源文件中
//注意单例对象是一等的，是特殊的class
object CompanionClass extends App {
    //启动Scala程序
    //1.混入App，默认就可以执行里面的语句
    //2.增加自己的main方法
    //3.混入特质并重写main
    //继承/混入APP特质
    //单例可以混入特质
    private val str2 = "world";
    val companionClass = new CompanionClass() //new只能实例化类
    println(companionClass.str1) //单例对象类似Java的static方法调用
    companionClass.print() //可以互相访问对方的私有属性，方法

}

class Construction {
    //class有主构造器和辅助构造器，辅助构造器第一行代码必须用调用主构造器
    def this(var1: String) = {
        //无返回值类型
        this() //因为此时默认的主构造就是无参
    }
}

//使用var表示在类的内部和外部均可以修改（因为生成字段var1，和var1的set方法）
//var1不使用val、var修饰则Scala不会为其生成字段以及访问器，只能暂时使用该变量
class Construction2(var1: String, var2: String) {
    //类结构可以嵌入主构造，此时主构造就是2个参数
    //定义一个参数的辅助构造
    def this(var1: String) = {
        this(null, "hello") //同样需要第一行调用主构造
    }
}

//val这种是常见情况
//类定义可以不需要花括号
class Construction3(val var1: String, val var2: String)

//序列化使用注解（非要使用Java的也可以，基础这个接口即可）
//注意：使用注解需要加一对括号
//构造函数里面加也可以
class Construction4 @SerialVersionUID(1L)(val var1: String, val var2: String)