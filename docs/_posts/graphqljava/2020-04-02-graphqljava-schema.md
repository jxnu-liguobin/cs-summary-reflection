---
title: Schema
categories:
- GraphqlJava
tags: [graphql-java 1.4文档]
description: 本章介绍graphql-java中的Schema如何定义
---

* 目录
{:toc}

# Creating a schema

定义数据模型

GraphQL API具有一个schema，该schema定义了可以查询或突变的每个字段以及这些字段的类型。

graphql-java提供了两种不同的方式来定义schema：以编程方式使用Java代码或通过特殊的graphql dsl（称为SDL）。

如果不确定要使用哪种方式，我们建议使用SDL。

SDL 示例
```graphql
type Foo {
    bar: String
}
```
Java 示例
```java
GraphQLObjectType fooType = newObject()
    .name("Foo")
    .field(newFieldDefinition()
            .name("bar")
            .type(GraphQLString))
    .build();
```

# DataFetcher and TypeResolver

定义数据读取器和类型解析器

DataFetcher提供字段的数据（如果是突变的话，并更改某些内容）。

GraphQL定义了两种请求类型，查询query和突变mutation（突变包含新增、删除、修改）。

每个字段定义都有一个DataFetcher。如果未配置，则使用PropertyDataFetcher，这是一个默认的读取器。
PropertyDataFetcher从Map和Java Bean获取数据。因此，当字段名称与Map关键字或源Object的属性名称匹配时，就不需要DataFetcher。

TypeResolver帮助graphql-java决定具体值属于哪种类型。Interface和Union类型需要此功能。

例如，假设您有一个名为MagicUserType的Interface，该接口可解析回一系列名为Wizard，Witch和Necromancer的Java类。类型解析器负责检查运行时对象，并确定应使用什么GraphqlObjectType来表示该对象，从而决定要调用哪些数据读取程序和字段。

一个常见的实现如下
```java
new TypeResolver() {
    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment env) {
        Object javaObject = env.getObject();
        if (javaObject instanceof Wizard) {
            return env.getSchema().getObjectType("WizardType");
        } else if (javaObject instanceof Witch) {
            return env.getSchema().getObjectType("WitchType");
        } else {
            return env.getSchema().getObjectType("NecromancerType");
        }
    }
};
```

# Creating a schema using the SDL

通过SDL定义架构时，在创建可执行schema时，需要提供所需的DataFetcher和TypeResolver。

下面以名为starWarsSchema.graphqls的静态schema文件为例
```graphql
schema {
    query: QueryType
}

type QueryType {
    # !表示不可为空，否则报错
    hero(episode: Episode): Character
    human(id : String) : Human
    droid(id: ID!): Droid
}


enum Episode {
    NEWHOPE
    EMPIRE
    JEDI
}

interface Character {
    id: ID!
    name: String!
    # []表示返回一个列表
    friends: [Character]
    appearsIn: [Episode]!
}

type Human implements Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
    homePlanet: String
}

type Droid implements Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
    primaryFunction: String
}
```

如上使用静态schema定义的文件starWarsSchema.graphqls，包含字段和类型定义，但是您需要运行时织入才能使其成为真正的可执行schema。

运行时连接包含DataFetcher，TypeResolvers和自定义Scalar，它们是制作完全可执行的schema所必需的。

您可以使用以下建造者模式将其连接在一起
```java
RuntimeWiring buildRuntimeWiring() {
    return RuntimeWiring.newRuntimeWiring()
            .scalar(CustomScalar)
            //数据读取器
            .type("QueryType", typeWiring -> typeWiring
                    .dataFetcher("hero", new StaticDataFetcher(StarWarsData.getArtoo()))
                    .dataFetcher("human", StarWarsData.getHumanDataFetcher())
                    .dataFetcher("droid", StarWarsData.getDroidDataFetcher())
            )
            .type("Human", typeWiring -> typeWiring
                    .dataFetcher("friends", StarWarsData.getFriendsDataFetcher())
            )
            .type("Droid", typeWiring -> typeWiring
                    .dataFetcher("friends", StarWarsData.getFriendsDataFetcher())
            )
            //类型解析器
            .type(
                    newTypeWiring("Character")
                            .typeResolver(StarWarsData.getCharacterTypeResolver())
                            .build()
            )
            .build();
}
```

最后，您可以通过连接静态schema和运行时织入对象（RuntimeWiring），生成可执行schema，如本示例所示
```java
SchemaParser schemaParser = new SchemaParser();
SchemaGenerator schemaGenerator = new SchemaGenerator();

File schemaFile = loadSchema("starWarsSchema.graphqls");

TypeDefinitionRegistry typeRegistry = schemaParser.parse(schemaFile);
RuntimeWiring wiring = buildRuntimeWiring();
GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, wiring);
```

