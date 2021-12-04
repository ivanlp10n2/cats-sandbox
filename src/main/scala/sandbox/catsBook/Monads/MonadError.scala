package sandbox.catsBook.Monads

object MonadErrorApp extends App{

  import cats.Monad
  trait MonadErrorTest[F[_], E] extends Monad[F]{
    //lift error into `F` context
    def raiseError[A](e: E): F[A] // pure

    //handle an error, potentially recovering from it
    def handleErrorWith[A](fa: F[A])(f: E => F[A]): F[A] // flatmap

    //handle all errors, recovering from them
    def handleError[A](fa: F[A])(f: E => A): F[A] //map

    //test an instance of `F`
    // Failing if predicate is not satisfied
    def ensure[A](fa: F[A])(e: E)(test: A => Boolean): F[A]
  }

  type ErrorOr[A] = Either[String, A]

  import cats.MonadError
  val monadError = MonadError[ErrorOr, String]
  println(monadError.pure(42))
  val error = (monadError.raiseError("todo mal"))
  monadError.handleErrorWith(error)(e => e match {
    case "Something went wrong" => monadError.pure("recovering")
    case _ => monadError.raiseError("not ok")
  })
}
object Excercise extends App{
  import cats.MonadError
//  import cats.syntax.applicativeError._
//  import cats.syntax.applicative._
  def validateAdult[F[_]](age: Int)(implicit me: MonadError[F, Throwable]): F[Int]=
    me.ensure(me.pure(age))(new IllegalArgumentException("not adult"))(age => age >= 18)

//    if (age >= 18) age.pure[F]
//    else (new IllegalArgumentException("not adult")).raiseError[F, Int]
  import scala.util.Try
  println(validateAdult[Try](18))
  println(validateAdult[Try](8))

  type ExceptionOr[A] = Either[Throwable, A]
  println(validateAdult[ExceptionOr](8))
}
