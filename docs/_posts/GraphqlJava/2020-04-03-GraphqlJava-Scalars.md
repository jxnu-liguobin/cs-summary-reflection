---
title: Scalars
categories:
- GraphqlJava
tags: [V1.4]
description: 本章介绍graphql-java中如何自定义标量类型
---

* 目录
{:toc}

graphql类型系统的叶节点称为标量。一旦达到标量类型，就无法进一步下降到类型层次结构中。标量类型旨在表示不可分的值。

graphql规范指出，所有实现都必须具有以下标量类型。

* String 又名 GraphQLString - 一个UTF‐8字符序列
* Boolean 又名 GraphQLBoolean - true 或 false
* Int 又名 GraphQLInt - 一个有符号的32位整数
* Float 又名 GraphQLFloat - 一个有符号双精度浮点数
* ID 又名 GraphQLID 唯一标识符，以与String相同的方式序列化；但是，将其定义为ID表示它不是人类可读的。

graphql-java添加了以下标量类型，这些标量类型在基于Java的系统中很有用

* Long GraphQLLong - java.lang.Long
* Short GraphQLShort - java.lang.Short 
* Byte GraphQLByte - java.lang.Byte
* BigDecimal GraphQLBigDecimal - java.math.BigDecimal
* BigInteger GraphQLBigInteger - java.math.BigInteger

graphql.Scalars类包含所提供标量类型的单例实例

# Writing your Own Custom Scalars

您可以编写自己的自定义标量实现。这样，您将负责在运行时强制值，我们将在稍后解释。

想象我们决定需要一个电子邮件标量类型。它将电子邮件地址作为输入和输出。

我们将为此创建一个单例graphql.schema.GraphQLScalarType实例。
```java
public static final GraphQLScalarType EMAIL = new GraphQLScalarType("email", "A custom scalar that handles emails", new Coercing() {
    
    //接受一个Java对象并将其转换为该标量的输出形式
    @Override
    public Object serialize(Object dataFetcherResult) {
        return serializeEmail(dataFetcherResult);
    }
    
    //接受变量输入对象并转换为Java运行时表示形式
    @Override
    public Object parseValue(Object input) {
        return parseEmailFromVariable(input);
    }
    
    //将AST文字graphql.language.Value作为输入并转换为Java运行时表示形式
    @Override
    public Object parseLiteral(Object input) {
        return parseEmailFromAstLiteral(input);
    }
});
```

# Coercing values

任何自定义标量实现中的实际工作都是graphql.schema.Coercing实现。

您的自定义标量代码必须处理2种形式的输入（parseValue或parseLiteral）和1种形式的输出（序列化）。

想象一下这个查询，它使用变量，AST文字并输出我们的标量类型电子邮件。
```graphql
mutation Contact($mainContact: Email!) {
    makeContact(mainContactEmail: $mainContact, backupContactEmail: "backup@company.com") {
    id
    mainContactEmail
    }
}
```

我们的自定义电子邮件标量将
* 通过parseValue调用以将$mainContact变量值转换为运行时对象
* 通过parseLiteral调用以将AST graphql.language.StringValue"backup@company.com"转换为运行时对象
* 通过序列化调用，以将mainContactEmail的运行时表示形式转换为可用于输出的表单

# Validation of input and output

该方法可以验证接收到的输入是否有意义。例如，我们的电子邮件标量将尝试验证输入和输出确实是电子邮件地址。

graphql.schema.Coercing的JavaDoc方法协定如下

* 只允许从序列化抛出graphql.schema.CoercingSerializeException。这表明该值不能序列化为适当的形式。您不得允许其他运行时异常转义此方法以获取正常的graphql行为进行验证。您必须返回非null值。
* 仅允许从parseValue引发graphql.schema.CoercingParseValueException。这表明该值不能解析为适当形式的输入。您不得允许其他运行时异常转义此方法以获取正常的graphql行为进行验证。您必须返回非null值。
* 仅允许从parseLiteral引发graphql.schema.CoercingParseLiteralException。这表明AST值不能解析为适当形式的输入。您不得允许任何运行时异常转义此方法，以获取正常的graphql行为进行验证。

