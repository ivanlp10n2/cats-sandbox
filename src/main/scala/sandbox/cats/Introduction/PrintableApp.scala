package sandbox.cats.Introduction

import sandbox.cats.Introduction.PrintableCat.Cat

object PrintableApp extends App {

  trait Printable[A] {
    def format(value: A): String
  }

  object PrintableInstances {
    def apply[A](implicit instance: Printable[A]): Printable[A] =
      instance

    implicit val printString = new Printable[String] {
      override def format(value: String): String = value
    }

    implicit val printInt = new Printable[Int] {
      override def format(value: Int): String = value.toString
    }
  }

  object Printable {
    def format[A](value: A)(implicit printer: Printable[A]): String =
      printer format value

    def print[A](value: A)(implicit printer: Printable[A]): Unit =
      println(format(value))
  }

  implicit val printCat: Printable[Cat] = new Printable[Cat] {
    override def format(value: Cat): String = value.toString
  }
  val cat = Cat("jose", 32, "blanco")
  Printable.print(cat)

  object PrintableSyntax {
    implicit class PrintableOps[A](value: A) {
      def format(implicit printable: Printable[A]): String =
        printable.format(value)

      def print(implicit printable: Printable[A]): Unit =
        println(format)
    }
  }

  import PrintableSyntax._
  cat.print

}
