---
title: 动态代理与反射在Graphql客户端的应用
categories:
- GraphqlJava
tags: [动态代理与反射]
description: 本文介绍如何使用反射和动态代理来减少Graphql客户端开发中的冗余代码
---

* 目录
{:toc}


# 反射与动态代理实践

## 简介

目前需要使用 graphql 对外提供 open api，准备开发一个 sdk，使得尽可能的自动实现 graphql 的调用。也就是绝大部分代码使用代码生成。

代码生成主要针对 Java，其他 JVM 语言都可以使用，对于其他语言也可以参考类似的设计方法。
使用的库是 [graphql-java-codegen](https://github.com/kobylynskyi/graphql-java-codegen)，该工具参考了 swagger-api 。

熟悉 graphql 的人应该知道，graphql 具有复杂的数据类型定义和更强的功能支持，如果要使用代码生成，但是编程语言与该类型不存在一一对应的关系，则可能会增加设计工具的复杂性。
好在 Java 支持绝大多数的 graphql 类型，我们使用的 graphql-java-codegen 也很好的支持了代码生成这一步。

总的来说，对 graphql-java-codegen 而言，会抽象出四个主要的模型：
- Request           用于抽象 graphql 的请求参数和请求类型，目前在 graphql 中没有 HTTP 的 GET/POST/DELETE/PUT，只有 query/mutation/subscription
- Response          用于抽象 graphql 的返回数据结构，众所周知，graphql 是强类型的，类似 protobuffer。
- Response Projection  有了返回的数据结构我们还需要选择返回哪些字段，这是 graphql 的特性之一，通常我们只返回我们需要的字段。
- Resolver  用于抽象 graphql 的接口，每个 resolver 都是一个 graphql 中的一个方法，对应一个 HTTP 调用。需要注意的是，对一个资源的操作，CRUD 可以生成四个 resolver，每个仅有一个方法；也可以仅生成一个 resolver 接口包含有四个方法。这取决于代码生成策略。

有了上面基础，我们在看如何使用生成的实现 graphql client。

我们的 schema 如下：
```
type Query {
    hero(episode: Episode) : Character
    human(id : String) : Human
    humans: [Human]
    droid(id: ID!) : Droid
}
interface Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
    secretBackstory : String @deprecated(reason : "We have decided that this is not canon")
}

type Human implements Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
    homePlanet: String
    secretBackstory : String @deprecated(reason : "We have decided that this is not canon")
    email: Email!
}

type Droid implements Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
    primaryFunction: String
    secretBackstory : String @deprecated(reason : "We have decided that this is not canon")
}
```

## 普通方式

我们使用测试代码生成了一个 resolver，它包含了四个方法
```java
@javax.annotation.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2020-08-10T15:37:50+0800"
)
public interface QueryResolver {

    @com.fasterxml.jackson.annotation.JsonTypeInfo(use=com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include=com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY,property = "__typename")
    @com.fasterxml.jackson.annotation.JsonSubTypes(value = {
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = HumanDO.class, name = "Human"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = DroidDO.class, name = "Droid")})
    CharacterDO hero(EpisodeDO episode) throws Exception;

    HumanDO human(String id) throws Exception;

    java.util.List<HumanDO> humans() throws Exception;

    DroidDO droid(String id) throws Exception;

}
```
正常情况下，我们应该实现这个接口，然后使用IOC注入这个接口或者 new 接口的实现类，调用 graphql 服务端。
下面是一个使用 Scala 实现的 QueryResolverImpl 
```scala
class QueryResolverImpl extends QueryResolver {

  @throws[Exception]
  def hero(episode: EpisodeDO): CharacterDO = {
    val heroQueryRequest = new HeroQueryRequest
    heroQueryRequest.setEpisode(episode)
    //must use typename, and add jackson annotation to support, since v2.4
    val characterResponseProjection = new CharacterResponseProjection().id().name().typename()
      .friends(new CharacterResponseProjection().id().name().typename()).appearsIn()
    val graphQLRequest = new GraphQLRequest(heroQueryRequest, characterResponseProjection)
    val retFuture = OkHttp.executeRequest(graphQLRequest, new TypeReference[CharacterDO] {})
    val ret = Await.result(retFuture, Duration.Inf)
    ret
  }

  @throws[Exception]
  def human(id: String): HumanDO = {
    val humanQueryRequest = new HumanQueryRequest
    humanQueryRequest.setId(id)
    //must use typename, and add jackson annotation to support, since v2.4
    val humanResponseProjection = new HumanResponseProjection().id().name().typename()
    val graphQLRequest = new GraphQLRequest(humanQueryRequest, humanResponseProjection)
    val retFuture = OkHttp.executeRequest(graphQLRequest, new TypeReference[HumanDO] {})
    val ret = Await.result(retFuture, Duration.Inf)
    ret
  }

  @throws[Exception]
  def humans: util.List[HumanDO] = {
    import scala.collection.JavaConverters._
    val humanQueryRequest = new HumansQueryRequest
    //must use typename, and add jackson annotation to support, since v2.4
    val humanResponseProjection = new HumanResponseProjection().id().name().typename()
    val graphQLRequest = new GraphQLRequest(humanQueryRequest, humanResponseProjection)
    val retFuture = OkHttp.executeRequest(graphQLRequest, new TypeReference[List[HumanDO]] {})
    val ret = Await.result(retFuture, Duration.Inf)
    ret.asJava
  }

  @throws[Exception]
  def droid(id: String): DroidDO = {
    val productByIdQueryRequest = new DroidQueryRequest
    productByIdQueryRequest.setId(id)
    //must use typename, and add jackson annotation to support, since v2.4
    val droidResponseProjection = new DroidResponseProjection().id().name().typename()
    val graphQLRequest = new GraphQLRequest(productByIdQueryRequest, droidResponseProjection)
    val retFuture = OkHttp.executeRequest(graphQLRequest, new TypeReference[DroidDO] {})
    val ret = Await.result(retFuture, Duration.Inf)
    ret
  }
}
```
可以看到，不仅需要为每个 resolver 编写一个实现类，而且每个 request 都需要去 set 传进来的参数，并且需要实例化 projection 指定返回的字段（调用 projection 的方法，将其添加到 Map 中）；如果是嵌套的字段，还需要 typename。
很显然，所有 resolver 的实现，也都是这个流程，通过构造 request 和 projection 来实例化 GraphQLRequest，生成 graphql query，调用服务端接口。
所以我们可以使用动态代理来优化这个调用过程。

## 动态代理 & 反射

首先，我们当然需要创建一个代理对象，我们定义一个 Java 类，叫做 ResolverImplClient
```java
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLOperationRequest;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseProjection;

import java.lang.reflect.Proxy;

/**
 * invoker for proxy
 *
 * @author liguobin@growingio.com
 * @version 1.0, 2020/7/28
 */
final public class ResolverImplClient {

    private Class<?> resolver;
    private GraphQLResponseProjection projection;
    private GraphQLOperationRequest request;
    private int maxDepth;

    private Object getResolver() {
        DynamicProxy invocationHandler = new DynamicProxy(projection, request, maxDepth);
        return Proxy.newProxyInstance(resolver.getClassLoader(), new Class[]{resolver}, invocationHandler);
    }

    private void setResolver(Class<?> resolver) {
        this.resolver = resolver;
    }

    private void setRequest(GraphQLOperationRequest request) {
        this.request = request;
    }

    private void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    private void setProjection(GraphQLResponseProjection projection) {
        this.projection = projection;
    }


    public static class ResolverImplClientBuilder {
        private GraphQLResponseProjection projection;
        private GraphQLOperationRequest request;
        private int maxDepth = 3;

        private ResolverImplClientBuilder() {

        }

        public ResolverImplClientBuilder setRequest(GraphQLOperationRequest request) {
            this.request = request;
            return this;
        }

        public ResolverImplClientBuilder setMaxDepth(int maxDepth) {
            this.maxDepth = maxDepth;
            return this;
        }

        public ResolverImplClientBuilder setProjection(GraphQLResponseProjection projection) {
            this.projection = projection;
            return this;
        }

        public static ResolverImplClientBuilder newBuilder() {
            return new ResolverImplClientBuilder();
        }

        /**
         * Resolver is generic type, if we do not want to cast to real resolver on the user side, we need set resolver when invoker builder method,
         * although this is not in line with the conventional builder model
         *
         * @return R resolver which need for invoke graphql
         */
        @SuppressWarnings(value = "unchecked")
        public <R> R build(Class<R> resolver) {
            ResolverImplClient invoke = new ResolverImplClient();
            assert (projection != null);
            assert (resolver != null);
            assert (request != null);
            invoke.setProjection(projection);
            invoke.setResolver(resolver);
            invoke.setMaxDepth(maxDepth);
            invoke.setRequest(request);
            return (R) invoke.getResolver();
        }
    }

}
```
这里我们将泛型放到了 build 方法上，是因为基于构建者模式，外部类的泛型在内部类 new 时会丢失则得到的代理对象需要强转，而强转增加了使用的复杂性，所以将泛型放到了最后的 build 方法。
当然，如果是 Scala，可以使用 runtimeclass，因为目的是想 JVM 语言都支持，所以这里都使用 Java 实现了。

知道如何构造代理对象，我们还需要定义处理函数，在 Java 中，我们只需要实现 InvocationHandler 接口（JDK动态代理）

```java
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLOperationRequest;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseField;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseProjection;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * dynamic proxy for create request
 * <p>
 * this is a experimental implement
 *
 * @author liguobin@growingio.com
 * @version 1.0, 2020/7/28
 */
final public class DynamicProxy implements InvocationHandler, ExecutionGraphql {

    private GraphQLResponseProjection projection;

    private GraphQLOperationRequest request;

    /**
     * should limit max depth when invoke method on projection.
     */
    private int maxDepth;

    DynamicProxy(GraphQLResponseProjection projection, GraphQLOperationRequest request, int maxDepth) {
        this.projection = projection;
        this.request = request;
        this.maxDepth = maxDepth;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.println("before Invoking");
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                System.out.println("invoking by resolver implement");
                return method.invoke(this, args);
            } catch (Throwable t) {
                t.printStackTrace();
                return null;
            }
        } else {
            System.out.println("invoking by dynamic proxy");
            return proxyInvoke(method, args);
        }
    }

    /**
     *  在处理投影（projection）时，我们使用反射直接调用所有方法（不处理alias），而在处理请求（需要参数）时，我们使用反射来获取 input 字段，这是基于 setXXX 方法的内部实现来实现的。
     *
     * @param parentProjection
     * @param parentMethod
     * @param currentDepth
     */
    private void invokeOnProjection(GraphQLResponseProjection parentProjection, Method parentMethod, int currentDepth) {
        try {
            //对于无参方法，我们使用了 `parentMethod.invoke(parentProjection, null);`，这里的 null 不能使用空数组替代。
            if (parentMethod.getParameterCount() == 0) {
                System.out.println("method <" + parentMethod.getName() + ">");
                parentMethod.invoke(parentProjection, null);
                return;
            }

            //if this method have parameters, eg: name(String alias) or friends(CharacterResponseProjection subProjection), friends(CharacterResponseProjection subProjection, String alias),
            //we only handle do not have any alias, like: friends(CharacterResponseProjection subProjection)
            for (Class<?> parameterClazz : parentMethod.getParameterTypes()) {
                //处理只有一个参数的方法调用
                if (parentMethod.getParameterCount() == 1) {
                    if (GraphQLResponseProjection.class.isAssignableFrom(parameterClazz)) {
                        currentDepth++;
                        GraphQLResponseProjection subProjection = (GraphQLResponseProjection) parameterClazz.newInstance();
                        //at now,not support `..on`
                        List<Method> methods = Arrays.stream(parameterClazz.getDeclaredMethods()).filter(m -> !m.getName().startsWith("on")).collect(Collectors.toList());
                        for (Method subProjectionMethod : methods) {
                            //if this method have no parameter
                            if (subProjectionMethod.getParameterCount() == 0) {
                                String t = "->";
                                for (int i = 0; i < currentDepth; i++) {
                                    t = t.concat("->");
                                }
                                System.out.println(t + " method <" + subProjectionMethod.getName() + ">");
                                subProjectionMethod.invoke(subProjection, null);
                            } else if (subProjectionMethod.getParameterCount() == 1 && GraphQLResponseProjection.class.isAssignableFrom(subProjectionMethod.getParameterTypes()[0])) {
                                //if this method have one parameter and type is GraphQLResponseProjection sub class
                                //recursive continuation call
                                if (currentDepth < maxDepth) {
                                    invokeOnProjection(subProjection, subProjectionMethod, currentDepth);
                                }
                            } else {
                                //TODO getParameterCount == 2, (GraphQLResponseProjection sub and String alias)
                            }
                        }
                        parentMethod.invoke(parentProjection, subProjection);
                    }
                } else {
                    //TODO getParameterCount == 2, (GraphQLResponseProjection sub and String alias)
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }


    private Object proxyInvoke(Method method, Object[] args) {
        int i = 0;
        Field field = null;
        List<GraphQLResponseField> fields = null;
        String entityClazzName;
        //对于集合类型，目前只处理了 List，我们需要拿到它的泛型参数，才能正确反序列化。所以使用 `method.getGenericReturnType()` 获取 resolver 接口的方法的返回类型。
        //如果返回类型是一个 参数化类型，我们还需要继续获取其中的值。`Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();` 帮助我们获取该类型的参数化类型的列表，
        //由于我们只处理 List 类型，很显然，它只有一个参数化类型，所以我们使用 `entityClazzName = parameterizedType[0].getTypeName();`。
        //而当 resolver 接口的方法的返回类型是一个普通 entity 时，我们只需要直接获取它的类名即可。
        //使用 `type.getTypeName();`
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            entityClazzName = parameterizedType[0].getTypeName();
        } else {
            entityClazzName = type.getTypeName();
        }
        List<Parameter> parameters = Arrays.stream(method.getParameters()).collect(Collectors.toList());

        //if this method have no parameter, then do not need invoke on request instance
        //other wise, we need append parameters to request field which use hashmap store params
         if (!parameters.isEmpty()) {
                for (Parameter parameter : parameters) {
                    Object argsCopy = args[i++];
                    //我们必须在反射时动态获取参数名称，所以我们应该使用java8的编译参数 -parameters，以便字节码中保留参数名称，否则参数名都是 var0 var1，没办法获取真实的参数名
                    request.getInput().put(parameter.getName(), argsCopy);
                }
         }

        try {
            field = projection.getClass().getSuperclass().getDeclaredField("fields");
            field.setAccessible(true);
            fields = (List<GraphQLResponseField>) field.get(projection);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }

        //if fields not null, use it directly, because user want to select fields
        if (projection != null && (fields == null || fields.isEmpty())) {
        //获取自己定义的方法，因为很显然，projection 是提供本 response 的字段选择功能，而父类继承过来的方法，我们根本不需要。
            for (Method m : projection.getClass().getDeclaredMethods()) {
                invokeOnProjection(projection, m, 1);
            }
        }

        return executeByHttp(entityClazzName, request, projection);
    }
}
```
这里有比较关键的几点，我们使用动态代理时，没有办法知道需要返回哪些字段，所以一律返回所有字段。我们不考虑使用传递参数的形式指定字段，这对于使用客户端的用户来说，不够友好，用户只需要得到数据。

且动态代理是自动构建 projection，所以也没有办法知道需要对嵌套查询如何终止，所以这里采用限制递归深度，每次仅查询最大深度为3的的子结构。

如果我们不递归调用子类型的 projection，无法返回嵌套结构。在 graphql 中，想要返回该数据中的字段的字段，则需要提供字段本身的详细结构。

如果我们不递归，friends 就不知道返回哪些数据，此时可以干脆去掉 friends()（此时返回结构中就没有 friends 的内容了），也可以向最上面普通方式一样：
```scala
val characterResponseProjection = new CharacterResponseProjection().id().name().typename()
      .friends(new CharacterResponseProjection().id().name().typename()).appearsIn() //向 friends 传入 CharacterResponseProjection 查询 friends 的详细内容。
```

现在我们可以使用动态代理来发起调用：
使用Java
```java
import io.github.dreamylost.api.HumanQueryResolver;
import io.github.dreamylost.api.HumansQueryResolver;
import io.github.dreamylost.model.HumanDO;
import io.github.dreamylost.model.HumanQueryRequest;
import io.github.dreamylost.model.HumanResponseProjection;
import io.github.dreamylost.model.HumansQueryRequest;

import java.util.List;

/**
 * @author liguobin@growingio.com
 * @version 1.0, 2020/7/29
 */
public class HumanResolverJavaApp {

    public static void main(String[] args) {

        ResolverImplClient.ResolverImplClientBuilder humanInvokerBuilder = ResolverImplClient.ResolverImplClientBuilder.newBuilder().
                setProjection(new HumanResponseProjection());
        //Set your own request and resolver for each request
        try {
            System.out.println("======human========");
            //默认返回所有projection的字段，如果需要自己选择怎么办？使用：new HumanQueryRequest().id().name()，就可以只返回id和name字段的数据了。
            //此时最大嵌套递归深度不生效
            HumanDO humanDO = humanInvokerBuilder.setRequest(new HumanQueryRequest()).build(HumanQueryResolver.class).human("1001");
            assert humanDO.getEmail() == "dreamylost@outlook.com";
            System.out.println(humanDO);

            System.out.println("======humans========");
            List<HumanDO> humanDOs = humanInvokerBuilder.setRequest(new HumansQueryRequest()).build(HumansQueryResolver.class).humans();
            assert humanDOs.size() == 4;
            System.out.println(humanDOs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //......
    }
}
```
使用Scala
```scala
import io.github.dreamylost.api.{ HumanQueryResolver, HumansQueryResolver }
import io.github.dreamylost.model.{ HumanQueryRequest, HumanResponseProjection, HumansQueryRequest }

import scala.collection.JavaConverters._

/**
 * use invoke by proxy in scala
 *
 * @author liguobin@growingio.com
 * @version 1.0,2020/7/28
 */
object HumanResolverScalaApp extends App {

  //For a model, the projection and maximum depth fields can be common
  val humanInvokerBuilder = ResolverImplClient.ResolverImplClientBuilder.newBuilder().
    setProjection(new HumanResponseProjection())

  //Set your own request and resolver for each request
  println("======human========")
  //默认返回所有projection的字段，如果需要自己选择怎么办？使用：new HumanQueryRequest().id().name()，就可以只返回id和name字段的数据了。
  //此时最大嵌套递归深度不生效
  val human = humanInvokerBuilder.setRequest(new HumanQueryRequest).build(classOf[HumanQueryResolver]).human("1001")
  println(human)

  println("======humans========")
  val humans = humanInvokerBuilder.setRequest(new HumansQueryRequest).build(classOf[HumansQueryResolver]).humans().asScala
  for (human <- humans) {
    println("->" + human)
  }
    //......
}
```
可以看到，我们实际上只需要一个链式调用即可，无需编写 resolver 的实现类。也就是，我们只需要使用代码生成工具，生成代码，就可以使用代码调用服务端了。