有些人试图依靠运行时异常进行验证，并希望它们以graphql错误的形式出现。不是这种情况。您必须遵循Coercing方法协定，以允许graphql-java引擎根据有关标量类型的graphql规范工作。

# Example implementation

以下是我们想象中的email标量类型的一个非常粗略的实现，以向您展示如何实现这种标量的Coercing方法。

```java
public static class EmailScalar {

    public static final GraphQLScalarType Email = new GraphQLScalarType("email", "A custom scalar that handles emails", new Coercing() {
        @Override
        public Object serialize(Object dataFetcherResult) {
            return serializeEmail(dataFetcherResult);
        }

        @Override
        public Object parseValue(Object input) {
            return parseEmailFromVariable(input);
        }

        @Override
        public Object parseLiteral(Object input) {
            return parseEmailFromAstLiteral(input);
        }
    });


    private static boolean looksLikeAnEmailAddress(String possibleEmailValue) {
        // 这个正则不是标准的验证邮件地址的那个
        return Pattern.matches("^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$", possibleEmailValue);
    }

    private static Object serializeEmail(Object dataFetcherResult) {
        String possibleEmailValue = String.valueOf(dataFetcherResult);
        if (looksLikeAnEmailAddress(possibleEmailValue)) {
            return possibleEmailValue;
        } else {
            throw new CoercingSerializeException("Unable to serialize " + possibleEmailValue + " as an email address");
        }
    }

    private static Object parseEmailFromVariable(Object input) {
        if (input instanceof String) {
            String possibleEmailValue = input.toString();
            if (looksLikeAnEmailAddress(possibleEmailValue)) {
                return possibleEmailValue;
            }
        }
        throw new CoercingParseValueException("Unable to parse variable value " + input + " as an email address");
    }

    private static Object parseEmailFromAstLiteral(Object input) {
        if (input instanceof StringValue) {
            String possibleEmailValue = ((StringValue) input).getValue();
            if (looksLikeAnEmailAddress(possibleEmailValue)) {
                return possibleEmailValue;
            }
        }
        throw new CoercingParseLiteralException(
                "Value is not any email address : '" + String.valueOf(input) + "'"
        );
    }
}
```

# 使用自定义标量

使用上面定义的Email类型完成请求与相应

```java
//自定义标量，......表示省略
......

//在StarWarsWiring中定义（或直接使用，主要为了使用依赖注入）
GraphQLScalarType Email = EmailScalar.Email;

......

//在织入时绑定
private GraphQLSchema buildSchema(String sdl) {
    //读取从resources下加载解析的gql schema文件的字符串
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
    //创建运行时类型织入
    RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
    //动态映射
    //运行时织入包含DataFetcher、TypeResolvers和自定义Scalar，它们是制作完全可执行的schema所必需的。
    builder.type(newTypeWiring("Query")
            .dataFetcher("hero", starWarsWiring.heroDataFetcher)
            .dataFetcher("human", starWarsWiring.humanDataFetcher)
            .dataFetcher("humans", starWarsWiring.humansDataFetcher)
            .dataFetcher("droid", starWarsWiring.droidDataFetcher));
    builder.type(newTypeWiring("Human").dataFetcher("friends", starWarsWiring.friendsDataFetcher));
    builder.type(newTypeWiring("Droid").dataFetcher("friends", starWarsWiring.friendsDataFetcher));
    builder.type(newTypeWiring("Character").typeResolver(starWarsWiring.characterTypeResolver));
    builder.type(newTypeWiring("Episode").enumValues(starWarsWiring.episodeResolver));
    builder.scalar(starWarsWiring.Email);//注入StarWarsWiring中实现的自定义的标量
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    //创建gql schema对象，构造可执行请求的gql
    return schemaGenerator.makeExecutableSchema(typeRegistry, builder.build());
}
```

在starWarsSchemaAnnotated.graphqls文件修改为
```graphql
scalar Email

......
type Human implements Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
    homePlanet: String
    secretBackstory : String @deprecated(reason : "We have decided that this is not canon")
    # 添加一个属性，使用了自定义标量
    email: Email!
}
......

```

修改Java POJO，为Human类增加String类型 email字段

