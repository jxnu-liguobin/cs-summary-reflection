---
title: Exceptions
categories:
- GraphqlJava
tags: [graphql-java 1.4文档]
description: 本章介绍graphql-java中的常见异常
---

* 目录
{:toc}

# 运行时异常

如果遇到某些特殊情况，graphql引擎可能会抛出运行时异常。以下列出了可以从graphql.execute(...)调用中抛出的异常类型。

这些不是执行中的graphql错误，而是在完全不可接受的条件下执行graphql查询。

* graphql.schema.CoercingSerializeException 
    - 当无法通过标量类型对值进行序列化时（例如，将String值强制转换为Int），将抛出此异常。
* graphql.schema.CoercingParseValueException 
    - 当无法通过标量类型解析值时（例如，将String输入值解析为Int），将抛出该异常。
* graphql.execution.UnresolvedTypeException 
    - 如果graphql.schema.TypeResolver无法提供给定的接口或联合类型的具体对象类型，将抛出此异常。
* graphql.execution.NonNullableValueCoercedAsNullException 
    - 如果在执行过程中将非null变量参数强制转化为null值，将抛出该异常。
* graphql.execution.InputMapDefinesTooManyFieldsException 
     - 如果查询中定义了多个操作，并且缺少操作名，或者GraphQL查询中没有包含匹配的操作名，将抛出该异常。
* graphql.GraphQLException 
    - 被抛出为通用运行时异常，例如，如果代码在检查POJO时无法访问命名字段，如果可以的话，它类似于RuntimeException。
* graphql.AssertException 
    - 对于真正意外的代码条件，它被作为低级的代码断言异常抛出，即我们断言的事情在实践中永远不应该发生。