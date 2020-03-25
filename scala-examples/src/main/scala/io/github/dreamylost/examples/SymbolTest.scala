package io.github.dreamylost.examples

/**
 *
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


/**
 * 这个类提供了一个简单的方法来获取相等字符串的唯一对象。由于符号是拘禁的，所以可以使用引用相等来比较它们。使用Scala内置的引号机制可以轻松创建`Symbol`的实例。
 * 例如，Scala术语`'mysym`将按以下方式调用`Symbol`类的构造函数：`Symbol("mysym")`。
 *
 * @author Martin Odersky, Iulian Dragos
 * @since 1.7
 */
final class Symbol private(val name: String) extends Serializable {
  /**
   * 将Symbol转为字符串
   */
  override def toString(): String = "'" + name

  @throws(classOf[java.io.ObjectStreamException])
  private def readResolve(): Any = Symbol.apply(name)

  override def hashCode = name.hashCode()

  //比较引用
  override def equals(other: Any) = this eq other.asInstanceOf[AnyRef]
}

//伴生对象，继承了缓存抽象类，具体实现抽象在UniquenessCache中
object Symbol extends UniquenessCache[String, Symbol] {
  override def apply(name: String): Symbol = super.apply(name)

  //string -> Symbol，从字符串生成Symbol
  protected def valueFromKey(name: String): Symbol = new Symbol(name)

  //Symbol -> Option[String]   从Symbol生成字符串
  protected def keyFromValue(sym: Symbol): Option[String] = Some(sym.name)
}

/**
 * 这是私有的，因此它不会出现在标准库API中
 */
private[scala] abstract class UniquenessCache[K, V >: Null] {

  import java.lang.ref.WeakReference
  import java.util.WeakHashMap
  import java.util.concurrent.locks.ReentrantReadWriteLock

  //读写锁
  private val rwl = new ReentrantReadWriteLock()
  //读锁
  private val rlock = rwl.readLock
  //写锁
  private val wlock = rwl.writeLock
  //存储映射的map，是一个弱引用。（无论当前内存是否足够，都会回收掉只被弱引用关联的对象）
  private val map = new WeakHashMap[K, WeakReference[V]]

  //string -> Symbol
  protected def valueFromKey(k: K): V

  //Symbol -> Option[String]
  protected def keyFromValue(v: V): Option[K]

  //使用Symbol("hello")会调用super.apply(name)，所以会调用此方法
  def apply(name: K): V = {
    def cached(): V = {
      //加读锁从map中获取已有的Symbol对象
      rlock.lock
      try {
        val reference = map get name
        //获取到则直接返回，没有则null
        if (reference == null) null
        else reference.get // will be null if we were gc-ed
      }
      finally rlock.unlock
    }

    def updateCache(): V = {
      //加写锁更新map中的字符串对应的Symbol对象
      wlock.lock
      try {
        //若缓存中已有，则直接返回，否则获取该字符串对应的Symbol对象，并更新map，然后返回Symbol对象
        val res = cached()
        if (res != null) res
        else {
          //如果我们不从map映射中删除旧的字符串键，我们可能会以一个字符串作为键，而将另一个字符串作为Symbol中的name字段，这可能会导致意外的GC行为和重复的Symbols
          map remove name
          val sym = valueFromKey(name) //生成Symbol对象
          map.put(name, new WeakReference(sym))
          sym
        }
      }
      finally wlock.unlock
    }

    val res = cached()
    //若缓存中没有，则更新，否则直接返回Symbol对象
    if (res == null) updateCache()
    else res
  }

  def unapply(other: V): Option[K] = keyFromValue(other)
}
