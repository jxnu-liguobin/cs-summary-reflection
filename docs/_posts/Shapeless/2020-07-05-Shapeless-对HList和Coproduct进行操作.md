---
title: 对HList和Coproduct进行操作
categories:
- Shapeless
tags: [Scala]
description: Scala的泛型编程库Shapeless的入门教程-6
---

* 目录
{:toc}

> 在线电子书 https://dreamylost.gitbook.io/dreamylost/

> shapeless v2.3.2

第一部分我们学习了为ADT派生类型类实例的方法，可以使用类型类派生的方式来致力于增强几乎任何类型类，尽管复杂的情况下为了操作HList和Coproduct必须写一系列的支持代码。

第二部分我们将着重介绍shapeless.ops包，它提供了一套有用的工具，可以将其直接用作代码块。每一个“操作（op）”分为两部分：一个在隐式解析过程中使用的类型类和为HList和Coproduct调用而准备的扩展方法。

有三套通用的方法组对应三个包，分别为：

* shapeless.ops.hlist：它为HList定义了类型类，这些操作可以通过定义在shapeless.syntax.hlist中的扩展方法被HList直接使用。
* shapeless.ops.coproduct：它为Coproduct定义了类型类，这些操作可以通过定义在shapeless.syntax.coproduct中的扩展方法被Coproduct直接使用。
* shapeless.ops.record：它为shapeless记录（在5.2节中介绍的元素被标记的HList）定义了类型类，这些操作可以通过定义在shapeless.syntax.record中的扩展方法被HList使用，只需要显式引入shapeless.record。

此书没有过多的空间逐一讲解所有“操作”。幸运的是大多数情况下我们的源码都通俗易懂并配有详细的文档。此处不准备提供详尽的指导而是介绍一些主要的理论和要点并向你展示如何从shapeless的基础代码中获取更进一步的信息。

## 6.1 简单的“操作”样例

HList有init和last两个扩展方法，它们分别基于shapeless.ops.hlist.Init和shapeless.ops.hlist.Last类型类。Coproduct有相似的方法和类型类。它们都是ops模式的完美样例。下面是这两个扩展方法的简单定义：

```scala
package shapeless 
package syntax

implicit class HListOps[L <: HList](l : L) { 
    def last(implicit last: Last[L]): last.Out = last.apply(l) 
    def init(implicit init: Init[L]): init.Out = init.apply(l)
}
```

每一个方法的返回类型都由隐式参数的一个依赖类型决定，为每一个类型类定义的实例提供了类型的真实对应关系。下面以Last类型类的框架为例：

```scala
trait Last[L <: HList] { 
    type Out
    def apply(in: L): Out 
}

object Last {
    type Aux[L <: HList, O] = Last[L] { type Out = O } 
    implicit def pair[H]: Aux[H :: HNil, H] = ??? 
    implicit def list[H, T <: HList]
        (implicit last: Last[T]): Aux[H :: T, last.Out] = ???
}
```

仔细观察这些实现。首先我们通常可以实现具有几个实际成员的（上例中只有两个）的ops类型类。因此我们能把类型类的伴生对象中所有可能需要的实际成员打包在一起，最终直接调用对应的扩展方法而无需引入shapeless.ops包。调用如下：

```scala
import shapeless._

("Hello" :: 123 :: true :: HNil).last 
// res0: Boolean = true

("Hello" :: 123 :: true :: HNil).init 
// res1: String :: Int :: shapeless.HNil = Hello :: 123 :: HNil
```

第二，这些类型类只能用在至少包含一个元素的HList实例，使得在一定程度上具有对代码进行检查的功能。当我们尝试对一个空的HList调用last方法，编译器会直接报错。如下：

```scala
HNil.last 
// <console>:16: error: Implicit not found: shapeless.Ops.Last[ shapeless.HNil.type]. 
//    shapeless.HNil.type is empty, so there is no last element.
//        HNil.last
//             ^
```

