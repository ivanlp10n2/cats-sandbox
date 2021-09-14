package sandbox.Functors

object ContraMap extends App {

  trait Printable[A] {
    self =>
    def format(value: A): String

    // Converts from Print[A] and f: B => A
    // To Print[B]. It can be used to create supertypes
    def contramap[B](func: B => A): Printable[B] =
      new Printable[B] {
        override def format(value: B): String = {
          self.format(func(value))
        }
      }
  }

  def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)

  {
    implicit val stringPrintable: Printable[String] =
      new Printable[String] {
        def format(value: String): String =
          s"'${value}'"
      }

    implicit val booleanPrintable: Printable[Boolean] =
      new Printable[Boolean] {
        def format(value: Boolean): String =
          if (value) "yes" else "no"
      }

    format("hello")
    // res2: String = "'hello'"
    format(true)
    // res3: String = "yes"

    implicit def boxPrintable[A](implicit ev: Printable[A])
    : Printable[Box[A]] =
      ev.contramap[Box[A]](a => a.value)


    final case class Box[A](value: A)
    format(Box("Hello"))
  }
}
