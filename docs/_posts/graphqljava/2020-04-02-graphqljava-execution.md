---
title: Execution
categories:
  - GraphqlJava
tags:
  - graphql-java 1.4文档
description: 本章介绍graphql-java中如何执行请求
---

# 2020-04-02-GraphqlJava-Execution

* 目录

  {:toc}

## Queries

要对schema执行查询，需要使用适当的参数构建一个新的GraphQL对象，然后调用execute\(\)方法。

查询的结果是ExecutionResult，它可能包含查询数据或错误信息列表。

```java
GraphQLSchema schema = GraphQLSchema.newSchema()
        .query(queryType)
        .build();

GraphQL graphQL = GraphQL.newGraphQL(schema)
        .build();

ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { hero { name } }")
        .build();

ExecutionResult executionResult = graphQL.execute(executionInput);

Object data = executionResult.getData();
List<GraphQLError> errors = executionResult.getErrors();
```

## Data Fetchers

每个graphql字段类型都有一个与之关联的graphql.schema.DataFetcher。其他graphql实现通常将这种类型的代码称为“resolvers”。

通常，您可以依赖graphql.schema.PropertyDataFetcher来检查Java POJO对象以从中获取字段值。如果您未在字段上指定数据提取程序，则将使用此数据提取器。

但是，您可能需要通过自己的自定义数据获取程序来获取顶级领域对象。这可能涉及进行数据库调用或通过HTTP语句与另一个系统交互。

graphql-java对于如何获取领域数据对象并不固执己见，这是您最关心的问题。对于该数据的用户授权也没有意见。您应该将所有逻辑在业务逻辑层代码中实现。

数据获取器可能如下所示

```java
DataFetcher userDataFetcher = new DataFetcher() {
    @Override
    public Object get(DataFetchingEnvironment environment) {
        return fetchUserFromDatabase(environment.getArgument("userId"));
    }
};
```

每个DataFetcher都会传递一个graphql.schema.DataFetchingEnvironment对象，该对象包含要获取的字段，向该字段提供了哪些参数以及其他信息，例如该字段的父对象，查询根对象或查询上下文对象。

在上面的示例中，执行将等待数据获取程序返回后再继续。您可以通过返回CompletionStage使DataFetcher异步执行，这将在此页进一步解释。

## Exceptions while fetching data

如果在数据获取器程序调用期间发生异常，则默认情况下，执行策略将产生graphql.ExceptionWhileDataFetching错误，并将其添加到结果错误列表中。请记住，graphql允许带有错误的部分结果。

这是标准行为的代码

```java
public class SimpleDataFetcherExceptionHandler implements DataFetcherExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(SimpleDataFetcherExceptionHandler.class);

    @Override
    public void accept(DataFetcherExceptionHandlerParameters handlerParameters) {
        Throwable exception = handlerParameters.getException();
        SourceLocation sourceLocation = handlerParameters.getField().getSourceLocation();
        ExecutionPath path = handlerParameters.getPath();

        ExceptionWhileDataFetching error = new ExceptionWhileDataFetching(path, exception, sourceLocation);
        handlerParameters.getExecutionContext().addError(error);
        log.warn(error.getMessage(), exception);
    }
}
```

如果抛出的异常本身是GraphqlError，则它将消息和自定义扩展属性从该异常传输到ExceptionWhileDataFetching对象。这使您可以将自己的自定义属性放入并发送到调用方的graphql错误中。

例如，假设您的数据获取程序引发了此异常。foo和fizz属性将包含在生成的graphql错误中。

```java
class CustomRuntimeException extends RuntimeException implements GraphQLError {
    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> customAttributes = new LinkedHashMap<>();
        customAttributes.put("foo", "bar");
        customAttributes.put("fizz", "whizz");
        return customAttributes;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}
```

您可以通过创建自己的graphql.execution.DataFetcherExceptionHandler异常处理代码并将其提供给执行策略来更改此行为。

例如，上面的代码记录了潜在的异常和堆栈跟踪。某些人可能不希望在输出错误列表中看到它。因此，您可以使用此机制来更改该行为。

