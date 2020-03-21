package io.github.dreamylost


class Construction {
  //class有主构造器和辅助构造器，辅助构造器第一行代码须用调用主构造器（例外可以是：另一个调用了主构造的辅助构造）
  def this(var1: String) = {
    //无返回值类型
    this() //因为此时默认的主构造就是无参
  }
}

//使用var表示在类的内层和外层均可以修改（因为生成字段var1，和var1的set方法）
//var1不使用val、var修饰则Scala不会为其生成字段以及访问器，只能暂时使用该变量
class Construction2(var1: String, var2: String) {
  //类结构可以嵌入主构造，此时主构造就是2个参数
  //定义一个参数的辅助构造
  def this(var1: String) = {
    this(null, "hello") //同样需要第一行调用主构造，或者是调用另一个出现在发起调用的构造方法之前的另一个辅助构造
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
  var1, var1) {

  //Trait的线性化细节描述Scala编程P238
  //1.java的super是静态绑定的
  //在java（单一继承）里面，假设有一个对象a，它既是类型X，又是类型Y，那么X和Y必定具有“父子关系”，也就是说，其中一个是另一个的父类。
  //因为java的继承是单一继承，不管实际类型是什么，一个对象的“继承链”，从super所在类开始往左的层分，都是在编译时期就可以确定下来的。
  //2.scala的super是动态绑定的（类中还是静态绑定的）
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

//仅是演示，混入多个特质
abstract class Trait extends Serializable with Comparable[String] with Iterable[String] {

  //特质的特点：与Java8的接口很像，但功能更强大
  //特质可以声明字段并保持状态，特质可以做任何在类中能做的事，除了两种情况
  //1.无类参数（主构造函数是无参的）
  //2.特质中super是动态绑定的，类中是静态绑定的

}

//样例类的主构造必须得有参数
//初始化可以省略new
//因为默认自带equals、toString等。可以使用==比较，也可以使用println直接打印内容
//match模式匹配时用的比较多
//默认生成自己的伴生对象
case class Construction5(name: String, pws: String = "pass")

object TestcaseClass extends App {

  val jack = Construction5("name", "password")
  val rese = Construction5("name")
  println(jack, rese)
  val name = jack.name //样例类的实例内容可以直接打印
  println(jack.name == "name", rese == Construction5("name"))

}