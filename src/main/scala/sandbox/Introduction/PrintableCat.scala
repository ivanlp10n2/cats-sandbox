package sandbox.Introduction

import sandbox.Introduction.PrintableApp.Printable

object PrintableCat extends App {
  final case class Cat(name: String, age: Int, color: String)

  object PrintableCatsInstances {
    implicit val printCat = new Printable[Cat] {

      import PrintableApp.PrintableInstances._
      override def format(value: Cat): String = {
        val name = Printable format value.name
        val age = Printable format value.age
        val color = Printable format value.color
        s"$name is a $age year-old $color cat."
      }
    }
  }


  import PrintableApp.PrintableSyntax._
  import PrintableCatsInstances._

  Cat("jose", 34, "blanco").print
}