## 6.2 创建一个自定义的“操作”（引理（lemma）模式）

如果我们找到一个能满足需求的特殊的“操作”序列，就可以将它们打成一个包并作为一个新的ops类型类，这是我们在4.4节中介绍过的“引理（lemma）”模式的例子。

下面以创建自定义“操作”来作为练习。合并Last和Init来创建一个Penultimate类型类，它能从HList中取出倒数第二个元素。下面是用Aux类型别名和apply方法完成的定义代码：

```scala
import shapeless._

trait Penultimate[L] {
    type Out 
    def apply(l: L): Out
}

object Penultimate {
    type Aux[L, O] = Penultimate[L] { type Out = O }

    def apply[L](implicit p: Penultimate[L]): Aux[L, p.Out] = p
}
```

再次注意上面的apply方法返回了一个Aux\[L, O\]类型而不是Penultimate\[L\]类型，这一点能够确保类型成员Out在4.2节中所介绍的对于所获取的实例是可见的，没有被擦除。

我们只需要定义一个Penultimate实例，该实例使用4.3节中所介绍的技巧将Init和Last结合起来。代码如下：

```scala
import shapeless.ops.hlist

implicit def hlistPenultimate[L <: HList, M <: HList, O]( 
    implicit
    init: hlist.Init.Aux[L, M], 
    last: hlist.Last.Aux[M, O] 
): Penultimate.Aux[L, O] = 
    new Penultimate[L] { 
        type Out = O
        def apply(l: L): O = 
            last.apply(init.apply(l))
    }
```

我们能用下面的方式使用该实例：

```scala
type BigList = String :: Int :: Boolean :: Double :: HNil

val bigList: BigList = "foo" :: 123 :: true :: 456.0 :: HNil

Penultimate[BigList].apply(bigList) 
// res4: Boolean = true
```

获取Penultimate实例需要编译器同时完成获取Last和Init的实例，所以对于长度不满足的HList继承了与Last和Init的同一类型检查标准。代码如下：

```scala
type TinyList = String :: HNil

val tinyList = "bar" :: HNil

Penultimate[TinyList].apply(tinyList) 
// <console>:21: error: could not find implicit value for parameter p: Penultimate[TinyList]
//        Penultimate[TinyList].apply(tinyList)
//                   ^
```

对于底层用户我们可以为HList定义扩展方法，使调用变的更容易。代码如下：

```scala
implicit class PenultimateOps[A](a: A) { 
    def penultimate(implicit inst: Penultimate[A]): inst.Out = 
        inst.apply(a)
}

bigList.penultimate 
// res7: Boolean = true
```

通过提供一个基于Generic的实例就可以为所有的乘积类型提供Penultimate类型类操作。代码如下：

```scala
implicit def genericPenultimate[A, R, O]( 
    implicit
    generic: Generic.Aux[A, R], 
    penultimate: Penultimate.Aux[R, O]
): Penultimate.Aux[A, O] =
    new Penultimate[A] { 
        type Out = O 
        def apply(a: A): O = 
            penultimate.apply(generic.to(a))
    }

case class IceCream(name: String, numCherries: Int, inCone: Boolean)

IceCream("Sundae", 1, false).penultimate 
// res9: Int = 1
```

重要的是通过定义Penultimate类型类，我们创建了一个可以在任何地方复用的工具。shapeless为各种各样的目的提供了相应的“操作”，我们把自己定义的“操作”添加到工具箱里也很容易。

## 6.3 案例学习：样例类迁移

当在我们的代码中将多个“操作”链接成代码块的时候它的力量就已经显露无遗。下面我们将以一个极具吸引力的例子结束这一章：创建一个类型类用于展示样例类的迁移（这个词借用“数据库迁移”——数据库结构的自动升级的SQL脚本）或称之为进化。例如，如果我们的某个应用的第一版包含下面的样例类：

