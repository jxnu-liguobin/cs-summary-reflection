---
title: Scala Spring开发工具类
categories:
- Scala
tag: [工具类-不再更新]
---

* 目录
{:toc}

#### Spring Data Redis工具 redis键值对操作

```scala
import java.util.Set
import java.util.concurrent.TimeUnit

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService {

    private val LOGGER: Logger = LoggerFactory.getLogger(classOf[RedisService])

    @Autowired
    private var redisTemplate: RedisTemplate[String, String] = _

    /**
      * 获取Set集合数据
      *
      * @param k
      * @return Set[String]
      */
    def getSets(k: String): Set[String] = {
        redisTemplate.opsForSet.members(k)
    }

    /**
      * 移除Set集合中的value
      *
      * @param k
      * @param v
      */
    def removeSetValue(k: String, v: String): Unit = {
        if (k == null && v == null) {
            return
        }
        redisTemplate.opsForSet().remove(k, v)
    }

    /**
      * 保存到Set集合中
      *
      * @param k
      * @param v
      */
    def setSet(k: String, v: String): Unit = {
        if (k == null && v == null) {
            return
        }
        redisTemplate.opsForSet().add(k, v)
    }

    /**
      * 存储Map格式
      *
      * @param key
      * @param hashKey
      * @param hashValue
      *
      */
    def setMap(key: String, hashKey: String, hashValue: String) = {
        redisTemplate.opsForHash().put(key, hashKey, hashValue)
    }

    /**
      * 存储带有过期时间的key-value
      *
      * @param key
      * @param value
      * @param timeOut 过期时间
      * @param unit    时间单位
      *
      */
    def setTime(key: String, value: String, timeOut: Long, unit: TimeUnit) = {
        if (value == null) {
            LOGGER.info("redis存储的value的值为空")
            throw new IllegalArgumentException("redis存储的value的值为空")
        }
        if (timeOut > 0) {
            redisTemplate.opsForValue().set(key, value, timeOut, unit)
        } else {
            redisTemplate.opsForValue().set(key, value)
        }
    }

    /**
      * 存储key-value
      *
      * @param key
      * @return Object
      *
      */
    def set(key: String, value: String) = {
        if (value == null) {
            LOGGER.info("redis存储的value的值为空")
            throw new IllegalArgumentException("redis存储的value的值为空")
        }
        redisTemplate.opsForValue().set(key, value)
    }

    /**
      * 根据key获取value
      *
      * @param key
      * @return Object
      *
      */
    def get(key: String): Object = redisTemplate.opsForValue().get(key)

    /**
      * 判断key是否存在
      *
      * @param key
      * @return Boolean
      *
      */
    def exists(key: String): Boolean = redisTemplate.hasKey(key)


    /**
      * 删除key对应的value
      *
      * @param key
      *
      */
    def removeValue(key: String): Unit = if (exists(key)) redisTemplate.delete(key)

    /**
      * 模式匹配批量删除key
      *
      * @param keyParttern
      *
      */
    def removePattern(keyParttern: String) = {
        val keys: Set[String] = redisTemplate.keys(keyParttern)
        if (keys.size() > 0) redisTemplate.delete(keys)
    }

}
```
#### SpringMail工具 邮件发送相关服务

