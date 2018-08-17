## SSM 相关问题

### 1. 开发中主要使用 Spring 的什么技术 ?

        . IOC 容器管理各层的组件
        . 使用 AOP 配置声明式事务
        . 整合其他框架
        
### 2. 简述 AOP 和 IOC 概念

1. AOP： Aspect Oriented Program， 面向(方面)切面的编程；Filter(过滤器)
也是一种 AOP。 AOP 是一种新的方法论， 是对传统 OOP(Object-Oriented
Programming， 面向对象编程) 的补充。 AOP 的主要编程对象是切面(aspect)，
而切面模块化横切关注点.可以举例通过事务说明。
2. IOC： Invert Of Control， 控制反转。 也成为 DI(依赖注入)其思想是反转
资源获取的方向。 传统的资源查找方式要求组件向容器发起请求查找资源。作为
回应， 容器适时的返回资源. 而应用了 IOC 之后， 则是容器主动地将资源推送
给它所管理的组件，组件所要做的仅是选择一种合适的方式来接受资源。这种行
为也被称为查找的被动形式。

## 3. 在 Spring 中如何配置 Bean ？
	
Bean 的配置方式： 

    通过全类名（反射）
    通过工厂方法（静态工厂方法 & 实例工厂方法）
    FactoryBean
    


FactoryBean定义

```
package org.springframework.beans.factory;

public interface FactoryBean<T> {
    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}

```   
FactoryBean 通常是用来创建比较复杂的bean，一般的bean 直接用xml配置即可，
但如果一个bean的创建过程中涉及到很多其他的bean 和复杂的逻辑，用xml配置比较困难，这时可以考虑用FactoryBean。

### 4. IOC 容器对 Bean 的生命周期：

	. 通过构造器或工厂方法创建 Bean 实例
	. 为 Bean 的属性设置值和对其他 Bean 的引用
    . 将 Bean 实例传递给 Bean 前置处理器的postProcessBeforeInitialization 方法
    . 调用 Bean 的初始化方法(init-method)
    . 将 Bean 实例传递给 Bean 后置处理器的postProcessAfterInitialization 方法
	. Bean 可以使用了
	. 当容器关闭时， 调用 Bean 的销毁方法(destroy-method)
	
