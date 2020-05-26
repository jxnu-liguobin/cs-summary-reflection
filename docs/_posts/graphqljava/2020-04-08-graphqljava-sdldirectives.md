---
title: SDL Directives
categories:
  - GraphqlJava
tags:
  - graphql-java 1.4文档
description: 本章介绍graphql-java中的如何使用指令功能
---

# 2020-04-08-GraphqlJava-SDLDirectives

* 目录

  {:toc}

## Adding Behaviour

SDL允许您以声明的方式定义您的graphql类型，而无需使用代码。但是，您仍然需要连接（织入）以支持这些类型及其字段的所有逻辑。

Schema指令允许您执行此操作。您可以在SDL元素上放置指令，然后编写一次支持逻辑（可以通用的），然后可以将其应用到许多地方。

“一次编写”的想法是这里的关键概念。只有代码位置需要编写逻辑，最后将其应用于SDL中具有命名指令的所有位置。

与在常规运行时连接中可能要在10-100s的数据获取器中进行连接相比，这是一种更强大的模型。

例如，假设您具有以下类型

```graphql
type Employee
    id : ID
    name : String!
    startDate : String!
    salary : Float
}
```

将薪水信息发布给每个可以看到该雇员姓名的人可能不是你想要的。相反，您可能想要某种访问控制，以便如果您的角色是经理（下文manager），则可以看到薪水，否则将无法获得任何数据（指薪水代表的字段）。

Directives（指令）可以帮助您更轻松地声明这一点。我们上面的声明将如下所示

```graphql
directive @auth(role : String!) on FIELD_DEFINITION

type Employee
    id : ID
    name : String!
    startDate : String!
    salary : Float @auth(role : "manager")
}
```

因此，我们已经说过，只有拥有角色"manager"的人员才有权查看此字段。现在，我们可以在需要经理角色授权的任何字段上使用此指令。

```graphql
directive @auth(role : String!) on FIELD_DEFINITION

type Employee
    id : ID
    name : String!
    startDate : String!
    salary : Float @auth(role : "manager")
}

type Department {
    id : ID
    name : String
    yearlyOperatingBudget : Float @auth(role : "manager")
    monthlyMarketingBudget : Float @auth(role : "manager")
}
```

现在，我们需要使用此@auth指令连接可以处理任何字段的代码。我们使用graphql.schema.idl.SchemaDirectiveWiring来做到这一点。

```java
class AuthorisationDirective implements SchemaDirectiveWiring {

        @Override
        public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
            String targetAuthRole = (String) environment.getDirective().getArgument("role").getValue();

            GraphQLFieldDefinition field = environment.getElement();
            GraphQLFieldsContainer parentType = environment.getFieldsContainer();
            // 构建一个数据获取程序，该数据获取程序首先检查授权角色，然后再调用原始数据获取程序
            DataFetcher originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
            DataFetcher authDataFetcher = new DataFetcher() {
                @Override
                public Object get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
                    Map<String, Object> contextMap = dataFetchingEnvironment.getContext();
                    AuthorisationCtx authContext = (AuthorisationCtx) contextMap.get("authContext");

                    if (authContext.hasRole(targetAuthRole)) {
                        return originalDataFetcher.get(dataFetchingEnvironment);
                    } else {
                        return null;
                    }
                }
            };
            // 现在更改字段定义以使用新的授权数据提取程序
            environment.getCodeRegistry().dataFetcher(parentType, field, authDataFetcher);
            return field;
        }
    }

    // 我们通过指令名称将其连接到运行时（织入）
    RuntimeWiring.newRuntimeWiring()
            .directive("auth", new AuthorisationDirective())
            .build();
```

这已修改了GraphQLFieldDefinition，以便仅在当前授权上下文具有经理角色的情况下才调用其原始数据获取程序。 究竟使用哪种授权机制取决于您自己。举例说，您可以使用Spring Security，但graphql-java并不在乎。

您可以将此授权检查器提供给graphql输入的执行（ExecutionInput）的"context"对象，以便以后可以在DataFetchingEnvironment中对其进行访问。

```java
AuthorisationCtx authCtx = AuthorisationCtx.obtain();

ExecutionInput executionInput = ExecutionInput.newExecutionInput()
        .query(query)
        .context(authCtx)
        .build();
```

## Declaring Directives

为了在SDL中使用指令，graphql规范要求您必须在使用前声明其形态。在使用之前，我们上面的@auth指令示例需要像这样声明。

```graphql
# 这是指令声明
directive @auth(role : String!) on FIELD_DEFINITION

type Employee
    id : ID
    # 这是指令的用法    
    salary : Float @auth(role : "manager")
}
```

一个例外是@deprecated指令，它为您隐式声明，如下所示

