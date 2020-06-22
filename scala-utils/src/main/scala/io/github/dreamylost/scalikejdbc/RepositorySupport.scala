package io.github.dreamylost.scalikejdbc

import java.util.Properties

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import scalikejdbc.ConnectionPool
import scalikejdbc.DB
import scalikejdbc.DBSession
import scalikejdbc.DataSourceConnectionPool
import scalikejdbc.using

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * scalikejdbc 数据库连接池支持
  *
  * @author 梦境迷离
  * @time 2019-08-18
  * @version v1.0
  */
trait RepositorySupport extends LazyLogging {

  //使用贷出模式借贷资源，无需关闭资源
  def readOnly[A](execution: DBSession ⇒ A): Future[A] =
    concurrent.Future {
      using(getDB) { db: DB =>
        db.readOnly((session: DBSession) => execution(session))
      }
    }

  //事务和Future支持
  def localTx[A](execution: DBSession ⇒ A): Future[A] =
    concurrent.Future {
      using(getDB) { db: DB =>
        db.localTx((session: DBSession) => execution(session))
      }
    }

  @deprecated
  def localTxWithoutFuture[A](execution: DBSession ⇒ A): A =
    using(getDB) { db: DB =>
      db.localTx((session: DBSession) => execution(session))
    }

  def getAutoCommitSession = getDB.autoCommitSession()

  def getReadOnlySession = getDB.readOnlySession()

  def getConnectionPool: ConnectionPool = {
    ConnectionPool.get()
  }

  def getDB: DB = {
    DB(ConnectionPool.get().borrow())
  }
}

/**
  * 数据库连接池，启动服务时需要执行init方法初始化数据库
  */
object RepositorySupport extends RepositorySupport {

  private final lazy val defaultConfig = ConfigFactory.load("application.conf")

  def init(config: Config = defaultConfig): Unit = {
    logger.info("Init connection pool from config scalike")
    val dataSourceConfig = getScalikeDatasourceProperties("seckill", config)
    val _config = new HikariConfig(dataSourceConfig)
    val dataSource = new HikariDataSource(_config)
    ConnectionPool.singleton(new DataSourceConnectionPool(dataSource))
  }

  private def getScalikeDatasourceProperties(databaseName: String, config: Config): Properties = {
    import scala.collection.JavaConverters._
    val properties = new Properties()
    properties.setProperty("dataSourceClassName", config.getString("scalike.dataSourceClassName"))
    val databaseConfig = config.getConfig("scalike").getConfig(databaseName)
    databaseConfig.entrySet().asScala.foreach { e =>
      properties.setProperty(e.getKey, e.getValue.unwrapped().toString)
    }
    properties
  }
}
