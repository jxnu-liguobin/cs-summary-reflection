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
class Construction3(val var1: String, val var2: String) {
    def print(string: String) = println(string)
}

//序列化使用注解（非要使用Java的也可以，基础这个接口即可）
//注意：使用注解需要加一对括号
//构造函数里面加也可以
//重写父类的非抽象成员需要使用override
//在scala中，若有继承，那么，只有主构造函数才能将参数的值传递到父类的构造函数中去。
//继承父类时，必须用父类的主构造函数，并且传入参数，与上一句一个意思
class Construction4 @SerialVersionUID(1L)(override val var1: String, override val var2: String) extends Construction3(
    String, String) {

    //1.java的super是静态绑定的
    //在java（单一继承）里面，假设有一个对象a，它既是类型X，又是类型Y，那么X和Y必定具有“父子关系”，也就是说，其中一个是另一个的父类。
    //因为java的继承是单一继承，不管实际类型是什么，一个对象的“继承链”，从super所在类开始往左的部分，都是在编译时期就可以确定下来的。
    //2.scala的super是动态绑定的
    //在scala（多重继承）里面，假设有一个对象a，它既是trait X，又是trait Y， X和Y可能具有父子关系，也可能是共享同一个祖先的“兄弟”，反正，它们的关系不再限定在“父子”上。
    //因为scala允许多重继承，父亲类和trait们的优先顺序，是由对象的实际类型的线性化结果决定的，所以需要动态绑定。
    //3.调用方法，有三种情况，如下
    //    （1）当你看到 obj.method 的时候，你想知道实际被执行的是哪个类或者trait里的method：
    //    你首先要知道obj所指向的对象的“实际类型”，然后做线性化，然后，从线性化得到的“链”的最右边的类或者trait开始，往左边查找，首先找到的那个method就是实际被执行的方法。
    //    （2）当你看到 this.method 的时候（this可能被省略），你想知道实际被执行的是哪个类或者trait里的method：
    //    你首先要知道this所指向的对象的“实际类型”，然后做线性化，然后，从线性化得到的“链”的最右边的类或者trait开始，往左边查找，首先找到的那个method就是实际被执行的方法。
    //    （3）当你在某个类或者trait X 里面看到super.method的时候，你想知道实际被执行的是哪个类或者trait里的method：
    //    你首先要知道这个super所指向的对象的“实际类型”，然后做线性化，然后，从线性化得到的“链”里，从X开始往左边找（不包括X本身），首先找到的那个method就是实际被执行的方法。
    //    需要注意的是上述是针对特质，抽象类仍然是单继承
    def this() {
        this(null, null) //使用空构造，也需要调用主构造并传入null，因为默认自己写了有参的主构造，就不再提供无参主构造
        super.print("hello world") //排除自身从左开始就是：Construction3类
    }

}

