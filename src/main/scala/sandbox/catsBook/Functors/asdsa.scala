package sandbox.catsBook.Functors

import cats.Functor
object asdsa extends App{
    case class Dog[A](name: A)

    implicit val dogFunctor: Functor[Dog] = new Functor[Dog] {
      override def map[A, B](fa: Dog[A])(f: A => B): Dog[B] = Dog(f(fa.name))
    }
    // Dog // int
    // dog[A] // List
  import cats.syntax.functor._

  println(
    Dog(3)
      .map(a => a.toString)
      .name)

}