```scala
case class IceCreamV1(name: String, numCherries: Int, inCone: Boolean)
```

我们的迁移类库应该能够自由的实现手工升级。比如现有以下的后续版本的样例类：

```scala
// Remove fields:
case class IceCreamV2a(name: String, inCone: Boolean)

// Reorder fields:
case class IceCreamV2b(name: String, inCone: Boolean, numCherries: Int)

// Insert fields (provided we can determine a default value):
case class IceCreamV2c(name: String, inCone: Boolean, numCherries: Int, numWaffles: Int)
```

理想情况下我们用下述代码迁移我们的IceCreamV1：

```scala
IceCreamV1("Sundae", 1, false).migrateTo[IceCreamV2a]
```

迁移类型类应该使得迁移过程没有冗余代码。

### 6.3.1 Migration类型类

Migration类型类展示了从源类型到目标类型的转换过程。这两个在我们的派生过程都将作为输入类型，所以都是类型参数。我们不需要Aux类型别名因为没有需要提取的类型成员。Migration类型类的代码如下：

```scala
trait Migration[A, B] {
    def apply(a: A): B 
}
```

我们这里也提供一个扩展方法使得例子更易读。代码如下:

```scala
implicit class MigrationOps[A](a: A) {
    def migrateTo[B](implicit migration: Migration[A, B]): B = 
        migration.apply(a) 
}
```

### 6.3.2 第一步：移除字段

让我们逐步地找到问题的解决方案。第一步从移除字段开始，移除字段又可以细分为以下几步：

1. 将A实例转换为它的泛型表示；
2. 过滤上面得到的HList，只保留B中同样包含的字段；
3. 将过滤后的输出结果转换为B。

使用Generic或者LabelledGeneric实现第1步和第3步，并用一个叫做Intersection的“操作”实现第2步。LabelledGeneric看上去是一个明智的选择，因为我们需要根据名称来识别字段。代码如下：

```scala
import shapeless._ 
import shapeless.ops.hlist

implicit def genericMigration[A, B, ARepr <: HList, BRepr <: HList](
    implicit 
    aGen : Generic.Aux[A, ARepr],
    bGen : Generic.Aux[B, BRepr], 
    inter : hlist.Intersection.Aux[ARepr, BRepr, BRepr] 
): Migration[A, B] = new Migration[A, B] { 
    def apply(a: A): B = 
        bGen.from(inter.apply(aGen.to(a))) 
}
```