```scala
import java.io.File

import javax.mail.MessagingException
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.{JavaMailSender, MimeMessageHelper}
import org.springframework.stereotype.Service

@Service
class MailService {

    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[MailService])

    @Autowired
    private var sender: JavaMailSender = _

    @Value("${spring.mail.username}")
    private var username: String = _

    /**
      * 发送纯文本的简单邮件
      *
      * @param to      邮件接收者
      * @param subject 主题
      * @param content 内容
      */
    def sendSimpleMail(to: String, subject: String, content: String) = {
        val message = new SimpleMailMessage
        message.setFrom(username)
        message.setTo(to)
        message.setSubject(subject)
        message.setText(content)
        try {
            sender.send(message)
            LOGGER.info("发送给  " + to + " 邮件发送成功")
        } catch {
            case ex: Exception => {
                LOGGER.info("发送给 " + to + " 邮件发送失败！" + ex.getMessage)
            }
        }
    }

    /**
      * 发送html格式的邮件
      *
      * @param to      邮件接收者
      * @param subject 主题
      * @param content 内容
      */
    def sendHtmlMail(to: String, subject: String, content: String) = {
        val message = sender.createMimeMessage()
        val helper = new MimeMessageHelper(message, true)
        helper.setFrom(username)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(content, true)
        try {
            sender.send(message)
            LOGGER.info("发送给  " + to + " html格式的邮件发送成功")
        } catch {
            case ex: MessagingException => {
                LOGGER.info("发送给  " + to + " html格式的邮件发送失败！" + ex.getMessage)
            }
        }
    }

    /**
      * 发送带附件的邮件
      *
      * @param to       邮件接收者
      * @param subject  主题
      * @param content  内容
      * @param filePath 附件路径
      */
    def sendAttachmentsMail(to: String, subject: String, content: String, filePath: String) = {
        val message = sender.createMimeMessage()
        val helper = new MimeMessageHelper(message, true)
        helper.setFrom(username)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(content, true)
        val file = new FileSystemResource(new File(filePath))
        val fileName = filePath.substring(filePath.lastIndexOf(File.separator))
        helper.addAttachment(fileName, file)
        try {
            sender.send(message)
            LOGGER.info("发送给  " + to + " 带附件邮件发送成功")
        } catch {
            case ex: MessagingException => {
                LOGGER.info("发送给   " + to + " 带附件邮件发送失败！" + ex.getMessage)
            }
        }
    }

    /**
      * 发送嵌入静态资源（一般是图片）的邮件
      *
      * @param to      邮件接收者
      * @param subject 主题
      * @param content 邮件内容，需要包括一个静态资源的id，比如：<img src=\"cid:rscId01\" >
      * @param rscPath 静态资源路径和文件名
      * @param rscId   静态资源id
      */
    def sendInlineResourceMail(to: String, subject: String, content: String, rscPath: String, rscId: String) = {
        val message = sender.createMimeMessage()
        val helper = new MimeMessageHelper(message, true)
        helper.setFrom(username)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(content, true)
        val res = new FileSystemResource(new File(rscPath))
        helper.addInline(rscId, res)
        try {
            sender.send(message)
            LOGGER.info("发送给  " + to + " 嵌入静态资源的邮件发送成功")
        } catch {
            case ex: MessagingException => {
                LOGGER.info("发送给  " + to + " 嵌入静态资源的邮件发送失败！" + ex.getMessage)
            }
        }
    }
}
```
#### User实体类 定义与Java兼容的Scala实体类用于Mybatis操作数据库

```scala
import java.util.Date

import org.hibernate.validator.constraints.NotEmpty
import org.springframework.format.annotation.DateTimeFormat

import scala.beans.BeanProperty

class User extends Serializable {

    @BeanProperty
    var id: Int = _

    @BeanProperty
    @NotEmpty
    var username: String = _

    @BeanProperty
    @NotEmpty
    var password: String = _

    @BeanProperty
    @NotEmpty
    var sign: String = _

    @BeanProperty
    var avatar: String = _

    @BeanProperty
    @NotEmpty
    var email: String = _

    @BeanProperty
    //@NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var createDate: Date = _

    @BeanProperty
    var sex: Int = _

    @BeanProperty
    var status: String = _

    @BeanProperty
    var active: String = _

    override def toString = s"User(id=$id, username=$username, password=$password, sign=$sign, avatar=$avatar, email=$email, createDate=$createDate, sex=$sex, status=$status, active=$active)"
}
```
#### Spring缓存支持配置 配置自定义缓存管理器为redis

