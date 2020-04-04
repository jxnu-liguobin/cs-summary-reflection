---
title: Data mapping
categories:
- GraphqlJava
tags: [graphql-java 1.4文档]
description: 本章介绍graphql-java中如何将数据映射到Java对象类型（DTO）
---

* 目录
{:toc}

# How graphql maps object data to types

graphql的核心是声明类型schema并将其映射到支持的运行时数据。

作为类型schema的设计者，要使这些元素在中间相遇是您的挑战。

例如，假设我们想要一个graphql类型的架构，如下所示
```graphql
type Query {
    products(match : String) : [Product]
}

type Product {
    id : ID
    name : String
    description : String
    cost : Float
    tax : Float
}
```

然后，我们可以通过类似于以下内容的简单schema运行查询
```graphql
query ProductQuery {
    products(match : "Paper*")
    {
        id, name, cost, tax
    }
}
```

我们将在Query.products字段上有一个DataFetcher，它负责查找与传入的参数匹配的产品列表。

现在想象我们有3个下游服务。一种获取产品信息，一种获取产品成本信息，另一种获取产品税信息。

graphql-java的工作原理是：在对象上运行数据获取程序以获取所有这些信息，并将其映射回schema中指定的类型。

我们的挑战是采用这三种信息来源并将它们呈现为一种统一的类型。

我们可以在进行这些计算的成本和税收字段上指定数据获取程序，但这要维护得多，并且很可能导致N + 1性能问题。

我们最好在Query.products数据提取器中完成所有这些工作，并创建数据的统一视图。
```java
DataFetcher productsDataFetcher = new DataFetcher() {
    @Override
    public Object get(DataFetchingEnvironment env) {
        String matchArg = env.getArgument("match");

        List<ProductInfo> productInfo = getMatchingProducts(matchArg);

        List<ProductCostInfo> productCostInfo = getProductCosts(productInfo);

        List<ProductTaxInfo> productTaxInfo = getProductTax(productInfo);

        return mapDataTogether(productInfo, productCostInfo, productTaxInfo);
    }
};
```

因此，查看上面的代码，我们需要对3种信息进行组合，以便上面的graphql查询可以访问字段 id，name，cost，tax。
我们有两种创建此映射的方法。一种是通过使用非类型安全的List<Map>结构，而另一种是通过封装为类型安全的List<ProductDTO>类。

Map 示例
```java 
private List<Map> mapDataTogetherViaMap(List<ProductInfo> productInfo, List<ProductCostInfo> productCostInfo, List<ProductTaxInfo> productTaxInfo) {
    List<Map> unifiedView = new ArrayList<>();
    for (int i = 0; i < productInfo.size(); i++) {
        ProductInfo info = productInfo.get(i);
        ProductCostInfo cost = productCostInfo.get(i);
        ProductTaxInfo tax = productTaxInfo.get(i);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("id", info.getId());
        objectMap.put("name", info.getName());
        objectMap.put("description", info.getDescription());
        objectMap.put("cost", cost.getCost());
        objectMap.put("tax", tax.getTax());

        unifiedView.add(objectMap);
    }
    return unifiedView;
}
```

DTO 示例
```java 
class ProductDTO {
    private final String id;
    private final String name;
    private final String description;
    private final Float cost;
    private final Float tax;

    public ProductDTO(String id, String name, String description, Float cost, Float tax) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.tax = tax;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getCost() {
        return cost;
    }

    public Float getTax() {
        return tax;
    }
}

private List<ProductDTO> mapDataTogetherViaDTO(List<ProductInfo> productInfo, List<ProductCostInfo> productCostInfo, List<ProductTaxInfo> productTaxInfo) {
    List<ProductDTO> unifiedView = new ArrayList<>();
    for (int i = 0; i < productInfo.size(); i++) {
        ProductInfo info = productInfo.get(i);
        ProductCostInfo cost = productCostInfo.get(i);
        ProductTaxInfo tax = productTaxInfo.get(i);

        ProductDTO productDTO = new ProductDTO(
                info.getId(),
                info.getName(),
                info.getDescription(),
                cost.getCost(),
                tax.getTax()
        );
        unifiedView.add(productDTO);
    }
    return unifiedView;
}
```

graphql引擎现在将使用该对象列表并对其运行查询子字段 id，name，cost，tax。

graphql-java中的默认数据获取程序为graphql.schema.PropertyDataFetcher，它同时具有Map支持和POJO支持。

对于列表中的每个对象，它将查找一个id字段，在Map中通过名称或通过getId()方法找到它，并将其发送回graphql响应中。它会针对该类型的查询中的每个字段执行此操作。

通过在更高级别的数据读取器中创建“统一视图”，您已经在数据的运行时视图和数据的graphql schema视图之间进行了映射。
