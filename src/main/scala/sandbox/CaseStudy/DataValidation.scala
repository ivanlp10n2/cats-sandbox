package sandbox.CaseStudy

object DataValidation extends App{
//  type Check[E, A] = A => Either[E, A]

  sealed trait Check[E, A]{
//    import cats.syntax.semigroup._
    def apply(a: A) : Either[E, A]

    //    String => Option[Int]
    //    A => F[B] flatMap B => F[C]
    //    Option[B] flatMap B => Option[C]

//    def and(that: Check[E, A]): Check[E,A] =
//      Check.this.apply(_).flatMap(that.apply(_))
  }
//  final case class Ok[A](value: A) extends Check[Nothing,A]
//  final case class Error[A](value: A) extends Check[A, Nothing]
//  type Login = String
//  class CheckLogin(val login: Login) extends Check[String, Login]


}
