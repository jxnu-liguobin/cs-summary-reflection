package io.github.dreamylost.examples

/**
  * @author liguobin@growingio.com
  * @version 1.0,2020/3/25
  */
object SymbolTest {

  //在Scala中，Symbol类型的对象是被拘禁的(interned)，任意的同名symbols都指向同一个Symbol对象，避免了因冗余而造成的内存开销。
  //而对于String类型，只有编译时确定的字符串是被拘禁的(interned)。字符串的equals方法需要逐个字符比较两个字符串
  val s = 'aSymbol
  //输出true
  println(s == 'aSymbol)
  //输出true
  println(s == Symbol("aSymbol"))
}
