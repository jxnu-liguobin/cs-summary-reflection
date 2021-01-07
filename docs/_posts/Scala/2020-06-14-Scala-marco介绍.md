---
title: macro介绍
categories:
- Scala
tags: [Scala]
description: Scala的宏编程
---

* 目录
{:toc}

# Scala 泛型

一般是定义类型的时候使用

- 上界 <:
- 下界 >:
- 视界 <%     对于`[A <% T]`必须使用隐式转换将有界类型A转换为有界类型T，在2.11开始过期 [scala issue](OV5HIP4ZVD-eyJsaWNlbnNlSWQiOiJPVjVISVA0WlZEIiwibGljZW5zZWVOYW1lIjoi6I635Y+W77yaIGxvb2tkaXYuY29tIiwiYXNzaWduZWVOYW1lIjoiIiwiYXNzaWduZWVFbWFpbCI6IiIsImxpY2Vuc2VSZXN0cmljdGlvbiI6IiIsImNoZWNrQ29uY3VycmVudFVzZSI6ZmFsc2UsInByb2R1Y3RzIjpbeyJjb2RlIjoiSUkiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6ZmFsc2V9LHsiY29kZSI6IkFDIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOmZhbHNlfSx7ImNvZGUiOiJEUE4iLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiUlNDIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOnRydWV9LHsiY29kZSI6IlBTIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOmZhbHNlfSx7ImNvZGUiOiJSU0YiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiR08iLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6ZmFsc2V9LHsiY29kZSI6IkRNIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOnRydWV9LHsiY29kZSI6IkNMIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOmZhbHNlfSx7ImNvZGUiOiJSUzAiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiUkMiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiUkQiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6ZmFsc2V9LHsiY29kZSI6IlBDIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOmZhbHNlfSx7ImNvZGUiOiJSU1YiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiUlNVIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOmZhbHNlfSx7ImNvZGUiOiJSTSIsInBhaWRVcFRvIjoiMjAyMC0wOC0wMiIsImV4dGVuZGVkIjpmYWxzZX0seyJjb2RlIjoiV1MiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6ZmFsc2V9LHsiY29kZSI6IkRCIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOmZhbHNlfSx7ImNvZGUiOiJEQyIsInBhaWRVcFRvIjoiMjAyMC0wOC0wMiIsImV4dGVuZGVkIjp0cnVlfSx7ImNvZGUiOiJQREIiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiUFdTIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOnRydWV9LHsiY29kZSI6IlBHTyIsInBhaWRVcFRvIjoiMjAyMC0wOC0wMiIsImV4dGVuZGVkIjp0cnVlfSx7ImNvZGUiOiJQUFMiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiUFBDIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOnRydWV9LHsiY29kZSI6IlBSQiIsInBhaWRVcFRvIjoiMjAyMC0wOC0wMiIsImV4dGVuZGVkIjp0cnVlfSx7ImNvZGUiOiJQU1ciLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiRFAiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiUlMiLCJwYWlkVXBUbyI6IjIwMjAtMDgtMDIiLCJleHRlbmRlZCI6dHJ1ZX0seyJjb2RlIjoiRFBBIiwicGFpZFVwVG8iOiIyMDIwLTA4LTAyIiwiZXh0ZW5kZWQiOnRydWV9XSwibWV0YWRhdGEiOiIwMTIwMjAwNzAzUFBBTTAwMDAwNSIsImhhc2giOiIx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
)
- 边界 :
- 协变 +T
- 逆变 -T

演变的其他限定（一般是匹配类型时使用）

- <:<
- =:=
- <％<

对于A和B这两种类型，如果编译器可以找到A <:< B类型的隐式值，则它知道A符合B（是B的子类型）。
同样，如果编译器可以找到A =:= B类型的隐式值，那么它知道A和B的类型相同。它们在编码通用类型约束时很有用。

