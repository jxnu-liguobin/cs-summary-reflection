package io.github.dreamylost.guice

import java.util.Objects

import com.google.common.reflect.ClassPath
import com.google.inject.AbstractModule

import scala.collection.JavaConverters._

/**
  * 用于guice的自定义依赖注入模块，继承该类并实例化给inject即可
  *
 * @author 梦境迷离
  * @time 2019-08-18
  * @version v1.0
  */
abstract class AbstractModuleSupport extends AbstractModule {

  private[this] final val CLASS_PATH = ClassPath.from(this.getClass.getClassLoader)

  def bindComponents(packageName: String): Unit = {
    CLASS_PATH.getTopLevelClasses(packageName).asScala.foreach { classInfo ⇒
      val clazz = classInfo.load()
      val interfaces = clazz.getInterfaces
      val interfaceOpt = interfaces.find { irfe ⇒
        clazz.getSimpleName.contains(irfe.getSimpleName) && clazz.getPackage.getName
          .contains(irfe.getPackage.getName)
      }
      //将包下所有类实例绑定到对应的接口
      if (Objects.nonNull(interfaces) && interfaces.nonEmpty && interfaceOpt.isDefined) {
        bind(interfaceOpt.get.asInstanceOf[Class[Any]]).to(clazz.asInstanceOf[Class[Any]])
      }
    }
  }

  /**
    * 反回指定包名的所有顶级类
    *
   * @param packageName
    * @return
    */
  def loadClasses(packageName: String): Set[Class[_]] = {
    CLASS_PATH.getTopLevelClasses(packageName).asScala.map(_.load()).toSet
  }
}