```graphql
directive @deprecated(  reason: String = "No longer supported") on FIELD_DEFINITION | ENUM_VALUE
```

有效的SDL指令位置如下

```graphql
SCHEMA,
SCALAR,
OBJECT,
FIELD_DEFINITION,
ARGUMENT_DEFINITION,
INTERFACE,
UNION,
ENUM,
ENUM_VALUE,
INPUT_OBJECT,
INPUT_FIELD_DEFINITION
```

指令通常应用于字段定义，但是如您所见，它们可以在许多地方使用。

## Another Example - Date Formatting

日期格式是一个横切关注点，我们只需要编写一次并将其应用于许多地方。

下面演示了一个schema指令示例，该指令可将日期格式应用于LocaleDate对象的字段。

在此示例中最棒的是，它向应用到的每个字段添加了一个额外的格式参数。因此，客户端可以根据您的每个请求选择需要提供的日期格式。

```graphql
directive @dateFormat on FIELD_DEFINITION

type Query {
    dateField : String @dateFormat
}
```

那么我们的运行时代码可能是

```java
public static class DateFormatting implements SchemaDirectiveWiring {
    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();
        // DataFetcherFactories.wrapDataFetcher是包装数据读取器的助手，以便正确处理CompletionStage和POJO
        DataFetcher originalFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher dataFetcher = DataFetcherFactories.wrapDataFetcher(originalFetcher, ((dataFetchingEnvironment, value) -> {
            DateTimeFormatter dateTimeFormatter = buildFormatter(dataFetchingEnvironment.getArgument("format"));
            if (value instanceof LocalDateTime) {
                return dateTimeFormatter.format((LocalDateTime) value);
            }
            return value;
        }));

        // 这将通过为日期格式添加一个新的“format”参数来扩展字段，它允许客户端进行选择，并包装基本数据获取器，以便在基本值之上执行格式化。
        FieldCoordinates coordinates = FieldCoordinates.coordinates(parentType, field);
        environment.getCodeRegistry().dataFetcher(coordinates, dataFetcher);

        return field.transform(builder -> builder
                .argument(GraphQLArgument
                        .newArgument()
                        .name("format")
                        .type(Scalars.GraphQLString)
                        .defaultValue("dd-MM-YYYY")
                )
        );
    }

    private DateTimeFormatter buildFormatter(String format) {
        String dtFormat = format != null ? format : "dd-MM-YYYY";
        return DateTimeFormatter.ofPattern(dtFormat);
    }
}

static GraphQLSchema buildSchema() {

    String sdlSpec = "directive @dateFormat on FIELD_DEFINITION\n" +
                  "type Query {\n" +
                  "    dateField : String @dateFormat \n" +
                  "}";

    TypeDefinitionRegistry registry = new SchemaParser().parse(sdlSpec);

    RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .directive("dateFormat", new DateFormatting())
            .build();

    return new SchemaGenerator().makeExecutableSchema(registry, runtimeWiring);
}

public static void main(String[] args) {
    GraphQLSchema schema = buildSchema();
    GraphQL graphql = GraphQL.newGraphQL(schema).build();

    Map<String, Object> root = new HashMap<>();
    root.put("dateField", LocalDateTime.of(1969, 10, 8, 0, 0));

    String query = "" +
            "query {\n" +
            "    default : dateField \n" +
            "    usa : dateField(format : \"MM-dd-YYYY\") \n" +
            "}";

    ExecutionInput executionInput = ExecutionInput.newExecutionInput()
            .root(root)
            .query(query)
            .build();

    ExecutionResult executionResult = graphql.execute(executionInput);
    Map<String, Object> data = executionResult.getData();

    // data['default'] == '08-10-1969'
    // data['usa'] == '10-08-1969'
}
```

**注意，SDL定义没有format参数，一旦指令连接被应用，它就会被添加到字段定义中，因此客户可以开始使用它。** **请注意，graphql-java没有附带这个实现。这里提供的只是一个示例，您可以自己添加一些内容。**

## Chaining Behaviour

这些指令是按照它们遇到的顺序被应用的。例如，想象一下改变了字段值大小写的指令。

```graphql
directive @uppercase on FIELD_DEFINITION
directive @lowercase on FIELD_DEFINITION
directive @mixedcase on FIELD_DEFINITION
directive @reversed on FIELD_DEFINITION

type Query {
    lowerCaseValue : String @uppercase
    upperCaseValue : String @lowercase
    mixedCaseValue : String @mixedcase
    # 指令按顺序被应用，因此顺序将依次为lower, then upper, then mixed then reversed
    allTogetherNow : String @lowercase @uppercase @mixedcase @reversed
}
```