![](https://github.com/jxnu-liguobin/Java-Learning-Summary/blob/master/src/cn/edu/jxnu/practice/picture/SpringBeanBean.jpg)
	
### 5. Spring MVC 的运行流程
	
1. 在整个 Spring MVC 框架中， DispatcherServlet 处于核心位置，负
责协调和组织不同组件以完成请求处理并返回响应的工作。

2. SpringMVC 处理请求过程：

	* 1、若一个请求匹配 DispatcherServlet 的请求映射路径(在 web.xml
中指定)， WEB 容器将该请求转交给 DispatcherServlet 处理。
	* 2、DispatcherServlet 接收到请求后， 将根据请求信息(包括 URL、HTTP
方法、请求头、请求参数、Cookie 等)及 HandlerMapping 的配置找到处理请求的处理器(Handler)。可将 HandlerMapping 看成路由控制器，将 Handler 看成目标主机。
	* 3、当 DispatcherServlet 根据 HandlerMapping 得到对应当前请求的Handler 后，通过 HandlerAdapter 对 Handler 进行封装，再以统一的适配器接口调用 Handler。
	* 4、处理器完成业务逻辑的处理后将返回一个 ModelAndView 给DispatcherServlet， ModelAndView 包含了视图逻辑名和模型数据信息。
	* 5、DispatcherServlet 借助 ViewResoler 完成逻辑视图名到真实视图对象的解析， 得到真实视图对象 View 后， DispatcherServlet 使用这个 View 对ModelAndView 中的模型数据进行视图渲染。
	
![](https://github.com/jxnu-liguobin/Java-Learning-Summary/blob/master/src/cn/edu/jxnu/practice/picture/SpringMVC.jpg)

### 6. 说出 Spring MVC 常用的 5 个注解

	. @RequestMapping 、@PathVariable 、@RequestParam 、@RequestBoy 、
	. @ResponseBody
	
### 7. 如何使用 SpringMVC 完成 JSON 操作

	. 配置MappingJacksonHttpMessageConverter
	. 使用 @ResponseBody 注解或 ResponseEntity 作为返回值
    . ResponseEntity 可以定义返回的HttpHeaders和HttpStatus
    . 使用@RestController修饰控制器（Spring 4 MVC）
	

### 8. 相关注解

@RequestBody

处理HttpEntity传递过来的数据，一般用来处理非Content-Type： application/x-www-form-urlencoded编码格式的数据。
GET请求中，因为没有HttpEntity，所以@RequestBody并不适用。
POST请求中，通过HttpEntity传递的参数，必须要在请求头中声明数据的类型Content-Type，SpringMVC通过使用HandlerAdapter 配置的HttpMessageConverters来解析HttpEntity中的数据，然后绑定到相应的bean上。

@Autowired与@Resource、@Qualifier

@Resource装配顺序

1. 如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常
2. 如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常
3. 如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常
4. 如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配

主要区别

1. @Autowired与@Resource都可以用来装配bean. 都可以写在字段上，或写在setter方法上。
2. @Autowired默认按类型装配（这个注解是属业spring的），默认情况下必须要求依赖对象必须存在，如果要允许null值，可以设置它的required属性为false。
3. @Resource（这个注解属于J2EE的，是JSR规范定义的注解），默认按照名称进行装配，名称可以通过name属性进行指定，如果没有指定name属性，当注解写在字段上时，默认取字段名进行按照名称查找，如果注解写在setter方法上默认取属性名进行装配。
当找不到与名称匹配的bean时才按照类型进行装配。但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配。
4. 推荐使用：@Resource注解在字段上，这样就不用写setter方法了，并且这个注解是属于J2EE的，减少了与spring的耦合。这样代码看起就比较优雅。
5. @Qualifier一般作为@Autowired()的修饰用，当使用@Autowired注入出现多个Bean的注入异常时，则需要指定注入的Bean的名称。

### 9. SpringBoot & SpringCloud

1、SpringBoot自动配置原理

[参考](https://www.cnblogs.com/leihuazhe/p/7743479.html)

![自动配置](https://github.com/jxnu-liguobin/Java-Learning-Summary/blob/master/src/cn/edu/jxnu/practice/picture/SpringBootAuto.jpg)

2、SpringCloud Config properties属性加载分析

[上](https://blog.csdn.net/qq_34446485/article/details/81004369)
[下](https://blog.csdn.net/qq_34446485/article/details/81011553)

3、SpringBoot解决循环依赖

[循环依赖的解决](https://blog.csdn.net/qq_34446485/article/details/81259618)

### 10. Spring主要的几个原生接口

    Resource　　　　　　　　//资源文件的抽象，xml 、 properties ...
    BeanDefinition　　　　 //bean的抽象定义 (bean的一些基本信息是否是 抽象、单例、懒加载、作用域...)基本信息
    BeanDefinitionReader  //不同的资源文件的bean的解析 
    BeanFactory　　　　　　//bean工厂的顶层抽象定义了几个基础的方法 getBean() contanisBean() .... 
    ApplicationContext　　//应用程序上下文

### 11. SpringBoot的多数据具体怎么配置的？

步骤：

      1、继承AbstractRoutingDataSource路由
      2、ThreadLocal存数据源
      3、构建数据源并初始化
      4、指定主库 @Primary
      5、AOP拦截注解+注解标记方法
     
### 12. Spring AOP什么时候会失效？

在对象内部的方法中调用该对象的其他使用AOP机制的方法，被调用方法的AOP注解失效。

在一个类的A方法中调用同类的B方法，实际上使用的是使用实例调用方式this.B()，而代理对象是$开头的一个对象，此时的调用并不会走代理，注解会无效。
因为在被代理对象的方法中调用被代理对象的其他方法时。其实是没有用代理调用，是用了被代理对象本身调用的。     
   
### 13. AOP的JDK动态代理

newProxyInstance 产生一个代理对象 ，三个参数

        1.classloader 代理对象和被代理对象应该处于同一个 classloader
        2.接口产生的代理对象应该实现哪些接口
        3.handel 执行代理对象方法时，应用哪个handel 处理。
        
(接口中有什么方法，代理中就有什么方法 代理中的每个方法在调用的时候都会把方法自身传给 handel， 并把代理对象和参数都传递过去 )   

### 14. 静态代理与动态代理

静态代理：由程序员或者自动生成工具生成代理类，然后进行代理类的编译和运行。在代理类、委托类运行之前，代理类已经以.class的格式存在。

动态代理：在程序运行时，由反射机制动态创建而成。（各种字节码操纵工具以及框架可以实现）

### 15. CGLib与JDK代理的区别

     . JDK动态代理只能对实现了接口的类生成代理，而不能针对没有实现接口的类
     . CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法，因为是继承，所以该类或方法最好不要声明成final 


动态代理设计模式类图

![](https://github.com/jxnu-liguobin/Java-Learning-Summary/blob/master/src/cn/edu/jxnu/practice/picture/dproxy.png)

动态代理宏观图

![](https://github.com/jxnu-liguobin/Java-Learning-Summary/blob/master/src/cn/edu/jxnu/practice/picture/proxy2.jpg)

[连接ASM部分](https://github.com/jxnu-liguobin/Java-Learning-Summary/blob/master/src/cn/edu/jxnu/reflect/asm/ASM.md)

### 16. Mybatis #和$ 的区别？

1. \#{}  将传入的数据都当成一个字符串，会对自动传入的数据加一个双引号。
如：order by #user_id#，如果传入的值是111,那么解析成sql时的值为order by "111", 
如果传入的值是id，则解析成的sql为order by "id"。
2. $将传入的数据直接显示生成在sql中。如：order by $user_id$，如果传入的值是111,那么解析成sql时的值为order by user_id,  
如果传入的值是id，则解析成的sql为order by id。
3. \#{}方式能够很大程度防止sql注入。
4. $方式无法防止Sql注入。
5. $方式一般用于传入数据库对象，例如传入表名.
6. 一般能用#的就别用$。
7. MyBatis排序时使用order by 动态参数时需要注意，用$而不是#。

补充模糊查询

1. 表达式: name like"%"#{name}"%"         #起到占位符的作用
2. 表达式: name like '%${name}%'           $进行字符串的拼接,直接把传入的值,拼接上去了,没有任何问题
3. 表达式: name likeconcat(concat('%',#{username}),'%')         这是使用了cancat进行字符串的连接,同时使用了#进行占位
4. 表达式：name like CONCAT('%','${name}','%')         对上面的表达式进行了简化,更方便了

SQL注入漏洞测试:原文：http://www.cnblogs.com/leftshine/p/SQLInjection.html#downloadFile

### 17. Xml映射文件中，除了常见的select|insert|updae|delete标签之外，还有哪些标签？

还有很多其他的标签，``` <resultMap>、<parameterMap>、<sql>、<include>、<selectKey> ```，加上动态sql的9个标签，trim|where|set|foreach|if|choose|when|otherwise|bind等，其中 ```<sql>``` 为sql片段标签，
通过 ```<include>``` 标签引入sql片段，```<selectKey>``` 为不支持自增的主键生成策略标签。

### 18. 最佳实践中，通常一个Xml映射文件，都会写一个Dao接口与之对应，请问，这个Dao接口的工作原理是什么？Dao接口里的方法，参数不同时，方法能重载吗？

Dao接口，就是人们常说的Mapper接口，接口的全限名，就是映射文件中的namespace的值，接口的方法名，就是映射文件中MappedStatement的id值，
接口方法内的参数，就是传递给sql的参数。Mapper接口是没有实现类的，当调用接口方法时，接口全限名+方法名拼接字符串作为key值，可唯一定位一个MappedStatement，
举例：com.mybatis3.mappers.StudentDao.findStudentById，可以唯一找到namespace为com.mybatis3.mappers.StudentDao下面id = findStudentById的MappedStatement。
在Mybatis中，每一个 ```<select>、<insert>、<update>、<delete> ``` 标签，都会被解析为一个MappedStatement对象。
Dao接口里的方法，是不能重载的，因为是全限名+方法名的保存和寻找策略。
Dao接口的工作原理是JDK动态代理，Mybatis运行时会使用JDK动态代理为Dao接口生成代理proxy对象，代理对象proxy会拦截接口方法，转而执行MappedStatement所代表的sql，然后将sql执行结果返回。

### 19. Mybatis是如何进行分页的？分页插件的原理是什么？

Mybatis使用RowBounds对象进行分页，它是针对ResultSet结果集执行的内存分页，而非物理分页，可以在sql内直接书写带有物理分页的参数来完成物理分页功能，也可以使用分页插件来完成物理分页。
分页插件的基本原理是使用Mybatis提供的插件接口，实现自定义插件，在插件的拦截方法内拦截待执行的sql，然后重写sql，根据dialect方言，添加对应的物理分页语句和物理分页参数。
举例：select * from student，拦截sql后重写为：select t.* from （select * from student）t limit 0，10

### 20. Mybatis动态sql是做什么的？都有哪些动态sql？能简述一下动态sql的执行原理不？

Mybatis动态sql可以让我们在Xml映射文件内，以标签的形式编写动态sql，完成逻辑判断和动态拼接sql的功能，Mybatis提供了9种动态sql标签trim|where|set|foreach|if|choose|when|otherwise|bind。
其执行原理为，使用OGNL从sql参数对象中计算表达式的值，根据表达式的值动态拼接sql，以此来完成动态sql的功能。

### 21. Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？

Mybatis仅支持association关联对象和collection关联集合对象的延迟加载，association指的就是一对一，collection指的就是一对多查询。在Mybatis配置文件中，可以配置是否启用延迟加载lazyLoadingEnabled=true|false。
它的原理是，使用CGLIB创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，比如调用a.getB().getName()，拦截器invoke()方法发现a.getB()是null值，那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来，
然后调用a.setB(b)，于是a的对象b属性就有值了，接着完成a.getB().getName()方法的调用。这就是延迟加载的基本原理。
当然了，不光是Mybatis，几乎所有的包括Hibernate，支持延迟加载的原理都是一样的。

### 22. 如何获取自动生成的(主)键值？

如果我们一般插入数据的话，如果我们想要知道刚刚插入的数据的主键是多少，我们可以通过以下的方式来获取
通过LAST_INSERT_ID()获取刚插入记录的自增主键值，在insert语句执行后，执行select LAST_INSERT_ID()就可以获取自增主键。

```
    <insert id="insertUser" parameterType="cn.itcast.mybatis.po.User">
        <selectKey keyProperty="id" order="AFTER" resultType="int">
            select LAST_INSERT_ID()
        </selectKey>
        INSERT INTO USER(username,birthday,sex,address) VALUES(#{username},#{birthday},#{sex},#{address})
    </insert>
```

### 23. 在mapper中如何传递多个参数？

第一种：使用占位符的思想

在映射文件中使用#{0},#{1}代表传递进来的第几个参数,使用@param注解：来命名参数

第二种：使用Map集合作为参数来装载，根据key自动找到对应Map集合的value
 
 
 
 
PS:由于一直使用SpringBoot、SpringCloud，对于SSM没有过多研究,主要是根据网络和自己的经历整理的。仅供参考