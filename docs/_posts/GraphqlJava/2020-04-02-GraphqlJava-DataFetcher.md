---
title: Data fetching
categories:
- GraphqlJava
tags: [V1.4]
description: 本章介绍graphql-java中如何获取数据
---

* 目录
{:toc}

# How graphql fetches data

数据获取或提取器

graphql中的每个字段都有一个与之关联的graphql.schema.DataFetcher。

某些字段将使用专门的数据获取程序代码，该代码会知道如何访问数据库，以获取字段信息，而最简单的方法是使用字段名称和Plain Old Java Object（POJO）模式从返回的内存对象中获取数据。

> 注意：在其他graphql实现中，数据获取程序有时被称为“resolvers”。

因此，想象一下像下面这样的类型声明

```
type Query {
    products(match : String) : [Product]# 返回Product列表
}

type Product {
    id : ID
    name : String
    description : String
    cost : Float
    tax : Float
    launchDate(dateFormat : String = "dd, MMM, yyyy') : String
}
```

Query.products字段具有数据获取程序，Product类型的每个字段也具有数据获取程序。

Query.products字段上的数据获取器可能是更复杂的数据获取器，其中包含代码，该代码访问数据库以获取Product对象的列表。它带有一个可选的match参数，因此，如果客户指定了，则可以过滤这些产品的返回结果。

它可能如下所示
```java
DataFetcher productsDataFetcher = new DataFetcher<List<ProductDTO>>() {
    @Override
    public List<ProductDTO> get(DataFetchingEnvironment environment) {
        DatabaseSecurityCtx ctx = environment.getContext();
        List<ProductDTO> products;
        String match = environment.getArgument("match");
        if (match != null) {
            products = fetchProductsFromDatabaseWithMatching(ctx, match);
        } else {
            products = fetchAllProductsFromDatabase(ctx);
        }
        return products;
    }
};
```
每个DataFetcher都会传递一个graphql.schema.DataFetchingEnvironment对象，该对象包含要获取的字段，向该字段提供了哪些参数以及其他信息，例如字段的类型，其父类型，查询根对象或查询上下文对象

请注意，上面的数据获取程序代码如何将上下文对象用作特定于应用程序的安全性句柄来访问数据库。这是提供较低层调用上下文的常用技术。

一旦有了ProductDTO对象的列表，我们通常不需要在每个字段上使用专门的数据获取程序。 graphql-java附带了一个智能的graphql.schema.PropertyDataFetcher，它知道如何根据字段名称遵循POJO模式。在上面的示例中，有一个name字段，因此它将尝试查找 public String getName() POJO方法来获取数据。

graphql.schema.PropertyDataFetcher是默认情况下自动与每个字段关联的数据获取器。

但是，您仍然可以在DTO方法中访问graphql.schema.DataFetchingEnvironment。这使您可以在发送值之前对其进行调整。例如，在上面的示例中，我们有一个launchDate字段，它带有一个可选的dateFormat参数。我们可以让ProductDTO具有将日期格式应用到所需格式的逻辑。

```java
class ProductDTO {

    private ID id;
    private String name;
    private String description;
    private Double cost;
    private Double tax;
    private LocalDateTime launchDate;

    // ...

    public String getName() {
        return name;
    }

    // ...

    public String getLaunchDate(DataFetchingEnvironment environment) {
        String dateFormat = environment.getArgument("dateFormat");
        return yodaTimeFormatter(launchDate,dateFormat);
    }
}
```

# Customising PropertyDataFetcher

如上所述，graphql.schema.PropertyDataFetcher是graphql-java中字段的默认数据获取器，它将使用标准模式来获取对象字段值。

它以Java惯用方式支持POJO方式和Map方式。默认情况下，它假定对于graphql字段fieldX，它可以找到一个名为fieldX的POJO属性，或者如果该支持对象是Map则可以找到一个名为fieldX的映射键。

但是，您的graphql schema命名和运行时对象命名之间可能会有小的差异。例如，假设在运行时支持Java对象中，Product.description实际上表示为getDesc()。