```scala
import java.lang.reflect.Method

import com.fasterxml.jackson.annotation.{JsonAutoDetect, PropertyAccessor}
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.{CachingConfigurerSupport, EnableCaching}
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.{RedisTemplate, StringRedisTemplate}
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer

@EnableCaching
@Configuration
class CacheConfig extends CachingConfigurerSupport {

    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[CacheConfig])

    //允许超时
    @Value("${spring.redis.timeout}")
    private var timeout: Int = _

    @Bean
    def cacheManager(redisTemplate: RedisTemplate[String, String]): CacheManager = {
        val cacheManager = new RedisCacheManager(redisTemplate)
        //设置key-value过期时间
        cacheManager.setDefaultExpiration(timeout)
        LOGGER.info("初始化Redis缓存管理器完成!")
        cacheManager
    }

    /**
      * 缓存保存策略
      *
      * @return KeyGenerator
      */
    @Bean
    def wiselyKeyGenerator(): KeyGenerator = {
        new KeyGenerator() {
            override protected def generate(target: Any, method: Method, params: AnyRef*): Object = {
                val sb = new StringBuilder
                sb.append(target.getClass.getName)
                sb.append(method.getName)
                for (param <- params) {
                    sb.append(param.toString)
                }
                sb.toString
            }
        }
    }

    @Bean
    def redisTemplate(factory: RedisConnectionFactory): RedisTemplate[String, String] = {
        val template = new StringRedisTemplate(factory)
        setSerializer(template)
        template.afterPropertiesSet()
        template
    }

    def setSerializer(template: StringRedisTemplate): Unit = {
        val jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(classOf[Object])
        val om = new ObjectMapper
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)
        template.setValueSerializer(jackson2JsonRedisSerializer)
    }
}
```
#### Spring连接池属性注入 配置redis连接

```scala
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import redis.clients.jedis.JedisPoolConfig

@Configuration
class RedisConfig {

    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[RedisConfig])
    //主机地址    
    @Value("${spring.redis.host}")
    private var host: String = _
    //端口
    @Value("${spring.redis.port}")
    private var port: Int = _
    //允许超时
    @Value("${spring.redis.timeout}")
    private var timeout: Int = _
    //认证
    @Value("${spring.redis.password}")
    private var password: String = _
    //数据库索引数量
    @Value("${spring.redis.database}")
    private var database: Int = _
    //连接池中的最大空闲连接
    @Value("${spring.redis.pool.max-idle}")
    private var maxIdle: Int = _
    //连接池中的最小空闲连接
    @Value("${spring.redis.pool.min-idle}")
    private var minIdle: Int = _
    //连接池最大连接数（使用负值表示没有限制）
    @Value("${spring.redis.pool.max-active}")
    private var maxActive: Int = _
    //连接池最大阻塞等待时间（使用负值表示没有限制）
    @Value("${spring.redis.pool.max-wait}")
    private var maxWait: Int = _

    /**
      * Jedis数据源配置
      *
      * @return JedisPoolConfig
      */
    @Bean
    def jedisPoolConfig(): JedisPoolConfig = {
        val jedisPoolConfig = new JedisPoolConfig
        jedisPoolConfig.setMaxIdle(maxIdle)
        jedisPoolConfig.setMinIdle(minIdle)
        jedisPoolConfig.setMaxWaitMillis(maxWait)
        LOGGER.info("Init the RedisPoolConfig Finished")
        jedisPoolConfig
    }

    /**
      * Jedis数据连接工厂
      *
      * @return JedisConnectionFactory
      */
    @Bean
    def redisConnectionFactory(poolConfig: JedisPoolConfig): JedisConnectionFactory = {
        val factory: JedisConnectionFactory = new JedisConnectionFactory
        factory.setHostName(host)
        factory.setPort(port)
        factory.setTimeout(timeout)
        factory.setPassword(password)
        factory.setDatabase(database)
        factory.setPoolConfig(poolConfig)
        LOGGER.info("Init the Redis instance Finished")
        factory
    }

}
```
#### SpringMVC配置 Scala继承Java抽象类

```scala
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.{InterceptorRegistry, ResourceHandlerRegistry, ViewControllerRegistry, WebMvcConfigurerAdapter}

@Configuration
class SpringMVCConfig extends WebMvcConfigurerAdapter {

 //在SpringBoot2.0及Spring 5.0 WebMvcConfigurerAdapter已被废弃
    //    1.直接实现WebMvcConfigurer （官方推荐）
    //    2.直接继承WebMvcConfigurationSupport
    /**
      * 重写addViewControllers方法配置默认主页
      *
      * @param registry
      */
    override def addViewControllers(registry: ViewControllerRegistry): Unit = {
        registry.addViewController("/").setViewName("forward:/index.html")
        registry.setOrder(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
        super.addViewControllers(registry)
    }

    /**
      * 注册拦截器
      *
      * @param registry
      */
    override def addInterceptors(registry: InterceptorRegistry) = {
        // addPathPatterns 用于添加拦截规则，excludePathPatterns 用户排除拦截
        registry.addInterceptor(new SystemHandlerInterceptor)
          .addPathPatterns("/**")
          .excludePathPatterns("/")
          .excludePathPatterns("/*.html")
          .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
        super.addInterceptors(registry)
    }

    /**
      * addResourceLocations是必须的，否则swagger被拦截
      *
      * @param registry
      */
    override def addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/")
        super.addResourceHandlers(registry)

    }
}
```
#### Swagger2配置

