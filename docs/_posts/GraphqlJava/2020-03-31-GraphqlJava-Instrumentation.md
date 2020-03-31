---
title: Instrumentation
categories:
- GraphqlJava
tags: [Graphql-Java学习]
description: 本章介绍graphql-java中Instrumentation的功能以及使用
---

# Instrumentation 

这个词没找到好的中文对应。以下直接使用本单词。

graphql.execution.instrumentation.Instrumentation接口允许您注入代码，这可以观察一个查询的执行并且还可以更改运行时行为。

这样做的主要用例是允许性能监控和自定义日志记录，但是它可以用于许多不同的目的。

构建Graphql对象时，可以指定要使用的Instrumentation（如果有的话）。
```java
GraphQL.newGraphQL(schema)
        .instrumentation(new TracingInstrumentation())
        .build();
```

# Custom Instrumentation

即定制化的Instrumentation。Instrumentation的实现需要实现“begin”步骤方法，这些方法表示graphql查询的执行。

每个步骤都必须返回一个非null的graphql.execution.instrumentation.InstrumentationContext对象，该对象将在步骤完成时被调用，并且将被告知该对象成功或以Throwable失败。

以下是一个基本的自定义Instrumentation，它计算总体执行时间并将其放入有状态对象。

```java
/**
 * 自定义的instrumentation实现，用以计算请求处理时间
 *
 * @author 梦境迷离
 * @time 2020年03月31日14:22:24
 */
class CustomInstrumentationState implements InstrumentationState {
    private Map<String, Object> anyStateYouLike = new HashMap<>();

    void recordTiming(String key, long time) {
        anyStateYouLike.put(key, time);
    }
}

public class CustomInstrumentation extends SimpleInstrumentation {
    @Override
    public InstrumentationState createState() {
        return new CustomInstrumentationState();
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        long startNanos = System.nanoTime();
        return new SimpleInstrumentationContext<ExecutionResult>() {
            @Override
            public void onCompleted(ExecutionResult result, Throwable t) {
                CustomInstrumentationState state = parameters.getInstrumentationState();
                state.recordTiming(parameters.getQuery(), System.nanoTime() - startNanos);
            }
        };
    }

    @Override
    public DataFetcher<?> instrumentDataFetcher(DataFetcher<?> dataFetcher, InstrumentationFieldFetchParameters parameters) {
        return dataFetcher;
    }

    @Override
    public CompletableFuture<ExecutionResult> instrumentExecutionResult(ExecutionResult executionResult, InstrumentationExecutionParameters parameters) {
        return CompletableFuture.completedFuture(executionResult);
    }
}
```

# Chaining Instrumentation

您可以使用graphql.execution.instrumentation.ChainedInstrumentation类将多个Instrumentation对象组合在一起，该类接受Instrumentation对象的列表并按定义的顺序调用它们。

```java
List<Instrumentation> chainedList = new ArrayList<>();
chainedList.add(new FooInstrumentation());
chainedList.add(new BarInstrumentation());
ChainedInstrumentation chainedInstrumentation = new ChainedInstrumentation(chainedList);

GraphQL.newGraphQL(schema)
        .instrumentation(chainedInstrumentation)
        .build();
```

# Apollo Tracing Instrumentation

graphql.execution.instrumentation.tracing.TracingInstrumentation是一种Instrumentation实现，用于创建有关正在执行的查询的跟踪信息。

> 它遵循在以下位置定义的Apollo建议的跟踪格式：https://github.com/apollographql/apollo-tracing <https://github.com/apollographql/apollo-tracing>_

将创建一个详细的跟踪视图，并将其放置在结果的扩展部分中。
有查询请求如下
```
{
  human(id:"1001") {
    id
    name
  }
}
```
返回详细信息，在extensions中有追踪信息
```json
{
    "data": {
        "human": {
            "id": "1001",
            "name": "Darth Vader"
        }
    },
    "extensions": {
        "tracing": {
            "version": 1,
            "startTime": "2020-03-31T06:38:11.782Z",
            "endTime": "2020-03-31T06:38:11.787Z",
            "duration": 5009671,
            "parsing": {
                "startOffset": 763392,
                "duration": 651573
            },
            "validation": {
                "startOffset": 1075836,
                "duration": 271042
            },
            "execution": {
                "resolvers": [
                    {
                        "path": [
                            "human"
                        ],
                        "parentType": "Query",
                        "returnType": "Human",
                        "fieldName": "human",
                        "startOffset": 1676625,
                        "duration": 803572
                    },
                    {
                        "path": [
                            "human",
                            "id"
                        ],
                        "parentType": "Human",
                        "returnType": "ID!",
                        "fieldName": "id",
                        "startOffset": 3789805,
                        "duration": 752910
                    },
                    {
                        "path": [
                            "human",
                            "name"
                        ],
                        "parentType": "Human",
                        "returnType": "String!",
                        "fieldName": "name",
                        "startOffset": 4693376,
                        "duration": 49369
                    }
                ]
            }
        },
        "dataloader": {
            "overall-statistics": {
                "loadCount": 2,
                "loadErrorCount": 0,
                "loadErrorRatio": 0.0,
                "batchInvokeCount": 2,
                "batchLoadCount": 2,
                "batchLoadRatio": 1.0,
                "batchLoadExceptionCount": 0,
                "batchLoadExceptionRatio": 0.0,
                "cacheHitCount": 0,
                "cacheHitRatio": 0.0
            },
            "individual-statistics": {
                "characters": {
                    "loadCount": 2,
                    "loadErrorCount": 0,
                    "loadErrorRatio": 0.0,
                    "batchInvokeCount": 2,
                    "batchLoadCount": 2,
                    "batchLoadRatio": 1.0,
                    "batchLoadExceptionCount": 0,
                    "batchLoadExceptionRatio": 0.0,
                    "cacheHitCount": 0,
                    "cacheHitRatio": 0.0
                }
            }
        }
    }
}
```

# Field Validation Instrumentation

graphql.execution.instrumentation.fieldvalidation.FieldValidationInstrumentation是一种工具实现，可用于在执行查询之前验证字段及其参数。如果在此过程中返回错误，则查询执行将中止，并且错误将出现在查询结果中。

您可以使自己实现FieldValidation，也可以使用SimpleFieldValidation类添加简单的每个字段检查规则。
```java
/**
 * @author liguobin@growingio.com
 * @version 1.0, 2020/3/31
 */
public class FieldValidationBuilder {

    public static FieldValidationInstrumentation builder() {
        //遇到是human的请求，强制验证id长度不能小于4
        ExecutionPath fieldPath = ExecutionPath.parse("/human");
        FieldValidation fieldValidation = new SimpleFieldValidation()
                .addRule(fieldPath, (fieldAndArguments, environment) -> {
                    String nameArg = fieldAndArguments.getArgumentValue("id");
                    if (nameArg.length() < 4) {
                        return Optional.of(environment.mkError("Invalid id length", fieldAndArguments));
                    }
                    return Optional.empty();
                });

        return new FieldValidationInstrumentation(fieldValidation);
    }
}
```
有查询如下
```
{
  human(id:"1") { #改成长度不小4的字符串，就会返回数据或null
    id
    name
  }
}
```
返回完整信息
```json
{
    "errors": [
        {
            "message": "Invalid id length",
            "locations": [
                {
                    "line": 2,
                    "column": 3
                }
            ],
            "path": [
                "human"
            ]
        }
    ]
}
```

完整例子请参考 https://github.com/jxnu-liguobin/springboot-examples