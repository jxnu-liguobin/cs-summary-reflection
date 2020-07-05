/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.scalikejdbc

import java.sql.ResultSet

import io.github.dreamylost.json.JacksonScalaSupport
import org.postgresql.util.PGobject
import scalikejdbc.ParameterBinderFactory
import scalikejdbc.TypeBinder

/**
  * 使得scalikejdbc的类型支持pgsql的json
  *
  * @author 梦境迷离
  * @time 2019-12-07
  * @version v1.0
  */
object RepositoryHelper {

  //使得scalikejdbc的类型支持pgsql的json
  object ParameterBinders {

    implicit val targetFields: ParameterBinderFactory[Map[String, String]] =
      ParameterBinderFactory[Map[String, String]] { value => (stmt, idx) =>
        {
          import io.github.dreamylost.json.JacksonScalaSupport
          val obj = new PGobject()
          obj.setType("json")
          obj.setValue(JacksonScalaSupport.mapper.writeValueAsString(value))
          stmt.setObject(idx, obj)
        }
      }
  }

  //使得scalikejdbc支持查询映射到entity的Map参数有
  object TypeBinders {

    implicit val targetFieldsTypeBinder: TypeBinder[Map[String, String]] =
      new TypeBinder[Map[String, String]] {
        def apply(rs: ResultSet, label: String): Map[String, String] =
          JacksonScalaSupport.mapper.readValue(rs.getString(label), classOf[Map[String, String]])

        def apply(rs: ResultSet, columnIndex: Int): Map[String, String] =
          JacksonScalaSupport.mapper
            .readValue(rs.getString(columnIndex), classOf[Map[String, String]])
      }
  }

}
