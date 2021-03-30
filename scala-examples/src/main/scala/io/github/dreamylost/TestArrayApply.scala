/* Licensed under Apache-2.0 (C) All Contributors */
package io.github.dreamylost

/**
  * @author 梦境迷离
  * @since 2021/3/18
  * @version 1.0
  */
object TestArrayApply extends App {

  class False {
    //scala2.13.x bug #12201，字节码未使用基本类型数组优化
    val i = Array[Double](1)
    val j = Array[Double](1.0)
    val k = Array[Unit](())

  }

  class True {
    val x = Array(1.0)
    val y: Array[Double] = Array(1)
    val k: Array[Unit] = Array(())
  }

  //False的常量池：
  //  #19 = Utf8               ()[D
  //  #20 = NameAndType        #14:#15        // i:[D
  //  #21 = Fieldref           #2.#20         // Test$False.i:[D
  //  #22 = Utf8               this
  //  #23 = Utf8               LTest$False;
  //  #24 = NameAndType        #16:#15        // j:[D
  //  #25 = Fieldref           #2.#24         // Test$False.j:[D
  //  #26 = Utf8               Test$False$$$outer
  //  #27 = Utf8               ()LTest;
  //  #28 = NameAndType        #17:#18        // $outer:LTest;
  //  #29 = Fieldref           #2.#28         // Test$False.$outer:LTest;
  //  #30 = Utf8               <init>
  //  #31 = Utf8               (LTest;)V
  //  #32 = Utf8               ()V
  //  #33 = NameAndType        #30:#32        // "<init>":()V
  //  #34 = Methodref          #4.#33         // java/lang/Object."<init>":()V
  //  #35 = Utf8               scala/Array$
  //  #36 = Class              #35            // scala/Array$
  //  #37 = Utf8               MODULE$
  //  #38 = Utf8               Lscala/Array$;
  //  #39 = NameAndType        #37:#38        // MODULE$:Lscala/Array$;
  //  #40 = Fieldref           #36.#39        // scala/Array$.MODULE$:Lscala/Array$;
  //  #41 = Utf8               scala/runtime/ScalaRunTime$
  //  #42 = Class              #41            // scala/runtime/ScalaRunTime$
  //  #43 = Utf8               Lscala/runtime/ScalaRunTime$;
  //  #44 = NameAndType        #37:#43        // MODULE$:Lscala/runtime/ScalaRunTime$;
  //  #45 = Fieldref           #42.#44        // scala/runtime/ScalaRunTime$.MODULE$:Lscala/runtime/ScalaRunTime$;
  //  #46 = Utf8               wrapDoubleArray
  //  #47 = Utf8               ([D)Lscala/collection/immutable/ArraySeq;
  //  #48 = NameAndType        #46:#47        // wrapDoubleArray:([D)Lscala/collection/immutable/ArraySeq;
  //  #49 = Methodref          #42.#48        // scala/runtime/ScalaRunTime$.wrapDoubleArray:([D)Lscala/collection/immutable/ArraySeq;
  //  #50 = Utf8               scala/reflect/ClassTag$
  //  #51 = Class              #50            // scala/reflect/ClassTag$
  //  #52 = Utf8               Lscala/reflect/ClassTag$;
  //  #53 = NameAndType        #37:#52        // MODULE$:Lscala/reflect/ClassTag$;
  //  #54 = Fieldref           #51.#53        // scala/reflect/ClassTag$.MODULE$:Lscala/reflect/ClassTag$;
  //  #55 = Utf8               Double
  //  #56 = Utf8               ()Lscala/reflect/ManifestFactory$DoubleManifest;
  //  #57 = NameAndType        #55:#56        // Double:()Lscala/reflect/ManifestFactory$DoubleManifest;
  //  #58 = Methodref          #51.#57        // scala/reflect/ClassTag$.Double:()Lscala/reflect/ManifestFactory$DoubleManifest;
  //  #59 = Utf8               apply
  //  #60 = Utf8               (Lscala/collection/immutable/Seq;Lscala/reflect/ClassTag;)Ljava/lang/Object;
  //  #61 = NameAndType        #59:#60        // apply:(Lscala/collection/immutable/Seq;Lscala/reflect/ClassTag;)Ljava/lang/Object;
  //  #62 = Methodref          #36.#61        // scala/Array$.apply:(Lscala/collection/immutable/Seq;Lscala/reflect/ClassTag;)Ljava/lang/Object;
  //  #63 = Class              #15            // "[D"
}
