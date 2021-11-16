package sandbox.errorHandling

import cats.data.{EitherT, Validated}
import cats.{Applicative, Monad}

object ErrorHandlingApp {

  //  Either
  //  EitherT
  //  Validated

  //  Error handling
  def division(x: Int, y: Int): Int = x / y // y = 0

  def divisionEither(x: Int, y: Int): ErrorEither[Int] =
    if (y == 0) Left("Division por 0")
    else Right(x / y)

  def sqrResult(x: Int): Int = ???

  /**
   * Either[A, B]
   * A => Left[A]
   * B => Right[B]
   * */
  type ErrorEither[A] = Either[String, A]
  Monad[ErrorEither].flatMap(divisionEither(0, 0))((x: Int) => Right(x + 1))

  /**
   * Monad: una forma de sequenciar operaciones
   * flatMap: F[A] > A => F[B]  > F[B]
   * map:     F[A] > A => B     > F[B]
   * fail fast
   * */
  Option(3).flatMap(x => Option(x + 1))
  divisionEither(10, 5).map(x => sqrResult(x))

  for {
    x <- divisionEither(10, 5)
    y <- divisionEither(10, 5)
  } yield x + y


  val x = 3
  Applicative[Option].pure(x)
  /**
   * Applicative: una forma
   * pure: x => F[x]
   * product: F[A] > F[B] > F[(A,B)]
   */
  def userData = ???
  Validated

  /**
   * Validated[A, B]
   * A => Invalid[A]
   * B => Valid[B]
 * */
  // Validated => cats data structure
//  def validate =
//    for {
//      x <- validateUsername(10, 5)
//      y <- validateEmail(10, 5)
//    } yield x + y

  import cats.data.Validated.Invalid
  import cats.data.Validated.Valid

  type ErrorValidated[A]= Validated[String, A]
  def divisionValidated(x: Int, y: Int): ErrorValidated[Int] =
    if (y == 0) Invalid("Division por 0")
    else Valid(x / y)

    /**
     * EiterT = Monad Transformer
     * Either[F[_], A, B]
     *
     * F => Effect[F]
     * A => Left[A]
     * B => Right[B]
     * */
//  Option => None / Some // empty result
//  Either => Left / right // error handling
//  Future => // async operation
//  IO => // async operation | delay operations
//  Resource // adquirir y terminar conecciones
  import scala.concurrent.Future
  val a : Either[String, Int] = Right(3)
  def b : Future[Either[String, Option[Int]]] = ???
  def c : EitherT[Future, String, Int] = ???

  type ApplicationError = String
  type ApplicationResult[A] = Future[Either[ApplicationError, A]]
  def foo: ApplicationResult[Int] = ???


}