如果使用SDL指定schema，则可以使用@fetch指令指示此重新映射。
```
directive @fetch(from : String!) on FIELD_DEFINITION

type Product {
    id : ID
    name : String
    description : String @fetch(from:"desc")
    cost : Float
    tax : Float
}
```

这将告诉graphql.schema.PropertyDataFetcher在为名为description的graphql字段获取数据时使用属性名称desc。

如果您要手工编码schema，那么您可以直接通过在字段数据获取器中连接来指定它。

```java
GraphQLFieldDefinition descriptionField = GraphQLFieldDefinition.newFieldDefinition()
        .name("description")
        .type(Scalars.GraphQLString)
        .build();

GraphQLCodeRegistry codeRegistry = GraphQLCodeRegistry.newCodeRegistry()
        .dataFetcher(
                coordinates("ObjectType", "description"),
                PropertyDataFetcher.fetching("desc"))
        .build();
```

# The interesting parts of the DataFetchingEnvironment

每个数据获取程序都传递一个graphql.schema.DataFetchingEnvironment对象，该对象使它可以进一步了解要获取的内容和提供的参数。这是DataFetchingEnvironment的一些更有趣的部分。

* <T> T getSource() - 源对象用于获取字段的信息。其对象是父字段获取的结果。在通常情况下，它是内存中的DTO对象，因此，简单的POJO getter将用于字段值。在更复杂的情况下，您可以检查一下以了解如何获取当前字段的特定信息。执行graphql字段树时，每个返回的字段值都将成为子字段的源对象。
* <T> T getRoot() - 此特殊对象用于为graphql查询提供种子。对于顶级字段，根和源是相同的。根对象在查询期间不会改变，它可以为null，因此不使用。
* Map <String, Object> getArguments() - 这表示字段上提供的参数，以及通过传入变量、AST文本和默认参数值解析的参数值。您可以使用字段的参数来控制它返回的值。
* <T> T getContext() - 上下文对象是在首次执行查询时设置的，并在查询的整个生命周期内保持不变。该上下文可以是任何值，通常用于为每个数据获取程序提供一些在尝试获取字段数据时所需的调用上下文。例如，当前用户凭据或数据库连接参数可以包含在上下文对象中，以便数据获取程序可以进行业务层调用。作为graphql系统设计师，您要做出的一项关键设计决策是如何在获取程序中使用上下文（如果有的话）。有些人使用的依赖项框架会自动将上下文注入数据获取器，因此不需要使用这些框架。
* ExecutionStepInfo getExecutionStepInfo() - 字段类型信息是捕获在执行查询时建立的所有字段类型信息的存储区。
* DataFetchingFieldSelectionSet getSelectionSet() - 选择集表示已在当前执行的字段下方“selected”的子字段。这有助于帮助我们提前了解客户所需的子字段信息。
* ExecutionId getExecutionId() - 每个查询执行都被赋予唯一的ID。您可以在日志中使用它来标记每个单独的查询。

# The interesting parts of ExecutionStepInfo

graphql查询的执行将创建字段及其类型的调用树。 graphql.execution.ExecutionStepInfo.getParentTypeInfo允许您向上导航，查看导致当前字段执行的类型和字段。

由于这是在执行期间形成的树路径，因此graphql.execution.ExecutionStepInfo.getPath方法将返回该路径的表示形式。这对于记录和调试查询很有用。

还有一些帮助方法可以帮助您获取非null的基础类型名称和列表包装的类型。

# The interesting parts of DataFetchingFieldSelectionSet

想象一下如下查询
```
query {
    products {
        # 下面的字段表示选择集，需要什么数据就填什么字段
        # 结果是根据这个选择集返回的
        name
        description
        sellingLocations {
            state
        }
    }
}
```

产品字段的此处子字段表示该字段的选择集。知道已请求了哪些字段集可能很有用，以便数据提取器可以优化数据访问查询。例如，SQL支持的系统可能能够使用field子选项来仅检索已请求的列。

在上面的示例中，我们询问了SellingLocations信息，因此，我们可以在请求产品信息和销售地点信息的同时进行更有效的数据访问查询。