```scala
import org.springframework.context.annotation.{Bean, Configuration}
import springfox.documentation.builders.{ApiInfoBuilder, PathSelectors, RequestHandlerSelectors}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class Swagger2Config {

    /**
      * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
      */
    @Bean
    def createRestApi() = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
      .select().apis(RequestHandlerSelectors.basePackage("cn.edu.controller"))
      .paths(PathSelectors.any()).build()

    private val apiInfo = () => new ApiInfoBuilder()
      // 页面标题
      .title("test")
      // 创建人
      .description("梦境迷离：https://github.com/jxnu-liguobin").termsOfServiceUrl("https://github.com/jxnu-liguobin")
      // 创建人
      .contact("梦境迷离")
      // 版本号
      .version("1.0").build()

}
```
#### Spring处理拦截器 Scala实现Java接口

```scala
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.servlet.{HandlerInterceptor, ModelAndView}

class SystemHandlerInterceptor extends HandlerInterceptor {

    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[SystemHandlerInterceptor])

    /**
      * 前置处理器，在请求处理之前调用
      *
      * @param request
      * @param response
      * @param handler
      * @return Boolean
      */
    override def preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Object): Boolean = {
        LOGGER.info("前置处理器，在请求处理之前调用")
            return true
    }

    /**
      * 请求处理之后进行调用，但是在视图被渲染之前(Controller方法调用之后)
      *
      * @param request
      * @param response
      * @param handler
      * @param modelAndView
      */
    override def postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Object,
                            modelAndView: ModelAndView): Unit = {
        LOGGER.info("请求处理之后，视图渲染之前调用")

    }

    /**
      * 后置处理器，渲染视图完成
      *
      * @param request
      * @param response
      * @param handler
      * @param ex
      */
    override def afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Object,
                                 ex: Exception) = {
        LOGGER.info("后置处理器，在请求处理之后调用")

    }
}
```
#### Scala时间工具

