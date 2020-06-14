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

- 上界 <:
- 下界 >:
- 视界 <%     允许使用宽松的通过隐式转化的方式关联
- 边界 :
- 协变 +T
- 逆变 -T

演变的其他限定

- <:<
- =:=
- <％<

对于A和B这两种类型，如果编译器可以找到A <:< B类型的隐式值，则它知道A符合B（是B的子类型）。
同样，如果编译器可以找到A =:= B类型的隐式值，那么它知道A和B的类型相同。它们在编码通用类型约束时很有用。

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

## 关于 Context

- Context 封装了一个编译时Universe (scala.reflect.macros.Universe)
- Context 同时也有一个 macroApplication，它提供了一个对宏展开处完整的AST
- 类型检查，编译警告、报错等

## 黑盒和白盒

一般来讲，黑盒（blackbox.Context） 会比 白盒（whitebox.Context） 有更严格的类型检查

### 黑盒例子

黑盒宏的使用, 会有四点限制, 主要方面是

- 类型检查
- 类型推到
- 隐式推到
- 模式匹配

具体看官网 [blackbox-whitebox](https://docs.scala-lang.org/overviews/macros/blackbox-whitebox.html)

```scala
import scala.reflect.macros.blackbox

object Macros {
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

### 白盒例子

```scala
import scala.reflect.macros.blackbox

object Macros {
    def hello: Unit = macro helloImpl

    def helloImpl(c: blackbox.Context): c.Tree = {
      import c.universe._
      //可见写起来黑盒也更方便
      c.Expr(q"""println("hello!")""")
    }
}
```

了解了Macros的两种规范之后，我们再来看看它的两种用法，一种和C的风格很像，只是在编译期将宏展开，减少了方法调用消耗。 
还有一种用法，我想大家更熟悉，就是注解，将一个宏注解标记在一个类、方法或者成员上，就可以将所见的代码，通过AST变成everything。

# 定义一个宏

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

## c.Expr

这是Macros的表达式包装器，里面放置着类型String，为什么不能直接传String呢？
当然是不可以了，因为宏的入参只接受Expr，调用宏传入的参数也会默认转为Expr。
这里要注意, 这个`(s: c.Expr[String])`的入参名必须等于`hello2[T](s: String)`的入参名。

## WeakTypeTag[T]

有时我们无法为泛型提供边界，就需要使用WeakTypeTag，此时无法使用TypeTag和ClassTag。但是应尽可能的提供更加具体的类型给WeakTypeTag。

像Manifests一样，TypeTags总是由编译器生成，并且可以通过三种方式获得，typeTag、classTag或weakTypeTag。

[manifests](https://docs.scala-lang.org/overviews/reflection/typetags-manifests.html)

# 关于 Universe

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

想要了解 Universe 最好是应用Scala反射接口，如使用Scala注解。

# 关于 Scala AST

编译器在编译代码前首先会把 source code 解析为编译器更容易消化的抽象语法树 (Abstract Syntax Tree AST)。

[AST在线测试](https://astexplorer.net/)