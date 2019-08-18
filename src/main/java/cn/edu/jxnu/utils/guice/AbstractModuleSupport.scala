package cn.edu.jxnu.utils.guice

import java.util.Objects

import com.google.common.reflect.ClassPath
import com.google.inject.AbstractModule

import scala.collection.JavaConverters._

/**
 * guice将包下所有类实例化并绑定到对应的接口
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
        clazz.getSimpleName.contains(irfe.getSimpleName) && clazz.getPackage.getName.contains(irfe.getPackage.getName)
      }
      if (Objects.nonNull(interfaces) && interfaces.nonEmpty && interfaceOpt.isDefined) {
        bind(interfaceOpt.get.asInstanceOf[Class[Any]]).to(clazz.asInstanceOf[Class[Any]])
      }
    }
  }

  def loadClasses(packageName: String): Set[Class[_]] = {
    CLASS_PATH.getTopLevelClasses(packageName).asScala.map(_.load()).toSet
  }

}