```java
DataFetcherExceptionHandler handler = new DataFetcherExceptionHandler() {
        @Override
        public void accept(DataFetcherExceptionHandlerParameters handlerParameters) {
            //自定义处理逻辑
        }
    };
    ExecutionStrategy executionStrategy = new AsyncExecutionStrategy(handler);
```

## Returning data and errors

通过直接返回graphql.execution.DataFetcherResult或包装在CompletableFuture实例中以异步执行，还可以在DataFetcher实现中同时返回数据和多个错误。当您的DataFetcher可能需要从多个源或另一个GraphQL资源中检索数据时，这很有用。

在此示例中，DataFetcher从另一个GraphQL资源检索用户并返回其数据和错误。

```java
DataFetcher userDataFetcher = new DataFetcher() {
        @Override
        public Object get(DataFetchingEnvironment environment) {
            Map response = fetchUserFromRemoteGraphQLResource(environment.getArgument("userId"));
            List<GraphQLError> errors = response.get("errors")).stream()
                .map(MyMapGraphQLError::new)
                .collect(Collectors.toList();
            return new DataFetcherResult(response.get("data"), errors);
        }
    };
```

## Serializing results to JSON

调用graphql的最常见方法是通过HTTP并期望返回JSON响应。因此，您需要将graphql.ExecutionResult转换为有效JSON。

常用的方法是使用JSON序列化库，例如Jackson或GSON。但是，他们对数据结果的确切解释对他们来说却是特定的。例如，空值在graphql结果中很重要（字段不能被省略），因此您必须设置json映射器以包括它们。

为了确保您获得100％符合graphql规范的JSON结果，您应该在结果上调用toSpecification，然后将其作为JSON发送回去。