[What's the difference between A <: B and +B in Scala?](https://stackoverflow.com/questions/4531455/whats-the-difference-between-ab-and-b-in-scala)

[<:<、=:=、<%<](https://www.scala-lang.org/old/node/10632)

[ProgrammingInScala.pdf，从第61页开始](https://cs.uwaterloo.ca/~brecht/courses/702/Possible-Readings/scala/ProgrammingInScala.pdf)

[What are Scala context and view bounds?](https://stackoverflow.com/questions/4465948/what-are-scala-context-and-view-bounds)

示例如下

```
scala> def prove[T](implicit proof: T) = true
prove: [T](implicit proof: T)Boolean

scala> prove[Null <:< String]
res0: Boolean = true

scala> prove[String <:< AnyRef]
res1: Boolean = true

scala> prove[Null <:< Long]
<console>:9: error: Cannot prove that Null <:< Long.
       prove[Null <:< Long]
            ^

scala> prove[Long <:< AnyRef]
<console>:9: error: Cannot prove that Long <:< AnyRef.
       prove[Long <:< AnyRef]
            ^

scala> prove[AnyRef =:= java.lang.Object]
res4: Boolean = true

scala> trait Foo { type T }
defined trait Foo

scala> val foo = new Foo { type T = Int }
foo: java.lang.Object with Foo{type T = Int} = $anon$1@258361f6

scala> prove[foo.T =:= Int]
res5: Boolean = true

scala> prove[Foo#T <:< AnyRef]
<console>:10: error: Cannot prove that Foo#T <:< AnyRef.
       prove[Foo#T <:< AnyRef]
            ^

scala> prove[Foo#T <:< Any]
res9: Boolean = true
```

# 关于宏

- 宏是程序源代码在编译前完成的对源代码一系列操作的定义
- 宏一般用来动态生成要编译的代码
- C 中的宏
    - C 中的宏比较简单
  
```
#include <stdio.h>
#define PI 3.141592653589 # FLAG 1
define int main() {
 #ifdef FLAG
    double v = 3 * PI * 2;
    printf("%f\n",v);
#else
    double v = 3 * PI * 2;
    printf("%d\n",v);
#endif }
```

# Scala 宏

1. Scala 中的宏要复杂的多，但它对生成代码的方式，提供了更大的灵活性。
2. Scala 宏仍然是用 Scala 写的，一定程序上保证了开发体验的一致。
3. Scala 宏总是要返回一个AST，这需要你对 Scala AST 有一定的了解。
4. 但 Quasiquotes（准引用） 可以帮你轻松生成 AST。

准引用是一种简洁的表示法，使您可以轻松地操作Scala AST 语法树。

## 黑盒和白盒

一般来讲，黑盒（blackbox.Context）会比白盒（whitebox.Context）有更严格的类型检查

### 黑盒例子

黑盒宏的使用，会有四点限制，主要方面是

- 类型检查
- 类型推导
- 隐式推导
- 模式匹配

具体看官网 [blackbox-whitebox](https://docs.scala-lang.org/overviews/macros/blackbox-whitebox.html)

```scala
  import scala.language.experimental.macros
  import scala.reflect.macros.blackbox

  object MacrosBlackbox {

    def hello: Unit = macro helloImpl

    def helloImpl(c: blackbox.Context): c.Expr[Unit] = {
      import c.universe._
      c.Expr {
        Apply(
          Ident(TermName("println")),
          List(Literal(Constant("hello!")))
        )
      }
    }
  }
```
使用差值器（`q`）
```scala
  import scala.language.experimental.macros
  import scala.reflect.macros.blackbox

  object HelloQ {
    def hello(msg: String): Unit = macro helloImpl

    def helloImpl(c: blackbox.Context)(msg: c.Expr[String]): c.Expr[Unit] = {
      import c.universe._
      c.Expr(q"""println("hello!")""")
    }
  }
```

### 白盒例子

```scala
  import scala.language.experimental.macros
  import scala.reflect.macros.blackbox

  object MacrosWhitebox {
    def hello: Unit = macro helloImpl

    def helloImpl(c: whitebox.Context): c.Tree = {
      import c.universe._
      q"""println("hello!")"""
    }
  }
```

了解了Macros的两种规范之后，我们再来看看它的两种用法，一种和C的风格很像，只是在编译期将宏展开，减少了方法调用消耗。 
还有一种用法，我想大家更熟悉，就是注解，将一个宏注解标记在一个类、方法或者成员上，就可以将所见的代码，通过AST变成everything。
这里不会展开介绍第二种注解用法，可以参考官方文档。

## 定义一个宏

如果我们要传递一个参数或者泛型呢？

```scala
object Macros {
    def hello2[T](s: String): Unit = macro hello2Impl[T]

    def hello2Impl[T](c: blackbox.Context)(s: c.Expr[String])(tag: c.WeakTypeTag[T]): c.Expr[Unit] = {
        import c.universe._
        c.Expr {
            Apply(
                Ident(TermName("println")),
                List(
                    Apply(
                        Select(
                            Apply(
                                Select(
                                    Literal(Constant("hello ")),
                                    TermName("$plus")
                                ),
                                List(
                                    s.tree
                                )
                            ),
                            TermName("$plus")
                        ),
                        List(
                            Literal(Constant("!"))
                        )
                    )
                )
            )
        }
    }
}
```

和之前的不同之处，暴露的方法hello2主要在于多了参数s和泛型T，而hello2Impl实现也多了两个括号

* (s: c.Expr[String])
* (tag: c.WeakTypeTag[T])

### c.Expr

这是Macros的表达式包装器，里面放置着类型String，为什么不能直接传String呢？
当然是不可以了，因为宏的入参只接受Expr，调用宏传入的参数也会默认转为Expr。
这里要注意, 这个`(s: c.Expr[String])`的入参名必须等于`hello2[T](s: String)`的入参名。

### WeakTypeTag[T]

有时我们无法为泛型提供边界，就需要使用WeakTypeTag，此时无法使用TypeTag和ClassTag。但是应尽可能的提供更加具体的类型给WeakTypeTag。

像Manifests一样，TypeTags总是由编译器生成，并且可以通过三种方式获得，typeTag、classTag或weakTypeTag。

[manifests](https://docs.scala-lang.org/overviews/reflection/typetags-manifests.html)

## 关于 Context

- Context 封装了一个编译时 Universe （scala.reflect.macros.Universe）
- Context 同时也有一个 macroApplication，它提供了一个对宏展开处完整的AST
- 类型检查，编译警告、报错等

## 关于 Universe

- scala.reflect.api.Universe 提供了一个用来 Scala 反射的完整的操作集合，比如查看类型的成员，或反射出子类型
    - scala.reflect.api.JavaUniverse 是一个用在 JVM 实例上的对 Scala 反射的一个实现
    - scala.reflect.macros.Universe 是在进行 Scala 宏编程时对 Scala 反射的实现
- Universe 可以看作 Scala 反射的一个入口，主要混合了以下一些类型
    - Types 类型相关
    - Symbols 定义相关
    - Trees 抽象语法树相关
    - Names term 和 type names 相关
    - Annotations 注解相关
    - Positions 源码位置相关
    - FlagSet represent sets of flags that apply to symbols and definition trees
    - Constants 编译时常量相关

想要了解 Universe 最好是应用Scala反射接口，如使用Scala反射操作Scala注解，了解Scala和Java反射。

# 关于 Scala AST

编译器在编译代码前首先会把 source code 解析为编译器更容易消化的抽象语法树 (Abstract Syntax Tree AST)。

[AST在线测试](https://astexplorer.net/)