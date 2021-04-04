package io.github.dreamylost.scala3

/**
  * 结构类型: https://dotty.epfl.ch/docs/reference/changed-features/structural-types.html
  */
object StructuralTypes:

  //本地可选实例
  case class Record(elems: (String, Any)*) extends Selectable:
    //该方法将字段名称映射为其值。通过调用此方法来选择结构类型成员
    def selectDynamic(name: String): Any = elems.find(_._1 == name).get._2

  //结构体？
  type Person = Record {
    val name: String
    val age: Int
  }

  val person = Record("name" -> "Emma", "age" -> 42, "salary" -> 320L).asInstanceOf[Person]

  val invalidPerson = Record("name" -> "John", "salary" -> 42).asInstanceOf[Person]

  def test(): Unit =
    println(person.name)
    println(person.age)

    println(invalidPerson.name)
    // age field is java.util.NoSuchElementException: None.get
    //println(invalidPerson.age)

end StructuralTypes