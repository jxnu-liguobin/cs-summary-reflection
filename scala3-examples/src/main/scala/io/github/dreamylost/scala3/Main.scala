package io.github.dreamylost.scala3

//main函数
@main
def Main(args: String*): Unit =
  runExample("Trait Params")(TraitParams.test())

  runExample("Enum Types")(EnumTypes.test())

  runExample("Context Functions")(ContextFunctions.test())

  runExample("Given Instances")(GivenInstances.test())

  runExample("Conversion")(Conversion.test())

  runExample("Union Types")(UnionTypes.test())

  runExample("Intersection Types")(IntersectionTypes.test())

  runExample("Type Lambda")(TypeLambdas.test())

  runExample("Multiversal Equality")(MultiversalEquality.test())

  runExample("Parameter Untupling")(ParameterUntupling.test())

  runExample("Structural Types")(StructuralTypes.test())

  runExample("Pattern Matching")(PatternMatching.test())
end Main

private def runExample(name: String)(f: => Unit): Unit =
  println(Console.MAGENTA + s"$name example:" + Console.RESET)
  f
  println()
