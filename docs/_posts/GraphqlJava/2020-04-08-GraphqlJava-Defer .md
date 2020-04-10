---
title: Defer
categories:
- GraphqlJava
tags: [graphql-java 1.4文档]
description: 本章介绍graphql-java中的Defer使用
---

* 目录
{:toc}

# Deferred Execution

延迟执行

通常，执行查询时，您有两类数据：您立即需要的数据以及稍后可能到达的数据。

例如，假设该查询获取帖子及其评论和回复的数据。
```graphql
query {
   post {
       postText
       comments {
           commentText
       }
       reviews {
           reviewText {
       }
}
```

在这种形式下，您必须等待评论和回复数据被检索，然后才能将发布数据发送回客户端。所有三个数据元素都绑定到一个查询。

简单的方法是先进行两个查询以获取最重要的数据，但是现在有了更好的方法。

在graphql-java中提供了对延迟执行的实验性支持。
```graphql
query {
   post {
       postText
       comments @defer {
           commentText
       }
       reviews @defer {
           reviewText {
       }
}
```

@defer指令告诉引擎推迟执行这些字段，并在以后交付它们。查询的其余部分照常执行。
通常会有初始数据的ExecutionResult，然后是延迟数据的org.reactivestreams.Publisher。

在上面的查询中，帖子数据将在初始结果中发送出去，然后稍后将评论和回复数据（按查询顺序）发送到发布者（Publisher）。通过订阅延迟结果可以在结果真正到达时，主动发送给客户端。

您可以像执行任何其他graphql查询一样执行查询。延期结果Publisher将通过扩展地map提供给您
```java
GraphQLSchema schema = buildSchemaWithDirective();
GraphQL graphQL = GraphQL.newGraphQL(schema).build();

//deferredQuery包含带有@defer指令的查询
ExecutionResult initialResult = graphQL.execute(ExecutionInput.newExecutionInput().query(deferredQuery).build());

//初始结果首先发生，延迟的结果将在这些初始结果完成后开始
sendResult(httpServletResponse, initialResult);

Map<Object, Object> extensions = initialResult.getExtensions();
Publisher<ExecutionResult> deferredResults = (Publisher<ExecutionResult>) extensions.get(GraphQL.DEFERRED_RESULTS);

//像其他reactive streams一样订阅延迟的结果
deferredResults.subscribe(new Subscriber<ExecutionResult>() {

    Subscription subscription;

    @Override
    public void onSubscribe(Subscription s) {
        subscription = s;
        // how many you request is up to you
        subscription.request(10);
    }

    @Override
    public void onNext(ExecutionResult executionResult) {
        //当每个延迟结果到达时，将其发送到需要去的地方
        sendResult(httpServletResponse, executionResult);
        subscription.request(10);
    }

    @Override
    public void onError(Throwable t) {
        handleError(httpServletResponse, t);
    }

    @Override
    public void onComplete() {
        completeResponse(httpServletResponse);
    }
});
```

上面的代码订阅了延迟的结果，当每个结果到达时，将其发送给客户端。

您可以在http://www.reactive-streams.org/上查看有关reactive-streams的更多详细信息。

graphql-java仅产生延迟结果的流。它本身并不关心使用何种网络方式发送这些结果。
这很重要，但基本的graphql-java库并不关心。您可以使用任何网络机制（websockets / long poll /…。）将结果返回给客户。

还要注意，此功能目前是实验性的，没有由官方的graphql规范定义。我们保留在将来的版本中更改它的权利，或者如果它进入官方规范。graphql-java项目非常希望获得关于此功能的反馈。