除了上面显示的建造器风格外，还可以使用WiringFactory接口来连接TypeResolver和DataFetcher。由于可以检查SDL定义以确定要连接的内容，因此可以进行更动态的运行时连接。例如，您可以查看SDL指令或SDL定义的其他方面，以帮助您确定要创建的运行时。
```java
RuntimeWiring buildDynamicRuntimeWiring() {
    WiringFactory dynamicWiringFactory = new WiringFactory() {
        @Override
        public boolean providesTypeResolver(TypeDefinitionRegistry registry, InterfaceTypeDefinition definition) {
            return getDirective(definition,"specialMarker") != null;
        }

        @Override
        public boolean providesTypeResolver(TypeDefinitionRegistry registry, UnionTypeDefinition definition) {
            return getDirective(definition,"specialMarker") != null;
        }

        @Override
        public TypeResolver getTypeResolver(TypeDefinitionRegistry registry, InterfaceTypeDefinition definition) {
            Directive directive  = getDirective(definition,"specialMarker");
            return createTypeResolver(definition,directive);
        }

        @Override
        public TypeResolver getTypeResolver(TypeDefinitionRegistry registry, UnionTypeDefinition definition) {
            Directive directive  = getDirective(definition,"specialMarker");
            return createTypeResolver(definition,directive);
        }

        @Override
        public boolean providesDataFetcher(TypeDefinitionRegistry registry, FieldDefinition definition) {
            return getDirective(definition,"dataFetcher") != null;
        }

        @Override
        public DataFetcher getDataFetcher(TypeDefinitionRegistry registry, FieldDefinition definition) {
            Directive directive = getDirective(definition, "dataFetcher");
            return createDataFetcher(definition,directive);
        }
    };
    return RuntimeWiring.newRuntimeWiring()
            .wiringFactory(dynamicWiringFactory).build();
}
```

# Creating a schema programmatically

以编程方式创建schema时，将在类型创建时提供DataFetcher和TypeResolver

Java示例如下
```java
DataFetcher<Foo> fooDataFetcher = new DataFetcher<Foo>() {
    @Override
    public Foo get(DataFetchingEnvironment environment) {
        // environment.getSource() is the value of the surrounding
        // object. In this case described by objectType
        Foo value = perhapsFromDatabase(); // Perhaps getting from a DB or whatever
        return value;
    }
};

GraphQLObjectType objectType = newObject()
        .name("ObjectType")
        .field(newFieldDefinition()
                .name("foo")
                .type(GraphQLString)
        )
        .build();

GraphQLCodeRegistry codeRegistry = newCodeRegistry()
        .dataFetcher(
                coordinates("ObjectType", "foo"),
                fooDataFetcher)
        .build();

```

# Types

GraphQL类型系统支持以下类型

* Scalar
* Object
* Interface
* Union
* InputObject
* Enum

## Scalar

graphql-java支持以下标量

标准graphql标量

* *GraphQLString 
* *GraphQLBoolean 
* *GraphQLInt 
* *GraphQLFloat 
* *GraphQLID

扩展graphql-java标量

* GraphQLLong
* GraphQLShort
* GraphQLByte
* GraphQLFloat
* GraphQLBigDecimal
* GraphQLBigInteger

**请注意，客户可能无法理解扩展标量范围的语义。例如，将Java Long（最大值2^63-1）映射到JavaScript Number（最大值2^53-1）对您来说可能有问题。**

## Object

SDL 示例

```graphql
type SimpsonCharacter {
    name: String
    mainCharacter: Boolean
}
```

Java 示例
```java
GraphQLObjectType simpsonCharacter = newObject()
.name("SimpsonCharacter")
.description("A Simpson character")
.field(newFieldDefinition()
        .name("name")
        .description("The name of the character.")
        .type(GraphQLString))
.field(newFieldDefinition()
        .name("mainCharacter")
        .description("One of the main Simpson characters?")
        .type(GraphQLBoolean))
.build();
```

## Interface

接口是类型的抽象定义。

SDL 示例

```graphql
interface ComicCharacter {
    name: String;
}
```

Java 示例
```java
GraphQLInterfaceType comicCharacter = newInterface()
    .name("ComicCharacter")
    .description("An abstract comic character.")
    .field(newFieldDefinition()
            .name("name")
            .description("The name of the character.")
            .type(GraphQLString))
    .build();
```

## Union

