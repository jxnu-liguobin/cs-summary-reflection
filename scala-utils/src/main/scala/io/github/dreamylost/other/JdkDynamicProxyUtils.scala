package io.github.dreamylost.other

import java.io.FileOutputStream
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

import sun.misc.ProxyGenerator

/**
  * Scala JDK代理
  *
 * @author 梦境迷离
  * @version 1.0, 2019-07-20
  */
object JdkDynamicProxyUtils {

  /**
    * 获取代理对象
    *
   * {{{
    *    val real = new RealSubject
    *    val prxoy = getProxy(real, classOf[Subject])
    *    prxoy.doSomething()
    * }}}
    *
   * @param `object` 需要被代理的对象
    * @param clazz    需要被代理对象所实现的接口
    * @tparam T 需要被代理类的类型
    */
  def getProxy[T](`object`: T, clazz: Class[_ >: T]): T = {
    Proxy
      .newProxyInstance(clazz.getClassLoader, Array[Class[_]](clazz), new ProxyHandler(`object`))
      .asInstanceOf[T]
  }

  /**
    * 代理对象
    */
  class ProxyHandler(`object`: Any) extends InvocationHandler {
    override def invoke(proxy: scala.Any, method: Method, args: Array[AnyRef]): AnyRef = {
      method.invoke(`object`, args: _*) //需要以单独元素传入
    }
  }

  /** 将代理对象写入指定文件，以指定名称保存
    * {{{
    *   createProxyClassFile("./", "RealSubject", classOf[Subject])
    * }}}
    *
   * @param path  写入文件保存的路径
    * @param name  写入保存后的class名称
    * @param clazz 需要被代理类的接口class
    * @tparam T
    */
  def createProxyClassFile[T](path: String, name: String, clazz: Class[_ >: T]): Unit = {
    val savePath =
      if (path.replace("\\", "/").endsWith("/")) path
      else path.replace("\\", "/") + "/"
    val data = ProxyGenerator.generateProxyClass(name, Array[Class[_]](clazz))
    var out: FileOutputStream = null
    try {
      out = new FileOutputStream(savePath + name + ".class")
      out.write(data)
      out.close()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally {
      if (out != null) {
        out.flush()
        out.close()
      }
    }
  }

  //==========================测试的两个类======================================//
  /**
    * 需要被代理的类所实现的接口
    */
  trait Subject {
    def doSomething()
  }

  /**
    * 需要被代理的类
    */
  class RealSubject extends Subject {
    def doSomething() {
      println("call doSomething()")
    }
  }

  /**
    * Result for getProxy & createProxyClassFile method
    * {{{
    *   //
    * // Source code recreated from a .class file by IntelliJ IDEA
    * // (powered by Fernflower decompiler)
    * //
    *
   * import cn.edu.jxnu.other.DynamicProxyScala.Subject;
    * import java.lang.reflect.InvocationHandler;
    * import java.lang.reflect.Method;
    * import java.lang.reflect.Proxy;
    * import java.lang.reflect.UndeclaredThrowableException;
    *
   * public final class RealSubject extends Proxy implements Subject {
    *     private static Method m1;
    *     private static Method m3;
    *     private static Method m2;
    *     private static Method m0;
    *
   *     public RealSubject(InvocationHandler var1) throws  {
    *         super(var1);
    *     }
    *
   *     public final boolean equals(Object var1) throws  {
    *         try {
    *             return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
    *         } catch (RuntimeException | Error var3) {
    *             throw var3;
    *         } catch (Throwable var4) {
    *             throw new UndeclaredThrowableException(var4);
    *         }
    *     }
    *
   *     public final void doSomething() throws  {
    *         try {
    *             super.h.invoke(this, m3, (Object[])null);
    *         } catch (RuntimeException | Error var2) {
    *             throw var2;
    *         } catch (Throwable var3) {
    *             throw new UndeclaredThrowableException(var3);
    *         }
    *     }
    *
   *     public final String toString() throws  {
    *         try {
    *             return (String)super.h.invoke(this, m2, (Object[])null);
    *         } catch (RuntimeException | Error var2) {
    *             throw var2;
    *         } catch (Throwable var3) {
    *             throw new UndeclaredThrowableException(var3);
    *         }
    *     }
    *
   *     public final int hashCode() throws  {
    *         try {
    *             return (Integer)super.h.invoke(this, m0, (Object[])null);
    *         } catch (RuntimeException | Error var2) {
    *             throw var2;
    *         } catch (Throwable var3) {
    *             throw new UndeclaredThrowableException(var3);
    *         }
    *     }
    *
   *     static {
    *         try {
    *             m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
    *             m3 = Class.forName("cn.edu.jxnu.other.DynamicProxyScala$Subject").getMethod("doSomething");
    *             m2 = Class.forName("java.lang.Object").getMethod("toString");
    *             m0 = Class.forName("java.lang.Object").getMethod("hashCode");
    *         } catch (NoSuchMethodException var2) {
    *             throw new NoSuchMethodError(var2.getMessage());
    *         } catch (ClassNotFoundException var3) {
    *             throw new NoClassDefFoundError(var3.getMessage());
    *         }
    *     }
    * }
    * }}}
    */

}