shapeless源码中的Intersection类型（见[https://github.com/milessabin/shapeless/blob/shapeless-2.3.2/core/src/main/scala/shapeless/ops/hlists.scala\#L1297-L1352](https://github.com/milessabin/shapeless/blob/shapeless-2.3.2/core/src/main/scala/shapeless/ops/hlists.scala#L1297-L1352)）。它的Aux类型别名有三个参数：两个HList类型的输入和二者相交的HList类型的输出。上述样例代码中我们将ARepr和BRepr指定为输入类型、BRepr指定为输出类型，这意味着只有在B的字段完全是A中的字段的子集的时候隐式解析才能成功，此处完全指的是名称和顺序均需一致。将IceCreamV1迁移到IceCreamV2a的代码如下：

```scala
IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2a] 
// res6: IceCreamV2a = IceCreamV2a(Sundae,true)
```

但是如果我们尝试迁移字段不完全匹配的类型，编译器就会报错。比如将IceCreamV1迁移到IceCreamV2b就会造成下面的结果：

```scala
IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2b] 
// <console>:23: error: could not find implicit value for parameter
//    migration: Migration[IceCreamV1,IceCreamV2b]
//         IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2b] 
//                                       ^
```

### 6.3.2 第二步：调整字段顺序 

我们需要使用另一个ops类型类使迁移支持调整字段顺序。[Align](https://github.com/milessabin/shapeless/blob/shapeless-2.3.2/core/src/main/scala/shapeless/ops/hlists.scala#L1973-L1997)“操作”能调整一个HList中的字段顺序使之与另一个HList中的字段顺序相匹配。可以使用Align修改上面的genericMigration定义使之完成此功能。代码如下：

```scala
implicit def genericMigration[ 
    A, B, 
    ARepr <: HList, BRepr <: HList, 
    Unaligned <: HList 
](
    implicit
    aGen : LabelledGeneric.Aux[A, ARepr],
    bGen : LabelledGeneric.Aux[B, BRepr],
    inter : hlist.Intersection.Aux[ARepr, BRepr, Unaligned], 
    align : hlist.Align[Unaligned, BRepr]
): Migration[A, B] = new Migration[A, B] { 
    def apply(a: A): B = 
        bGen.from(align.apply(inter.apply(aGen.to(a)))) 
}
```

此处引入了一个叫做Unaligned的新的类型参数来表示在调整顺序之前ARepr和BRepr两个类型字段相交的结果类型，并使用Align将Unaligned实例转换为BRepr。修改之后我们就能同时迁移减少字段和调整字段顺序。以下两个例子都会正常运行：

```scala
IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2a] 
// res8: IceCreamV2a = IceCreamV2a(Sundae,true)

IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2b] 
// res9: IceCreamV2b = IceCreamV2b(Sundae,true,1)
```

然而，如果我们尝试对添加字段的类型进行迁移，还是会报错。比如将IceCreamV1转化为IceCreamV2c的结果如下：

```scala
IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2c] 
// <console>:25: error: could not find implicit value for parameter
//    migration: Migration[IceCreamV1,IceCreamV2c]
//         IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2c] 
//                                                ^
```

### 6.3.4 第三步：增加字段 

为了计算新增字段的默认值我们需要一些技巧。shapeless没有为这种情况提供类型类，但是Cat框架以幺半群（Monoid）的方式提供了这种类型类。下面是Monoid的简单定义：

```scala
package cats

trait Monoid[A] {
    def empty: A
    def combine(x: A, y: A): A 
}
```

Monoid定义了两个操作：为创建一个“零”值定义的empty操作以及为两个值“相加”而定义的combine操作。在我们的代码中只需empty操作（默认值），但是定义一个combine操作也不麻烦。

Cat为我们关心的所有基础类型（Int、Double、Boolean和String）提供了Monoid实例。我们可以使用第五章中的技巧为HNil和::定义Monoid实例。代码如下：

```scala
import cats.Monoid 
import cats.instances.all._ 
import shapeless.labelled.{field, FieldType}

def createMonoid[A](zero: A)(add: (A, A) => A): Monoid[A] = 
    new Monoid[A] {
        def empty = zero
        def combine(x: A, y: A): A = add(x, y)
    }

implicit val hnilMonoid: Monoid[HNil] = 
    createMonoid[HNil](HNil)((x, y) => HNil)

implicit def emptyHList[K <: Symbol, H, T <: HList]( 
    implicit
    hMonoid: Lazy[Monoid[H]], 
    tMonoid: Monoid[T]
): Monoid[FieldType[K, H] :: T] = 
    createMonoid(field[K](hMonoid.value.empty) :: tMonoid.empty) {
        (x, y) => 
            field[K](hMonoid.value.combine(x.head, y.head)) :: 
                tMonoid.combine(x.tail, y.tail)
    }
```

我们需要将Monoid与一系列的其它ops结合来完成我们最终版的Migration。下面是所有步骤：

1. 使用LabelledGeneric将A转换为它的泛型表示；
2. 使用Intersection计算A和B共有字段的HList；
3. 推断在B中出现但是A中没有出现的字段的类型；
4. 使用Monoid来推断3中结果类型的默认值；
5. 将4中得到的新字段与2中得到的共同字段进行结合；
6. 使用Align调整5中的结果类型的顺序使之与B相同；
7. 使用LabelledGeneric将6中的输出结果转换为B。

我们已经学习了如何实现1、2、4、6和7步，可以使用一个叫做Diff的与Intersection相似的操作来实现第3步，并使用另一个叫做Prepend的操作来实现第5步。下面是完整的解决方案：

```scala
implicit def genericMigration[ 
    A, B, ARepr <: HList, BRepr <: HList,
    Common <: HList, Added <: HList, Unaligned <: HList
](
    implicit 
    aGen : LabelledGeneric.Aux[A, ARepr],
    bGen : LabelledGeneric.Aux[B, BRepr],
    inter : hlist.Intersection.Aux[ARepr, BRepr, Common],
    diff : hlist.Diff.Aux[BRepr, Common, Added], 
    monoid : Monoid[Added], 
    prepend : hlist.Prepend.Aux[Added, Common, Unaligned],
    align : hlist.Align[Unaligned, BRepr]
): Migration[A, B] =
    new Migration[A, B] {
        def apply(a: A): B = 
            bGen.from(align(prepend(monoid.empty, inter(aGen.to(a))))) 
    }
```

注意上述代码没有在值层面使用每一个类型类，使用Diff来推断增加的数据类型，但是在运行时并不需要真的运行diff.apply方法，而是使用Monoid来获取增加的实例。

有了最终版的类型类实例，就可以使用genericMigration为6.3节中设置的所有情况进行数据迁移。样例如下：

```scala
IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2a] 
// res14: IceCreamV2a = IceCreamV2a(Sundae,true)

IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2b] 
// res15: IceCreamV2b = IceCreamV2b(Sundae,true,1)

IceCreamV1("Sundae", 1, true).migrateTo[IceCreamV2c] 
// res16: IceCreamV2c = IceCreamV2c(Sundae,true,1,0)
```

我们能使用ops类型类来完成的功能是非常神奇的。只为Migration类型类定义一个隐式方法——genericMigration，它的具体实现也只有一行代码——即可完成在任何两个样例类之间进行自动迁移。使用标准的类库我们能以与此差不多的代码量写出处理单独一对类型的迁移工具。这就是shapeless的强大之处！

## 6.4 Record ops 

这一章我们花了一些时间学习了shapeless.ops.hlist和shapeless.ops.coproduct包里的类型类，下面来学习名为shapeless.ops.record的第三个重要的包，并以此结束本章。

shapeless中的“record ops”对元素被标记的HList提供了类似映射（map）的操作。下面以IceCream类为例，介绍对其进行操作的几个例子。首先获取IceCream的LabelledGeneric类型：

```scala
import shapeless._

case class IceCream(name: String, numCherries: Int, inCone: Boolean)

val sundae = LabelledGeneric[IceCream]. to(IceCream("Sundae", 1, false)) 
// sundae: String with shapeless.labelled.KeyTag[Symbol with shapeless
//    .tag.Tagged[String("name")],String] :: Int with shapeless. 
//    labelled.KeyTag[Symbol with shapeless.tag.Tagged[String(" 
//    numCherries")],Int] :: Boolean with shapeless.labelled.KeyTag[
//    Symbol with shapeless.tag.Tagged[String("inCone")],Boolean] :: 
//    shapeless.HNil = Sundae :: 1 :: false :: HNil
```

不像我们之前已经看到的HList和Coproduct操作，record ops语法需要对shapeless.record包进行显式引入。代码如下：

```scala
import shapeless.record._
```

### 6.4.1 选择字段

get扩展方法和它的对应的Selector类型类允许我们根据标签来获取此标签对应的字段的值。样例如下：

```scala
sundae.get('name) 
// res1: String = Sundae

sundae.get('numCherries) 
// res2: Int = 1
```

获取未定义的字段会导致编译错误。具体如下：

```scala
sundae.get('nomCherries) 
// <console>:20: error: No field Symbol with shapeless.tag.Tagged[ 
//    String("nomCherries")] in record String with shapeless.labelled. 
//    KeyTag[Symbol with shapeless.tag.Tagged[String("name")],String] :: 
//    Int with shapeless.labelled.KeyTag[Symbol with shapeless.tag. 
//    Tagged[String("numCherries")],Int] :: Boolean with shapeless. l
//    abelled.KeyTag[Symbol with shapeless.tag.Tagged[String("inCone") 
//    ],Boolean] :: shapeless.HNil
//  sundae.get('nomCherries)
//            ^
```

### 6.4.2 更新和删除字段 

updated方法和Updater类型类允许我们根据key值来修改字段。remove方法和Remover类型类允许我们根据key值来删除字段。使用方式如下：

```scala
sundae.updated('numCherries, 3) 
// res4: shapeless.::[String with shapeless.labelled.KeyTag[Symbol with 
//    shapeless.tag.Tagged[String("name")],String],shapeless.::[Int with 
//    shapeless.labelled.KeyTag[Symbol with shapeless.tag.Tagged[String(" 
//    numCherries")],Int],shapeless.::[Boolean with shapeless.labelled. 
//    KeyTag[Symbol with shapeless.tag.Tagged[String("inCone")],Boolean], 
//    shapeless.HNil]]] = Sundae :: 3 :: false :: HNil

sundae.remove('inCone) 
// res5: (Boolean, shapeless.::[String with shapeless.labelled.KeyTag[ 
//    Symbol with shapeless.tag.Tagged[String("name")],String],shapeless 
//    .::[Int with shapeless.labelled.KeyTag[Symbol with shapeless.tag. 
//    Tagged[String("numCherries")],Int],shapeless.HNil]]) = (false, 
//    Sundae :: 1 :: HNil)
```

updateWith方法和Modifier类型类允许我们传入一个更新函数来修改字段。以下代码实现将name字段更新为“MASSIVE ”加其原始值：

```scala
sundae.updateWith('name)("MASSIVE " + _) 
// res6: shapeless.::[String with shapeless.labelled.KeyTag[Symbol with 
//    shapeless.tag.Tagged[String("name")],String],shapeless.::[Int with 
//    shapeless.labelled.KeyTag[Symbol with shapeless.tag.Tagged[String(" 
//    numCherries")],Int],shapeless.::[Boolean with shapeless.labelled. 
//    KeyTag[Symbol with shapeless.tag.Tagged[String("inCone")],Boolean], 
//    shapeless.HNil]]] = MASSIVE Sundae :: 1 :: false :: HNil
```

### 6.4.3 转换为普通的Map对象 

toMap方法和ToMap类型类可以将一个记录转换为Map对象。代码如下：

```scala
sundae.toMap
// res7: Map[Symbol with shapeless.tag.Tagged[_ >: String("inCone") 
//    with String("numCherries") with String("name") <: String],Any] = 
//    Map('inCone -> false, 'numCherries -> 1, 'name -> Sundae)
```

### 6.4.4 其它操作 

由于篇幅限制很多操作不能在这里介绍，例如：重命名字段、合并记录、对字段的值进行映射等等，你可以从shapeless.ops.record和shapeless.syntax.record包中得到更多信息。

## 6.5 小结 

这一章我们探索了shapeless.ops包中提供的几个类型类。我们以Last和Init作为两个简单的ops模式的样例并以链接现有代码段的方式建立了我们自己的Penultimate和Migration类型类。

剩下的很多ops类型类与已经学过的这些ops模式具有很多相似之处。最简单的学习方式就是查看shapeless.ops和shapeless.syntax包中的源码。

后续我们将学习两套需要深层次理论的ops类型类。第七章学习在HList实例上进行类似map和flatMap的函数化操作。第八章学习需要在类型级别表示数字的类型类。这些知识将加深我们对shapeless.ops中的类型类的多样性的理解。