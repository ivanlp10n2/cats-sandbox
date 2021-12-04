package sandbox.catsBook.Functors

object Contramap2 extends App{
  trait Printable[A] { self =>
    def format(value: A): String

    def contramap[B](func: B => A): Printable[B] =
      new Printable[B] {
        def format(value: B): String =
          self.format(func(value))
      }
  }

  def format[A](value: A)(implicit P: Printable[A]): String =
    P.format(value)

  object Implicits {
    implicit val stringPrintable: Printable[String] =
      new Printable[String] {
        def format(value: String): String =
          s"'${value}'"
      }

    implicit val booleanPrintable: Printable[Boolean] =
      new Printable[Boolean] {
        def format(value: Boolean): String =
          if(value) "yes" else "no"
      }

    implicit def boxPrintable[A](implicit P: Printable[A]): Printable[Box[A]] =
      P.contramap[Box[A]]( box => box.value)
  }

  final case class Box[A](value: A)

  import Implicits._
  assert (format(Box("hello world")) == "hello world")
  assert (format(Box(true)) == "yes")
}