```scala
import java.text.SimpleDateFormat
import java.util.{Calendar, Date, TimeZone}

/**
 * 日期处理
 *
 * @author 梦境迷离
 * @version 1.0, 2019-07-14
 */
object DateUtils {

  private final val patternComplete = "yyyy-MM-dd HH:mm:ss"

  private final val patternAll = "yyyy-MM-dd HH:mm:ss SSS"

  private final val patternPart = "yyyy-MM-dd"

  private final lazy val zone = TimeZone.getTimeZone("GMT+8:00")

  /**
   * 时区默认北京的部分解析器
   *
   * @param timeZone
   */
  private def partSimpleDateFormat(timeZone: TimeZone = zone): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(patternPart)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * 时区默认北京的完整解析器
   *
   * @param timeZone
   */
  private def completeSimpleDateFormat(timeZone: TimeZone = zone): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(patternComplete)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * 时区默认北京的精确解析器
   *
   * @param timeZone
   */
  private def allSimpleDateFormat(timeZone: TimeZone = zone): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(patternAll)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * 时区默认北京的自定义解析器
   *
   * @param timeZone
   * @param pattern
   * @return
   */
  def simpleDateFormat(timeZone: TimeZone = zone, pattern: String): SimpleDateFormat = {
    val sdf = new SimpleDateFormat(pattern)
    sdf.setTimeZone(timeZone)
    sdf
  }

  /**
   * 获取格式化后的当前时间 yyyy-MM-dd
   */
  def partFormatCurrentDate = partSimpleDateFormat().format(new Date)

  /**
   * 获取特定格式的当前时间 yyyy-MM-dd
   */
  def partParseCurrentDate = partSimpleDateFormat().parse(partFormatCurrentDate)

  /**
   * 获取特定格式的当前时间
   *
   * @param pattern 自定义格式
   */
  def formatCurrentDate(pattern: String) = simpleDateFormat(pattern = pattern).format(new Date)

  /**
   * 获取当前时间 yyyy-MM-dd HH:mm:ss
   */
  def completeFormatCurrentDate = completeSimpleDateFormat().format(new Date)

  /**
   * 获取特定格式的当前时间 yyyy-MM-dd HH:mm:ss
   */
  def completeParseCurrentDate = completeSimpleDateFormat().parse(completeFormatCurrentDate)

  /**
   * 获取当前时间Long类型
   */
  def getCurrentTimeStamp = new Date().getTime

  /**
   * 获取当前时间戳的 yyyy-MM-dd HH:mm:ss 表示
   *
   * @param timeStamp 时间戳
   */
  def completeFormatTimeStamp(timeStamp: Long) = completeSimpleDateFormat().format(new Date(timeStamp))

  /**
   * 使用自定义的格式串，格式化指定的日期
   *
   * @param date    日期
   * @param pattern 日期格式
   */
  def formatDate(date: Date, pattern: String) = simpleDateFormat(pattern = pattern).format(date)

  /**
   * 使用自定义的格式串，格式化指定的日期字符串
   *
   * @param dateStr
   * @param pattern
   */
  def parseDate(dateStr: String, pattern: String) = simpleDateFormat(pattern = pattern).parse(dateStr)

  /**
   * 用 yyyy-MM-dd HH:mm:ss 格式化指定的日期
   *
   * @param date 时间对象
   */
  def completeFormatDate(date: Date) = completeSimpleDateFormat().format(date)

  /**
   * 从日期字符串解析出完整的日期（有时分秒）
   *
   * @param dateStr 时间字符串
   */
  def completeParseDate(dateStr: String) = completeSimpleDateFormat().parse(dateStr)

  /**
   * 从日期字符串仅解析出日期（无时分秒）
   *
   * @param dateStr 日期字符串
   */
  def partParseDate(dateStr: String) = partSimpleDateFormat().parse(dateStr)

  /**
   * 用 yyyy-MM-dd 格式化指定的日期
   */
  def partFormatDate(date: Date) = partSimpleDateFormat().format(date)

  /**
   * 精确解析时间字符串（毫秒级）
   *
   * @param dateStr 包含毫秒的时间字符串
   */
  def allParseDate(dateStr: String) = allSimpleDateFormat().parse(dateStr)

  /**
   * 用 yyyy-MM-dd HH:mm:ss sss 格式化指定的日期
   *
   * @param date 包含毫秒的时间
   */
  def allFormatDate(date: Date) = allSimpleDateFormat().format(date)

  /**
   * 获取当前时间戳的 yyyy-MM-dd HH:mm:ss SSS 表示
   *
   * @param timeStamp 时间戳
   */
  def allFormatTimeStamp(timeStamp: Long) = allSimpleDateFormat().format(new Date(timeStamp))

  /**
   * 获取星期几
   */
  def dayOfWeek = (dateStr: String) => {
    val date = partSimpleDateFormat().parse(dateStr)
    val cal = Calendar.getInstance()
    cal.setTime(date)
    var w = cal.get(Calendar.DAY_OF_WEEK) - 1
    //星期天 默认为0
    if (w <= 0)
      w = 7
    w
  }

  /**
   * 判断是否是周末
   */
  def isWeekendDay = (dateStr: String) => {
    val dayNumOfWeek = dayOfWeek(dateStr)
    dayNumOfWeek == 6 || dayNumOfWeek == 7
  }
}
```
#### Scala下载URL图片

```scala
/**
 *
 * @param fileUrl 下载链接
 * @param target  保存地址且重命名后的文件
 */
def downloadFile(fileUrl: String, target: String) {
    var in: InputStream = null
    var connection: HttpURLConnection = null
    var out: OutputStream = null
    try {

        val url = new URL(fileUrl)
        connection = url.openConnection().asInstanceOf[HttpURLConnection]
        connection.setRequestMethod("GET")
        in = connection.getInputStream
        val fileToDownloadAs = new java.io.File(target)
        out = new BufferedOutputStream(new FileOutputStream(fileToDownloadAs))
        val byteArray = Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray
        out.write(byteArray)

    } catch {
        case ex: Exception => {
            logger.info(ex.getMessage)
        }
    }
    finally {

        if (in != null) {
            in.close()
        }

        if (out != null) {
            out.close()
        }
    }
}
```