使用如下查询
```graphql
{
  humans {
    name
    email
  }
}
```
返回信息（开了追踪所以有extensions属性）
```json
{
    "data": {
        "humans": [
            {
                "name": "Luke Skywalker",
                "email": "dreamylost@outlook.com"
            },
            {
                "name": "Darth Vader",
                "email": "dreamylost@outlook.com"
            },
            {
                "name": "Han Solo",
                "email": "dreamylost@outlook.com"
            },
            {
                "name": "Leia Organa",
                "email": "dreamylost@outlook.com"
            }
        ]
    },
    "extensions": {
        "tracing": {
            "version": 1,
            "startTime": "2020-04-03T09:25:00.753Z",
            "endTime": "2020-04-03T09:25:00.757Z",
            "duration": 3874865,
            "parsing": {
                "startOffset": 491647,
                "duration": 404205
            },
            "validation": {
                "startOffset": 754108,
                "duration": 231721
            },
            "execution": {
                "resolvers": [
                    {
                        "path": [
                            "humans"
                        ],
                        "parentType": "Query",
                        "returnType": "[Human]",
                        "fieldName": "humans",
                        "startOffset": 1317556,
                        "duration": 506334
                    },
                    {
                        "path": [
                            "humans",
                            0,
                            "name"
                        ],
                        "parentType": "Human",
                        "returnType": "String!",
                        "fieldName": "name",
                        "startOffset": 2007874,
                        "duration": 58120
                    },
                    {
                        "path": [
                            "humans",
                            0,
                            "email"
                        ],
                        "parentType": "Human",
                        "returnType": "Email!",
                        "fieldName": "email",
                        "startOffset": 2199262,
                        "duration": 58219
                    },
                    {
                        "path": [
                            "humans",
                            1,
                            "name"
                        ],
                        "parentType": "Human",
                        "returnType": "String!",
                        "fieldName": "name",
                        "startOffset": 2507644,
                        "duration": 36967
                    },
                    {
                        "path": [
                            "humans",
                            1,
                            "email"
                        ],
                        "parentType": "Human",
                        "returnType": "Email!",
                        "fieldName": "email",
                        "startOffset": 2643613,
                        "duration": 27507
                    },
                    {
                        "path": [
                            "humans",
                            2,
                            "name"
                        ],
                        "parentType": "Human",
                        "returnType": "String!",
                        "fieldName": "name",
                        "startOffset": 2880688,
                        "duration": 34660
                    },
                    {
                        "path": [
                            "humans",
                            2,
                            "email"
                        ],
                        "parentType": "Human",
                        "returnType": "Email!",
                        "fieldName": "email",
                        "startOffset": 3097616,
                        "duration": 47421
                    },
                    {
                        "path": [
                            "humans",
                            3,
                            "name"
                        ],
                        "parentType": "Human",
                        "returnType": "String!",
                        "fieldName": "name",
                        "startOffset": 3369605,
                        "duration": 30026
                    },
                    {
                        "path": [
                            "humans",
                            3,
                            "email"
                        ],
                        "parentType": "Human",
                        "returnType": "Email!",
                        "fieldName": "email",
                        "startOffset": 3505375,
                        "duration": 45639
                    }
                ]
            }
        },
        "dataloader": {
            "overall-statistics": {
                "loadCount": 8,
                "loadErrorCount": 0,
                "loadErrorRatio": 0.0,
                "batchInvokeCount": 1,
                "batchLoadCount": 4,
                "batchLoadRatio": 0.5,
                "batchLoadExceptionCount": 0,
                "batchLoadExceptionRatio": 0.0,
                "cacheHitCount": 4,
                "cacheHitRatio": 0.5
            },
            "individual-statistics": {
                "characters": {
                    "loadCount": 8,
                    "loadErrorCount": 0,
                    "loadErrorRatio": 0.0,
                    "batchInvokeCount": 1,
                    "batchLoadCount": 4,
                    "batchLoadRatio": 0.5,
                    "batchLoadExceptionCount": 0,
                    "batchLoadExceptionRatio": 0.0,
                    "cacheHitCount": 4,
                    "cacheHitRatio": 0.5
                }
            }
        }
    }
}
```

完整代码 https://github.com/jxnu-liguobin/springboot-examples