SDL 示例
```graphql
type Cat {
    name: String;
    lives: Int;
}

type Dog {
    name: String;
    bonesOwned: int;
}

union Pet = Cat | Dog
```

Java 示例
```java
TypeResolver typeResolver = new TypeResolver() {
    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment env) {
        if (env.getObject() instanceof Cat) {
            return CatType;
        }
        if (env.getObject() instanceof Dog) {
            return DogType;
        }
        return null;
    }
};
GraphQLUnionType PetType = newUnionType()
        .name("Pet")
        .possibleType(CatType)
        .possibleType(DogType)
        .build();

GraphQLCodeRegistry codeRegistry = newCodeRegistry()
        .typeResolver("Pet", typeResolver)
        .build();
```

## Enum

SDL 示例
```graphql
enum Color {
    RED
    GREEN
    BLUE
}
```
Java 示例
```java
GraphQLEnumType colorEnum = newEnum()
    .name("Color")
    .description("Supported colors.")
    .value("RED")
    .value("GREEN")
    .value("BLUE")
    .build();
```

## ObjectInputType

SDL 示例
```graphql
input Character {
    name: String
}
```
当使用graphql做突变操作时就需要input。（类似restful中定义request body）
Java 示例
```java
GraphQLInputObjectType inputObjectType = newInputObject()
    .name("inputObjectType")
    .field(newInputObjectField()
            .name("field")
            .type(GraphQLString))
    .build();
```

## Type References (recursive types)

GraphQL支持递归类型：例如，一个Person可以包含相同类型的朋友列表。

为了能够声明这种类型，graphql-java具有GraphQLTypeReference类。

创建schema时，GraphQLTypeReference将替换为实际类型对象。

示例如下
```java
GraphQLObjectType person = newObject()
        .name("Person")
        .field(newFieldDefinition()
                .name("friends")
                .type(GraphQLList.list(GraphQLTypeReference.typeRef("Person"))))
        .build();
```

通过SDL声明schema时，不需要为递归类型进行特殊处理，因为它可以为您检测到并自动完成。

# Modularising the Schema SDL

拥有一个大的schema文件并不总是可行的。您可以使用两种技术对模式进行模块化。

第一种技术是将多个Schema SDL文件合并到一个逻辑单元中。在下面的情况下，在生成模式之前，已将schema拆分为多个文件并合并在一起。

```java
SchemaParser schemaParser = new SchemaParser();
SchemaGenerator schemaGenerator = new SchemaGenerator();

File schemaFile1 = loadSchema("starWarsSchemaPart1.graphqls");
File schemaFile2 = loadSchema("starWarsSchemaPart2.graphqls");
File schemaFile3 = loadSchema("starWarsSchemaPart3.graphqls");

TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();

//合并
typeRegistry.merge(schemaParser.parse(schemaFile1));
typeRegistry.merge(schemaParser.parse(schemaFile2));
typeRegistry.merge(schemaParser.parse(schemaFile3));

GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, buildRuntimeWiring());
```
Graphql SDL类型系统具有用于将schema模块化的另一种方法。您可以使用type extensions将其他字段和接口添加到类型。

假设您在一个schema文件中有这样的类型定义
```graphql
type Human {
    id: ID!
    name: String!
}
```
系统的另一部分可以扩展此类型以为其添加更多形状。

```graphql
extend type Human implements Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
}
```
您可以根据需要选择任意数量的扩展名。它们将按照遇到的顺序组合在一起。重复的字段将合并为一个（但是不允许将字段重新定义为新类型）。
```graphql
extend type Human {
    homePlanet: String
}
```
有了所有这些类型扩展后，Human类型现在在运行时看起来像这样。
```graphql
type Human implements Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
    homePlanet: String
}
```

这在最高层尤其有用。您可以使用扩展类型向顶层schema“query”添加新字段。团队可以贡献“sections”来提供总的graphql查询。
```graphql
schema {
  query: CombinedQueryFromMultipleTeams
}

type CombinedQueryFromMultipleTeams {
    createdTimestamp: String
}

# maybe the invoicing system team puts in this set of attributes
extend type CombinedQueryFromMultipleTeams {
    invoicing: Invoicing
}

# and the billing system team puts in this set of attributes
extend type CombinedQueryFromMultipleTeams {
    billing: Billing
}

# and so and so forth
extend type CombinedQueryFromMultipleTeams {
    auditing: Auditing
}
```

# Subscription Support

订阅使您可以执行查询，并且只要该查询的支持对象发生更改，就会发送更新。
```graphql
subscription foo {
    # normal graphql query
}
```