这将确保结果符合[http://facebook.github.io/graphql/\#sec-Response中概述的规范](http://facebook.github.io/graphql/#sec-Response中概述的规范)

```java
ExecutionResult executionResult = graphQL.execute(executionInput);

Map<String, Object> toSpecificationResult = executionResult.toSpecification();

sendAsJson(toSpecificationResult);
```

## Mutations

从[http://graphql.org/learn/queries/\#mutations了解更多关于graphql中的数据突变是好的起点。](http://graphql.org/learn/queries/#mutations了解更多关于graphql中的数据突变是好的起点。)

本质上，您需要定义一个以参数作为输入的GraphQLObjectType。这些参数可以在调用数据获取器时来改变存储的数据。

突变是通过类似以下查询的方式调用的

```java
mutation CreateReviewForEpisode($ep: Episode!, $review: ReviewInput!) {
  createReview(episode: $ep, review: $review) {
    stars
    commentary
  }
}
```

您需要在该突变操作期间发送参数，在这种情况下，请输入$ep和$review变量

您将创建类似这样的类型来处理此突变

```java
//使用java代码定义的方式
//输入类型 类似restful request body的参数
GraphQLInputObjectType episodeType = newInputObject()
        .name("Episode")
        .field(newInputObjectField()
                .name("episodeNumber")
                .type(Scalars.GraphQLInt))
        .build();
//输入类型 类似restful request body的参数
GraphQLInputObjectType reviewInputType = newInputObject()
        .name("ReviewInput")
        .field(newInputObjectField()
                .name("stars")
                .type(Scalars.GraphQLString)
                .name("commentary")
                .type(Scalars.GraphQLString))
        .build();

GraphQLObjectType reviewType = newObject()
        .name("Review")
        .field(newFieldDefinition()
                .name("stars")
                .type(GraphQLString))
        .field(newFieldDefinition()
                .name("commentary")
                .type(GraphQLString))
        .build();

GraphQLObjectType createReviewForEpisodeMutation = newObject()
        .name("CreateReviewForEpisodeMutation")
        .field(newFieldDefinition()
                .name("createReview")
                .type(reviewType)
                .argument(newArgument()
                        .name("episode")
                        .type(episodeType)
                )
                .argument(newArgument()
                        .name("review")
                        .type(reviewInputType)
                )
        )
        .build();

GraphQLCodeRegistry codeRegistry = newCodeRegistry()
        .dataFetcher(
                coordinates("CreateReviewForEpisodeMutation", "createReview"),
                mutationDataFetcher()
        )
        .build();


GraphQLSchema schema = GraphQLSchema.newSchema()
        .query(queryType)
        .mutation(createReviewForEpisodeMutation)
        .codeRegistry(codeRegistry)
        .build();
```

**请注意，输入参数的类型为GraphQLInputObjectType，这个很重要。输入参数只能是该类型，并且不能使用诸如GraphQLObjectType之类的输出类型。标量类型可同时作为输入和输出类型。**

此处的数据获取程序负责执行突变并返回一些合理的输出值。

```java
private DataFetcher mutationDataFetcher() {
    return new DataFetcher() {
        @Override
        public Review get(DataFetchingEnvironment environment) {
            // 输入参数必须是map，您可以将它们转换为数据提取器中的POJO
            // See http://facebook.github.io/graphql/October2016/#sec-Input-Objects
            Map<String, Object> episodeInputMap = environment.getArgument("episode");
            Map<String, Object> reviewInputMap = environment.getArgument("review");
            // 在这种情况下，我们有类型安全的Java对象来调用我们的支持代码
            EpisodeInput episodeInput = EpisodeInput.fromMap(episodeInputMap);
            ReviewInput reviewInput = ReviewInput.fromMap(reviewInputMap);
            //更新存储
            Review updatedReview = reviewStore().update(episodeInput, reviewInput);
            //返回数据的一个新的视图
            return updatedReview;
        }
    };
}
```

## Asynchronous Execution

graphql-java在执行查询时使用完全异步的执行技术。您可以像这样调用executeAsync\(\)来获得CompleteableFuture结果。

```java
GraphQL graphQL = buildSchema();

ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { hero { name } }")
        .build();

CompletableFuture<ExecutionResult> promise = graphQL.executeAsync(executionInput);

promise.thenAccept(executionResult -> {
    // 在这里，您可能会通过HTTP将结果作为JSON发送回
    encodeResultToJsonAndSendResponse(executionResult);
});

promise.join();
```

使用CompletableFuture可让您撰写在执行完成时将应用的动作和功能。 对.join\(\)的最终调用以等待执行。

实际上，在幕后，graphql-java引擎使用异步执行，并通过为您调用join使execute\(\)方法显得同步。 因此，以下代码实际上是相同的。

```java
ExecutionResult executionResult = graphQL.execute(executionInput);

//上面的代码等效于以下代码（很长时间）

CompletableFuture<ExecutionResult> promise = graphQL.executeAsync(executionInput);
ExecutionResult executionResult2 = promise.join();
```

如果graphql.schema.DataFetcher返回CompletableFuture 对象，则它将被组合到整个异步查询执行中。这意味着您可以并行触发多个字段获取请求。究竟使用哪种线程策略取决于数据获取程序代码。

以下代码使用标准Java java.util.concurrent.ForkJoinPool.commonPool\(\)线程执行程序在另一个线程中生产值。

```java
DataFetcher userDataFetcher = new DataFetcher() {
        @Override
        public Object get(DataFetchingEnvironment environment) {
            CompletableFuture<User> userPromise = CompletableFuture.supplyAsync(() -> {
                return fetchUserViaHttp(environment.getArgument("userId"));
            });
            return userPromise;
        }
    };
```

使用Java 8 lambda可以更简洁地编写如下

```java
DataFetcher userDataFetcher = environment -> CompletableFuture.supplyAsync(
    () -> fetchUserViaHttp(environment.getArgument("userId")));
```

graphql-java引擎确保将所有CompletableFuture对象组合在一起，以提供遵循graphql规范的执行结果。

graphql-java中有一个有用的快捷方式来创建异步数据获取程序。使用graphql.schema.AsyncDataFetcher.async\(DataFetcher \)来包装DataFetcher。可以将其与静态导入一起使用，以产生更具可读性的代码。

```java
DataFetcher userDataFetcher = async(environment -> fetchUserViaHttp(environment.getArgument("userId")));
```

## Execution Strategies

从graphql.execution.ExecutionStrategy派生的类用于运行查询或突变。graphql-java提供了许多不同的策略，如果您真的很热衷，甚至可以编写自己的策略。

您可以在创建GraphQL对象时确定要使用的执行策略。

```java
GraphQL.newGraphQL(schema)
        .queryExecutionStrategy(new AsyncExecutionStrategy())
        .mutationExecutionStrategy(new AsyncSerialExecutionStrategy())
        .build();
```

实际上，以上代码等效于默认设置，并且在大多数情况下这是执行策略的非常明智的选择。

### AsyncExecutionStrategy

默认情况下，“query”执行策略是graphql.execution.AsyncExecutionStrategy，它将把每个字段调度为CompleteableFuture对象，而不关心哪个字段先完成。此策略可实现最高效的执行。

调用的数据获取程序本身可以返回CompletionStage值，这将创建完全异步的行为。

因此，想象一个查询如下

```graphql
query {
  hero {
    enemies {
      name
    }
    friends {
      name
    }
  }
}
```

AsyncExecutionStrategy可以在与获取好友字段同时获取敌人字段。它不必先做敌人，再做朋友，这会降低效率。

但是它将按顺序组合结果。查询结果将遵循graphql规范，并返回按查询字段顺序组合的对象值。只有数据获取的执行可以任意顺序自由进行。

graphql规范中允许这种行为，并且实际上积极鼓励[http://facebook.github.io/graphql/\#sec-Query进行只读查询。](http://facebook.github.io/graphql/#sec-Query进行只读查询。)

有关详细信息，请参见规范[http://facebook.github.io/graphql/\#sec-Normal-evaluation](http://facebook.github.io/graphql/#sec-Normal-evaluation)

### AsyncSerialExecutionStrategy

graphql规范指出，必须按查询字段出现的顺序连续执行突变。

因此，默认情况下会将graphql.execution.AsyncSerialExecutionStrategy用于突变，并确保每个字段在处理下一个字段之前都已完成，依此类推。 您仍然可以在突变数据获取器中返回CompletionStage对象，但是它们将被串行执行并在分派下一个突变字段数据获取器之前完成。

### SubscriptionExecutionStrategy

Graphql订阅允许您创建对graphql数据的有状态订阅。您需要使用SubscriptionExecutionStrategy作为执行策略，因为它支持reactive-streams APIs。

有关反应式Publisher和Subscriber接口的更多信息，请参见[http://www.reactive-streams.org/。](http://www.reactive-streams.org/。)

另请参阅[订阅](https://www.graphql-java.com/documentation/v14/subscriptions/)页面，以获取有关如何编写基于订阅的graphql服务的更多详细信息。

## Query Caching

在graphql-java引擎执行查询之前，必须先对其进行解析和验证，并且此过程可能会有些耗时。

为了避免需要重新解析/验证GraphQL.Builder允许PreparsedDocumentProvider的实例重用Document实例。

**请注意，这不缓存查询结果，仅缓存已解析的文档。**

```java
Cache<String, PreparsedDocumentEntry> cache = Caffeine.newBuilder().maximumSize(10_000).build(); (1)

PreparsedDocumentProvider preparsedCache = PreparsedDocumentProvider {
    @Override
    public PreparsedDocumentEntry getDocument(ExecutionInput executionInput, Function<ExecutionInput, PreparsedDocumentEntry> computeFunction) {
            Function<String, PreparsedDocumentEntry> mapCompute = key -> computeFunction.apply(executionInput);
            return cache.get(executionInput.getQuery(), mapCompute);
    }
}    

GraphQL graphQL = GraphQL.newGraphQL(StarWarsSchema.starWarsSchema)
        .preparsedDocumentProvider(preparsedCache) (2)
        .build();
```

1. 创建首选缓存实例的实例，此处使用Caffeine [https://github.com/ben-manes/caffeine](https://github.com/ben-manes/caffeine) \_，因为它是一种高质量的缓存解决方案。缓存实例应该是线程安全的并且是共享的。
2. PreparsedDocumentProvider是仅具有getDocument方法的函数接口。调用此接口可获取缓存的预解析查询，如果不存在该查询，则可以调用computeFunction来解析和验证查询。

为了获得较高的高速缓存命中率，建议将字段参数作为变量传递，而不是直接在查询中传递。

以下查询

```graphql
query HelloTo {
     sayHello(to: "Me") {
        greeting
     }
}
```

应该改写为

```graphql
query HelloTo($to: String!) {
     sayHello(to: $to) {
        greeting
     }
}
```

带有变量

```javascript
{
   "to": "Me"
}
```

现在，无论提供了什么变量值，都可以重用该查询。

